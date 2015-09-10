package uk.co.crystalcube.instagramfeeds.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import uk.co.crystalcube.instagramfeeds.eventbus.EventBusManager;
import uk.co.crystalcube.instagramfeeds.rest.events.FetchPopularMediaSuccess;

/**
 * <p>
 * Base fragment that encapsulates Otto event-bus functions
 * </p>
 *
 * @author tanny
 *         Created: 24/03/15.
 */
@EFragment
public class EventBusAwareFragment extends Fragment {

    /* access event bus */
    @Bean
    protected EventBusManager busManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterBus();
    }

    private void registerBus() {
        busManager.register(this);
    }

    private void unregisterBus() {
        busManager.unregister(this);
    }
}
