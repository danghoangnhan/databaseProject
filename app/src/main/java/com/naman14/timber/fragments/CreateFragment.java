package com.naman14.timber.fragments;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.naman14.timber.activities.CreateActivity;
import com.naman14.timber.R;
import com.naman14.timber.activities.MainActivity;


public class CreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   Button b1;

    // TODO: Rename and change types of parameters




    public CreateFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_create, container, false);

        b1 = v.findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}