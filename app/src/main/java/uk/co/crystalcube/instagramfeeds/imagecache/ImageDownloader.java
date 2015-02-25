package uk.co.crystalcube.instagramfeeds.imagecache;

import android.content.Context;
import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.BreakIterator;

import uk.co.crystalcube.instagramfeeds.rest.RestClient;

/**
 * <p>
 * An helper class to manage image downloading and caching easier.
 * </p>
 *
 * Created on 26/06/2014.
 *
 * @see com.android.volley.toolbox.ImageLoader
 * @see com.android.volley.RequestQueue
 *
 * @author aslamt
 */
@EBean(scope = EBean.Scope.Singleton)
public class ImageDownloader {

    private static final String LOG_TAG = ImageDownloader.class.getSimpleName() ;

    /**
     * Android application context to access android resources
     */
    @RootContext
    protected Context context;

    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private String baseUrl;

    /**
     * Initialise Bean with imageLoader and requestQueue instance and
     * sets image repository base URI from build configuration.
     */
    @AfterInject
    protected void setup() {
        requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
        baseUrl = RestClient.ROOT_URL;
    }

    /**
     * Download an image from give URI and set it to specified {@link android.widget.ImageView} upon
     * successful download or set a placeholder image if download failed.
     *
     * @param imageView target view to set image to
     * @param imageUrl relative URI to image resource in image repository.
     * @param loadingImageResId resource ID of an awaiting image.
     * @param failedImageResId resource ID of a placeholder image.
     */
    protected void download(final ImageView imageView, String imageUrl,
                            final int loadingImageResId, final int failedImageResId) {

        String url = parseImageUri(imageUrl);

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean b) {
               if(response != null && response.getBitmap() != null) {
                   imageView.setImageBitmap(response.getBitmap());
               } else if(loadingImageResId != View.NO_ID) {
                   imageView.setImageResource(loadingImageResId);
               }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(failedImageResId != View.NO_ID) {
                    imageView.setImageResource(failedImageResId);
                }
            }
        });
    }

    private String parseImageUri(String imageUrl) {
        String url;
        if(Uri.parse(imageUrl).getScheme() == null) {
          url = baseUrl + imageUrl;
        } else {
          url = imageUrl;
        }
        return url;
    }

    /**
     * Downland an image from specified url or return a cached copy if
     * it's already downloaded and set to ImageView.
     *
     * @param imageView imageView to set image to.
     * @param imageUrl a string containing image url.
     * @param loadingImageResId drawable resource ID to set while image is being downloaded.
     * @param failedImageResId drawable resource ID to set when image is not available.
     *
     * @return Downloaded or cached bitmap or null if image is not available
     */
    public synchronized void setImage(ImageView imageView, String imageUrl,
                                      int loadingImageResId, int failedImageResId) {

        if(imageView == null) {
            throw new IllegalArgumentException("ImageView is null");
        }

        Log.d(LOG_TAG, "Setting image: " + imageUrl);

        if(requestQueue.getCache().get(imageUrl) != null) {
            setFromCache(imageView, imageUrl);
        } else {
            download(imageView, imageUrl, loadingImageResId, failedImageResId);
        }

        imageView.invalidate();

    }

    private void setFromCache(ImageView imageView, String imageUrl) {

        Bitmap bitmap;

        Cache cache = requestQueue.getCache();

        if(cache instanceof BitmapLruCache) {
            bitmap = ((BitmapLruCache)cache).get(imageUrl);
        } else {
            Cache.Entry entry = cache.get(imageUrl);
            bitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
        }

        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Directly set image from image repository to {@link NetworkImageView}.
     *
     * @param imageView view that can set image directly from network.
     * @param imageUrl relative image URI in image repository.
     * @param placeholderResId placeholder image to set if download failed.
     */
    public synchronized void setImage(NetworkImageView imageView, String imageUrl, int placeholderResId) {

        imageView.setDefaultImageResId(placeholderResId);
        imageView.setImageUrl(parseImageUri(imageUrl), imageLoader);
    }

    /**
     * Cancel download request.
     *
     * @param imageUrl Image url as request tag
     */
    public void cancelDownload(String imageUrl) {
        requestQueue.cancelAll(imageUrl);
    }
}
