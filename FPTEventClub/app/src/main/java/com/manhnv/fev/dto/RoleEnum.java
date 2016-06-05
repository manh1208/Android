package com.manhnv.fev.dto;

/**
 * Created by ManhNV on 12/30/2015.
 */
public enum RoleEnum {
    CN("CN"),
    PCN("PCN"),
    TQ("TQ"),
    MEMBER("Member"),
    HR("HR"),
     TBKH("TBKH"),
    TBTH("TBTH"),
    TBTT("TBTT");


    private String value;
    private RoleEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
