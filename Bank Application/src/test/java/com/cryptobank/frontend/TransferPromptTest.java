package com.cryptobank.frontend;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.TransactionDAO;
import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.AccountDAOImpl;
import com.cryptobank.DAOImpl.TransactionDAOImpl;
import com.cryptobank.DAOImpl.UserDAOImpl;
import com.cryptobank.models.BankAccount;
import com.cryptobank.models.Transfer;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;

public class TransferPromptTest {
	private static Logger log = Logger.getLogger(TransferPromptTest.class);
	private static Scanner sc = new Scanner(System.in);
	private UserDAO userDao = new UserDAOImpl();
	private AccountDAO accountDao = new AccountDAOImpl();
	private TransactionDAO tranDao = new TransactionDAOImpl();
	private User testUser;
	private User testUser2;
	private BankAccount testAccount;
	private BankAccount testAccount2;
	private Transfer testTran;

	@Before
	public void setUpUsers() {
		testUser = User.createUser("testUser1", "12341234", "testEmail1@test.com", UserGroup.customer);
		UserGroup.addToGroup(testUser);
		log.info(testUser);
		testAccount = accountDao.createAccount(testUser);
		testUser.addAccount(testAccount);
		log.info(testAccount.toString());

		testUser2 = User.createUser("testUser2", "12341234", "testEmail2@test.com", UserGroup.customer);
		UserGroup.addToGroup(testUser2);
		log.info(testUser2);
		testAccount2 = accountDao.createAccount(testUser2);
		testUser2.addAccount(testAccount2);
		log.info(testAccount2.toString());

		testTran = new Transfer(testAccount, testAccount2, 0.0);

	}

	@After
	public void tearDownUser() {


		log.info(testUser.getAccounts().get(0).toString());
		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser);

		log.info(testUser2.getAccounts().get(0).toString());
		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser2.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser2);

	}

	@Test 
	public void test() {
		// make a transaction
		Dashboard.getDashboard().transferFundsDashboard(sc, testUser, testAccount);
		
	}
}
