# Error codes for Broker (2018-09-25)

Errors consist of two parts: an error code and a message. Codes are universal,
 but messages can vary. Here is the error JSON payload:

```javascript
{
  "code":-1121,
  "msg":"Invalid symbol."
}
```

## 10xx - General Server or Network issues

### -1000 UNKNOWN

* An unknown error occured while processing the request.

### -1001 DISCONNECTED

* Internal error; unable to process your request. Please try again.

### -1002 UNAUTHORIZED

* You are not authorized to execute this request. Request need API Key included in . We suggest that API Key be included in any request.

### -1003 TOO_MANY_REQUESTS

* Too many requests; please use the websocket for live updates.
* Too many requests; current limit is %s requests per minute. Please use the websocket for live updates to avoid polling the API.
* Way too many requests; IP banned until %s. Please use the websocket for live updates to avoid bans.

### -1006 UNEXPECTED_RESP

* An unexpected response was received from the message bus. Execution status unknown. OPEN API server find some exception in execute request .Please report to Customer service.

### -1007 TIMEOUT

* Timeout waiting for response from backend server. Send status unknown; execution status unknown.

### -1014 UNKNOWN_ORDER_COMPOSITION

* Unsupported order combination.

### -1015 TOO_MANY_ORDERS

* Reach the rate limit .Please slow down your request speed.
* Too many new orders.
* Too many new orders; current limit is %s orders per %s.

### -1016 SERVICE_SHUTTING_DOWN

* This service is no longer available.

### -1020 UNSUPPORTED_OPERATION

* This operation is not supported.

### -1021 INVALID_TIMESTAMP

* Timestamp for this request is outside of the recvWindow.
* Timestamp for this request was 1000ms ahead of the server's time.
* Please check the difference between your local time and server time .

### -1022 INVALID_SIGNATURE

* Signature for this request is not valid.

## 11xx - Request issues

### -1100 ILLEGAL_CHARS

* Illegal characters found in a parameter.
* Illegal characters found in parameter '%s'; legal range is '%s'.

### -1101 TOO_MANY_PARAMETERS

* Too many parameters sent for this endpoint.
* Too many parameters; expected '%s' and received '%s'.
* Duplicate values for a parameter detected.

### -1102 MANDATORY_PARAM_EMPTY_OR_MALFORMED

* A mandatory parameter was not sent, was empty/null, or malformed.
* Mandatory parameter '%s' was not sent, was empty/null, or malformed.
* Param '%s' or '%s' must be sent, but both were empty/null!

### -1103 UNKNOWN_PARAM

* An unknown parameter was sent.
* In BHEx Open Api , each request requires at least one parameter. {Timestamp}.

### -1104 UNREAD_PARAMETERS

* Not all sent parameters were read.
* Not all sent parameters were read; read '%s' parameter(s) but was sent '%s'.

### -1105 PARAM_EMPTY

* A parameter was empty.
* Parameter '%s' was empty.

### -1106 PARAM_NOT_REQUIRED

* A parameter was sent when not required.
* Parameter '%s' sent when not required.

### -1111 BAD_PRECISION

* Precision is over the maximum defined for this asset.

### -1112 NO_DEPTH

* No orders on book for symbol.

### -1114 TIF_NOT_REQUIRED

* TimeInForce parameter sent when not required.

### -1115 INVALID_TIF

* Invalid timeInForce.
* In the current version, this parameter is either empty or GTC.

### -1116 INVALID_ORDER_TYPE

* Invalid orderType.
* In the current version , ORDER_TYPE values is LIMIT or MARKET.

### -1117 INVALID_SIDE

* Invalid side.
* ORDER_SIDE values is BUY or SELL

### -1118 EMPTY_NEW_CL_ORD_ID

* New client order ID was empty.

### -1119 EMPTY_ORG_CL_ORD_ID

* Original client order ID was empty.

### -1120 BAD_INTERVAL

* Invalid interval.

### -1121 BAD_SYMBOL

* Invalid symbol.

### -1125 INVALID_LISTEN_KEY

* This listenKey does not exist.

### -1127 MORE_THAN_XX_HOURS

* Lookup interval is too big.
* More than %s hours between startTime and endTime.

### -1128 OPTIONAL_PARAMS_BAD_COMBO

* Combination of optional parameters invalid.

### -1130 INVALID_PARAMETER

* Invalid data sent for a parameter.
* Data sent for paramter '%s' is not valid.

### -1132 ORDER_PRICE_TOO_HIGH

* Order price too high.

### -1133 ORDER_PRICE_TOO_SMALL

* Order price lower than the minimum,please check general broker info.

### -1134 ORDER_PRICE_PRECISION_TOO_LONG

* Order price decimal too long,please check general broker info.

### -1135 ORDER_QUANTITY_TOO_BIG

* Order quantity too large.

### -1136 ORDER_QUANTITY_TOO_SMALL

* Order quantity lower than the minimum.

### -1137 ORDER_QUANTITY_PRECISION_TOO_LONG

* Order quantity decimal too long.

### -1138 ORDER_PRICE_WAVE_EXCEED

* Order price exceeds permissible range.

### -1139 ORDER_HAS_FILLED

* Order has been filled.

### -1140 ORDER_AMOUNT_TOO_SMALL

* Transaction amount lower than the minimum.

### -1141 ORDER_DUPLICATED

* Duplicate clientOrderId

### -1142 ORDER_CANCELLED

* Order has been canceled

### -1143 ORDER_NOT_FOUND_ON_ORDER_BOOK

* Cannot be found on order book

### -1144 ORDER_LOCKED

* Order has been locked

### -1145 ORDER_NOT_SUPPORT_CANCELLATION

* This order type does not support cancellation

### -1146 ORDER_CREATION_TIMEOUT

* Order creation timeout

### -1147 ORDER_CANCELLATION_TIMEOUT

* Order cancellation timeout


### -2010 NEW_ORDER_REJECTED

* NEW_ORDER_REJECTED

### -2011 CANCEL_REJECTED

* CANCEL_REJECTED

### -2013 NO_SUCH_ORDER

* Order does not exist.

### -2014 BAD_API_KEY_FMT

* API-key format invalid.

### -2015 REJECTED_MBX_KEY

* Invalid API-key, IP, or permissions for action.

### -2016 NO_TRADING_WINDOW

* No trading window could be found for the symbol. Try ticker/24hrs instead.

## Messages for -1010 ERROR_MSG_RECEIVED, -2010 NEW_ORDER_REJECTED, and -2011 CANCEL_REJECTED

This code is sent when an error has been returned by the matching engine.
The following messages which will indicate the specific error:

| Error message                                                 | Description                                                                                 |
| ------------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| "Unknown order sent."                                         | The order (by either `orderId`, `clOrdId`, `origClOrdId`) could not be found                |
| "Duplicate order sent."                                       | The `clOrdId` is already in use                                                             |
| "Market is closed."                                           | The symbol is not trading                                                                   |
| "Account has insufficient balance for requested action."      | Not enough funds to complete the action                                                     |
| "Market orders are not supported for this symbol."            | `MARKET` is not enabled on the symbol                                                       |
| "Iceberg orders are not supported for this symbol."           | `icebergQty` is not enabled on the symbol                                                   |
| "Stop loss orders are not supported for this symbol."         | `STOP_LOSS` is not enabled on the symbol                                                    |
| "Stop loss limit orders are not supported for this symbol."   | `STOP_LOSS_LIMIT` is not enabled on the symbol                                              |
| "Take profit orders are not supported for this symbol."       | `TAKE_PROFIT` is not enabled on the symbol                                                  |
| "Take profit limit orders are not supported for this symbol." | `TAKE_PROFIT_LIMIT` is not enabled on the symbol                                            |
| "Price* QTY is zero or less."                                 | `price`* `quantity` is too low                                                              |
| "IcebergQty exceeds QTY."                                     | `icebergQty` must be less than the order quantity                                           |
| "This action disabled is on this account."                    | Contact customer support; some actions have been disabled on the account.                   |
| "Unsupported order combination"                               | The `orderType`, `timeInForce`, `stopPrice`, and/or `icebergQty` combination isn't allowed. |
| "Order would trigger immediately."                            | The order's stop price is not valid when compared to the last traded price.                 |
| "Cancel order is invalid. Check origClOrdId and orderId."     | No `origClOrdId` or `orderId` was sent in.                                                  |
| "Order would immediately match and take."                     | `LIMIT_MAKER` order type would immediately match and trade, and not be a pure maker order.  |

## -9xxx Filter failures

| Error message                            | Description                                                                              |
| ---------------------------------------- | ---------------------------------------------------------------------------------------- |
| "Filter failure: PRICE_FILTER"           | `price` is too high, too low, and/or not following the tick size rule for the symbol.    |
| "Filter failure: LOT_SIZE"               | `quantity` is too high, too low, and/or not following the step size rule for the symbol. |
| "Filter failure: MIN_NOTIONAL"           | `price`* `quantity` is too low to be a valid order for the symbol.                       |
| "Filter failure: MAX_NUM_ORDERS"         | Account has too many open orders on the symbol.                                          |
| "Filter failure: MAX_ALGO_ORDERS"        | Account has too many open stop loss and/or take profit orders on the symbol.             |
| "Filter failure: BROKER_MAX_NUM_ORDERS"  | Account has too many open orders on the broker.                                          |
| "Filter failure: BROKER_MAX_ALGO_ORDERS" | Account has too many open stop loss and/or take profit orders on the broker.             |
| "Filter failure: ICEBERG_PARTS"          | Iceberg order would break into too many parts; icebergQty is too small.                  |
