package com.example.fajlehrabbi.appmcci.Adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.SubCommittee;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;


import java.util.ArrayList;

/**
 * Created by Administrator on 12/12/2017.
 */

public class CustomAdapter extends ArrayAdapter<ComLists>{
   // private final Activity context1;
    private ArrayList<ComLists> dataSet;
    Context mContext;
    SubCommittee subCommittee;
    public Button button;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<ComLists> data, Context context) {
        super(context, R.layout.committee_list, data);
        this.dataSet = data;
        this.mContext=context;
    }




    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ComLists comLists =getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
           convertView = inflater.inflate(R.layout.committee_list, parent, false);
          //  viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            button= (Button) (viewHolder.txtType = (Button) convertView.findViewById(R.id.type));
            //viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
           // viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

      viewHolder.txtType.setText(comLists.getCat_name());

        /*// Return the completed view to render on screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,SubCommittee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });*/
        return convertView;
    }
}
