package com.example.tenant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tenant.databinding.ActivityTenantDetailBinding;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class Tenant_Detail extends AppCompatActivity {
 int paid;
    int amount;
    int month=9,year=2021;
    int newAmount;
    private ActivityTenantDetailBinding binding;
    private static final int REQUEST_CALL=1;
    String id,tenant_name,tenant_balance,tenant_cell,tenant_amount;
    final int SECONDS_IN_A_DAY = 24 * 60 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityTenantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getIntentData();
        long leftdays=checkExpiry(this.month,this.year);
        String day="Days left :"+ leftdays;
        binding.textViewDays.setText(day);
        if (leftdays == 00000000000000000 )  //2592000 is 30 days in seconds
        {
            MyDatabaseHelper myDb = new MyDatabaseHelper(Tenant_Detail.this);
            newAmount = IncreaseAmount();

            myDb.UpdateData(id, newAmount);
            checkExpiry(this.month++,this.year++);
        }



            ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(tenant_name);

        }

        binding.buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb= new MyDatabaseHelper(Tenant_Detail.this);
                paid= Integer.parseInt(String.valueOf(binding.editTextTextAmount.getText()));
                paid=getRemainder(paid);
                myDb.UpdateData(id,paid);
                finish();
            }
        });
        binding.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             callTenant();
            }
        });
        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               confirmDialog();
            }
        });


    }

    private long checkExpiry(int month, int year) {
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH,1);
        thatDay.set(Calendar.MONTH,this.month); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, this.year);
        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;

     return days;



    }

    private void callTenant() {
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ tenant_cell));
        if(ActivityCompat.checkSelfPermission(Tenant_Detail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Tenant_Detail.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            return;
        }
        startActivity(intent);
    }

    void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("balance") &&  getIntent().hasExtra("cell") && getIntent().hasExtra("amount")){
            id=getIntent().getStringExtra("id");
            tenant_name=getIntent().getStringExtra("name");
            tenant_balance=getIntent().getStringExtra("balance");
            tenant_cell=getIntent().getStringExtra("cell");
            tenant_amount=getIntent().getStringExtra("amount");


            binding.textViewName.setText(tenant_name);
            String amount="Amount due:" + tenant_balance;
            binding.textViewAmount.setText(amount);
             String cell= "Phone Number : 0" + tenant_cell;
            binding.textViewCell.setText(cell);

        }else{
            Toast.makeText(this,"No data Available",Toast.LENGTH_SHORT).show();
        }
    }

    private int getRemainder(int paid) {

        return(parseInt(tenant_balance)-paid);
    }

    void confirmDialog(){
        AlertDialog.Builder  builder= new AlertDialog.Builder(this);
        builder.setTitle("Delete" + tenant_name + "?");
        builder.setMessage("Are you sure you want ti delete "+ tenant_name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDb= new MyDatabaseHelper(Tenant_Detail.this);

                myDb.deleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deleteAll ) {
            Toast.makeText(this, "Deleted All", Toast.LENGTH_SHORT).show();
            MyDatabaseHelper myDb= new MyDatabaseHelper(Tenant_Detail.this);

            myDb.deleteAll();
            Intent intent =new Intent (Tenant_Detail.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if(id == R.id.action_mainpage) {
            Intent intent =new Intent (Tenant_Detail.this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CALL){
            callTenant();
        }
    }
    int IncreaseAmount(){
        amount = (Integer.parseInt(tenant_balance) + Integer.parseInt(tenant_amount));
        return amount;
    }

}