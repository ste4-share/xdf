package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.LedgerDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class LedgerDetailController implements Initializable {
    @FXML
    private Label phieu,tungay,dengay,dvx,dvn,nv,km,gio,loainv,nguoinhan,xmt,loai_xmt,dinhmuc,so,tcnx,lenhkh,soxe;
    @FXML
    private TableView<LedgerDetails> chitietdonhang_table;
    @FXML
    private TableColumn<LedgerDetails,String> stt,tenxd,loaixd,gia,nhietdo,tytrong,vcf,px,tx,thanhtien;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void closeAction(ActionEvent actionEvent) {
    }
}
