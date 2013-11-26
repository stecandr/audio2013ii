package com.example.activity_audio_app;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageManager;


public class MainActivity extends Activity {
	private static MediaRecorder mediaRecorder;
	private static MediaPlayer mediaPlayer;

	private static String audioFilePath;
	private static Button stopButton;
	private static Button playButton;
	private static Button recordButton;
	
	private boolean isRecording = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recordButton = (Button) findViewById(R.id.RecordButton);
		playButton = (Button) findViewById(R.id.playButton);
		stopButton = (Button) findViewById(R.id.stopButton);
	
		if (!hasMicrophone())
		{
			stopButton.setEnabled(false);
			playButton.setEnabled(false);
			recordButton.setEnabled(false);
		} else {
			playButton.setEnabled(false);
			stopButton.setEnabled(false);
		}
		
		audioFilePath = 
             Environment.getExternalStorageDirectory().getAbsolutePath() 
                 + "/myaudio.3gp";	
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    protected boolean hasMicrophone() {
		PackageManager pmanager = this.getPackageManager();
		return pmanager.hasSystemFeature(
               PackageManager.FEATURE_MICROPHONE);
	}
    
    
    public void RecordAudio (View view) throws IOException
    {
       isRecording = true;
       stopButton.setEnabled(true);
       playButton.setEnabled(false);
       recordButton.setEnabled(false);
    	   
       try {
         mediaRecorder = new MediaRecorder();
         mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
         mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
         mediaRecorder.setOutputFile(audioFilePath);
         mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
         mediaRecorder.prepare();
       } catch (Exception e) {
    	   e.printStackTrace();
       }

       mediaRecorder.start();			
    }
    
    public void StopClicked (View view)
    {
    		
    	stopButton.setEnabled(false);
    	playButton.setEnabled(true);
    		
    	if (isRecording)
    	{	
    		recordButton.setEnabled(false);
    		mediaRecorder.stop();
    		mediaRecorder.release();
    		mediaRecorder = null;
    		isRecording = false;
    	} else {
    		mediaPlayer.release();
    	        mediaPlayer = null;
    		recordButton.setEnabled(true);
    	}
    }
    
    public void playAudio (View view) throws IOException
    {
    	playButton.setEnabled(false);
    	recordButton.setEnabled(false);
    	stopButton.setEnabled(true);

    	mediaPlayer = new MediaPlayer();
    	mediaPlayer.setDataSource(audioFilePath);
    	mediaPlayer.prepare();
    	mediaPlayer.start();
    }
    
}
