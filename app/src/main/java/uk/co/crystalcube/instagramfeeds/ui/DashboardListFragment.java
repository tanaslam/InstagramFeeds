package uk.co.crystalcube.instagramfeeds.ui;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import uk.co.crystalcube.instagramfeeds.R;
import uk.co.crystalcube.instagramfeeds.model.media.popular.Datum;
import uk.co.crystalcube.instagramfeeds.model.media.popular.PopularMediaModel;
import uk.co.crystalcube.instagramfeeds.rest.RestClient;

/**
 * A Fragment class that embeds root layout of application dashboard
 * Created by tanny on 04/02/15.
 */
@EFragment(R.layout.fragment_dashboard)
public class DashboardListFragment extends SwipeRefreshFragment {

    @Bean
    protected RestClient restClient;

    @Bean
    protected PopularMediaModel model;

    @ViewById(R.id.container)
    protected SwipeRefreshLayout container;

    @ViewById(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @ViewById(R.id.progress_bar)
    protected ProgressBar progressbar;

    @AfterViews
    protected void setupViews() {
        setContainer(container);
        setupRecyclerView();
        doRefresh();
    }

    @Override
    protected void updateModel() {
        restClient.fetchPopularMedia();
    }


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void updateViews() {
        if (model.getData() == null || model.getData().size() == 0) {
            return;
        }
        recyclerView.setAdapter(new RecyclerAdapter(model.getData(), R.layout.list_item));
        progressbar.setVisibility(View.GONE);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<Datum> dataSet;
        private int resIdLayout;

        public RecyclerAdapter(List<Datum> list, int resIdLayout) {
            this.dataSet = list;
            this.resIdLayout = resIdLayout;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(resIdLayout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Datum item = dataSet.get(position);

            String caption = item.getCaption() == null ?
                    getActivity().getString(R.string.na) : item.getCaption().getText();
            holder.text.setText(caption);
            Picasso.with(getActivity())
                    .load(item.getImages().getStandardResolution().getUrl())
                    .into(holder.thumbnail);

            holder.itemView.setTag(item);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.thumbnail.setImageBitmap(null);
        }

        @Override
        public int getItemCount() {
            return dataSet == null ? 0 : dataSet.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView thumbnail;
            public TextView text;

            public ViewHolder(View itemView) {

                super(itemView);

                thumbnail = (ImageView) itemView.findViewById(R.id.grid_item_thumb);
                text = (TextView) itemView.findViewById(R.id.grid_item_caption);
            }
        }
    }
}
