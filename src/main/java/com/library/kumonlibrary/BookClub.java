/*
Jafar Hashim
AP CS-A | Cloud Computing
April 27 2023
 */

package com.library.kumonlibrary;

public class BookClub {

    private String checkedOutBy;
    private String dateCheckedOut;
    private int id;
    private String bookName;
    private String authorName;
    private int totalAmt;
    private int amtAvailable;
    private String level;
    private boolean active;

    public BookClub(String bookName,int totalAmt, int amtAvailable, String level, String checkedOutBy, String dateCheckedOut) {
        this.id = id;
        this.bookName = bookName;
        //this.authorName = authorName;
        this.totalAmt = totalAmt;
        this.amtAvailable = amtAvailable;
        this.level = level;
        this.checkedOutBy = checkedOutBy;
        this.dateCheckedOut = dateCheckedOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(int totalAmt) {
        this.totalAmt = totalAmt;
    }

    public int getAmtAvailable() {
        return amtAvailable;
    }

    public void setAmtAvailable(int amtAvailable) {
        this.amtAvailable = amtAvailable;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCheckedOutBy(){
        return checkedOutBy;
    }

    public void setCheckedOutBy(){
        this.checkedOutBy = checkedOutBy;
    }

    public String getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(){
        this.dateCheckedOut = dateCheckedOut;
    }
}
