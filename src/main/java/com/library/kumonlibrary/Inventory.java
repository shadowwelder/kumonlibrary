package com.library.kumonlibrary;

public class Inventory {

    private String name;
    private String checkedOutBy;
    private String dateCheckedOut;

    public Inventory(String name, String checkedOutBy, String dateCheckedOut){
        this.name = name;
        this.checkedOutBy = checkedOutBy;
        this.dateCheckedOut = dateCheckedOut;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(String checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public String getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(String dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
    }
}
