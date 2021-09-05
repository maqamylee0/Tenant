package com.example.tenant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private  Context context;
    private static final String DATABASE_NAME = "Tenant.db";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME ="my_tenant";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_NAME ="tenant_name";
    private static final String COLUMN_BALANCE ="tenant_balance";
    private static final String COLUMN_CELL = "tenant_cell";
    private static final String COLUMN_AMOUNT = "tenant_amount";



    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                " CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_BALANCE + " INTEGER, " +
                        COLUMN_CELL + " TEXT, " +
                        COLUMN_AMOUNT + " INTEGER );";
             db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
    }
    public void addTenant(String name,int balance, String cell){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BALANCE, balance);
        cv.put(COLUMN_CELL, cell);
        cv.put(COLUMN_AMOUNT, balance);


        long result= db.insert(TABLE_NAME, null,cv);
//        if(result == -1) {
//            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
//        } else{
//            Toast.makeText(context,"Succcessfully added Tenant",Toast.LENGTH_SHORT).show();
//
//        }

    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
     long UpdateData(String row_id, int balance){
         SQLiteDatabase db= this.getReadableDatabase();
         ContentValues cv=new ContentValues();
         cv.put(COLUMN_BALANCE,balance);

         long result=db.update(TABLE_NAME,cv,"id=?",new String[]{row_id});
         return result;
//

    }

    void  deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result==-1){
            Toast.makeText(context,"Failed to Delete data",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();

        }
    }
    void  deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
