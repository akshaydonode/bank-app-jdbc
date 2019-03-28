package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.AccountIdNotMatchException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {
	// in between () we can fully qualified class name
	static final Logger logger = Logger.getLogger(BankAccountClient.class);

	public static void main(String[] args) throws AccountIdNotMatchException {

		int choice;
		int choice1;
		double balance;
		List<BankAccount> accounts;
		long accountId;
		long accountId1;
		String accountHolderName;
		String accountType;
		double accountBalance;
		BankAccount account;
		BankAccountService bankService = new BankAccountServiceImpl();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1. Open a new account.\n2. Check Balance.\n3. Withdraw.\n4. Deposit");
				System.out.println("5. Fund Transfer.\n6. Delete Account.\n7. Search Account.");
				System.out.println("8. Display all account details.\n9. Update Bank Details.\n10. Exist");

				System.out.println("Enter Your Choice");
				choice = Integer.parseInt(reader.readLine());
				switch (choice) {

				case 1:
					System.out.println("Enter your name :");
					accountHolderName = reader.readLine();
					System.out.println("Enter your account type :");
					accountType = reader.readLine();
					System.out.println("Enter account balance :");
					accountBalance = Double.parseDouble(reader.readLine());
					 account = new BankAccount(accountHolderName, accountType, accountBalance);

					if (bankService.addNewBankAccount(account)) {
						System.out.println("Your account created successfully");
					} else {
						System.out.println("Failed to create new account...");
					}
					break;

				case 2:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					try {
					balance = bankService.checkBalance(accountId);
					System.out.println(balance);
					}
					catch(AccountIdNotMatchException e) {
						logger.error("Account doesn't exist: ",e);
					}
					break;

				case 3:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter amount :");
					accountBalance = Double.parseDouble(reader.readLine());
					try {
						balance = bankService.withdraw(accountId, accountBalance);
						System.out.println(balance);
					} catch (LowBalanceException e) {
						logger.error("Exception :", e);
					}

					break;

				case 4:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter amount :");
					accountBalance = Double.parseDouble(reader.readLine());
					try {
					balance = bankService.deposit(accountId, accountBalance);
					System.out.println("Your Updated balance is :" + balance);
					}
					catch(AccountIdNotMatchException e) {
						logger.error("Account doesn't exist: ",e);
					}
					break;

				case 5:
					System.out.println("Enter account id of sender");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter account id of receiver");
					accountId1 = Long.parseLong(reader.readLine());
					System.out.println("Enter amount");
					accountBalance = Double.parseDouble(reader.readLine());
					try {
						balance = bankService.fundTransfer(accountId, accountId1, accountBalance);
						System.out.println(balance);
					} catch (AccountIdNotMatchException| LowBalanceException e) {
						logger.error("Exception :", e);
					}

					break;

				case 6:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					try {
					if (bankService.deleteBankAccount(accountId))
						System.out.println("Account deleted successfully");
					}
					catch(AccountIdNotMatchException e) {
						logger.error("Account doesn't exist: ",e);
					}
					break;

				case 7:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					try {
					BankAccount bankaccount = bankService.searchBankAccount(accountId);
					System.out.println(bankaccount);
					}
					catch(AccountIdNotMatchException e) {
						logger.error("Account doesn't exist: ",e);
					}
					break;

				case 8:
					accounts = bankService.findAllBankAccount();
					for (BankAccount bankAccount : accounts) {
						System.out.println(bankAccount);
					}
					break;

				case 9:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
				
					try {
						account = bankService.searchBankAccount(accountId);
					if(account.getAccountHolderName() != null)
						System.out.println(account);
					

					accountId =account.getAccountId();
					System.out.println("Enter your new name :");
					accountHolderName = reader.readLine();
					System.out.println("Enter your new account type:");
					accountType = reader.readLine();
					accountBalance = account.getAccountBalance();
					BankAccount account1 = new BankAccount(accountId,accountHolderName, accountType, accountBalance);
					if (bankService.updateAccount(account1)) {
						System.out.println("Your account updated successfully");
					} }
					catch(AccountIdNotMatchException e) {
						logger.error("Account doesn't exist: ",e);
					}
					break;

				case 10:
					System.out.println("Thanks for banking with us");
					System.exit(0);

				default:
					System.out.println("Enter Valid Choice");
					break;
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
