package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.RoleEnum;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.service.HashService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class AccountsController implements Initializable {
    @FXML
    private Button saveBtn;
    @FXML
    private PasswordField pawd;
    @FXML
    private TextField user_tf,colors;
    @FXML
    private ComboBox<String> rolecbb;
    @FXML
    private TableView<Accounts> user_tb;
    @FXML
    private TableColumn<Accounts,String> stt,username,color,role;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HashService hashService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_tb.setPrefWidth(DashboardController.screenWidth);
        user_tb.setPrefHeight(DashboardController.screenHeigh-300);
        setFactoryTable();
        initUserTable();
        initRoleCbb();
    }
    private void initRoleCbb() {
        rolecbb.setItems(FXCollections.observableList(List.of(RoleEnum.USER.getName(),RoleEnum.ADMIN.getName())));
        rolecbb.getSelectionModel().selectFirst();
    }
    private void initUserTable() {
        user_tb.setItems(FXCollections.observableList(accountService.findAll()));
    }

    private void setFactoryTable(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(user_tb.getItems().indexOf(column.getValue())+1).asString());
        username.setCellValueFactory(new PropertyValueFactory<Accounts, String>("username"));
        color.setCellValueFactory(new PropertyValueFactory<Accounts, String>("color"));
        role.setCellValueFactory(new PropertyValueFactory<Accounts, String>("roles"));
    }
    @FXML
    public void rolecbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void user_clicked(MouseEvent mouseEvent) {
        Accounts a = user_tb.getSelectionModel().getSelectedItem();
        if (a!=null){
            user_tf.setText(a.getUsername());
            pawd.setText(a.getPasswd());
            colors.setText(a.getColor());
            rolecbb.getSelectionModel().select(a.getRoles());
        }
    }
    @FXML
    public void saveBtnAction(ActionEvent actionEvent) {
        try {
            String u = user_tf.getText().trim();
            String pass = pawd.getText().trim();
            if (!u.isEmpty() || !pass.isEmpty()){
                if (DialogMessage.callAlertWithMessage(null,"Luu thay doi", "Xac nhan luu thay doi", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                    Optional<Accounts> acc = accountService.findAccountByUsername(u);
                    if (acc.isEmpty()){
                        accountService.save(new Accounts(user_tf.getText(),null,rolecbb.getValue(),hashService.generateSHA1Hash(pawd.getText()),
                                color.getText(), StatusCons.ACTIVED.getName()));
                        DialogMessage.successShowing("Thanh cong");
                        initUserTable();
                    }
                }
            }else{
                DialogMessage.errorShowing("vui long dien tai khoan hoac mat khau");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
