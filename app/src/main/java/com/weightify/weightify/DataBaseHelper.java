package com.weightify.weightify;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by rajkumar_vijayan on 11/12/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.weightify.weightify/databases/";

    private static String DB_NAME = "weightifyschedule.db";

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
        String tDate = GetCurrentDate();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();
                String insertAll = "INSERT INTO mealschedule (date, breakfast, snack1, lunch, snack2, dinner, snack3) VALUES('"+ tDate +"','8:30','10:30','12:00','15:00','19:00', '20:00')";
                //String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('11/18/2016', " + mealTime +")";
                //String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('11/18/2016', '" + mealTime +"')";

                //checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                String myPath = DB_PATH + DB_NAME;
                SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                Log.d("Debug", insertAll);
                //System.out.println(insertq);
                db.execSQL(insertAll);

                //                myDbHelper.insertDb("breakfast", "8:00am");
//                myDbHelper.insertDb("snack1", "10:15am");
//                myDbHelper.insertDb("snack2", "3:45pm");
//                myDbHelper.insertDb("snack3", "8:30pm");
//                myDbHelper.insertDb("lunch", "12:30pm");
//                myDbHelper.insertDb("dinner", "7:00pm");

            } catch (IOException e) {
                Log.d("Debug", "Insert failed");
                throw new Error("Error copying database");

            }
        }

    }


    public String GetCurrentDate() {

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        return df.format(dateobj).toString();

    }

    public int getTotalUniqRows(){

        try {

            SQLiteDatabase db = openDataBase();
            String s;
            Cursor c = db.rawQuery("SELECT  count(DISTINCT(date)) FROM mealschedule", null);
            int count = 0;


            if(null != c) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    count = c.getInt(0);
                }
                c.close();
            }
            Log.d("Count", Integer.toString(count));

            return count;
//             c.moveToFirst();
//             s=c.getString(c.getColumnIndex(meal));
//            db.close();
//            //Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            //return String.valueOf(Total);
        }catch(SQLiteException sqle){

            throw sqle;

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
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

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
            int count = getTotalUniqRows();
            SQLiteDatabase db = openDataBase();
            String s;
            Cursor c = db.rawQuery("Select * from mealschedule", null);
            int Total = 0;
            int hours = 0;
            int mins = 0;
            String data;


            c.moveToFirst();
            do{
                //Log.d("Data:", c.getString(c.getColumnIndex(meal)));
                data = c.getString(c.getColumnIndex(meal));
                Total += 1;

                if (!data.equals("0")) {
                    String[] splittime = data.split(":");
                    hours += Integer.valueOf(splittime[0]);
                    mins += Integer.valueOf(splittime[1]);
                }

                //Total += Integer.valueOf(data);
                //return data;

                    // do what ever you want here
            }while(c.moveToNext());
            int newhour = (hours + (mins/60))/count;
            int newmins = (mins % 60)/count;

            c.close();
//             c.moveToFirst();
//             s=c.getString(c.getColumnIndex(meal));
//            db.close();
//            //Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            //return String.valueOf(Total);
            return String.valueOf(newhour)+":"+String.valueOf(newmins);
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
        String tDate = GetCurrentDate();


        try {
            //******
            SQLiteDatabase db = openDataBase();
            String s;
            String selectq = "SELECT " + meals + " FROM mealschedule where date like '%"+tDate+"%'";
            Cursor c = db.rawQuery(selectq, null);
            String data;
            if (!(c.moveToFirst()) || c.getCount() ==0){
                String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('"+ tDate +"', '" + mealTime +"')";

                //checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                String myPath = DB_PATH + DB_NAME;
                SQLiteDatabase db1 = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                Log.d("Debug", insertq);
                //System.out.println(insertq);
                db1.execSQL(insertq);
            } else {
                String updateq = "update mealschedule set "+meals+"='"+mealTime+ "' where date = '"+tDate+"'";
                String myPath = DB_PATH + DB_NAME;
                SQLiteDatabase db2 = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                Log.d("Debug", updateq);
                //System.out.println(insertq);
                db2.execSQL(updateq);

            }



            //String insertq = "INSERT INTO mealschedule (date, breakfast, snack1, lunch, snack2, dinner, snack3) VALUES('11/18/2016','8:30am','10:30am','12:00am','3:00am','7:00am', '9:00am')";
            //String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('11/18/2016', " + mealTime +")";
//            String insertq = "INSERT INTO mealschedule (date, "+ meals +") VALUES('"+ tDate +"', '" + mealTime +"')";
//
//            //checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//            String myPath = DB_PATH + DB_NAME;
//            SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//            Log.d("Debug", insertq);
//            //System.out.println(insertq);
//            db.execSQL(insertq);
        }
        catch (Exception e){
            Log.d("Debug", "Insert failed");
        }
    }

    public void deleteDB(){

        try {

            String delTable = "DELETE FROM mealschedule";

            //checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            String myPath = DB_PATH + DB_NAME;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d("Debug", delTable);
            //System.out.println(insertq);
            db.execSQL(delTable);
        }
        catch (Exception e){
            Log.d("Debug", "Delete failed");
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
