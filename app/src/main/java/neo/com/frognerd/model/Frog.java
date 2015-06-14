package neo.com.frognerd.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Frog {
    private final String mName;

    public Frog(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public static Frog fromJsonObject(JSONObject jsonObject) throws JSONException {
        return new Frog(jsonObject.getString("name"));
    }

}
