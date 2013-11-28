package com.laposte.tn.dialogs;

import java.util.Calendar;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.AlertDialog.Builder;
import org.holoeverywhere.app.DatePickerDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.app.DatePickerDialog.OnDateSetListener;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.Toast;
import org.json.JSONException;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.laposte.tn.activities.LaPosteTunisienne;
import com.laposte.tn.activities.R;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FragmentInfosFacture extends SherlockFragment {

	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;
	private EditText dateDu;
	private EditText dateAu;
	View viewOfFragment;

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
		// Inflate the layout for this fragment
		viewOfFragment = inflater.inflate(R.layout.step_infos_facture_1,
				container, false);

		EditText codeOrganisme = (EditText) viewOfFragment
				.findViewById(R.id.facture_code_organisme);
		codeOrganisme.setText(getSherlockActivity().getIntent().getStringExtra(
				"ref"));

		// Text fields for date :
		dateDu = (EditText) viewOfFragment.findViewById(R.id.facture_date_du);
		dateAu = (EditText) viewOfFragment.findViewById(R.id.facture_date_au);

		ImageButton buttonDu = (ImageButton) viewOfFragment
				.findViewById(R.id.facture_date_du_button);
		buttonDu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// DatePickerDialog
				createDialog1();

			}

		});

		ImageButton buttonAu = (ImageButton) viewOfFragment
				.findViewById(R.id.facture_date_au_button);
		buttonAu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// triggers the DatePickerDialog
				createDialog2();

			}
		});

		return viewOfFragment;
	}

	private void createDialog1() {

		LayoutInflater li = LayoutInflater.from(getSherlockActivity());
		final View promptsView = li.inflate(R.layout.layout_date_picker, null);

		alertDialogBuilder = new AlertDialog.Builder(getSherlockActivity());

		final DatePicker dp1 = (DatePicker) promptsView
				.findViewById(R.id.datePicker1);

		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setTitle("Choisir date");

		// set dialog message
		alertDialogBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dateDu.setText(dp1.getDayOfMonth() + "/" + dp1.getMonth() + "/"
								+ dp1.getYear());

					}

				});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	private void createDialog2() {

		LayoutInflater li = LayoutInflater.from(getSherlockActivity());
		final View promptsView = li.inflate(R.layout.layout_date_picker, null);

		alertDialogBuilder = new AlertDialog.Builder(getSherlockActivity());

		final DatePicker dp1 = (DatePicker) promptsView
				.findViewById(R.id.datePicker1);

		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setTitle("Choisir date");

		// set dialog message
		alertDialogBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dateAu.setText(dp1.getDayOfMonth() + "/" + dp1.getMonth() + "/"
								+ dp1.getYear());

					}

				});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
}