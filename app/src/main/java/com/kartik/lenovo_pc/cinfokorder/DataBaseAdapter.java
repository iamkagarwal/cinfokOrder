package com.kartik.lenovo_pc.cinfokorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class DataBaseAdapter extends SQLiteOpenHelper {
    static final String NAME="Dishes";
    static final String CARTNAME="cartitems";
    static final String ORDERS="orderss";
    static final String DB_NAME="FoodOrder";
    private static final int VERSION=3;
    final static String DISH_NAME = "name";
    final static String PRICE = "price";
    final static String DESCRIPTION = "description";
    final static String DISHES="alldishes";
    final static String TOTALPRICE="totalpriceordered";
    final static String TYPE = "type";
    final static String AMOUNT = "amount";
    public final Context mContext;
    final static String _ID = "_id";
    final static String NO="number";
    final static String[] columns = { _ID,DISH_NAME,PRICE,DESCRIPTION,TYPE };
    final static String[] cartColumns = { _ID,DISH_NAME,PRICE,DESCRIPTION,TYPE,NO,AMOUNT };
    final static String[] orderColumns={_ID,DISHES,TOTALPRICE };


    final private static String CREATE_CMD = "CREATE TABLE " + NAME + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DISH_NAME + " TEXT NOT NULL, "+ PRICE +" TEXT NOT NULL, "+ DESCRIPTION +
            " TEXT NOT NULL, "+ TYPE +" TEXT NOT NULL )";

    final private static String CREATE_CMD1 = "CREATE TABLE " + CARTNAME + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DISH_NAME + " TEXT NOT NULL, "+ PRICE +" TEXT NOT NULL, "+ DESCRIPTION +
            " TEXT NOT NULL, "+ TYPE +" TEXT NOT NULL, "+ NO +" TEXT NOT NULL, "+ AMOUNT +" TEXT NOT NULL )";


    final private static String CREATE_CMD2 = "CREATE TABLE if not exists " + ORDERS + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DISHES + " TEXT NOT NULL, "+ TOTALPRICE + " TEXT NOT NULL )";


    public DataBaseAdapter(Context c)
    {
        super(c,DB_NAME,null,VERSION);
        this.mContext=c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CMD);
        sqLiteDatabase.execSQL(CREATE_CMD1);
        sqLiteDatabase.execSQL(CREATE_CMD2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DataBaseAdapter.NAME);
        onCreate(sqLiteDatabase);
    }
}
