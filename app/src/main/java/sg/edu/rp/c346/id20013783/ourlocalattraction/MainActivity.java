package sg.edu.rp.c346.id20013783.ourlocalattraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTitle , etDescription , etLocation;
    RatingBar rb;
    Button btnInsert , btnShowList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        rb = findViewById(R.id.ratingBar);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnshowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String location = etLocation.getText().toString().trim();

                if(title.length()==0 || description.length()==0 || location.length()==0){
                    Toast.makeText(MainActivity.this,"Incomplete Data",Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbh = new DBHelper(MainActivity.this);
                int rating = (int)rb.getRating();
                long result = dbh.insertIsland(title, description, location, rating);
                dbh.close();

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Attraction have been inserted successfully", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etDescription.setText("");
                    etLocation.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Attraction have not been inserted successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}