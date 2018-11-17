Welcome to Bhex-Python
======================

An official Python implementation of the lastest Open API for Bhex.

Install
-------

```bash
pip install bhex
```

Usage
-----

Init bhex client
```python
b = BhexClient(api_key='', secret='')
```

Request with proxies:

```python
proxies = {
  "http": "http://ip:port",
  "https": "http://ip:port",
}

b = BhexClient(api_key='', secret='', proxies=proxies)
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

