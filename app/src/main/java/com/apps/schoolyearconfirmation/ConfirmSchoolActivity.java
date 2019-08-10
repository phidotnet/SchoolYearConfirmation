package com.apps.schoolyearconfirmation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ConfirmSchoolActivity extends AppCompatActivity {

    Button buttonConfirm;
    EditText editTextFullName;
    TextView textViewWelcomeMsg;

    StudentObject myStudent = new StudentObject();
    SharedPreferences prefStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_school);

        // Welcome Message / Full Name / Confirm Button
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        textViewWelcomeMsg = (TextView) findViewById(R.id.textViewWelcomeMsg);

        // Get StudentID from Intent sent from 1st screen
        myStudent.StudentID = getIntent().getStringExtra("g_student_ui");
        // Load student info stored in SharedPreferences
        prefStudent = getSharedPreferences("STUDENT", Context.MODE_PRIVATE);
        if (prefStudent != null) {
            String strStudentID = prefStudent.getString("student_id", null);
            if (myStudent.StudentID.equals(strStudentID)) {
                myStudent.FullName = prefStudent.getString("full_name", null);
                myStudent.SelectedSchool = prefStudent.getString("selected_school", null);
            }
        }

        // Welcome message
        textViewWelcomeMsg.setText("Welcome, " + myStudent.StudentID + "!");
        editTextFullName.setText(myStudent.FullName);

        // Select School
        Spinner listSchools = (Spinner) findViewById(R.id.spinnerSchools);
        final ArrayAdapter<CharSequence> adapterSchools = ArrayAdapter.createFromResource(this, R.array.schoolArray, android.R.layout.simple_dropdown_item_1line);
        listSchools.setAdapter(adapterSchools);
        // Check if there is an existing selected school
        for (int i = 0; i < adapterSchools.getCount(); i++) {
            if (adapterSchools.getItem(i).toString().equals(myStudent.SelectedSchool)) {
                listSchools.setSelection(i);
                break;
            }
        }

        // Select an item action
        listSchools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myStudent.SelectedSchool = adapterSchools.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Please select a school for your child!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void confirmSchoolSelection(View v) {
        // Student Full Name
        myStudent.FullName = editTextFullName.getText().toString();
        if (myStudent.FullName == null || myStudent.FullName.trim().length() == 0) {
            Toast.makeText(this, "Student Full Name cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Selected school
        if (myStudent.SelectedSchool == null || myStudent.SelectedSchool.length() == 0) {
            Toast.makeText(this, "Please select a school for your child!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save preferences and inform user
        SharedPreferences.Editor prefStudentEditor = prefStudent.edit();
        prefStudentEditor.putString("student_id", myStudent.StudentID);
        prefStudentEditor.putString("full_name", myStudent.FullName);
        prefStudentEditor.putString("selected_school", myStudent.SelectedSchool);
        prefStudentEditor.commit();

        Toast.makeText(this, "SCHOOL REGISTRATION IS SUCCESSFUL !", Toast.LENGTH_LONG).show();
    }

    public void onExit(View v) {
        finish();
    }
}
