package com.manhnv.fev.dto;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.manhnv.fev.dao.ISqliteTable;

import java.io.Serializable;

/**
 * Created by ManhNV on 5/23/2016.
 */
public class Fund implements Serializable, ISqliteTable, Parcelable {

    private Integer Id ;
    private String Creator ;
    private String CreateDate;
    private String Resource;
    private Integer Amount;
    private Integer Type;
    private String Note;

    public Fund(){

    }

    protected Fund(Parcel in) {
        Id = in.readInt();
        Creator = in.readString();
        CreateDate = in.readString();
        Resource = in.readString();
        Amount = in.readInt();
        Type = in.readInt();
        Note = in.readString();
    }

    public static final Creator<Fund> CREATOR = new Creator<Fund>() {
        @Override
        public Fund createFromParcel(Parcel in) {
            return new Fund(in);
        }

        @Override
        public Fund[] newArray(int size) {
            return new Fund[size];
        }
    };

    @Override
    public void setValue(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        this.setCreator(cursor.getString(cursor.getColumnIndex("Creator")));
        this.setCreateDate(cursor.getString(cursor.getColumnIndex("CreateDate")));
        this.setResource(cursor.getString(cursor.getColumnIndex("Resource")));
        this.setAmount(cursor.getInt(cursor.getColumnIndex("Amount")));
        this.setType(cursor.getInt(cursor.getColumnIndex("Type")));
        this.setNote(cursor.getString(cursor.getColumnIndex("Note")));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("Id",this.getId());
        values.put("Creator",this.getCreator());
        values.put("CreateDate",this.getCreateDate());
        values.put("Resource",this.getResource());
        values.put("Amount",this.getAmount());
        values.put("Type",this.getType());
        values.put("Note",this.getNote());
        return values;
    }

    @Override
    public String getPrimaryValue() {
        return String.valueOf(this.Id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getCreator());
        dest.writeString(this.getCreateDate());
        dest.writeString(this.getResource());
        dest.writeInt(this.getAmount());
        dest.writeInt(this.getType());
        dest.writeString(this.getNote());
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getResource() {
        return Resource;
    }

    public void setResource(String resource) {
        Resource = resource;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
}
