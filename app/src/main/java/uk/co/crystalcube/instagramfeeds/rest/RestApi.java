package uk.co.crystalcube.instagramfeeds.rest;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.PopularMediaModel;


/**
 * Created by tanny on 04/02/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(converters = {
        CustomGsonConverter.class /*,
        GsonHttpMessageConverter.class */})
public interface RestApi extends RestClientErrorHandling {

    /**
     * Root URL of back-end REST API.
     *
     * @param rootUrl root URL of RESTful API from configuration.
     */
    void setRootUrl(String rootUrl);
    RestTemplate getRestTemplate();

    /**
     * Set additional headers for request.
     *
     * @param name Header name
     * @param value Header value
     */
    void setHeader(String name, String value);

    /**
     * Set custom REST template.
     * This allows to override REST template configuration that is set by
     * default.
     *
     * @param restTemplate an instance of custom {@link RestTemplate}
     */
    void setRestTemplate(RestTemplate restTemplate);

    /**
     * Get a collection of popular media from instagram API
     *
     * @return Model object parsed from JSON
     */
    @Get("/v1/media/popular?access_token={token}")
    PopularMediaModel getMediaPopular(String token);
}

