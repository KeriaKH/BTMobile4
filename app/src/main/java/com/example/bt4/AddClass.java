package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddClass extends AppCompatActivity {
    EditText name,khoa;
    Button add,cancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclass);
        name=findViewById(R.id.name);
        khoa=findViewById(R.id.khoa);
        add=findViewById(R.id.okButton);
        cancel=findViewById(R.id.cancelButton);
        cancel.setOnClickListener(v -> {
            finish();
        });
        add.setOnClickListener(v -> {
            String nameText=name.getText().toString();
            String khoaText=khoa.getText().toString();
            if (nameText.isEmpty() || khoaText.isEmpty()) {
                Toast.makeText(AddClass.this,"please fill all field",Toast.LENGTH_LONG).show();}
            else {
                Toast.makeText(AddClass.this,"add student successfully",Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", nameText);
                resultIntent.putExtra("khoa",khoaText);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
