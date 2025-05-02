package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.dto.QuarterDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DashboardController implements Initializable {
    public static String so_select = null;
    public static Stage primaryStage;
    public static NguonNx ref_Dv=null;
    public static QuarterDto ref_Quarter=null;
    public static List<PhuongTien> xmt_ls = new ArrayList<>();
    public static List<NhiemVu> nv_ls = new ArrayList<>();
    public static List<LoaiXangDau> xd_ls = new ArrayList<>();
    public static List<NguonNx> units_ls = new ArrayList<>();
    public static List<Tcn> tcn_ls = new ArrayList<>();
    public static List<UnitXmt> unitxmt_ls = new ArrayList<>();
    public static List<UnitXmt> unitxmt_ls_all = new ArrayList<>();
    public static List<ChitietNhiemVu> ctnv_ls = new ArrayList<>();
    public static List<NhiemVuDto> ctnv_ls_all = new ArrayList<>();
    public static Map<String, List<TrucThuoc>> map = new HashMap<>();
    public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static int screenHeigh = (int) Screen.getPrimary().getBounds().getHeight();
    private String resetLayout = "-fx-cursor: hand;\n" +
            "-fx-background-color: #262626;\n";
    private String setupLayout = "-fx-border-color: #aaaaaa;\n" +
            "-fx-background-color: #aaaaaa;\n";

//    private static int rowsPerPage = 15;

    @FXML
    private BorderPane borderpane_base;
    @FXML
    private Label datetime_showing,preUser;
    @FXML
    private HBox dvi_menu, dinhmuc_menu,tonkho_menu, nhiemvu_menu,report,ledger_menu;
    @FXML
    private AnchorPane main_menu;

    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private UnitXmtService unitXmtService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private TructhuocService tructhuocService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private TcnService tcnService;
    @Autowired
    private LoaiXdService loaiXdService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLabel();
        getQuarterList();
        initRefDv();
        initPulbicList();
        getTructhuocList();
        getCurrentTiming();
        customStyleMenu();
        setStyleForClickedMEnu(ledger_menu,tonkho_menu,dvi_menu,dinhmuc_menu,nhiemvu_menu,report);
        openFxml("ledger.fxml");
    }
    private void initPulbicList() {
        xmt_ls = phuongtienService.findAll();
        unitxmt_ls = unitXmtService.findAllByMaybay(ref_Dv.getId());
        unitxmt_ls_all = unitXmtService.findAll();
        ctnv_ls = chitietNhiemvuService.findAllCtnvByTypeMaybay();
        ctnv_ls_all = chitietNhiemvuService.findAllDtoById();
        units_ls = nguonNxService.findAll();
        tcn_ls = tcnService.findAll();
        xd_ls = loaiXdService.findAll();
        nv_ls = chitietNhiemvuService.findAll();
    }
    private void getQuarterList() {
        try {
            ref_Quarter = configurationService.getListQuarter(LocalDate.now().getYear()).stream()
                    .filter(x->(x.getStart_date().isBefore(LocalDate.now()) || x.getStart_date().isEqual(LocalDate.now())) &&
                            (x.getEnd_date().isAfter(LocalDate.now()) || x.getEnd_date().isEqual(LocalDate.now()))).findFirst().orElse(null);
        } catch (RuntimeException e) {
            DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
            throw new RuntimeException(e);
        }
    }
    private Map<String, List<TrucThuoc>> getTructhuocList(){
        List<TrucThuoc> tt_nhap =  new ArrayList<>();
        List<TrucThuoc> tt_xuat =  new ArrayList<>();
        for (int i=0; i<tructhuocService.findAll().size(); i++) {
            TrucThuoc tt = tructhuocService.findAll().get(i);
            if (tt.getLoaiphieu().contains(LoaiPhieuCons.X.getName())){
                tt_xuat.add(tt);
            }
            if(tt.getLoaiphieu().contains(LoaiPhieuCons.N.getName())){
                tt_nhap.add(tt);
            }
        }
        map = new HashMap<>();
        map.put(LoaiPhieuCons.PHIEU_NHAP.getName(),tt_nhap);
        map.put(LoaiPhieuCons.PHIEU_XUAT.getName(),tt_xuat);
        return map;
    }

    private void initLabel() {
        ConnectLan.primaryStage.getIcons().add(new Image(Objects.requireNonNull(MainApplicationApp.class.getResourceAsStream("img/icon_app4.png"))));
        preUser.setText("--- " + ConnectLan.pre_acc.getUsername()+" ---");
    }

    @FXML
    public void tonkho_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(tonkho_menu,dvi_menu,dinhmuc_menu,nhiemvu_menu,report,ledger_menu);
        openFxml("tonkho.fxml");
    }
    @FXML
    public void ledger_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(ledger_menu,tonkho_menu,dvi_menu,dinhmuc_menu,nhiemvu_menu,report);
        openFxml("ledger.fxml");
    }
    @FXML
    public void nhiemvu_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(nhiemvu_menu,tonkho_menu,dvi_menu,dinhmuc_menu,report,ledger_menu);
        openFxml("nhiemvu.fxml");
    }
    @FXML
    public void dinhmuc_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(dinhmuc_menu,nhiemvu_menu,tonkho_menu,dvi_menu,report,ledger_menu);
        openFxml("norm_menu.fxml");
    }
    @FXML
    public void dvi_menu_action(MouseEvent event) {
        setStyleForClickedMEnu(dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,report,ledger_menu);
        openFxml("donvi_menu.fxml");
    }
    @FXML
    public void report_menu_action(MouseEvent mouseEvent) {
        setStyleForClickedMEnu(report,dvi_menu,dinhmuc_menu,nhiemvu_menu,tonkho_menu,ledger_menu);
        try {
            VBox vBox = (VBox) getNodeBySource("reporters.fxml");
            borderpane_base.setCenter(vBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void SettingClicked(ActionEvent actionEvent) {
        primaryStage = new Stage();
        primaryStage.setOnHidden(event -> {
            ConnectLan.primaryStage.toFront();
            ConnectLan.primaryStage.requestFocus();
        });
        Common.openNewStage("setting.fxml", primaryStage,"CÀI ĐẶT",StageStyle.UTILITY);
    }
    private void initRefDv() {
        Optional<Configuration> configuration = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        if (configuration.isPresent()){
            Optional<NguonNx> nx = nguonNxService.findById(Integer.parseInt(configuration.get().getValue()));
            nx.ifPresent(x->ref_Dv=x);
        }
    }
    public static Node getNodeBySource(String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource(source));
        fxmlLoader.setControllerFactory(MainApplicationApp.context::getBean);
        return fxmlLoader.load();
    }public static FXMLLoader getFXLoadderBySource(String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource(source));
        fxmlLoader.setControllerFactory(MainApplicationApp.context::getBean);
        return fxmlLoader;
    }
    private void setStyleForClickedMEnu(HBox selected, HBox remainder1,HBox remainder2,HBox remainder3,HBox remainder4,HBox remainder5){
        selected.setStyle(setupLayout);
        selected.setDisable(true);
        remainder1.setStyle(resetLayout);
        remainder1.setDisable(false);
        remainder2.setStyle(resetLayout);
        remainder2.setDisable(false);
        remainder3.setStyle(resetLayout);
        remainder3.setDisable(false);
        remainder4.setStyle(resetLayout);
        remainder4.setDisable(false);
        remainder5.setStyle(resetLayout);
        remainder5.setDisable(false);
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
    private void openFxml(String fxml){
        try {
            HBox hBox = (HBox) getNodeBySource(fxml);
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    private void setPagination_nxt(List<MiniLedgerDto> ls){
//        pagination_tbnxt.setPageFactory(this::createPage);
//        pagination_tbnxt.setPrefHeight(screenHeigh-300);
//        pagination_tbnxt.setPrefWidth(screenWidth);
//        pagination_tbnxt.setPageCount((ls.size()/rowsPerPage) +1);
//    }
//    private void mapData(List<MiniLedgerDto> ls){
//        setPagination_nxt(ls);
//        setLedgersToTable(ls);
//    }
//    private Node createPage(int pageIndex) {
//        int fromIndex = pageIndex * rowsPerPage;
//        int toIndex = Math.min(fromIndex + rowsPerPage, ttp_ls.size());
//        tbTTNX.setItems(FXCollections.observableArrayList(ttp_ls.subList(fromIndex, toIndex)));
//        return tbTTNX;
//    }
//    @FXML
//    public void refreshAction(ActionEvent actionEvent) {
//        ttp_ls = ledgerService.findAllInterfaceLedger(StatusCons.ACTIVED.getName());
//        setPagination_nxt(ttp_ls);
//        setLedgersToTable(ttp_ls);
//    }

}
