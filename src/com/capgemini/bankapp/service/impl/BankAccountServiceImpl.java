package com.capgemini.bankapp.service.impl;

import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao;


	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}
	@Override
	public double checkBalance(long accountId) {
		double balance = bankAccountDao.getBalance(accountId);
		return balance;
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException {
		double balance = bankAccountDao.getBalance(accountId);

		if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);

			return balance;
		} else {
			throw new LowBalanceException("You don't have sufficient fund..");
		}
	}

	@Override
	public double deposit(long accountId, double amount) {
		double balance = bankAccountDao.getBalance(accountId);
		balance += amount;
		bankAccountDao.updateBalance(accountId, balance);
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		
		return bankAccountDao.deleteBankAccount(accountId);
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException {
		double newBalance = withdraw(fromAccount, amount);
		double balance = deposit(toAccount, amount);
		System.out.println(balance + " "+ newBalance);
		return newBalance;
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		
		return bankAccountDao.addNewBankAccount(account);
	}

	@Override
	public List<BankAccount> findAllBankAccount() {
		return bankAccountDao.findAllBankAccount();
	}
	@Override
	public BankAccount searchBankAccount(long accountId) {
	 
		return bankAccountDao.searchBankAccount(accountId);
	}
	@Override
	public boolean updateAccount(BankAccount account1) {
		return bankAccountDao.updateAccount(account1);
	}

}
