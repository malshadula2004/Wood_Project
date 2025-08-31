package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.ItemsDto;
import org.example.ggg.dao.impl.ItemsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {

    @FXML
    private TextField txtName, txtDescription, txtCategory, txtPrice, txtSearch;

    @FXML
    private TableView<ItemsDto> tblItem;

    @FXML
    private TableColumn<ItemsDto, String> colItemId, colName, colDescription, colCategory, colUnitPrice;

    private ObservableList<ItemsDto> itemList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        loadItemData();

        // Set up a listener to populate the text fields when a row is clicked
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });
    }

    private void loadItemData() {
        try {
            ArrayList<ItemsDto> items = ItemsModel.loadItems();
            itemList.clear();
            itemList.addAll(items);
            tblItem.setItems(itemList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load items: " + e.getMessage());
        }
    }

    @FXML
    private void addToItem(ActionEvent event) {
        String id = generateItemId();
        ItemsDto item = new ItemsDto(id, txtName.getText(), txtDescription.getText(), txtCategory.getText(), txtPrice.getText());
        try {
            ItemsModel.addItem(item);
            itemList.add(item);
            clearFields();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add item: " + e.getMessage());
        }
    }

    @FXML
    private void updateItem(ActionEvent event) {
        ItemsDto selected = tblItem.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(txtName.getText());
            selected.setDescription(txtDescription.getText());
            selected.setCategory(txtCategory.getText());
            selected.setUnitPrice(txtPrice.getText());
            try {
                ItemsModel.updateItem(selected);
                tblItem.refresh();
                clearFields();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to update item: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select an item to update.");
        }
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        ItemsDto selected = tblItem.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                ItemsModel.deleteItem(selected.getItemId());
                itemList.remove(selected);
                clearFields();
                refreshTableData();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to delete item: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }

    @FXML
    private void searchItem(ActionEvent event) {

    }

    private String generateItemId() {
        return "I" + (itemList.size() + 1);
    }

    private void populateFields(ItemsDto item) {
        txtName.setText(item.getName());
        txtDescription.setText(item.getDescription());
        txtCategory.setText(item.getCategory());
        txtPrice.setText(item.getUnitPrice());
    }

    private void clearFields() {
        txtName.clear();
        txtDescription.clear();
        txtCategory.clear();
        txtPrice.clear();
    }

    private void refreshTableData() {
        loadItemData(); // Reload the table data from the database
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void resetItem(ActionEvent actionEvent) {
        tblItem.getSelectionModel().clearSelection();
        txtSearch.clear();
        tblItem.setItems(itemList);
        clearFields();
    }
}
