package com.smartlibrary.model;
import java.sql.Date;
public class Borrow {
    private int bId;
    private int mId;
    private Date borrowDate;
    private Date returnDate;
    private int penalty;
    public int getbId() { return bId; }
    public void setbId(int bId) { this.bId = bId; }
    public int getmId() { return mId; }
    public void setmId(int mId) { this.mId = mId; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public int getPenalty() { return penalty; }
    public void setPenalty(int penalty) { this.penalty = penalty; }
}
