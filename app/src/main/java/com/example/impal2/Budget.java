package com.example.impal2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Budget extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db = new DatabaseHelper(this);
    EditText inpt, desk;
    Spinner spin;
    List<BudgetList> budgetLists;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        budgetLists = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listViewBudget);
        loadHistoryBudget();

        getBalance();
        inpt = findViewById(R.id.inputBudget);
        desk = findViewById(R.id.inputDesc);
        spin = findViewById(R.id.spinner);

        findViewById(R.id.inputBtn).setOnClickListener(this);

    }

    private void addBudget(){
        String input = inpt.getText().toString();
        String Desc = desk.getText().toString();
        String type = spin.getSelectedItem().toString();
        String key = getIntent().getStringExtra("Username");

        try {
            int num = Integer.parseInt(input);
            Log.i("",num+" is a number");
        } catch (NumberFormatException e) {
            Log.i("",input+" is not a number");
        }

        if(input.isEmpty()){
           inpt.setError("Value Tidak Bisa Kosong");
           inpt.requestFocus();
           return;
        }
        if(Desc.isEmpty()){
            desk.setText("Gatau ini apa lupa");
            return;
        }
        if(Desc.isEmpty()){
            desk.setText("Gatau ini apa lupa");
            return;
        }

        try {
            int num = Integer.parseInt(input);
            Log.i("",num+" is a number");

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String Date = sdf.format(cal.getTime());

            try {
                if (num < 99999999 ){
                    long val = db.addBudget(key,num,Date,type,Desc);
                    if(val > 0){
                        Toast.makeText(Budget.this,"Input Budget Berhasil",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Budget.class);
                        intent.putExtra("Username",key);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        Toast.makeText(Budget.this, "Input Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    inpt.setError("Value Tidak Bisa Valid");
                    inpt.requestFocus();
                    return;
                }
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(Budget.this,"Inptan tidak valid",Toast.LENGTH_SHORT).show();
                System.out.println(e.toString());
            }

        }catch (NumberFormatException e){
            Log.i("",input+" is not a number");
            Toast.makeText(Budget.this,"Inptan tidak valid",Toast.LENGTH_SHORT).show();
        }

    }
    public void getBalance(){
        String key = getIntent().getStringExtra("Username");

        Cursor cursor = db.getBalance(key);
        StringBuilder balance = new StringBuilder();
        while(cursor.moveToNext()){
            balance.append(cursor.getString(0));
        }
        TextView show = (TextView)findViewById(R.id.balance);
        show.setText(balance);
    }
    public void loadHistoryBudget(){
        String key = getIntent().getStringExtra("Username");
        Cursor cursor = db.getHirotyBudget(key);

        if (cursor.moveToFirst()){
            do {
                budgetLists.add(new BudgetList(
                        cursor.getInt(0),
                        cursor.getInt(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            }while (cursor.moveToNext());

            BudgetAdapter adapter = new BudgetAdapter(this,R.layout.list_layout,budgetLists);
            listView.setAdapter(adapter);
        }
    }

    ///Prosedur Untuk Akses Ke Activity Lain(Page Lain)

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    */

    @Override
    public void onClick(View v) {
        addBudget();
    }

    public void back(View v){
        String key = getIntent().getStringExtra("Username");

        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra("Username",key);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);


    }
}
