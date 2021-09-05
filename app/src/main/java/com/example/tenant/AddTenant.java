package com.example.tenant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.tenant.databinding.ActivityAddTenantBinding;

import static java.lang.Integer.parseInt;

public class AddTenant extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddTenantBinding binding;
    EditText name_input,balance_input,cell_input;
   // Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTenantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar=getSupportActionBar();



        binding.addButton.setOnClickListener(v -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(AddTenant.this);
            myDb.addTenant(binding.editTextTextPersonName.getText().toString().trim(), parseInt(binding.editTextTextBalance.getText().toString()), binding.editTextTextCell.getText().toString().trim());
//
//
          Intent intent =new Intent (AddTenant.this, MainActivity.class);
           startActivity(intent);
            finish();

        });

//        SharedPreferences.Editor editor1= getSharedPreferences("vipexpire", MODE_PRIVATE).edit();
//        int  amount  = parseInt(binding.editTextTextBalance.getText().toString().trim());
//        editor1.putInt("starttime", (int) System.currentTimeMillis() / 1000);
//        editor1.putInt("initial", amount);
//        editor1.apply();
    }


}