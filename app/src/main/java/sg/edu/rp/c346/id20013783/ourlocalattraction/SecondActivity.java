package sg.edu.rp.c346.id20013783.ourlocalattraction;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btnShow ,btntoinsert;
    ListView lv;
    ArrayList<attraction> attractionList;
    CustomAdapter adapter;
    int requestCode = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnShow = findViewById(R.id.btnShowThrill);
        btntoinsert = findViewById(R.id.btntoInsert);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(this);
        attractionList = dbh.getAllAttraction();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, attractionList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("attraction", attractionList.get(position));
                startActivityForResult(i, requestCode);
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                attractionList.clear();
                attractionList.addAll(dbh.getAllAttractionByStar(5));
                adapter.notifyDataSetChanged();
            }
        });
        btntoinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inflate the input.xml layout file
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);

                final EditText etInputTitle = viewDialog.findViewById(R.id.etTitle);
                final EditText etInputDescription = viewDialog.findViewById(R.id.etDescription);
                final EditText etLocation = viewDialog.findViewById(R.id.etLocation);
                final RatingBar etRating = viewDialog.findViewById(R.id.ratingBar);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert Attraction");
                myBuilder.setPositiveButton("INSERT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etInputTitle.getText().toString().trim();
                        String description = etInputDescription.getText().toString().trim();
                        String location = etLocation.getText().toString().trim();
                        if (title.length() == 0 || description.length() == 0 || location.length()==0) {
                            Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        int rating = (int) etRating.getRating();
                        long result = dbh.insertIsland(title, description, location, rating);
                        adapter.notifyDataSetChanged();
                        dbh.close();
                        if (result != -1) {
                            Toast.makeText(SecondActivity.this, "Island inserted", Toast.LENGTH_LONG).show();
                            etInputTitle.setText("");
                            etInputDescription.setText("");
                            etLocation.setText("");
                        } else {
                            Toast.makeText(SecondActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            DBHelper dbh = new DBHelper(this);
            attractionList.clear();
            attractionList.addAll(dbh.getAllAttraction());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}