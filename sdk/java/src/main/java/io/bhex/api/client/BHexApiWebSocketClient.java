package io.bhex.api.client;


import io.bhex.api.client.domain.account.Order;
import io.bhex.api.client.domain.account.SocketUserResponse;
import io.bhex.api.client.domain.event.CandlestickEvent;
import io.bhex.api.client.domain.event.DepthEvent;
import io.bhex.api.client.domain.event.TickerEvent;
import io.bhex.api.client.domain.event.TradeEvent;
import io.bhex.api.client.domain.market.CandlestickInterval;

import java.io.Closeable;

/**
 * BHex API data streaming façade, supporting streaming of events through web sockets.
 */
public interface BHexApiWebSocketClient extends Closeable {

    /**
     * Open a new web socket to receive {@link DepthEvent depthEvents} on a callback.
     *
     * @param symbols  SYMBOL must be form with exchangeId.symbol like '301.BTCUSDT'
     * @param callback the callback to call on new events
     * @return a {@link Closeable} that allows the underlying web socket to be closed.
     */
    Closeable onDepthEvent(String symbols, BHexApiCallback<DepthEvent> callback);

    /**
     * Open a new web socket to receive {@link CandlestickEvent candlestickEvents} on a callback.
     *
     * @param symbols  SYMBOL must be form with exchangeId.symbol like '301.BTCUSDT'
     * @param interval the interval of the candles tick events required
     * @param callback the callback to call on new events
     * @return a {@link Closeable} that allows the underlying web socket to be closed.
     */
    Closeable onCandlestickEvent(String symbols, CandlestickInterval interval, BHexApiCallback<CandlestickEvent> callback);


    /**
     * Open a new web socket to receive {@link TradeEvent} on a callback
     *
     * @param symbols  SYMBOL must be form with exchangeId.symbol like '301.BTCUSDT'
     * @param callback the callback to call on new events
     * @return a {@link Closeable} that allows the underlying web socket to be closed.
     */
    Closeable onTradeEvent(String symbols, BHexApiCallback<TradeEvent> callback);

    /**
     * Open a new web socket to receive {@link TickerEvent} on a callback
     *
     * @param symbols  SYMBOL must be form with exchangeId.symbol like '301.BTCUSDT'
     * @param callback the callback to call on new events
     * @return a {@link Closeable} that allows the underlying web socket to be closed.
     */
    Closeable onTicker24HourEvent(String symbols, BHexApiCallback<TickerEvent> callback);

    Closeable onUserEvent(String listenKey, BHexApiCallback<SocketUserResponse> callback);
}
