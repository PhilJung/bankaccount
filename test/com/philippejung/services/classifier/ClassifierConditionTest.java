package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierConditionTest {

    @Test
    public void testDetailStartsWithCondition() {
        ClassifierCondition cond1 = new ClassifierCondition("startsWith", "a", null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(0);
        dto.setDetail("AA");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("BA");
        Assert.assertFalse(cond1.isVerified(dto));
    }

    @Test
    public void testDetailEndsWithCondition() {
        ClassifierCondition cond1 = new ClassifierCondition("endsWith", "a", null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(0);
        dto.setDetail("AA");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("AB");
        Assert.assertFalse(cond1.isVerified(dto));
    }

    @Test
    public void testDetailEqualsCondition() {
        ClassifierCondition cond1 = new ClassifierCondition("equals", "a", null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(0);
        dto.setDetail("A");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("B");
        Assert.assertFalse(cond1.isVerified(dto));
    }

    @Test
    public void testDetailContainsCondition() {
        ClassifierCondition cond1 = new ClassifierCondition("contains", "a", null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(0);
        dto.setDetail("AB");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("BA");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("BAB");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("B");
        Assert.assertFalse(cond1.isVerified(dto));
    }

    @Test
    public void testDetailMatchesCondition() {
        ClassifierCondition cond1 = new ClassifierCondition("matches", "A.*AB.*C", null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(0);
        dto.setDetail("A_AB__C");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setDetail("A_AB__CD");
        Assert.assertFalse(cond1.isVerified(dto));
    }

    @Test
    public void testAmountBasedCondition() {
        ClassifierCondition cond1 = new ClassifierCondition(null, null, "==", 10.1);
        TransactionDTO dto100 = new TransactionDTO();
        dto100.setDetail("10.0");
        dto100.setAmount(10.0);
        TransactionDTO dto101 = new TransactionDTO();
        dto101.setDetail("10.1");
        dto101.setAmount(10.1);
        TransactionDTO dto102 = new TransactionDTO();
        dto102.setDetail("10.2");
        dto102.setAmount(10.2);
        //   ==
        Assert.assertFalse(cond1.isVerified(dto100));
        Assert.assertTrue(cond1.isVerified(dto101));
        Assert.assertFalse(cond1.isVerified(dto102));
        //   !=
        cond1.setAmountConditionTest("!=");
        Assert.assertTrue(cond1.isVerified(dto100));
        Assert.assertFalse(cond1.isVerified(dto101));
        Assert.assertTrue(cond1.isVerified(dto102));
        //   >
        cond1.setAmountConditionTest(">");
        Assert.assertFalse(cond1.isVerified(dto100));
        Assert.assertFalse(cond1.isVerified(dto101));
        Assert.assertTrue(cond1.isVerified(dto102));
        //   >=
        cond1.setAmountConditionTest(">=");
        Assert.assertFalse(cond1.isVerified(dto100));
        Assert.assertTrue(cond1.isVerified(dto101));
        Assert.assertTrue(cond1.isVerified(dto102));
        //   <
        cond1.setAmountConditionTest("<");
        Assert.assertTrue(cond1.isVerified(dto100));
        Assert.assertFalse(cond1.isVerified(dto101));
        Assert.assertFalse(cond1.isVerified(dto102));
        //   <=
        cond1.setAmountConditionTest("<=");
        Assert.assertTrue(cond1.isVerified(dto100));
        Assert.assertTrue(cond1.isVerified(dto101));
        Assert.assertFalse(cond1.isVerified(dto102));
    }

    @Test
    public void testTrueWhenNoCondition() {
        ClassifierCondition cond1 = new ClassifierCondition(null, null, null, 0.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setDetail("A");
        dto.setAmount(0);
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setAmount(1);
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setAmount(-1);
        Assert.assertTrue(cond1.isVerified(dto));
    }

    @Test
    public void testBothConditionsPresent() {
        ClassifierCondition cond1 = new ClassifierCondition("endsWith", "A", "==", 1.0);
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(1);
        dto.setDetail("ba");
        Assert.assertTrue(cond1.isVerified(dto));
        dto.setAmount(2);
        dto.setDetail("ba");
        Assert.assertFalse(cond1.isVerified(dto));
        dto.setAmount(1);
        dto.setDetail("ab");
        Assert.assertFalse(cond1.isVerified(dto));
        dto.setAmount(2);
        dto.setDetail("ab");
        Assert.assertFalse(cond1.isVerified(dto));
    }
}
