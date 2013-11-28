package com.laposte.tn.activities;


import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laposte.tn.model.Bureau;

public class BureauActivity extends Activity {

	// Declare Variables
	ActionBar mActionBar;
	private String URL;
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	Bureau bureau = new Bureau();



	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_bureau);

		setSupportProgressBarIndeterminateVisibility(false);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("Bureaux");
		

		LinearLayout ll= (LinearLayout) findViewById(R.id.layout_bureau_resultat);
		ll.setVisibility(View.INVISIBLE);

		// On r�cup�re notre RequestQueue et notre ImageLoader depuis notre
		// objet XebiaApplication

		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueue();
		URL=((LaPosteTunisienne) getApplicationContext()).URL;
		


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
		JsonArrayRequest request = new JsonArrayRequest (URL+"bureau?bureau="+numero, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jsonArray) {
				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				

				LinearLayout ll= (LinearLayout)findViewById(R.id.layout_bureau_resultat); 
				ll.setVisibility(View.INVISIBLE);
				
				if (jsonArray.isNull(0)) {

					alerter(" ", "Aucun bureau � ce nom !");

				} else {

					
							try {
								bureau= gson.fromJson(jsonArray
										.getJSONObject(0).toString(),
										Bureau.class);
							} catch (JsonSyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							fillOnViewRapidPost();

					

				}

				setSupportProgressBarIndeterminateVisibility(false);



			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				alerter(" ",
						"Pas de connexion disponible au serveur de la poste");
				LinearLayout ll= (LinearLayout)findViewById(R.id.layout_bureau_resultat); 
				ll.setVisibility(View.INVISIBLE);
				setSupportProgressBarIndeterminateVisibility(false);
			}
		});
		request.setTag(this);
		mRequestQueue.add(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final SearchView searchView = new
                SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("ville/commune..");
        searchView.setIconified(false);
        
        MenuItem menu1=menu.add("");
        		
                menu1.setIcon(R.drawable.ic_action_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu1.expandActionView();
                                

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String newText) {
				// On ajoute la Request au RequestQueue pour la lancer
				setSupportProgress(Window.PROGRESS_END);
				setSupportProgressBarIndeterminateVisibility(true);

				Rechercher(newText);
				searchView.setIconified(true);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return true;
	}

	public void alerter(String titre, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				BureauActivity.this);

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

	public void fillOnViewRapidPost() {

		
		  
		  
		  
		  
		  
		  LinearLayout ll= (LinearLayout)findViewById(R.id.layout_bureau_resultat); 
		  ll.setVisibility(View.VISIBLE);
		  
		  TextView bureau_nom= (TextView) findViewById(R.id.dialog_bureau_nom);
		  TextView bureau_code= (TextView) findViewById(R.id.dialog_bureau_code);
		  TextView bureau_horaire_ete= (TextView) findViewById(R.id.dialog_bureau_horaire_ete);
		  TextView bureau_horaire_normal= (TextView) findViewById(R.id.dialog_bureau_horaire_normal);
		  TextView bureau_horaire_ramadhan= (TextView) findViewById(R.id.dialog_bureau_horaire_ramadhan);
		  TextView bureau_services= (TextView) findViewById(R.id.dialog_bureau_services);
		  
		  
		  
		  bureau_nom.setText(bureau.getNom());
		  bureau_code.setText(bureau.getCode());
		  bureau_horaire_ete.setText(bureau.getHorairesete());
		  bureau_horaire_normal.setText(bureau.getHorairesnormal());
		  bureau_horaire_ramadhan.setText(bureau.getHorairesramadhan());
		  bureau_services.setText(bureau.getServices());
		  
		  
		  
		 
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
