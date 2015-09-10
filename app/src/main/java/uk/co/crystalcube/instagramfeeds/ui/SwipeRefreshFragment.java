package uk.co.crystalcube.instagramfeeds.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import uk.co.crystalcube.instagramfeeds.rest.events.FetchPopularMediaSuccess;

/**
 * Abstract fragment class that implements pull to refresh abstraction and keeps common subscribed events
 * Created by Tanveer Aslam on 24/02/2015.
 */
@EFragment
public abstract class SwipeRefreshFragment extends EventBusAwareFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout container;

    /**
     * @see android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh()
     */
    @Override
    public void onRefresh() {

        container.setRefreshing(true);

        updateModel();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefreshing();
            }
        }, 5000);
    }

    @Subscribe
    public void onFetchPopularMedia(FetchPopularMediaSuccess event) {
        if (event.isSuccess()) {
            updateUi();
        }
    }

    @UiThread
    protected void updateUi() {
        stopRefreshing();
        updateViews();
    }

    protected void setContainer(SwipeRefreshLayout layout) {
        container = layout;
        initContainer();
    }

    private void initContainer() {

        assert container != null;

        container.setOnRefreshListener(this);
        container.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void stopRefreshing() {
        container.setRefreshing(false);
    }

    /**
     * Override this method in a derived class to refresh data model.
     */
    protected abstract void updateModel();

    /**
     * Override this method in derived class to refresh UI view components.
     */
    protected abstract void updateViews();

}
