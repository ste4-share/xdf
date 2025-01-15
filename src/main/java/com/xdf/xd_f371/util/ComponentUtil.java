package com.xdf.xd_f371.util;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

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
}
