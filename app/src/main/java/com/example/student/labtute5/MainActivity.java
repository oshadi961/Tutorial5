package com.example.student.labtute5;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtName, txtAddress, txtConNo;
    Button btnSave, btnShow, btnUpdate,btnDelete;

    Student student;

    DatabaseReference dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtConNo = findViewById(R.id.txtConNo);

        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        student = new Student();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setID(txtID.getText().toString());
                student.setName(txtName.getText().toString());
                student.setAddress(txtAddress.getText().toString());
                student.setConNo(Integer.parseInt(txtConNo.getText().toString()));

                dbref = FirebaseDatabase.getInstance().getReference().child("Student");
//                dbref.push().setValue(student);
                dbref.child("KeyForUser").setValue(student);
//                dbref.child(student.getID()).setValue(student);

                Toast.makeText(getApplicationContext(), "Database Save Successfully", Toast.LENGTH_SHORT).show();



                ClearAll();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("KeyForUser");

            readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()){
                    txtID.setText(dataSnapshot.child("id").getValue().toString());
                    txtName.setText(dataSnapshot.child("name").getValue().toString());
                    txtAddress.setText(dataSnapshot.child("address").getValue().toString());
                    txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Student");

                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("KeyForUser")){
                            student.setID(txtID.getText().toString().trim());
                            student.setName(txtName.getText().toString().trim());
                            student.setAddress(txtAddress.getText().toString().trim());
                            student.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                            dbref = FirebaseDatabase.getInstance().getReference().child("Student").child("KeyForUser");

                            dbref.setValue(student);
                            ClearAll();

                            Toast.makeText(getApplicationContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "No Source to Update",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");

                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("KeyForUser")){
                            dbref = FirebaseDatabase.getInstance().getReference().child("Student").child("KeyForUser");
                            dbref.removeValue();
                            ClearAll();

                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "No Source to Delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    void ClearAll(){
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtConNo.setText("");
    }
}
