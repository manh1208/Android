package com.manhnv.fev.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.manhnv.fev.dto.Member;
import com.manhnv.fev.util.DbUtil;

/**
 * Created by ManhNV on 12/27/2015.
 */
public class MemberDAO extends DataAccessObject<Member>{

    public MemberDAO(DbUtil dbUtil) {
        super(dbUtil, DbUtil.MEMBERS_TABLE_NAME, "Id", Member.class);
    }

    public Member checkLogin(String email, String password) {
        Member result=null;
        SQLiteDatabase db = getDbUtil().getReadableDatabase();
        String query = "SELECT * FROM " + this.getTableName() + " WHERE Email = ? AND Password = ?";
        String[] param = new String[]{email,password};
        Cursor cursor = db.rawQuery(query, param);
        if (cursor.moveToFirst()) {
            do {
                Member entity = SqliteTableFactory.getInstance().getSqliteTableObject(Member.class);
                entity.setValue(cursor);
                result=entity;
            } while (cursor.moveToNext());

        }
        return result;
    }

    public Member checkEmailEdit(String email, String id) {
        Member result=null;
        SQLiteDatabase db = getDbUtil().getReadableDatabase();
        String query = "SELECT * FROM " + this.getTableName() + " WHERE Email = ? AND Id != ?";
        String[] param = new String[]{email,id};
        Cursor cursor = db.rawQuery(query, param);
        if (cursor.moveToFirst()) {
            do {
                Member entity = SqliteTableFactory.getInstance().getSqliteTableObject(Member.class);
                entity.setValue(cursor);
                result=entity;
            } while (cursor.moveToNext());

        }
        return result;
    }


}
