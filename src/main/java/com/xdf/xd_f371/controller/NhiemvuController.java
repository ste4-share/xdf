package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.AssignTypeEnum;
import com.xdf.xd_f371.model.LoaiGB;
import com.xdf.xd_f371.model.LoaiPTEnum;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
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
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
@Component
public class NhiemvuController implements Initializable {

    private NhiemVuService nhiemVuService = new NhiemVuImp();
    private PhuongTienService phuongTienService = new PhuongTienImp();
    private NguonNXService nguonNXService = new NguonNXImp();
    private QuarterService quarterService = new QuarterImp();
    private CategoryService categoryService = new CategoryImp();
    private LedgerDetailsService ledgerDetailsService = new LedgerDetailsImp();
    private static int quarterid_selected = 0;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;

    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNvTb();
        System.out.println("asdfdg: "+hanmucNhiemvuService.getAll().size());
    }

    private void initNvTb() {
        tennv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_nv"));
        ctnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("chitiet"));
        lnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_loai_nv"));
    }

    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            System.out.println("nhiemvu: " + nv_tb.getSelectionModel().getSelectedItem().getTen_nv());
        }
    }

    @FXML
    public void bcNlbayTheoKh(ActionEvent actionEvent) {
        initNhiemVu();
    }

    private void initNhiemVu() {
        int quarterId = DashboardController.findByTime.getId();
        for (int i =0;i < nguonNXService.findNguonnxTructhuocF().size(); i++){
            NguonNx nguonNx = nguonNXService.findNguonnxTructhuocF().get(i);
            for (int j=0; j< nhiemVuService.getAllCtnvByType(1).size(); j++){
                List<AssignmentCategory> assignmentCategories = categoryService.getAllAssCategory();
                ChiTietNhiemVuDTO ctnvDto = nhiemVuService.getAllCtnvByType(1).get(j);
                String ct_tk = "20:11:00";
                String ct_md = "01:12:00";
                int consumpt =9000;
//                Duration duration = Duration.ofHours(8);
//                try {
//                    nhiemVuService.createHanmucNhiemVu(new HanmucNhiemvu(0, quarterId, nguonNx.getId(),ctnvDto.getCtnv_id(), ,new PGInterval(ct_md),consumpt));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                HanmucNhiemvu hanmucNhiemvu = nhiemVuService.getHanmucNhiemvu(nguonNx.getId(), ctnvDto.getCtnv_id(), quarterId);
//                GioBay gioBay_md = ledgerDetailsService.getSumofWorkingTime(ctnvDto.getCtnv_id(), quarterId, LoaiGB.MD.getName());
//                GioBay gioBay_tk = ledgerDetailsService.getSumofWorkingTime(ctnvDto.getCtnv_id(), quarterId, LoaiGB.TK.getName());
////                int haohut = ledgerDetailsService.getHHByNV(ctnvDto.getCtnv_id(), quarterId);
//                Map<String, Integer> map = ledgerDetailsService.getSumofconsumption_nvuId(ctnvDto.getCtnv_id(), quarterId);
//                for (int k =0; k< assignmentCategories.size(); k++){
//                    AssignmentCategory assignmentCategory = assignmentCategories.get(k);
//
//                    NhiemVuReport nhiemVuReport = new NhiemVuReport();
//                    nhiemVuReport.setTitle_1(assignmentCategory.getTitle1());
//                    nhiemVuReport.setTitle_2(assignmentCategory.getTitle2());
//                    nhiemVuReport.setTitle_3(assignmentCategory.getTitle3());
//                    nhiemVuReport.setTitle_4(assignmentCategory.getTitle4());
//                    nhiemVuReport.setTen_nv_1(ctnvDto.getNhiemvu());
//                    nhiemVuReport.setTen_nv_2(ctnvDto.getChiTietNhiemVu());
//                    nhiemVuReport.setTen_nv_3(nguonNx.getTen());
//                    if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_TK.getName())){
//                        nhiemVuReport.setSoluong(hanmucNhiemvu.getCt_tk());
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_MD.getName())) {
//                        nhiemVuReport.setSoluong(hanmucNhiemvu.getCt_md());
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_SUM.getName())) {
//                        int gb_tk_h = getTimeFromString(hanmucNhiemvu.getCt_tk()).get(1);
//                        int gb_tk_m = getTimeFromString(hanmucNhiemvu.getCt_tk()).get(2);
//
//                        int gb_md_h = getTimeFromString(hanmucNhiemvu.getCt_md()).get(1);
//                        int gb_md_m = getTimeFromString(hanmucNhiemvu.getCt_md()).get(2);
//
//                        int sum_m = convertM_H(gb_tk_m+ gb_md_m).get(1);
//                        int sum_h = gb_tk_h + gb_md_h + convertM_H(gb_tk_m+ gb_md_m).get(0);
//                        nhiemVuReport.setSoluong(formatFlightHours(sum_h, sum_m));
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.NL.getName())) {
//                        nhiemVuReport.setSoluong(String.valueOf(hanmucNhiemvu.getConsumpt()));
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_TK.getName())) {
//                        nhiemVuReport.setSoluong("0");
////                        nhiemVuReport.setSoluong(formatFlightHours(gioBay_tk.getFlight_hours(), gioBay_tk.getFlight_minutes()));
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_MD.getName())) {
//                        nhiemVuReport.setSoluong("0");
////                        nhiemVuReport.setSoluong(formatFlightHours(gioBay_md.getFlight_hours(), gioBay_md.getFlight_minutes()));
//                        nhiemVuService.create(nhiemVuReport);
//                    } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_SUM.getName())) {
//                        int gb_tk_h = gioBay_tk.getFlight_hours();
//                        int gb_tk_m = gioBay_tk.getFlight_minutes();
//
//                        int gb_md_h = gioBay_md.getFlight_hours();
//                        int gb_md_m = gioBay_md.getFlight_minutes();
//
//                        int sum_m = convertM_H(gb_tk_m+ gb_md_m).get(1);
//                        int sum_h = gb_tk_h + gb_md_h + convertM_H(gb_tk_m+ gb_md_m).get(0);
//
//                        nhiemVuReport.setSoluong(formatFlightHours(sum_h, sum_m));
//                        nhiemVuService.create(nhiemVuReport);
//                    }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_TK.getName())) {
//                        nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.TK.getName())));
//                        nhiemVuService.create(nhiemVuReport);
//                    }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_MD.getName())) {
//                        nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.MD.getName())));
//                        nhiemVuService.create(nhiemVuReport);
//                    }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_SUM.getName())) {
//                        nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.MD.getName()) + map.get(LoaiGB.TK.getName())));
//                        nhiemVuService.create(nhiemVuReport);
//                    }
////                    else if (assignmentCategory.getCode().equals(AssignTypeEnum.HH.getName())) {
//////                        nhiemVuReport.setSoluong(String.valueOf();
////                        nhiemVuService.create(nhiemVuReport);
////                    }
//                }
            }
        }
    }

    private void initNLBayTheoPT() {
        int quarterId = DashboardController.findByTime.getId();
        List<NormDto> phuongTienList = phuongTienService.getAllPt(LoaiPTEnum.MAYBAY_a.getNameVehicle());
        for (int i =0; i< phuongTienList.size(); i++){
            NormDto phuongTien = phuongTienList.get(i);
            List<AssignmentCategory> assignmentCategories = categoryService.getAllAssCategory();
            GioBay gioBay_md = ledgerDetailsService.getSumofWorkingTime(phuongTien.getPt_id(), quarterId, LoaiGB.MD.getName());
            GioBay gioBay_tk = ledgerDetailsService.getSumofWorkingTime(phuongTien.getPt_id(), quarterId, LoaiGB.TK.getName());
            Map<String, Integer> map = ledgerDetailsService.getSumofconsumption(phuongTien.getPt_id(), quarterId);
            ChitieuDTO chitieuDTO = phuongTienService.getChitieuDtoById(phuongTien.getPt_id(),quarterId );
            for (int j =0; j< assignmentCategories.size(); j++){
                AssignmentCategory assignmentCategory = assignmentCategories.get(j);

                NhiemVuReport nhiemVuReport = new NhiemVuReport();
                nhiemVuReport.setTitle_1(assignmentCategory.getTitle1());
                nhiemVuReport.setTitle_2(assignmentCategory.getTitle2());
                nhiemVuReport.setTitle_3(assignmentCategory.getTitle3());
                nhiemVuReport.setTitle_4(assignmentCategory.getTitle4());
                nhiemVuReport.setTen_nv_1(phuongTien.getNamePt());
                if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_TK.getName())){
                    nhiemVuReport.setSoluong(chitieuDTO.getHanmuc_tk());
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_MD.getName())) {
                    nhiemVuReport.setSoluong(chitieuDTO.getHanmuc_md());
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.GB_SUM.getName())) {
                    int gb_tk_h = getTimeFromString(chitieuDTO.getHanmuc_tk()).get(1);
                    int gb_tk_m = getTimeFromString(chitieuDTO.getHanmuc_tk()).get(2);

                    int gb_md_h = getTimeFromString(chitieuDTO.getHanmuc_md()).get(1);
                    int gb_md_m = getTimeFromString(chitieuDTO.getHanmuc_md()).get(2);

                    int sum_m = convertM_H(gb_tk_m+ gb_md_m).get(1);
                    int sum_h = gb_tk_h + gb_md_h + convertM_H(gb_tk_m+ gb_md_m).get(0);
                    nhiemVuReport.setSoluong(formatFlightHours(sum_h, sum_m));
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.NL.getName())) {
                    nhiemVuReport.setSoluong(String.valueOf(chitieuDTO.getSoluong()));
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_TK.getName())) {
                    nhiemVuReport.setSoluong(formatFlightHours(gioBay_tk.getFlight_hours(), gioBay_tk.getFlight_minutes()));
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_MD.getName())) {
                    nhiemVuReport.setSoluong(formatFlightHours(gioBay_md.getFlight_hours(), gioBay_md.getFlight_minutes()));
                    nhiemVuService.create(nhiemVuReport);
                } else if (assignmentCategory.getCode().equals(AssignTypeEnum.HD_SUM.getName())) {
                    int gb_tk_h = gioBay_tk.getFlight_hours();
                    int gb_tk_m = gioBay_tk.getFlight_minutes();

                    int gb_md_h = gioBay_md.getFlight_hours();
                    int gb_md_m = gioBay_md.getFlight_minutes();

                    int sum_m = convertM_H(gb_tk_m+ gb_md_m).get(1);
                    int sum_h = gb_tk_h + gb_md_h + convertM_H(gb_tk_m+ gb_md_m).get(0);

                    nhiemVuReport.setSoluong(formatFlightHours(sum_h, sum_m));
                    nhiemVuService.create(nhiemVuReport);
                }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_TK.getName())) {
                    nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.TK.getName())));
                    nhiemVuService.create(nhiemVuReport);
                }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_MD.getName())) {
                    nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.MD.getName())));
                    nhiemVuService.create(nhiemVuReport);
                }else if (assignmentCategory.getCode().equals(AssignTypeEnum.NLTT_SUM.getName())) {
                    nhiemVuReport.setSoluong(String.valueOf(map.get(LoaiGB.MD.getName()) + map.get(LoaiGB.TK.getName())));
                    nhiemVuService.create(nhiemVuReport);
                }
            }
        }
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

    private void initNLBayTheoKH() {
        List<NhiemVu> nhiemVuList = nhiemVuService.getAll();
        List<PhuongTien> phuongTienList = phuongTienService.getAll();

    }
}
