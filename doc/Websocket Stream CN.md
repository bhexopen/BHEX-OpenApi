# Web Socket行情接口 (2019-08-12)

## 基本信息

* 行情基础端点请参见[这里](endpoint.md)
* 直接访问时URL格式为 **/openapi/quote/ws/v1**

| 名称 | 值 |
| :--- | :---- |
| topic | realtimes, trade, kline_$interval, depth|
| event | sub, cancel, cancel_all|
| interval | 1m, 5m, 15m, 30m, 1h, 2h, 6h, 12h, 1d, 1w, 1M|

**请求订阅数据样例:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "$topic",
  "event": "sub",
  // 可调整的参数
    "params": {
        // kline返回上限是2000，默认为1
        "limit": "$limit",
        // 返回的数据是否是压缩过的，默认为false
        "binary": "false"
    }
}
```

| 名称 | 解释 |
| :--- | :---- |
|limit|规定返回结果的数量|
|binary|规定返回的数据是否是压缩过的。**默认**值是**false**。|

## 心跳

每隔一段时间，客户端需要发送ping帧，服务端会回复pong帧，否则服务端会在5分钟内主动断开链接。

* 请求
```javascript
{
    "ping": 1535975085052
}
```

* 返回
```javascript
{
    "pong": 1535975085052
}
```

### 逐笔交易

逐笔交易推送每一笔成交的信息。成交，或者说交易的定义是仅有一个吃单者与一个挂单者相互交易。

在成功连接到服务器后，服务器首先会推送一条最近的60条成交。在这条推送之后，每条推送都是实时的成交。

变量“v”可以理解成一个交易ID。这个变量是全局递增的并且独特的。例如：假设过去5秒有3笔交易发生，分别是ETHUSDT、BTCUSDT、BHTBTC。它们的“v”会为连续的值（112，113，114）。


**请求订阅数据样例:**

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

**返回:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "trade",
  "data": [{
    "v": "426635153180475392", // 参见解释
    "t": 1565594873508,  //时间戳
    "p": "11369", // 价格
    "q": "0.01", // 数量
    "m": false // true = 买, false = 卖
  }, {
    "v": "426635153373413376",
    "t": 1565594873531,
    "p": "11369",
    "q": "0.0012",
    "m": false
  }],
  "f": false // 是不是第一个返回
}
```

### Symbol的Ticker

按Symbol逐秒刷新的24小时完整ticker信息

**请求订阅数据样例:**

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

**返回:**

```javascript
{
  "symbol": "ETHUSDT",
  "topic": "realtimes",
  "data": [{
    "t": "1565592599015", //时间戳
    "s": "ETHUSDT", //symbol
    "c": "212.63", //收盘价
    "h": "216.96", //最高价
    "l": "206.78", //最低价
    "o": "210.23", //开盘价
    "v": "73013.575", //交易量
    "qv": "15726612.498168", //交易额
    }],
  "f": false // 是否为第一个返回
}
```


### K线/蜡烛图

K线stream逐秒推送所请求的K线种类(最新一根K线)的更新

**K线/蜡烛图间隔:**

订阅Kline需要提供间隔参数，最短为分钟线，最长为月线。支持以下间隔:


m -> 分钟; h -> 小时; d -> 天; w -> 周; M -> 月

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

**请求订阅数据样例:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "kline_"+$间隔,
  "event": "sub",
  "params": {
    "binary": false
  }
}
```

**返回:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "kline",
  "params": {"klineType": "15m"},
  "data": [{
    "t": 1565595900000, //k线开始时间
    "s": "BTCUSDT", // symbol
    "c": "11436.14", //收盘价
    "h": "11437", //最高价
    "l": "11381.89", //最低价
    "o": "11381.89", //开盘价
    "v": "16.3306" //交易量
  }],
  "f": true // 是否为第一个返回
}
```

### 订单簿深度信息

Symbol的深度信息。

这里是订单簿快照推送的详细信息：
* 订单簿快照频率：每300ms, 如果book变了的话。
* 订单簿快照频率深度：bids 和 asks各300
* 订单簿版本变更触发事件：
  * 订单进入订单簿
  * 订单离开订单簿
  * 订单数量变更
  * 订单已完成

#### 有限档深度信息

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

**返回:**

```javascript
{
  "symbol": "BTCUSDT",
  "topic": "depth",
  "data": [{
    "s": "BTCUSDT", //Symbol
    "t": 1565600357643, //时间戳
    "v": "112801745_18", //见上面解释
    "b": [ //Bids
      ["11371.49", "0.0014"], //[价格, 数量]
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
      ["11375.41", "0.0053"], //[价格, 数量]
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
  "f": true//是否为第一个返回
}
```

#### 增量深度信息

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

每秒推送订单簿的变化部分（如果有）。

在增量深度信息中，数量不一定等于对应价格的数量。如果数量=0，这说明在上一条推送中的这个价格已经没有了。如果数量>0，这时的数量为更新后的这个价格所对应的数量

假设我们收到的返回数据中有这样一条：

```javascript
["0.00181860", "155.92000000"]// 价格，数量
```

如果下一条返回数据中有：
```javascript
["0.00181860", "12.3"]
```
这说明这个价格对应的数量有变更，已经更新变更的数量

如果下一条返回数据中有：
```javascript
["0.00181860", "0"]
```
这说明这个价格对应的数量已经消失，将会在客户端中删除。

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
  "f": false //是否为第一个返回值
}
```

### 指数数据

期权和期货指数数据。

**请求订阅数据样例:**

```javascript
{
  "symbol": "$symbol0, $symbol1",
  "topic": "index",
  "event": "sub",
}
```

**返回:**

```javascript
{
  "symbol": "HTUSDT",
  "topic": "index",
  "data": [{
    "symbol": "HTUSDT", //symbol
    "index": "5.0941", //当前指数价
    "edp": "5.08799333", //预计交割价，见期权rest文档
    "formula": "(5.0941[HUOBI])/1" //来源
  }],
  "f": true //是否为第一个返回
}
```

## 错误处理

**错误码:**

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
