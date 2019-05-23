package com.example.impal2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this) ;
    EditText username,password;
    Button click;
    String usr,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login(); //procedure untuk login
    }

    public void login() {
        click = (Button)findViewById(R.id.inBtn);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = username.getText().toString();
                pwd = password.getText().toString();
                try {
                        boolean res = db.checkUser(usr,pwd);
                        if(res){
                            Intent HomePage = new Intent(MainActivity.this,MainMenu.class);
                            HomePage.putExtra("Username",usr);
                            startActivity(HomePage);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this,"Username / Password Salah",Toast.LENGTH_SHORT).show();
                        }
                }catch (Exception e){
                        System.out.println("The input is wrong or never been registered");
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setMessage("Maaf Password atau username anda salah");
                        alert.setTitle("Fill With Correct User/Password");
                        alert.setPositiveButton("ok",null);
                        alert.setCancelable(true);
                        alert.create().show();

                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                }
            }
        });
    }



    ///Prosedur Untuk Akses Ke Activity Lain(Page Lain)


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void toSignUP(View v) {
        Intent intent = new Intent(this, FormDaftar.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void toForget(View v){
        Intent intent = new Intent(this, ForgotPass.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
