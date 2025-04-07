package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.DefaultVarCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.InventoryUnitDto;
import com.xdf.xd_f371.dto.PttkDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class TonkhoController implements Initializable {

    public static Stage tk_stage;
    private List<InventoryUnitDto> inventoryUnitDtoArrayList = new ArrayList<>();
    public static InventoryUnitDto pickTonKho = new InventoryUnitDto();
    private static List<PttkDto> pttkDtos = new ArrayList<>();
    @FXML
    private TableView<InventoryUnitDto> tb_inventory;
    @FXML
    public TableView<PttkDto> pttk_tb;
    @FXML
    public TableColumn<PttkDto, String> pttk_stt,pttk_loaixd,pttk_tenxd,pttk_e916,pttk_e921,pttk_e923,pttk_e927,pttk_dnb,pttk_dka,pttk_dvi,pttk_dns,pttk_fbo,pttk_tdv;
    @FXML
    public TableColumn<InventoryUnitDto, String> col_stt_i,col_tenxd_i,col_cl_i,col_nvdx_tdk_i,col_sscd_tdk_i,
            col_cong_tdk_i, col_pre_nvdx_i, col_pre_sscd_i,col_pre_inv_i, col_nvdx_datt_i, col_sscd_datt_i,col_cong_datt_i;
    @FXML
    private TextField search_inventory,timkiem_pttk_tf;
    @FXML
    private CheckBox tdvChk;
    @FXML
    private Label sd_lb, ed_lb,dv_lb,time_ref_lb,total_nvdx_lb,total_sscd_lb,total_lb,xd_selected_lb;
    @FXML
    private ListView<String> priceLs;
    @FXML
    private TableView<TransactionHistory> transaction_tb;
    @FXML
    private TableColumn<TransactionHistory,String> stt3,nx,timexd3,mucgia3,danhap3,conton3;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private TransactionHistoryService transactionHistoryService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSize();
        setLb();
        fillDataToTableInventoryUnit();
        setCellFactoryInventoryTb();
        setCellFactoryPttk();
        initPttkTb();
        setCellFactoryForTransactionTb();
        searching(inventoryUnitDtoArrayList.stream().map(InventoryUnitDto::getTenxd).toList());
    }

    private void initSize() {
        transaction_tb.setPrefWidth(DashboardController.screenWidth-700);
        tb_inventory.setPrefWidth(DashboardController.screenWidth-900);
        tb_inventory.setPrefHeight(DashboardController.screenHeigh-850);
        priceLs.setPrefHeight(DashboardController.screenHeigh-850);
        pttk_tb.setPrefWidth(DashboardController.screenWidth);
        pttk_tb.setPrefHeight(DashboardController.screenHeigh-350);
    }

    private void fillDataToTableInventoryUnit() {
        if (DashboardController.ref_Dv!=null){
            dv_lb.setText("--Tồn kho: " +DashboardController.ref_Dv.getTen());
            inventoryUnitDtoArrayList = transactionHistoryService.getInventoryOf_Lxd(DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date());
            setDataToTbInventory(inventoryUnitDtoArrayList);
        }
//        else{
//            dv_lb.setText("--Tồn kho toàn đơn vị--");
//            inventoryUnitDtoArrayList = inventoryService.getAllInventoryUnitDto();
//            setDataToTbInventory(inventoryUnitDtoArrayList);
//        }
    }
    private void setTransactionHistoryList(int xd_id){
        List<TransactionHistory> ls = transactionHistoryService.getTransactionHistoryByDate(xd_id,DashboardController.ref_Quarter.getEnd_date());
        ls.forEach(x->{
            x.setSoluong_str(TextToNumber.textToNum_2digits(x.getSoluong()));
            x.setTon(TextToNumber.textToNum_2digits(x.getTonkhotong()));
            x.setMucgia_str(TextToNumber.textToNum_2digits(x.getMucgia()));
            x.setCreated_at_str(x.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        setItemsForTransactionTb(ls);
    }
    private void setDataToTbInventory(List<InventoryUnitDto> ls){
        tb_inventory.setItems(FXCollections.observableList(ls));
        tb_inventory.refresh();
    }
    private void setItemsForListMucgia(List<String> ls){
        priceLs.setItems(FXCollections.observableList(ls));
        priceLs.refresh();
    }
    private void setCellFactoryForTransactionTb(){
        stt3.setSortable(false);
        stt3.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(transaction_tb.getItems().indexOf(column.getValue())+1).asString());
        nx.setCellValueFactory(new PropertyValueFactory<TransactionHistory, String>("loaiphieu"));
        timexd3.setCellValueFactory(new PropertyValueFactory<TransactionHistory, String>("created_at_str"));
        mucgia3.setCellValueFactory(new PropertyValueFactory<TransactionHistory, String>("mucgia_str"));
        danhap3.setCellValueFactory(new PropertyValueFactory<TransactionHistory, String>("soluong_str"));
        conton3.setCellValueFactory(new PropertyValueFactory<TransactionHistory, String>("ton"));
        transaction_tb.setRowFactory(tv -> new TableRow<TransactionHistory>() {
            @Override
            protected void updateItem(TransactionHistory transactionHistory, boolean empty) {
                super.updateItem(transactionHistory, empty);

                if (transactionHistory == null || empty) {
                    setStyle(null);
                } else {
                    if (transactionHistory.getLoaiphieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())) {
                        setStyle("-fx-background-color: #5f94e8;"+
                                "-fx-border-color: transparent transparent black transparent; " +
                                "-fx-border-width: 0px 0px 1px;");
                    }else if (transactionHistory.getLoaiphieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName())) {
                        setStyle("-fx-background-color: #fa4655;"+
                                "-fx-border-color: transparent transparent black transparent; " +
                                "-fx-border-width: 0px 0px 1px;");
                    }else if (transactionHistory.getLoaiphieu().equals(LoaiPhieuCons.PHIEU_THAYDOI.getName())) {
                        setStyle("-fx-background-color: #f78520;"+
                                "-fx-border-color: transparent transparent black transparent; " +
                                "-fx-border-width: 0px 0px 1px;");
                    }
                }
            }
        });
    }
    private void setItemsForTransactionTb(List<TransactionHistory> ls){
        transaction_tb.setItems(FXCollections.observableList(ls));
        transaction_tb.refresh();
    }
    private void setItemsForLbTotal(double nvdx,double sscd){
        total_nvdx_lb.setText(TextToNumber.textToNum_2digits(nvdx));
        total_sscd_lb.setText(TextToNumber.textToNum_2digits(sscd));
        total_lb.setText(TextToNumber.textToNum_2digits(nvdx+sscd));
    }
    private void setLb(){
        sd_lb.setText(DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));
        time_ref_lb.setText(LocalDate.now().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));
        ed_lb.setText(DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));
    }

    private void initPttkTb(){
        if (DashboardController.ref_Quarter.getStart_date()!=null){
            pttkDtos = inventoryService.mapPttkPetro();
            mapPttkTb(pttkDtos);
        }else{
            mapPttkTb(new ArrayList<>());
        }
    }
    private void mapPttkTb(List<PttkDto> ls){
        pttk_tb.setItems( FXCollections.observableArrayList(ls));
        pttk_tb.refresh();
    }
    private void setCellFactoryInventoryTb(){
        col_stt_i.setSortable(false);
        col_stt_i.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_inventory.getItems().indexOf(column.getValue())+1).asString());
        col_tenxd_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tenxd"));
        col_cl_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("loai"));
        col_nvdx_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_nvdx_str"));
        col_sscd_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_sscd_str"));
        col_cong_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_str"));
        col_pre_nvdx_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_nvdx_str"));
        col_pre_sscd_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_sscd_str"));
        col_pre_inv_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_str"));
        col_nvdx_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_nvdx_str"));
        col_sscd_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_sscd_str"));
        col_cong_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_str"));
    }
    private void setCellFactoryPttk(){
        pttk_stt.setSortable(false);
        pttk_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(pttk_tb.getItems().indexOf(column.getValue())+1).asString());
        pttk_tenxd.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("tenxd"));
        pttk_loaixd.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("loai"));
        pttk_e916.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e916"));
        pttk_e921.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e921"));
        pttk_e923.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e923"));
        pttk_e927.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e927"));
        pttk_dnb.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dnb"));
        pttk_dka.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dka"));
        pttk_dvi.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dvi"));
        pttk_dns.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dns"));
        pttk_fbo.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("fb"));
        pttk_tdv.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("tdv"));
    }
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().contains(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }
    @FXML
    public void setClickToTonTb(MouseEvent mouseEvent) {
        InventoryUnitDto spotDto = tb_inventory.getSelectionModel().getSelectedItem();
        setListAndLabel(spotDto.getPetro_id());
        setTransactionHistoryList(spotDto.getPetro_id());
        xd_selected_lb.setText(spotDto.getTenxd());
        setCellFactoryForTransactionTb();
        if (mouseEvent.getClickCount() == 2) {
            pickTonKho = spotDto;
            tk_stage = new Stage();
            List<InventoryDto> list;
//            if (ref_unit!=null){
                list = inventoryService.findPreInventoryPetroFollowUnit(spotDto.getPetro_id(),DashboardController.ref_Dv.getId());
//            }else{
//                list = inventoryService.findPreInventoryPetro(spotDto.getPetro_id());
//            }
            if (list==null){
                DialogMessage.successShowing(spotDto.getTenxd() + " đã hết hàng!! Vui lòng nhập thêm");
            }else{
                Common.openNewStage("changesscd-form.fxml", tk_stage,"Thay Doi", StageStyle.DECORATED);
                fillDataToTableInventoryUnit();
            }
        }
    }
    private void setListAndLabel(int xd_id){
        List<TransactionHistory> ls = transactionHistoryService.getLastestTimeForEachPrices(xd_id);
        List<String> strls = new ArrayList<>();
        double total_nvdx = 0;
        double total_sscd = 0;
        for (TransactionHistory x : ls) {
            strls.add("Mức giá: " + TextToNumber.textToNum_2digits(x.getMucgia()) + " (vnđ), Tồn cho (NVDX): "
                    + TextToNumber.textToNum_2digits(x.getTonkho_gia()) + " (Lit), Tồn cho (SSCD): " + TextToNumber.textToNum_2digits(x.getTonkh_gia_sscd())+" (Lit)");
            total_nvdx = total_nvdx + x.getTonkho_gia();
            total_sscd = total_sscd + x.getTonkh_gia_sscd();
        }
        setItemsForListMucgia(strls);
        setItemsForLbTotal(total_nvdx,total_sscd);
    }
    @FXML
    public void addnew_petro(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("add_inv_form.fxml", tk_stage,"THEM MOI", StageStyle.DECORATED);
        fillDataToTableInventoryUnit();
    }
    @FXML
    public void timkiem_tk_clicked(MouseEvent mouseEvent) {
        search_inventory.selectAll();
    }
    @FXML
    public void inv_kr(KeyEvent keyEvent) {
        String text = search_inventory.getText().trim();
        if (!text.isEmpty()){
            List<InventoryUnitDto> ls = inventoryUnitDtoArrayList.stream().filter(x->x.getTenxd().equals(text)).toList();
            setDataToTbInventory(ls);
        }else{
            setDataToTbInventory(inventoryUnitDtoArrayList);
        }
    }
    @FXML
    public void endQuarterAction(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("quarter.fxml", tk_stage,null, StageStyle.UTILITY);
        fillDataToTableInventoryUnit();
    }

    @FXML
    public void pttkClicked(MouseEvent mouseEvent) {
        timkiem_pttk_tf.selectAll();
    }
    @FXML
    public void pttk_kr(KeyEvent keyEvent) {
        String t = timkiem_pttk_tf.getText().trim();
        if (!t.isEmpty()){
            mapPttkTb(pttkDtos.stream().filter(x->x.getTenxd().toLowerCase().contains(t)).toList());
        }else{
            mapPttkTb(pttkDtos);
        }
    }
    @FXML
    public void tdvChckAction(ActionEvent actionEvent) {
        dv_lb.setText("--Tồn kho: " +DashboardController.ref_Dv.getTen());
        inventoryUnitDtoArrayList = transactionHistoryService.getInventoryOf_Lxd(DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date());
        setDataToTbInventory(inventoryUnitDtoArrayList);
    }
    @FXML
    public void refreshPttkAction(ActionEvent actionEvent) {
        initPttkTb();
    }
}