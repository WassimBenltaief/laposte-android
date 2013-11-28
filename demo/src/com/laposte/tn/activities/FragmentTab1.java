package com.laposte.tn.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.laposte.tn.activities.R;
import com.laposte.tn.dialogs.FragmentDialogSupport;

public class FragmentTab1 extends SherlockFragment {

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
		// Get the view from fragmenttab1.xml
		View view = inflater.inflate(R.layout.fragmenttab1, container, false);


		LinearLayout trackAndTrace = (LinearLayout) view
				.findViewById(R.id.track_and_trace);
		trackAndTrace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentDialogSupport newFragment = new FragmentDialogSupport();
				newFragment.show(getFragmentManager(), "TAG");

			}
		});

		LinearLayout codepostal = (LinearLayout) view
				.findViewById(R.id.code_postal);
		codepostal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CodePostalActivity.class);

				startActivity(i);

			}
		});
		LinearLayout bureau = (LinearLayout) view
				.findViewById(R.id.recherche_bureau);
		bureau.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), BureauActivity.class);

				startActivity(i);

			}
		});
		
		
		LinearLayout map = (LinearLayout) view
				.findViewById(R.id.map_button);
		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
				

				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					// fetch data
					Intent toMap = new Intent(getActivity(), MapActivity.class);
					startActivity(toMap);
				} else if (status != ConnectionResult.SUCCESS) {
					Toast.makeText(
							getActivity(),
							"Google Services n'est pas installé ou n'est pas configuré correctement !!",
							Toast.LENGTH_LONG).show();
					
				}else {
					// display error
					Toast.makeText(
							getActivity(),
							"Vous n'êtes pas connnecté à internet, verrifiez votre connexion !!",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

}
