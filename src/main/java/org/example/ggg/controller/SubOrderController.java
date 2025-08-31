package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.SupplierOrderDto;
import org.example.ggg.dao.impl.SupplierOrderModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubOrderController {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<SupplierOrderDto> tblSupplierOrders;

    @FXML
    private TableColumn<SupplierOrderDto, String> colOrderId;

    @FXML
    private TableColumn<SupplierOrderDto, String> colDate;

    @FXML
    private TableColumn<SupplierOrderDto, String> colSupplierId;

    @FXML
    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        loadSupplierOrders();
    }

    private void loadSupplierOrders() {
        try {
            ArrayList<SupplierOrderDto> supplierOrders = SupplierOrderModel.getAllSupplierOrders();
            ObservableList<SupplierOrderDto> observableList = FXCollections.observableArrayList(supplierOrders);
            tblSupplierOrders.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error loading supplier orders: " + e.getMessage());
        }
    }

    @FXML
    public void searchBySupplierId(ActionEvent event) {
        String supplierIdText = txtSearch.getText();

        if (supplierIdText == null || supplierIdText.trim().isEmpty()) {
            loadSupplierOrders();
            return;
        }

        try {
            String supplierId = supplierIdText.trim();
            ArrayList<SupplierOrderDto> filteredOrders = SupplierOrderModel.getSupplierOrdersBySupplierId(supplierId);
            tblSupplierOrders.setItems(FXCollections.observableArrayList(filteredOrders));
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching supplier orders: " + e.getMessage());
        }
    }
}
