Welcome to Broker-Python
======================

An official Python implementation of the lastest Open API for Broker.

Install
-------

```bash
pip install broker-client
```

Usage
-----

#### REST API

Init broker client:
```python
b = BrokerClient(api_key='', secret='', entry_point='')
```

Init broker options client:
```python
b = BrokerOptionClient(api_key='', secret='', entry_point='')
```

Init broker contract client:
```python
b = BrokerContractClient(api_key='', secret='', entry_point='')
```

Request with proxies:

```python
proxies = {
  "http": "http://ip:port",
  "https": "http://ip:port",
}

b = BrokerClient(api_key='', secret='', proxies=proxies, entry_point='')
```

#### Web Socket

##### Init

Init broker websocket client:
```python
client = BrokerWss(api_key='', secret='', entry_point='', rest_entry_point='')
```

##### Subscribe
Subscribe trades:
```python
client.subscribe_to_trades(symbol='BTCUSDT', callback=handler)
```

Subscribe Kline/Candlestick:
```python
client.subscribe_to_kline(symbol='BTCUSDT', interval='5m', callback=handler)
```

Subscribe market tickers:
```python
client.subscribe_to_realtimes(symbol='BTCUSDT,ETHUSDT', callback=handler)
```

Subscribe book depth tickers:
```python
client.subscribe_to_depth(symbol='BTCUSDT', callback=handler)
```

Subscribe user data:
```python
client.user_data_stream(callback=handler)
```

##### Start
Start websocket thread
```python
client.start()
```

API List
--------
### Public API

#### ping
```python
b.ping()
```

#### time
```python
b.time()
```

#### broker info
```python
b.broker_info()
```

#### depth
```python
b.depth('BTCUSDT')
```

#### trades
```python
b.trades('BTCUSDT')
```

#### klines
```python
b.klines('BTCUSDT')
```

#### ticker 24hr
```python
b.ticker_24hr('BTCUSDT')
```

### Private API

#### new order
```python
 b.order_new(symbol='BTCUSDT', side='BUY', type='LIMIT', quantity='0.01', price='1000', timeInForce='GTC')
``` 

#### get order
```python
b.order_get(order_id='')
```

#### cancel order
```python
b.order_cancel(order_id='')
```

#### open orders
```python
b.open_orders()
```

#### history orders
```python
b.history_orders()
```

#### account
```python
b.account()
```

#### my trades
```python
b.my_trades()
```

#### deposit orders
```python
b.deposit_orders()
```

Examples
--------

* [examples.py](examples/examples.py)
* [option-examples.py](examples/option-examples.py)
* [contract-examples.py](examples/contract-examples.py)
* [ws-examples.py](examples/ws-examples.py)

