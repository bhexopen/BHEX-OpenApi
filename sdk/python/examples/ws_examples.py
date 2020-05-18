from broker.websockets import BrokerWss


def handler(msg):
    print(msg)


entry_point = 'wss://wsapi.yourbroker.com/openapi/'  # input your broker websocket api url
rest_entry_point = 'https://api.yourbroker.com/openapi/'  # input your broker api uri

client = BrokerWss(entry_point, rest_entry_point, api_key='', secret='')

client.subscribe_to_realtimes(symbol='BTCUSDT,ETHUSDT', callback=handler)

client.subscribe_to_trades(symbol='BTCUSDT', callback=handler)

client.subscribe_to_kline(symbol='BTCUSDT', interval='5m', callback=handler)

client.subscribe_to_depth(symbol='BTCUSDT', callback=handler)

client.subscribe_to_index(symbol='BTCUSDT', callback=handler)

client.user_data_stream(callback=handler)

client.start()
