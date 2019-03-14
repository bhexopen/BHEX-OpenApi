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
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`|`BTC-21JAN19-3750-CS`|Name of option
`clientOrderId`|string|`NO`|`as78sda9f`|A unique ID of the order.Automatically generated if not sent.
`side`|string|`YES`|`buy`|Direction of the order. Possible values include `buy` and `sell`.
`type`|string|`YES`|`limit`|The order type, possible types: `limit`, `market`
`timeInForce`|string|`NO`|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`price`|float|`NO` Required for limit orders|`3213.32`|Price of the order
`quantity`|float|`YES`|`22.12`|The number of contracts to buy

### **Response:**
Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|Timestamp when the order is created.
`orderId`|integer|`891`|ID of the order.
`clientOrderId`|integer|`213443`|A unique ID of the order.
`symbol`|string|`BTC-21JAN19-3750-CS`|Name of the option.
`price`|float|`4765.29`|Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`4754.24`|Average price of filled orders.
`type`|string|`limit`|The order type, possible types: `limit`, `market`
`side`|string|`buy`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`new`|The state of the order.Possible values include `new`, `partially_filled`, `filled`, `canceled`, and `rejected`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.


### **Example:**
```js
{
    "time":1541161088303,
    "orderId": 28,
    "clientOrderId": 213443,
    "symbol": "BTC-21JAN19-3750-CS",
    "price": 102.32,
    "origQty": 21.3,
    "executedQty": 10.2,
    "avgPrice": 3121.13
    "type": "LIMIT",
    "side": "SELL",
    "status": "NEW",
    "timeInForce": "GTC"
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
------------ | ------------ | ------------ | ------------
`orderId`|integer|`NO`|`12817334`|The order ID of the order to be canceled
`clientOrderId`|string|`NO`||Unique ID of the order.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1541161088303`|timestamp when order request is submitted.
`orderId`|integer|`713637304`|ID of the order
`clientOrderId`|string|`213443`|Unique ID of the order.
`symbol`|string|`BTC-21JAN19-3750-CS`|name of the option
`price`|float||Price of the order.
`origQty`|float|`1.01`|Quantity ordered
`executedQty`|float|`1.01`|Quantity of orders that has been executed
`avgPrice`|float|`4754.24`|Average price of filled orders.
`type`|string|`limit`|The order type, possible types: `limit`, `market`
`side`|string|`buy`|Direction of the order. Possible values include `BUY` or `SELL`
`status`|string|`new`|The state of the order.Possible values include `new`, `partially_filled`, `filled`, `canceled`, and `rejected`.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.

###  **Example:**

```js
{
  "time":1541161088303,
  "orderId": 713637304,
  "clientOrderId": 213443,
  "symbol": "BTC-21JAN19-3750-CS",
  "price": 102.32,
  "origQty": 21.3,
  "executedQty": 10.2,
  "avgPrice": 3121.13
  "type": "LIMIT",
  "side": "SELL",
  "status": "NEW",
  "timeInForce": "GTC"
}
```

### `getOpenOrders`

Retrieves open orders. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/getOpenOrders
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`NO`||Symbol to return open orders for
`orderId`|integer|`NO`|| order ID
`side`|string|`NO`||Direction of the order(`buy` or `sell`).
`type`|string|`NO`||Order types to return. Valid values include `limit`, `market`,`any`
`limit`|integer|`NO`|`20`|Number of entries to return.

If `orderId` is set, it will get orders < that `orderId`. Otherwise most recent orders are returned.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|long|`1541161088303`|The timestamp when the order is placed.
`updateTime`|long|`1541161088303`|Current timestamp
`orderId`|integer|`221669707124616960`|Order ID
`clientOrderId`|integer|`ads87yasd`|A unique ID for the order.
`symbol`|string|`BTC-21JAN19-3750-CS`|Name of the option.
`side`|string|`buy`|Direction of the order. Possible values include `buy` or `sell`
`type`|string|`limit`|The order type, possible types: `limit`, `market`
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`price`|float|`4765.29`|Price of the order
`origQty`|	float|`1000`|	The number of contracts to be traded
`executedQty`|	float|	`700`|	The number of contracts already filled
`avgPrice`|float|`4765.29`|Average fill price of the order.
`status`|string|`new`|The state of the order.Possible values include `NEW`, `PARTIALLY_FILLED`, `FILLED`, `CANCELED`, and `REJECTED`.

### **Example:**

```js
[
  {
    "time": 1541161088303,
    "orderId": 713637304,
    "clientOrderId": "2173313",
    "symbol": "BTC-21JAN19-3750-CS",
    "price": 102.32,
    "origQty": 10,
    "executedQty": 10,
    "avgPrice": 8913.21,
    "type": "limit",
    "side": "sell",
    "status": "NEW",
    "timeInForce": "GTC"
  },...

]
```

## `positions`

Retrieves current positions. This API endpoint requires your request to be signed. This API endpoint does not take any arguments

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/positions
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`|`all`|names of the options.`all` to return all options position.

### **Response:**
For each unique `symbol`, this endpoint will return the following information.

name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC-30MAR19-5470-CS`|name of the option.
`strikePrice`|float|`2221`|Strike price of the option.
`settlementTime`|integer|`1541161089303`|Settlement timestamp of the option.
`maxPayoff`|float|`400`|Maximum payoff of the option.(only for spread options)
`optionType`|string|`call`|Type of the option.
`position`|float|`12.00`|The position size in contracts. Can be negative (short) or positive (long)
`price`|float|`71477.5214`|position price
`availablePosition`|float|`11.00`|Number of option contracts that can be cleared.
`costPrice`|float|`1200`|Entry cost of the position.
`averagePrice`|float|`9693.502194671`|average price for the position
`index`|float|`8354.53`|Current index price
`margin`|float|`0`|margin
`changed`|float|`1.2103`|Profit or loss for holding the position.
`changedRate`|string|`1.02%`|Change percentage of the position

### **Example:**

```js
[
  {
    "symbol": "BTC-30MAR18-3970-CS",
    "position":12.00,
    "margin": 400,
    "settlementTime":1541161089303,
    "strikePrice": 3970,
    "price":712,
    "costPrice":1200,
    "availablePosition":11.00,
    "averagePrice":9693.502194671,
    "changed":1.2103,
    "changedRate":"1.02%",
    "index":8354.53
  }
],...
```

## `orderHistory`

Retrieves history of orders that have been partially or fully filled or canceled. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/historyOrders
```

### **Parameters:**
Parameter|type|required|efault|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`NO`||Name of the option
`side`|string||Direction of the order
`type`|string||Order Type
`orderStatus`|string||Status of the order. Possible values include `PARTIALLY_FILLED`, `FILLED`, and `CANCELED`.x
`limit`|integer|`20`|number of items to be returned

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|integer|`1530759674528`|The timestamp when the order is placed.
`updateTime`|long|`1541161088303`|Current timestamp
`orderId`|integer|`221669707124616960`|Order ID
`clientOrderId`|integer|`21314`|A unique ID for the order.
`symbol`|string|`BTC-21JAN19-3750-CS`|Name of the option
`price`|float|`4765.29`|Price of the order
`origQty`|	float|`1000`|	The number of contracts to be traded
`executedQty`|	float|`700`|	The number of contracts already filled
`avgPrice`|float|`4765.29`|Average fill price of the order.
`type`|string|`limit`|The order type, possible types: `limit`, `market`
`side`|string|`buy`|Direction of the order. Possible values include `buy` or `sell`
`status`|string|`filled`|The state of the order.
`timeInForce`|string|`GTC`|Time in force. Possible values include `GTC`,`FOK`,`IOC`.
`fees`|float|`0`|Commission paid so far (in USDT)

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
      "time": 1528631913389,
      "updateTime": 1541161088303,
      "orderId": 630491422,
      "symbol": "BTC-28SEP18-7340-CS",
      "side":"buy",
      "type":"limit",
      "timeInForce":"GTC",
      "price": 7093,
      "origQty": 30,
      "executedQty":20,
      "avgPrice":7023.32,
      "fees": [{
        "feeToken":"USDT",
        "fee":0.000008459
      }],
      "status":"filled",
    },...
  ]
}
```

## `tradeHistory`
Retrieve the trade history of the account. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/myTrades
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`|| Name of the option
`limit`|integer|`NO`|`20`|The number of trades returned (clamped to max 1000)
`side`|string|`NO`||Direction of the order.
`fromId`|integer|`NO`||TradeId to fetch from.
`toId`|integer|`NO`||TradeId to fetch to.

### **Response:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`time`|string|`1503439494351`|The timestamp of the trade
`tradeId`|string|`49366`|The ID for the trade
`orderId`|string|`630491422`|	ID of the order
`symbol`|string|`BTC-25AUG17-3900-CS`|	The name of the option
`price`|string|`0.055`|The price of the trade.
`quantity`|float|`23.3`|Quantity of the trade.
`side`|string|`buy`|Trade side from the user's point of view
`type`|string|`limit`|The order type, possible types: `limit`, `market`
`feeTokenName`|string|`USDT`|fee token name
`fee`|string|`0.000090000000000000`|fee of the trade.

### **Example:**
```js
{
  [
    {
      "time": 1528631913389,
      "tradeId": 7186202,
      "orderId": 630491422,
      "symbol": "BTC-28SEP18-7340-C",
      "price": 7093,
      "quantity": 30,
      "side":"buy",
      "type":"USDT",
      "feeTokenName":"USDT",
      "fee":"0.000090000000000000"
    },...
  ]
}
```

## `settlementHistory`

Retrieves settlement events that have affected your account. This API endpoint requires your request to be signed.

### **Request Weight:**

1

### **Request Url:**
```bash
GET  /openapi/option/settlements
```
### **Parameters:**

Parameter|type|default|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`all`|The option name. Or `all` to return all.

### **Responses:**

Name|type|example|description
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC-30MAR18-3740-CS`|Name of the option.
`timestamp`|integer|`1517299201923`|The timestamp of the settlement.
`settlementPrice`|float|`11008.37`|Underlying index price at time of settlement.
`strikePrice`|float|`3740`|Strike price of the option
`maxPayoff`|float|`400`|Maximum payoff of the option
`optionType`|string|`call`|Type of the option.
`position`|string|`1000`|position size
`margin`|float|`400`|Margin for the position
`averagePrice`|float|`213.2`|Average price of position.
`changed`|float|`20.3`|Profit or loss of the option
`changedRate`|string|`2.34%`|Change percentage of the total position price.

### **Examples:**
```js
[
  {
    "symbol":"BTC-23MAR18-9500-PS",
    "timestamp":1517299201923,
    "settlementPrice": 11008.37,
    "strikePrice":3740,
    "maxPayoff":400,
    "optionType":"call"
    "position":1000,
    "margin":0,
    "averagePrice":213.2,
    "changed":20.3,
    "chnagedRate":"2.34%"
    },...
  ]       
```

## `Account`

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
`balances`|float||Show balance status.

In the `balances` field:

Name|type|example|description
------------ | ------------ | ------------ | ------------
`free`|float|`600.0`|Amount available for use
`locked`|float|`100.0`|Amount locked (for open orders)
`margin`|float|`100.0`|Amount used for margin (for short positions)
### **Examples:**
```js
{
  'totalAsset': '100000.0',
  'optionAsset': '0.0',
  'balances': [
    {
      'tokenId': 'USDT',
      'tokenName': 'USDT',
      'free': '99899.9',
      'locked': '100.1',
      'margin': '0.0'
    }
  ]
}
```
