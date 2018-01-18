package com.example.fajlehrabbi.appmcci.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/12/2017.
 */

public class DateAdapter extends ArrayAdapter<String>{

    private ArrayList<String> dataSet;
    Context mContext;
    public  Button button;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public DateAdapter(ArrayList<String> data, Context mContext) {
        super(mContext, R.layout.committee_list, data);
        this.dataSet = data;
        this.mContext=mContext;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String date = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.date_list, parent, false);
            button= (Button) (viewHolder.txtType = (Button) convertView.findViewById(R.id.type));
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;
        viewHolder.txtType.setText(date);

      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,FileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // intent.putExtra(SubCommittee.ACCESSIBILITY_SERVICE.concat(po));
                mContext.startActivity(intent);
                //mContext.startActivity(new Intent(mContext, FileActivity.class));
            }
        });*/

        return convertView;
    }
}
