package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.repo.QuarterRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

@Component
public class QuyController implements Initializable {

    @Autowired
    QuarterRepository quarterRepository;

    @FXML
    private TextArea tarea_note;
    @FXML
    private TextField tf_quarterName;
    @FXML
    private DatePicker dp_startdate, dp_enddate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void createQuarter(ActionEvent actionEvent) {
        Quarter quarter = new Quarter();
        quarter.setName(tf_quarterName.getText());
        quarter.setConvey(tarea_note.getText());
        quarter.setStart_date(dp_startdate.getValue());
        quarter.setEnd_date(dp_enddate.getValue());
        int year = Year.now().getValue();
        quarter.setYear(String.valueOf(year));
        quarterRepository.save(quarter);
    }

    @FXML
    public void cancel_createNewQuarter(ActionEvent actionEvent) {
        TonkhoController.quarter_stage.close();
    }
}
