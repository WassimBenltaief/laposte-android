package com.laposte.tn.dialogs;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;

import com.laposte.tn.activities.R;
import com.laposte.tn.activities.SuiviActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class FragmentDialogSupport extends DialogFragment {
    
	
	
	
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choisir un type")
               .setItems(R.array.list_post, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                	   switch (which) {
					case 0:
						Intent i = new Intent(getActivity(), SuiviActivity.class); 
						i.putExtra("type", "colis");
						startActivity(i); 
						break;

					case 1:
						Intent i1 = new Intent(getActivity(), SuiviActivity.class); 
						i1.putExtra("type", "rapidpost");
						startActivity(i1); 
						break;
					}
               }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        });
        return builder.create();
        }
}