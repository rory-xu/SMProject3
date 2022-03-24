package com.example.project3;

/**
 * A college checking account sub-class of the Account class
 * @author Rory Xu, Hassan Alfareed
 */
public class CollegeChecking extends Checking {

    private int campusCode;

    /**
     * Constructs a college checking account
     * Uses Checking account's super but also includes a campus code
     * @param holder The account holder
     * @param balance The balance the account has
     * @param campusCode The campus code of the holder of the account
     */
    public CollegeChecking(Profile holder, double balance, int campusCode) {
        super(holder, balance);
        this.campusCode = campusCode;
    }

    /**
     * Gets the campus of the account holder based on the campus code
     * @return A string reflecting the corresponding campus
     */
    public String getCampus() {
        if (campusCode == 0) return "NEW_BRUNSWICK";
        return campusCode == 1 ? "NEWARK" : "CAMDEN";
    }

    /**
     * Gets the campus code in integer form
     * @return the campus code
     */
    public int getCode() {
        return campusCode;
    }

    /**
     * Changes the campus code of the account
     * @param newCode The new campus code to be adopted
     */
    public void changeCampus(int newCode) {
        this.campusCode = newCode;
    }

    /**
     * Overrides the Checking toString to display important information pertaining to a college checking account
     * @return The college checking account's details
     */
    @Override
    public String toString() {
        return !closed
                ? this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance) + "::" + this.getCampus()
                : this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance) + "::CLOSED::" + this.getCampus();
    }

    /**
     * Overrides the abstract method in Checking.java for college checking account specifics
     * @return The interest rate of the college checking account
     */
    @Override
    public double monthlyInterest() {
        return 0.0025/12;
    }

    /**
     * Overrides the abstract method in Checking.java for college checking account specifics
     * @return The monthly fee of the college checking account
     */
    @Override
    public double fee() {
        return 0;
    }

    /**
     * Overrides the abstract method in Checking.java for college checking account specifics
     * @return This account's type
     */
    @Override
    public String getType() {
        return "College Checking";
    }
}
