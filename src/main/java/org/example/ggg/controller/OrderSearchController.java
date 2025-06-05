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
import org.example.ggg.dto.PurchaseOrdersDto;
import org.example.ggg.model.PurchaseOrdersModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderSearchController {

    public Button btnSearch;
    public TextField txtSearch;
    @FXML
    private TableView<PurchaseOrdersDto> tblReport;

    @FXML
    private TableColumn<PurchaseOrdersDto, String> colPurchaseOrdersId, colInventoryId, colItemId, colCustomerId, colOrderDate, colStatus, colTotalAmount, colQuantity;

    public void initialize() {
        // Bind columns to DTO properties
        colPurchaseOrdersId.setCellValueFactory(new PropertyValueFactory<>("purchaseOrdersId"));
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Load data into the TableView

    }

}
