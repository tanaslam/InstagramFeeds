package uk.co.crystalcube.instagramfeeds.imagecache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * <p>
 * Utility cache class to store/get {@link android.graphics.Bitmap} downloaded through
 * {@link com.android.volley.toolbox.ImageLoader} .
 * </p>
 *
 * Created on 26/06/2014.
 *
 * @see android.util.LruCache
 *
 * @author aslamt
 */
public class BitmapLruCache extends LruCache<String, Bitmap>
        implements ImageLoader.ImageCache {

    //8MB default image cache in kilobytes
    private static final int MAX_CACHE_SIZE = 16 * 1024;

    /**
     * Construct object with default {@link BitmapLruCache.MAX_CACHE_SIZE} cache size
     */
    public BitmapLruCache() {
        super(MAX_CACHE_SIZE);
    }

    /**
     * Construct with give cache size in kilobytes.
     *
     * @param sizeInKiloBytes cache size in kilobytes.
     */
    public BitmapLruCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    /**
     * Get cached bitmap.
     *
     * @param key Image url as string.
     *
     * @return return Cached {@link android.graphics.Bitmap} object.
     */
    @Override
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    /**
     * Put bitmap in cache with given key.
     *
     * @param key image url as string.
     *
     * @param bitmap {@link android.graphics.Bitmap} to store in cache.
     */
    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        put(key, bitmap);
    }

    /**
     * Returns size of given bitmap store in cache.
     *
     * @param key image url as string.
     * @param value {@link android.graphics.Bitmap} to store in cache.
     *
     * @return Image size in kilobytes.
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
}
