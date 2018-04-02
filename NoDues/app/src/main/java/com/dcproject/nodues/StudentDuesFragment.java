package com.dcproject.nodues;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDuesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "roll_number";
    private static final String ARG_PARAM2 = "department";

    // TODO: Rename and change types of parameters
    private String rollno;
    private String department;
    View view;
    TextView rollnoShow;
    ListView duesView;
    Button approve, reject;
    ArrayList<String> dues = new ArrayList<String>();
    public static final String TAG = "StudentDuesFragment";


    //private OnFragmentInteractionListener mListener;

    public StudentDuesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StudentDuesFragment newInstance(String param1, String param2) {
        StudentDuesFragment fragment = new StudentDuesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rollno = getArguments().getString(ARG_PARAM1);
            department = getArguments().getString(ARG_PARAM2);
        }
    }*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(rollno);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_student_dues, container, false);
        if (getArguments() != null) {
            rollno = getArguments().getString(ARG_PARAM1);
            department = getArguments().getString(ARG_PARAM2);
        }
        rollnoShow=(TextView)view.findViewById(R.id.student_rollno);
        approve=(Button) view.findViewById(R.id.approve_button);
        reject=(Button)view.findViewById(R.id.reject_button);
        duesView = (ListView) view.findViewById(R.id.dues_list);
        rollnoShow.setText(rollno);

        displayDuesList();

        //Listeners for accept/reject buttons
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveRequest();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequest();
            }
        });


        return view;
    }


    public void approveRequest(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        DatabaseReference request=db.child("Request").child(department).child(rollno);
        request.setValue("approved");
        Toast.makeText(getActivity(), "Approved Succesfully", Toast.LENGTH_LONG ).show();
        Log.d(TAG,"Approved" );
        getFragmentManager().popBackStackImmediate ();
    }

    public void rejectRequest(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        DatabaseReference request=db.child("Request").child(department).child(rollno);
        request.setValue("rejected");
        Toast.makeText(getActivity(), "Rejected Succesfully", Toast.LENGTH_LONG ).show();
        Log.d(TAG,"Rejected" );
        getFragmentManager().popBackStackImmediate ();
    }

    public void displayDuesList(){
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        DatabaseReference studentdues= db.child("Dues").child(department).child(rollno);
        studentdues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dues.clear();
                dues.add("Due\t\tReason");
                for(DataSnapshot duesnap : dataSnapshot.getChildren()){
                    Dues due = duesnap.getValue(Dues.class);
                    if(due!=null && due.getRemaining()>0) {
                        String display = due.getRemaining().toString() +"\t\t"+ due.getreason();
                        dues.add(display);
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, dues);
                duesView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
