package uk.co.crystalcube.instagramfeeds;

import android.app.Application;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.scribe.model.Verifier;

import uk.co.crystalcube.instagramfeeds.auth.AuthenticationManager;

/**
 * Main Application object that gives us access over application context.
 * Created by Tanveer Aslam on 24/02/2015.
 */
@EApplication
public class InstagramApp extends Application {

    public static final String CLIENT_ID = "4141a1979ae146e3af1f95226736db1d";
    public static final String CLIENT_SECRET = "21f83507f8a74b8f86d6f4adebe061ec";
    public static final String REDIRECT_URL = "http://www.crystalcube.co.uk/login?client_id=";
    public static final String SCOPE = "likes";

    @Bean
    protected AuthenticationManager auth;

    private String accessToken;

    @AfterInject
    protected void initApplication() {
        auth.init(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL, SCOPE);
    }

    /**
     *
     * @return accessToken that was obtain by OAuth authentication.
     */
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
