package com.example.project3;

/**
 * A savings account sub-class of the Account class
 * @author Rory Xu, Hassan Alfareed
 */
public class Savings extends Account {

    protected int loyalty;

    /**
     * Constructs a savings account
     * Uses Account's super, but also includes a loyalty code
     * @param holder The account holder
     * @param balance The balance of the account
     * @param loyalty The loyalty code of the account
     */
    public Savings(Profile holder, double balance, int loyalty) {
        super(holder, balance);
        this.loyalty = loyalty;
    }

    /**
     * Sets the loyalty of the account
     * @param loyalty The loyalty code to be adopted
     */
    public void changeLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    /**
     * Gets the loyalty code of the account
     * @return The loyalty code of the account
     */
    public int getLoyalty() {
        return loyalty;
    }

    /**
     * Gets the loyalty status of the account in string form
     * @return A string that displays whether the account is loyal
     */
    public String isLoyal() {
        return loyalty == 1 ? "::Loyal" : "";
    }

    /**
     * Overrides the Account.java toString to display important information for checking accounts
     * @return This checking account's information
     */
    @Override
    public String toString() {
        return !closed
                ? this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance) + this.isLoyal()
                : this.getType() + "::" + this.holder.toString() + "::Balance $" + df.format(this.balance) + "::CLOSED";
    }

    /**
     * Overrides the abstract method in Account.java for savings account specifics
     * @return The interest rate of the savings account
     */
    @Override
    public double monthlyInterest() {
        return loyalty == 1 ? 0.0045/12 : 0.003/12;
    }

    /**
     * Overrides the abstract method in Account.java for savings account specifics
     * @return The monthly fee of the savings account
     */
    @Override
    public double fee() {
        return balance >= 300 ? 0 : 6;
    }

    /**
     * Overrides the abstract method in Account.java for savings account specifics
     * @return This account's type
     */
    @Override
    public String getType() {
        return "Savings";
    }
}
