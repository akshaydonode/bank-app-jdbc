package com.capgemini.bankapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountDaoImpl implements BankAccountDao {

	@Override
	public double getBalance(long accountId) {

		String query = "SELECT account_balance FROM bankaccounts WHERE account_id = " + accountId;
		double balance = 0;

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			result.next();
			balance = result.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public void updateBalance(long accountId, double newBalance) {

		String query = "UPDATE bankaccounts SET account_balance=? WHERE account_id=?";

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDouble(1, newBalance);
			statement.setLong(2, accountId);

			int result = statement.executeUpdate();
			System.out.println("No of Rows Updated: " + result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		String query = "DELETE FROM bankaccounts WHERE account_id=" + accountId;
		int result;

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			result = statement.executeUpdate();
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		String query = "INSERT INTO bankaccounts (customer_name,account_type,account_balance) VALUES (?,?,?)";

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, account.getAccountHolderName());
			statement.setString(2, account.getAccountType());
			statement.setDouble(3, account.getAccountBalance());

			int result = statement.executeUpdate();
			if (result > 0) {
				//System.out.println("Your account created successfully...");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BankAccount> findAllBankAccount() {
		String query = "SELECT * FROM bankaccounts";
		List<BankAccount> accountList = new ArrayList<BankAccount>();

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {

			while (result.next()) {
				long accountId = result.getLong(1);
				String accountHolderName = result.getString(2);
				String accountType = result.getString(3);
				double accountBalance = result.getDouble(4);
				BankAccount account = new BankAccount(accountId,accountHolderName,accountType,accountBalance);
				accountList.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accountList;
	}

	@Override
	public BankAccount searchBankAccount(long accountId) {
		String query = "select * from bankaccounts where account_id=" + accountId;
		BankAccount account = new BankAccount();
		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
				
			result.next();
				String accountHolderName = result.getString(2);
				String accountType = result.getString(3);
				double accountBalance = result.getDouble(4);
				account = new BankAccount(accountId,accountHolderName,accountType,accountBalance);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		

	}
		return account;
	}
}

