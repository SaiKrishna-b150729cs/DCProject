package com.dcproject.nodues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by SAI on 29-03-2018.
 */

public class statusFragment extends Fragment {

    FirebaseUser user;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> departments = new ArrayList<String>();
    ArrayList<String> status = new ArrayList<String>();
    public static final String TAG = "statusFragment";
    String rollno;

    ListView status_lv,dep_lv;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.d(TAG,"In statusFragment");
        view =inflater.inflate(R.layout.fragment_status, container, false);

        status_lv=(ListView) view.findViewById(R.id.status_list);
        dep_lv=(ListView) view.findViewById(R.id.dep_list);

        user= FirebaseAuth.getInstance().getCurrentUser();
        final Query student = db.child("Students").orderByChild("email").equalTo(user.getEmail());

        student.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Log.d(TAG,"Student Exists");
                    for(DataSnapshot stu : dataSnapshot.getChildren()){
                        Log.d(TAG,"Roll no"+stu.getKey());
                        rollno=stu.getKey();
                        break;
                    }
                    getstatus();
                }
                else {
                    Log.d(TAG,"Student doesn't Exists");
                    //Toast.makeText(getActivity(), "User Data not available", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Status");
    }

    public void getstatus(){

        DatabaseReference request=db.child("Request");

        request.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"Getting Departments and status"+dataSnapshot.getChildrenCount());
                for(DataSnapshot dept : dataSnapshot.getChildren()){
                    departments.add(dept.getKey());
                    status.add(dept.child(rollno).getValue().toString());
                }
                setlistview();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setlistview(){
        Log.d(TAG,"Setting dept listview");
        ArrayAdapter<String> listadapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,departments);
        dep_lv.setAdapter(listadapter);

        ArrayAdapter<String> listadapter2 =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,status);
        status_lv.setAdapter(listadapter2);

        Log.d(TAG,"Setting status listview");

    }
}
