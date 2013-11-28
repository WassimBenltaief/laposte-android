package com.laposte.tn.activities;

import java.util.ArrayList;
import java.util.List;




import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.json.JSONArray;
import org.json.JSONException;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import com.laposte.tn.business.RapidPostAdapter;
import com.laposte.tn.model.Colis;
import com.laposte.tn.model.RapidPost;

public class SuiviActivity extends Activity {

	// Declare Variables
	ActionBar mActionBar;
	private String URL;
	private RequestQueue mRequestQueue;

	private Gson gson = new Gson();
	public String type;

	RapidPostAdapter rapidpostAdapter;
	private List<RapidPost> rapidPostList = new ArrayList<RapidPost>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recherche);

		setSupportProgressBarIndeterminateVisibility(false);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("La Poste Tunisienne");
		mActionBar.setSubtitle("Rechercher "
				+ getIntent().getStringExtra("type"));

		// LinearLayout ll= (LinearLayout) findViewById(R.id.layout_resultat);
		// ll.setVisibility(View.INVISIBLE);

		// On r�cup�re notre RequestQueue et notre ImageLoader depuis notre
		// objet XebiaApplication

		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueue();
		URL = ((LaPosteTunisienne) getApplicationContext()).URL;
		// list

		ListView list = (ListView) findViewById(R.id.listColis);
		



		// Pass results to ListViewAdapter Class
		rapidpostAdapter = new RapidPostAdapter(getApplicationContext(),
				rapidPostList);

		// Binds the Adapter to the ListView
		list.setAdapter(rapidpostAdapter);

	}

	@Override
	protected void onStop() {
		mRequestQueue.cancelAll(this);
		super.onStop();
	}

	public void Rechercher(String numero) {
		type = getIntent().getStringExtra("type");
		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray
		JsonArrayRequest request = new JsonArrayRequest(URL + type + "?"
				+ type + "=" + numero, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jsonArray) {
				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				LinearLayout ll = (LinearLayout) findViewById(R.id.layout_resultat);
				ll.setVisibility(View.VISIBLE);
				rapidPostList.clear();

				if (jsonArray.isNull(0)) {

					alerter(" ", "Pas de Rapid Post � ce numero");

				} else {

					int len = jsonArray.length();
					for (int i = 0; i < len; i++) {

						try {
							rapidPostList.add(gson.fromJson(jsonArray
									.getJSONObject(i).toString(),
									RapidPost.class));
						} catch (JsonSyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

				setSupportProgressBarIndeterminateVisibility(false);
				// Update Adapter
				rapidpostAdapter.filter();

			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				alerter(" Oups",
						"Pas de connexion disponible au serveur de la poste");
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
        searchView.setQueryHint("Num° ..");
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
				SuiviActivity.this);

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

		/*
		 * 
		 * if((rapidPost.getId()).equalsIgnoreCase("0")){
		 * 
		 * Toast.makeText(SuiviActivity.this, "Pas de "+type+" � ce numero ! ",
		 * Toast.LENGTH_LONG).show(); LinearLayout ll= (LinearLayout)
		 * findViewById(R.id.layout_resultat); ll.setVisibility(View.INVISIBLE);
		 * } else{ LinearLayout ll= (LinearLayout)
		 * findViewById(R.id.layout_resultat); ll.setVisibility(View.VISIBLE);
		 * 
		 * TextView idRecherche= (TextView) findViewById(R.id.idRecherche);
		 * TextView dateRecherche= (TextView) findViewById(R.id.dateRecherche);
		 * TextView etatRecherche= (TextView) findViewById(R.id.etatRecherche);
		 * TextView noteRecherche= (TextView) findViewById(R.id.noteRecherche);
		 * 
		 * idRecherche.setText(type+" num� : "+rapidPost.getId());
		 * dateRecherche.setText("A la date : "+rapidPost.getDate());
		 * etatRecherche.setText("Etat : "+rapidPost.getEtat());
		 * noteRecherche.setText("Notes : "+rapidPost.getNotes());
		 * 
		 * Log.d("rechercher", "Numero : "+rapidPost.getId());
		 * setSupportProgressBarIndeterminateVisibility(false); }
		 */
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
