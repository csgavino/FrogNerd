package neo.com.frognerd.model;

import android.util.Base64;
import android.util.Log;

import com.android.volley.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import neo.com.frognerd.api.BaseRequest;
import neo.com.frognerd.api.CroakRequest;

public class Croak {
    private final String mData;

    public Croak(String data) {
        mData = data;
    }

    public String getData() {
        return mData;
    }

    public static BaseRequest<Frog> submitAudio(String filename,
                                                Response.Listener<Frog> listener,
                                                Response.ErrorListener errorListener) {


        String base64String = readAndBase64Encode(filename);
        return new CroakRequest(new Croak(base64String), listener, errorListener);
    }

    private static String readAndBase64Encode(String filename) {
        try {
            File file = new File(filename);
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(buffer);
            fis.close();

            return Base64.encodeToString(buffer, Base64.DEFAULT);


//            String outputFileName;
//            outputFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//            outputFileName += "/something.txt";
//            FileOutputStream fos = new FileOutputStream(outputFileName);
//            fos.write(base64.getBytes());
//            fos.close();
//            Log.d("TEST", "something");
//            Croak croak = new Croak(base64);
//            return new CroakRequest(croak, listener, errorListener);
        } catch (IOException e) {
            Log.d("TEST", "Failed to encode: ", e);
            return null;
        }
    }
}
