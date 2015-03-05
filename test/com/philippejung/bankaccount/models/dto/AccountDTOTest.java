package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.models.Currency;
import javafx.scene.chart.XYChart;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AccountDTOTest {

    private final static Double EPSILON = 1E-15;

    @Test
    public void testGetBalanceHistoryByWeeks() throws Exception {
        AccountDTO dto = new AccountDTO();
        dto.setName("TestAccount");
        TransactionDTO t1 = new TransactionDTO();
        t1.setAmount(new Currency(-1000L));
        LocalDate now = LocalDate.now();
        t1.setDate(now);
        dto.addTransaction(t1);
        TransactionDTO t2 = new TransactionDTO();
        t2.setAmount(new Currency(-500L));
        t2.setDate(now.minusDays(7));
        dto.addTransaction(t2);
        TransactionDTO t3 = new TransactionDTO();
        t3.setAmount(new Currency(-500L));
        t3.setDate(now.minusDays(20));
        dto.addTransaction(t3);
        TransactionDTO t4 = new TransactionDTO();
        t4.setAmount(new Currency(-1000L));
        t4.setDate(now.minusDays(17));
        dto.addTransaction(t4);
        dto.setInitialBalance(Currency.zero());
        dto.initialLoadComplete();
        XYChart.Series series = dto.getBalanceVariationByWeeks();
        // Should contains three entries
        assertEquals(3, series.getData().size());
        // 14/02-20/02 should be -10
        assertEquals("-2", ((XYChart.Data<String, Double>) series.getData().get(0)).getXValue());
        assertEquals(-15.0, ((XYChart.Data<String, Double>) series.getData().get(0)).getYValue().doubleValue(), EPSILON);
        // 07/02-13/02 should be -5
        assertEquals("-1", ((XYChart.Data<String, Double>) series.getData().get(1)).getXValue());
        assertEquals(-5.0, ((XYChart.Data<String, Double>) series.getData().get(1)).getYValue().doubleValue(), EPSILON);
        // 31/01-06/02 should be -15
        assertEquals("0", ((XYChart.Data<String, Double>) series.getData().get(2)).getXValue());
        assertEquals(-10.0, ((XYChart.Data<String, Double>) series.getData().get(2)).getYValue().doubleValue(), EPSILON);
    }
}