package com.example.project3;

import java.text.DecimalFormat;

/**
 * A abstract account class to allow for inheritance of different account types
 * Includes account holder information, account state, and account balance
 * @author Rory Xu, Hassan Alfareed
 */

public abstract class Account {
    protected Profile holder;
    protected boolean closed = false;
    protected double balance;

    /**
     * All accounts contain a holder and a balance by default
     * @param holder The owner of the account
     * @param balance The amount of money in the account
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * DecimalFormat function that allows for money to be displayed in two decimal places
     */
    protected static final DecimalFormat df = new DecimalFormat("#,##0.00");

    /**
     * Overrides the default java equals to see if two accounts are the same
     * @param obj The account in question
     * @return Whether or not the account is the same as the param
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account other))
            return false;
        return this.holder.equals(other.holder) && this.getType().equals(other.getType());
    }

    /**
     * Overrides the default java toString to display account information
     * @return A string containing the account information
     */
    @Override
    public String toString() {
        return !closed
                ? this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance)
                : this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance) + "::CLOSED";
    }

    /**
     * Withdraws a certain amount of money from the account
     * @param amount The amount to be withdrawn
     */
    public void withdraw(double amount) {
        balance -= amount;
    }

    /**
     * Deposits a certain amount of money from the account
     * @param amount The amount to be deposited
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Changes the state of the account from closed to open or vice versa
     */
    public void changeState() {
        closed = !closed;
    }

    /**
     * Returns the state of the account
     */
    public boolean checkState() {
        return closed;
    }

    /**
     * Sets the balance of the account to a specified amount
     * @param newBal The new balance the account should have
     */
    public void setBalance(int newBal) {
        balance = newBal;
    }

    /**
     * Returns the name and dob of the owner of the account
     * @return The owner's personal information
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Returns the monthly interest of the account, must be overridden in subclasses
     * @return The monthly interest of the account
     */
    public abstract double monthlyInterest();

    /**
     * Returns the monthly interest of the account, must be overridden in subclasses
     * @return The monthly fee of the account
     */
    public abstract double fee();

    /**
     * Returns the what type the account is
     * @return The type of the account
     */
    public abstract String getType();
}
