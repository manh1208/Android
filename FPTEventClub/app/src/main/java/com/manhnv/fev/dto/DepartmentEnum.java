package com.manhnv.fev.dto;

/**
 * Created by ManhNV on 12/30/2015.
 */
public enum  DepartmentEnum {
    KEHOACH(1),
    THUCHIEN(2),
    TRUYENTHONG(3);



    private int value;
    private DepartmentEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
