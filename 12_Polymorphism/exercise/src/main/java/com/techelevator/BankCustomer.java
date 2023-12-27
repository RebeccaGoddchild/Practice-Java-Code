package com.techelevator;
import java.util.ArrayList;
import java.util.List;
public class BankCustomer {
    private String name;
    private String address;
    private String phoneNumber;
    private List<Accountable> accounts = new ArrayList<>();
   //initialize
    public BankCustomer(String name, String address, String phoneNumber, List<Accountable> accounts) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accounts = accounts;
    }

    public BankCustomer(){

    }
    //Getters

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Accountable[] getAccounts() {
        return accounts.toArray(new Accountable[accounts.size()]);
    }
    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //Methods
    public void addAccount(Accountable newAccount) {
        accounts.add(newAccount);
    }
    public boolean isVip() {
        int total = 0;

        for (int i = 0; i < accounts.size(); i++) {
            total += accounts.get(i).getBalance();
        }
        if (total >= 25000) {
            return true;
        } else {
            return false;
        }
    }
}
