package com.apps.schoolyearconfirmation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    EditText editTextStudentID;
    CheckBox checkBoxAck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        editTextStudentID = (EditText) findViewById(R.id.editTextStudentID);
        checkBoxAck = (CheckBox) findViewById(R.id.checkBoxAck);
    }

    public void onClickStart(View v) {
        // Check Student ID length
        if (editTextStudentID.getText().toString().length() != 6) {
            Toast.makeText(this, "Invalid Student ID. Please provide 6 digits Student ID.", Toast.LENGTH_LONG).show();
        }

        if (checkBoxAck.isChecked() == false) {
            Toast.makeText(this, "You must be Parent/Guardian to continue.", Toast.LENGTH_SHORT).show();
        }
    }
}
