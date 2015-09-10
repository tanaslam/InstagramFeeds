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
import uk.co.crystalcube.instagramfeeds.eventbus.EventBusManager;
import uk.co.crystalcube.instagramfeeds.rest.RestClient;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.Caption;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.Datum;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.PopularMediaModel;

/**
 * A Fragment class that embeds root layout of application dashboard
 * Created by tanny on 04/02/15.
 */
@EFragment(R.layout.fragment_dashboard)
public class DashboardListFragment extends SwipeRefreshFragment {

    @Bean
    protected EventBusManager bus;

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
        updateModel();
    }

    @Override
    protected void updateModel() {
        restClient.fetchPopularMedia();
    }

    @Override
    protected void updateViews() {
        if (model.getData() == null || model.getData().size() == 0) {
            return;
        }
        recyclerView.setAdapter(new RecyclerAdapter(model.getData(), R.layout.list_item));
        progressbar.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<Datum> dataSet;
        private int resIdLayout;

        public RecyclerAdapter(List<Datum> list, int resIdLayout) {
            this.dataSet = list;
            this.resIdLayout = resIdLayout;
        }

        @Override
        public int getItemViewType(int position) {
            //TODO override this method for different element layouts e.g. header/multi-column spanned elements
            return super.getItemViewType(position);
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(resIdLayout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Datum item = dataSet.get(position);

            setCaption(holder, item);
            setImage(holder, item);

            holder.itemView.setTag(item);
        }

        private void setImage(ViewHolder holder, Datum item) {
            Picasso.with(getActivity())
                    .load(item.getImages().getStandardResolution().getUrl())
                    .into(holder.thumbnail);
        }

        private void setCaption(ViewHolder holder, Datum item) {
            Caption caption = item.getCaption();
            if (caption == null) {
                holder.text.setVisibility(View.GONE);
            } else {
                holder.text.setVisibility(View.VISIBLE);
                holder.text.setText(item.getCaption().getText());
            }
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
