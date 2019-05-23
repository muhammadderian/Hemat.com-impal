package com.example.impal2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "impal.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id_usr";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_BUDGET_ALL = "allbudget";

    public static final String TABLE_NAME_BUDGET = "budget";
    public static final String COLUMN_ID_BUDGET = "id_bdgt";
    public static final String COLUMN_NAME_USR = "nameUsr";
    public static final String COLUMN_BUDGET = "budget";
    public static final String COLUMN_TYPE = "jenis";
    public static final String COLUMN_DESC = "deskripsi";
    public static final String COLUMN_TIME = "waktu";

    private static final String TABLE_CREATE = "create table "+TABLE_NAME+" (id_usr integer primary key autoincrement, " +
            "name text, pass text,allbudget integer)";
    private static final String TABLE_CREATE_BUDGET = "create table "+TABLE_NAME_BUDGET+" (id_bdgt integer primary key autoincrement, " +
            "nameUsr text, budget integer, jenis text,deskripsi text, waktu text, foreign key(nameUsr)references user(name) ON DELETE SET DEFAULT\n" +
            ")";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_BUDGET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        db.execSQL("drop table if exists "+TABLE_NAME_BUDGET);
        onCreate(db);
    }
///FUNGSI UNTUK LOGIN DAN REGISTER
    public long insertUser(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues u = new ContentValues();
        u.put(COLUMN_NAME,user);
        u.put(COLUMN_PASS,pass);

        long res = db.insert("user",null,u);
        db.close();
        return res;
    }

    public void forgotPass(String user, String password){
        db = this.getWritableDatabase();
        ContentValues u = new ContentValues();
        u.put(COLUMN_PASS,password);
        db.update("user", u,COLUMN_NAME+"='"+user+"'",null);
        db.close();
    }

    public boolean checkUser(String username, String password){
        String[] columns = { COLUMN_ID };
        db = this.getReadableDatabase();
        String selection = COLUMN_NAME + "=?" + " and " + COLUMN_PASS + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("user",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }
///FUNGSI FUNGSI UNTUK BUDGET
    public long addBudget(String a,int b, String waktu, String jenis, String desc){

        if (jenis.equals("Pengeluaran")){
            b = -b;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues u = new ContentValues();
        u.put(COLUMN_NAME_USR,a);
        u.put(COLUMN_BUDGET,b);
        u.put(COLUMN_TIME,waktu);
        u.put(COLUMN_DESC,desc);
        u.put(COLUMN_TYPE,jenis);

        long ef = db.insert("budget",null,u);
        updateBalance(a);
        db.close();
        return ef;
    }

    public void updateBalance(String key){
        db = this.getWritableDatabase();
        String updateBalance = "UPDATE user " +
                "SET allbudget= (SELECT sum(budget) FROM budget WHERE nameUsr = '"+key+"')"+
                " WHERE name = '"+key+"'";
        db.execSQL(updateBalance);
        db.close();
    }

    public Cursor getBalance(String key){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT allbudget FROM user WHERE name = '"+key+"'",null);

        return cursor;
    }
    public Cursor getHirotyBudget(String key){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM budget WHERE nameUsr = '"+key+"'",null);

        return cursor;
    }
    public void deleteBudget(int id){
        db = this.getWritableDatabase();

        String SQL = "DELETE FROM budget WHERE id_bdgt = "+id;
        db.execSQL(SQL,null);
    }

}
