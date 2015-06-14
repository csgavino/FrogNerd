package neo.com.frognerd;

import android.media.MediaRecorder;

import java.io.IOException;

public class FrogRecorder {

    private MediaRecorder mRecorder = null;
    private String mOutputFile;
    private int mOutputFormat;
    private int mAudioEncoder;

    public FrogRecorder(MediaRecorder mediaRecorder, String outputFile, int outputFormat, int audioEncoder) {
        mRecorder = mediaRecorder;
        mOutputFile = outputFile;
        mOutputFormat = outputFormat;
        mAudioEncoder = audioEncoder;
    }

    public static FrogRecorder getInstance(String outputFile, int outputFormat, int audioEncoder) {
        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setAudioSamplingRate(44);
        mediaRecorder.setAudioChannels(2);
        mediaRecorder.setAudioEncodingBitRate(192);

        mediaRecorder.setOutputFormat(outputFormat);
        mediaRecorder.setOutputFile(outputFile);
        mediaRecorder.setAudioEncoder(audioEncoder);


        return new FrogRecorder(mediaRecorder, outputFile, outputFormat, audioEncoder);
    }

    public MediaRecorder getMediaRecorder() {
        return mRecorder;
    }

    public String getOutputFile() {
        return mOutputFile;
    }

    public int getOutputFormat() {
        return mOutputFormat;
    }

    public int getAudioEncoder() {
        return mAudioEncoder;
    }

    public void start() throws IOException {
        mRecorder.prepare();
        mRecorder.start();
    }

    public void cleanup() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void release() {
        mRecorder.release();
    }
}
