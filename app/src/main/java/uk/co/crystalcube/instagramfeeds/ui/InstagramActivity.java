package uk.co.crystalcube.instagramfeeds.ui;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import uk.co.crystalcube.instagramfeeds.eventbus.EventBusManager;

/**
 * <p>
 * Abstract base activity for all or most activities in the app.
 * </p>
 *
 * @author tanny
 * Created: 09/09/15.
 */
@EActivity
public abstract class InstagramActivity extends AppCompatActivity {

    @Bean
    protected EventBusManager busManager;

    @Override
    protected void onStart() {
        super.onStart();
        busManager.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        busManager.unregister(this);
    }
}
