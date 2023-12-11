package com.example.quanlyactivity.model;

public class NotiResponse {
    private long nulticast_id;
    private int success,failure,canonical_ids;

    public NotiResponse(){

    }

    public long getNulticast_id() {
        return nulticast_id;
    }

    public void setNulticast_id(long nulticast_id) {
        this.nulticast_id = nulticast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }
}
