package com.philippejung.bankaccount.services.classifier;

import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dto.ClassifierDTO;
import com.philippejung.bankaccount.models.dto.TransactionDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierServiceTest {

    private final static Currency DIXZERO = new Currency(1000L);
    private final static Currency DIXDIX = new Currency(1010L);
    private final static Currency DIXVINGT = new Currency(1020L);

    @Test
    public void testDetailStartsWithCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("startsWith");
        cond1.setDetailConditionValue("a");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(Currency.zero());
        dto.setDetail("AA");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("BA");
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testDetailEndsWithCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("endsWith");
        cond1.setDetailConditionValue("a");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(Currency.zero());
        dto.setDetail("AA");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("AB");
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testDetailEqualsCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("equals");
        cond1.setDetailConditionValue("a");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(Currency.zero());
        dto.setDetail("A");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("B");
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testDetailContainsCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("contains");
        cond1.setDetailConditionValue("a");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(Currency.zero());
        dto.setDetail("AB");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("BA");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("BAB");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("B");
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testDetailMatchesCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("matches");
        cond1.setDetailConditionValue("A.*AB.*C");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(Currency.zero());
        dto.setDetail("A_AB__C");
        Assert.assertTrue(cs.isVerified());
        dto.setDetail("A_AB__CD");
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testAmountEqualsCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionTest("==");
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        //   ==
        cond1.setAmountConditionTest("==");
        dto.setAmount(DIXZERO);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testAmountBasedDifferentCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("!=");
        dto.setAmount(DIXZERO);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedGreaterCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest(">");
        dto.setAmount(DIXZERO);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedGreaterOrEqualCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest(">=");
        dto.setAmount(DIXZERO);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedLessCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("<");
        dto.setAmount(DIXZERO);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testAmountBasedLessOrEqualCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(DIXDIX);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("<=");
        dto.setAmount(DIXZERO);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXDIX);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(DIXVINGT);
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testTrueWhenNoCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setDetail("A");
        dto.setAmount(new Currency(0L));
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(new Currency(100L));
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(new Currency(-100L));
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testBothConditionsPresent() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("endsWith");
        cond1.setDetailConditionValue("A");
        cond1.setAmountConditionTest("==");
        cond1.setAmountConditionValue(new Currency(100L));
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(new Currency(100L));
        dto.setDetail("ba");
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(new Currency(200L));
        dto.setDetail("ba");
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(new Currency(100L));
        dto.setDetail("ab");
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(new Currency(200L));
        dto.setDetail("ab");
        Assert.assertFalse(cs.isVerified());
    }
}
