package uk.co.crystalcube.instagramfeeds.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import uk.co.crystalcube.instagramfeeds.R;
import uk.co.crystalcube.instagramfeeds.rest.RestClient;
import uk.co.crystalcube.instagramfeeds.rest.events.FetchPopularMediaSuccess;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.Datum;
import uk.co.crystalcube.instagramfeeds.rest.model.media.popular.PopularMediaModel;

/**
 * A fragment class that show media feed in @{link GridView}
 * Created by tanny on 25/02/2015.
 */
@EFragment(R.layout.fragment_dashboard)
public class DashboardGridFragment extends SwipeRefreshFragment {

    @Bean
    protected RestClient restClient;

    @Bean
    protected PopularMediaModel model;

    @ViewById(R.id.container)
    protected SwipeRefreshLayout container;

    @ViewById(R.id.progress_bar)
    protected ProgressBar progressBar;

    @ViewById(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @AfterViews
    protected void setupViews() {
        setContainer(container);
        setupRecyclerView();
        updateModel();
    }

    @Override
    protected void updateViews() {
        if (model.getData() == null || model.getData().size() == 0) {
            return;
        }
        recyclerView.setAdapter(new RecyclerAdapter(model.getData(), R.layout.grid_item));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void updateModel() {
        restClient.fetchPopularMedia();
    }


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),
                getActivity().getResources().getInteger(R.integer.max_columns)));
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
                    .load(item.getImages().getThumbnail().getUrl())
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
