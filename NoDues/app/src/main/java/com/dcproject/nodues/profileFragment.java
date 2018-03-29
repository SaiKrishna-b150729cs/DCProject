package com.dcproject.nodues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.sql.Types.NULL;

/**
 * Created by SAI on 29-03-2018.
 */

public class profileFragment extends Fragment {

    FirebaseUser user;
    DatabaseReference db;

    Student studata;

    public static final String TAG = "profileFragment";


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView branch_tv=(TextView)view.findViewById(R.id.tv_branch);
        final TextView email_tv=(TextView)view.findViewById(R.id.tv_email);
        final TextView mobile_tv=(TextView)view.findViewById(R.id.tv_mobile);
        final TextView name_tv=(TextView)view.findViewById(R.id.tv_name);
        final TextView programme_tv=(TextView)view.findViewById(R.id.tv_programme);
        final TextView roll_tv=(TextView)view.findViewById(R.id.tv_rollno);
        final TextView year_tv=(TextView)view.findViewById(R.id.tv_year);

        user= FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();
        final Query student = db.child("Students").orderByChild("email").equalTo(user.getEmail());

        student.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Log.d(TAG,"Student Exists");
                    for(DataSnapshot stu : dataSnapshot.getChildren()){

                        Log.d(TAG,"Roll no"+stu.getKey());
                        studata=stu.getValue(Student.class);

                        Log.d(TAG,"Name"+studata.getname());
                        Log.d(TAG,"branch"+studata.getbranch());
                        Log.d(TAG,"email"+studata.getemail());


                        name_tv.setText(studata.getname());
                        roll_tv.setText(stu.getKey());
                        branch_tv.setText(studata.getbranch());
                        programme_tv.setText(studata.getprogramme());
                        year_tv.setText(studata.getyear());
                        mobile_tv.setText(studata.getPhone());
                        email_tv.setText(studata.getemail());

                        break;
                    }
                }
                else {
                    Log.d(TAG,"Student doesn't Exists");
                    Toast.makeText(getActivity(), "User Data not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
    }
}
