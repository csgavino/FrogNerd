package neo.com.frognerd.api;

import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

public abstract class BaseRequest<T> extends JsonRequest<T> {

    protected static final String MEDIA_TYPE_S3O = "application/json";
    protected static final String ACCEPT_HEADERS = "application/json";

    public static final String PATH_API = "api";

    private static final int DEFAULT_TIMEOUT_MS = 10000;
    private static final int DEFAULT_MAX_RETRIES = 0;

    private static final Uri BASE_URI = Uri.parse("http://10.0.1.99:4567");
//    private static final Uri BASE_URI = Uri.parse("http://10.0.1.100:4567"); /* carlos */

    public BaseRequest(int method,
                       String urlPath,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(method, urlPath, null, listener, errorListener);
    }

    public BaseRequest(int method,
                       String urlPath,
                       JSONObject requestBody,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, urlPath, (requestBody == null) ? null : requestBody.toString(), listener,
                errorListener);

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES,
                DEFAULT_BACKOFF_MULT));
    }

    public static String buildPath(String... parts) {
        StringBuilder pathBuilder = new StringBuilder();

        for (String part : parts) {
            pathBuilder.append(part).append("/");
        }

        return pathBuilder.toString();
    }

    protected abstract Response<T> parseJsonObject(NetworkResponse response, String jsonString);

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return parseJsonObject(response, jsonString);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public String getUrl() {
        final String path = super.getUrl();
        String asd = BASE_URI.buildUpon().encodedPath(path).toString();
        Log.d("TEST", asd);
        return asd;
    }

    protected Map<String, String> buildHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", ACCEPT_HEADERS);
        return headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(buildHeaders());
    }

    @Override
    public String getBodyContentType() {
        return MEDIA_TYPE_S3O;
    }

    public static class JSONObjectBuilder {
        private final JSONObject mObject = new JSONObject();

        public JSONObject build() {
            return mObject;
        }

        public JSONObjectBuilder put(String key, CharSequence value) {
            try {
                mObject.put(key, value.toString());
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid key/value pair", e);
            }
            return this;
        }

        public JSONObjectBuilder putOpt(String key, String value) {
            try {
                mObject.putOpt(key, value);
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid key/value pair", e);
            }
            return this;
        }

        public JSONObjectBuilder put(String key, int value) {
            try {
                mObject.put(key, value);
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid key/value pair", e);
            }
            return this;
        }

        public JSONObjectBuilder putOpt(String key, int value) {
            try {
                mObject.putOpt(key, value);
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid key/value pair", e);
            }
            return this;
        }

        public JSONObjectBuilder put(String key, boolean value) {
            try {
                mObject.put(key, value);
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid key", e);
            }
            return this;
        }
    }

}
