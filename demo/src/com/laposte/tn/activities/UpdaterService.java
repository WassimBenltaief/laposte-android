package com.laposte.tn.activities;

import java.util.Calendar;

import org.holoeverywhere.preference.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class UpdaterService extends Service {

	private static final String ALARM_REFRESH_ACTION = "it.trento.alchemiasoft.casagranda.simone.alarmmanagertutorial.ALARM_REFRESH_ACTION";
	private static final int DEFAULT_START_DELAY = 1000;
	private static final long DEFAULT_INTERVAL_DELAY = 10000;
	private static final int ALARM_CODE = 20;
	private BroadcastReceiver alarmReceiver;
	private PendingIntent pendingIntent;
	private AlarmManager alarmManager;

	private static final String TAG = "UpdaterService";
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	private String URL;
	private String ccp;
	private String ccpValue;
	private static String returnedCcp ="";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueueSsl();
		URL = ((LaPosteTunisienne) getApplicationContext()).URL_SSL;
		Log.d(TAG, "onCreate");
		ccp = (((LaPosteTunisienne) getApplicationContext())
				.getSharedPreferences("shared", 0).getString("updatedCCP", ""));
		ccpValue = (((LaPosteTunisienne) getApplicationContext())
				.getSharedPreferences("shared", 0).getString("updatedCcpValue",
				""));

		// We get the AlarmManager
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// We prepare the pendingIntent for the AlarmManager
		Intent intent = new Intent(ALARM_REFRESH_ACTION);
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		// We create and register a broadcast receiver for alarms
		alarmReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				// We notify to handler the arrive of alarm
				Message msg = myHandler.obtainMessage(ALARM_CODE, intent);
				myHandler.sendMessage(msg);
			}
		};
		// We register dataReceiver to listen ALARM_REFRESH_ACTION
		IntentFilter filter = new IntentFilter(ALARM_REFRESH_ACTION);
		registerReceiver(alarmReceiver, filter);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		startRepeating();
		Toast.makeText(this, "Le service de notification est activé.",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		// Note: You can start a new thread and use it for long background
		// processing from here.
	}

	@Override
	public void onDestroy() {
		stopAlarms();
		Toast.makeText(this, "Le service de notification est désactivé.",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ALARM_CODE:

				Rechercher(ccp);

				break;
			default:
				break;
			}
		}
	};

	public void startRepeating() {
		// We get value for repeating alarm
		int startTime = DEFAULT_START_DELAY;
		long intervals = DEFAULT_INTERVAL_DELAY;
		// We have to register to AlarmManager
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MILLISECOND, startTime);
		// We set a repeating alarm
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), intervals, pendingIntent);
	}

	public void stopAlarms() {
		// We cancel alarms that matches with pending intent
		alarmManager.cancel(pendingIntent);
		unregisterReceiver(alarmReceiver);
		Toast.makeText(this, "Alarms stopped", Toast.LENGTH_SHORT).show();
	}

	public void Rechercher(String numero) {

		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray
		JsonObjectRequest request = new JsonObjectRequest(URL + "ccp?ccp="
				+ numero, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {

				try {

					returnedCcp = jsonObject.get("solde").toString();
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (Float.parseFloat(returnedCcp) != Float.parseFloat(ccpValue)) {
					
					createNotification(returnedCcp);
					ccpValue = returnedCcp;
					SharedPreferences settings = ((LaPosteTunisienne) getApplicationContext())
							.getSharedPreferences("shared", 0);
					SharedPreferences.Editor editor = settings.edit();
					
					editor.putString("updatedCcpValue", returnedCcp);
					editor.commit();
				}
				

			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				Toast.makeText(UpdaterService.this,
						"Erreur : " + volleyError.getMessage(),
						Toast.LENGTH_SHORT).show();
				// alerter(" ",
				// "Pas de connexion disponible au serveur de la poste");

			}
		});
		request.setTag(this);
		mRequestQueue.add(request);
	}

	public void createNotification(String solde) {
	
			
			
		
		Intent intent = new Intent(this, UpdaterService.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this)
        		.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Mise à jour de votre compte CCP")
                .setContentText("Solde = "+solde+" dt");
        // Creates an explicit intent for an Activity in your app
        
       

        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(100, mBuilder.build());

	}

}