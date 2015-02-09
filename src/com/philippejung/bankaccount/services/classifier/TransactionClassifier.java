package com.philippejung.bankaccount.services.classifier;

import com.philippejung.bankaccount.models.dto.ClassifierDTO;
import com.philippejung.bankaccount.models.dto.TransactionDTO;
import javafx.collections.ObservableList;

/**
 * Created by philippe on 28/01/15.
 */
public class TransactionClassifier {

    private ObservableList<ClassifierDTO> classifiers;
    private ObservableList<TransactionDTO> items;

    public void setClassifiers(ObservableList<ClassifierDTO> classifiers) {
        this.classifiers = classifiers;
    }

    private final ClassifierService classifierService = new ClassifierService();

    public void setItems(ObservableList<TransactionDTO> items) {
        this.items = items;
    }

    public ObservableList<TransactionDTO> getItems() {
        return items;
    }

    public void classify() {
        for (TransactionDTO oneTransaction : getItems()) {
            for (ClassifierDTO classifier : classifiers) {
                if (classifierService.classifyAndTellIfWeShouldStop(classifier, oneTransaction))
                    // Classifier has decided we should stop classification on this item
                    break;
            }
        }
    }
}
