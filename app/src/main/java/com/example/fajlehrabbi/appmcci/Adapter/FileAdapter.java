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

public class FileAdapter extends ArrayAdapter<FileLists>{

    private ArrayList<FileLists> dataSet;
    Context mContext;
    public  Button button;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView ivFile;
    }

    public FileAdapter(ArrayList<FileLists> data, Context mContext) {
        super(mContext, R.layout.file_list, data);
        this.dataSet = data;
        this.mContext=mContext;

    }



    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FileLists fileLists = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.file_list, parent, false);
            viewHolder.txtType = (Button) convertView.findViewById(R.id.type);
            viewHolder.ivFile = (ImageView) convertView.findViewById(R.id.ivFile);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtType.setText(fileLists.getName()+"."+fileLists.getFile_extension());
        if(fileLists.getFile_extension().equalsIgnoreCase("pdf")){
            viewHolder.ivFile.setBackgroundResource(R.drawable.pdf);
        }else {
            viewHolder.ivFile.setBackgroundResource(R.drawable.doc2);
        }

        return convertView;
    }
}
