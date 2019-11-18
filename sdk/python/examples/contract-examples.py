import logging
import time

from broker.client import BrokerContractClient
from broker.exceptions import BrokerRequestException, BrokerApiException

if __name__ == '__main__':
    from broker import broker_log

    broker_log.setLevel(logging.DEBUG)
    broker_log.addHandler(logging.StreamHandler())

    proxies = {
        "http": '',
        "https": '',
    }

    entry_point = ''  # enter your open api entry point

    b = BrokerContractClient(entry_point, api_key='', secret='', proxies=proxies)

    try:
        print(b.time())

        print(int(time.time() * 1000))

        print(b.broker_info())

        print(b.depth('CBTC-PERP-REV'))

        print(b.trades('CBTC-PERP-REV'))

        print(b.klines('CBTC-PERP-REV'))

        result = b.order_new(symbol='CBTC-PERP-REV', clientOrderId=int(time.time()), side='BUY_OPEN', orderType='LIMIT',
                             quantity='2', price='8000', leverage='10', timeInForce='GTC', triggerPrice=None)

        print(result)

        order_id = result['orderId']

        print(order_id)

        print(b.get_order('LIMIT', order_id=order_id))

        print(b.order_cancel(order_id=order_id))

        print(b.open_orders(order_type='LIMIT'))

        print(b.history_orders())

        print(b.account())

        print(b.my_trades())

        listen_key = b.stream_get_listen_key()

        print(listen_key)

        print(b.stream_keepalive(listen_key.get('listenKey')))

        print(b.stream_close(listen_key.get('listenKey')))

    except BrokerRequestException as bre:
        logging.error(bre)
    except BrokerApiException as bae:
        logging.error(bae)
