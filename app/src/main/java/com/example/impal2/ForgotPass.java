package com.example.impal2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgotPass extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        forgot();
    }

    public void forgot(){
        final EditText usr,newPwd,cPwd;
        Button yes;
        usr = (EditText)findViewById(R.id.username);
        newPwd = (EditText)findViewById(R.id.password);
        cPwd = (EditText)findViewById(R.id.email);
        yes = (Button)findViewById(R.id.confirmBtn);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usr.getText().toString();
                String newpass = newPwd.getText().toString();
                String conpass = cPwd.getText().toString();
                try {
                    boolean chck = UserIsExistsOrNot(user);
                    if (chck){
                        if (newpass.equals(conpass) && !newpass.equals("") && !conpass.equals("")){

                            db.forgotPass(user,newpass);

                            Toast.makeText(ForgotPass.this,"Ubah Password Sukses",Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(ForgotPass.this,MainActivity.class);
                            startActivity(moveToLogin);
                            finish();
                        }else{
                            Toast.makeText(ForgotPass.this,"Password Baru Tidak Valid!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ForgotPass.this,"Account Not Found!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    public boolean UserIsExistsOrNot(String a){
        String F_Result = "Not_Found";
        SQLiteDatabase check;
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

    public void back(View view){
        Intent intent = new Intent(this, com.example.impal2.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
