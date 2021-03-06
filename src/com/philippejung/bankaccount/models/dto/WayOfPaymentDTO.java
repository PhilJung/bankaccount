package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.models.dao.WayOfPaymentDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 29/01/15.
 */
public class WayOfPaymentDTO extends RootDTO {
    private final SimpleStringProperty name = new SimpleStringProperty();

    public WayOfPaymentDTO() {
        super();
        setName(null);
    }

    private WayOfPaymentDTO(WayOfPaymentDAO dao) {
        super(dao);
        setName(dao.getName());
    }

    public void toDAO(RootDAO dao) {
        super.toDAO(dao);
        WayOfPaymentDAO wayOfPaymentDAO =(WayOfPaymentDAO)dao;
        wayOfPaymentDAO.setName(getName());
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
        ArrayList<WayOfPaymentDAO> queryResult = WayOfPaymentDAO.getAll();
        ArrayList<WayOfPaymentDTO> retVal = new ArrayList<>();
        for(WayOfPaymentDAO wayOfPaymentDAO : queryResult) {
            //System.out.println("Trouvé moyen de paiement " + wayOfPaymentDAO.getName());
            retVal.add(new WayOfPaymentDTO(wayOfPaymentDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public RootDAO newDAO() {
        return new WayOfPaymentDAO();
    }

    @Override
    public String toString() {
        return getName();
    }
}
