package com.example.student.labtute5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }

    void ClearAll(){
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtConNo.setText("");
    }
}
