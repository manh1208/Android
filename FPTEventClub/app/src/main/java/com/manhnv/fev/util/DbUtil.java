package com.manhnv.fev.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nhattruong on 12/13/2015.
 */
public class DbUtil extends SQLiteOpenHelper {
    public static final String MEMBERS_TABLE_NAME = "Members";
    private static final String CREATE_MEMBERS_TABLE_SCRIPT =
            "CREATE TABLE " + MEMBERS_TABLE_NAME + "(" +
                    "Id Text Primary Key ," +
    "FirstName Text," +
    "MiddleName Text," +
    "LastName Text," +
    "StudentId Text," +
    "CurrAddress Text," +
    "IsActive Text," +
    "SchoolYear Integer," +
    "Department Text," +
    "Birthdate Text," +
    "Note Text," +
    "Status Integer," +
    "Email Text," +
    "UserName Text," +
    "Password Text," +
                    "HomeTown Text,"+
    "PhoneNumber Text," +

    "Role Text,"+ "Change Text)";

    public DbUtil(Context context) {
        super(context, "FEV.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEMBERS_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {

        }
    }
}
