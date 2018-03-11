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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class StudentActivity extends Activity {

    Button logout, request ;
    // Creating TextView.
    TextView userEmailShow ;
    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;
    // Creating FirebaseAuth.
    FirebaseUser firebaseUser;
    public String rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        logout=(Button)findViewById(R.id.logout);
        request=(Button)findViewById(R.id.request);
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

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser user=firebaseAuth.getCurrentUser();
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                Query student = db.child("Students").orderByChild("email").equalTo(user.getEmail());

                student.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot stu : dataSnapshot.getChildren()){
                            rollno =stu.getKey();
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference department = db.child("Departments");
                department.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dept : dataSnapshot.getChildren()){
                            DatabaseReference request = db.child("Request").child(dept.getKey()).child(rollno);
                                request.setValue("pending");
                        }
                        Toast.makeText(StudentActivity.this, "Requests sent", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
