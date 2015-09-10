package uk.co.crystalcube.instagramfeeds.rest;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import uk.co.crystalcube.instagramfeeds.InstagramApp;
import uk.co.crystalcube.instagramfeeds.eventbus.EventBusManager;
import uk.co.crystalcube.instagramfeeds.rest.events.FetchPopularMediaSuccess;
import uk.co.crystalcube.instagramfeeds.rest.events.RestCallComplete.CallType;
import uk.co.crystalcube.instagramfeeds.rest.events.RestCallFailed;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.PopularMediaModel;

/**
 * REST API client for Instagram back-end API.
 * Created by tanny on 04/02/15.
 */

@EBean(scope = EBean.Scope.Singleton)
public class RestClient implements RestErrorHandler {

    private static final String LOG_TAG = RestClient.class.getCanonicalName();

    private static final int HTTP_REQUEST_CONNECTION_TIMEOUT = 5000;
    private static final int HTTP_REQUEST_READ_TIMEOUT = 15000;
    private static final boolean SHOULD_USE_PROXY = false;

    public static final String ROOT_URL = "https://api.instagram.com";

    @RootContext
    protected InstagramApp context;

    @RestService
    protected RestApi restService;

    @Bean
    protected PopularMediaModel model;

    @Bean
    protected EventBusManager busManager;

    private CallType callType = CallType.UNKNOWN;

    @AfterInject
    void setupRestClient() {
        restService.setRootUrl(ROOT_URL);
        restService.setRestErrorHandler(this);
        setRequestFactory();
    }

    private void setRequestFactory() {

        HttpsClientRequestFactory factory =
                new HttpsClientRequestFactory(getHostnameVerifier());

        factory.setConnectTimeout(HTTP_REQUEST_CONNECTION_TIMEOUT);
        factory.setReadTimeout(HTTP_REQUEST_READ_TIMEOUT);

        if (SHOULD_USE_PROXY) {

            InetSocketAddress proxyHost =
                    new InetSocketAddress("192.0.0.10", 8080);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyHost);
            factory.setProxy(proxy);
        }

        restService.getRestTemplate().setRequestFactory(factory);
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {

            /**
             * Implements {@link javax.net.ssl.HostnameVerifier}
             * and verifies hostname of RESTful backend API server by string matching with build configuration.
             *
             * @return true if RESTful API root URI contains host name.
             */
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return ROOT_URL.contains(hostname);
            }
        };
    }

    public PopularMediaModel getModel() {
        return model;
    }

    @Background
    public void fetchPopularMedia() {

        if (!isValid(CallType.FETCH_POPULAR_MEDIA)) {
            return;
        }

        callType = CallType.FETCH_POPULAR_MEDIA;

        // Set authorization header or token
        PopularMediaModel response = restService.getMediaPopular(context.getAccessToken());
        updateModel(response);

        busManager.post(new FetchPopularMediaSuccess());

        callType = CallType.UNKNOWN;
    }

    private boolean isValid(CallType callType) {
        return this.callType != callType;
    }

    protected void updateModel(PopularMediaModel response) {

        if (response != null) {
            model.setData(response.getData());
            model.setMeta(response.getMeta());
        }
    }

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {

        Log.e(LOG_TAG, "Failed to parse response", e);

        if (e.contains(HttpStatusCodeException.class)) {
            HttpStatusCodeException httpException = (HttpStatusCodeException) e.getCause();
            int statusCode = httpException.getStatusCode().value();
            String error = httpException.getMessage();
            busManager.post(new RestCallFailed(callType, statusCode, error));
        } else {
            busManager.post(new RestCallFailed(callType));
        }

        callType = CallType.UNKNOWN;
    }
}
