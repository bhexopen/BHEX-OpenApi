# Web Socket Streams for Broker (2018-09-25)

## General WSS information

* The base endpoint is: **wss://$HOST**
* Raw streams are accessed at **/openapi/quote/ws/v1**
* Combined stream events are wrapped as follows:

***Note***:
You must add **HOST** to your request headers

```javascript
{
  "symbol": "$symbol0,$symbol1",
  "topic": "$topic",
  "event": "$event"
}

```

| name     | values                                      |
| :------- | :------------------------------------------ |
| topic    | realtimes,trade,kline_$interval,depth       |
| event    | sub,cancel,cancel_all                       |
| interval | 1m,3m,5m,15m,30m,1h,2h,6h,8h,12,1d,3d,1w,1M |

## Detailed Stream information

### Trade Streams

The Trade Streams push raw trade information; each trade has a unique buyer and seller.

**Stream Name:** trades

**Payload:**

```javascript
{
    "symbol": "LTCBTC",
    "topic": "trade",
    "data": [{
        "t": 1528631035415,//time
        "p": "9500.0000",//price
        "q": "3800.000", //quantity
        "m": true //true is buy, flase is sell
    }]
}
```

### Kline/Candlestick Streams

The Kline/Candlestick Stream push updates to the current klines/candlestick every second.

**Kline/Candlestick chart intervals:**

m -> minutes; h -> hours; d -> days; w -> weeks; M -> months

* 1m
* 3m
* 5m
* 15m
* 30m
* 1h
* 2h
* 4h
* 6h
* 8h
* 12h
* 1d
* 3d
* 1w
* 1M

**Stream Name:** kline

**Payload:**

```javascript
{
    "symbol": "LTCBTC",
    "topic": "kline_1m",
    "data": [{
         "t": "1531193421003",//time
         "s": "BCHBTC", // symbol
         "c": "0.1531193171219",//close price
         "h": "0.1531193171219",//high price
         "l": "0.1531193168802",//low price
         "o": "0.1531193171219", //open price
         "v": "0.0" //volume
    },{
         "t": "1531193421003",//time
         "s": "BCHBTC", // symbol
         "c": "0.1531193171219",//close price
         "h": "0.1531193171219",//high price
         "l": "0.1531193168802",//low price
         "o": "0.1531193171219", //open price
         "v": "0.0" //volume
    }]
}
```

### Market Tickers Stream

24hr Ticker statistics for a symbol that changed in an array.

**Stream Name:** realtimes

**Payload:**

```javascript
{
    "symbol": "LTCBTC",
    "topic": "realtimes",
    "data": [{
        "t": "1531193421003",//time
        "s": "LTCBTC", // symbol
        "c": "0.1531193171219",//close price
        "h": "0.1531193171219",//high price
        "l": "0.1531193168802",//low price
        "o": "0.1531193171219", //open price
        "v": "0.0", //volume
        "e": "301" //ignored
    },{
        "t": "1531193421003",//time
        "s": "LTCBTC", // symbol
        "c": "0.1531193171219",//close price
        "h": "0.1531193171219",//high price
        "l": "0.1531193168802",//low price
        "o": "0.1531193171219", //open price
        "v": "0.0", //volume
        "e": "301" //ignored
    }]
}
```

### Partial Book Depth Streams

The Depth Streams for symbols.

Here is the book dump instructions：
* The book dump frequency：Every 300ms, if book version changed.
* The book dump depth：300 for asks and bids each.
* The book version change event：
  * order enters book
  * order leaves book
  * order quantity or amount changes
  * order is finished

**Stream Name:** depth

**Payload:**

```javascript
{
    "symbol": "LTCBTC",
    "topic": "depth",
    "data": [{
        "t": 1528618006671, //time
        "s": "BCHBTC", //symbol
        "a": [ //asks
            ["0.00181860", "155.92000000"],// price, quantity
            ["0.00182830", "263.53000000"],
            ["0.00183760", "41.76000000"],
            ["0.00184860", "155.92000000"],
            ["0.00185830", "263.53000000"],
            ["0.00186760", "41.76000000"]
        ],
        "b": [ //bids
            ["0.00186860", "155.92000000"],// price, quantity
            ["0.00185830", "263.53000000"],
            ["0.00184760", "41.76000000"],
            ["0.00183860", "155.92000000"],
            ["0.00182830", "263.53000000"],
            ["0.00181760", "41.76000000"]
        ]
    }]
}
```
