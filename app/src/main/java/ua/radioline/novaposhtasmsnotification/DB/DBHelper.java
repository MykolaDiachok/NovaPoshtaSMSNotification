package ua.radioline.novaposhtasmsnotification.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ua.radioline.novaposhtasmsnotification.MainActivity;

/**
 * Created by mikoladyachok on 1/4/16.
 */
public class DBHelper extends SQLiteOpenHelper {




    public static abstract class SMSInfo implements BaseColumns {
        public static final String TABLE_NAME = "smsinfo";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_NPID = "npid";
        public static final String COLUMN_info = "info";
        public static final String COLUMN_date = "smsdate";
        public static final String COLUMN_send = "send";
    }


    public static final String DATABASE_NAME = "SMSInfo.db";

    private HashMap hp;

    public DBHelper()
    {
        super(MainActivity.getContextOfApplication(), DATABASE_NAME , null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + SMSInfo.TABLE_NAME + " " +
                        "(" + SMSInfo.COLUMN_ID + " integer primary key," +
                        " " + SMSInfo.COLUMN_NAME + " text," +
                        " " + SMSInfo.COLUMN_PHONE + " text," +
                        " " + SMSInfo.COLUMN_NPID + " text," +
                        " " + SMSInfo.COLUMN_info + " text," +
                        " " + SMSInfo.COLUMN_date + " datatime," +
                        " " + SMSInfo.COLUMN_send + " BOOLEAN)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+SMSInfo.TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String name, String phone, String npid, String info, boolean send)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMSInfo.COLUMN_NAME, name);
        contentValues.put(SMSInfo.COLUMN_PHONE, phone);
        contentValues.put(SMSInfo.COLUMN_NPID, npid);
        contentValues.put(SMSInfo.COLUMN_info, info);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        contentValues.put(SMSInfo.COLUMN_date, dateFormat.format(date));
        contentValues.put(SMSInfo.COLUMN_send, send);
        db.insert(SMSInfo.TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+SMSInfo.TABLE_NAME+" where "+SMSInfo.COLUMN_ID+"="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SMSInfo.TABLE_NAME);
        return numRows;
    }

    public boolean update(Integer id, String name, String phone, String npid, String info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMSInfo.COLUMN_NAME, name);
        contentValues.put(SMSInfo.COLUMN_PHONE, phone);
        contentValues.put(SMSInfo.COLUMN_NPID, npid);
        contentValues.put(SMSInfo.COLUMN_info, info);
        db.update(SMSInfo.TABLE_NAME, contentValues, SMSInfo.COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer delete(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SMSInfo.TABLE_NAME,
                SMSInfo.COLUMN_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }

    public boolean getByNPID(String npID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+SMSInfo.TABLE_NAME+" where " + SMSInfo.COLUMN_NPID +"='"+npID + "' order by "+SMSInfo.COLUMN_date+" desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            return true;

        }
        return false;
    }

    public ArrayList<String> getAll()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+SMSInfo.TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(SMSInfo.TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}