package com.xdf.xd_f371.fatory;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
}
