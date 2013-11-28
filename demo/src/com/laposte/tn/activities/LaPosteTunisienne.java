package com.laposte.tn.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.app.Application;
import org.holoeverywhere.preference.SharedPreferences;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.laposte.tn.model.CodePostal;
import com.laposte.tn.ssl.ExtHttpClientStack;
import com.laposte.tn.ssl.SslHttpClient;

public class LaPosteTunisienne extends Application {

	private RequestQueue mVolleyRequestQueue;
	private RequestQueue mVolleyRequestQueueSsl;
	private String[] code;
	private String[] commune;
	private String[] quartier;
	private String[] gouvernorat;
	public List<CodePostal> arraylist = new ArrayList<CodePostal>();
	public String URL = "http://10.42.0.1:8080/LaPosteProxy/resources/";
	public String URL_SSL = "https://10.42.0.1:8443/LaPosteProxy/resources/";
	public String login;
	public String password;
	public String updatedCCP;
	public String updatedCcpValue;
	public static final String PREFS_COUNT = "shared";

	@Override
	public void onCreate() {
		super.onCreate();

		// code

		code = getResources().getStringArray(R.array.codepostal);
		commune = getResources().getStringArray(R.array.commune);
		quartier = getResources().getStringArray(R.array.quartier);
		gouvernorat = getResources().getStringArray(R.array.gouvernorat);

		for (int i = 0; i < code.length; i++) {
			CodePostal cp = new CodePostal(code[i], commune[i], quartier[i],
					gouvernorat[i]);
			// Binds all strings into an array
			arraylist.add(cp);

		}

		InputStream keyStore = getResources().openRawResource(R.raw.my);
		// On initialise notre Thread-Pool
		mVolleyRequestQueueSsl = Volley.newRequestQueue(
				getApplicationContext(), new ExtHttpClientStack(
						new SslHttpClient(keyStore, "bousta123", 8443)));
		mVolleyRequestQueueSsl.start();

		mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mVolleyRequestQueue.start();

		// Restore preferences
		SharedPreferences settings = getSharedPreferences("PREFS_COUNT", 0);
		login = settings.getString("login", "");
		setLogin(login);
		password = settings.getString("password", "");
		setPassword(password);
		updatedCCP = settings.getString("updatedCCP", "");
		setUpdatedCCP(updatedCCP);
		updatedCcpValue = settings.getString("updatedCcpValue", "");
		setUpdatedCCP(updatedCcpValue);
	}

	public void setArraylist(List<CodePostal> arraylist) {
		this.arraylist = arraylist;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getUpdatedCCP() {
		return updatedCCP;
	}
	
	public void setUpdatedCCP(String updatedCCP) {
		this.updatedCCP = updatedCCP;
	}

	public RequestQueue getVolleyRequestQueue() {
		return mVolleyRequestQueue;
	}

	public RequestQueue getVolleyRequestQueueSsl() {
		return mVolleyRequestQueueSsl;
	}

	@Override
	public void onTerminate() {
		mVolleyRequestQueue.stop();
		super.onTerminate();
	}
}