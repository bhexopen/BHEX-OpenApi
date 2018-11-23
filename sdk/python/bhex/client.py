from . base import Request


class BhexClient(Request):

    def time(self):
        """
        Check server time
        """
        return self._get('time')

    def broker_info(self):
        """
        Broker information
        """
        return self._get('brokerInfo')

    def depth(self, symbol, limit=100):
        """
        Market Data endpoints
        """
        params = {
            'symbol': symbol,
            'limit': limit,
        }
        return self._quote_get('depth', params=params)

    def trades(self, symbol, limit=100):
        """
        Recent trades list
        """
        params = {
            'symbol': symbol,
            'limit': limit,
        }
        return self._quote_get('trades', params=params)

    def klines(self, symbol, interval='1m', start_time='', end_time='', limit=100):
        """
        Kline/Candlestick data
        """
        params = {
            'symbol': symbol,
            'interval': interval,
            'startTime': start_time,
            'endTime': end_time,
            'limit': limit,
        }
        return self._quote_get('klines', params=params)

    def ticker_24hr(self, symbol=''):
        """
        24hr ticker price change statistics
        """
        params = {
            'symbol': symbol,
        }
        return self._quote_get('ticker/24hr', params=params)

    def ticker_price(self, symbol=''):
        """
        Symbol price ticker
        """
        params = {
            'symbol': symbol,
        }
        return self._quote_get('ticker/price', params=params)

    def book_ticker(self, symbol=''):
        """
        Symbol order book ticker
        """
        params = {
            'symbol': symbol,
        }
        return self._quote_get('ticker/bookTicker', params=params)

    def order_new(self, **params):
        """
        New order  (TRADE)
        """
        return self._post('order', signed=True, data=params)

    def order_test(self, **params):
        """
        Test new order (TRADE)
        """
        return self._post('order/test', signed=True, data=params)

    def order_get(self, order_id='', orig_client_order_id=''):
        """
        Query order (USER_DATA)
        """
        params = {
            'orderId': order_id,
            'origClientOrderId': orig_client_order_id
        }
        return self._get('order', signed=True, params=params)

    def order_cancel(self, order_id='', client_order_id=''):
        """
        Cancel order (TRADE)
        """
        params = {
            'orderId': order_id,
            'clientOrderId': client_order_id
        }
        return self._delete('order', signed=True, params=params)

    def open_orders(self, **params):
        """
        Current open orders (USER_DATA)
        """
        return self._get('openOrders', signed=True, params=params)

    def history_orders(self, **params):
        """
        Current open orders (USER_DATA)
        """
        return self._get('historyOrders', signed=True, params=params)

    def account(self):
        """
        Account information (USER_DATA)
        """
        return self._get('account', signed=True)

    def my_trades(self, **params):
        """
        Account trade list (USER_DATA)
        """
        return self._get('myTrades', signed=True, params=params)

    def stream_get_listen_key(self):
        """
        Start user data stream (USER_STREAM)
        """
        return self._post('userDataStream', signed=True)

    def stream_keepalive(self, listen_key):
        """
        Keepalive user data stream (USER_STREAM)
        """
        params = {
            'listenKey': listen_key
        }
        return self._put('userDataStream', signed=True, params=params)

    def stream_close(self, listen_key):
        """
        Close user data stream (USER_STREAM)
        """
        params = {
            'listenKey': listen_key
        }
        return self._delete('userDataStream', signed=True, params=params)
