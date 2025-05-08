package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudent extends AppCompatActivity {
    EditText name,date;
    Button add,cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudent);
        String className = getIntent().getStringExtra("className");
        name=findViewById(R.id.name);
        date=findViewById(R.id.date);
        add=findViewById(R.id.okButton);
        cancel=findViewById(R.id.cancelButton);
        cancel.setOnClickListener(v -> {
            finish();
        });
        add.setOnClickListener(v -> {
            String nameText=name.getText().toString();
            String dateText=date.getText().toString();
            if (nameText.isEmpty() || dateText.isEmpty()) {
                Toast.makeText(AddStudent.this,"please fill all field",Toast.LENGTH_LONG).show();}
            else {
                Toast.makeText(AddStudent.this,"add student successfully",Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", nameText);
                resultIntent.putExtra("date",dateText);
                resultIntent.putExtra("lop",className);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
