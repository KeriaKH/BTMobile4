package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Class> Classes;
    ClassListAdapter adapter;
    private ClassRepo classRepo;
    private StudentRepo studentRepo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        studentRepo=new StudentRepo(this);
        if(studentRepo.loadAll().isEmpty())
        {
            studentRepo.addNew(new Student("N1", "01/01/2000", "A"));
            studentRepo.addNew(new Student("N2", "02/02/2001", "A"));
            studentRepo.addNew(new Student("N3", "03/03/2002", "B"));
            studentRepo.addNew(new Student("N4", "03/03/2002", "B"));
            studentRepo.addNew(new Student("N5", "03/03/2002", "C"));
            studentRepo.addNew(new Student("N6", "03/03/2002", "C"));
        }
        classRepo=new ClassRepo(this);
        List<Class> classList = classRepo.loadAll(); // Fetch from database
        Classes = new ArrayList<>(classList);
        listView=findViewById(R.id.listview);
        adapter=new ClassListAdapter(this,R.layout.item,Classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(SecondActivity.this,ThirdActivity.class);
                intent1.putExtra("className", Classes.get(position).name);
                startActivity(intent1);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid=item.getItemId();
        if(itemid==R.id.option1){
            Intent addintent=new Intent(this,AddClass.class);
            startActivityForResult(addintent,1);
            return true;
        } else if (itemid == R.id.option2) {
            List<Class> ClassesToRemove = new ArrayList<>();
            for (Class aclass : Classes) {
                if (aclass.isSelected==true) {
                    ClassesToRemove.add(aclass);
                    classRepo.delete(aclass.name);
                }
            }
            Classes.removeAll(ClassesToRemove);
            ClassListAdapter adapter = new ClassListAdapter(this, R.layout.item, Classes);
            listView.setAdapter(adapter);
            return true;
        }
        else
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String khoa = data.getStringExtra("khoa");
            Classes.add(new Class(name, khoa));
            classRepo.addNew(new Class(name, khoa));
            adapter.notifyDataSetChanged();
        }
    }
}

