package com.example.fajlehrabbi.appmcci.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Activity.MainActivity;
import com.example.fajlehrabbi.appmcci.DownloadTask;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Administrator on 12/12/2017.
 */

public class FileAdapter extends ArrayAdapter<FileLists>{

    private ArrayList<FileLists> dataSet;
    Context mContext;
    public  Button button;



    public FileAdapter(ArrayList<FileLists> data, Context mContext) {
        super(mContext, R.layout.file_list, data);
        this.dataSet = data;
        this.mContext=mContext;
    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
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
            viewHolder.btnSync = (ImageButton) convertView.findViewById(R.id.btnSync);
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

        String downloadUrl= fileLists.getFile_upload();
        String downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf( '/' ),downloadUrl.length());//Create file name by picking download file name from URL
        String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
        File f = new File(Environment.getExternalStorageDirectory()+ File.separator + fileLocation);
        if(!f.exists()){
            //new DownloadTask(mContext,fileLists.getFile_upload(),"","");
            viewHolder.btnSync.setVisibility(View.GONE);
        }else {
            viewHolder.btnSync.setVisibility(View.VISIBLE);
        }

        viewHolder.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(mContext, "Hi", Toast.LENGTH_SHORT).show();
                FileLists fileLists1 = dataSet.get(position);
                Toast.makeText(mContext, fileLists1.getName(), Toast.LENGTH_SHORT).show();
                */

                FileLists fileLists = (FileLists) dataSet.get(position);
                String downloadUrl= fileLists.getFile_upload();
                String extension = fileLists.getFile_extension();
                String downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf( '/' ),downloadUrl.length());//Create file name by picking download file name from URL
                String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
                //if(!openFile(fileLocation,extension)){
                    if(Utils.isInternetConnected(mContext)){
                        new DownloadTask(parent.getContext(),downloadUrl,extension,"sync");
                    }else {
                        Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                //}
            }
        });
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView ivFile;
        ImageButton btnSync;
    }
}
