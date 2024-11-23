package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.QuarterRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
@Component
public class NhiemvuController implements Initializable {

    @Autowired
    private QuarterRepository quarterRepository;
    @Autowired
    private ChitietNhiemvuRepo chitietNhiemvuRepo;

    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv, khoi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initQuarterCbb();
        initNvTb();
    }

    private void initNvTb() {
        nv_tb.setItems(FXCollections.observableList(chitietNhiemvuRepo.findAllDtoBy()));
        tennv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_nv"));
        ctnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("chitiet"));
        lnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_loai_nv"));
        khoi.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("khoi"));
    }

    private void initQuarterCbb(){
        quy_cbb.setItems(FXCollections.observableList(quarterRepository.findAll()));
        quy_cbb.setConverter(new StringConverter<Quarter>() {
            @Override
            public String toString(Quarter object) {
                return object==null ?"": object.getName();
            }

            @Override
            public Quarter fromString(String string) {
                return quarterRepository.findByName(quy_cbb.getValue().getName()).isPresent()?quy_cbb.getValue():null;
            }
        });
    }

    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            System.out.println("nhiemvu: " + nv_tb.getSelectionModel().getSelectedItem().getTen_nv());
        }
    }

    @FXML
    public void bcNlbayTheoKh(ActionEvent actionEvent) {
    }

    private String formatFlightHours(int hours, int minutes){
        if (minutes >= 60){
            int pre_hour = minutes/60;
            int remainder = minutes%60;
            hours = hours+pre_hour;
            return String.valueOf(hours+":"+ remainder);
        }
        if (minutes >=0 && minutes<60){
            return String.valueOf(hours+":"+minutes);
        }
        return null;
    }
    private List<Integer> convertM_H(int minutes){
        if (minutes >= 60){
            int pre_hour = minutes/60;
            int remainder = minutes%60;
            return List.of(pre_hour, remainder);
        }
        if (minutes >=0 && minutes<60){
            return List.of(0, minutes);
        }
        return List.of();
    }
    private Map<Integer, Integer> getTimeFromString(String time){
        Map<Integer, Integer> map = new HashMap<>();
        try {
            int hours = Integer.parseInt(time.substring(0, time.indexOf(":")));
            int minutes = Integer.parseInt(time.substring(time.indexOf(":")+1));
            map.put(1, hours);
            map.put(2, minutes);
        }catch (NumberFormatException e){
            throw new RuntimeException(e);
        }
        return map;
    }
}
