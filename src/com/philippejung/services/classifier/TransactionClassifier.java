package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by philippe on 28/01/15.
 */
public class TransactionClassifier {

    private ObservableList<TransactionDTO> items;
    private ArrayList<Classifier> allClassifiers = new ArrayList<Classifier>();

    public void setItems(ObservableList<TransactionDTO> items) {
        this.items = items;
    }

    public ObservableList<TransactionDTO> getItems() {
        return items;
    }

    public void classify() {
        for(TransactionDTO oneTransaction : getItems()) {
            for(Classifier classifier: allClassifiers) {
                classifier.classify(oneTransaction);
            }
        }
    }

    public void registerAllClassifiers() {
        registerAmountClassifier();
        registerTransferClassifier();
    }

    interface Classifier {
        public void classify(TransactionDTO dto);
    }

    private void registerAmountClassifier() {
        allClassifiers.add( (dto) -> {
            if (dto.getAmount() > 0)
                dto.setType(TypeOfTransaction.INCOME);
            else
                dto.setType(TypeOfTransaction.EXPENSE);
        });
    }

    private void registerTransferClassifier() {
        allClassifiers.add( (dto) -> {
            if (dto.getDetail().contains("VIREMENT")) {
                if (dto.getAmount() > 0)
                    dto.setType(TypeOfTransaction.TRANSFER_FROM);
                else
                    dto.setType(TypeOfTransaction.TRANSFER_TO);
            }
        });
    }

}
