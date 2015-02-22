package com.philippejung.bankaccount.models.dto;

import javafx.scene.chart.XYChart;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AccountDTOTest {

    private final static Double EPSILON = 1E-15;

    @Test
    public void testGetBalanceHistoryByWeeks() throws Exception {
        AccountDTO dto = new AccountDTO();
        TransactionDTO t1 = new TransactionDTO();
        t1.setAmount(-1000);
        LocalDate now = LocalDate.now();
        t1.setDate(now);
        dto.addTransaction(t1);
        TransactionDTO t2 = new TransactionDTO();
        t2.setAmount(-500);
        t2.setDate(now.minusDays(7));
        dto.addTransaction(t2);
        TransactionDTO t3 = new TransactionDTO();
        t3.setAmount(-500);
        t3.setDate(now.minusDays(20));
        dto.addTransaction(t3);
        TransactionDTO t4 = new TransactionDTO();
        t4.setAmount(-1000);
        t4.setDate(now.minusDays(17));
        dto.addTransaction(t4);
        dto.setInitialBalance(0);
        XYChart.Series series = dto.getBalanceHistoryByWeeks();
        // Should contains three entries
        assertEquals(3, series.getData().size());
        // 14/02-20/02 should be -10
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(0)).getXValue(), "-2");
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(0)).getYValue().doubleValue(), -15.0, EPSILON);
        // 07/02-13/02 should be -5
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(1)).getXValue(), "-1");
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(1)).getYValue().doubleValue(), -5.0, EPSILON);
        // 31/01-06/02 should be -15
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(2)).getXValue(), "0");
        assertEquals(((XYChart.Data<String, Double>) series.getData().get(2)).getYValue().doubleValue(), -10.0, EPSILON);
    }
}