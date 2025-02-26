package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.InvDto3;
import com.xdf.xd_f371.dto.LedgerDto2;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.FxUtilTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class LedgerController implements Initializable {
    private static List<LedgerDto2> ledgerSelectList = new ArrayList<>();
    private static List<LocalDate> selectedDateLs = new ArrayList<>();

    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private NguonNxService nguonNxService;

    @FXML
    private Button ref_to_root;
    @FXML
    private TableView<LedgerDto2> ledgers_table,doichieu_table_1,doichieu_table_2;
    @FXML
    private TableView<InvDto3> tonkho_tb;
    @FXML
    private Label tab1_dvi_label,root_unit_lable,unitClickedLable;
    @FXML
    private DatePicker st_time,lst_time,tab2_tungay,tab2_denngay;
    @FXML
    private ListView<String> date_ls;
    @FXML
    private RadioButton all_rd,nhap_rd,xuat_rd;
    @FXML
    private CheckBox mucgiaCk;
    @FXML
    private TextField search_by_name_tf;
    @FXML
    private ComboBox<NguonNx> dvi_ref_cbb;
    @FXML
    private HBox importBtn;
    @FXML
    private ImageView searchImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all_rd.setSelected(true);
        hoverBtn();
        initVietnameseDate();
        initTableSize();
        initRootUnitLable();
        initNnxCombobox();
        initStartDate();

    }

    private void initStartDate() {
        st_time.setValue(ConnectLan.pre_acc.getSd());
        lst_time.setValue(ConnectLan.pre_acc.getEd());
        tab2_tungay.setValue(ConnectLan.pre_acc.getSd());
        tab2_denngay.setValue(ConnectLan.pre_acc.getEd());
    }

    private void initNnxCombobox() {
        ComponentUtil.setItemsToComboBox(dvi_ref_cbb,nguonNxService.findByAllBy(),NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_ref_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
    }

    private void hoverBtn() {
        Common.hoverButton(ref_to_root ,"#027a20");
        importBtn.setOnMouseEntered(e -> importBtn.setStyle("-fx-background-color: #027a20; -fx-border-color: #000000; -fx-border-width:3;-fx-background-radius:10;-fx-border-radius:10"));
        importBtn.setOnMouseExited(e -> importBtn.setStyle("-fx-background-color: #027a20;-fx-border-color:#a8a8a8;-fx-background-radius:10;-fx-border-radius:10"));
    }

    private void initVietnameseDate() {
        CommonFactory.setVi_DatePicker(st_time);
        CommonFactory.setVi_DatePicker(lst_time);
        CommonFactory.setVi_DatePicker(tab2_tungay);
        CommonFactory.setVi_DatePicker(tab2_denngay);
    }

    private void initRootUnitLable() {
        root_unit_lable.setText(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()).get(0).getTen());
        tab1_dvi_label.setText(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()).get(0).getTen());
        unitClickedLable.setText("-----");
    }

    private void initTableSize() {
        ledgers_table.setPrefWidth(DashboardController.screenWidth-100);
        ledgers_table.setPrefHeight(DashboardController.screenHeigh-500);
        doichieu_table_1.setPrefWidth(DashboardController.screenWidth-100);
        doichieu_table_1.setPrefHeight(DashboardController.screenHeigh-500);
        doichieu_table_2.setPrefWidth(DashboardController.screenWidth-100);
        doichieu_table_2.setPrefHeight(DashboardController.screenHeigh-500);
        tonkho_tb.setPrefWidth(DashboardController.screenWidth-100);
        tonkho_tb.setPrefHeight(DashboardController.screenHeigh-500);
    }

    @FXML
    public void select_date_Clicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void all_radio_action(ActionEvent actionEvent) {
    }
    @FXML
    public void nhap_radio_action(ActionEvent actionEvent) {
    }
    @FXML
    public void xuat_radio_action(ActionEvent actionEvent) {
    }
    @FXML
    public void select_ledger_table(MouseEvent mouseEvent) {
    }
    @FXML
    public void doichieu_table_1Clicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void tungayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void denngayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void ref_to_rootAction(ActionEvent actionEvent) {
    }
    @FXML
    public void dvi_ref_cbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void doichieu_table_2Click(MouseEvent mouseEvent) {
    }
    @FXML
    public void importBtnClick(MouseEvent mouseEvent) {
    }
    @FXML
    public void search_by_name_Click(MouseEvent mouseEvent) {
    }
    @FXML
    public void search_by_name_tfAction(ActionEvent actionEvent) {
    }
    @FXML
    public void mucgiaCkAction(ActionEvent actionEvent) {
    }
    @FXML
    public void dateLoadingClick(MouseEvent mouseEvent) {
        st_time.setValue(ConnectLan.pre_acc.getSd());
        lst_time.setValue(ConnectLan.pre_acc.getEd());
    }
}
