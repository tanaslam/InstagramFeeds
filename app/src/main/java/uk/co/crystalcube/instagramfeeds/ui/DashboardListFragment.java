package uk.co.crystalcube.instagramfeeds.ui;


import android.support.v4.widget.SwipeRefreshLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import uk.co.crystalcube.instagramfeeds.R;
import uk.co.crystalcube.instagramfeeds.model.media.popular.PopularMediaModel;
import uk.co.crystalcube.instagramfeeds.rest.RestClient;

/**
 * A Fragment class that embeds root layout of application dashboard
 * Created by tanny on 04/02/15.
 */
@EFragment(R.layout.fragment_dashboard_list)
public class DashboardListFragment extends SwipeRefreshFragment {

    @Bean
    protected RestClient restClient;

    @ViewById(R.id.container)
    protected SwipeRefreshLayout container;

    private PopularMediaModel model;

    @AfterViews
    protected void setupViews() {
        setContainer(container);
        doRefresh();
    }

    @Override
    protected void updateModel() {
        restClient.fetchPopularMedia();
    }

    @Override
    protected void updateViews() {
        model = restClient.getModel();
        if (model != null) {
            //TODO do something
        }
    }
}
