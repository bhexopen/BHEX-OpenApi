import logging
import time

from broker.client import BrokerOptionClient
from broker.exceptions import BrokerRequestException, BrokerApiException

if __name__ == '__main__':

    proxies = {
        "http": "",
        "https": "",
    }

    b = BrokerOptionClient(api_key='', secret='', proxies=proxies, entry_point='')

    try:

        print(b.time())

        print(int(time.time() * 1000))

        print(b.broker_info())

        print(b.depth('BTC0405CS4150'))

        print(b.trades('BTC0405CS4150'))

        print(b.klines('BTC0405CS4150'))

        result = b.order_new(symbol='BTC0405CS4150', side='BUY', type='LIMIT', quantity='10', price='0.1', timeInForce='GTC')

        print(result)

        order_id = result['orderId']

        print(order_id)

        print(b.order_get(order_id=order_id))

        print(b.order_cancel(order_id=order_id))

        print(b.open_orders())

        print(b.history_orders())

        print(b.option_account())

        print(b.my_trades())

        listen_key = b.stream_get_listen_key()

        print(listen_key)

        print(b.stream_keepalive(listen_key.get('listenKey')))

        print(b.stream_close(listen_key.get('listenKey')))

    except BrokerRequestException as bre:
        logging.error(bre)
    except BrokerApiException as bae:
        logging.error(bae)
