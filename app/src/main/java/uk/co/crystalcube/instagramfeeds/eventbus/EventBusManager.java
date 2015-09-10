package uk.co.crystalcube.instagramfeeds.eventbus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;


/**
 * <p>
 * The event busManager manager
 * </p>
 *
 * @author tanny
 *         Created: 03/03/2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EventBusManager {

    private Bus eventBus;

    @AfterInject
    protected void setupBean() {
        eventBus = new Bus(ThreadEnforcer.ANY);
    }

    /**
     * Get access to Otto bus object
     *
     * @return {@link Bus}
     */
    protected Bus getEventBus() {
        return eventBus;
    }

    /**
     * Register a subscriber with event bus.
     *
     * @param subscriber The subscriber object
     */
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    /**
     * Unregister a subscriber with event bus.
     *
     * @param subscriber The subscriber object
     */
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    /**
     * Post event on main thread
     *
     * @param event The subscribed event
     */
    public void postOnUi(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                eventBus.post(event);
            }
        });
    }

    /**
     * Post event on main thread
     *
     * @param event The subscribed event
     */
    public void post(final Object event) {
        eventBus.post(event);
    }
}
