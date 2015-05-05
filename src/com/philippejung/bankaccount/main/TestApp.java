package com.philippejung.bankaccount.main;/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 20/03/15.
 */

import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.utils.utils.ObjectStringConverter;
import com.philippejung.bankaccount.utils.utils.SearchableComboBoxTableCell;
import com.philippejung.bankaccount.view.tablecolumn.CategoryColumn;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;

public class TestApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox rootLayout = new VBox();
        rootLayout.setFillWidth(true);
        rootLayout.setPrefHeight(800);

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());

        ArrayList<CategoryDTO> categories = new ArrayList<>();
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Alimentation");
        categories.add(dto);
        dto = new CategoryDTO();
        dto.setName("Assurance maison");
        categories.add(dto);
        dto = new CategoryDTO();
        dto.setName("Assurance voiture");
        categories.add(dto);
        dto = new CategoryDTO();
        dto.setName("Habillement");
        categories.add(dto);
        dto = new CategoryDTO();
        dto.setName("Essence");
        categories.add(dto);

        TableView<Item> tableView = new TableView<>();
        tableView.setEditable(true);
        TableColumn<Item, CategoryDTO> categoryColumn = new TableColumn<>("Catégorie");
        categoryColumn.setEditable(true);
        TableColumn<Item, Integer> numberColumn = new TableColumn<>("Nombre");
        tableView.getColumns().add(numberColumn);
        tableView.getColumns().add(categoryColumn);
        categoryColumn.setPrefWidth(400);

        numberColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("number"));
        categoryColumn.setCellFactory(
                param -> new SearchableComboBoxTableCell<>(new ObjectStringConverter<>(), FXCollections.observableArrayList(categories))
        );
        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );
        categoryColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Item, CategoryDTO> event) -> {
                    Item impactedObject = (Item) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    CategoryDTO newValue = event.getNewValue();
                    impactedObject.setCategory(newValue);
                }
        );

        tableView.setItems(FXCollections.observableArrayList(items));

        rootLayout.getChildren().add(tableView);
        primaryStage.setScene(new Scene(rootLayout, 1200, 875));
        primaryStage.show();
    }

    public class Item {

        private SimpleIntegerProperty number = new SimpleIntegerProperty();
        private SimpleObjectProperty<CategoryDTO> category = new SimpleObjectProperty<>();

        public Item() {
            setNumber((int)Math.round(Math.random()*1000.0));
        }
        public CategoryDTO getCategory() {
            return category.get();
        }

        public SimpleObjectProperty<CategoryDTO> categoryProperty() {
            return category;
        }

        public void setCategory(CategoryDTO category) {
            this.category.set(category);
        }

        public int getNumber() {
            return number.get();
        }

        public SimpleIntegerProperty numberProperty() {
            return number;
        }

        public void setNumber(int number) {
            this.number.set(number);
        }
    }
}
