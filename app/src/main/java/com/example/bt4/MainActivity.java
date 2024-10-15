package com.example.bt4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class User {
    public String name;
    public String password;

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

class Student  {
    public String name;
    public String date;

    public String id;
    public String className;
    public boolean isSelected;

    Student(String name, String date,String lop) {
        this.name = name;
        this.date=date;
        this.className=lop;
        this.isSelected = false;
    }
}

class Class  {
    public String name;
    public String khoa;

    public String id;
    public boolean isSelected;

    Class(String name, String khoa) {
        this.name = name;
        this.khoa=khoa;
        this.isSelected = false;
    }
}

public class MainActivity extends AppCompatActivity {

    EditText user,password;
    private List<User> userList;

    public ClassRepo classRepo;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.user);
        password=findViewById(R.id.password);
        login=findViewById(R.id.button);
        userList = new ArrayList<>();
        userList.add(new User("user1", "pass1"));
        userList.add(new User("user2", "pass2"));
        classRepo=new ClassRepo(this);
        List<Class> classList = classRepo.loadAll();
        if (classList.isEmpty()) {
            classRepo.addNew(new Class("A", "CNPM"));
            classRepo.addNew(new Class("B", "CNPM"));
            classRepo.addNew(new Class("C", "CNPM"));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUser(user.getText().toString(), password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean checkUser(String name, String password) {
        for (User user : userList) {
            if (user.name.equals(name) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }
}

class StudentListAdapter extends ArrayAdapter<Student> {
    int resource;
    private List<Student> students;

    public StudentListAdapter(Context context, int resource, List<Student> students) {
        super(context, resource, students);
        this.students=students;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }

        Student student =getItem(position);
        if (student != null) {
            CheckBox checkBox = v.findViewById(R.id.checkBox);
            TextView nameTextView = v.findViewById(R.id.textView);
            TextView passwordTextView = v.findViewById(R.id.textView2);
            if (nameTextView != null) {
                nameTextView.setText(student.name);
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    student.isSelected = isChecked;
                });
            }
            if (passwordTextView != null) {
                passwordTextView.setText(student.date);
            }
        }
        return v;
    }
}

class ClassListAdapter extends ArrayAdapter<Class> {
    int resource;
    private List<Class> classes;

    public ClassListAdapter(Context context, int resource, List<Class> classes) {
        super(context, resource, classes);
        this.classes=classes;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }

        Class aClass = getItem(position);
        if (aClass != null) {
            CheckBox checkBox = v.findViewById(R.id.checkBox);
            TextView nameTextView = v.findViewById(R.id.textView);
            TextView passwordTextView = v.findViewById(R.id.textView2);
            if (nameTextView != null) {
                nameTextView.setText(aClass.name);
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    aClass.isSelected = isChecked;
                });
            }
            if (passwordTextView != null) {
                passwordTextView.setText(aClass.khoa);
            }
        }
        return v;
    }
}