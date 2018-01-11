package com.example.fajlehrabbi.appmcci.Utilities;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fajlehrabbi.appmcci.Model.Person;

import com.example.fajlehrabbi.appmcci.R;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by User on 3/23/2017.
 */

public class ContactsCompletionView extends TokenCompleteTextView {
    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Object object) {
        Person p = (Person)object;

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.contact_toke, (ViewGroup)ContactsCompletionView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(p.getName());

        return view;
    }

    @Override
    protected Object defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Person(completionText, completionText.replace(" ", "") + "@example.com");
        } else {
            return new Person(completionText.substring(0, index), completionText);
        }
    }
}
