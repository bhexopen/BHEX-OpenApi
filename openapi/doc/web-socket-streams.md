# Web Socket Streams for BHex (2018-09-25)
# General WSS information
* The base endpoint is: **wss://$HOST**
* Raw streams are accessed at **/openapi/quote/ws/v1**
* Combined stream events are wrapped as follows: 
```json
{
  "symbol": "$exchangeId0.$symbol0,$exchangeId1.$symbol1",
  "topic": "$topic",
  "event": "$event"
}

```

| name | values |
| :--- | :---- | 
| topic | realtimes,trade,kline_$interval,depth|
| event | sub,cancel,cancel_all|
| interval | 1m,3m,5m,15m,30m,1h,2h,6h,8h,12,1d,3d,1w,1M|

# Detailed Stream information
## Trade Streams
The Trade Streams push raw trade information; each trade has a unique buyer and seller.

**Stream Name:** trades

**Payload:**
```json
{
    "symbol": "301.LTCBTC",
    "topic": "trade",
    "data": [{
        "t": 1528631035415,//time
        "p": "9500.0000",//price
        "q": "3800.000", //quantity
        "m": true //true is buy, flase is sell
    }]
}
```

## Kline/Candlestick Streams
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
```json
{
    "symbol": "301.LTCBTC",
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

## Market Tickers Stream
24hr Ticker statistics for a symbol that changed in an array.

**Stream Name:** realtimes

**Payload:**
```json
{
    "symbol": "301.LTCBTC",
    "topic": "realtimes",
    "data": [{
        "t": "1531193421003",//time
        "s": "LTCBTC", // symbol
        "c": "0.1531193171219",//close price
        "h": "0.1531193171219",//high price
        "l": "0.1531193168802",//low price
        "o": "0.1531193171219", //open price
        "v": "0.0", //volume  
        "e": "301" //exchange id
    },{
        "t": "1531193421003",//time
        "s": "LTCBTC", // symbol
        "c": "0.1531193171219",//close price
        "h": "0.1531193171219",//high price
        "l": "0.1531193168802",//low price
        "o": "0.1531193171219", //open price
        "v": "0.0", //volume  
        "e": "301" //exchange id
    }]
}
```

## Partial Book Depth Streams
The Depth Streams for symbols.

**Stream Name:** depth

**Payload:**

```json
{
    "symbol": "301.LTCBTC",
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

