package com.manhnv.fev.dto;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.manhnv.fev.dao.ISqliteTable;

import java.io.Serializable;

/**
 * Created by Nhattruong on 12/13/2015.
 */
public class Member implements Serializable, ISqliteTable, Parcelable {
        private String Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String StudentId;
    private String CurrAddress;
    private Boolean IsActive;
    private Integer SchoolYear;
    private String Department;
    private String Birthdate;
    private String Note;
    private Integer Status;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private String Role;
    private String HomeTown;
    private boolean Change;

    public boolean isChange() {
        return Change;
    }

    public void setChange(boolean change) {
        Change = change;
    }



    public Member() {
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public Member(Parcel in) {
        Id = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        StudentId = in.readString();
        CurrAddress = in.readString();
        IsActive = in.readString().toUpperCase().equals("TRUE") ? true : false;
        SchoolYear = in.readInt();
        Department = in.readString();
        Birthdate = in.readString();
        Note = in.readString();
        Status = in.readInt();
        Email = in.readString();
        Password = in.readString();
        PhoneNumber = in.readString();
        Role = in.readString();
        HomeTown = in.readString();
        Change = in.readString().toUpperCase() == "TRUE" ? true : false;
    }


    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public void setValue(Cursor cursor) {

        this.setId(cursor.getString(cursor.getColumnIndex("Id")));
        this.setFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
        this.setMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
        this.setLastName(cursor.getString(cursor.getColumnIndex("LastName")));
        this.setStudentId(cursor.getString(cursor.getColumnIndex("StudentId")));
        this.setCurrAddress(cursor.getString(cursor.getColumnIndex("CurrAddress")));
        this.setIsActive(cursor.getString(cursor.getColumnIndex("IsActive")).toUpperCase().equals("TRUE") ? true : false);
        this.setSchoolYear(cursor.getInt(cursor.getColumnIndex("SchoolYear")));
        this.setDepartment(cursor.getString(cursor.getColumnIndex("Department")));
        this.setBirthdate(cursor.getString(cursor.getColumnIndex("Birthdate")));
        this.setNote(cursor.getString(cursor.getColumnIndex("Note")));
        this.setStatus(cursor.getInt(cursor.getColumnIndex("Status")));
        this.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
        this.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
        this.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
        this.setRole(cursor.getString(cursor.getColumnIndex("Role")));
        this.setHomeTown(cursor.getString(cursor.getColumnIndex("HomeTown")));
        this.setChange(cursor.getString(cursor.getColumnIndex("Change")).toUpperCase().equals( "TRUE") ? true : false);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("Id", this.getId());
        values.put("FirstName", this.getFirstName());
        values.put("MiddleName", this.getMiddleName());
        values.put("LastName", this.getLastName());
        values.put("StudentId", this.getStudentId());
        values.put("CurrAddress", this.getCurrAddress());
        values.put("IsActive", this.getIsActive().toString());
        values.put("SchoolYear", this.getSchoolYear());
        values.put("Department", this.getDepartment());
        values.put("Birthdate", this.getBirthdate()!=null?this.getBirthdate().toString():null);
        values.put("Note", this.getNote());
        values.put("Status", this.getStatus());
        values.put("Email", this.getEmail());
        values.put("Password", this.getPassword());
        values.put("PhoneNumber", this.getPhoneNumber());
        values.put("Role", this.getRole());
        values.put("HomeTown",this.getHomeTown());
        values.put("Change",this.isChange()?"True":"False");
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getCurrAddress() {
        return CurrAddress;
    }

    public void setCurrAddress(String currAddress) {
        CurrAddress = currAddress;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean isActive) {
        IsActive = isActive;
    }

    public Integer getSchoolYear() {
        return SchoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        SchoolYear = schoolYear;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }


    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        if (note !=null && note.trim().length()>0) Note = note;
    }


    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getHomeTown() {
        return HomeTown;
    }

    public void setHomeTown(String homeTown) {
        HomeTown = homeTown;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
        dest.writeString(StudentId);
        dest.writeString(CurrAddress);
        dest.writeString(IsActive.toString());
        dest.writeInt(SchoolYear);
        dest.writeString(Department);
        dest.writeString(Birthdate == null ? null : Birthdate.toString());
        dest.writeString(Note);
        dest.writeInt(Status);
        dest.writeString(Email);
        dest.writeString(PhoneNumber);
        dest.writeString(Role);
        dest.writeString(HomeTown);
        dest.writeString(Change?"True":"False");
    }
}
