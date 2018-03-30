package com.dcproject.nodues;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class addStudentsFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser User;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    EditText email, password,year ;
    Button SignUp,Back ;
    String EmailHolder,PasswordHolder,YearHolder;
    ProgressDialog progressDialog;
    Boolean EditTextStatus ;

    public static String TAG = "addStudentsFragment";


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.d(TAG,"In addStudentsFragment");
        view =inflater.inflate(R.layout.fragment_addstudents, container, false);


        email = (EditText)view.findViewById(R.id.EditText_User_EmailID);
        password = (EditText)view.findViewById(R.id.EditText_User_Password);
        year=(EditText)view.findViewById(R.id.year);
        SignUp = (Button)view.findViewById(R.id.Button_SignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        User=firebaseAuth.getCurrentUser();
        //AuthCredential credential = EmailAuthProvider.getCredential(User.getEmail(),);

        progressDialog = new ProgressDialog(getActivity());

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckEditTextIsEmptyOrNot()){
                    UserRegistrationFunction();
                }
                else {
                    Toast.makeText(getActivity(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });


        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("View Requests");
    }
    public boolean CheckEditTextIsEmptyOrNot(){

        // Getting name and email from EditText and save into string variables.
        EmailHolder = email.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();
        YearHolder=year.getText().toString().trim();


        if(TextUtils.isEmpty(EmailHolder)||!android.util.Patterns.EMAIL_ADDRESS.matcher(EmailHolder).matches() ){
            Toast.makeText(getActivity(), "Enter a valid Email", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(PasswordHolder)){
            Toast.makeText(getActivity(), "Enter a Password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true ;
    }

    // Creating UserRegistrationFunction
    public void UserRegistrationFunction(){

        progressDialog.setMessage("Please Wait,Registering");
        progressDialog.show();



        // Creating createUserWithEmailAndPassword method and pass email and password inside it.
        firebaseAuth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder).
                addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Checking if user is registered successfully.
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Student added Successfully",Toast.LENGTH_LONG).show();
                            //firebaseAuth.signOut();
                        }else{
                            Toast.makeText(getActivity(),"Something Went Wrong.",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}
