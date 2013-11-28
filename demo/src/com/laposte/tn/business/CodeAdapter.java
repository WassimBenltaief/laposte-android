package com.laposte.tn.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.laposte.tn.activities.R;
import com.laposte.tn.model.CodePostal;
 

public class CodeAdapter extends BaseAdapter {
	 
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<CodePostal> codepostallist = null;
    private ArrayList<CodePostal> arraylist;
 
    public CodeAdapter(Context context, List<CodePostal> codepostallist) {
        mContext = context;
        this.codepostallist = codepostallist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<CodePostal>();
        this.arraylist.addAll(codepostallist);
    }
 
    public class ViewHolder2 {
    	TextView codepostal_code;
        TextView codepostal_quartier;
        TextView codepostal_commune;
        TextView codepostal_gouvernorat;
    }

    @Override
    public int getCount() {
        return codepostallist.size();
    }
 
    @Override
    public CodePostal getItem(int position) {
        return codepostallist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder2 holder;
        if (view == null) {
            holder = new ViewHolder2();
            view = inflater.inflate(R.layout.codepostal_item, null);
            // Locate the TextViews in listview_item.xml
         // Locate the TextViews in listview_item.xml
            holder.codepostal_code = (TextView) view.findViewById(R.id.codepostal_code);
            holder.codepostal_quartier = (TextView) view.findViewById(R.id.codepostal_quartier);
            holder.codepostal_commune = (TextView) view.findViewById(R.id.codepostal_commune);
            holder.codepostal_gouvernorat = (TextView) view.findViewById(R.id.codepostal_gouvernement);


            view.setTag(holder);
        } else {
            holder = (ViewHolder2) view.getTag();
        }
        // Set the results into TextViews
        holder.codepostal_code.setText(codepostallist.get(position).getCode());
        holder.codepostal_quartier.setText(codepostallist.get(position).getQuartier());
        holder.codepostal_commune.setText(codepostallist.get(position).getCommune());
        holder.codepostal_gouvernorat.setText(codepostallist.get(position).getGouvernorat());
 
        if (position % 2 == 1) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));  
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f5f5"));  
        }
        
        
        return view;
    }
    
 // Filter Class
	 public void filter(String charText) {
	        charText = charText.toLowerCase(Locale.getDefault());
	        codepostallist.clear();
	        if (charText.length() == 0) {
	            codepostallist.addAll(arraylist);
	        } 
	        else
	        {
	            for (CodePostal wp : arraylist) 
	            {
	                if (wp.getCode().toLowerCase(Locale.getDefault()).contains(charText) 
	                		|| wp.getCommune().toLowerCase(Locale.getDefault()).contains(charText)
	                		|| wp.getGouvernorat().toLowerCase(Locale.getDefault()).contains(charText)
	                		|| wp.getQuartier().toLowerCase(Locale.getDefault()).contains(charText)) 
	                {
	                    codepostallist.add(wp);
	                }
	            }
	        }
	        notifyDataSetChanged();
	    }
    
}



/*
public final class CodeAdapter extends BaseAdapter {
 
    
	// Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<CodePostal> codePostalList;
    private List<CodePostal> arraylist;
    
    public CodeAdapter(Context context, List<CodePostal> codePostalList ) {
        mContext = context;
        this.codePostalList = codePostalList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<CodePostal>();
        this.arraylist.addAll(codePostalList);
        
        
    }
    
    public class ViewHolder {
    	TextView codepostal_code;
        TextView codepostal_quartier;
        TextView codepostal_commune;
        TextView codepostal_gouvernorat;
    }
    
    @Override
    public int getCount() {
        return codePostalList.size();
    }
 
    @Override
    public CodePostal getItem(int position) {
        return codePostalList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 

	public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.codepostal_code = (TextView) view.findViewById(R.id.codepostal_code);
            holder.codepostal_quartier = (TextView) view.findViewById(R.id.codepostal_quartier);
            holder.codepostal_commune = (TextView) view.findViewById(R.id.codepostal_commune);
            holder.codepostal_gouvernorat = (TextView) view.findViewById(R.id.codepostal_gouvernement);
            
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.codepostal_code.setText(codePostalList.get(position).getCode());
        holder.codepostal_quartier.setText(codePostalList.get(position).getQuartier());
        holder.codepostal_commune.setText(codePostalList.get(position).getCommune());
        holder.codepostal_gouvernorat.setText(codePostalList.get(position).getGouvernorat());
       
        
        
 
        
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        }); 
 
        return view;
    }
    
 // Filter Class
	 public void filter(String charText) {
	        charText = charText.toLowerCase(Locale.getDefault());
	        codePostalList.clear();
	        if (charText.length() == 0) {
	            codePostalList.addAll(arraylist);
	        } 
	        else
	        {
	            for (CodePostal wp : arraylist) 
	            {
	                if (wp.getCode().toLowerCase(Locale.getDefault()).contains(charText) 
	                		|| wp.getCommune().toLowerCase(Locale.getDefault()).contains(charText)
	                		|| wp.getGouvernorat().toLowerCase(Locale.getDefault()).contains(charText)
	                		|| wp.getQuartier().toLowerCase(Locale.getDefault()).contains(charText)) 
	                {
	                    codePostalList.add(wp);
	                }
	            }
	        }
	        notifyDataSetChanged();
	    }
    
}

*/