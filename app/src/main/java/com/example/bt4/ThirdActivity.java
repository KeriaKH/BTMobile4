package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Student> students;
    StudentListAdapter adapter;
    StudentRepo studentRepo;

    String className;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        listView=findViewById(R.id.listview);
        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        studentRepo=new StudentRepo(this);
        students=new ArrayList<>(studentRepo.loadAllByClass(className));
        adapter=new StudentListAdapter(this,R.layout.item,students);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid=item.getItemId();
        if(itemid==R.id.option1){
            Intent addintent=new Intent(this,AddStudent.class);
            addintent.putExtra("className",className);
            startActivityForResult(addintent,1);
            return true;
        } else if (itemid == R.id.option2) {
            List<Student> StudentsToRemove = new ArrayList<>();
            for (Student student : students) {
                if (student.isSelected==true) {
                    StudentsToRemove.add(student);
                    studentRepo.deleteByName(student.name);
                }
            }
            students.removeAll(StudentsToRemove);
            StudentListAdapter adapter = new StudentListAdapter(this, R.layout.item, students);
            listView.setAdapter(adapter);
            return true;
        } else if (itemid==R.id.option3) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String date = data.getStringExtra("date");
            String lop= data.getStringExtra("lop");
            students.add(new Student(name, date,lop));
            studentRepo.addNew(new Student(name, date,lop));
            adapter.notifyDataSetChanged();
        }
    }
}
