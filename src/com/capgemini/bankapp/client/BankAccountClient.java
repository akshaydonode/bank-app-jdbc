package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {

	public static void main(String[] args) {

		int choice;
		int choice1;
		double balance;
		List<BankAccount> accounts;
		long accountId;
		long accountId1;
		String accountHolderName;
		String accountType;
		double accountBalance;
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
					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);

					if (bankService.addNewBankAccount(account)) {
						System.out.println("Your account created successfully");
					} else {
						System.out.println("Failed to create new account...");
					}
					break;

				case 2:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					balance = bankService.checkBalance(accountId);
					System.out.println(balance);
					break;

				case 3:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter amount :");
					accountBalance = Double.parseDouble(reader.readLine());
					balance = bankService.withdraw(accountId, accountBalance);
					System.out.println(balance);
					break;

				case 4:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter amount :");
					accountBalance = Double.parseDouble(reader.readLine());
					balance = bankService.deposit(accountId, accountBalance);
					System.out.println("Your Updated balance is :" + balance);
					break;

				case 5:
					System.out.println("Enter account id of sender");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter account id of receiver");
					accountId1 = Long.parseLong(reader.readLine());
					System.out.println("Enter amount");
					accountBalance = Double.parseDouble(reader.readLine());

					balance = bankService.fundTransfer(accountId, accountId1, accountBalance);
					System.out.println(balance);
					break;

				case 6:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					if (bankService.deleteBankAccount(accountId))
						System.out.println("Account deleted successfully");
					else
						System.out.println("Acoount not exist");
					break;

				case 7:
					System.out.println("Enter account id :");
					accountId = Long.parseLong(reader.readLine());
					BankAccount bankaccount = bankService.searchBankAccount(accountId);
					System.out.println(bankaccount);
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
					BankAccount bankaccount1 = bankService.searchBankAccount(accountId);
					System.out.println(bankaccount1);
					System.out.println("1. Edit Account Holder Name \n2. Edit Account Type");
					System.out.println("Enter Your Choice");
					choice1 = Integer.parseInt(reader.readLine());

					switch (choice1) {

					case 1:
						System.out.println("Enter your name :");
						accountHolderName = reader.readLine();
						accountType = bankaccount1.getAccountType();
						accountBalance = bankaccount1.getAccountBalance();
						BankAccount account1 = new BankAccount(accountHolderName, accountType, accountBalance);
						if (bankService.addNewBankAccount(account1)) {
							System.out.println("Your account updated successfully");
						} else {
							System.out.println("Failed to update account...");
						}
						
						break;

					case 2:
						break;

					default:
						System.out.println("Enter Valid Choice");
						break;
					}

				case 10:
					System.out.println("Thanks for banking with us");
					System.exit(0);

				default:
					System.out.println("Enter Valid Choice");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LowBalanceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
	}

}
