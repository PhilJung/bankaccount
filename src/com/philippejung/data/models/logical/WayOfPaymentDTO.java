package com.philippejung.data.models.logical;

import com.philippejung.data.models.dao.WayOfPaymentDAO;
import com.philippejung.main.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by philippe on 29/01/15.
 */
public class WayOfPaymentDTO extends RootDTO {
    private final SimpleStringProperty name = new SimpleStringProperty();

    public WayOfPaymentDTO(Integer id, String name) {
        super(id);
        setName(name);
    }

    private WayOfPaymentDTO(WayOfPaymentDAO dao) {
        super(dao);
        setName(dao.getName());
    }

    public void toDAO(WayOfPaymentDAO dao) {
        super.toDAO(dao);
        dao.setName(getName());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public static ObservableList<WayOfPaymentDTO> getAll() {
        ArrayList<WayOfPaymentDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM wayOfPayment", WayOfPaymentDAO.class);
        ArrayList<WayOfPaymentDTO> retVal = new ArrayList<>();
        for(WayOfPaymentDAO wayOfPaymentDAO : queryResult) {
            System.out.println("Trouvé moyen de paiement " + wayOfPaymentDAO.getName());
            retVal.add(new WayOfPaymentDTO(wayOfPaymentDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public String toString() {
        return getName();
    }
}
