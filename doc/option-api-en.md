# Base URL

The base url of Option open API is: https://$HOST/openapi

# Public Options Endpoints

## `brokerInfo`

Current broker trading rules and symbol information.

### **Request Weight:**

0

### **Request Url:**
```bash
get /brokerInfo
```

### **Parameters:**

None

### **Response:**
name|type|example|description
------------ | ------------ | ------------ | ------------
`timezone`|string|`UTC`|Timezone of timestamp
`serverTime`|long|`1554887652929`|Retrieves the current time on server (in ms).

In the `symbols` field, the endpoint will return information on current actively trading cryptos on Broker. You can ignore this section.

In the `options` field:
All actively trading options will be displayed.

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0308CS3900`|Name of the option
`status`|string|`TRADING`|Status of the option
`baseAsset`|string|`BTC0308CS3900`|Underlying asset for the option
`baseAssetPrecision`|float|`0.001`|Precision of the option quantity
`quoteAsset`|string|`BUSDT`|Quote asset for the option
`quoteAssetPrecision`|float|`0.01`|Precision of the option price
`icebergAllowed`|string|`false`|Whether iceberg orders are allowed.

For `filters` in `options` field:

name|type|example|description
------------ | ------------ | ------------ | ------------
`filterType`|string|`PRICE_FILTER`|Type of the filter.
`minPrice`|float|`0.001`|Precision of the option price。
`maxPrice`|float|`100000.00000000`|
`tickSize`|float|`0.001`|Precision of the option price.
`minQty`|float|`0.01`|Minimal trading quantity of the option
`maxQty`|float|`100000.00000000`|
`stepSize`|float|`0.001`|Precision of the option quantity
`minNotional`|float|`1`|Precision of the option order size (quantity * price)

### **Example:**
```js
{
  'timezone': 'UTC',
  'serverTime': '1555048558151',
  'brokerFilters': [],
  'symbols': [{...}],
  'options': [
        {
          'filters': [
            {
              'minPrice': '0.01',
              'maxPrice': '100000.00000000',
              'tickSize': '0.01',
              'filterType': 'PRICE_FILTER'
            },
            {
              'minQty': '0.01',
              'maxQty': '100000.00000000',
              'stepSize': '0.001',
              'filterType': 'LOT_SIZE'
            },
            {
              'minNotional': '1',
              'filterType': 'MIN_NOTIONAL'
            }
          ],
          'exchangeId': '301',
          'symbol': 'BTC0412PS5100',
          'status': 'TRADING',
          'baseAsset': 'BTC0412PS5100',
          'baseAssetPrecision': '0.001',
          'quoteAsset': 'USDT',
          'quotePrecision': '0.01',
          'icebergAllowed': False
          },...
        ]
      }
```

## `getOptions`

Retrieves available trading and expired options. Expired options will be returned if `expired` is set `true`.

### **Request Weight:**

1

### **Request URL:**
```
GET /getOptions
```

### **Parameters：**
name|type|required|default|description
------------ | ------------ | ------------ | ------------ | ----
`expired`|string|`NO`|`false`|Set to `true` to show expired options instead of active ones. This can be useful for retrieving historic data.

### **Response:**

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0412CS5400`|Name of the option. 'underlying - expiration date - option type(CS is call spread and PS is put spread) - strike'
`strike`|float|`5400.0`|The strike price of the option.
`created`|long|`1554710400000`|Unix timestamp when the option was first created (ms).
`expiration`|long|`1555055400000`| Unix timestamp when the option will expire (ms)
`optionType`|integer|`1`|`1`=Call Spread, `0`= Put Spread
`maxPayoff`|float|`500`|The maximum payoff of the option.
`underlying`|string|`BTCBUSDT`|The underlying price index name of the option
`settlement`|string|`weekly`|The settlement interval.



### **Example:**
```js
[
  {'symbol': 'BTC0412PS5100',
  'strike': '5100.0',
  'created': '1554710400000',
  'expiration': '1555055400000',
  'optionType': 0,
  'maxPayOff': '500.0',
  'underlying': 'BTCUSDT',
  'settlement': 'weekly'
},...
      ]
```

## `index`
Retrieves the current index price and EDP. This API endpoint does not take any Parameters.

### **Request Weight:**

0

### **Request URL:**
```
GET /quote/v1/option/index
```

### **Parameters:**
None

### **Response：**
name|type|example|description
------------ | ------------ | ------------ | ------------
`index`|float|`3652.81`|The currency index price.
`edp`|float|`3652.81`|Estimated delivery price (Average index price in the last 10 minutes).

### **Example:**
```js
{
  'BTCUSDT':{
    'index':3795.77,
    'edp': 3652.81
  },
  ...
}
```

## `depth`

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
GET /quote/v1/option/depth
```

### **Parameters:**
parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | -----
`symbol`|string|`YES`||The option name for which to retrieve the order book, use `getOptions` to get option names.
`limit`|integer|`NO`|`100` (max = 100)|The number of entries to return for bids and asks.

### **Response:**
name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1550829103981`|Current timestamp (ms)
`bids`|list|(see below)|List of all bids, best bids first. See below for entry details.
`asks`|list|(see below)|List of all asks, best asks first. See below for entry details.

The fields `bids` and `asks` are lists of order book price level entries, sorted from best to worst.

name|type|example|description
------------ | ------------ | ------------ | ------------
`''`|float|`123.10`|price level
`''`|float|`300`|The total quantity of orders for this price level

### **Example:**

```js
{
  'time': 1555049455783,
  'bids': [
   ['78.82', '0.526'],//[Price, Quantity]
   ['77.24', '1.22'],
   ['76.65', '1.043'],
   ['76.58', '1.34'],
   ['75.67', '1.52'],
   ['75.12', '0.635'],
   ['75.02', '0.72'],
   ['75.01', '0.672'],
   ['73.73', '1.282'],
   ['73.58', '1.116'],
   ['73.45', '0.471'],
   ['73.44', '0.483'],
   ['72.32', '0.383'],
   ['72.26', '1.283'],
   ['72.11', '0.703'],
   ['70.61', '0.454']],
   'asks': [
     ['122.96', '0.381'],//[Price, Quantity]
     ['144.46', '1'],
     ['155.55', '0.065'],
     ['160.16', '0.052'],
     ['200', '0.775'],
     ['249', '0.17'],
     ['250', '1'],
     ['300', '1'],
     ['400', '1'],
     ['499', '1']]
   }

```

## `trades`

Retrieve the latest trades that have occurred for a specific option.

### **Request Weight:**

1

### **Request URL:**
```
GET /quote/v1/option/trades
```
### **Parameters：**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | ------
`symbol`|string|`YES`||The name of the option.
`limit`|integer|`NO` (clamped to max 1000)|`100`|The number of trades returned

### **Response:**

name|type|example|description
------------ | ------------ | ------------ | ------------
`price`|float|`0.055`|The price of the trade
`time`|long|`1537797044116`|Current timestamp (ms)
`qty`|float|`5`|The quantity traded
`isBuyerMaker`|string|`true`|Maker or taker of the trade. `true`= maker, `false` = taker


### **Example:**
```js
[
  {
    'price': '1.21',
    'time': 1555034474064,
    'qty': '0.725',
    'isBuyerMaker': False
  },...
]
```

## `klines`

Retrieves the kline information (open, high, trade volume, etc.) for a specific option.

### **Request Weight:**

1

### **Request URL:**
```
GET /quote/v1/option/klines
```

### **Parameters：**
| Parameter|type|required|default|description |
| ------------ | ------------ | ------------ | ------------ | ---- |
|`symbol`|string|`YES`||Name of the option.|
|`interval`|string|`YES`||Interval of the kline. Possible values include: `1m`,`5m`,`15m`,`30m`,`1h`,`1d`,`1w`,`1M`|
|`limit`|integer|`NO`|`1000`|Number of entries returned. Max is capped at 1000.|
|`to`|integer|`NO`||timestamp of the last datapoint|

### **Response:**
name|type|example|description
------------ | ------------ | ------------ | ------------
`''`|long|`1538728740000`|Open Time
`''`|float|`36.00000'`|Open
`''`|float|`36.00000`|High
`''`|float|`36.00000`|Low
`''`|float|`36.00000`|Close
`''`|float|`148976.11427815`|Trade volume amount
`''`|long|`1538728740000`|Close time
`''`|float|`2434.19055334`|Quote asset volume
`''`|integer|`308`|Number of trades
`''`|float|`1756.87402397`|Taker buy base asset volume
`''`|float|`28.46694368`|Taker buy quote asset volume


### **Example:**
```js
[
  [
    1538728740000, //'opentime'
    '36.000000000000000000', //'open'
    '36.000000000000000000', //'high'
    '36.000000000000000000', //'low':
    '36.000000000000000000', //'close'
    '148976.11427815',  // Volume
    1499644799999,      // Close time
    '2434.19055334',    // Quote asset volume
    308,                // Number of trades
    '1756.87402397',    // Taker buy base asset volume
    '28.46694368'       // Taker buy quote asset volume
  ],...
]
```
`base asset` refers to the asset that is the quantity of a symbol.

`quote asset` refers to the asset that is the price of a symbol.

# Private Options Endpoints

## `order`

Places a buy order for an option. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request URL:**
```bash
POST /openapi/option/order
```

### **Parameters：**
Parameter|type|required|example|description
------------ | ------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`|`BTC0412PS3900`|Name of option
`clientOrderId`|string/long|`NO`|`as78sda9f`|A unique ID of the order. Automatically generated if not sent.
`side`|string|`YES`|`BUY`|Direction of the order. Possible values include `BUY` and `SELL`.
`type`|string|`YES`|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`timeInForce`|string|`NO`|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`price`|float|`NO` Required for limit orders|`3213.32`|Price of the order
`quantity`|float|`YES`|`22.12`|The number of contracts to buy

You can get options' price, quantity configuration data in the `brokerInfo` endpoint.

### **Response:**
Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|Timestamp when the order is created.
`updateTime`|long|`1551062936784`|Last time this order was updated
`orderId`|integer|`891`|ID of the order.
`clientOrderId`|integer|`213443`|A unique ID of the order.
`symbol`|string|`BTC0412CS4200`|Name of the option.
`price`|float|`4765.29`|Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`4754.24`|Average price of filled orders.
`type`|string|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`side`|string|`BUY`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`NEW`|The state of the order.Possible values include `NEW`, `PARTIALLY_FILLED`, `FILLED`, `CANCELED`, and `REJECTED`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`fees`|||Fees incurred for this order.

In the `fees` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|Fee token kind.
`fee`|float|`0`|Actual transaction fees occurred.


### **Example:**
```js
{
    'time':1541161088303,
    'updateTime': 1541161088303,
    'orderId': 28,
    'clientOrderId': 213443,
    'symbol': 'BTC0412CS4200',
    'price': 102.32,
    'origQty': 21.3,
    'executedQty': 10.2,
    'avgPrice': 3121.13
    'type': 'LIMIT',
    'side': 'SELL',
    'status': 'NEW',
    'timeInForce': 'GTC',
    'fees':[]
}
```

## `cancel`

Cancels an order, specified by `orderId` or `clientOrderId`. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
DELETE /openapi/option/order/cancel
```

### **Parameter:**

Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | -----
`orderId`|integer|`NO`|`12817334`|The order ID of the order to be canceled
`clientOrderId`|string/long|`NO`||Unique ID of the order.

One **MUST** be provided of these two parameters.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1541161088303`|Timestamp when order request is submitted (ms).
`updateTime`|long|`1551062936784`|Last time this order was updated (ms)
`orderId`|integer|`713637304`|ID of the order
`clientOrderId`|string|`213443`|Unique ID of the order.
`symbol`|string|`BTC0412CS4200`|name of the option
`price`|float||Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`4754.24`|Average price of filled orders.
`type`|string|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`side`|string|`BUY`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`NEW`|The state of the order.Possible values include `NEW`, `PARTIALLY_FILLED`, `FILLED`, `CANCELED`, and `REJECTED`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`fees`|||Fees incurred for this order.

In the `fees` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|Fee token kind.
`fee`|float|`0`|Actual transaction fees occurred.

###  **Example:**

```js
{
  'time':1541161088303,
  'updateTime': 1541161088303,
  'orderId': 713637304,
  'clientOrderId': 213443,
  'symbol': 'BTC0412CS4200',
  'price': 102.32,
  'origQty': 21.3,
  'executedQty': 10.2,
  'avgPrice': 3121.13
  'type': 'LIMIT',
  'side': 'SELL',
  'status': 'CANCELED', //status will always be `CANCELED` for cancel request
  'timeInForce': 'GTC',
  'fees': []
}
```

### `openOrders`

Retrieves open orders. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/openOrders
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | --------
`symbol`|string|`NO`||Symbol to return open orders for. If not sent, orders of all options will be returned.
`orderId`|integer|`NO`|| Order ID
`side`|string|`NO`||Direction of the order, possible values include `BUY` and `SELL`.
`type`|string|`NO`||Order types to return, possible values include `LIMIT` and `MARKET`.
`limit`|integer|`NO`|`20`|Number of entries to return.

If `orderId` is set, it will get orders < that `orderId`. Otherwise most recent orders are returned.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1541161088303`|Timestamp when order request is submitted (ms).
`updateTime`|long|`1551062936784`|Last time this order was updated (ms)
`orderId`|integer|`713637304`|ID of the order
`clientOrderId`|string|`213443`|Unique ID of the order.
`symbol`|string|`BTC0412CS4200`|name of the option
`price`|float|`12.34`|Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`4754.24`|Average price of filled orders.
`type`|string|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`side`|string|`BUY`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`NEW`|The state of the order.Possible values include `NEW`, `PARTIALLY_FILLED`, `FILLED`, `CANCELED`, and `REJECTED`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`fees`|||Fees incurred for this order.

In the `fees` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|Fee token kind.
`fee`|float|`0`|Actual transaction fees occurred.

### **Example:**

```js
[
  {
    'time': '1554948456641',
    'updateTime': '0',
    'orderId': '337326535438529024',
    'clientOrderId': '19524737',
    'symbol': 'BTC0412CS4200',
    'price': '1.98',
    'origQty': '1',
    'executedQty': '0',
    'avgPrice': '0',
    'type': 'LIMIT',
    'side': 'BUY',
    'status': 'NEW',
    'timeInForce': 'GTC',
    'fees': []
  },...

]
```

## `positions`

Retrieves current positions. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/positions
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | --------
`symbol`|string|`NO`||Name of the option. If not sent, positions for all options will be returned.

### **Response:**
For each unique `symbol`, this endpoint will return the following information.

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0405PS3850`|Name of the option.
`position`|float|`-10.760`|The position of the option. Can be negative (short) or positive (long)
`margin`|float|`5380`|Total margin amount for position held.
`settlementTime`|integer|`1555056000000`|Settlement timestamp of the option.
`strikePrice`|float|`4200`|Strike price of the option.
`price`|float|`500.00`|Current option price
`availablePosition`|float|`10.76`|Number of option contracts that can be closed.
`averagePrice`|float|`9693.502194671`|Average price for the position
`changed`|float|`-4018.21`|Profit or loss for holding the position.
`changedRate`|float|`1.02`|**Long Position:** `changed`/`averagePrice` **Short Position:** (`changed` \* `position`) / (`margin` - `averagePrice` \* `position`)
`index`|float|`5012.28666667`|Current index price of the underlying asset.

### **Example:**

```js
[
  {
    'symbol': 'BTC0412CS4200',
    'position': '-10.760',
    'margin': '5380',
    'settlementTime': '1555056000000',
    'strikePrice': '4200',
    'price': '500.00',
    'availablePosition': '10.76',
    'averagePrice': '126.56',
    'changedRate': '-100.00',
    'changed': '-4018.21',
    'index': '5012.28666667'
  },...
]
```

## `historyOrders`

Retrieves history of orders that have been partially or fully filled or canceled. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/historyOrders
```

### **Parameters:**
Parameter|type|required|efault|description
------------ | ------------ | ------------ | ------------ | --------
`symbol`|string|`NO`||Name of the option. If not sent, orders of all options will be returned.
`side`|string|`buy`|Direction of the order. Possible values include `BUY` and `SELL`.
`type`|string||Order Type. Possible values include `LIMIT` and `MARKET`.
`orderStatus`|string||Status of the order. Possible values include `PARTIALLY_FILLED`, `FILLED`, and `CANCELED`.
`limit`|integer|`20`|Number of items to be returned

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1541161088303`|Timestamp when order request is submitted (ms).
`updateTime`|long|`1551062936784`|Last time this order was updated
`orderId`|integer|`713637304`|ID of the order
`clientOrderId`|string|`213443`|Unique ID of the order.
`symbol`|string|`BTC0412CS4200`|Name of the option
`price`|float||Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`44.24`|Average price of filled orders.
`type`|string|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`side`|string|`BUY`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`NEW`|The state of the order.Possible values include `NEW`, `PARTIALLY_FILLED`, `FILLED`, `CANCELED`, and `REJECTED`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`fees`|||Fees incurred for this order.

In the `fees` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|Fee token kind.
`fee`|float|`0`|Actual transaction fees occurred.

### **Example:**
```js
{
  [
    {
      'time':1541161088303,
      'updateTime': 1541161088303,
      'orderId': 28,
      'clientOrderId': 213443,
      'symbol': 'BTC0412CS4200',
      'price': 102.32,
      'origQty': 21.3,
      'executedQty': 10.2,
      'avgPrice': 3121.13
      'type': 'LIMIT',
      'side': 'SELL',
      'status': 'NEW',
      'timeInForce': 'GTC',
      'fees':[]
    },...
  ]
}
```

## `myTrades`
Retrieve the trade history of the account. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/myTrades
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | -------
`symbol`|string|`NO`|| Name of the option. If not sent, trades for all symbols will be returned.
`limit`|integer|`NO`|`20`|The number of trades returned (clamped to max 1000)
`side`|string|`NO`||Direction of the order.
`fromId`|integer|`NO`||TradeId to fetch from.
`toId`|integer|`NO`||TradeId to fetch to.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1503439494351`|The timestamp of the trade（ms）
`tradeId`|long|`49366`|The ID for the trade
`orderId`|long|`630491422`|	ID of the order
`price`|float|`0.055`|The price of the trade.
`quantity`|float|`23.3`|Quantity of the trade.
`feeTokenName`|string|`USDT`|Fee token name
`fee`|float|`0.000090000000000000`|Fee of the trade.
`side`|string|`BUY`|Trade side from the user's point of view. Possible values include `BUY` and `SELL`
`type`|string|`LIMIT`|The order type, possible types: `LIMIT`, `MARKET`
`symbol`|string|`BTC0412PS3900`|	The name of the option

### **Example:**
```js
[
  {
    'time': '1554897921663',
    'tradeId': '336902617393292032',
    'orderId': '336902617267462912',
    'price': '99',
    'quantity': '11.414',
    'feeTokenName': 'BUSDT',
    'fee': '0.1129986',
    'type': 'LIMIT',
    'side': 'BUY',
    'symbol': 'BTC0412PS3900'
  },...
]
```

## `settlements`

Retrieves settlement events that have affected your account. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET  /openapi/option/settlements
```
### **Parameters:**

None

### **Responses:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0412PS3900`|Name of the option.
`optionType`|string|`call`|Type of the option. Possible values include: 'call' and 'put'
`margin`|float|`400`|Margin for the position
`timestamp`|integer|`1517299201923`|The timestamp of the settlement.
`strikePrice`|float|`3740`|Strike price of the option
`settlementPrice`|float|`11008.37`|Settlement price (EDP, which is the average index price in the last 10 minutes) at time of settlement.
`maxPayoff`|float|`400`|Maximum payoff of the option
`averagePrice`|float|`9693.502194671`|Average price for the position
`position`|string|`1000`|Position quantity
`changed`|float|`20.3`|Settlement payoff of the option
`changedRate`|float|`2.34`|**Long Position**: `changed`/(averagePrice \* position). **Short Position**: `changed`/(`margin` - averagePrice \* position)

### **Examples:**
```js
[
  {'symbol': 'BTC0405PS3850',
  'optionType': 'put',
  'margin': '0',
  'timestamp': '1554451200000',
  'strikePrice': '3850',
  'settlementPrice': '4956.54',
  'maxPayOff': '500',
  'averagePrice': '119.27',
  'position': '0',
  'changed': '0',
  'changedRate': '0'},...
  ]
```

## `account`

This endpoint is used to retrieve options account balance. This endpoint requires
you to be signed.

### **Request Weight:**
1

### **Request Url:**
```bash
GET  /openapi/option/account
```

### **Parameters:**
None

### **Response:**
Name|type|example|description
------------ | ------------ | ------------ | ------------
`totalAsset`|float|`1000.0`|Total asset value in option account quoted in USDT.
`optionAsset`|float|`100.0`|Total option value quoted in USDT
`balances`|float||Show balance details.

In the `balances` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`tokenName`|string|`USDT`|Name of the asset
`free`|float|`600.0`|Amount available for use
`locked`|float|`100.0`|Amount locked (for open orders)
`margin`|float|`100.0`|Amount used for margin (for short positions)

### **Examples:**
```js
{
  'totalAsset': '8533.0606762',
  'optionAsset': '558.1832',
  'balances': [
    {
      'tokenName': 'USDT',
      'free': '0.0',
      'locked': '0.0',
      'margin': '0.0'
    },
    {
      'tokenName': 'BUSDT',
      'free': '7961.9951881',
      'locked': '12.8822881',
      'margin': '5798.0'
    },...
  ]
}
```
