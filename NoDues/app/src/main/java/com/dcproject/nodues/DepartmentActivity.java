package com.dcproject.nodues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DepartmentActivity extends Activity {

    Button logout,addStu,ViewReq,updateDueBtn ;
    // Creating TextView.
    TextView userEmailShow ;
    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;
    // Creating FirebaseAuth.
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        logout=(Button)findViewById(R.id.logout_dep);
        addStu=(Button)findViewById(R.id.addStuBtn);
        ViewReq=(Button)findViewById(R.id.viewReqBtn);
        userEmailShow=(TextView)findViewById(R.id.department);
        updateDueBtn=(Button)findViewById(R.id.UpdateDueBtn);

        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Intent intent=new Intent(DepartmentActivity.this, com.dcproject.nodues.LoginActivity.class);
            startActivity(intent);

            Toast.makeText(DepartmentActivity.this,"Please Login in to continue",Toast.LENGTH_LONG).show();
        }
        firebaseUser=firebaseAuth.getCurrentUser();

        userEmailShow.setText("Department Email:"+firebaseUser.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();

                Intent intent=new Intent(DepartmentActivity.this, com.dcproject.nodues.LoginActivity.class);
                startActivity(intent);
                Toast.makeText(DepartmentActivity.this,"Logged Out Successfully.",Toast.LENGTH_LONG).show();
            }
        });

        addStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DepartmentActivity.this,AddStudentsActivity.class);
                startActivity(intent);
            }
        });

        ViewReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DepartmentActivity.this,ViewRequestsActivity.class);
                startActivity(intent);
            }
        });

        updateDueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DepartmentActivity.this,UpdateDueActivity.class);
                startActivity(intent);
            }
        });


    }
}