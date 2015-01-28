package com.philippejung.data.models.logical;

/**
 * Created by philippe on 29/01/15.
 */
public enum TypeOfTransaction {
    NONE ( 0 ),
    INCOME ( 1 ),
    EXPENSE ( 2 ),
    TRANSFER_TO( 3 ),
    TRANSFER_FROM( 4 );

    private int value;

    private TypeOfTransaction(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (this) {
            case EXPENSE: return "Dépense";
            case INCOME: return "Recette";
            case TRANSFER_FROM: return "Virement de";
            case TRANSFER_TO: return "Virement vers";
        }
        return super.toString();
    }
}
