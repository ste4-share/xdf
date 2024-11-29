package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.dto.LichsuXNK;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.QuarterRepository;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
@Component
public class DashboardController implements Initializable {

    public static Stage primaryStage;
    public static Stage xuatStage ;
    public static Stage ctStage;
    public static List<Ledger> ledgerList = new ArrayList<>();
    private static List<MiniLedgerDto> ttp_ls = new ArrayList<>();

    private static List<LichsuXNK> lichsuXNKS = new ArrayList<>();
    private static int rowsPerPage = 9;
    private boolean addedBySelection_lstb = false;

    @FXML
    private BorderPane borderpane_base;
    @FXML
    private BarChart<?, ?> bc_barchart;

    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private VBox nx_vbox, vb_nxt_tb;
    @FXML
    private TableView<LichsuXNK> tb_viewlichsu;
    @FXML
    private ComboBox<String> cbb_loaiphieu_filter;
    @FXML
    private Label lb_from, lb_to,datetime_showing;
    @FXML
    private Pagination pagination_tbnxt,pagination_lichsu;

//    @FXML
//    private TableColumn<Inventory, String> col_tkt_loaixd, col_tkt_soluong;
    @FXML
    private TableColumn<LichsuXNK, String> col_lsnxk_tenxd,col_lsnxk_mucgia, col_lsnxk_loaiphieu,col_lsnxk_soluong, col_lsnxk_tontruoc,col_lsnxk_tonsau,col_lsnxk_ngay;
//    @FXML
//    private TableColumn<Inventory, String> col_slton,col_loaixd,col_stt, col_mucgia;
    @FXML
    public TableView<MiniLedgerDto> tbTTNX;
    @FXML
    private TableColumn<MiniLedgerDto, String> so,ngaytao, loaiphieu, soluong,tong;
    @FXML
    private TextField tf_search_txnt,tf_search_history;
    @FXML
    private LichsuNXKService lichsuNXKService = new LichsuNXKImp();
    public static Quarter findByTime;
    @FXML
    private HBox dvi_menu,nxt_menu, loai_xd_menu, haohut_menu, dinhmuc_menu,tonkho_menu, nhiemvu_menu;
    @FXML
    private AnchorPane main_menu;

    @Autowired
    private QuarterRepository quarterRepository;
    @Autowired
    private LedgersRepo ledgersRepo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctStage = new Stage();
        ttp_ls = ledgersRepo.findInterfaceLedger();
        lichsuXNKS = new ArrayList<>();
        ledgerList = ledgersRepo.findAll();
//        getDataToChart(root_inventory);
        getCurrentQuarter();
        getCurrentTiming();
        resetStyleField();
        customStyleMenu();
        setDataToPhieuCombobox();
        setDataToViewTable();

//        setUpForSearchCompleteTion();
//        searching();
        fillDataToLichsuTb();
        tbTTNX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2){
                    try {
                        MiniLedgerDto miniLedgerDto =  tbTTNX.getSelectionModel().getSelectedItem();
                        Common.openNewStage("chitietsc.fxml", xuatStage,"CHI TIẾT");
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        setColLichSuNXK();
    }

    private void resetStyleField() {
        String cssLayout1 =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        nxt_menu.setStyle(cssLayout1
        );
        dvi_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
    }

    private void getCurrentQuarter(){
        findByTime = quarterRepository.findByCurrentTime(LocalDate.now()).get();
        lb_to.setTextFill(Color.rgb(33, 12, 162));
        lb_to.setText(findByTime.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_from.setTextFill(Color.rgb(33, 12, 162));
        lb_from.setText(findByTime.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
    }

    private void setDataToPhieuCombobox(){
        List<String> nx_ = new ArrayList<>();
        nx_.add("ALL");
        nx_.add("PHIẾU NHẬP");
        nx_.add("PHIẾU XUẤT");
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

    private void fillDataToLichsuTb(){
        lichsuXNKS = new ArrayList<>();
        lichsuXNKS = lichsuNXKService.getAll();
        lichsuXNKS.forEach(x -> {
            x.setTonsau_str(TextToNumber.textToNum(String.valueOf(x.getTonsau())));
            x.setMucgia(TextToNumber.textToNum(x.getMucgia()));
            x.setSoluong_str(TextToNumber.textToNum(String.valueOf(x.getSoluong())));
            x.setTontruoc_str(TextToNumber.textToNum(String.valueOf(x.getTontruoc())));
        });
        tb_viewlichsu.setItems(FXCollections.observableArrayList(lichsuXNKS));
        pagination_lichsu.setPageCount((lichsuXNKS.size()/rowsPerPage)+1);
        pagination_lichsu.setPrefHeight(400);
        setPagination_lichsu();
        List<String> search_arr_forLs = new ArrayList<>();
        for(int i=0; i< lichsuXNKS.size(); i++){
            search_arr_forLs.add(lichsuXNKS.get(i).getTen_xd());
        }
//        setUpForSearchCompleteTion_lichsu(search_arr_forLs);
        searchingFor_lichsuTb();
    }

//    private void getDataToChart(List<TonkhoTong> tongs){
//        if (!tongs.isEmpty()){
//            XYChart.Series set1 = new XYChart.Series<>();
//            tongs.forEach(x -> {
//
//                LoaiXangDau loaiXangDau = loaiXdService.findLoaiXdByID_non(x.getId_xd());
//                set1.getData().add(new XYChart.Data<>(loaiXangDau.getTenxd(),x.getTck_sum()));
//            });
//            bc_barchart.getData().addAll(set1);
//        }
//    }

    private void getCurrentTiming(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                datetime_showing.setText("Ngày: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setColLichSuNXK(){
        col_lsnxk_ngay.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("createTime"));
        col_lsnxk_tenxd.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("ten_xd"));
        col_lsnxk_loaiphieu.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("loai_phieu"));
        col_lsnxk_tontruoc.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tontruoc_str"));
        col_lsnxk_tonsau.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tonsau_str"));
        col_lsnxk_soluong.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("soluong_str"));
        col_lsnxk_mucgia.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("mucgia"));
    }

    public void setDataToViewTable(){
        ttp_ls =ledgersRepo.findInterfaceLedger();
        so.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("so_str"));
        loaiphieu.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("loai_phieu"));
        ngaytao.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("timestamp_str"));
        soluong.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("count_str"));
        tong.setCellValueFactory(new PropertyValueFactory<MiniLedgerDto,String>("tong_str"));
        tbTTNX.setItems(FXCollections.observableList(ttp_ls));
        tbTTNX.refresh();
        setPagination_nxt();
    }

    private void setPagination_nxt(){
        pagination_tbnxt.setPageFactory(this::createPage);
        pagination_tbnxt.setPrefHeight(400);
        pagination_tbnxt.setPageCount((ttp_ls.size()/rowsPerPage) +1);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, ttp_ls.size());
        tbTTNX.setItems(FXCollections.observableArrayList(ttp_ls.subList(fromIndex, toIndex)));
        return tbTTNX;
    }

    private void setPagination_lichsu(){
        pagination_lichsu.setPageFactory(this::createPage_lichsu);
        pagination_lichsu.setPrefHeight(400);
        pagination_lichsu.setPageCount((lichsuXNKS.size()/rowsPerPage) +1);
    }

    private Node createPage_lichsu(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, lichsuXNKS.size());
        tb_viewlichsu.setItems(FXCollections.observableArrayList(lichsuXNKS.subList(fromIndex, toIndex)));

        return tb_viewlichsu;
    }

    @FXML
    public void importActionClick(ActionEvent actionEvent) throws IOException{
        primaryStage = new Stage();
        Common.openNewStage("nhap.fxml", primaryStage,"FORM NHAP");
        setDataToViewTable();
//        fillDataToLichsuTb();
    }

    @FXML
    public void exportBtnClick(ActionEvent actionEvent) throws IOException {
        xuatStage = new Stage();
        Common.openNewStage("xuat.fxml", xuatStage,"FORM XUAT");
        setDataToViewTable();
//        getDataToChart(prepare_addnew_inventory);
        fillDataToLichsuTb();
    }
    @FXML
    public void nxt_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                "-fx-background-color: #aaaaaa;\n" ;
        nxt_menu.setStyle(cssLayout);
        dvi_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        tonkho_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());

        borderpane_base.setCenter(nx_vbox);
    }
    @FXML
    public void tonkho_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                "-fx-background-color: #aaaaaa;\n" ;
        tonkho_menu.setStyle(cssLayout);
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());
        dvi_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());

        try {
            HBox hBox = (HBox) getNodeBySource("tonkho.fxml");
            borderpane_base.setCenter(hBox);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void lxd_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        loai_xd_menu.setStyle(cssLayout);
        dvi_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());
        tonkho_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
        try {
            VBox vBox = (VBox) getNodeBySource("petroleum_menu.fxml");
            borderpane_base.setCenter(vBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void nhiemvu_menu_action(MouseEvent mouseEvent) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        nhiemvu_menu.setStyle(cssLayout);
        dvi_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        tonkho_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());

        try {
            HBox hBox = (HBox) getNodeBySource("nhiemvu.fxml");
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Node getNodeBySource(String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource(source));
        fxmlLoader.setControllerFactory(MainApplicationApp.context::getBean);
        return fxmlLoader.load();
    }

    @FXML
    public void haohut_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        haohut_menu.setStyle(cssLayout);
        dvi_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());
        tonkho_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
    }
    @FXML
    public void dinhmuc_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        dinhmuc_menu.setStyle(cssLayout);
        dvi_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());
        tonkho_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
        try {
            HBox hBox = (HBox) getNodeBySource("norm_menu.fxml");
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void dvi_menu_action(MouseEvent event) {
        String cssLayout =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        dvi_menu.setStyle(cssLayout);
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nxt_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
        try {
            VBox vBox = (VBox) getNodeBySource("donvi_menu.fxml");
            borderpane_base.setCenter(vBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String resetStyle(){
        String cssLayout =
                "-fx-cursor: hand;\n" +
                        "-fx-background-color: #262626;\n" ;
        return cssLayout;
    }

    public void loc_phieu(ActionEvent actionEvent) {
//        String lp= cbb_loaiphieu_filter.getValue().trim();
//        if (lp.equals("PHIẾU NHẬP")){
//            List<TTPhieuDto> ttPhieuDtoList = ledgerDetailsService.getTTPhieu_ByLoaiPhieu("N");
//            List<TTPhieuModel> ttPhieuModelList = new ArrayList<>();
//
//            ttPhieuDtoList.forEach(item -> ttPhieuModelList.add(new TTPhieuModel(item.getSo(),item.getNgaytao(), item.getLoai_phieu().trim().equals("N") ? "Nhập" : "Xuất", item.getDvn(), item.getDvvc(),item.getTcn(),item.getHang_hoa(),TextToNumber.textToNum(String.valueOf(item.getTong())))));
//            tbTTNX.setItems(FXCollections.observableArrayList(ttPhieuModelList));
//        }else if (lp.equals("PHIẾU XUẤT")){
//            List<TTPhieuDto> ttPhieuDtoList = ledgerDetailsService.getTTPhieu_ByLoaiPhieu("X");
//            List<TTPhieuModel> ttPhieuModelList = new ArrayList<>();
//
//            ttPhieuDtoList.forEach(item -> ttPhieuModelList.add(new TTPhieuModel(item.getSo(),item.getNgaytao(), item.getLoai_phieu().trim().equals("N") ? "Nhập" : "Xuất", item.getDvn(), item.getDvvc(),item.getTcn(),item.getHang_hoa(),TextToNumber.textToNum(String.valueOf(item.getTong())))));
//            tbTTNX.setItems(FXCollections.observableArrayList(ttPhieuModelList));
//        }else {
//            List<TTPhieuDto> ttPhieuDtoList = ledgerDetailsService.getTTPhieu();
//            List<TTPhieuModel> ttPhieuModelList = new ArrayList<>();
//
//            ttPhieuDtoList.forEach(item -> ttPhieuModelList.add(new TTPhieuModel(item.getSo(),item.getNgaytao(), item.getLoai_phieu().trim().equals("N") ? "Nhập" : "Xuất", item.getDvn(), item.getDvvc(),item.getTcn(),item.getHang_hoa(),TextToNumber.textToNum(String.valueOf(item.getTong())))));
//            tbTTNX.setItems(FXCollections.observableArrayList(ttPhieuModelList));
//        } 
    }

    public void search_phieu_tnxt(ActionEvent actionEvent) {
    }

    public void search_history(ActionEvent actionEvent) {
    }
//
//    private void setUpForSearchCompleteTion(){
//        List<String> search_arr = new ArrayList<>();
//        for(int i=0; i< ttp_ls.size(); i++){
//            search_arr.add(ttp_ls.get(i).getSo());
//        }
//        TextFields.bindAutoCompletion(tf_search_txnt, t -> {
//            return search_arr.stream().filter(elem
//                    -> {
//                return String.valueOf(elem).startsWith(t.getUserText().trim());
//            }).collect(Collectors.toList());
//        });
//        tf_search_txnt.setOnKeyPressed(e -> {
//            addedBySelection = false;
//        });
//
//        tf_search_txnt.setOnKeyReleased(e -> {
//            if (tf_search_txnt.getText().trim().isEmpty()){
//                ttp_ls = new ArrayList<>();
//                tbTTNX.setItems(FXCollections.observableArrayList(new ArrayList<>()));
//                List<TTPhieuDto> ttPhieuDtoList = ledgerDetailsService.getTTPhieu();
//
//                ttPhieuDtoList.forEach(item -> {
//                    ttp_ls.add(new TTPhieuModel(item.getSo(), item.getNgaytao(), item.getLoai_phieu().trim().equals("N") ? "Nhập" : "Xuất", item.getDvn(), item.getDvvc(), item.getTcn(), item.getHang_hoa(), TextToNumber.textToNum(String.valueOf(item.getTong()))));
//                });
//                tbTTNX.setItems(FXCollections.observableArrayList(ttp_ls));
//            }
//            addedBySelection = true;
//        });
//    }

    private void searching(){
//        tf_search_txnt.textProperty().addListener(e -> {
//            if (addedBySelection) {
//                List<TTPhieuModel> tkt_buf = ttp_ls.stream().filter(ttp -> ttp.getSo().equals(tf_search_txnt.getText())).toList();
//                ObservableList<TTPhieuModel> observableList = FXCollections.observableArrayList(tkt_buf);
//                tbTTNX.setItems(observableList);
//                addedBySelection = false;
//            }
//        });
    }

    private void setUpForSearchCompleteTion_lichsu(List<String> search_arr){
        TextFields.bindAutoCompletion(tf_search_history, t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().trim().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
        tf_search_history.setOnKeyPressed(e -> {
            addedBySelection_lstb = false;
        });

        tf_search_history.setOnKeyReleased(e -> {
            if (tf_search_history.getText().trim().isEmpty()){
                // reset
                tb_viewlichsu.setItems(FXCollections.observableArrayList(new ArrayList<>()));
                lichsuXNKS = lichsuNXKService.getAll();
                lichsuXNKS.forEach(ttp -> {
                    ttp.setTonsau_str(TextToNumber.textToNum(String.valueOf(ttp.getTonsau())));
                    ttp.setSoluong_str(TextToNumber.textToNum(String.valueOf(ttp.getSoluong())));
                    ttp.setMucgia(TextToNumber.textToNum(ttp.getMucgia()));
                    ttp.setTontruoc_str(TextToNumber.textToNum(String.valueOf(ttp.getTontruoc())));
                });
                tb_viewlichsu.setItems(FXCollections.observableArrayList(lichsuXNKS));
            }
            addedBySelection_lstb = true;
        });
    }

    private void searchingFor_lichsuTb(){
        tf_search_history.textProperty().addListener(e -> {
            if (addedBySelection_lstb) {
                List<LichsuXNK> tkt_buf = lichsuXNKS.stream().filter(ttp ->
                    ttp.getTen_xd().equals(tf_search_history.getText())).toList();
                tkt_buf.forEach(ttp -> {
                    ttp.setTonsau_str(TextToNumber.textToNum(String.valueOf(ttp.getTonsau())));
                    ttp.setSoluong_str(TextToNumber.textToNum(String.valueOf(ttp.getSoluong())));
                    ttp.setTontruoc_str(TextToNumber.textToNum(String.valueOf(ttp.getTontruoc())));
                });
                ObservableList<LichsuXNK> observableList = FXCollections.observableArrayList(tkt_buf);
                tb_viewlichsu.setItems(observableList);
                addedBySelection_lstb = false;
            }
        });
    }

    @FXML
    public void report_menu_action(MouseEvent mouseEvent) {
        try {
            HBox hBox = (HBox) getNodeBySource("reporters.fxml");
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
