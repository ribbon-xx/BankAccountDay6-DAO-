package com.qsoft.longdt;

import android.test.AndroidTestCase;

import com.qsoft.longdt.dao.BankAccountDAO;
import com.qsoft.longdt.dao.BankAccountDTO;

public class BankAccountDAOTest extends AndroidTestCase {

	private BankAccountDAO baDAO;
	private String accNumber;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		baDAO = new BankAccountDAO(getContext(), null);
		accNumber = "0123456789";
	}

	public void testOpenNewAccount() {
		BankAccountDTO bankAccount = createAccount(accNumber);
		boolean result = baDAO.insert(bankAccount);
		assertEquals(true, result);
		assertEquals(1, baDAO.getTotalRecord());
	}

	public void testGetAccountByAccountNumber() {
		BankAccountDTO bankAccount = createAccount(accNumber);
		baDAO.insert(bankAccount);
		BankAccountDTO baGot = baDAO.getBankAccountDTO(accNumber);
		assertTrue(null != baGot);
		assertTrue(bankAccount == baGot);
	}

	public void testOpenAccountConflict() throws Exception {
		BankAccountDTO bankAccount = createAccount(accNumber);
		baDAO.insert(bankAccount);

		bankAccount = createAccount(accNumber);
		baDAO.insert(bankAccount);

		assertEquals(1, baDAO.getTotalRecord());
	}

	public void testUpdateBankAccount() {
		baDAO.insert(createAccount(accNumber));
		BankAccountDTO bankAccount = baDAO.getBankAccountDTO(accNumber);
		long amount = 500;
		long timeStamp = System.currentTimeMillis();
		bankAccount.setBalance(bankAccount.getBalance() + amount);
		bankAccount.setTime(timeStamp);
		boolean result = baDAO.update(bankAccount);
		bankAccount = baDAO.getBankAccountDTO(accNumber);
		assertEquals(1, result);
		assertEquals(amount, bankAccount.getBalance(), 0.01);
		assertEquals(timeStamp, bankAccount.getTime());
	}

	private BankAccountDTO createAccount(String accNumber) {
		BankAccountDTO bankAccount = new BankAccountDTO();
		bankAccount.setAccountNumber(accNumber);
		bankAccount.setTime(System.currentTimeMillis());
		bankAccount.setBalance(0);
		return bankAccount;
	}
}
