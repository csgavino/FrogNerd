package neo.com.frognerd.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import neo.com.frognerd.model.Croak;
import neo.com.frognerd.model.Frog;

public class CroakRequest extends BaseRequest<Frog> {
    public CroakRequest(Croak croak,
                        Response.Listener<Frog> listener,
                        Response.ErrorListener errorListener) {
        super(Method.POST,
                buildPath(PATH_API, "find_frog"),
                new JSONObjectBuilder()
                        .putOpt("data", croak.getData())
                        .build(),
                listener, errorListener);
    }

    @Override
    protected Response<Frog> parseJsonObject(NetworkResponse response, String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Frog frog = Frog.fromJsonObject(jsonObject);
            return Response.success(frog, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
