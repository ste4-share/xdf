package com.xdf.xd_f371.util;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.List;

public class ComboboxUtil {
    public static <T> void setItemsToComboBox(ComboBox<T> comboBox, List<T> items, StringConverter<T> converter) {
        comboBox.setItems(FXCollections.observableArrayList(items));
        comboBox.setConverter(converter);
        comboBox.getSelectionModel().selectFirst();
    }
}
