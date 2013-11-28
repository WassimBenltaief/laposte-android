package com.laposte.tn.dialogs;

import com.actionbarsherlock.app.SherlockFragment;
import com.laposte.tn.activities.R;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentFactureValidation extends SherlockFragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.layout_facture_validation, container,
				false);
		
		
		return view;
	}

	
}