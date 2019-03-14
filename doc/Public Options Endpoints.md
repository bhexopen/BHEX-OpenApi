# Public Options Endpoints

## `brokerInfo`

Current broker trading rules and symbol information.

### **Request Weight:**

0

### **Request Url:**
```bash
get /api/v1/option/brokerInfo
```

### **Parameters:**

None

### **Response:**
In the `rateLimits` field:

name|type|example|description
------------ | ------------ | ------------ | ------------
`rateLimitType`|string|`REQUEST_WEIGHT`|Rate limit type.
`interval`|string|`SECOND`|Interval set for the rate limit type.
`limit`|string|`10`|Limit set for the rate limit type.

In the `symbols` field:
Note that all actively trading instruments will be displayed (including both cryptos and options).

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC-21JAN19-3750-CS`|Name of the option
`status`|string|`TRADING`|Status of the option
`underlyingAsset`|string|`BTC`|Underlying asset for the option
`quoteAsset`|string|`USDT`|Quote asset for the option

For `filters` in `symbols` field, the specifications of price, quantity, and minimal notional amount:

name|type|example|description
------------ | ------------ | ------------ | ------------
`filterType`|string|`PRICE_FILTER`|Type of the filter.
`minPrice`,`minQty`,`minNotional`|float|`0.001`|Minimal price/quantity/notional allowed.
`maxPrice`,`maxQty`|float|`0.001`|Maximum price/quantity allowed.
`tickSize`,`stepSize`|float|`0.001`|Size of increment change.

### **Example:**
```js
{
  "timezone": "UTC",
  "serverTime": 1538323200000,
  "rateLimits": [{
      "rateLimitType": "REQUESTS_WEIGHT",
      "interval": "MINUTE",
      "limit": 1200
    },
    {
      "rateLimitType": "ORDERS",
      "interval": "SECOND",
      "limit": 10
    },
    {
      "rateLimitType": "ORDERS",
      "interval": "DAY",
      "limit": 100000
    }
  ],
  "symbols": [{
    "symbol": "BTC-21JAN19-3750-CS",
    "status": "TRADING",
    "underlyingAsset": "BTC",
    "quoteAsset": "USDT",
    "filters": [{
      "filterType": "PRICE_FILTER",
      "minPrice": "0.00000100",
      "maxPrice": "100000.00000000",
      "tickSize": "0.000100"
    }, {
      "filterType": "LOT_SIZE",
      "minQty": "0.00100000",
      "maxQty": "100000.00000000",
      "stepSize": "0.00100000"
    }, {
      "filterType": "MIN_NOTIONAL",
      "minNotional": "0.00100000"
    }]
  }]
}
```

## `getoptions`

Retrieves available trading and expired options. This API endpoint can be used to see which options are currently actively trading and which options have expired (if `expired` is set `true`).

### **Request Weight:**

1

### **Request URL:**
```
GET /api/v1/option/getoptions
```

### **Parameters：**
name|type|required|default|description
------------ | ------------ | ------------ | ------------
`expired`|boolean|`YES`|`false`|Set to true to show expired options instead of active ones. This can be useful for retrieving historic data.

### **Response:**

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`"BTC-21JAN19-3750-CS"`|Name of the option."underlying-expiration date-strike-option type"
`settlement`|string|`weekly`|The settlement interval. Possible values include 'monthly' and 'weekly'
`created`|long|`1547100893000`|Unix timestamp when the option was first created.
`expiration`|string|`1551260848000`| Unix timestamp when the option will expire  
`strike`|float|`3750`|The strike price of the option.
`maxPayoff`|float|`500`|The maximum payoff of the option.
`volume`|float|`4750.032963800925`|Volume of the option
`optionType`|string|`callSpread`|Type of the option.

### **Example:**
```js
{
  "data":[
    {
      "symbol":"BTC-28SEP18-3750-CS",
      "settlement":"weekly",
      "created":"1547100893000",
      "expiration":"1551260848000",
      "strike": 3750,
      "maxPayoff": 500,
      "volume":4750.032963800925,
      "optionType":"callSpread"
    }
  ]    
}
```

## `index`
Retrieves the current index price for options. This API endpoint does not take any Parameters

### **Request Weight:**

0

### **Request URL:**
```
GET /api/quote/v1/options/index
```

### **Parameters:**
None

### **Response：**
name|type|example|description
------------ | ------------ | ------------ | ------------
`Underlying`|float|`3652.81`|The currency index price for certain trading pairs.
`EDP`|float|`3652.81`|Estimated delivery price for those trading pairs (Average index price in the last 30 minutes).

### **Example:**
```js
{
  "Underlying":[
    "BTCUSDT": 3795.77
    ],
  "EDP": [
    "BTCUSDT": 3797.76
    ]
}
```

## `getOrderBook`

Retrieves the options order book.


### **Request Weight:**

Adjusted based on the limit:

Limit|Weight
------------ | ------------
5, 10, 20, 50, 100|1
500|5
1000|10


### **Request Url:**
```
GET /api/quote/v1/option/getOrderBook
```

### **Parameters:**
parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`||The option name for which to retrieve the order book, use `getoptions` to get option names.
`limit`|integer|`NO`|`100` (max=`100`)|The number of entries to return for bids and asks.

### **Response:**
Response in the `result` field.

name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1550829103981`|Current timestamp
`bids`|list|(see below)|List of all bids, best bids first. See below for entry details.
`asks`|list|(see below)|List of all asks, best asks first. See below for entry details.

The fields `bids` and `asks` are lists of order book price level entries, sorted from best to worst.

name|type|example|description
------------ | ------------ | ------------ | ------------
`""`|float|`123.10`|price level
`""`|float|`300`|The total quantity of orders for this price level

### **Example:**

```js
{
  "symbol": "BTC-21JAN19-3750-CS",
  "time": 1538728831579,
  "bids": [
    [
      123.10, //price
      300, //quantity
    ],...
  ],
  "asks": [
    [
      128.92, //price
      410, //quantity
    ],...
  ],
}
```

## `getLastTrades`

Retrieve the latest trades that have occurred for a specific option.

### **Request Weight:**

1

### **Request URL:**
```
GET /api/quote/v1/option/getLastTrades
```
### **Parameters：**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`||The name of the option.
`limit`|integer|`NO` (clamped to max 1000)|`100`|The number of trades returned

### **Response:**

name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1537797044116`|Current timestamp
`price`|string|`0.055`|The price of the trade
`qty`|number|`5`|The quantity traded
`isBuyerMaker`|boolean|`true`|Buyer or taker. Possible values include `true` and `false`.


### **Example:**
```js
[
  {
    "time": 1537797044116,
    "price": 0.055,
    "qty": 5,
    "isBuyerMaker":true
  },...
]
```

## `klines`

Retrieves the kline information open, high, 24h volume, etc. for a specific option.

### **Request Weight:**

1

### **Request URL:**
```
GET /api/quote/v1/option/quote/klines
```

### **Parameters：**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`||Name of the option.
`interval`|string|`YES`||Interval of the kline. Possible values include: `1m`,`5m`,`15m`,`30m`,`1h`,`1d`,`1w`,`1M`
`limit`|integer|`NO`|`1000`|Number of entries returned. Max is capped at 1000.
`to`|integer|`NO`||timestamp of the last datapoint

### **Response:**
name|type|example|description
------------ | ------------ | ------------ | ------------
`“”`|long|`1538728740000`|open time
`”“`|float|`""`|Open
`”“`|float|`""`|High
`”“`|float|`""`|Low
`”“`|float|`""`|Close
`”“`|float|`""`|Trade Volume
`”“`|long|`1538728740000`|Close time
`”“`|float|`2434.19055334`|Quote asset volume
`”“`|integer|`308`|Quote asset volume
`”“`|float|`1756.87402397`|Taker buy base asset volume
`”“`|float|`28.46694368`|Taker buy quote asset volume


### **Example:**
```js
[
  [
    1538728740000, //"opentime"
    "36.000000000000000000", //"open"
    "36.000000000000000000", //"high"
    "36.000000000000000000", //"low":
    "36.000000000000000000", //"close"
    "148976.11427815",  // Volume
    1499644799999,      // Close time
    "2434.19055334",    // Quote asset volume
    308,                // Number of trades
    "1756.87402397",    // Taker buy base asset volume
    "28.46694368"       // Taker buy quote asset volume
  ],...
]
```
