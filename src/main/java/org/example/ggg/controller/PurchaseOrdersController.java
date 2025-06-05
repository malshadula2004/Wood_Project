package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.ggg.TM.CartTM;
import org.example.ggg.dto.InventoryDto;
import org.example.ggg.dto.ItemsDto;
import org.example.ggg.model.CustomersModel;
import org.example.ggg.model.InventoryModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PurchaseOrdersController {

    @FXML
    private Label lblOrderId, orderDate, lblCustomerName;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private Label lblItemName, lblItemQty, lblItemPrice;

    @FXML
    private TextField txtAddToCartQty;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colAction;


    private final CustomersModel customerModel = new CustomersModel();

    private ItemsDto itemsDto = new ItemsDto();

    private int quantity;

    private ObservableList<CartTM> list = FXCollections.observableArrayList();

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    public void initialize() {
        try {
            loadCustomerIds(); // Load customer IDs
            loadItemIds();     // Load item IDs
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data!").show();
        }

        setCellValueFactory();
    }

    private void loadData() {
        itemsDto.setItemId(cmbItemId.getValue());
        itemsDto.setName(lblItemName.getText());
        itemsDto.setUnitPrice(lblItemPrice.getText());

        Image img = new Image(getClass().getResource("/org/example/ggg/icon/icons8-minimize-48.png").toExternalForm());
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setFitWidth(15);
        Button button = new Button();
        button.setStyle("-fx-background-color: white;");
        button.setGraphic(view);

        button.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblCart.getSelectionModel().getSelectedIndex();
                try{
                    list.remove(selectedIndex);
                } catch (Exception exception){
                    new Alert(Alert.AlertType.INFORMATION,"Select Column And Remove !!").show();
                    return;
                }
                tblCart.refresh();
            }
        });

        int qty = 0;
        try {
            qty = Integer.parseInt(txtAddToCartQty.getText());
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return;
        }

        if (Integer.parseInt(txtAddToCartQty.getText()) > Integer.parseInt(lblItemQty.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Out of Stoke.Please Check Quantity !!").show();
            return;
        }

        for (int i = 0; i < tblCart.getItems().size(); i++){
            if (itemsDto.getItemId().equals(colItemId.getCellData(i))){
                CartTM tm = list.get(i);

                qty += Integer.parseInt(tm.getQty().trim());

                tm.setQty(String.valueOf(qty));
                tm.setTotal(String.valueOf(qty * Double.parseDouble(itemsDto.getUnitPrice().trim())));

                tblCart.refresh();
                return;
            }
        }

        CartTM tm = new CartTM(itemsDto.getItemId(), itemsDto.getName(), itemsDto.getUnitPrice(), String.valueOf(qty), String.valueOf(qty * Double.parseDouble(itemsDto.getUnitPrice().trim())), button);
        list.add(tm);
        tblCart.setItems(list);
    }

    // Load all customer IDs into cmbCustomerId
    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIdsList = customerModel.getAllCustomerIds();
        ObservableList<String> customerIds = FXCollections.observableArrayList(customerIdsList);
        cmbCustomerId.setItems(customerIds);
    }

    // Load all item IDs into cmbItemId
    private void loadItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemIdsList = InventoryModel.getAllItemIds();
        ObservableList<String> itemIds = FXCollections.observableArrayList(itemIdsList);
        cmbItemId.setItems(itemIds);
    }

    // Handle customer selection
    @FXML
    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        if (selectedCustomerId != null) {
            try {
                String customerName = customerModel.findNameById(selectedCustomerId);
                lblCustomerName.setText(customerName != null ? customerName : "Name not found");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                lblCustomerName.setText("Error loading name");
            }
        }
    }

    // Handle item selection
    @FXML
    public void cmbItemOnAction(ActionEvent actionEvent) {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        if (selectedItemId != null) {
            try {
                String[] itemDetails = InventoryModel.getItemDetailsById(selectedItemId);
                if (itemDetails != null) {
                    lblItemName.setText(itemDetails[0]);    // Item Name
                    lblItemQty.setText(itemDetails[1]);    // Quantity Available
                    quantity = Integer.parseInt(itemDetails[1]);
                    lblItemPrice.setText(itemDetails[2]);  // Unit Price
                } else {
                    new Alert(Alert.AlertType.WARNING, "Item details not found!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load item details!").show();
            }
        }
    }

    @FXML
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        // Logic for adding items to the cart can be implemented here
        loadData();
    }

    @FXML
    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
    }

    @FXML
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        // Logic for placing the order can be implemented here

    }

    // Reset the form after placing the order
    private void resetForm() {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblCart.getItems().clear();
        lblCustomerName.setText("");
        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.clear();
    }
}
