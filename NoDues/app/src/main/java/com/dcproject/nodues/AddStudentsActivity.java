package com.dcproject.nodues;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AddStudentsActivity extends Activity {

    EditText email, password,year ;
    Button SignUp,Back ;
    String EmailHolder,PasswordHolder,YearHolder;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth ;
    Boolean EditTextStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        email = (EditText)findViewById(R.id.EditText_User_EmailID);
        password = (EditText)findViewById(R.id.EditText_User_Password);
        year=(EditText)findViewById(R.id.year);

        SignUp = (Button)findViewById(R.id.Button_SignUp);
        Back=(Button)findViewById(R.id.Back);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(AddStudentsActivity.this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();

                if(EditTextStatus){
                    UserRegistrationFunction();
                }
                else {
                    Toast.makeText(AddStudentsActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddStudentsActivity.this,DepartmentActivity.class);
                startActivity(intent);
            }
        });
    }

    // Creating UserRegistrationFunction
    public void UserRegistrationFunction(){

        progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
        progressDialog.show();

        // Creating createUserWithEmailAndPassword method and pass email and password inside it.
        firebaseAuth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder).
                addOnCompleteListener(AddStudentsActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Checking if user is registered successfully.
                        if(task.isSuccessful()){
                            // If user registered successfully then show this toast message.
                            Toast.makeText(AddStudentsActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();
                            //firebaseAuth.signOut();
                        }else{
                            // If something goes wrong.
                            Toast.makeText(AddStudentsActivity.this,"Something Went Wrong.",Toast.LENGTH_LONG).show();
                        }
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                    }
                });

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting name and email from EditText and save into string variables.
        EmailHolder = email.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();
        YearHolder=year.getText().toString().trim();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)||TextUtils.isEmpty(YearHolder))
        {
            EditTextStatus = false;
        }
        else {
            EditTextStatus = true ;
        }
    }
    
}
