package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {
    public static String lp = null;
    public static Long so_select = 0L;
    public static Stage primaryStage;
    public static Stage xuatStage ;
    public static Stage ctStage;
    public static Quarter findByTime;
    private static List<MiniLedgerDto> ttp_ls = new ArrayList<>();
    public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static int screenHeigh = (int) Screen.getPrimary().getBounds().getHeight();
    private String resetLayout = "-fx-cursor: hand;\n" +
            "-fx-background-color: #262626;\n";
    private String setupLayout = "-fx-border-color: #aaaaaa;\n" +
            "-fx-background-color: #aaaaaa;\n";

    private static int rowsPerPage = 15;

    @FXML
    private BorderPane borderpane_base;
    @FXML
    private VBox nx_vbox;
    @FXML
    private ComboBox<String> cbb_loaiphieu_filter;
    @FXML
    private ComboBox<Quarter> quy_cbb;
    @FXML
    private Label lb_from, lb_to,datetime_showing,preUser;
    @FXML
    private Pagination pagination_tbnxt;
    @FXML
    public TableView<MiniLedgerDto> tbTTNX;
    @FXML
    private TextField tf_search;
    @FXML
    private Button importBtn,exportBtn;
    @FXML
    private ImageView img_v;
    @FXML
    private TableColumn<MiniLedgerDto, String> nguoitao,so,ngaytao, loaiphieu,dvi_nhan,dvi_xuat,nvu, soluong,tong;
    @FXML
    private HBox dvi_menu,nxt_menu, dinhmuc_menu,tonkho_menu, nhiemvu_menu,setting,report;
    @FXML
    private AnchorPane main_menu;

    @Autowired
    private QuarterService quarterService;
    @Autowired
    private LedgerService ledgerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbTTNX.setPrefWidth(screenWidth);
        tbTTNX.setPrefHeight(screenHeigh-300);
        Common.hoverButton(importBtn ,"#040cb5");
        Common.hoverButton(exportBtn,"#27b50e");

        preUser.setText("--- " + ConnectLan.pre_acc.getUsername()+" ---");
        so_select=0L;
        getCurrentQuarter();
        ttp_ls = ledgerService.findInterfaceLedger(StatusCons.ACTIVED.getName(), findByTime.getId());
        getCurrentTiming();
        customStyleMenu();
        setDataToPhieuCombobox();
        setLedgersToTable(ttp_ls);
        setFactoryCell();
        setPagination_nxt(ttp_ls);
        initQuyCombobox();
        setStyleForClickedMEnu(nxt_menu,dvi_menu,dinhmuc_menu,tonkho_menu,nhiemvu_menu,setting,report);
        startUpdatingLedgerData();
    }
    private void startUpdatingLedgerData() {
        LedgerUpdateService service = new LedgerUpdateService();
        service.setOnSucceeded(event -> {
            List<MiniLedgerDto> ls =service.getValue();
            setLedgersToTable(ls);
            setPagination_nxt(ls);
        });
        service.start();
    }

    @FXML
    public void importActionClick(ActionEvent actionEvent) throws IOException{
        primaryStage = new Stage();
        Common.openNewStage("nhap.fxml", primaryStage,"FORM NHAP", StageStyle.UTILITY);
        updateData();
    }
    private void updateData(){
        ttp_ls = ledgerService.findInterfaceLedger(StatusCons.ACTIVED.getName(), findByTime.getId());
        setLedgersToTable(ttp_ls);
        setPagination_nxt(ttp_ls);
    }
    @FXML
    public void exportBtnClick(ActionEvent actionEvent) throws IOException {
        xuatStage = new Stage();
        Common.openNewStage("xuat.fxml", xuatStage,"FORM XUAT",StageStyle.UTILITY);
        updateData();
    }
    @FXML
    public void nxt_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(nxt_menu,dvi_menu,dinhmuc_menu,tonkho_menu,nhiemvu_menu,setting,report);
        borderpane_base.setCenter(nx_vbox);
        getCurrentQuarter();
    }
    @FXML
    public void tonkho_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(tonkho_menu,nxt_menu,dvi_menu,dinhmuc_menu,nhiemvu_menu,setting,report);
        openFxml("tonkho.fxml");
    }
    @FXML
    public void nhiemvu_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(nhiemvu_menu,tonkho_menu,nxt_menu,dvi_menu,dinhmuc_menu,setting,report);
        openFxml("nhiemvu.fxml");
    }
    public static Node getNodeBySource(String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource(source));
        fxmlLoader.setControllerFactory(MainApplicationApp.context::getBean);
        return fxmlLoader.load();
    }
    @FXML
    public void dinhmuc_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(dinhmuc_menu,nhiemvu_menu,tonkho_menu,nxt_menu,dvi_menu,setting,report);
        openFxml("norm_menu.fxml");
    }
    @FXML
    public void dvi_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,nxt_menu,setting,report);
        openFxml("donvi_menu.fxml");
    }
    @FXML
    public void setting_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(setting,dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,nxt_menu,report);
        openFxml("setting_menu.fxml");
    }
    @FXML
    public void report_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(report,setting,dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,nxt_menu);
        try {
            VBox vBox = (VBox) getNodeBySource("reporters.fxml");
            borderpane_base.setCenter(vBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void quy_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void loc_phieu(ActionEvent actionEvent) {
        String lp = cbb_loaiphieu_filter.getSelectionModel().getSelectedItem();
        if (lp!=null){
            if (lp.equals("ALL")){
                mapData(ttp_ls);
            }else{
                List<MiniLedgerDto> ls = ttp_ls.stream().filter(x->String.valueOf(x.getLoai_phieu()).equals(lp)).toList();
                mapData(ls);
            }
        }
    }
    private void mapData(List<MiniLedgerDto> ls){
        setPagination_nxt(ls);
        setLedgersToTable(ls);
    }
    @FXML
    public void tb_selected(MouseEvent mouseEvent) {
        ctStage = new Stage();
        if (mouseEvent.getClickCount()==2){
            MiniLedgerDto m = tbTTNX.getSelectionModel().getSelectedItem();
            if (m!=null){
                so_select = (long) m.getSo();
                lp = m.getLoai_phieu();
                Common.openNewStage("chitietsc.fxml", ctStage,"CHI TIẾT",StageStyle.UTILITY);
            }
        }
    }
    private void initQuyCombobox(){
        ComponentUtil.setItemsToComboBox(quy_cbb, quarterService.findAllByYear(String.valueOf(Year.now().getValue())), Quarter::getName, input -> quarterService.findByName(input).orElse(null));
        quy_cbb.getSelectionModel().selectFirst();
    }
    private void getCurrentQuarter(){
        if (quarterService.findByCurrentTime(LocalDate.now()).isPresent()){
            findByTime = quarterService.findByCurrentTime(LocalDate.now()).get();
            lb_to.setTextFill(Color.rgb(33, 12, 162));
            lb_to.setText(findByTime.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
            lb_from.setTextFill(Color.rgb(33, 12, 162));
            lb_from.setText(findByTime.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        }else {
            DialogMessage.message("Thông báo", "Vui lòng tạo quý cho nam: " + Year.now().getValue(),
                    "HẾt quý", Alert.AlertType.CONFIRMATION);
            setStyleForClickedMEnu(setting,dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,nxt_menu,report);
            openFxml("setting_menu.fxml");
        }
    }
    private void setDataToPhieuCombobox(){
        List<String> nx_ = new ArrayList<>();
        nx_.add("ALL");
        nx_.add("NHAP");
        nx_.add("XUAT");
        cbb_loaiphieu_filter.setItems(FXCollections.observableArrayList(nx_));
        cbb_loaiphieu_filter.getSelectionModel().selectFirst();
    }
    private void customStyleMenu(){
        String cssLayout =
                "-fx-border-insets: 5;\n" +
                        "-fx-background-color: #262626;\n" +
                        "-fx-background-radius: 0 150 0 0;\n" ;
        main_menu.setStyle(cssLayout);
    }
    private void getCurrentTiming(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                datetime_showing.setText("Ngày: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private void setLedgersToTable(List<MiniLedgerDto> ls){
        tbTTNX.setItems(FXCollections.observableList(ls));
        tbTTNX.refresh();
    }
    public void setFactoryCell(){
        nguoitao.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("username"));
        so.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("so_str"));
        loaiphieu.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("loai_phieu"));
        dvi_nhan.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("dvi_nhap"));
        dvi_xuat.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("dvi_xuat"));
        nvu.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("nhiemvu"));
        soluong.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("count_str"));
        tong.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("tong_str"));
        ngaytao.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("timestamp_str"));
    }
    private void setPagination_nxt(List<MiniLedgerDto> ls){
        pagination_tbnxt.setPageFactory(this::createPage);
        pagination_tbnxt.setPrefHeight(screenHeigh-300);
        pagination_tbnxt.setPrefWidth(screenWidth);
        pagination_tbnxt.setPageCount((ls.size()/rowsPerPage) +1);
    }
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, ttp_ls.size());
        tbTTNX.setItems(FXCollections.observableArrayList(ttp_ls.subList(fromIndex, toIndex)));
        return tbTTNX;
    }
    private void setStyleForClickedMEnu(HBox selected, HBox remainder1,HBox remainder2,HBox remainder3,HBox remainder4,HBox remainder5,HBox re6){
        selected.setStyle(setupLayout);
        remainder1.setStyle(resetLayout);
        remainder2.setStyle(resetLayout);
        remainder3.setStyle(resetLayout);
        remainder4.setStyle(resetLayout);
        remainder5.setStyle(resetLayout);
        re6.setStyle(resetLayout);
    }
    private void openFxml(String fxml){
        try {
            HBox hBox = (HBox) getNodeBySource(fxml);
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isNumber(String text) {
        if (Common.isNumber(text)){
            tf_search.setStyle(null);
            return true;
        }
        DialogMessage.message(null,null,"Số không được chứa ký tự.", Alert.AlertType.WARNING);
        tf_search.setStyle(CommonFactory.styleErrorField);
        return false;
    }
    @FXML
    public void searchClicked(MouseEvent mouseEvent) {
        tf_search.setStyle(null);
        tf_search.selectAll();
    }
    @FXML
    public void seachAction(MouseEvent mouseEvent) {
        String text = tf_search.getText().trim();
        if(!text.isEmpty()){
            if (isNumber(text)){
                List<MiniLedgerDto> ls = ttp_ls.stream().filter(x->String.valueOf(x.getSo()).contains(text)).toList();
                mapData(ls);
            }
        } else {
            mapData(ttp_ls);
        }
    }
    @FXML
    public void search_exit(MouseEvent mouseEvent) {
        img_v.setStyle("-fx-opacity: 1.0;");
    }
    @FXML
    public void search_enter(MouseEvent mouseEvent) {
        img_v.setStyle("-fx-opacity: 0.7;");
    }
}
