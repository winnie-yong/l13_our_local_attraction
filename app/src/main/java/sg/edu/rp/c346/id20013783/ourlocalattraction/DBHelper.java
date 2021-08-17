package sg.edu.rp.c346.id20013783.ourlocalattraction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "attraction.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ATTRACTION = "attraction";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_ATTRACTION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTRACTION);
        onCreate(db);
    }
    public long insertIsland(String title, String description, String location, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_ATTRACTION, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }
    public ArrayList<attraction> getAllAttraction() {
        ArrayList<attraction> attractionList = new ArrayList<attraction>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_DESCRIPTION + ","
                + COLUMN_LOCATION + ","
                + COLUMN_STARS + " FROM " + TABLE_ATTRACTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String location = cursor.getString(3);
                int stars = cursor.getInt(4);

                attraction newAttraction = new attraction(id, title,description, location, stars);
                attractionList.add(newAttraction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attractionList;
    }
    public ArrayList<attraction> getAllAttractionByStar(int starsFilter) {
            ArrayList<attraction> attractionList = new ArrayList<attraction>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_LOCATION, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_ATTRACTION, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String location = cursor.getString(3);
                int stars = cursor.getInt(4);

                attraction newAttraction = new attraction(id, title, description, location, stars);
                attractionList.add(newAttraction);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return attractionList;
    }
    public int editAttraction(attraction data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_LOCATION,data.getLocation());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ATTRACTION, values, condition, args);
        db.close();
        return result;
    }
    public int deleteAttraction(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ATTRACTION, condition, args);
        db.close();
        return result;
    }
}
