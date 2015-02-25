package uk.co.crystalcube.instagramfeeds.ui;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

/**
 * Abstract fragment class that implements pull to refresh abstraction
 * Created by Tanveer Aslam on 24/02/2015.
 */
@EFragment
public abstract class SwipeRefreshFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout container;

    private void initContainer() {

        assert container != null;

        container.setOnRefreshListener(this);
        container.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    protected void setContainer(SwipeRefreshLayout layout) {
        container = layout;
        initContainer();
    }

    @Override
    public void onRefresh() {
        container.setRefreshing(true);
        doRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefreshing();
            }
        }, 5000);
    }

    protected void stopRefreshing() {
        container.setRefreshing(false);
    }

    @Background
    protected void doRefresh() {
        updateModel();
        updateUi();
    }

    @UiThread
    protected void updateUi() {
        updateViews();
        stopRefreshing();
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
