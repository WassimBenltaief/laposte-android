package com.laposte.tn.activities;


import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.preference.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.laposte.tn.dialogs.FragmentDialogPaiement;
import com.laposte.tn.dialogs.FragmentDialogSupport;
import com.laposte.tn.model.Bureau;
import com.laposte.tn.model.User;

public class FragmentTab2 extends SherlockFragment {
	
	private RequestQueue mRequestQueue;
	private Gson gson = new Gson();
	private String URL;
	private ProgressDialog progressDialog;
	private EditText username;
	private EditText password;
	private AlertDialog alertDialog;
	private TextView connexionError;
	private AlertDialog.Builder alertDialogBuilder;
	private LinearLayout trackAndTrace;
	private String reponse;
		
	@Override
	public SherlockFragmentActivity getSherlockActivity() {
		return super.getSherlockActivity();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.fragmenttab2, container, false);
		
		// Volley
		
		mRequestQueue = ((LaPosteTunisienne) getSherlockActivity().getApplicationContext())
				.getVolleyRequestQueueSsl();
		URL = ((LaPosteTunisienne) getSherlockActivity().getApplicationContext()).URL_SSL;
		
		
		//CCP
		trackAndTrace = (LinearLayout) view.findViewById(R.id.consultation);
		trackAndTrace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(getSherlockActivity());
				final View promptsView = li.inflate(R.layout.dialog_signin , null);
 
				alertDialogBuilder = new AlertDialog.Builder(
						getSherlockActivity());
 
				connexionError = (TextView) promptsView.findViewById(R.id.connexion_error);
				connexionError.setVisibility(TextView.GONE);
				
				username = (EditText) promptsView.findViewById(R.id.username);
				password = (EditText) promptsView.findViewById(R.id.password);
				
				username.setText(((LaPosteTunisienne) getSherlockActivity().getApplicationContext()).getSharedPreferences("shared", 0).getString("login", ""));
				password.setText(((LaPosteTunisienne) getSherlockActivity().getApplicationContext()).getSharedPreferences("shared", 0).getString("password", ""));
				
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
				alertDialogBuilder.setTitle("Connexion").setIcon(getSherlockActivity().getResources().getDrawable(R.drawable.ic_action_secure));
				
 

				
				
				
 
				// set dialog message
				alertDialogBuilder.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						// save login state :
						
						
						
						SharedPreferences settings = ((LaPosteTunisienne) getSherlockActivity().getApplicationContext()).getSharedPreferences("shared", 0);
					    SharedPreferences.Editor editor = settings.edit();
					    editor.putString("login", username.getText().toString());
					    editor.putString("password", password.getText().toString());
					    // Commit the edits!
					    editor.commit();
						
						
						
						
						progressDialog = new ProgressDialog(getSherlockActivity(), ProgressDialog.THEME_HOLO_LIGHT);
						progressDialog.setIndeterminate(true);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.setMessage("Connexion en cours ...");
						progressDialog.show();
						try {
							Rechercher(username.getText().toString(), password.getText().toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
					}
				});
 
				// create alert dialog
				alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
				
			}
		});
		
		// Change
		LinearLayout change = (LinearLayout) view.findViewById(R.id.change);
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ChangeActivity.class); 
				
				startActivity(i); 
				
			}
		});
		
		LinearLayout paiement = (LinearLayout) view
				.findViewById(R.id.paiement);
		paiement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentDialogPaiement newFragment = new FragmentDialogPaiement();
				newFragment.show(getFragmentManager(), "TAG");				
			}

		});
		return view;
	}
	
	
	public void Rechercher (String username, String password) throws JSONException{
		
		
		JSONObject jsonToPost= new JSONObject();
		jsonToPost.put("password", password);
		jsonToPost.put("username", username);

	        

	       
		
		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray
		
		

		
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL+"authenticate", jsonToPost, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				
				
				String ccp = null;
				try {
					ccp = jsonObject.getString("ccp");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				
				
				if (jsonObject.isNull("ccp")) {

					// Echec de connexion :
					progressDialog.cancel();
					alertDialog.show();
					connexionError.setVisibility(TextView.VISIBLE);
					

				} else {
					
					RechercherCCp(ccp);
					
					
					

				}
					

				




			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				// Echec de connexion avec le serveur
				progressDialog.cancel();
				alertDialog.show();
				connexionError.setVisibility(TextView.VISIBLE);
				connexionError.setText("Impossible de se connecter au serveur de la poste, réessayez plus tard !");
				
			}
		});
	
		request.setTag(this);
		mRequestQueue.add(request);	
		
		
		
		
	}
	
public void RechercherCCp(String numero) {
		
	
		
		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray
		JsonObjectRequest request = new JsonObjectRequest (URL+"ccp?ccp="+numero, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				// Ce code est appel� quand la requ�te r�ussi. �tant ici dans le
				// thread principal, on va pouvoir mettre � jour notre Adapter

				
				reponse= jsonObject.toString(); 
				progressDialog.cancel();
				Intent i = new Intent(getActivity(), CcpActivity.class); 
				
				i.putExtra("ccp", reponse);
				startActivity(i); 



			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				
			}
		});
		request.setTag(this);
		mRequestQueue.add(request);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	public void onStop() {
		mRequestQueue.cancelAll(this);
		super.onStop();
	}
	
}
