package com.dcproject.nodues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class updateDueFragment extends Fragment {
    FirebaseAuth firebaseauth;
    FirebaseUser user;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public static String TAG = "addStudentsFragment";


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.d(TAG,"In updateDueFragment");
        view =inflater.inflate(R.layout.fragment_viewrequests, container, false);

        firebaseauth=FirebaseAuth.getInstance();
        user=firebaseauth.getCurrentUser();



        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("View Requests");
    }
}
