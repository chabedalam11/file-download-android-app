package com.example.fajlehrabbi.appmcci.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.Model.SubComLists;
import com.example.fajlehrabbi.appmcci.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/12/2017.
 */

public class TaskAdapter extends ArrayAdapter<SubComLists>{

    private ArrayList<SubComLists> dataSet;
    Context mContext;
    public Button button;
    ComLists comLists;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
      //  TextView txtVersion;
       // ImageView info;
    }

    public TaskAdapter(ArrayList<SubComLists> data, Context context) {
        super(context, R.layout.sub_committe_list, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SubComLists subComLists = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.sub_committe_list, parent, false);
          button = (Button) (viewHolder.txtType = (TextView) convertView.findViewById(R.id.type));
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;
        viewHolder.txtType.setText(subComLists.getSubcat_name());
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,Datee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // intent.putExtra(SubCommittee.ACCESSIBILITY_SERVICE.concat(po));
                mContext.startActivity(intent);
               // mContext.startActivity(new Intent(mContext, Datee.class));
            }
        });*/
        return convertView;
    }
}
