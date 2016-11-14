package com.weightify.weightify;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rajkumar_vijayan on 11/12/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.weightify.weightify/databases/";

    private static String DB_NAME = "weightify.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public String getDBData(String meal){
        try {

            SQLiteDatabase db = openDataBase();
            String s;
            Cursor c = db.rawQuery("Select * from mealschedule", null);
            float Total = 0;

            if (c.moveToFirst()){
                do{
                    String data = c.getString(c.getColumnIndex(meal));
                    Total += Float.valueOf(data);

                    // do what ever you want here
                }while(c.moveToNext());
            }
            c.close();

//            c.moveToFirst();
//            s=c.getString(c.getColumnIndex(meal));
            db.close();
            //Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            return String.valueOf(Total);

        }catch(SQLiteException sqle){

            throw sqle;

        }
    }

    public void insertDb(String meals, String mealTime){
//        String breakfast = "8:00";
//        String lunch = "12:00";
//        String dinner = "19:00";
//        String snack1 = "10:30";
//        String snack2 = "15:00";
//        String snack3 = "21:00";

        try {
            //String insertq = "INSERT INTO mealschedule (date, breakfast, snack1, lunch, snack2, dinner, snack3) VALUES('11/18/2016','8:30am','10:30am','12:00am','3:00am','7:00am', '9:00am')";
            String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('11/18/2016', " + mealTime +")";
            SQLiteDatabase db = openDataBase();
            db.execSQL(insertq);
        }
        catch (Exception e){
        }
    }

    public SQLiteDatabase openDataBase() throws SQLiteException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
