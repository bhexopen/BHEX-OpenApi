import time

from bhex.client import BhexClient

if __name__ == '__main__':

    proxies = {
        "http": "",
        "https": "",
    }

    b = BhexClient(api_key='', secret='', entry_point='', proxies=proxies)

    print(b.time())

    print(int(time.time() * 1000))

    print(b.broker_info())

    print(b.depth('BTCUSDT'))

    print(b.trades('BTCUSDT'))

    print(b.klines('BTCUSDT'))

    print(b.ticker_24hr('BTCUSDT'))

    print(b.order_new(symbol='BTCUSDT', side='BUY', type='LIMIT', quantity='0.01', price='1000', timeInForce='GTC'))

    print(b.order_get(order_id='230906618880078080'))

    print(b.order_cancel(order_id='230912728362004736'))

    print(b.open_orders())

    print(b.history_orders())

    print(b.account())

    print(b.my_trades())
