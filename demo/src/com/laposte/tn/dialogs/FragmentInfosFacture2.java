package com.laposte.tn.dialogs;

import com.actionbarsherlock.app.SherlockFragment;
import com.laposte.tn.activities.R;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentInfosFacture2 extends SherlockFragment {
	
	String[] strings = {"Carte e-Dinar","Carte e-DINAR SMART",
            "Carte e-DINAR UNIVERSEL", "Carte DINARPOST VISA Electron", "Cartes Visa International","CCPNet"};
  
 
    int arr_images[] = { R.drawable.ic_edinar0,
                         R.drawable.ic_smart, R.drawable.ic_universal,
                         R.drawable.ic_visa_post, R.drawable.ic_visa, R.drawable.ic_ccpnet};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.step_infos_facture_2, container,
				false);
		
		Spinner mySpinner = (Spinner) view.findViewById(R.id.spinnerMoyenDePaiement);
        mySpinner.setAdapter(new MyAdapter(getSherlockActivity().getApplicationContext(), R.layout.row_cartes, strings));
        
       final CheckBox checkbox = (CheckBox) view.findViewById(R.id.facture_checkbox_email);
       final EditText email = (EditText) view.findViewById(R.id.facture_email);
       checkbox.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(checkbox.isChecked()==true){
				email.setEnabled(true);
				email.setVisibility(View.VISIBLE);
			}else{
				email.setEnabled(false);
				email.setVisibility(View.GONE);
			}
			
		}
	});
        
		return view;
	}

	public class MyAdapter extends ArrayAdapter<String>{
		 
        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }
 
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        public View getCustomView(int position, View convertView, ViewGroup parent) {
 
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.row_cartes, parent, false);
            TextView label=(TextView)row.findViewById(R.id.row_cartes_text);
            label.setText(strings[position]);
            ImageView icon=(ImageView)row.findViewById(R.id.row_cartes_image);
            icon.setImageResource(arr_images[position]);
 
            return row;
            }
        }
}