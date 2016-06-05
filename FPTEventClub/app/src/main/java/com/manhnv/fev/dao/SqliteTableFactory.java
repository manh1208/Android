package com.manhnv.fev.dao;

import com.manhnv.fev.dto.Fund;
import com.manhnv.fev.dto.Member;

/**
 * Created by ManhNV on 12/27/2015.
 */
public class SqliteTableFactory {
    private SqliteTableFactory() {

    }
    private static SqliteTableFactory instance;

    public static SqliteTableFactory getInstance() {
        if (instance==null){
            instance= new SqliteTableFactory();
        }
        return instance;
    }

    public <TEntity extends ISqliteTable> TEntity getSqliteTableObject(Class<TEntity> c) {
         if (c ==  Member.class) {
            return (TEntity) new Member();
        }else if (c== Fund.class){
             return (TEntity) new Fund();
         }
        return null;
    }
}