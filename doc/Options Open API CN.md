# Broker API URL

Broker Open API的地址请见[这里](endpoint.md)

# 期权公共端点

## `brokerInfo`

获取当前broker的交易规则和symbol的信息（精度单位等信息）

### **Request Weight:**

0

### **Request Url:**
```bash
GET /openapi/v1/brokerInfo
```

### **Parameters:**

None

### **Response:**
名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`timezone`|string|`UTC`|服务器所在时区
`serverTime`|long|`1554887652929`|当前服务器时间（Unix Timestamp格式，ms毫秒级)



在`symbols`对应的信息组里，显示的是币币交易的symbol的信息（精度等），与期权交易并无关联，可以忽略。

在 `options`对应的信息组里，所有当前正在交易的期权信息将会被返回：

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0308CS3900`|期权名称
`status`|string|`TRADING`|期权当前状态
`baseAsset`|string|`BTC0308CS3900`|期权的名称
`baseAssetPrecision`|float|`0.001`|期权交易张数精度
`quoteAsset`|string|`BUSDT`|计价的货币
`quoteAssetPrecision`|float|`0.01`|期权交易价格的精度
`icebergAllowed`|string|`false`|是否支持“冰山订单”

在`options`里面的`filters`对应的信息组里：

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`filterType`|string|`PRICE_FILTER`|Filter类型
`minPrice`|float|`0.001`|期权最小交易价格
`maxPrice`|float|`100000.00000000`|
`tickSize`|float|`0.001`|期权交易价格精度
`minQty`|float|`0.01`|期权最小交易张数
`maxQty`|float|`100000.00000000`|
`stepSize`|float|`0.001`|期权交易张数精度
`minNotional`|float|`1`|订单金额精度 (数量 * 价格)

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

获取所有正在交易和已经交割的期权信息。如果需要获取历史期权信息，需要将`expired`设置成`true`

### **Request Weight:**

1

### **Request URL:**
```
GET /openapi/v1/getOptions
```

### **Parameters：**
名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | -----
`expired`|string|`NO`|`false`|设置为`true`来展示历史期权，可以用来获取历史期权信息。

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0412CS5400`|期权名称。命名规则为：`标的资产-交割时间-期权类型（看涨CS/看跌PS）-行权价`
`strike`|float|`5400.0`|期权的行权价
`created`|long|`1554710400000`|期权开始交易时的Unix Timestamp（毫秒ms)
`expiration`|long|`1555055400000`| 期权结束交易时的Unix Timestamp（毫秒ms)
`optionType`|integer|`1`|期权类型，`1`=看涨期权，`0`= 看跌期权
`maxPayoff`|float|`500`|期权的最大收益
`underlying`|string|`BTCBUSDT`|期权的标的的指数价格名称
`settlement`|string|`weekly`|结算区间。`weekly`=每周。



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
获取当前指数价格和EDP（预估交割价格）。这个端点不用发送任何参数。

### **Request Weight:**

0

### **Request URL:**
```
GET /quote/v1/option/index
```

### **Parameters:**
None

### **Response：**
名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`index`|float|`3652.81`|当前指数价格。
`edp`|float|`3652.81`|预估交割价格（过去10分钟指数价格的平均值）

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

获取当前订单簿的数据。

### **Request Weight:**

根据数量会不一样，请求数量越多，重量越大:

数量|请求重量
------------ | ------------
5, 10, 20, 50, 100|1
500|5
1000|10


### **Request Url:**
```
GET /openapi/quote/v1/option/depth
```

### **Parameters:**

名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | -----
`symbol`|string|`YES`||用来获取订单簿的期权名称。使用`getOptions`来获取期权名称。
`limit`|integer|`NO`|`100` (max = 100)|返回`bids`和`asks`的数量


### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1550829103981`|当前时间（Unix Timestamp，毫秒ms）
`bids`|list|(如下)|所有bid的价格和数量信息，最优bid价格由上到下排列。
`asks`|list|(如下)|所有ask的价格和数量信息，最优ask价格由上到下排列。

`bids`和`asks`所对应的信息组代表了订单簿的所有价格以及价格对应数量的信息，由最优价格从上到下排列。

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`''`|float|`123.10`|价格
`''`|float|`300`|当前价格对应的数量

### **Example:**

```js
{
  'time': 1555049455783,
  'bids': [
   ['78.82', '0.526'],//[价格，数量]
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
     ['122.96', '0.381'],//[价格，数量]
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

获取某个期权最近成交订单的信息。

### **Request Weight:**

1

### **Request URL:**
```
GET /openapi/quote/v1/option/trades
```
### **Parameters：**
名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | -------
`symbol`|string|`YES`||期权名称
`limit`|integer|`NO` (最大值为1000)|`100`|返回成交订单的数量

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`price`|float|`0.055`|交易价格
`time`|long|`1537797044116`|当前Unix时间戳，毫秒(ms)
`qty`|float|`5`|数量（张数）
`isBuyerMaker`|string|`true`|卖方还是买方。`true`=卖方，`false`=买方

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

获取某个期权的K线信息（高，低，开，收，交易量...)

### **Request Weight:**

1

### **Request URL:**
```
GET /openapi/quote/v1/option/klines
```

### **Parameters：**
名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | ------
`symbol`|string|`YES`||期权名称
`interval`|string|`YES`||K线图区间。可识别发送的值为：  `1m`,`5m`,`15m`,`30m`,`1h`,`1d`,`1w`,`1M`（`m`=分钟，`h`=小时,`d`=天，`w`=星期，`M`=月）
`limit`|integer|`NO`|`1000`|返回值的数量，最大值为1000
`to`|integer|`NO`||最后一个数据点的时间戳

### **Response:**
名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`''`|long|`1538728740000`|开始时间戳，毫秒（ms）
`''`|float|`36.00000'`|开盘价
`''`|float|`36.00000`|最高价
`''`|float|`36.00000`|最低价
`''`|float|`36.00000`|收盘价
`''`|float|`148976.11427815`|期权交易金额
`''`|long|`1538728740000`|停止时间戳，毫秒（ms）
`''`|float|`2434.19055334`|交易数量（张数）
`''`|integer|`308`|已成交数量（张数）
`''`|float|`1756.87402397`|买方购买金额
`''`|float|`28.46694368`|买方购买数量（张数）


### **Example:**
```js
[
  [
    1538728740000, //'开盘时间'
    '36.000000000000000000', //'开盘价'
    '36.000000000000000000', //'最高价'
    '36.000000000000000000', //'最低价':
    '36.000000000000000000', //'收盘价'
    '148976.11427815',  // 期权交易金额
    1499644799999,      // 收盘时间
    '2434.19055334',    // 交易数量（张数）
    308,                // 已成交数量（张数）
    '1756.87402397',    // 买方购买金额
    '28.46694368'       // 买方购买数量（张数）
  ],...
]
```

# 个人期权端点

## `order`

下一个做多（buy，即买入）或者做空（sell，即卖出）期权的订单。这个期权端点需要你的签名

### **Request Weight:**

1

### **Request URL:**
```bash
POST /openapi/option/v1/order
```

### **Parameters：**

名称|类型|是否强制|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`YES`|期权名称
`clientOrderId`|string/long|`NO`|订单的ID。可自己定义，如果没有发送，将会自动生成。
`side`|string|`YES`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`type`|string|`YES`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`timeInForce`|string|`NO`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`price`|float|`NO` Required for limit orders|订单的价格
`quantity`|float|`YES`|订单购买的数量（张数）

你可以从`brokerInfo`中获取期权价格，数量的配置信息。

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|订单创建时的时间戳，毫秒（ms）
`updateTime`|long|`1551062936784`|上次订单更新时间，毫秒（ms)
`orderId`|integer|`891`|订单ID（系统生成）
`clientOrderId`|integer|`213443`|订单ID（自己发送的）
`symbol`|string|`BTC0412CS4200`|期权名称
`price`|float|`4765.29`|订单价格
`origQty`|float|`1.01`|订单数量
`executedQty`|float|`1.01`|已经成交订单数量
`avgPrice`|float|`4754.24`|订单已经成交的平均价格
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`status`|string|`NEW`|订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`timeInForce`|string|`GTC`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`fees`|||订单产生的手续费

在`fees`里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|手续费计价单位
`fee`|float|`0`|实际费用值


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

取消一个订单，用`orderId` 或者 `clientOrderId`来取消。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
DELETE /openapi/option/v1/order/cancel
```

### **Parameter:**

名称|类型|是否强制|描述
------------ | ------------ | ------------ | ------------
`orderId`|integer|`NO`|系统自动生成的订单ID。
`clientOrderId`|string/long|`NO`|自己传送的订单ID。

**必须**传送以上两个参数的其中一个。

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|订单创建时的时间戳，毫秒（ms）
`updateTime`|long|`1551062936784`|上次订单更新时间，毫秒（ms)
`orderId`|integer|`891`|订单ID（系统生成）
`clientOrderId`|integer|`213443`|订单ID（自己发送的）
`symbol`|string|`BTC0412CS4200`|期权名称
`price`|float|`4765.29`|订单价格
`origQty`|float|`1.01`|订单数量
`executedQty`|float|`1.01`|已经成交订单数量
`avgPrice`|float|`4754.24`|订单已经成交的平均价格
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`status`|string|`NEW`|订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`timeInForce`|string|`GTC`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`fees`|||订单产生的手续费

在`fees`里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|手续费计价单位
`fee`|float|`0`|实际费用值

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
  'status': 'CANCELED', //cancel请求的订单状态会一直为`CANCELED`
  'timeInForce': 'GTC',
  'fees': []
}
```

### `openOrders`

获取你当前未成交的订单。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/v1/openOrders
```

### **Parameters:**
名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | --------
`symbol`|string|`NO`||期权名称，如果没有发送默认返回所有期权订单。
`orderId`|integer|`NO`||订单ID。
`side`|string|`NO`||订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`type`|string|`NO`||订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`limit`|integer|`NO`|`20`|返回值的数量。

如果发送了`orderId`，将会返回小于`orderId`的所有订单。若没有，将会返回最新的订单。

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|订单创建时的时间戳，毫秒（ms）
`updateTime`|long|`1551062936784`|上次订单更新时间，毫秒（ms)
`orderId`|integer|`891`|订单ID（系统生成）
`clientOrderId`|integer|`213443`|订单ID（自己发送的）
`symbol`|string|`BTC0412CS4200`|期权名称
`price`|float|`4765.29`|订单价格
`origQty`|float|`1.01`|订单数量
`executedQty`|float|`1.01`|已经成交订单数量
`avgPrice`|float|`4754.24`|订单已经成交的平均价格
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`status`|string|`NEW`|订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`timeInForce`|string|`GTC`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`fees`|||订单产生的手续费

在`fees`里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|手续费计价单位
`fee`|float|`0`|实际费用值

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

获取当前仓位信息。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/v1/positions
```

### **Parameters:**

名称|类型|是否强制|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`NO`|期权名称，如果没有发送默认返回所有期权的仓位。

### **Response:**
对于每个`symbol`（期权名称），这个端点将会返回以下信息。

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0405PS3850`|期权名称
`position`|float|`-10.760`|该期权的持仓量（张数）。可以为正（做多）也可以为负（做空）。
`margin`|float|`5380`|当前仓位的总保证金量
`settlementTime`|integer|`1555056000000`|该期权的交割时间戳，毫秒（ms）
`strikePrice`|float|`4200`|该期权的行权价
`price`|float|`500.00`|当前期权价格
`availablePosition`|float|`10.76`|可平仓数量（张数）
`averagePrice`|float|`9693.502194671`|持仓均价（持有仓位的成交金额/持仓量）
`changed`|float|`-4018.21`|持仓盈亏。**做多：** （最新价-持仓均价）\* 持仓量 **做空：**（最新价-持仓均价）\* 持仓量 \*(-1)
`changedRate`|float|`1.02`|持仓盈亏百分比。**做多：** 持仓盈亏/ （持仓均价 \* 持仓量） **做空：** 持仓盈亏/（保证金 - 持仓均价 \* 持仓量）
`index`|float|`5012.28666667`|当前标的资产指数值

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

获取历史订单信息（部分成交的、全部成交的、取消的）。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/v1/historyOrders
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | ---------
`symbol`|string|`NO`||期权名称，如果没有发送将默认返回所有期权的订单。
`side`|string|`NO`||订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`type`|string|`NO`||订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`orderStatus`|string|`NO`||订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`limit`|integer|`NO`|`20`|返回值的数量

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|订单创建时的时间戳，毫秒（ms）
`updateTime`|long|`1551062936784`|上次订单更新时间，毫秒（ms)
`orderId`|integer|`891`|订单ID（系统生成）
`clientOrderId`|integer|`213443`|订单ID（自己发送的）
`symbol`|string|`BTC0412CS4200`|期权名称
`price`|float|`4765.29`|订单价格
`origQty`|float|`1.01`|订单数量
`executedQty`|float|`1.01`|已经成交订单数量
`avgPrice`|float|`4754.24`|订单已经成交的平均价格
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`status`|string|`NEW`|订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`timeInForce`|string|`GTC`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`fees`|||订单产生的手续费

在`fees`里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|手续费计价单位
`fee`|float|`0`|实际费用值

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

## `getOrder`

获取某个订单的详细信息

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/v1/getOrder
```

### **Parameters:**
名称|类型|是否强制|默认|描述
------------ | ------------ | ------------ | ------------ | --------
`orderId`|integer|`NO`||订单ID
`clientOrderId`|string|`NO`||用户定义的订单ID

**注意：**` orderId` 或者 `clientOrderId` **必须发送其中之一**


### **Response:**
名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1551062936784`|订单创建时的时间戳，毫秒（ms）
`updateTime`|long|`1551062936784`|上次订单更新时间，毫秒（ms)
`orderId`|integer|`891`|订单ID（系统生成）
`clientOrderId`|integer|`213443`|订单ID（自己发送的）
`symbol`|string|`BTC0412CS4200`|期权名称
`price`|float|`4765.29`|订单价格
`origQty`|float|`1.01`|订单数量
`executedQty`|float|`1.01`|已经成交订单数量
`avgPrice`|float|`4754.24`|订单已经成交的平均价格
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`status`|string|`NEW`|订单状态。可能出现的值为：`NEW`(新订单，无成交)、`PARTIALLY_FILLED`（部分成交）、`FILLED`（全部成交）、`CANCELED`（已取消）和`REJECTED`（订单被拒绝）.
`timeInForce`|string|`GTC`|订单时间指令（Time in Force）。可能出现的值为：`GTC`（Good Till Canceled，一直有效），`FOK`（Fill or Kill，全部成交或者取消），`IOC`（Immediate or Cancel，立即成交或者取消）.
`fees`|||订单产生的手续费

在`fees`里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`feeToken`|string|`USDT`|手续费计价单位
`fee`|float|`0`|实际费用值

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

## `myTrades`

获取当前账户的成交订单记录。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
GET /openapi/option/v1/myTrades
```

### **Parameters:**
Parameter|type|required|default|description
------------ | ------------ | ------------ | ------------ | --------
`symbol`|string|`NO`|| 期权名称，如果没有发送将默认返回所有期权的订单。
`limit`|integer|`NO`|`20`|返回值的数量 (最大值为1000)
`side`|string|`NO`||订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`fromId`|integer|`NO`||大于这个值的tradeId的订单
`toId`|integer|`NO`||小于这个值的tradeId的订单

### **Response:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`time`|long|`1503439494351`|订单成交时的时间戳，毫秒（ms）
`tradeId`|long|`49366`|成交订单ID
`orderId`|long|`630491422`|	订单ID
`matchOrderId`|long|`630491432`| 成交对方订单ID
`price`|float|`0.055`|订单价格
`quantity`|float|`23.3`|订单数量
`feeTokenName`|string|`USDT`|手续费计价单位
`fee`|float|`0.000090000000000000`|手续费用值
`side`|string|`BUY`|订单方向。可能出现的值只能为：`BUY`（买入做多） 和 `SELL`（卖出做空）
`type`|string|`LIMIT`|订单类型。可能出现的值只能为:`LIMIT`(限价)和`MARKET`（市价）
`symbol`|string|`BTC0412PS3900`|期权名称

### **Example:**
```js
[
  {
    'time': '1554897921663',
    'tradeId': '336902617393292032',
    'orderId': '336902617267462912',
    "matchOrderId": 336002617267469062,
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

获取你账户里交割期权的信息。这个API端点需要你的签名。

### **Request Weight:**

1

### **Request Url:**
```bash
GET  /openapi/option/v1/settlements
```
### **Parameters:**
None

### **Responses:**

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`symbol`|string|`BTC0412PS3900`|期权名称
`optionType`|string|`call`|期权类型
`margin`|float|`400`|仓位所需的保证金
`timestamp`|integer|`1517299201923`|交割时间的时间戳，毫秒（ms）。
`strikePrice`|float|`3740`|当前期权的行权价
`settlementPrice`|float|`11008.37`|期权结算价格
`maxPayoff`|float|`400`|期权的最大收益
`averagePrice`|float|`9693.502194671`|持仓均价
`position`|string|`1000`|持仓量（张）
`changed`|float|`20.3`|交割收益
`changedRate`|float|`2.34`|交割收益百分比。 **做多：** 做多交割收益/ (持仓均价 \* 持仓量) **做空：**做空交割收益/（保证金-持仓均价 \* 持仓量）

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

获取当前账户余额信息。这个API端点需要你的签名。

### **Request Weight:**
1

### **Request Url:**
```bash
GET  /openapi/option/v1/account
```

### **Parameters:**
None

### **Response:**
名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`totalAsset`|float|`1000.0`|期权账户中的全部资产估值（以USDT计算）
`optionAsset`|float|`100.0`|期权账户中的期权估值（以USDT计算）
`balances`|float||展示余额数据

在`balances`数据组里:

名称|类型|例子|描述
------------ | ------------ | ------------ | ------------
`tokenName`|string|`USDT`|资产的名称
`free`|float|`600.0`|可用额
`locked`|float|`100.0`|冻结额（未成交订单冻结）
`margin`|float|`100.0`|保证金 （做空期权抵押）

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
