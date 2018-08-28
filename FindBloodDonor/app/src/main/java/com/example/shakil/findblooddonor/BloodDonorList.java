package com.example.shakil.findblooddonor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Shakil on 17-Aug-18.
 */

public class BloodDonorList extends ArrayAdapter<UserInformation> {

    private Activity context;
    private List<UserInformation> bloodDonorList;

    public BloodDonorList(Activity context, List<UserInformation> bloodDonorList){
        super(context,R.layout.list_layout,bloodDonorList);
        this.context = context;
        this.bloodDonorList = bloodDonorList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.textView_name);
        TextView textViewContact = listViewItem.findViewById(R.id.textView_contact);
        TextView textViewBloodGroup = listViewItem.findViewById(R.id.textView_bloodGroup);

        UserInformation userInformation = bloodDonorList.get(position);

        textViewBloodGroup.setText(userInformation.getBloodGroup());
        textViewName.setText(userInformation.getDonorName());
        textViewContact.setText(userInformation.getContactNo());


        return listViewItem;
    }

}
