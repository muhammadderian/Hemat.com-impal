package com.example.impal2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BudgetAdapter extends ArrayAdapter<BudgetList> {

    SQLiteDatabase db;
    DatabaseHelper helper;

    Context m;
    int layout;
    List<BudgetList> budgetLists;

    public BudgetAdapter(Context m, int layout, List<BudgetList> budgetLists){
        super(m,layout,budgetLists);
        this.m = m;
        this.layout = layout;
        this.budgetLists = budgetLists;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        LayoutInflater  inflater = LayoutInflater.from(m);

        View view = inflater.inflate(layout,null);
        TextView input = view.findViewById(R.id.inputList);
        TextView type = view.findViewById(R.id.typeList);
        TextView time = view.findViewById(R.id.timeList);
        TextView desc = view.findViewById(R.id.descList);
        //Button delete = view.findViewById(R.id.deleteBudget);

        final BudgetList budget = budgetLists.get(position);
        input.setText(String.valueOf(budget.getBudget()));
        type.setText(budget.getJenis());
        time.setText(budget.getWaktu());
        desc.setText(budget.getDeskripsi());

        //delete procedure
        /*
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(m);
                builder.setTitle("Hapus Inputan ini?");
                builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteBudget(budget.getId_bdgt());
                        reloadEmployeesFromDatabase();

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/

        return view;
    }
    private void reloadEmployeesFromDatabase() {
        Cursor cursor = db.rawQuery("SELECT * FROM budget", null);
        if (cursor.moveToFirst()) {
            budgetLists.clear();
            do {
                budgetLists.add(new BudgetList(
                        cursor.getInt(0),
                        cursor.getInt(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
