package com.philippejung.data.models.logical;

import com.philippejung.data.models.dao.CategoryDAO;
import com.philippejung.main.MainApp;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by philippe on 29/01/15.
 */
public class CategoryDTO extends RootDTO {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleBooleanProperty expense = new SimpleBooleanProperty();

    public CategoryDTO() {
        super();
        setName(null);
        setExpense(false);
    }

    private CategoryDTO(CategoryDAO dao) {
        super(dao);
        setName(dao.getName());
        setExpense(dao.isExpense());
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

    public boolean getExpense() {
        return expense.get();
    }

    public SimpleBooleanProperty expenseProperty() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense.set(expense);
    }

    public void toDAO(CategoryDAO dao) {
        super.toDAO(dao);
        dao.setName(getName());
        dao.setExpense(getExpense());
    }

    public static ObservableList<CategoryDTO> getAll() {
        ArrayList<CategoryDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM category", CategoryDAO.class);
        ArrayList<CategoryDTO> retVal = new ArrayList<>();
        for(CategoryDAO categoryDAO : queryResult) {
            System.out.println("Trouvé catégorie " + categoryDAO.getName());
            retVal.add(new CategoryDTO(categoryDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public String toString() {
        return getName();
    }
}
