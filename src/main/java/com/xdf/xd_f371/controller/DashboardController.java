package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class DashboardController implements Initializable {
    public static Long so_select = 0L;
    public static Stage primaryStage;
    public static Stage xuatStage ;
    public static Stage ctStage;
    public static Quarter findByTime;
    public static List<Ledger> ledgerList = new ArrayList<>();
    private static List<MiniLedgerDto> ttp_ls = new ArrayList<>();
    public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static int screenHeigh = (int) Screen.getPrimary().getBounds().getHeight();

    private static int rowsPerPage = 9;

    @FXML
    private BorderPane borderpane_base;
    @FXML
    private VBox nx_vbox, vb_nxt_tb;
    @FXML
    private ComboBox<String> cbb_loaiphieu_filter;
    @FXML
    private ComboBox<Quarter> quy_cbb;
    @FXML
    private Label lb_from, lb_to,datetime_showing;
    @FXML
    private Pagination pagination_tbnxt;
    @FXML
    public TableView<MiniLedgerDto> tbTTNX;
    @FXML
    private TableColumn<MiniLedgerDto, String> so,ngaytao, loaiphieu, soluong,tong;
    @FXML
    private HBox dvi_menu,nxt_menu, loai_xd_menu, haohut_menu, dinhmuc_menu,tonkho_menu, nhiemvu_menu;
    @FXML
    private AnchorPane main_menu;

    @Autowired
    private QuarterService quarterService;
    @Autowired
    private LedgerService ledgerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctStage = new Stage();
        so_select=0L;
        ttp_ls = ledgerService.findInterfaceLedger();
        ledgerList = ledgerService.getAll();
        getCurrentQuarter();
        getCurrentTiming();
        resetStyleField();
        customStyleMenu();
        setDataToPhieuCombobox();
        setDataToViewTable();
        initQuyCombobox();
        tbTTNX.setPrefWidth(screenWidth);
        tbTTNX.setPrefHeight(screenHeigh);
    }

    private void initQuyCombobox(){
        quy_cbb.setItems(FXCollections.observableArrayList(quarterService.findAll()));
        quy_cbb.setConverter(new StringConverter<Quarter>() {
            @Override
            public String toString(Quarter object) {
                return object==null?"":object.getName();
            }
            @Override
            public Quarter fromString(String string) {
                return quarterService.findByName(string).orElse(null);
            }
        });
        quy_cbb.getSelectionModel().selectFirst();
    }

    private void resetStyleField() {
        String cssLayout1 =
                "-fx-border-color: #aaaaaa;\n" +
                        "-fx-background-color: #aaaaaa;\n" ;
        nxt_menu.setStyle(cssLayout1);
        dvi_menu.setStyle(resetStyle());
        loai_xd_menu.setStyle(resetStyle());
        haohut_menu.setStyle(resetStyle());
        dinhmuc_menu.setStyle(resetStyle());
        nhiemvu_menu.setStyle(resetStyle());
    }

    private void getCurrentQuarter(){
        findByTime = quarterService.findByCurrentTime(LocalDate.now()).get();
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
    private void getCurrentTiming(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                datetime_showing.setText("Ngày: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void setDataToViewTable(){
        ttp_ls =ledgerService.findInterfaceLedger();
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
        pagination_tbnxt.setPrefHeight(800);
        pagination_tbnxt.setPageCount((ttp_ls.size()/rowsPerPage) +1);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, ttp_ls.size());
        tbTTNX.setItems(FXCollections.observableArrayList(ttp_ls.subList(fromIndex, toIndex)));
        return tbTTNX;
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

    public void search_phieu_tnxt(ActionEvent actionEvent) {
    }
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
//
//    private void setUpForSearchCompleteTion_lichsu(List<String> search_arr){
//        TextFields.bindAutoCompletion(tf_search_history, t -> {
//            return search_arr.stream().filter(elem
//                    -> {
//                return elem.toLowerCase().trim().startsWith(t.getUserText().toLowerCase().trim());
//            }).collect(Collectors.toList());
//        });
//        tf_search_history.setOnKeyPressed(e -> {
//            addedBySelection_lstb = false;
//        });
//
//        tf_search_history.setOnKeyReleased(e -> {
//            if (tf_search_history.getText().trim().isEmpty()){
//                // reset
//                tb_viewlichsu.setItems(FXCollections.observableArrayList(new ArrayList<>()));
//                lichsuXNKS = lichsuNXKService.getAll();
//                lichsuXNKS.forEach(ttp -> {
//                    ttp.setTonsau_str(TextToNumber.textToNum(String.valueOf(ttp.getTonsau())));
//                    ttp.setSoluong_str(TextToNumber.textToNum(String.valueOf(ttp.getSoluong())));
//                    ttp.setMucgia(TextToNumber.textToNum(ttp.getMucgia()));
//                    ttp.setTontruoc_str(TextToNumber.textToNum(String.valueOf(ttp.getTontruoc())));
//                });
//                tb_viewlichsu.setItems(FXCollections.observableArrayList(lichsuXNKS));
//            }
//            addedBySelection_lstb = true;
//        });
//    }
//
//    private void searchingFor_lichsuTb(){
//        tf_search_history.textProperty().addListener(e -> {
//            if (addedBySelection_lstb) {
//                List<LichsuXNK> tkt_buf = lichsuXNKS.stream().filter(ttp ->
//                    ttp.getTen_xd().equals(tf_search_history.getText())).toList();
//                tkt_buf.forEach(ttp -> {
//                    ttp.setTonsau_str(TextToNumber.textToNum(String.valueOf(ttp.getTonsau())));
//                    ttp.setSoluong_str(TextToNumber.textToNum(String.valueOf(ttp.getSoluong())));
//                    ttp.setTontruoc_str(TextToNumber.textToNum(String.valueOf(ttp.getTontruoc())));
//                });
//                ObservableList<LichsuXNK> observableList = FXCollections.observableArrayList(tkt_buf);
//                tb_viewlichsu.setItems(observableList);
//                addedBySelection_lstb = false;
//            }
//        });
//    }

    @FXML
    public void report_menu_action(MouseEvent mouseEvent) {
        try {
            HBox hBox = (HBox) getNodeBySource("reporters.fxml");
            borderpane_base.setCenter(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void quy_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void loc_phieu(ActionEvent actionEvent) {
    }
    @FXML
    public void tb_selected(MouseEvent mouseEvent) {
        ctStage = new Stage();
        if (mouseEvent.getClickCount()==2){
            try {
                so_select = (long) tbTTNX.getSelectionModel().getSelectedItem().getSo();
                Common.openNewStage("chitietsc.fxml", ctStage,"CHI TIẾT");
            }catch (NullPointerException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
