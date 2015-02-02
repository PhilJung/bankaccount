package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.data.models.logical.TransactionDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierServiceTest {

    @Test
    public void testDetailStartsWithCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setDetailConditionTest("startsWith");
        cond1.setDetailConditionValue("a");
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(0);
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
        dto.setAmount(0);
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
        dto.setAmount(0);
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
        dto.setAmount(0);
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
        dto.setAmount(0);
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
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        //   ==
        cond1.setAmountConditionTest("==");
        dto.setAmount(10.0);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.2);
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testAmountBasedDifferentCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("!=");
        dto.setAmount(10.0);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.2);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedGreaterCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest(">");
        dto.setAmount(10.0);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.2);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedGreaterOrEqualCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest(">=");
        dto.setAmount(10.0);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.2);
        Assert.assertTrue(cs.isVerified());
    }

    @Test
    public void testAmountBasedLessCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("<");
        dto.setAmount(10.0);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(10.2);
        Assert.assertFalse(cs.isVerified());
    }

    @Test
    public void testAmountBasedLessOrEqualCondition() {
        ClassifierService cs = new ClassifierService();
        ClassifierDTO cond1 = new ClassifierDTO();
        cs.setClassifierDTO(cond1);
        cond1.setAmountConditionValue(10.1);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        cond1.setAmountConditionTest("<=");
        dto.setAmount(10.0);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.1);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(10.2);
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
        dto.setAmount(0);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(1);
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(-1);
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
        cond1.setAmountConditionValue(1.0);
        TransactionDTO dto = new TransactionDTO();
        cs.setTransactionDTO(dto);
        dto.setAmount(1);
        dto.setDetail("ba");
        Assert.assertTrue(cs.isVerified());
        dto.setAmount(2);
        dto.setDetail("ba");
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(1);
        dto.setDetail("ab");
        Assert.assertFalse(cs.isVerified());
        dto.setAmount(2);
        dto.setDetail("ab");
        Assert.assertFalse(cs.isVerified());
    }
}
