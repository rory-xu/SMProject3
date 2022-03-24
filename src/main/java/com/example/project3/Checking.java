package com.example.project3;

/**
 * A checking account sub-class of the Account class
 * @author Rory Xu, Hassan Alfareed
 */
public class Checking extends Account {

    /**
     * Constructs a checking account
     * @param holder The owner of the account
     * @param balance Amount of money the account has
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Overrides the abstract method in Account.java for checking account specifics
     * @return The interest rate of the checking account
     */
    @Override
    public double monthlyInterest() {
        return 0.001/12;
    }

    /**
     * Overrides the abstract method in Account.java for checking account specifics
     * @return The monthly fee of the checking account
     */
    @Override
    public double fee() {
        return this.balance >= 1000 ? 0 : 25;
    }

    /**
     * Overrides the abstract method in Account.java for checking account specifics
     * @return This account's type
     */
    @Override
    public String getType() {
        return "Checking";
    }
}
