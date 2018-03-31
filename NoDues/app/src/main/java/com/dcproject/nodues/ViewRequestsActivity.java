package com.dcproject.nodues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.dcproject.nodues.StudentDuesActivity.DEPARTMENT;
import static com.dcproject.nodues.StudentDuesActivity.ROLLNO;

public class ViewRequestsActivity extends Activity {

    ListView requests;
    FirebaseUser user;
    FirebaseAuth firebaseauth;
    public ArrayList<String> students= new ArrayList<String>();
    public String department;
    public static String TAG = "ViewRequestsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        firebaseauth=FirebaseAuth.getInstance();
        user=firebaseauth.getCurrentUser();
        requests=(ListView)findViewById(R.id.requests_list);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference request=db.child("Request");
        DatabaseReference dept=db.child("Departments");
        Log.d(TAG,"calling getDeptId");
        Query deptquery = dept.orderByChild("email").equalTo(user.getEmail());
        Log.d(TAG,"in getDeptId "+ user.getEmail() +deptquery.toString());
        deptquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "data" +dataSnapshot.toString());
                for(DataSnapshot dept1 : dataSnapshot.getChildren()){
                    Log.d(TAG, dept1.toString());
                    department = dept1.getKey();
                    getAllRequests(request.child(department));
                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"database error in getdept id");
            }
        });

    }

    // Add all requests into the list 'students'
    public void getAllRequests(DatabaseReference request){

        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students.clear();
                for(DataSnapshot student : dataSnapshot.getChildren()){
                    if(student.getValue().toString().equals("pending")){
                        students.add(student.getKey());
                    }
                }
                ListIterator list= students.listIterator();
                while(list.hasNext()){
                    String rollno=(String) list.next();
                    Log.d(TAG,"roll no "+ rollno);
                }
                displayList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void displayList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        requests.setAdapter(adapter);

        //Setting Itemclick listeners
        AdapterView.OnItemClickListener rollnoClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "In click listener " +position);
                Intent intent=new Intent(ViewRequestsActivity.this, StudentDuesActivity.class);
                intent.putExtra(ROLLNO,students.get(position));
                intent.putExtra(DEPARTMENT, department);
                startActivity(intent);
            }
        };
        requests.setOnItemClickListener(rollnoClickListener);
    }
}