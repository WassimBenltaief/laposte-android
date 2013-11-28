package com.laposte.tn.activities;

import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.laposte.tn.model.CCP;

public class CcpActivity extends SherlockActivity {

	// Declare Variables
	ActionBar mActionBar;
	private String URL;
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	CCP ccp = new CCP();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_ccp);

		setSupportProgressBarIndeterminateVisibility(false);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("La Poste Tunisienne");
		mActionBar.setSubtitle("Compte CCP");

		ccp = gson.fromJson(getIntent().getStringExtra("ccp"), CCP.class);

		TextView ccp_adresse = (TextView) findViewById(R.id.ccp_adresse);
		TextView ccp_cle = (TextView) findViewById(R.id.ccp_cle);
		TextView ccp_identifiant = (TextView) findViewById(R.id.ccp_identifiant);
		TextView ccp_nature = (TextView) findViewById(R.id.ccp_nature);
		TextView ccp_rip = (TextView) findViewById(R.id.ccp_rip);
		TextView ccp_solde = (TextView) findViewById(R.id.ccp_solde);
		TextView ccp_status = (TextView) findViewById(R.id.ccp_status);

		Log.d("ccp", ccp.toString());

		ccp_adresse.setText(ccp.getAdresse());
		ccp_cle.setText(String.valueOf(ccp.getCle()));
		ccp_identifiant.setText(String.valueOf(ccp.getIdentifiant()));
		ccp_nature.setText(String.valueOf(ccp.getNature()));
		ccp_rip.setText(String.valueOf(ccp.getRip()));
		ccp_status.setText(String.valueOf(ccp.getStatus()));
		ccp_solde.setText(String.valueOf(ccp.getSolde()) + " Dinars");

		// On r�cup�re notre RequestQueue et notre ImageLoader depuis notre
		// objet XebiaApplication

		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueueSsl();
		URL = ((LaPosteTunisienne) getApplicationContext()).URL_SSL;

		final ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);
		String ccpSharedPreference = (((LaPosteTunisienne) getApplicationContext())
				.getSharedPreferences("shared", 0).getString("updatedCCP", ""));
		if (ccpSharedPreference.equalsIgnoreCase("")) {
			button.setChecked(false);
		} else {
			button.setChecked(true);
		}
		
		// put ccp in shared preferences :
		SharedPreferences settings = ((LaPosteTunisienne) getApplicationContext())
				.getSharedPreferences("shared", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("updatedCCP", ccp.getIdentifiant()
				.toString());
		editor.putString("updatedCcpValue", Float.toString(ccp.getSolde()));
		editor.commit();
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ((button.getText().toString()).equalsIgnoreCase("activé")) {
					
					
					Intent intent = new Intent(getApplicationContext(),
							UpdaterService.class);
					
					startService(intent);
					
				} else {
//					SharedPreferences settings = ((LaPosteTunisienne) getApplicationContext())
//							.getSharedPreferences("shared", 0);
//					SharedPreferences.Editor editor = settings.edit();
//					editor.putString("updatedCCP", "");
//					editor.commit();
					stopService(new Intent(getApplicationContext(),
							UpdaterService.class));
				}
			}
		});

	}

	@Override
	protected void onStop() {
		mRequestQueue.cancelAll(this);
		super.onStop();
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
