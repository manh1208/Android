package com.manhnv.fev.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.manhnv.fev.dto.Fund;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.util.DbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 5/23/2016.
 */
public class FundDAO extends DataAccessObject<Fund> {

    public FundDAO(DbUtil dbUtil) {
        super(dbUtil, DbUtil.FUND_TABLE_NAME, "Id", Fund.class);
    }

    public List<Fund> getFive() {
        List<Fund> result = new ArrayList<>();
        SQLiteDatabase db = getDbUtil().getReadableDatabase();
        String query = "SELECT * FROM " + this.getTableName() + " ORDER BY CreateDate DESC LIMIT 5";
        String[] param = null;
        Cursor cursor = db.rawQuery(query, param);
        if (cursor.moveToFirst()) {
            do {
                Fund entity = SqliteTableFactory.getInstance().getSqliteTableObject(Fund.class);
                entity.setValue(cursor);
                result.add(entity);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
