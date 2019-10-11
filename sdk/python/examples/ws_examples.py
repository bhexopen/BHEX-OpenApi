from broker.websockets import BrokerWss


def handler(msg):
    print(msg)


client = BrokerWss(api_key='',
                   secret='',
                   entry_point='',
                   rest_entry_point='')

client.subscribe_to_realtimes(symbol='BTCUSDT,ETHUSDT', callback=handler)

client.subscribe_to_trades(symbol='BTCUSDT', callback=handler)

client.subscribe_to_kline(symbol='BTCUSDT', interval='5m', callback=handler)

client.subscribe_to_depth(symbol='BTCUSDT', callback=handler)

client.user_data_stream(callback=handler)

client.start()
