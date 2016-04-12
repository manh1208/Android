package com.manhnv.fev.response;

import com.manhnv.fev.dto.Member;

import java.util.List;

/**
 * Created by ManhNV on 4/2/2016.
 */
public class SyncResponse {
    private boolean status;
    private String message;
    private List<Member> members;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
