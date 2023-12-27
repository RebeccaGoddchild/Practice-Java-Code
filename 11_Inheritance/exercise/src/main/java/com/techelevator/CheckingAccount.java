package com.techelevator;
public class CheckingAccount extends BankAccount {
    public CheckingAccount(String accountHolderName,String accountNumber) {
        super(accountHolderName,accountNumber);
    }
public CheckingAccount(String accountHolderName, String accountNumber,int balance){
        super(accountHolderName,accountNumber,balance);
}

    public int withdraw(int amountToWithdraw) {
        int currentBalance = getBalance();

        // If balance is already below or equal to -100, deny overdraft
        if (currentBalance <= -100) {
            return currentBalance;
        }

        int newBalance = currentBalance - amountToWithdraw;

        // If new balance is positive, perform a regular withdrawal
        if (newBalance >= 0) {
            super.withdraw(amountToWithdraw);
        }

        // If new balance is between -100 and 0 (exclusive), apply overdraft fee
        if (newBalance < 0 && newBalance > -100) {
            amountToWithdraw += 10; // Apply overdraft fee
            super.withdraw(amountToWithdraw);
        }

        return getBalance();
    }
}
