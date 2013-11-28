package com.laposte.tn.business;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.laposte.tn.activities.R;
import com.laposte.tn.model.RapidPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public final class RapidPostAdapter extends BaseAdapter {
 
    
	// Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<RapidPost> rapidPostList = null;
    private ArrayList<RapidPost> arraylist;
    
    public RapidPostAdapter(Context context, List<RapidPost> rapidPostList ) {
        mContext = context;
        this.rapidPostList = rapidPostList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<RapidPost>();
        this.arraylist.addAll(rapidPostList);
    }
    
    public class ViewHolder {
    	TextView RapidPostDate;
        TextView RapidPostPays;
        TextView RapidPostLieu;
        TextView RapidPostType;
        TextView RapidPostInfos;
    }
    
    @Override
    public int getCount() {
        return rapidPostList.size();
    }
 
    @Override
    public RapidPost getItem(int position) {
        return rapidPostList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @SuppressLint("ResourceAsColor")
	public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.RapidPostDate = (TextView) view.findViewById(R.id.RapidPostDate);
            holder.RapidPostInfos = (TextView) view.findViewById(R.id.RapidPostInfos);
            holder.RapidPostLieu = (TextView) view.findViewById(R.id.RapidPostLieu);
            holder.RapidPostPays = (TextView) view.findViewById(R.id.RapidPostPays);
            holder.RapidPostType = (TextView) view.findViewById(R.id.RapidPostType);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.RapidPostDate.setText(rapidPostList.get(position).getDate());
        holder.RapidPostInfos.setText(rapidPostList.get(position).getInformations());
        holder.RapidPostLieu.setText(rapidPostList.get(position).getLieu());
        holder.RapidPostPays.setText(rapidPostList.get(position).getPays());
        holder.RapidPostType.setText(rapidPostList.get(position).getType());
        
        
        if (position % 2 == 1) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));  
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f5f5"));  
        }
 
        /*
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
        }); */
 
        return view;
    }
    
 // Filter Class
    public void filter() {
        
        notifyDataSetChanged();
    }
    
}