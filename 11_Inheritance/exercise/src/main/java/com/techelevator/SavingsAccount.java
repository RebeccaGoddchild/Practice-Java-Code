package com.techelevator;

public class SavingsAccount extends BankAccount{
    public SavingsAccount(String accountHolderName, String accountNumber) {
        super(accountHolderName, accountNumber);
    }
    public SavingsAccount(String accontHolderName,String accountNumber,int balance){
        super(accontHolderName,accountNumber,balance);
    }

    public int withdraw(int amountToWithdraw) {
        int overdraftLimit = 150;
        int serviceCharge = 2;

        int currentBalance = getBalance();

        if (amountToWithdraw > currentBalance) {
            // Deny withdrawal that exceeds available balance
            return currentBalance;
        }

        int adjustedAmount = amountToWithdraw;

        if (currentBalance - amountToWithdraw < overdraftLimit) {
            // Adjust the amount and apply the service charge if it puts the balance below the overdraft limit
            adjustedAmount += serviceCharge;
        }

        if (adjustedAmount > currentBalance) {
            // Deny withdrawal as the adjusted total amount exceeds the current balance
            return currentBalance;
        }

        // Perform the withdrawal
        super.withdraw(adjustedAmount);
        return currentBalance - adjustedAmount;
    }
}



