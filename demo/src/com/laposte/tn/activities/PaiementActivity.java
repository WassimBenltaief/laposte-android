package com.laposte.tn.activities;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.internal.em;
import com.google.gson.Gson;
import com.laposte.tn.business.MyPagerAdapter;
import com.laposte.tn.business.NonSwipeableViewPager;
import com.laposte.tn.model.Carte;

public class PaiementActivity extends SherlockFragmentActivity {

	ActionBar mActionBar;
	private PagerAdapter mPagerAdapter;
	private NonSwipeableViewPager pager;
	private String URL;
	private RequestQueue mRequestQueue;
	private ProgressDialog progressDialog;
	private Carte carte;
	private EditText montant;
	private JSONObject jsonPaiement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_paiement);

		// mRequestQueue
		mRequestQueue = ((LaPosteTunisienne) getApplicationContext())
				.getVolleyRequestQueueSsl();
		URL = ((LaPosteTunisienne) getApplicationContext()).URL_SSL;

		String type = getIntent().getStringExtra("type");

		// Activate Navigation Mode Tabs
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("Facture");
		mActionBar.setSubtitle(type);

		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyPagerAdapter(
				super.getSupportFragmentManager());

		pager = (NonSwipeableViewPager) super
				.findViewById(R.id.viewpagerFacture);
		pager.setOffscreenPageLimit(4);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.mPagerAdapter);

		final Button buttonRetour = (Button) findViewById(R.id.button_retour);
		final Button buttonAvancer = (Button) findViewById(R.id.button_avancer);
		final TextView titre = (TextView) findViewById(R.id.titre);
		final View step1 = (View) findViewById(R.id.step1);
		final View step2 = (View) findViewById(R.id.step2);
		final View step3 = (View) findViewById(R.id.step3);
		final View step4 = (View) findViewById(R.id.step4);

		final Drawable drawButtonAvancer = buttonAvancer.getBackground();
		buttonRetour.setText("");

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onPageSelected(int position) {

				switch (position) {
				case 0:
					step1.setBackgroundColor(getResources().getColor(
							R.color.Blue));
					step2.setBackgroundColor(getResources().getColor(
							R.color.GrisTransparent));
					buttonRetour.setEnabled(false);
					buttonRetour.setText("");
					break;
				case 1:

					step1.setBackgroundColor(getResources().getColor(
							R.color.BlueTransparent));
					step2.setBackgroundColor(getResources().getColor(
							R.color.Blue));
					step3.setBackgroundColor(getResources().getColor(
							R.color.GrisTransparent));
					buttonRetour.setEnabled(true);
					buttonRetour.setText("Retour");
					buttonAvancer.setText("Avancer");
					titre.setText("Formulaire");
					break;
				case 2:
					step2.setBackgroundColor(getResources().getColor(
							R.color.BlueTransparent));
					step3.setBackgroundColor(getResources().getColor(
							R.color.Blue));
					step4.setBackgroundColor(getResources().getColor(
							R.color.GrisTransparent));
					titre.setText("Verification du paiement");
					titre.setTextColor(getResources().getColor(
							R.color.Blue_holo));
					buttonAvancer.setText("Vérifier");
					buttonAvancer.setTextColor(getResources().getColor(
							R.color.Black));
					buttonAvancer.setBackgroundDrawable(drawButtonAvancer);
					break;
				case 3:

					retreaveFragmentsInfos();

					step3.setBackgroundColor(getResources().getColor(
							R.color.BlueTransparent));
					step4.setBackgroundColor(getResources().getColor(
							R.color.Green));
					titre.setText("Validation");
					titre.setTextColor(getResources().getColor(R.color.Green));
					buttonAvancer.setText("Payer");
					buttonAvancer.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.layout_focus_button));
					buttonAvancer.setTextColor(getResources().getColor(
							R.color.White));
					step3.setBackgroundColor(getResources().getColor(
							R.color.BlueTransparent));
					step4.setBackgroundColor(getResources().getColor(
							R.color.Green));

					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

		buttonAvancer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (pager.getCurrentItem()) {
				case 0:
					pager.setCurrentItem(pager.getCurrentItem() + 1, true);
					break;
				case 1:
					pager.setCurrentItem(pager.getCurrentItem() + 1, true);
					break;
				case 2:

					// spinner
					final TextView mySpinner = (TextView) pager
							.findViewById(R.id.row_cartes_text);
					final String MoyenDePaiement = mySpinner.getText()
							.toString();
					final EditText numero_carte = (EditText) pager
							.findViewById(R.id.facture_numero_carte);
					final EditText password_carte = (EditText) pager
							.findViewById(R.id.facture_password_carte);

					verificationSolde(MoyenDePaiement, numero_carte.getText()
							.toString(), password_carte.getText().toString());

					break;
				case 3:
					// send paiement via HTTP
					progressDialog.setMessage("Paiement en cours ....");
					progressDialog.show();
					payer();
					break;

				default:
					break;
				}

			}

		});

		buttonRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem() - 1, true);
			}
		});

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

	public void retreaveFragmentsInfos() {

		montant = (EditText) pager.findViewById(R.id.facture_montant);
		final EditText referance = (EditText) pager
				.findViewById(R.id.facture_referance);
		final EditText cle = (EditText) pager.findViewById(R.id.facture_cle);
		final EditText date_du = (EditText) pager
				.findViewById(R.id.facture_date_du);
		final EditText date_au = (EditText) pager
				.findViewById(R.id.facture_date_au);
		final EditText nom_et_prenom = (EditText) pager
				.findViewById(R.id.facture_nom_et_prenom);
		// Radio Type :
		final RadioGroup radioGroup = (RadioGroup) pager
				.findViewById(R.id.radioType);
		final RadioButton radioButton = (RadioButton) pager
				.findViewById(radioGroup.getCheckedRadioButtonId());

		final EditText telephone = (EditText) pager
				.findViewById(R.id.facture_telephone);
		final EditText pays = (EditText) pager.findViewById(R.id.facture_pays);
		final EditText email = (EditText) pager
				.findViewById(R.id.facture_email);
		// spinner
		final TextView mySpinner = (TextView) pager
				.findViewById(R.id.row_cartes_text);
		final String MoyenDePaiement = mySpinner.getText().toString();
		final EditText numero_carte = (EditText) pager
				.findViewById(R.id.facture_numero_carte);

		// /////////////////////////////

		final TextView codeOrganisme = (TextView) pager
				.findViewById(R.id.facture_result_code_organisme);
		final TextView resultatMontant = (TextView) pager
				.findViewById(R.id.facture_result_montant);
		final TextView resultatReferance = (TextView) pager
				.findViewById(R.id.facture_result_referance);
		final TextView resultatCle = (TextView) pager
				.findViewById(R.id.facture_result_cle);
		final TextView resultatDateDe = (TextView) pager
				.findViewById(R.id.facture_result_periode_de);
		final TextView resultatDateAu = (TextView) pager
				.findViewById(R.id.facture_result_periode_au);
		final TextView resultatNom = (TextView) pager
				.findViewById(R.id.facture_result_nom);
		final TextView resultatTelephone = (TextView) pager
				.findViewById(R.id.facture_result_telephone);
		final TextView resultatPays = (TextView) pager
				.findViewById(R.id.facture_result_pays);
		final TextView resultatEmail = (TextView) pager
				.findViewById(R.id.facture_result_email);
		final TextView resultatMoyenDePaiement = (TextView) pager
				.findViewById(R.id.facture_result_moyen_paiement);
		final TextView resultatNumeroCarte = (TextView) pager
				.findViewById(R.id.facture_result_numero_carte);

		// update Fragment Validation :

		codeOrganisme.setText(getIntent().getStringExtra("ref"));
		resultatMontant.setText(montant.getText());
		resultatReferance.setText(referance.getText());
		resultatCle.setText(cle.getText());
		resultatDateDe.setText(date_du.getText());
		resultatDateAu.setText(date_au.getText());
		resultatNom.setText(nom_et_prenom.getText());
		resultatTelephone.setText(telephone.getText());
		resultatPays.setText(pays.getText());
		resultatEmail.setText(email.getText());
		resultatMoyenDePaiement.setText(MoyenDePaiement);
		resultatNumeroCarte.setText(numero_carte.getText());

		// Prepare JsonObject to send via HTTP :

		jsonPaiement = new JSONObject();
		try {
			jsonPaiement
					.put("codeOrganisme", getIntent().getStringExtra("ref"));
			jsonPaiement.put("montant", montant.getText());
			jsonPaiement.put("referance", referance.getText());
			jsonPaiement.put("cle", cle.getText());
			jsonPaiement.put("telephone", telephone.getText());
			jsonPaiement.put("dateDebutConso", date_du.getText());
			jsonPaiement.put("dateFinConso", date_au.getText());
			jsonPaiement.put("pays", pays.getText());
			jsonPaiement.put("nom", nom_et_prenom.getText());
			jsonPaiement.put("type", radioButton.getText());
			jsonPaiement.put("email", email.getText());
			jsonPaiement.put("numcarte", numero_carte.getText());
			jsonPaiement.put("typecarte", MoyenDePaiement);
			jsonPaiement.put("codeInternet", carte.getCodeInternet());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void verificationSolde(String type, String numero, String password) {

		progressDialog = new ProgressDialog(this,
				ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setIndeterminate(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("Verification de votre solde ....");
		progressDialog.show();

		JSONObject jsonToPost = new JSONObject();
		try {
			jsonToPost.put("type", type);
			jsonToPost.put("password", password);
			jsonToPost.put("numero", numero);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray

		JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL
				+ "getcarte", jsonToPost, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {

				String crt = jsonObject.toString();

				Gson gson = new Gson();

				carte = gson.fromJson(crt, Carte.class);

				progressDialog.cancel();

				if (carte.getType().matches("password")) {

					alert(2); // password error : 2

				} else if (carte.getType().matches("not found")) {

					alert(3); // not found error : 3
				} else {
					alert(1); // ok : 1
				}

			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {
				// Le code suivant est appel� lorsque Volley n'a pas r�ussi �
				// r�cup�rer le r�sultat de la requ�te
				// Echec de connexion avec le serveur
				progressDialog.cancel();
				alert(0); // network error : 0

			}
		});

		request.setTag(this);
		mRequestQueue.add(request);

	}

	private void alert(int action) {

		// network error : 0
		// ok : 1
		// password error : 2
		// not found error : 3

		switch (action) {
		case 0:
			// network error
			alerter(" ",
					"\nErreur du reseau, vérifier votre connexion à internet ou réessayer plus tard !!\n");
			break;
		case 1:
			// ok
			montant = (EditText) pager.findViewById(R.id.facture_montant);
			if (Integer.parseInt(montant.getText().toString()) >= Integer
					.parseInt(carte.getSolde())) {
				// solde insuffisent
				alerter(" ", "\nSolde insuffisant !! \n");
			} else {
				alerterProceder();
			}
			break;
		case 2:
			// password error
			alerter(" ",
					"\nNumero de la carte et/ ou mot de passe incorrects !!\n");

			break;
		case 3:
			// not found error
			alerter(" ", "\nNumero de la carte incorrect!!\n");

			break;

		default:
			break;
		}
	}

	public void alerter(String titre, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				PaiementActivity.this);

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

	public void alerterProceder() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				PaiementActivity.this);

		alertDialogBuilder.setTitle("Vérification du solde réussite").setIcon(
				getApplicationContext().getResources().getDrawable(
						R.drawable.ic_action_ok));
		alertDialogBuilder
				.setMessage("\nProcéder au paiement avec cette carte ?\n");
		alertDialogBuilder.setPositiveButton("Procéder",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						pager.setCurrentItem(pager.getCurrentItem() + 1, true);

					}

				}).setNegativeButton("Annuler",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	public void payer() {

		// On va cr�er une Request pour Volley.
		// JsonArrayRequest h�rite de Request et transforme automatiquement les
		// donn�es re�ues en un JSONArray

		JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL
				+ "paiement", jsonPaiement,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {

						progressDialog.cancel();
						Intent i = new Intent(getApplicationContext(),
								PaiementResultActivity.class);

						startActivity(i);

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError volleyError) {
						// Le code suivant est appel� lorsque Volley n'a pas
						// r�ussi �
						// r�cup�rer le r�sultat de la requ�te
						// Echec de connexion avec le serveur

						alert(0);
					}
				});

		request.setTag(this);
		mRequestQueue.add(request);

	}
}
