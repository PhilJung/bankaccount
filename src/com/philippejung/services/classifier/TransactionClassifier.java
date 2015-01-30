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
    private final ArrayList<Classifier> allClassifiers = new ArrayList<>();

    public void setItems(ObservableList<TransactionDTO> items) {
        this.items = items;
    }

    public ObservableList<TransactionDTO> getItems() {
        return items;
    }

    public void classify() {
        for(TransactionDTO oneTransaction : getItems()) {
            for(Classifier classifier: allClassifiers) {
                if (classifier.classifyAndTellIfWeShouldStop(oneTransaction))
                    // Classifier has decided we should stop classification on this item
                    break;
            }
        }
    }

    public void registerAllClassifiers() {
        registerAmountClassifier();
        registerTransferClassifier();
    }

    interface Classifier {
        // Perform one attempt of classification
        // Return false to stop further classification of the dto
        public boolean classifyAndTellIfWeShouldStop(TransactionDTO dto);
    }

    private void registerAmountClassifier() {
        allClassifiers.add( (dto) -> {
            if (dto.getAmount() > 0)
                dto.setType(TypeOfTransaction.INCOME);
            else
                dto.setType(TypeOfTransaction.EXPENSE);
            return false;
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
            return false;
        });
    }

}
