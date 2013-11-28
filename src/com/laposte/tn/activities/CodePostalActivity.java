package com.laposte.tn.activities;



import java.util.List;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.laposte.tn.business.CodeAdapter;
import com.laposte.tn.model.CodePostal;
 
public class CodePostalActivity extends SherlockActivity {
 
    // Declare Variables
	private ActionBar mActionBar;
	private ListView list ;
	private CodeAdapter adapter;
	private String[] code;
	private String[] commune;
	private String[] quartier;
	private String[] gouvernorat;
	private List<CodePostal> arraylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_codepostal);
        
        list = (ListView) findViewById(R.id.codepostal_listview);
        list.setEmptyView(findViewById(R.id.empty));
      
		
        
        setSupportProgressBarIndeterminateVisibility(false);
        mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("Code Postal");
		
		
		
		arraylist= ((LaPosteTunisienne) getApplicationContext()).arraylist;
		// Pass results to ListViewAdapter Class
        adapter = new CodeAdapter(this, arraylist);
 
        
        
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
		
		
		
		
    }
    
    
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	SearchView searchView = new
                SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("ville/code..");
        searchView.setIconified(false);
        
        MenuItem menu1=menu.add("");
        		
                menu1.setIcon(R.drawable.ic_action_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu1.expandActionView();
                                
        
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            
            
            @Override
            public boolean onQueryTextSubmit(String newText) {
            	
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            	
            	adapter.filter(newText);
                return true;}
        });
        
        return true;
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
    
}



