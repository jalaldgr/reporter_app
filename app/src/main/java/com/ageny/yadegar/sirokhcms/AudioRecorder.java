package com.ageny.yadegar.sirokhcms;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;

public class AudioRecorder {

    public final String path;
    public final Context cntx;
    public AudioRecorder(String path , Context context) {
        this.path = sanitizePath(path);
        this.cntx=context;
    }
     MediaRecorder recorder = new MediaRecorder();


    public String sanitizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".mp3";
        }
        return  path;
    }

    public void start() throws IOException {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted.  It is " + state
                    + ".");
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
        Log.d("hhh", "start: path record : "+path);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();

    }

    public void stop() throws IOException {

        try {
            recorder.stop();
            recorder.release();
        }catch (RuntimeException re){
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resum() throws IOException {

        try {
            recorder.resume();
        }catch (RuntimeException re){
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pause() throws IOException {

        try {
            recorder.pause();
        }catch (RuntimeException re){
        }

    }
    public void playarcoding(String path) throws IOException {
        MediaPlayer mp = new MediaPlayer();
        mp.setDataSource(path);
        mp.prepare();
        mp.start();
        mp.setVolume(10, 10);
    }
}

