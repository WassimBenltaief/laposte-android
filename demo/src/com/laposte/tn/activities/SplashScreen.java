package com.laposte.tn.activities;

import org.holoeverywhere.app.Activity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {
	private long ms=0;
	private long splashTime=2500;
	private boolean splashActive = true;
	private boolean paused=false;
	private boolean firstResume =true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
	}
	
	@Override
	protected void onResume() {
		
		super.onRestart();
		if(firstResume==true){
		Thread mythread = new Thread() {
	    	public void run() {
	    		try {
	    			while (splashActive && ms < splashTime) {
	    				if(!paused)
	    					ms=ms+100;
	    				sleep(100);
	    			}
	    		} catch(Exception e) {}
	    		finally {
	    			firstResume=false;
	    			Intent intent = new Intent(SplashScreen.this, MainActivity.class);
	        		startActivity(intent);
	    		}
	        	}
	    };
	    mythread.start(); }
		else{
			Intent intent = new Intent(SplashScreen.this, MainActivity.class);
    		startActivity(intent);
		}
	}
	
	

}