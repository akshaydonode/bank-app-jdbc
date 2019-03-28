package com.capgemini.bankapp.service;

import java.util.List;

import com.capgemini.bankapp.exception.AccountIdNotMatchException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountService {

	public double checkBalance(long accountId) throws AccountIdNotMatchException;
	public double withdraw(long accountId, double amount) throws LowBalanceException, AccountIdNotMatchException;
	public double deposit(long accountId, double amount)throws AccountIdNotMatchException;
	public boolean deleteBankAccount(long accountId)throws AccountIdNotMatchException;
	public double fundTransfer(long toAccount,long fromAccount,double amount)throws LowBalanceException, AccountIdNotMatchException;
	public boolean addNewBankAccount(BankAccount account);
	public List<BankAccount> findAllBankAccount();
	public BankAccount searchBankAccount(long accountId) throws AccountIdNotMatchException;
	public boolean updateAccount(BankAccount account1);
	
}
