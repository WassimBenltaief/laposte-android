package com.laposte.tn.dialogs;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;

import com.laposte.tn.activities.PaiementActivity;
import com.laposte.tn.activities.R;
import com.laposte.tn.activities.SuiviActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class FragmentDialogPaiement extends DialogFragment {
    
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choisir un type :")
               .setItems(R.array.list_factures, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                	   switch (which) {
					case 0:
						Intent i = new Intent(getActivity(), PaiementActivity.class); 
						i.putExtra("type", "Eau");
						i.putExtra("ref", "0502");
						startActivity(i); 
						break;

					case 1:
						Intent i1 = new Intent(getActivity(), PaiementActivity.class); 
						i1.putExtra("type", "Electricité");
						i1.putExtra("ref", "0701");
						startActivity(i1); 
						break;
					case 2:
						Intent i2 = new Intent(getActivity(), PaiementActivity.class); 
						i2.putExtra("type", "TunTel");
						i2.putExtra("ref", "0150");
						startActivity(i2); 
						break;
					case 3:
						Intent i3 = new Intent(getActivity(), PaiementActivity.class); 
						i3.putExtra("type", "Tunisiana");
						i3.putExtra("ref", "0777");
						startActivity(i3); 
						break;
					case 4:
						Intent i4 = new Intent(getActivity(), PaiementActivity.class); 
						i4.putExtra("type", "Topnet");
						i4.putExtra("ref", "0770");
						startActivity(i4); 
						break;
					case 5:
						Intent i5 = new Intent(getActivity(), PaiementActivity.class); 
						i5.putExtra("type", "Planet");
						i5.putExtra("ref", "0765");
						startActivity(i5); 
						break;
					case 6:
						Intent i6 = new Intent(getActivity(), PaiementActivity.class); 
						i6.putExtra("type", "Tradenet");
						i6.putExtra("ref", "0760");
						startActivity(i6); 
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