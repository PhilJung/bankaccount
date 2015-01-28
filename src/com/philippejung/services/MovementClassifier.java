package com.philippejung.services;

import com.philippejung.data.models.logical.TransactionDTO;
import javafx.collections.ObservableList;

import javax.xml.crypto.dsig.TransformService;
import java.util.ArrayList;

/**
 * Created by philippe on 28/01/15.
 */
public class MovementClassifier {

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
    }

    interface Classifier {
        public void classify(TransactionDTO dto);
    }

    private void registerAmountClassifier() {
        allClassifiers.add( (dto) -> {
            if (dto.getAmount() > 0)
                dto.setType(TransactionDTO.INCOME);
            else
                dto.setType(TransactionDTO.EXPENSE);
        });
    }
}
