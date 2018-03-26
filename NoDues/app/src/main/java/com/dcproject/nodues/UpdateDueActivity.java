package com.dcproject.nodues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class UpdateDueActivity extends Activity {

    EditText etRollno,etDue,etReason;
    Button  updateBtn;

    String rollNo;
    String dueAmount;
    String reason;
    String email,department;
    int dues;

    FirebaseAuth firebaseAuth;
    FirebaseUser User;

    DatabaseReference db;

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

        User=firebaseAuth.getCurrentUser();
        //email=User.getEmail().toString();

        db= FirebaseDatabase.getInstance().getReference();
        //retrive department name
        DatabaseReference dept=db.child("Departments");
        Query deptquery = dept.orderByChild("email").equalTo(User.getEmail());
        Log.d(TAG,"in getDeptId "+ User.getEmail() +deptquery.toString());
        deptquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "data" +dataSnapshot.toString());
                for(DataSnapshot dept1 : dataSnapshot.getChildren()){
                    Log.d(TAG, dept1.toString());
                    department = dept1.getKey();
                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"database error in getdept id");
            }
        });



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
        else
            dues=Integer.parseInt(dueAmount);
        if(reason.isEmpty()){
            Toast.makeText(UpdateDueActivity.this, "Enter Due reason", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void adddue(){
        DatabaseReference dbDues=db.child("Dues");
        dbDues.child(department).child(rollNo).push().setValue(new Dues(reason,dues, Calendar.getInstance().getTime()));
        //etRollno.setText("");
        etRollno.getText().clear();
        etReason.setText("");
        etDue.setText("");
        Toast.makeText(this,"Due Added",Toast.LENGTH_LONG).show();
        Log.d(TAG,"Due updated");
    }
}



