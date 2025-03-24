package com.xdf.xd_f371.fatory;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.stream.IntStream;

public class ExportFactory {
    public static void setCellBorderStyle(CellStyle style,BorderStyle b){
        style.setBorderTop(b);
        style.setBorderBottom(b);
        style.setBorderLeft(b);
        style.setBorderRight(b);
    }
    public static void setCellBorderStyle(CellStyle style,BorderStyle top,BorderStyle bot,BorderStyle lef,BorderStyle rig){
        style.setBorderTop(top);
        style.setBorderBottom(bot);
        style.setBorderLeft(lef);
        style.setBorderRight(rig);
    }
    public static void setCellAlightmentStyle(CellStyle style){
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
    }
    public static void setBoldFont(XSSFWorkbook wb, CellStyle style){
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
    }
    public static void setDataFormat(XSSFWorkbook wb,CellStyle style,String format_text){
        XSSFDataFormat format = wb.createDataFormat();
        style.setDataFormat(format.getFormat(format_text));
    }
    public static void mergerCell(XSSFSheet sheet) {
        removeMerger(sheet,8,8,8,10);
        removeMerger(sheet,8,9,7,7);
        removeMerger(sheet,8,9,6,6);
        sheet.addMergedRegion(new CellRangeAddress(8,8,8,10));
        sheet.addMergedRegion(new CellRangeAddress(8,9,7,7));
        sheet.addMergedRegion(new CellRangeAddress(8,9,6,6));
    }
    public static void removeMerger(XSSFSheet sheet, int f_row,int l_row,int f_col,int l_col){
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        int index = IntStream.range(0, mergedRegions.size())
                .filter(i -> {
                    CellRangeAddress region = mergedRegions.get(i);
                    return region.getFirstRow() == f_row &&
                            region.getFirstColumn() == f_col &&
                            region.getLastRow() == l_row &&
                            region.getLastColumn() == l_col;
                })
                .findFirst()
                .orElse(-1);
        if (index != -1) {
            sheet.removeMergedRegion(index);
        }
    }
}
