package uk.co.crystalcube.instagramfeeds.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import uk.co.crystalcube.instagramfeeds.R;
import uk.co.crystalcube.instagramfeeds.imagecache.BitmapLruCache;
import uk.co.crystalcube.instagramfeeds.imagecache.ImageDownloader;
import uk.co.crystalcube.instagramfeeds.model.media.popular.Datum;
import uk.co.crystalcube.instagramfeeds.model.media.popular.PopularMediaModel;
import uk.co.crystalcube.instagramfeeds.rest.RestClient;

/**
 * A fragment class that show media feed in @{link GridView}
 * Created by tanny on 25/02/2015.
 */
@EFragment(R.layout.fragment_dashboard_grid)
public class DashboardGridFragment extends SwipeRefreshFragment {

    @Bean
    protected RestClient restClient;

    @Bean
    protected ImageDownloader imageDownloader;

    @Bean
    protected PopularMediaModel model;

    @ViewById(R.id.container)
    protected SwipeRefreshLayout container;

    @ViewById(R.id.recycler_view)
    protected RecyclerView recyclerView;


    @AfterViews
    protected void setupViews() {

        setContainer(container);
        setupRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 50);

        if(model.getData() == null || model.getData().size() == 0) {
            doRefresh();
        } else {
            updateViews();
        }
    }

    /**
     * Refresh data model and fragment
     */
    public void refresh() {
        super.doRefresh();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),
                getActivity().getResources().getInteger(R.integer.max_columns)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void updateViews() {
        if (model.getData() == null || model.getData().size() == 0) {
            return;
        }
        recyclerView.setAdapter(new RecyclerAdapter(model.getData(), R.layout.grid_item));
    }

    @Override
    protected void updateModel() {
        restClient.fetchPopularMedia();
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
            imageDownloader.setImage(holder.thumbnail,
                    item.getImages().getThumbnail().getUrl(), View.NO_ID, R.drawable.ic_launcher);

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
