package io.broker.api.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.broker.api.client.BrokerApiError;
import io.broker.api.client.exception.BrokerApiException;
import io.broker.api.client.security.AuthenticationInterceptor;
import io.broker.api.client.service.BrokerApiService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

/**
 * Generates a Broker API implementation based on @see {@link BrokerApiService}.
 */
public class BrokerApiServiceGenerator {
    private static final OkHttpClient sharedClient = new OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Converter.Factory converterFactory = JacksonConverterFactory.create(OBJECT_MAPPER);

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, BrokerApiError> errorBodyConverter =
            (Converter<ResponseBody, BrokerApiError>) converterFactory.responseBodyConverter(
                    BrokerApiError.class, new Annotation[0], null);

    public static <S> S createService(String baseUrl, Class<S> serviceClass) {
        return createService(baseUrl, serviceClass, null, null);
    }

    /**
     *
     * @param baseUrl api base url
     * @param serviceClass the service class
     * @param apiKey api key
     * @param secret api secret
     * @return a service instance
     */
    public static <S> S createService(String baseUrl, Class<S> serviceClass, String apiKey, String secret) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory);

        if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(secret)) {
            retrofitBuilder.client(sharedClient);
        } else {
            // `adaptedClient` will use its own interceptor, but share thread pool etc with the 'parent' client
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
            retrofitBuilder.client(adaptedClient);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                BrokerApiError apiError = getBrokerApiError(response);
                throw new BrokerApiException(apiError);
            }
        } catch (IOException e) {
            throw new BrokerApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static BrokerApiError getBrokerApiError(Response<?> response) throws IOException, BrokerApiException {
        if (errorBodyConverter == null) {
            throw new BrokerApiException("errorBodyConverter is NULL");
        }

        ResponseBody responseBody = response.errorBody();
        if (responseBody == null) {
            BrokerApiError brokerApiError = new BrokerApiError();
            brokerApiError.setCode(-1);
            brokerApiError.setMsg("api response error body is empty.");
            return brokerApiError;
        } else {
            return errorBodyConverter.convert(responseBody);
        }
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }

}
