import json
import threading

from autobahn.twisted.websocket import WebSocketClientFactory, WebSocketClientProtocol, connectWS
from twisted.internet import reactor, ssl
from twisted.internet.error import ReactorAlreadyRunning
from twisted.internet.protocol import ReconnectingClientFactory

from broker import user_agent
from broker.exceptions import BrokerRequestException
from broker.client import BrokerClient


class BrokerClientProtocol(WebSocketClientProtocol):

    def __init__(self, factory, payload=None):
        super(BrokerClientProtocol, self).__init__()
        self.factory = factory
        self.payload = payload

    def onOpen(self):
        self.factory.protocol_instance = self

    def onConnect(self, response):
        if self.payload:
            self.sendMessage(self.payload, isBinary=False)
        self.factory.resetDelay()

    def onMessage(self, payload, isBinary):
        if not isBinary:
            try:
                payload_obj = json.loads(payload.decode('utf8'))
            except ValueError:
                pass
            else:
                self.factory.callback(payload_obj)


class BrokerReconnectingClientFactory(ReconnectingClientFactory):

    # set initial delay to a short time
    initialDelay = 0.1

    maxDelay = 10

    maxRetries = 5


class BrokerClientFactory(WebSocketClientFactory, BrokerReconnectingClientFactory):

    def __init__(self, *args, **kwargs):
        self.payload = kwargs.pop('payload')
        WebSocketClientFactory.__init__(self, *args, **kwargs)
        self.protocol_instance = None
        self.base_client = None

    protocol = BrokerClientProtocol
    _reconnect_error_payload = {
        'e': 'error',
        'm': 'Max reconnect retries reached'
    }

    def clientConnectionFailed(self, connector, reason):
        self.retry(connector)
        if self.retries > self.maxRetries:
            self.callback(self._reconnect_error_payload)

    def clientConnectionLost(self, connector, unused_reason):
        self.retry(connector)
        if self.retries > self.maxRetries:
            self.callback(self._reconnect_error_payload)

    def buildProtocol(self, addr):
        return BrokerClientProtocol(self, payload=self.payload)


class BrokerSocketManager(threading.Thread):

    def __init__(self, entry_point, rest_entry_point, api_key='', secret='', auth=True):
        threading.Thread.__init__(self)
        self.factories = {}
        self._conns = {}
        self._connected_event = threading.Event()
        self._api_key = api_key
        self._secret = secret
        self._user_timer = None
        self._listen_key = None

        if auth:
            self._client = BrokerClient(rest_entry_point, api_key=self._api_key, secret=self._secret) if api_key and secret else None

        if not entry_point.endswith('/'):
            entry_point = entry_point + '/'
        self._entry_point = entry_point

    def _start_socket(self, id_, path, payload, callback):
        if id_ in self._conns:
            return False

        factory_url = self._entry_point + path
        factory = BrokerClientFactory(factory_url, useragent=user_agent, payload=payload)
        factory.base_client = self
        factory.protocol = BrokerClientProtocol
        factory.callback = callback
        factory.reconnect = True
        self.factories[id_] = factory
        reactor.callFromThread(self.add_connection, id_)

    def _start_quote_socket(self, id_, payload, callback):
        self._start_socket(id_, 'quote/ws/v1', payload, callback)

    def _start_quote_socket_v2(self, id_, payload, callback):
        self._start_socket(id_, 'quote/ws/v2', payload, callback)

    def _start_auth_socket(self, id_, payload, callback):
        listen_key = self._client.stream_get_listen_key()
        if not listen_key:
            raise BrokerRequestException('Get listen key failure.')
        self._listen_key = listen_key.get('listenKey', '')
        path = 'ws/' + self._listen_key
        self._start_socket(id_, path, payload, callback)
        self._start_user_timer()

    def add_connection(self, id_):
        factory = self.factories[id_]
        context_factory = ssl.ClientContextFactory()
        self._conns[id_] = connectWS(factory, context_factory)

    def _start_user_timer(self):
        self._user_timer = threading.Timer(1800, self._keep_alive_user_socket)
        self._user_timer.setDaemon(True)
        self._user_timer.start()

    def _keep_alive_user_socket(self):
        self._client.stream_keepalive(self._listen_key)
        self._start_user_timer()

    def stop_socket(self, conn_key):
        if conn_key not in self._conns:
            return

        self._conns[conn_key].factory = WebSocketClientFactory(self._entry_point)
        self._conns[conn_key].disconnect()
        del self._conns[conn_key]

    def run(self):
        try:
            reactor.run(installSignalHandlers=False)
        except ReactorAlreadyRunning:
            # Ignore error about reactor already runing
            pass

    def close(self):
        keys = set(self._conns.keys())
        for key in keys:
            self.stop_socket(key)

        reactor.callFromThread(reactor.stop)
        self._conns = {}


class BrokerWss(BrokerSocketManager):

    def subscribe_to_realtimes(self, symbol, callback):
        id_ = "_".join(["realtimes", symbol])
        data = {
            'event': 'sub',
            'topic': 'realtimes',
            'symbol': symbol,
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket(id_, payload, callback)

    def subscribe_to_trades(self, symbol, callback):
        id_ = "_".join(["trades", symbol])
        data = {
            'event': 'sub',
            'topic': 'trade',
            'symbol': symbol,
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket(id_, payload, callback)

    def subscribe_to_kline(self, symbol, interval, callback):
        id_ = "_".join(["kline", symbol, interval])
        data = {
            'event': 'sub',
            'topic': 'kline_' + interval,
            'symbol': symbol,
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket(id_, payload, callback)

    def subscribe_to_depth(self, symbol, callback):
        id_ = "_".join(["depth", symbol])
        data = {
            'event': 'sub',
            'topic': 'depth',
            'symbol': symbol,
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket(id_, payload, callback)

    def subscribe_to_index(self, symbol, callback):
        id_ = "_".join(["index", symbol])
        data = {
            'event': 'sub',
            'topic': 'index',
            'symbol': symbol,
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket(id_, payload, callback)

    def user_data_stream(self, callback):
        return self._start_auth_socket('user_data_stream', None, callback)


class BrokerWssV2(BrokerWss):

    def subscribe_to_realtimes(self, symbol, callback):
        id_ = "_".join(["realtimes", symbol])
        data = {
            'topic': 'realtimes',
            'event': "sub",
            'params': {
                'binary': False,
                'symbol': symbol
            }
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket_v2(id_, payload, callback)

    def subscribe_to_trades(self, symbol, callback):
        id_ = "_".join(["trade", symbol])
        data = {
            'topic': 'trade',
            'event': "sub",
            'params': {
                'binary': False,
                'symbol': symbol
            }
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket_v2(id_, payload, callback)

    def subscribe_to_kline(self, symbol, interval, callback):
        id_ = "_".join(["kline", symbol, interval])
        data = {
            'topic': 'kline',
            'event': "sub",
            'params': {
                'binary': False,
                'symbol': symbol,
                'klineType': interval
            }
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket_v2(id_, payload, callback)

    def subscribe_to_depth(self, symbol, callback):
        id_ = "_".join(["depth", symbol])
        data = {
            'topic': 'depth',
            'event': "sub",
            'params': {
                'binary': False,
                'symbol': symbol
            }
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket_v2(id_, payload, callback)

    def subscribe_to_book_ticker(self, symbol, callback):
        id_ = "_".join(["bookTicker", symbol])
        data = {
            'topic': 'bookTicker',
            'event': "sub",
            'params': {
                'binary': False,
                'symbol': symbol
            }
        }
        payload = json.dumps(data, ensure_ascii=False).encode('utf8')
        return self._start_quote_socket_v2(id_, payload, callback)
