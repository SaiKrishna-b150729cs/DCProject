package com.dcproject.nodues;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDuesActivity extends Activity {

    public static final String ROLLNO = "student_rollnumber", DEPARTMENT="department_id";
    TextView rollnoShow;
    Button approve, reject;
    String studentRollno, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dues);

        rollnoShow=(TextView)findViewById(R.id.student_rollno);
        approve=(Button) findViewById(R.id.approve_button);
        reject=(Button)findViewById(R.id.reject_button);
        studentRollno=getIntent().getExtras().get(ROLLNO).toString();
        department=getIntent().getExtras().get(DEPARTMENT).toString();
        rollnoShow.setText(studentRollno);



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


    }

    public void approveRequest(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        DatabaseReference request=db.child("Request").child(department).child(studentRollno);
        request.setValue("approved");
        Toast.makeText(StudentDuesActivity.this, "Approved Succesfully", Toast.LENGTH_LONG ).show();
        finish();
    }

    public void rejectRequest(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        DatabaseReference request=db.child("Request").child(department).child(studentRollno);
        request.setValue("rejected");
        Toast.makeText(StudentDuesActivity.this, "Rejected Succesfully", Toast.LENGTH_LONG ).show();
        finish();
    }
}
