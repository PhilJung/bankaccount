package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.models.dao.AccountDAO;
import com.philippejung.bankaccount.models.dao.CategoryDAO;
import com.philippejung.bankaccount.models.dao.TransactionDAO;
import com.philippejung.bankaccount.models.dao.WayOfPaymentDAO;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReadWriteDTOTest {

    long accountId = -1L;
    long categoryId = -1L;
    long wayOfPaymentId = -1L;
    long transactionId = -1L;
    LocalDate now;

    private DatabaseAccess dbAccess = null;

    @Before
    public void setUp() throws Exception {
        dbAccess = new DatabaseAccess("/tmp/");
        now = LocalDate.now();
    }

    private void writeToDB() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName("account");
        accountDTO.setAccountNumber("0000");
        accountDTO.setInitialBalance(2345);
        accountDTO.setImporterFormat("lbp");
        accountDTO.writeToDB(dbAccess);
        accountId = accountDTO.getId();
        assertNotEquals(-1L, (long) accountId);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("category");
        categoryDTO.setExpense(true);
        categoryDTO.writeToDB(dbAccess);
        categoryId = categoryDTO.getId();
        assertNotEquals(-1L, (long) categoryId);
        WayOfPaymentDTO wayOfPaymentDTO = new WayOfPaymentDTO();
        wayOfPaymentDTO.setName("wayOfPayment");
        wayOfPaymentDTO.writeToDB(dbAccess);
        wayOfPaymentId = wayOfPaymentDTO.getId();
        assertNotEquals(-1L, (long) wayOfPaymentId);
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(accountDTO);
        transactionDTO.setAmount(-1234);
        transactionDTO.setCategory(categoryDTO);
        transactionDTO.setComment("Comment");
        transactionDTO.setDate(now);
        transactionDTO.setDetail("Detail");
        transactionDTO.setType(TypeOfTransaction.EXPENSE);
        transactionDTO.setWayOfPayment(wayOfPaymentDTO);
        transactionDTO.writeToDB(dbAccess);
        transactionId = transactionDTO.getId();
        assertNotEquals(-1L , (long) transactionId);
    }

    private void readFromDB() throws Exception {
        TransactionDAO transactionDAO = TransactionDAO.byId(transactionId, dbAccess);
        assertEquals(accountId, (long) transactionDAO.getAccountId());
        assertEquals(-1234L, (long) transactionDAO.getAmount());
        assertEquals(categoryId, (long) transactionDAO.getCategoryId());
        assertEquals("Comment", transactionDAO.getComment());
        assertEquals(now, transactionDAO.getDate().toLocalDate());
        assertEquals("Detail", transactionDAO.getDetail());
        assertEquals(TypeOfTransaction.EXPENSE.toInt(), transactionDAO.getType());
        assertEquals(wayOfPaymentId, (long) transactionDAO.getWayOfPaymentId());
        transactionDAO.writeToDB(dbAccess);

        AccountDAO accountDAO = AccountDAO.byId(accountId, dbAccess);
        assertEquals("account", accountDAO.getName());
        assertEquals("0000", accountDAO.getAccountNumber());
        assertEquals(2345L, (long) accountDAO.getInitialBalance());
        assertEquals("lbp", accountDAO.getImporterFormat());

        CategoryDAO categoryDAO = CategoryDAO.byId(categoryId, dbAccess);
        assertEquals("category", categoryDAO.getName());
        assertEquals(true, categoryDAO.isExpense());

        WayOfPaymentDAO wayOfPaymentDAO = WayOfPaymentDAO.byId(wayOfPaymentId, dbAccess);
        assertEquals("wayOfPayment", wayOfPaymentDAO.getName());
    }

    @Test
    public void testReadWriteToDB() throws Exception {
        writeToDB();
        readFromDB();
    }
}