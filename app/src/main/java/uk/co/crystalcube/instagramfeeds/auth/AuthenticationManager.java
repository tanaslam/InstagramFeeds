package uk.co.crystalcube.instagramfeeds.auth;

import android.net.Uri;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Created by tanny on 24/02/2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AuthenticationManager {

    private OAuthService service;
    private Verifier verifier;

    public void init(String clientId, String clientSecret, String redirectUrl, String scope) {
        service = new ServiceBuilder()
                .provider( InstagramApi.class )
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .scope(scope)
                .callback(redirectUrl + clientId) // OOB forbidden. We need an url and the better is on the tumblr website !
                .build();
    }

    /**
     *
     * @return Authorization url to present user with option.
     */
    public String getAuthorizationUrl() {
       return service.getAuthorizationUrl(Token.empty());
    }

    /**
     *
     * @return Request code if client is authorized or null.
     */
    public String getRequestToken() {

        try {
            return service.getRequestToken().getToken();
        } catch (UnsupportedOperationException e) {
            //NO-OP let it return null
        }

        return null;
    }

    public void setVerifier(Verifier verifier) {
        this.verifier = verifier;
    }
    /**
     *
     * @return Get API access token in exchange of request code.
     */
    public String getAccessToken() {
        try {
            return service.getAccessToken(service.getRequestToken(), verifier).getToken();
        } catch (UnsupportedOperationException e) {
            //NO-OP let it return null;
        }

        return null;
    }

}
