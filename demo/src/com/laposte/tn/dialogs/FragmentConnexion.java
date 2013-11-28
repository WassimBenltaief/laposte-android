package com.laposte.tn.dialogs;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;

import org.holoeverywhere.widget.Toast;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.laposte.tn.activities.R;

public class FragmentConnexion extends DialogFragment {

	EditText login;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = getActivity().getLayoutInflater();

		
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.dialog_signin, null))

		.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
//				FragmentDialogSupport newFragment = new FragmentDialogSupport();
//			    newFragment.show(getFragmentManager(), "TAG");
				
			    View view = inflater.inflate(R.layout.dialog_signin, null);
		        login = (EditText) view.findViewById(R.id.username);
		        
				Toast.makeText(getActivity().getApplicationContext(), "Hello "+login.getText().toString(),Toast.LENGTH_SHORT).show();

			}
		}).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dismiss();
			}
		});
		
		
		return builder.create();
	}
	
	
	
	

}