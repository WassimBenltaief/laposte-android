package com.laposte.tn.activities;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.AlertDialog.Builder;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Document;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laposte.tn.model.Bureau;

public class MapActivity extends Activity {
	static final LatLng Menzah9 = new LatLng(36.846212, 10.151836);
	static final LatLng Hached = new LatLng(36.797047, 10.181834);
	static final LatLng Rades = new LatLng(36.76897, 10.272492);
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	private Bureau bureau = new Bureau();
	private String URL = "";
	private GoogleMap map;
	private ProgressDialog progressDialog;
	ActionBar mActionBar;
	Marker kiel2;
	Marker kiel;
	GMapV2Direction md = new GMapV2Direction();
	LatLng myLocation;
	LatLng toPosition;
	Document doc;
	PolylineOptions rectLine;
	String distanceText;
	String durationText;
	Polyline poyline;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// Activate Navigation Mode Tabs
			mActionBar = getSupportActionBar();
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setTitle("La Poste Tunisienne");
			mActionBar.setSubtitle("Map des bureaux ");

			setContentView(R.layout.layout_map);
			SupportMapFragment frg = new SupportMapFragment();
			frg = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			map = frg.getMap();

			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// MyLocation enable :
			map.setMyLocationEnabled(true);

			// MyLocation marker
			// LatLng myStartLocation = new
			// LatLng(map.getMyLocation().getLatitude(),
			// map.getMyLocation().getLongitude());

			map.setLocationSource(new CurrentLocationProvider(this));

			// Marker hamburg = map.addMarker(new
			// MarkerOptions().position(Menzah9)
			// .title("Manzah9"));
			kiel = map.addMarker(new MarkerOptions()
					.position(Menzah9).title("Menzah9")
					.snippet("Bureau de Poste"));

			kiel2 = map.addMarker(new MarkerOptions().position(Hached)
					.title("Hached").snippet("Bureau de Poste"));

			Marker kiel3 = map.addMarker(new MarkerOptions().position(Rades)
					.title("Rades").snippet("Bureau de Poste"));

			// Move the camera instantly to hamburg with a zoom of 15.
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Hached, 21));

			// Zoom in, animating the camera.
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

			// OnMarkerDragListener
			// OnInfoWindowClickListener

			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick(Marker marker) {

					progressDialog = new ProgressDialog(MapActivity.this,
							ProgressDialog.THEME_HOLO_LIGHT);
					progressDialog.setIndeterminate(false);
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(" Telechargement ...");
					progressDialog.show();
					Rechercher("hached");

				}
			});

			mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
					.getVolleyRequestQueue();
			URL = ((LaPosteTunisienne) getApplicationContext()).URL;

		

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
		JsonArrayRequest request = new JsonArrayRequest(URL + "bureau?bureau="
				+ numero, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jsonArray) {
				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				

					
							try {
								bureau= gson.fromJson(jsonArray
										.getJSONObject(0).toString(),
										Bureau.class);
								Log.d("bureau", jsonArray
										.getJSONObject(0).toString()+" ....... "+ bureau.toString());
							} catch (JsonSyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							LayoutInflater li = LayoutInflater.from(MapActivity.this);
							final View promptsView = li.inflate(R.layout.dialog_bureau , null);
							
							Builder alertDialogBuilder = new AlertDialog.Builder(
									MapActivity.this);

							  TextView bureau_nom= (TextView) promptsView.findViewById(R.id.dialog_bureau_nom);
							  TextView bureau_code= (TextView) promptsView.findViewById(R.id.dialog_bureau_code);
							  TextView bureau_horaire_ete= (TextView) promptsView.findViewById(R.id.dialog_bureau_horaire_ete);
							  TextView bureau_horaire_normal= (TextView) promptsView.findViewById(R.id.dialog_bureau_horaire_normal);
							  TextView bureau_horaire_ramadhan= (TextView) promptsView.findViewById(R.id.dialog_bureau_horaire_ramadhan);
							  TextView bureau_services= (TextView) promptsView.findViewById(R.id.dialog_bureau_services);
							  
							  
							  
							  bureau_nom.setText(bureau.getNom());
							  bureau_code.setText(bureau.getCode());
							  bureau_horaire_ete.setText(bureau.getHorairesete());
							  bureau_horaire_normal.setText(bureau.getHorairesnormal());
							  bureau_horaire_ramadhan.setText(bureau.getHorairesramadhan());
							  bureau_services.setText(bureau.getServices());
							
							
							alertDialogBuilder.setView(promptsView);
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							
							alertDialog.show();
							progressDialog.hide();
				
				
				
				
				
			}

			
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				
				//Pas de connexion disponible au serveur de la poste :
				progressDialog.hide();
				alerter(" ",
						"Pas de connexion disponible au serveur de la poste");
				

			}
		});
		request.setTag(this);
		mRequestQueue.add(request);
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.map_bureau_proche:
			
			if(map.getMyLocation()==null){
				Toast.makeText(this,"Impossible de récuperer votre position",
						Toast.LENGTH_LONG).show();
			}else{
			new LongOperation().execute();}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_map, menu);

		return super.onCreateOptionsMenu(menu);
	}

	
	
	private class LongOperation extends AsyncTask<Document, Void, Document> {

        protected Document doInBackground(Document... params) {
        	
        		
        		Document doc = md.getDocument(myLocation, toPosition,
    				GMapV2Direction.MODE_WALKING);

        		ArrayList<LatLng> directionPoint = md.getDirection(doc);
        		rectLine = new PolylineOptions().width(3).color(
        				Color.RED);
        		
        		
        		for (int i = 0; i < directionPoint.size(); i++) {
        			rectLine.add(directionPoint.get(i));
        		}
        		
        		
        		
              return doc;
        }      

        @Override
        protected void onPostExecute(Document doc) {
        	
        	
    		poyline = map.addPolyline(rectLine);

    		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(Hached, 13);

    		// map.moveCamera(cu);

    		map.animateCamera(cu);

    		kiel2.setSnippet("à " + md.getDistanceText(doc) + ", environ "
    				+ md.getDurationText(doc) + " à pieds.");
    		kiel2.showInfoWindow();
    		
    		progressDialog.dismiss();
    		//Log.d("duration", doc.getElementsByTagName("legs").toString());
              
        }

        @Override
        protected void onPreExecute() {
        	progressDialog = new ProgressDialog(MapActivity.this,
					ProgressDialog.THEME_HOLO_LIGHT);
			progressDialog.setIndeterminate(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Recherche ...");
			progressDialog.show();
        	myLocation = new LatLng(map.getMyLocation().getLatitude(), map
    				.getMyLocation().getLongitude());

    		// changer �a :
    		toPosition = new LatLng(36.797047, 10.181834);
    		if(poyline!=null){poyline.remove();}
    		md = new GMapV2Direction();

    		
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
        
        
        
  }   
	
	
	public void alerter(String titre, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MapActivity.this);

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

}