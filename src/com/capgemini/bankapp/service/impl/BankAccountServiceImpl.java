package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.AccountIdNotMatchException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao;
	static final Logger logger = Logger.getLogger(DbUtil.class);


	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) throws AccountIdNotMatchException {
		double balance=0;
		try {
		balance = bankAccountDao.getBalance(accountId);
		if (balance<  0) {
			throw new AccountIdNotMatchException("Account doesn't exist");
		}
		
		}catch (AccountIdNotMatchException e) {
			logger.error("Exception ",e);
			throw e;

		}
		return balance;

	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, AccountIdNotMatchException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new AccountIdNotMatchException("BankABccount doesn't exist..");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else {
			throw new LowBalanceException("You don't have sufficient fund..");
		}
	}

	@Override
	public double deposit(long accountId, double amount) throws AccountIdNotMatchException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new AccountIdNotMatchException("BankABccount doesn't exist..");
		balance += amount;
		bankAccountDao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws AccountIdNotMatchException {

		boolean result = bankAccountDao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
			return result;
		}
		throw new AccountIdNotMatchException("BankAccount doesn't exist...");
			
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException,AccountIdNotMatchException {
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		}catch (LowBalanceException | AccountIdNotMatchException e) {
			logger.error("Exception ",e);
			DbUtil.rollback();
			throw e;
		}

	}

	public double withdrawForFundTransfer(long accountId, double amount) throws AccountIdNotMatchException, LowBalanceException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new AccountIdNotMatchException("BankAccount doesn't exist..");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			
			return balance;
		} else {
			throw new LowBalanceException("You don't have sufficient fund..");
		}
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {

		boolean result = bankAccountDao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccount() {
		return bankAccountDao.findAllBankAccount();
	}

	@Override
	public BankAccount searchBankAccount(long accountId) throws AccountIdNotMatchException {

		BankAccount account;
		 account = bankAccountDao.searchBankAccount(accountId);
		if(account != null) {
			return account;
		}
		logger.error("Exception");
		throw new AccountIdNotMatchException("BankAccount doesn't exist");
			
	}

	@Override
	public boolean updateAccount(BankAccount account1) {
		boolean result = bankAccountDao.updateAccount(account1);
		if(result) {
			DbUtil.commit();
		}
		return result;
			
	}

}
