package uk.co.crystalcube.instagramfeeds.rest.events;

import org.androidannotations.annotations.EBean;

/**
 * <p>
 * A test event
 * </p>
 *
 * @author tanny
 *         Created: 03/03/2015.
 */
@EBean
public class FetchPopularMediaSuccess extends RestCallComplete {

    /**
     * Construct this event with success flag.
     */
    public FetchPopularMediaSuccess() {
        super(CallType.FETCH_POPULAR_MEDIA, true);
    }
}
