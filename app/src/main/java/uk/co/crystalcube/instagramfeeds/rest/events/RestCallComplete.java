package uk.co.crystalcube.instagramfeeds.rest.events;

/**
 * <p>
 * Abstract base class to wrap rest event common functions.
 * </p>
 *
 * @author: tanny
 * Created: 08/09/15.
 */
public class RestCallComplete {

    /**
     * REST End-point call types
     */
    public enum CallType {
        UNKNOWN,
        FETCH_POPULAR_MEDIA
    }

    private CallType callType;
    private boolean Success;

    public RestCallComplete(CallType callType, boolean success) {
        this.callType = callType;
        Success = success;
    }

    public CallType getCallType() {
        return callType;
    }

    public boolean isSuccess() {
        return Success;
    }
}
