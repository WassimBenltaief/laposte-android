package com.laposte.tn.activities;


import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;


import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laposte.tn.model.Bureau;

public class ChangeActivity extends Activity {

	// Declare Variables
	ActionBar mActionBar;
	private String URL;
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	Bureau bureau = new Bureau();
	String cours = "";
	Spinner spinnerA;
	Spinner spinnerDe;
	EditText edittext;
	String somme = "";
	String de ="";
	String a= "";



	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_change);

		setSupportProgressBarIndeterminateVisibility(false);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("Cours du jour");
		
		
		//Spinner 1
		spinnerDe = (Spinner) findViewById(R.id.change_from);
		spinnerA = (Spinner) findViewById(R.id.change_to);
		spinnerDe.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				de = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spinnerA.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				a = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton inverserButton = (ImageButton) findViewById(R.id.change_inverser);
		inverserButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int aux = spinnerA.getSelectedItemPosition();
				spinnerA.setSelection(spinnerDe.getSelectedItemPosition());
				spinnerDe.setSelection(aux);
				
			}
		});
		

		
		LinearLayout ll= (LinearLayout) findViewById(R.id.layout_change_result);
		ll.setVisibility(View.INVISIBLE);

		// On r�cup�re notre RequestQueue et notre ImageLoader depuis notre
		// objet XebiaApplication

		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueue();
		URL=((LaPosteTunisienne) getApplicationContext()).URL;
		
		edittext = (org.holoeverywhere.widget.EditText) findViewById(R.id.change_somme);
		
		Button buttonConvert =  (Button) findViewById(R.id.change_convertir);
		buttonConvert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setSupportProgressBarIndeterminateVisibility(true);
				
	        	Rechercher(edittext.getText().toString());
				
			}
		});
		
		


	}

	@Override
	protected void onStop() {
		mRequestQueue.cancelAll(this);
		super.onStop();
	}

	
	public void Rechercher(String numero) {
		
		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray
		
		

		
		JsonArrayRequest request = new JsonArrayRequest (URL+"convert?de="+de
															+"&a="+a
															+"&somme="+edittext.getText().toString(),
															new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jsonArray) {
				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				
				
				
				
				if (jsonArray.isNull(0)) {

					alerter(" ", "Erreur de conversion !");

				} else {

					
							try {
								somme= jsonArray
										.getString(0);
								cours= jsonArray
										.getString(1);
								Log.d("rechercher", somme+" "+cours);
								
							} catch (JsonSyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							fillOnView();

					

				}

				setSupportProgressBarIndeterminateVisibility(false);



			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				alerter(" Oups",
						"Pas de connexion disponible au serveur de la poste");
				LinearLayout ll= (LinearLayout)findViewById(R.id.layout_change_result); 
				ll.setVisibility(View.INVISIBLE);
				setSupportProgressBarIndeterminateVisibility(false);
			}
		});
		request.setTag(this);
		mRequestQueue.add(request);
	}
	

	
	

	
	public void alerter(String titre, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				ChangeActivity.this);

		// set title
		alertDialogBuilder.setTitle(titre);
		alertDialogBuilder.setIcon(R.drawable.ic_action_error);

		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(true);

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	

	public void fillOnView() {

		
		  
		  
		  
		  
		  
		  LinearLayout ll= (LinearLayout)findViewById(R.id.layout_change_result); 
		  ll.setVisibility(View.VISIBLE);
		  
		  TextView change_result= (TextView) findViewById(R.id.change_result);
		  TextView change_cours= (TextView) findViewById(R.id.change_cours);
		  
		  
		  // de : monnai de 
		  // a : monnai �
		  // somme : resultat de convesion
		  // cours : cour du jours
		  change_cours.setText("Cours du jour : " + cours);
		  change_result.setText(somme+" "+a.toLowerCase()+"s");
		  
		  
		  
		  
		 
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
