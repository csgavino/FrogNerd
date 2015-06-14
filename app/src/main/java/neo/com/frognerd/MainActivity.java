package neo.com.frognerd;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.IOException;

import neo.com.frognerd.api.BaseRequest;
import neo.com.frognerd.model.Croak;
import neo.com.frognerd.model.Frog;

public class MainActivity extends Activity implements Response.Listener<Frog>, Response.ErrorListener {
    private static final String TAG = "MainActivity";
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private MediaPlayer mPlayer = null;
    private FrogRecorder mRecorder = null;

    private RequestQueue mRequestQueue;
    private RadioGroup mRadioGroup;

    private View.OnClickListener playCallback = new View.OnClickListener() {
        private boolean mStartPlaying = true;

        public void onClick(View view) {
            Button button = (Button) view;
            onPlay(mStartPlaying);

            if (mStartPlaying) {
                button.setText("Stop playing");
            } else {
                button.setText("Start playing");
            }

            mStartPlaying = !mStartPlaying;
        }
    };

    private View.OnClickListener recordCallback = new View.OnClickListener() {
        private boolean mStartRecording = true;

        public void onClick(View view) {
            Button button = (Button) view;
            onRecord(mStartRecording);
            if (mStartRecording) {
                button.setText("Stop recording");
            } else {
                button.setText("Start recording");
            }
            mStartRecording = !mStartRecording;
        }
    };

    private View.OnClickListener sendCallback = new View.OnClickListener() {
        public void onClick(View view) {
            BaseRequest<Frog> request = Croak.submitAudio(getOutputFile(),
                    MainActivity.this,
                    MainActivity.this);
            execute(request);
        }
    };

    private String getOutputFile() {
        int selectedRadio = mRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedRadio);
        return radioButton.getText().toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.start();

        ((Button) findViewById(R.id.playBtn)).setOnClickListener(playCallback);
        ((Button) findViewById(R.id.recordBtn)).setOnClickListener(recordCallback);
        ((Button) findViewById(R.id.sendBtn)).setOnClickListener(sendCallback);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        String[] radioBtns = new String[]{
                BaseRequest.buildPath(BASE_PATH, "three_gpp_aac_eld"),
                BaseRequest.buildPath(BASE_PATH, "mpeg_4_amr_mb"),
                BaseRequest.buildPath(BASE_PATH, "mpeg_4_amr_nb"), /* works */
                BaseRequest.buildPath(BASE_PATH, "mpeg_4_default"), /* works */
        };

        for (String radioBtnText : radioBtns) {
            RadioButton radioBtn = new RadioButton(this);
            radioBtn.setText(radioBtnText);
            mRadioGroup.addView(radioBtn);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(getOutputFile());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
// Okay
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_aac",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.AAC);

//        mRecorder = FrogRecorder.getInstance(
//                BaseRequest.buildPath(BASE_PATH, "three_gpp_aac_eld"),
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.AAC_ELD);
//
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_amr_nb",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.AMR_NB);
//
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_amr_wb",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.AMR_WB);
//
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_default",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.DEFAULT);
//
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_he_aac",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.HE_AAC);
//
//        mRecorder = FrogRecorder.getInstance(
//                "three_gpp_vorbis",
//                MediaRecorder.OutputFormat.THREE_GPP,
//                MediaRecorder.AudioEncoder.VORBIS);
//
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_aac",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.AAC);
//
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_aac_eld",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.AAC_ELD);

// Okay
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_amr_nb",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.AMR_NB);
// Okay
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_amr_wb",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.AMR_WB);
//
// Okay
        mRecorder = FrogRecorder.getInstance(
                "mpeg_4_default",
                MediaRecorder.OutputFormat.MPEG_4,
                MediaRecorder.AudioEncoder.DEFAULT);
//
// Doesn't work
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_he_aac",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.HE_AAC);
//
// Doesn't work
//        mRecorder = FrogRecorder.getInstance(
//                "mpeg_4_vorbis",
//                MediaRecorder.OutputFormat.MPEG_4,
//                MediaRecorder.AudioEncoder.VORBIS);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_aac",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.AAC);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_aac_eld",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.AAC_ELD);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_amr_nb",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.AMR_NB);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_amr_wb",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.AMR_WB);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_default",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.DEFAULT);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_he_aac",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.HE_AAC);
//
//        mRecorder = FrogRecorder.getInstance(
//                "webm_vorbis",
//                MediaRecorder.OutputFormat.WEBM,
//                MediaRecorder.AudioEncoder.VORBIS);


        try {
            mRecorder.start();
        } catch (IOException e) {
            Log.e(TAG, "Failed to start mediaRecorder", e);
        }

    }

    private void stopRecording() {
        mRecorder.cleanup();
        submitRecording();
    }

    private void submitRecording() {
        Log.d("TEST", "Submitting recording");
    }

    @Override
    public void onResponse(Frog frog) {
        Log.d("TEST", "===============");
        Log.d("TEST", "==== " + frog.getName() + "====");
        Log.d("TEST", "===============");

        Toast.makeText(this, frog.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("TEST", "===============");
        Log.d("TEST", "==== error ====");
        Log.d("TEST", "===============");
        Toast.makeText(this, "Failed to recognize croak.", Toast.LENGTH_LONG).show();
    }

    private void execute(BaseRequest request) {
        mRequestQueue.add(request);
    }
}