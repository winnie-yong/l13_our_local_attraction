package sg.edu.rp.c346.id20013783.ourlocalattraction;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID,etTitle,etDescription,etLocation;
    Button btnUpdate , btnDelete , btnCancel;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etThirdTitle);
        etDescription = findViewById(R.id.etThirdDescription);
        etLocation = findViewById(R.id.etThirdLocation);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btndelete);
        btnCancel = findViewById(R.id.cancel);
        rb = findViewById(R.id.ratingBar);

        Intent i = getIntent();
        final attraction currentAttraction = (attraction) i.getSerializableExtra("attraction");

        etID.setEnabled(false);
        etID.setText(currentAttraction.getId()+"");
        etTitle.setText(currentAttraction.getTitle());
        etDescription.setText(currentAttraction.getDescription());
        etLocation.setText(currentAttraction.getLocation());
        rb.setRating(currentAttraction.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Update");
                myBuilder.setMessage("Are you sure you want to update the attraction\n" + currentAttraction.getTitle());
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        currentAttraction.setTitle(etTitle.getText().toString().trim());
                        currentAttraction.setDescription(etDescription.getText().toString().trim());
                        currentAttraction.setLocation(etLocation.getText().toString().trim());
                        currentAttraction.setStars((int)rb.getRating());
                        int result = dbh.editAttraction(currentAttraction);
                        if (result>0){
                            Toast.makeText(ThirdActivity.this, "Attraction updated", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNeutralButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Delete");
                myBuilder.setMessage("Are you sure you want to delete the attraction\n" + currentAttraction.getTitle());
                myBuilder.setCancelable(false);

                //Configure the positive button
                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteAttraction(currentAttraction.getId());
                        if (result>0) {
                            Toast.makeText(ThirdActivity.this, "Attraction deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });

                // Configure the neutral button
                myBuilder.setNeutralButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}