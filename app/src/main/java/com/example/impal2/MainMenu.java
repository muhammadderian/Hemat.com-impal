package com.example.impal2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String username = getIntent().getStringExtra("Username");
        TextView show = (TextView)findViewById(R.id.ViewUsername);
        show.setText(username);
        ///Procedure Mengambil Data Balance
        getBalance();


    }
    public void getBalance(){
        String key = getIntent().getStringExtra("Username");

        Cursor cursor = db.getBalance(key);
        StringBuilder balance = new StringBuilder();
        while(cursor.moveToNext()){
            balance.append(cursor.getString(0));
        }
        TextView show = (TextView)findViewById(R.id.ViewBalance);
        show.setText(balance);
    }


    ///Prosedur Untuk Akses Ke Activity Lain(Page Lain)


    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainMenu.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        // Setting Dialog Message
        alertDialog.setMessage("Yakin akan meninggalkan Aplikasi?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    public void exit(View v){
        backButtonHandler();
    }

    public void toBudget(View v){
        String key = getIntent().getStringExtra("Username");

        Intent intent = new Intent(this, Budget.class);
        intent.putExtra("Username",key);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_up);
    }
}
