package com.example.project3;

/**
 * A money market account sub-class of the Account class
 * @author Rory Xu, Hassan Alfareed
 */
public class MoneyMarket extends Savings {

    private int withdrawals;

    /**
     * Constructs a Money Market account
     * Uses Savings' super, but also sets the number of withdrawals to 0
     * @param holder The holder of this account
     * @param balance The balance of this acocunt
     * @param loyalty The loyalty code of this account
     */
    public MoneyMarket(Profile holder, double balance, int loyalty) {
        super(holder, balance, loyalty);
        this.withdrawals = 0;
    }

    /**
     * Increments the number of withdrawals by 1
     */
    public void addWithdrawal() {
        this.withdrawals++;
    }

    /**
     * Gets the number of withdrawals this account has
     * @return The number of total withdrawals seen on this account
     */
    public int getWithdrawals() {
        return withdrawals;
    }

    /**
     * Resets the number of withdrawals to 0
     */
    public void resetWithdrawals() {
        this.withdrawals = 0;
    }

    /**
     * Overrides the Savings' toString in order display important information for a money market account
     * @return This money market account's information
     */
    @Override
    public String toString() {
        return !closed
                ? this.getType() + "::" + this.holder.toString()
                    + "::Balance $" + df.format(this.balance) + this.isLoyal() + "::withdrawl: " + getWithdrawals()
                : this.getType() + "::" + this.holder.toString()
                    + "::Balance $" + df.format(this.balance) + "::CLOSED::withdrawl: " + getWithdrawals();
    }

    /**
     * Overrides the abstract method in Savings.java for money market account specifics
     * @return The interest rate of the money market account
     */
    @Override
    public double monthlyInterest() {
        return this.loyalty == 1 ? 0.0095/12 : 0.008/12;
    }

    /**
     * Overrides the abstract method in Savings.java for money market account specifics
     * @return The monthly fee of the money market account
     */
    @Override
    public double fee() {
        if (withdrawals > 3) return 10;
        return this.balance > 2500 ? 0 : 10;
    }

    /**
     * Overrides the abstract method in Savings.java for money market account specifics
     * @return This account's type
     */
    @Override
    public String getType() {
        return "Money Market Savings";
    }
}
