package com.example.tenant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenant.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     //RecyclerView recyclerView;
    //private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    MyDatabaseHelper myDB;
    CustomAdapter customAdapter;
    ArrayList<String> tenant_id,tenant_name,tenant_balance,tenant_cell,tenant_amount;
    //Button fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //recyclerView = findViewById(R.id.recyclerView);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setSupportActionBar(binding.toolbar);
//           fab=(Button)findViewById(R.id.fab);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (MainActivity.this, AddTenant.class);
                startActivity(intent);
            }
//
        });

        myDB=new MyDatabaseHelper(MainActivity.this);
        tenant_id=new ArrayList<>();
        tenant_name=new ArrayList<>();
        tenant_balance=new ArrayList<>();
        tenant_cell=new ArrayList<>();
        tenant_amount=new ArrayList<>();

        storeDataInArrays();
        customAdapter= new CustomAdapter(MainActivity.this ,MainActivity.this,tenant_id,tenant_name,tenant_balance,tenant_cell,tenant_amount);

        binding.recyclerView.setAdapter(customAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor =myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                tenant_id.add(cursor.getString(0));
                tenant_name.add(cursor.getString(1));
                tenant_balance.add(cursor.getString(2));
                tenant_cell.add(cursor.getString(3));
                tenant_amount.add(cursor.getString(4));

            }
        }

    }


}