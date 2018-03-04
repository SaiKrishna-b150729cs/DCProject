package com.dcproject.nodues;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentActivity extends Activity {

    Button logout ;
    // Creating TextView.
    TextView userEmailShow ;
    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;
    // Creating FirebaseAuth.
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        logout=(Button)findViewById(R.id.logout);
        userEmailShow=(TextView)findViewById(R.id.user_email);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Intent intent=new Intent(StudentActivity.this, com.dcproject.nodues.LoginActivity.class);
            startActivity(intent);

            Toast.makeText(StudentActivity.this,"Please Login in to continue",Toast.LENGTH_LONG).show();
        }
        firebaseUser=firebaseAuth.getCurrentUser();

        userEmailShow.setText("Successfully Logged in,Your Email="+firebaseUser.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();

                Intent intent=new Intent(StudentActivity.this, com.dcproject.nodues.LoginActivity.class);
                startActivity(intent);
                Toast.makeText(StudentActivity.this,"Logged Out Successfully.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
