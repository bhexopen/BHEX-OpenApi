package io.bhex.api.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhex.api.client.BHexApiError;
import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.client.exception.BHexApiException;
import io.bhex.api.client.security.AuthenticationInterceptor;
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
 * Generates a BHex API implementation based on @see {@link io.bhex.api.client.service.BHexApiService}.
 */
public class BHexApiServiceGenerator {
    private static final OkHttpClient sharedClient = new OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Converter.Factory converterFactory = JacksonConverterFactory.create(OBJECT_MAPPER);

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, BHexApiError> errorBodyConverter =
            (Converter<ResponseBody, BHexApiError>) converterFactory.responseBodyConverter(
                    BHexApiError.class, new Annotation[0], null);

    public static <S> S createService(Class<S> serviceClass) {
        return createService(BHexConstants.API_BASE_URL, serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        return createService(BHexConstants.API_BASE_URL, serviceClass, apiKey, secret);
    }

    /**
     *
     * @param baseUrl
     * @param serviceClass
     * @param apiKey
     * @param secret
     * @param <S>
     * @return
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
                BHexApiError apiError = getBHexApiError(response);
                throw new BHexApiException(apiError);
            }
        } catch (IOException e) {
            throw new BHexApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static BHexApiError getBHexApiError(Response<?> response) throws IOException, BHexApiException {
        return errorBodyConverter.convert(response.errorBody());
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }

}
