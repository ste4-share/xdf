package com.xdf.xd_f371.util;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class ComponentUtil {
    public static <T> void setItemsToComboBox(ComboBox<T> comboBox, List<T> items, Function<T,String> toStr, Function<String,T> fr_str) {
        comboBox.setItems(FXCollections.observableArrayList(items));
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return object == null ? "" : toStr.apply(object);
            }

            @Override
            public T fromString(String string) {
                return fr_str.apply(string);
            }
        });
    }
    public static DatePicker createDatePicker(DatePicker datePicker, LocalDate initialDate, String dateFormat) {

        // Set the initial date, if provided
        if (initialDate != null) {
            datePicker.setValue(initialDate);
        }
        // Format the display of the date
        if (dateFormat != null && !dateFormat.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            datePicker.setConverter(new javafx.util.StringConverter<>() {
                @Override
                public String toString(LocalDate date) {
                    return date != null ? date.format(formatter) : "";
                }

                @Override
                public LocalDate fromString(String string) {
                    return string != null && !string.isEmpty()
                            ? LocalDate.parse(string, formatter)
                            : null;
                }
            });
        }

        return datePicker;
    }
}
