package neo.com.frognerd;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.IOException;

import neo.com.frognerd.api.BaseRequest;

public class FrogRecorder {

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private MediaRecorder mRecorder = null;
    private String mFileName;
    private int mOutputFormat;
    private int mAudioEncoder;

    public FrogRecorder(MediaRecorder mediaRecorder, String fileName, int outputFormat, int audioEncoder) {
        mRecorder = mediaRecorder;
        mFileName = fileName;
        mOutputFormat = outputFormat;
        mAudioEncoder = audioEncoder;
    }

    public static FrogRecorder getInstance(String fileName, int outputFormat, int audioEncoder) {
        MediaRecorder mediaRecorder = getMediaRecorder(fileName, outputFormat, audioEncoder);

        return new FrogRecorder(mediaRecorder, fileName, outputFormat, audioEncoder);
    }

    public String getOutputFile() {
        return outputFile(mFileName);
    }

    public void start() throws IOException {
        mRecorder.prepare();
        mRecorder.start();
    }

    public void cleanup() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = getMediaRecorder(mFileName, mOutputFormat, mAudioEncoder);
    }

    public void release() {
        mRecorder.release();
    }

    private static String outputFile(String fileName) {
        return BaseRequest.buildPath(BASE_PATH, fileName);
    }

    private static MediaRecorder getMediaRecorder(String fileName, int outputFormat, int audioEncoder) {
        int audioSource = MediaRecorder.AudioSource.MIC;
        int samplingRate = 44100;
        int audioChannels = 2;
        int bitRate = 192;

        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(audioSource);
        mediaRecorder.setAudioSamplingRate(samplingRate);
        mediaRecorder.setAudioChannels(audioChannels);
        mediaRecorder.setAudioEncodingBitRate(bitRate);

        mediaRecorder.setOutputFormat(outputFormat);

        mediaRecorder.setOutputFile(outputFile(fileName));
        mediaRecorder.setAudioEncoder(audioEncoder);

        return mediaRecorder;
    }
}
