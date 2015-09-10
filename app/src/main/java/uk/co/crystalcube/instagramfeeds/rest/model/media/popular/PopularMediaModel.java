
package uk.co.crystalcube.instagramfeeds.rest.model.media.popular;

import com.google.gson.annotations.Expose;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model extracted from:
 * GET /v1/media/popular?access_token=**** HTTP/1.1
 * Host: api.instagram.com
 * X-Target-URI: https://api.instagram.com
 */
@EBean(scope = EBean.Scope.Singleton)
public class PopularMediaModel {

    @Expose
    private Meta meta;
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * 
     * @return
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * 
     * @param meta
     *     The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public PopularMediaModel withMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public PopularMediaModel withData(List<Datum> data) {
        this.data = data;
        return this;
    }

}
