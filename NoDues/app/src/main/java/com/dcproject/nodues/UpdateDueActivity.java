package com.dcproject.nodues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateDueActivity extends Activity {

    EditText etRollno,etDue,etReason;
    Button  updateBtn;

    String rollNo,dueAmount,reason,email;

    FirebaseAuth firebaseAuth;
    FirebaseUser User;

    public static final String TAG = "UpdateDueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_due);

        etRollno=(EditText)findViewById(R.id.rollno);
        etDue=(EditText)findViewById(R.id.dueamount);
        etReason=(EditText)findViewById(R.id.reason);

        updateBtn=(Button)findViewById(R.id.addduebtn);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Toast.makeText(UpdateDueActivity.this,"Please Login in to continue",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(UpdateDueActivity.this, com.dcproject.nodues.LoginActivity.class);
            startActivity(intent);
        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfields())
                adddue();
            }
        });
    }

    public boolean checkfields(){

        rollNo=etRollno.getText().toString().trim();
        dueAmount=etDue.getText().toString().trim();
        reason=etReason.getText().toString().trim();

        if(rollNo.isEmpty()){
            Toast.makeText(UpdateDueActivity.this, "Enter a student Roll No", Toast.LENGTH_LONG).show();
            return false;
        }
        if(dueAmount.isEmpty()) {
            Toast.makeText(UpdateDueActivity.this, "Enter a Due amount", Toast.LENGTH_LONG).show();
            return false;
        }
        if(reason.isEmpty()){
            Toast.makeText(UpdateDueActivity.this, "Enter Due reason", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void adddue(){

        firebaseAuth=FirebaseAuth.getInstance();
        User=firebaseAuth.getCurrentUser();
        email=User.getEmail().toString();


    }
}



