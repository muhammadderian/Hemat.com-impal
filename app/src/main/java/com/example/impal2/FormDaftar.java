package com.example.impal2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FormDaftar extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    SQLiteDatabase check;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        SignUp();
    }

    public void SignUp(){
        final EditText uname, pass, conf;
        final Button add;

        uname = (EditText)findViewById(R.id.username);
        pass  = (EditText)findViewById(R.id.password);
        conf  = (EditText)findViewById(R.id.email);
        add   =  (Button)findViewById(R.id.regisBtn);

        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String u = uname.getText().toString();
                        String p = pass.getText().toString();
                        String c = conf.getText().toString();

                        Pattern PATTERN = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
                        Matcher M = PATTERN.matcher(u);

                        try {
                            if (!u.equals("") && !p.equals("") && !c.equals("") && !M.find()){
                                boolean chck = CheckingUserAlreadyExistsOrNot(u);
                                if (!chck){
                                    if (p.equals(c)){
                                        long val = db.insertUser(u,p);
                                        if(val > 0){
                                            Toast.makeText(FormDaftar.this,"Registrasi Sukses",Toast.LENGTH_SHORT).show();
                                            Intent moveToLogin = new Intent(FormDaftar.this,MainActivity.class);
                                            startActivity(moveToLogin);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(FormDaftar.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast msg = Toast.makeText(FormDaftar.this, "Password Tidak Sama", Toast.LENGTH_SHORT);
                                        msg.show();
                                    }
                                }else{
                                    Toast.makeText(FormDaftar.this,"Username Already Exists",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                if(p.isEmpty()){
                                    pass.setError("Tidak Bisa Kosong");
                                    pass.requestFocus();
                                    return;
                                }
                                if(c.isEmpty()){
                                    conf.setError("Tidak Boleh Kosong");
                                    conf.requestFocus();
                                    return;
                                }
                                if(M.find()){
                                    uname.setError("Tidak Boleh Inputan Simbol");
                                    uname.requestFocus();
                                }
                                }
                        }catch (Exception e){
                            System.out.println(e);
                            Toast msg = Toast.makeText(FormDaftar.this, "", Toast.LENGTH_SHORT);
                            msg.show();
                        }

                    }
                }
        );
    }

    public boolean CheckingUserAlreadyExistsOrNot(String a){
        // Opening SQLite database write permission.
        check = db.getWritableDatabase();
        // Adding search email query to cursor.
        Cursor cursor = check.query("user", null, DatabaseHelper.COLUMN_NAME+ "=?", new String[]{a}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "User Found";
                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        return F_Result.equals("User Found");
    }


    ///Prosedur Untuk Akses Ke Activity Lain(Page Lain)

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void back(View v){
        Intent intent = new Intent(this, com.example.impal2.MainActivity.class);
        startActivity(intent);
        finish();
    }

}
