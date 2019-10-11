# Web Socket Streams for bBoker (2019-08-12)

## General WSS information

* The base endpoint is [here](endpoint.md)
* Raw streams are accessed at **/openapi/quote/ws/v1**

| name | values |
| :--- | :---- |
| topic | realtimes, trade, kline_$interval, depth|
| event | sub, cancel, cancel_all|
| interval | 1m, 5m, 15m, 30m, 1h, 2h, 6h, 12h, 1d, 1w, 1M|

**Sample Subscription Data:**

```javascript
{
  "symbol" : "$symbol0, $symbol1",
  "topic" : "$topic",
  "event" : "sub",
  // customizable parameter
  "params" : {
      // kline max limit is 2000, default is 1
      "limit" : "$limit",
      // Whether data is returned in binary format, default to false
      "binary" : "false"
  }
}
```

| name | Explanation |
| :--- | :---- |
|limit|Specify number of entries returned|
|binary|Whether returned values is in binary format. **DEFAULT** value is **false**|

## Heartbeat

The  client need to send a `PING` message to the server regularly through the Websocket, which then the server replies with `PONG`. If the client does not send the message every 5 minutes, the server will close the connection.

* Request
```javascript
{
    "ping": 1535975085052
}
```

* Response
```javascript
{
    "pong": 1535975085052
}
```

### Trade Streams

The Trade Streams push raw trade information; each trade has a unique buyer and seller.

After successful handshake and connected to server, the server will return the latest 60 trades. After this payload, the following will be real-time trades.

Variable "v" acts as an tradeId. This variable is shared across different symbols; however, each ID is unique. For example, suppose in the last 5 seconds 3 trades happened in ETHSUDT, BTCUSDT, and BHTBTC. Their version (which is "v") will be consecutive: 112, 113, 114.   

**Subscription message structure:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "trade",
  "event": "sub",
  "params": {
    "binary": false // Whether data returned is in binary format
  }
}
```

**Payload:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "trade",
  "data": [{
    "v": "426635153180475392", // Version
    "t": 1565594873508,  //Timestamp
    "p": "11369", // Price
    "q": "0.01", // Quantity
    "m": false // true = buy, false = sell
  }, {
    "v": "426635153373413376",
    "t": 1565594873531,
    "p": "11369",
    "q": "0.0012",
    "m": false
  }],
  "f": false // Whether this is the first entry
}
```

### Market Tickers Stream

24hr Ticker statistics for a symbol that changed in an array.

**Subscription message structure:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "realtimes",
  "event": "sub",
  "params": {
    "binary": false
  }
}
```

**Payload:**

```javascript
{
  "symbol": "ETHUSDT",
  "topic": "realtimes",
  "data": [{
    "t": "1565592599015", //time
    "s": "ETHUSDT", //symbol
    "c": "212.63", //close
    "h": "216.96", //high
    "l": "206.78", //low
    "o": "210.23", //open
    "v": "73013.575", //volume
    "qv": "15726612.498168", //trade amount (in base asset, in this case is USDT)
    }],
  "f": false // Whether this is the first entry
}
```


### Kline/Candlestick Streams

The Kline/Candlestick Stream push updates to the current klines/candlestick every second.

**Kline/Candlestick chart intervals:**

m -> minutes; h -> hours; d -> days; w -> weeks; M -> months

* 1m
* 5m
* 15m
* 30m
* 1h
* 2h
* 4h
* 6h
* 12h
* 1d
* 1w
* 1M

**Subscription message structure:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "kline_"+$interval,
  "event": "sub",
  "params": {
    "binary": false
  }
}
```

**Payload:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "kline",
  "params": {"klineType": "15m"},
  "data": [{
    "t": 1565595900000, //kline start time
    "s": "BTCUSDT", // symbol
    "c": "11436.14", //close
    "h": "11437", //high
    "l": "11381.89", //low
    "o": "11381.89", //open
    "v": "16.3306" //volume
  }],
  "f": true// Whether this is the first entry
}
```

### Orderbook Depth Stream

The Depth Streams for symbols.

Here is the book dump instructions：
* The book dump frequency：Every 300ms, if book version changed.
* The book dump depth：300 for asks and bids each.
* The book version change event：
  * order enters book
  * order leaves book
  * order quantity or amount changes
  * order is finished

#### Merged Depth Stream

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "depth",
  "event": "sub",
  "params": {
    "binary": false
    }
}
```

**Payload:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "depth",
  "data": [{
    "s": "BTCUSDT", //Symbol
    "t": 1565600357643, //Timestamp
    "v": "112801745_18", //Version
    "b": [ //Bids
      ["11371.49", "0.0014"], //[Price, Quantity]
      ["11371.12", "0.2"],
      ["11369.97", "0.3523"],
      ["11369.96", "0.5"],
      ["11369.95", "0.0934"],
      ["11369.94", "1.6809"],
      ["11369.6", "0.0047"],
      ["11369.17", "0.3"],
      ["11369.16", "0.2"],
      ["11369.04", "1.3203"],
    "a": [//Asks
      ["11375.41", "0.0053"], //[Price, Quantity]
      ["11375.42", "0.0043"],
      ["11375.48", "0.0052"],
      ["11375.58", "0.0541"],
      ["11375.7", "0.0386"],
      ["11375.71", "2"],
      ["11377", "2.0691"],
      ["11377.01", "0.0167"],
      ["11377.12", "1.5"],
      ["11377.61", "0.3"]
    ]
  }],
  "f": true// Whether this is the first entry
}
```

#### Diff. Depth Stream

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "diffDepth",
  "event": "sub",
  "params": {
    "binary": false
  }
}
```

Order book price and quantity depth updates used to locally manage an order book pushed every second.

In the Diff. (difference) depth stream, the quantity doesn"t necessarily mean the corresponding quantity to the price anymore. If the quantity is 0, it means this previous price level is not in the orderbook anymore. If the quantity is > 0, it means the updated quantity for this price level.

Suppose now we have received the first depth data payload:

```javascript
["0.00181860", "155.92000000"]// price, quantity
```

If the next payload is:
```javascript
["0.00181860", "12.3"]
```
This means that this price level"s quantity has changed.

If the next payload is:
```javascript
["0.00181860", "0"]
```
This means that this price level is not in orderbook anymore.

**Payload:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "diffDepth",
  "data": [{
    "e": 0,
    "t": 1565687625534,
    "v": "115277986_18",
    "b": [
      ["11316.78", "0.078"],
      ["11313.16", "0.0052"],
      ["11312.12", "0"],
      ["11309.75", "0.0067"],
      ["11309.58", "0"],
      ["11306.14", "0.0073"]
    ],
    "a": [
      ["11318.96", "0.0041"],
      ["11318.99", "0.0017"],
      ["11319.12", "0.0017"],
      ["11319.22", "0.4516"],
      ["11319.23", "0.0934"],
      ["11319.24", "3.0665"]
    ]
  }],
  "f": false //Whether this is the first entry
}
```

### Index Data Stream

This stream is for index prices gathered for options and futures.

**Subscription message structure:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "index",
  "event": "sub",
}
```

**Payload:**

```javascript
{
  "symbol": "HTUSDT",  
  "topic": "index",
  "data": [{
    "symbol": "HTUSDT",
    "index": "5.0941",
    "edp": "5.08799333",
    "formula": "(5.0941[HUOBI])/1"
  }],
  "f": true// Whether this is the first entry
}
```

## Error handling

**Error Codes:**

```java
INVALID_REQUEST("-10000", "Invalid request!")
JSON_FORMAT_ERROR("-10001", "Invalid JSON!")
INVALID_EVENT("-10002", "Invalid event")
REQUIRED_EVENT("-10003", "Event required!")
INVALID_TOPIC("-10004", "Invalid topic!")
REQUIRED_TOPIC("-10005", "Topic required!")
PARAM_EMPTY("-10007", "Params required!")
PERIOD_EMPTY("-10008", "Period required!")
PERIOD_ERROR("-10009", "Invalid period!")
SYMBOLS_ERROR("-100010", "Invalid Symbols!")
```
