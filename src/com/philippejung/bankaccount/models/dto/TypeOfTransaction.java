package com.philippejung.bankaccount.models.dto;

/**
 * Created by philippe on 29/01/15.
 */
public enum TypeOfTransaction {
    NONE ( 0 ),
    INCOME ( 1 ),
    EXPENSE ( 2 ),
    TRANSFER_TO( 3 ),
    TRANSFER_FROM( 4 );

    private final int value;

    private TypeOfTransaction(int value) {
        this.value = value;
    }

    public static TypeOfTransaction fromInt(int value) {
        switch (value) {
            case 1: return INCOME;
            case 2: return EXPENSE;
            case 3: return TRANSFER_TO;
            case 4: return TRANSFER_FROM;
            default: return NONE;
        }
    }

    public Integer toInt() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case EXPENSE: return "Dépense";
            case INCOME: return "Recette";
            case TRANSFER_FROM: return "Virement de";
            case TRANSFER_TO: return "Virement vers";
            case NONE: return "";
        }
        return super.toString();
    }
}
