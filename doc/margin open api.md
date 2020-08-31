# Margin Open API

## 通用API信息

* 所有的端点都会返回一个JSON object或者array.
* 数据返回的是一个 **升序**。更早的在前，更新的在后。
* 所有的时间/时间戳有关的变量都是milliseconds（毫秒级）。
* HTTP `4XX` 返回错误码是指请求内容有误，这个问题是在请求发起者这边。
* HTTP `429` 返回错误码是指请求次数上限被打破。
* HTTP `418` 返回错误码是指IP在收到`429`错误码后还继续发送请求被自动封禁。
* HTTP `5XX` 返回错误码是内部系统错误；这说明这个问题是在券商这边。在对待这个错误时，**千万** 不要把它当成一个失败的任务，因为执行状态 **未知**，有可能是成功也有可能是失败。
* 任何端点都可能返回ERROR（错误）； 错误的返回payload如下

```javascript
{
  "code": -1121,
  "msg": "Invalid symbol."
}
```

* 详细的错误码和错误信息在请见错误码文件。
* 对于`GET`端点，必须发送参数为`query string`（查询字串）。
* 对于`POST`, `PUT`, 和 `DELETE` 端点，必需要发送参数为`query string`（查询字串）或者发送参数在`request body`（请求主体）并设置content type（内容类型）为`application/x-www-form-urlencoded`。可以同时在`query string`或者`request body`里混合发送参数如果有需要的话。
* 参数可以以任意顺序发送。
* 如果有参数同时在`query string` 和 `request body`里存在，只有`query string`的参数会被使用。

### 限制

* 在 `/openapi/v1/brokerInfo`的`rateLimits` array里存在当前broker的`REQUEST_WEIGHT`和`ORDER`频率限制。
* 如果任一频率限额被超过，`429` 会被返回。
* 每条线路有一个`weight`特性，这个决定了这个请求占用多少容量（比如`weight`=2说明这个请求占用两个请求的量）。返回数据多的端点或者在多个symbol执行任务的端点可能有更高的`weight`。
* 当`429`被返回后，你有义务停止发送请求。
* **多次反复违反频率限制和/或者没有在收到429后停止发送请求的用户将会被收到封禁IP（错误码418）**
* IP封禁会被跟踪和 **调整封禁时长**（对于反复违反规定的用户，时间从 **2分钟到3天不等**）

### 端点安全类型

* 每个端点有一个安全类型，这决定了你会怎么跟其交互。
* API-key要以`X-BH-APIKEY`的名字传到REST API header里面。
* API-keys和secret-keys **要区分大小写**。
* 默认情况下，API-keys可以访问所有的安全节点。

安全类型 | 描述
------------ | ------------
NONE | 端点可以自由访问。
TRADE | 端点需要发送有效的API-Key和签名。
USER_DATA | 端点需要发送有效的API-Key和签名。
USER_STREAM | 端点需要发送有效的API-Key。
MARKET_DATA | 端点需要发送有效的API-Key。

* `TRADE` 和 `USER_DATA` 端点是 `SIGNED`（需要签名）的端点。

### SIGNED（有签名的）(TRADE和USER_DATA) 端点安全

* `SIGNED`（需要签名）的端点需要发送一个参数，`signature`，在`query string` 或者 `request body`里。
* 端点用`HMAC SHA256`签名。`HMAC SHA256 signature`是一个对key进行`HMAC SHA256`加密的结果。用你的`secretKey`作为key和`totalParams`作为value来完成这一加密过程。
* `signature`  **不区分大小写**。
* `totalParams` 是指 `query string`串联`request body`。

### 时效安全

* 一个`SIGNED`(有签名)的端点还需要发送一个参数，`timestamp`，这是当请求发起时的毫秒级时间戳。
* 一个额外的参数（非强制性）, `recvWindow`, 可以说明这个请求在多少毫秒内是有效的。如果`recvWindow`没有被发送，**默认值是5000**。
* 在当前，只有创建订单的时候才会用到`recvWindow`。
* 该参数的逻辑如下：

  ```javascript
  if (timestamp < (serverTime + 1000) && (serverTime - timestamp) <= recvWindow) {
    // process request
  } else {
    // reject request
  }
  ```

**严谨的交易和时效紧紧相关** 网络有时会不稳定或者不可靠，这会导致请求发送服务器的时间不一致。
有了`recvWindow`，你可以说明在多少毫秒内请求是有效的，否则就会被服务器拒绝。

**建议使用一个相对小的recvWindow（5000或以下）！**

## 公共接口

### 查询杠杆可交易币对  
* 接口地址:  `/openapi/v1/brokerInfo`
* 请求方法: GET 
* 请求参数
    无

**返回数据**

```javascript
    {
  "timezone": "UTC",
  "serverTime": 1538323200000,
  "rateLimits": [{
      "rateLimitType": "REQUESTS_WEIGHT",
      "interval": "MINUTE",
      "limit": 1500
    },
    {
      "rateLimitType": "ORDERS",
      "interval": "SECOND",
      "limit": 20
    },
    {
      "rateLimitType": "ORDERS",
      "interval": "DAY",
      "limit": 350000
    }
  ],
  "brokerFilters":[],
  "symbols": [{
    "symbol": "ETHBTC",
    "status": "TRADING",
    "baseAsset": "ETH",
    "baseAssetPrecision": "0.001",
    "quoteAsset": "BTC",
    "quotePrecision": "0.01",
    "icebergAllowed": false,
    "allowMargin":true,//是否支持杠杆交易
    "filters": [{
      "filterType": "PRICE_FILTER",
      "minPrice": "0.00000100",
      "maxPrice": "100000.00000000",
      "tickSize": "0.00000100"
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
#### Symbol过滤层

##### PRICE_FILTER

`PRICE_FILTER` 定义某个symbol的`price` 精度. 一共有3个部分：

* `minPrice` 定义最小允许的 `price`/`stopPrice`
* `maxPrice` 定义最大允许的 `price`/`stopPrice`.
* `tickSize` 定义`price`/`stopPrice` 可以增加和减少的间隔。

如果要通过`price filter`要求，`price`/`stopPrice`必须满足：

* `price` >= `minPrice`
* `price` <= `maxPrice`
* (`price`-`minPrice`) % `tickSize` == 0

**/brokerInfo格式:**

```javascript
  {
    "filterType": "PRICE_FILTER",
    "minPrice": "0.00000100",
    "maxPrice": "100000.00000000",
    "tickSize": "0.00000100"
  }
```

##### LOT_SIZE

`LOT_SIZE` 过滤层定义某个symbol `quantity`(在拍卖行里又称为"lots"）的精度。 一共有三个部分：

* `minQty` 定义最小允许的  `quantity`/`icebergQty`
* `maxQty` 定义最大允许的  `quantity`/`icebergQty`
* `stepSize`定义`quantity`/`icebergQty`可以增加和减少的间隔。

如果要通过`lot size`要求，`quantity`/`icebergQty`必须满足:

* `quantity` >= `minQty`
* `quantity` <= `maxQty`
* (`quantity`-`minQty`) % `stepSize` == 0

**/brokerInfo格式:**

```javascript
  {
    "filterType": "LOT_SIZE",
    "minQty": "0.00100000",
    "maxQty": "100000.00000000",
    "stepSize": "0.00100000"
  }
```

##### MIN_NOTIONAL

`MIN_NOTIONAL` 过滤层定义某个symbol的名义金额精度。一个订单的名义金额为 `price` * `quantity`.

**/brokerInfo format:**

```javascript
  {
    "filterType": "MIN_NOTIONAL",
    "minNotional": "0.00100000"
  }
```

## 账户接口

### 开通杠杆账户
* 接口地址:  `/openapi/v1/margin/open`
* 请求方法: POST 
* 请求参数
| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| recvWindow | long | 否 |  |  |
| timestamp | Long | 是 |  |  |

**返回数据**

```javascript
{
    "success": "true"
}
```
### 安全度查询
* 接口地址:  `/openapi/v1/margin/safety`
* 请求方法: GET 
* 请求参数
| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| recvWindow | long | 否 |  |  |
| timestamp | Long | 是 |  |  |

**返回数据**

```javascript
{
    "safety": "2.23" //安全度
}
```
### 查询杠杆账户资产信息
* 接口地址:  `/openapi/v1/margin/account`
* 请求方法: GET 
* 请求参数
| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| recvWindow | long | 否 |  |  |
| timestamp | Long | 是 |  |  |

**返回数据**

```javascript
{
  "balances": [
    {
      "asset": "BTC",
      "free": "4723846.89208129",
      "locked": "0.00000000"
    },
    {
      "asset": "LTC",
      "free": "4763368.68006011",
      "locked": "0.00000000"
    }
  ]
}
```

## 交易接口

### 杠杆下单

* 接口地址:  `/openapi/v1/margin/order`
* 请求方法: POST
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| symbol | string | 是 |  |  |
| side | string | 是 |  | enum: buy sell |
| type | string | 是 |  | enum: limit market limit_maker |
| price | DECIMAL | 否 | 订单价格 | 市价单时非必填 |
| quantity | DECIMAL | 是 | 订单数量 |  |
| newClientOrderId | string | 否 | 一个自己给订单定义的ID，如果没有发送会自动生成。 |  |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

**返回数据**
```javascript
{
  "orderId": 28,
  "clientOrderId": "6k9M212T12092"
}
```
### 杠杆撤单

* 接口地址:  `/openapi/v1/margin/order`
* 请求方法: DELETE
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| orderId | Long | 否 |  |  |
| clientOrderId | STRING | 否 |  |  |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

单一 orderId 或者 clientOrderId必须被发送。

```javascript
{
  "symbol": "LTCBTC",
  "clientOrderId": "tU721112KM",
  "orderId": 1,
  "status": "CANCELED"
}
```
### 查询订单
* 接口地址:  `/openapi/v1/margin/order`
* 请求方法: GET
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| orderId | Long | 否 |  |  |
| clientOrderId | STRING | 否 |  |  |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

Notes:

* 单一 orderId 或者 origClientOrderId 必须被发送。
* 对于某些历史数据 cummulativeQuoteQty 可能会 < 0, 这说明数据当前不可用。

```javascript
{
  "symbol": "LTCBTC",
  "orderId": 1,
  "clientOrderId": "9t1M2K0Ya092",
  "price": "0.1",
  "origQty": "1.0",
  "executedQty": "0.0",
  "cummulativeQuoteQty": "0.0",
  "status": "NEW",
  "timeInForce": "GTC",
  "type": "LIMIT",
  "side": "BUY",
  "stopPrice": "0.0",
  "icebergQty": "0.0",
  "time": 1499827319559,
  "updateTime": 1499827319559,
  "isWorking": true
}
```

### 当前订单查询
* 接口地址:  `/openapi/v1/margin/openOrders`
* 请求方法: GET
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| orderId | Long | 否 |  |  |
| symbol | STRING | 否 |  |  |
| limit | INT | 否 |  | 默认 500; 最多 1000. |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

Notes:

* 如果orderId设定好了，会筛选订单小于orderId的。否则会返回最近的订单信息。

```javascript
[
	{
  "symbol": "LTCBTC",
  "orderId": 1,
  "clientOrderId": "9t1M2K0Ya092",
  "price": "0.1",
  "origQty": "1.0",
  "executedQty": "0.0",
  "cummulativeQuoteQty": "0.0",
  "status": "NEW",
  "timeInForce": "GTC",
  "type": "LIMIT",
  "side": "BUY",
  "stopPrice": "0.0",
  "icebergQty": "0.0",
  "time": 1499827319559,
  "updateTime": 1499827319559,
  "isWorking": true
	}
]
```
###  历史订单查询
* 接口地址:  `/openapi/v1/margin/historyOrders`
* 请求方法: GET
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| orderId | Long | 否 |  |  |
| symbol | STRING | 否 |  |  |
| startTime | Long | 否 |  |  |
| endTime | Long | 否 |  |  |
| limit | INT | 否 |  | 默认 500; 最多 1000. |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

Notes:

* 如果orderId设定好了，会筛选订单小于orderId的。否则会返回最近的订单信息。

```javascript
[
	{
  "symbol": "LTCBTC",
  "orderId": 1,
  "clientOrderId": "9t1M2K0Ya092",
  "price": "0.1",
  "origQty": "1.0",
  "executedQty": "0.0",
  "cummulativeQuoteQty": "0.0",
  "status": "NEW",
  "timeInForce": "GTC",
  "type": "LIMIT",
  "side": "BUY",
  "stopPrice": "0.0",
  "icebergQty": "0.0",
  "time": 1499827319559,
  "updateTime": 1499827319559,
  "isWorking": true
	}
]
```
### 成交信息查询

* 接口地址:  `/openapi/v1/margin/myTrades`
* 请求方法: GET
* 请求参数

| 参数  | 类型 | 是否必填 | 描述   | 备注 |
| :-------- | :--- | :------- | :----- | :--- |
| startTime | Long | 否 |  |  |
| endTime | Long | 否 |  |  |
| fromId | Long | 否 |  | TradeId to fetch from. |
| toId | STRING | 否 |  |  TradeId to fetch to.|
| limit | INT | 否 |  | 默认 500; 最多 1000. |
| recvWindow | Long | 否 |  |  |
| timestamp | Long | 是 |  |  |

Notes:

* 如果只有fromId，会返回订单号小于fromId的，倒序排列。
* 如果只有toId，会返回订单号小于toId的，升序排列。
* 如果同时有fromId和toId, 会返回订单号在fromId和toId的，倒序排列。
* 如果fromId和toId都没有，会返回最新的成交记录，倒序排列。 Response:

```javascript
[
  {
    "symbol": "ETHBTC",
    "id": 28457,
    "orderId": 100234,
    "matchOrderId": 109834,
    "price": "4.00000100",
    "qty": "12.00000000",
    "commission": "10.10000000",
    "commissionAsset": "ETH",
    "time": 1499865549590,
    "isBuyer": true,
    "isMaker": false,
    "feeTokenId": "ETH",
    "fee": "0.012"
  }
]
```
## User Data Streams for Broker

### General WSS information

* A User Data Stream `listenKey` is valid for 60 minutes after creation.
* Doing a `PUT` on a `listenKey` will extend its validity for 60 minutes.
* Doing a `DELETE` on a `listenKey` will close the stream.
* User Data Streams are accessed at **/openapi/ws/\<listenKey\>**
* A single connection to api endpoint is only valid for 24 hours; expect to be disconnected at the 24 hour mark
* User data stream payloads are **not guaranteed** to be in order during heavy periods; **make sure to order your updates using E**

### API Endpoints

#### Create a listenKey

```shell
POST /openapi/v1/userDataStream
```

Start a new user data stream. The stream will close after 60 minutes unless a keepalive is sent.

**Weight:**
1

**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{
  "listenKey": "1A9LWJjuMwKWYP4QQPw34GRm8gz3x5AephXSuqcDef1RnzoBVhEeGE963CoS1Sgj"
}
```

#### Ping/Keep-alive a listenKey

```shell
PUT /openapi/v1/userDataStream
```

Keepalive a user data stream to prevent a time out. User data streams will close after 60 minutes. It's recommended to send a ping about every 30 minutes.

**Weight:**
1

**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
listenKey | STRING | YES |
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{}
```

#### Close a listenKey

```shell
DELETE /openapi/v1/userDataStream
```

Close out a user data stream.

**Weight:**
1

**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
listenKey | STRING | YES |
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{}
```

### Web Socket Payloads

#### Account Update

Account state is updated with the `outboundMarginAccountInfo` event.

**Payload:**

```javascript
{
  "e": "outboundMarginAccountInfo",   // Event type
  "E": 1499405658849,           // Event time
  // "m": 0,                       // Maker commission rate (bips)
  // "t": 0,                       // Taker commission rate (bips)
  // "b": 0,                       // Buyer commission rate (bips)
  // "s": 0,                       // Seller commission rate (bips)
  "T": true,                    // Can trade?
  "W": true,                    // Can withdraw?
  "D": true,                    // Can deposit?
  // "u": 1499405658848,           // Time of last account update
  "B": [                        // Balances changed
    {
      "a": "LTC",               // Asset
      "f": "17366.18538083",    // Free amount
      "l": "0.00000000"         // Locked amount
    }
  ]
}
```

#### Order Update

Orders are updated with the `marginExecutionReport` event. Check the API documentation and below for relevant enum definitions.
Average price can be found by doing `Z` divided by `z`.

**Payload:**

```javascript
{
  "e": "marginExecutionReport",        // Event type
  "E": 1499405658658,            // Event time
  "s": "ETHBTC",                 // Symbol
  "c": 1000087761,               // Client order ID
  "S": "BUY",                    // Side
  "o": "LIMIT",                  // Order type
  "f": "GTC",                    // Time in force
  "q": "1.00000000",             // Order quantity
  "p": "0.10264410",             // Order price
  // "P": "0.00000000",             // Stop price
  // "F": "0.00000000",             // Iceberg quantity
  // "g": -1,                       // Ignore
  // "x": "NEW",                    // Current execution type
  "X": "NEW",                    // Current order status
  // "r": "NONE",                   // Order reject reason; will be an error code.
  "i": 4293153,                  // Order ID
  "l": "0.00000000",             // Last executed quantity
  "z": "0.00000000",             // Cumulative filled quantity
  "L": "0.00000000",             // Last executed price
  "n": "0",                      // Commission amount
  "N": null,                     // Commission asset
  "u": true,                     // Is the trade normal, ignore for now
  // "T": 1499405658657,            // Transaction time
  // "t": -1,                       // Trade ID
  // "I": 8641984,                  // Ignore
  "w": true,                     // Is the order working? Stops will have
  "m": false,                    // Is this trade the maker side?
  // "M": false,                    // Ignore
  "O": 1499405658657,            // Order creation time
  "Z": "0.00000000"              // Cumulative quote asset transacted quantity
}
```

**Execution types:**

* NEW
* PARTIALLY_FILLED
* FILLED
* CANCELED
* REJECTED





