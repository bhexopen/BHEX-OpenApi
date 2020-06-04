# 公有Broker Rest API (2019-07-01)

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

### SIGNED（签名） 的例子（对于POST /openapi/v1/order）

这里有一个详细的用Linux`echo`, `openssl`, 和 `curl`举例来展示如何发送一个有效的签名payload。

Key | 值
------------ | ------------
apiKey | tAQfOrPIZAhym0qHISRt8EFvxPemdBm5j5WMlkm3Ke9aFp0EGWC2CGM8GHV4kCYW
secretKey | lH3ELTNiFxCQTmi9pPcWWikhsjO04Yoqw3euoHUuOLC3GYBW64ZqzQsiOEHXQS76

参数名 | 参数值
------------ | ------------
symbol | ETHBTC
side | BUY
type | LIMIT
timeInForce | GTC
quantity | 1
price | 0.1
recvWindow | 5000
timestamp | 1538323200000

#### 例子 1: 在`queryString`里

* **queryString:** symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000
* **HMAC SHA256 signature:**

```shell
[linux]$ echo -n "symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000" | openssl dgst -sha256 -hmac "lH3ELTNiFxCQTmi9pPcWWikhsjO04Yoqw3euoHUuOLC3GYBW64ZqzQsiOEHXQS76"
(stdin)= 5f2750ad7589d1d40757a55342e621a44037dad23b5128cc70e18ec1d1c3f4c6
```

* **curl command:**

```shell
(HMAC SHA256)
[linux]$ curl -H "X-BH-APIKEY: tAQfOrPIZAhym0qHISRt8EFvxPemdBm5j5WMlkm3Ke9aFp0EGWC2CGM8GHV4kCYW" -X POST 'https://$HOST/openapi/v1/order?symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000&signature=5f2750ad7589d1d40757a55342e621a44037dad23b5128cc70e18ec1d1c3f4c6'
```

#### 例子 2:  在`request body`里

* **requestBody:** symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000
* **HMAC SHA256 signature:**

```shell
[linux]$ echo -n "symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000" | openssl dgst -sha256 -hmac "lH3ELTNiFxCQTmi9pPcWWikhsjO04Yoqw3euoHUuOLC3GYBW64ZqzQsiOEHXQS76"
(stdin)= 5f2750ad7589d1d40757a55342e621a44037dad23b5128cc70e18ec1d1c3f4c6
```

* **curl command:**

```shell
(HMAC SHA256)
[linux]$ curl -H "X-BH-APIKEY: tAQfOrPIZAhym0qHISRt8EFvxPemdBm5j5WMlkm3Ke9aFp0EGWC2CGM8GHV4kCYW" -X POST 'https://$HOST/openapi/v1/order' -d 'symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000&signature=5f2750ad7589d1d40757a55342e621a44037dad23b5128cc70e18ec1d1c3f4c6'
```

#### 例子 3: `queryString`和`request body`混合在一起

* **queryString:** symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC
* **requestBody:** quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000
* **HMAC SHA256 signature:**

```shell
[linux]$ echo -n "symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTCquantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000" | openssl dgst -sha256 -hmac "lH3ELTNiFxCQTmi9pPcWWikhsjO04Yoqw3euoHUuOLC3GYBW64ZqzQsiOEHXQS76"
(stdin)= 885c9e3dd89ccd13408b25e6d54c2330703759d7494bea6dd5a3d1fd16ba3afa
```

* **curl command:**

```shell
(HMAC SHA256)
[linux]$ curl -H "X-BH-APIKEY: tAQfOrPIZAhym0qHISRt8EFvxPemdBm5j5WMlkm3Ke9aFp0EGWC2CGM8GHV4kCYW" -X POST 'https://$HOST/openapi/v1/order?symbol=ETHBTC&side=BUY&type=LIMIT&timeInForce=GTC' -d 'quantity=1&price=0.1&recvWindow=5000&timestamp=1538323200000&signature=885c9e3dd89ccd13408b25e6d54c2330703759d7494bea6dd5a3d1fd16ba3afa'
```

***注意在例子3里有一点不一样，"GTC"和"quantity=1"之间没有&。***

## 公共 API 端点

### 术语解释

* `base asset` 指的是symbol的`quantity`（即数量）。

* `quote asset` 指的是symbol的`price`（即价格）。

### ENUM 定义

**Symbol 状态:**

* TRADING - 交易中
* HALT - 终止
* BREAK - 断开

**Symbol 类型:**

* SPOT - 现货

**资产类型:**

* CASH - 现金
* MARGIN - 保证金

**订单状态:**

* NEW - 新订单，暂无成交
* PARTIALLY_FILLED - 部分成交
* FILLED - 完全成交
* CANCELED - 已取消
* PENDING_CANCEL - 等待取消
* REJECTED - 被拒绝

**订单类型:**

* LIMIT - 限价单
* MARKET - 市价单
* LIMIT_MAKER - maker限价单
* STOP_LOSS (unavailable now)  - 暂无
* STOP_LOSS_LIMIT (unavailable now) - 暂无
* TAKE_PROFIT (unavailable now) - 暂无
* TAKE_PROFIT_LIMIT (unavailable now) - 暂无
* MARKET_OF_PAYOUT (unavailable now) - 暂无

**订单方向:**

* BUY - 买单
* SELL - 卖单

**订单时效类型:**

* GTC
* IOC
* FOK

**k线/烛线图区间:**

  m -> 分钟; h -> 小时; d -> 天; w -> 周; M -> 月

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

**频率限制类型 (rateLimitType)**

* REQUESTS_WEIGHT
* ORDERS

**频率限制区间**

* SECOND
* MINUTE
* DAY

### 通用端点

#### 测试连接

```shell
GET /openapi/v1/ping
```

测试REST API的连接。

**Weight:**
0

**Parameters:**
NONE

**Response:**

```javascript
{}
```

#### 服务器时间

```shell
GET /openapi/v1/time
```

测试连接并获取当前服务器的时间。

**Weight:**
0

**Parameters:**
NONE

**Response:**

```javascript
{
  "serverTime": 1538323200000
}
```

#### Broker信息

```shell
GET /openapi/v1/brokerInfo
```

当前broker交易规则和symbol信息

**Weight:**
0

**Parameters:**
NONE

**Response:**

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

### 市场数据端点

#### 订单簿

```shell
GET /openapi/quote/v1/depth
```

**Weight:**

根据limit不同：

Limit | Weight
------------ | ------------
5, 10, 20, 50, 100 | 1
500 | 5
1000 | 10

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |
limit | INT | NO | 默认 100; 最大 100.

**注意:** 如果设置limit=0会返回很多数据。

**Response:**

[价格, 数量]

```javascript
{
  "bids": [
    [
      "3.90000000",   // 价格
      "431.00000000"  // 数量
    ],
    [
      "4.00000000",
      "431.00000000"
    ]
  ],
  "asks": [
    [
      "4.00000200",  // 价格
      "12.00000000"  // 数量
    ],
    [
      "5.10000000",
      "28.00000000"
    ]
  ]
}
```

#### 最近成交

```shell
GET /openapi/quote/v1/trades
```

获取当前最新成交（最多500）

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |
limit | INT | NO | Default 500; max 1000.

**Response:**

```javascript
[
  {
    "price": "4.00000100",
    "qty": "12.00000000",
    "time": 1499865549590,
    "isBuyerMaker": true
  }
]
```

#### k线/烛线图数据

```shell
GET /openapi/quote/v1/klines
```

symbol的k线/烛线图数据
K线会根据开盘时间而辨别。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |
interval | ENUM | YES |
startTime | LONG | NO |
endTime | LONG | NO |
limit | INT | NO | 默认500; 最大1000.

* 如果startTime和endTime没有发送，只有最新的K线会被返回。

**Response:**

```javascript
[
  [
    1499040000000,      // 开盘时间
    "0.01634790",       // 开盘价
    "0.80000000",       // 最高价
    "0.01575800",       // 最低价
    "0.01577100",       // 收盘价
    "148976.11427815",  // 交易量
    1499644799999,      // 收盘时间
    "2434.19055334",    // Quote asset数量
    308,                // 交易次数
    "1756.87402397",    // Taker buy base asset数量
    "28.46694368"       // Taker buy quote asset数量
  ]
]
```

#### 24小时ticker价格变化数据

```shell
GET /openapi/quote/v1/ticker/24hr
```

24小时价格变化数据。**注意** 如果没有发送symbol，会返回很多数据。

**Weight:**

如果只有一个symbol，1; 如果symbol没有被发送，**40**。

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | NO |

* 如果symbol没有被发送，所有symbol的数据都会被返回。

**Response:**

```javascript
{
  "time": 1538725500422,
  "symbol": "ETHBTC",
  "bestBidPrice": "4.00000200",
  "bestAskPrice": "4.00000200",
  "lastPrice": "4.00000200",
  "openPrice": "99.00000000",
  "highPrice": "100.00000000",
  "lowPrice": "0.10000000",
  "volume": "8913.30000000"
}
```

OR

```javascript
[
  {
    "time": 1538725500422,
    "symbol": "ETHBTC",
    "lastPrice": "4.00000200",
    "openPrice": "99.00000000",
    "highPrice": "100.00000000",
    "lowPrice": "0.10000000",
    "volume": "8913.30000000"
 }
]
```

#### Symbol价格

```shell
GET /openapi/quote/v1/ticker/price
```

单个或多个symbol的最新价。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | NO |

* 如果symbol没有发送，所有symbol的最新价都会被返回。

**Response:**

```javascript
{
  "price": "4.00000200"
}
```

OR

```javascript
[
  {
    "symbol": "LTCBTC",
    "price": "4.00000200"
  },
  {
    "symbol": "ETHBTC",
    "price": "0.07946600"
  }
]
```

#### Symbol最佳订单簿价格

```shell
GET /openapi/quote/v1/ticker/bookTicker
```

单个或者多个symbol的最佳买单卖单价格。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | NO |

* 如果symbol没有被发送，所有symbol的最佳订单簿价格都会被返回。

**Response:**

```javascript
{
  "symbol": "LTCBTC",
  "bidPrice": "4.00000000",
  "bidQty": "431.00000000",
  "askPrice": "4.00000200",
  "askQty": "9.00000000"
}
```

OR

```javascript
[
  {
    "symbol": "LTCBTC",
    "bidPrice": "4.00000000",
    "bidQty": "431.00000000",
    "askPrice": "4.00000200",
    "askQty": "9.00000000"
  },
  {
    "symbol": "ETHBTC",
    "bidPrice": "0.07946700",
    "bidQty": "9.00000000",
    "askPrice": "100000.00000000",
    "askQty": "1000.00000000"
  }
]
```

### 账户端点

#### 创建新订单  (TRADE)

```shell
POST /openapi/v1/order  (HMAC SHA256)
```

发送一个新的订单

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |
assetType | STRING | NO |
side | ENUM | YES |
type | ENUM | YES |
timeInForce | ENUM | NO |
quantity | DECIMAL | YES |
price | DECIMAL | NO |
newClientOrderId | STRING | NO | 一个自己给订单定义的ID，如果没有发送会自动生成。
stopPrice | DECIMAL | NO | 与 `STOP_LOSS`, `STOP_LOSS_LIMIT`, `TAKE_PROFIT`, 和`TAKE_PROFIT_LIMIT` 订单一起使用. **当前不可用**
icebergQty | DECIMAL | NO | 与 `LIMIT`, `STOP_LOSS_LIMIT`, 和 `TAKE_PROFIT_LIMIT` 来创建冰山订单. **当前不可用**
recvWindow | LONG | NO |
timestamp | LONG | YES |

在`type`上的额外强制参数:

类型 | 额外强制参数
------------ | ------------
`LIMIT` | `timeInForce`, `quantity`, `price`
`MARKET` | `quantity`
`STOP_LOSS` | `quantity`, `stopPrice`  **当前不可用**
`STOP_LOSS_LIMIT` | `timeInForce`, `quantity`,  `price`, `stopPrice` **当前不可用**
`TAKE_PROFIT` | `quantity`, `stopPrice` **当前不可用**
`TAKE_PROFIT_LIMIT` | `timeInForce`, `quantity`, `price`, `stopPrice` **当前不可用**
`LIMIT_MAKER` | `quantity`, `price`


**Response:**

```javascript
{
  "orderId": 28,
  "clientOrderId": "6k9M212T12092"
}
```

#### 测试新订单 (TRADE)

```shell
POST /openapi/v1/order/test (HMAC SHA256)
```

用signature和recvWindow测试生成新订单。
创建和验证一个新订单但是不送入撮合引擎。

**Weight:**
1

**Parameters:**

和 `POST /openapi/v1/order`一样。

**Response:**

```javascript
{}
```

#### 查询订单 (USER_DATA)

```shell
GET /openapi/v1/order (HMAC SHA256)
```

查询订单状态。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
orderId | LONG | NO |
origClientOrderId | STRING | NO |
recvWindow | LONG | NO |
timestamp | LONG | YES |

Notes:

* 单一 `orderId` 或者 `origClientOrderId` 必须被发送。
* 对于某些历史数据 `cummulativeQuoteQty` 可能会 < 0, 这说明数据当前不可用。

**Response:**

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

#### 取消订单 (TRADE)

```shell
DELETE /openapi/v1/order  (HMAC SHA256)
```

取消当前正在交易的订单。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
orderId | LONG | NO |
clientOrderId | STRING | NO |
recvWindow | LONG | NO |
timestamp | LONG | YES |

单一 `orderId` 或者 `clientOrderId`必须被发送。

**Response:**

```javascript
{
  "symbol": "LTCBTC",
  "clientOrderId": "tU721112KM",
  "orderId": 1,
  "status": "CANCELED"
}
```

#### 当前订单(USER_DATA)

```shell
GET /openapi/v1/openOrders  (HMAC SHA256)
```

获取当前单个或者多个symbol的当前订单。**注意** 如果没有发送symbol，会返回很多数据。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | String | NO |
orderId | LONG | NO |
limit | INT | NO | 默认 500; 最多 1000.
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Notes:**

* 如果`orderId`设定好了，会筛选订单小于`orderId`的。否则会返回最近的订单信息。

**Response:**

```javascript
[
  {
    "symbol": "LTCBTC",
    "orderId": 1,
    "clientOrderId": "t7921223K12",
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

#### 历史订单 (USER_DATA)

```shell
GET /openapi/v1/historyOrders (HMAC SHA256)
```
获取当前账户的所有订单。亦或是取消的，完全成交的，拒绝的。

**Weight:**
5

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
symbol | String | NO |
orderId | LONG | NO |
startTime | LONG | NO |
endTime | LONG | NO |
limit | INT | NO | Default 500; max 1000.
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Notes:**

* 如果`orderId`设定好了，会筛选订单小于`orderId`的。否则会返回最近的订单信息。

**Response:**

```javascript
[
  {
    "symbol": "LTCBTC",
    "orderId": 1,
    "clientOrderId": "987yjj2Ym",
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

#### 账户信息 (USER_DATA)

```shell
GET /openapi/v1/account (HMAC SHA256)
```

获取当前账户信息

**Weight:**
5

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{
  "canTrade": true,
  "canWithdraw": true,
  "canDeposit": true,
  "updateTime": 123456789,
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

#### 账户交易记录 (USER_DATA)

```shell
GET /openapi/v1/myTrades  (HMAC SHA256)
```

获取当前账户历史成交记录

**Weight:**
5

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
startTime | LONG | NO |
endTime | LONG | NO |
fromId | LONG | NO | TradeId to fetch from.
toId | LONG | NO | TradeId to fetch to.
limit | INT | NO | Default 500; max 1000.
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Notes:**

* 如果只有`fromId`，会返回订单号小于`fromId`的，倒序排列。
* 如果只有`toId`，会返回订单号小于`toId`的，升序排列。
* 如果同时有`fromId`和`toId`, 会返回订单号在`fromId`和`toId`的，倒序排列。
* 如果`fromId`和`toId`都没有，会返回最新的成交记录，倒序排列。
**Response:**

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

#### 账户存款记录 (USER_DATA)

```shell
GET /openapi/v1/depositOrders  (HMAC SHA256)
```

获取当前账户的存款记录

**Weight:**
5

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
token | STRING | NO | 默认全部
startTime | LONG | NO |
endTime | LONG | NO |
fromId | LONG | NO | 从哪个OrderId起开始抓取。默认抓取最新的存款记录。
limit | INT | NO | 默认 500; 最大 1000.
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Notes:**

* 如果`orderId`设定好了，会筛选订单小于`orderId`的。否则会返回最近的订单信息。

**Response:**

```javascript
[
  {
	"orderId": 100234,
	"token": "EOS",
	"address": "deposit2bh",
	"addressTag": "19012584",
	"fromAddress": "clarkkent",
	"fromAddressTag": "19029901",
	"time": 1499865549590,
	"quantity": "1.01"
  }
]
```

#### 获取某个提币记录 (USER_DATA)

```shell
GET /openapi/v1/withdraw/detail  (HMAC SHA256)
```

获取当前账户的提币记录

**Weight:**
2

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
orderId | LONG | NO | orderId和clientOrderId两者必须有一个有值
clientOrderId | STRING | NO | orderId和clientOrderId两者必须有一个有值

**Response:**

```javascript

{
    "time":"1536232111669",
    "orderId":"90161227158286336",
    "accountId":"517256161325920",
    "tokenId":"BHC",
    "tokenName":"BHC",
    "address":"0x815bF1c3cc0f49b8FC66B21A7e48fCb476051209",
    "addressExt":"address tag",
    "quantity":"14", // 提币金额
    "arriveQuantity":"14", // 到账金额
    "statusCode":"PROCESSING_STATUS",
    "status":3,
    "txid":"",
    "txidUrl":"",
    "walletHandleTime":"1536232111669",
    "feeTokenId":"BHC",
    "feeTokenName":"BHC",
    "fee":"0.1",
    "requiredConfirmNum":0, // 要求确认数
    "confirmNum":0, // 确认数
    "kernelId":"", // BEAM 和 GRIN 独有
    "isInternalTransfer": false // 是否内部转账
}
```

#### 账户提币记录 (USER_DATA)

```shell
GET /openapi/v1/withdrawalOrders  (HMAC SHA256)
```

获取当前账户的提币记录

**Weight:**
5

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
token | STRING | NO | 默认全部
startTime | LONG | NO |
endTime | LONG | NO |
fromId | LONG | NO | 从哪个OrderId起开始抓取。默认抓取最新的存款记录。
limit | INT | NO | 默认 500; 最大 1000.
recvWindow | LONG | NO |
timestamp | LONG | NO |

**Notes:**

* 如果`orderId`设定好了，会筛选订单小于`orderId`的。否则会返回最近的订单信息。

**Response:**

```javascript
[
    {
        "time":"1536232111669",
        "orderId":"90161227158286336",
        "accountId":"517256161325920",
        "tokenId":"BHC",
        "tokenName":"BHC",
        "address":"0x815bF1c3cc0f49b8FC66B21A7e48fCb476051209",
        "addressExt":"address tag",
        "quantity":"14", // 提币金额
        "arriveQuantity":"14", // 到账金额
        "statusCode":"PROCESSING_STATUS",
        "status":3,
        "txid":"",
        "txidUrl":"",
        "walletHandleTime":"1536232111669",
        "feeTokenId":"BHC",
        "feeTokenName":"BHC",
        "fee":"0.1",
        "requiredConfirmNum":0, // 要求确认数
        "confirmNum":0, // 确认数
        "kernelId":"", // BEAM 和 GRIN 独有
        "isInternalTransfer": false // 是否内部转账
    },
    {
        "time":"1536053746220",
        "orderId":"762522731831527",
        "accountId":"517256161325920",
        "tokenId":"BHC",
        "tokenName":"BHC",
        "address":"fdfasdfeqfas12323542rgfer54135123",
        "addressExt":"EOS tag",
        "quantity":"",
        "arriveQuantity":"10",
        "statusCode":"BROKER_AUDITING_STATUS",
        "status":"2",
        "txid":"",
        "txidUrl":"",
        "walletHandleTime":"1536232111669",
        "feeTokenId":"BHC",
        "feeTokenName":"BHC",
        "fee":"0.1",
        "requiredConfirmNum":0, // 要求确认数
        "confirmNum":0, // 确认数
        "kernelId":"", // BEAM 和 GRIN 独有
        "isInternalTransfer": false // 是否内部转账
    }
]
```

**提币状态说明**

| status | statusCode | 描述 |
| :--- | :--- | :--- |
| 1 | BROKER_AUDITING_STATUS | 券商审核中 |
| 2 | BROKER_REJECT_STATUS | 券商审核拒绝 |
| 3 | AUDITING_STATUS | 平台审核中 |
| 4 | AUDIT_REJECT_STATUS | 平台审核拒绝 |
| 5 | PROCESSING_STATUS | 钱包处理中 |
| 6 | WITHDRAWAL_SUCCESS_STATUS | 提币成功 |
| 7 | WITHDRAWAL_FAILURE_STATUS | 提币失败 |
| 8 | BLOCK_MINING_STATUS | 区块打包中 |

### 用户数据流端点

详细的用户信息流说明在另一个文档中。

#### 开始用户信息流 (USER_STREAM)

```shell
POST /openapi/v1/userDataStream
```

开始一个新的用户信息流。如果keepalive指令没有发送，信息流将将会在60分钟后关闭。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{
  "listenKey": "1A9LWJjuMwKWYP4QQPw34GRm8gz3x5AephXSuqcDef1RnzoBVhEeGE963CoS1Sgj"
}
```

#### Keepalive用户信息流 (USER_STREAM)

```shell
PUT /openapi/v1/userDataStream
```

维持用户信息流来防止断开连接。用户信息流会在60分钟后自动中断，所以建议30分钟发送一次ping请求。

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
listenKey | STRING | YES |
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{}
```

#### 关闭用户信息流 (USER_STREAM)

```shell
DELETE /openapi/v1/userDataStream
```

关闭用户信息流

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
listenKey | STRING | YES |
recvWindow | LONG | NO |
timestamp | LONG | YES |

**Response:**

```javascript
{}
```

#### 子账户列表(SUB_ACCOUNT_LIST)

```shell
POST /openapi/v1/subAccount/query
```

查询子账户列表

**Parameters:**

无

**Weight:**
5

**Response:**

```javascript
[
    {
        "accountId": "122216245228131",
        "accountName": "",
        "accountType": 1,
        "accountIndex": 0 // 账户index 0 默认账户 >0, 创建的子账户
    },
    {
        "accountId": "482694560475091200",
        "accountName": "createSubAccountByCurl", // 子账户名称
        "accountType": 1, // 子账户类型 1 币币账户 3 合约账户
        "accountIndex": 1
    },
    {
        "accountId": "422446415267060992",
        "accountName": "",
        "accountType": 3,
        "accountIndex": 0
    },
    {
        "accountId": "482711469199298816",
        "accountName": "createSubAccountByCurl",
        "accountType": 3,
        "accountIndex": 1
    },
]
```


#### 账户内转账 (ACCOUNT_TRANSFER)

```shell
POST /openapi/v1/transfer
```

转账

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
fromAccountType | int | YES |源账户类型, 1 钱包(币币)账户 2 期权账户 3 合约账户
fromAccountIndex | int | YES |子账户index, 主账户Api调用时候有用，从子账户列表接口获取
toAccountType | int | YES | 目标账户类型, 1 钱包(币币)账户 2 期权账户 3 合约账户
toAccountIndex | int | YES | 子账户index, 主账户Api调用时候有用，从子账户列表接口获取
tokenId | STRING | YES | tokenID
amount | STRING | YES | 转账数量

**Response:**

```javascript
{
    "success":"true" // 0成功
}
```

**说明**

1、转账账户和收款账户的其中一方，必须是主账户(钱包账户)

2、主账户Api可以从钱包账户向其他账户(包括子账户)转账，也可以从其他账户向钱包账户转账

3、**子账户Api调用的时候只能从当前子账户向主账户(钱包账户)转账，所以fromAccountType\fromAccountIndex\toAccountType\toAccountIndex不用填**


#### 查询流水 (BALANCE_FLOW)

```shell
POST /openapi/v1/balance_flow
```

查询账户流水

**Weight:**
5

**Parameters:**

|名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
| accountType   | int     | 否 | 账户对应的account_type | 默认1 |
| accountIndex  | int     | 否 | 账户对应的account_index | 默认0 |
| tokenId       | string  | 否     | token_id     | eg: BTC                                |
| fromFlowId  | long    | 否     | 顺向查询数据 | 指定查询 id < fromFlowId的数据 |
| endFlowId   | long    | 否     | 反向查询数据 | 指定查询 id > |endFlowId的数据  |
| startTime     | long    | 否     | 开始时间     | 毫秒时间戳                             |
| endTime       | long    | 否     | 结束时间     | 毫秒时间戳                             |
| limit          | integer | 否     | 每页记录数   | 默认50，最大100                                       |

**Response:**

```javascript
[
    {
        "id": "539870570957903104",
        "accountId": "122216245228131",
        "tokenId": "BTC",
        "tokenName": "BTC",
        "flowTypeValue": 51, // 流水类型
        "flowType": "USER_ACCOUNT_TRANSFER", // 流水类型名称
        "flowName": "Transfer", // 流水类型说明
        "change": "-12.5", // 变动值
        "total": "379.624059937852365", // 变动后当前tokenId总资产
        "created": "1579093587214"
    },
    {
        "id": "536072393645448960",
        "accountId": "122216245228131",
        "tokenId": "USDT",
        "tokenName": "USDT",
        "flowTypeValue": 7,
        "flowType": "AIRDROP",
        "flowName": "Airdrop",
        "change": "-2000",
        "total": "918662.0917630848",
        "created": "1578640809195"
    }
]
```

#### 提现

```shell
POST /openapi/v1/withdraw
```

提现

**Weight:**
1

**Parameters:**

名称 | 类型 | 是否强制 | 描述
------------ | ------------ | ------------ | ------------
| tokenId | string | 必填 | tokenId |  |
| clientOrderId | long | 必填 | 券商端生成的订单id， 防止重复提币 |  |
| address | string | 必填| 提币地址(注意：提现地址必须是在PC端或者APP端维护在常用地址列表里面的地址) |  |
| addressExt | string | 选填| EOS tag |  |
| withdrawQuantity | string | 必填 | 提币数量 |  |
| chainType | string | 非必填 |chain type, USDT的chainType分别是OMNI ERC20 TRC20，默认OMNI |

**Response:**

```javascript
    {
        "status": 0,
        "success": true,
        "needBrokerAudit": false, // 是否需要券商审核
        "orderId": "423885103582776064" // 提币成功订单id
    }
```

**说明**

1、主账户Api可以查询钱包账户或者其他账户(包括子账户，指定accountType和accountIndex)的流水’

2、子账户Api只能查询当前子账户的流水，所以不用指定accountType和accountIndex

**流水类型说明请见如下**

归类|类型参数名|类型参数代号|解释说明|
------|------|------|------|
通用流水类|TRADE|1|交易|
通用流水类|FEE|2|交易手续费|
通用流水类|TRANSFER|3|转账|
通用流水类|DEPOSIT|4|充值|
衍生品业务|MAKER_REWARD|27|maker奖励
衍生品业务|PNL|28|期货等的盈亏
衍生品业务|SETTLEMENT|30|交割
衍生品业务|LIQUIDATION|31|强平
衍生品业务|FUNDING_SETTLEMENT|32|期货等的资金费率结算
用户子账户之间内部转账|USER_ACCOUNT_TRANSFER|51|userAccountTransfer 专用，流水没有subjectExtId
OTC|OTC_BUY_COIN|65|OTC 买入coin
OTC|OTC_SELL_COIN|66|OTC 卖出coin
OTC|OTC_FEE|73|OTC 手续费
OTC|OTC_TRADE|200|旧版 OTC 流水
活动|ACTIVITY_AWARD|67|活动奖励
活动|INVITATION_REFERRAL_BONUS|68|邀请返佣
活动|REGISTER_BONUS|69|注册送礼
活动|AIRDROP|70|空投
活动|MINE_REWARD|71|挖矿奖励

### 过滤层

过滤层（filter）定义某个broker的某个symbol的交易规则
过滤层（filter）有两个大类：`symbol filters` 和 `broker filters`

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
