package pe.edu.upeu.sistemabiblioteca.components;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.SortedSet;

public class AutoCompleteTextField<T> {

    private final TextField autoCompleteTextField;
    private final SortedSet<T> entries;
    private final ContextMenu entryMenu = new ContextMenu();
    private T lastSelectedObject;

    public AutoCompleteTextField(SortedSet<T> entries, TextField autTF) {
        this.entries = entries;
        this.autoCompleteTextField = autTF;
        autoCompleteTextField.setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyReleased(KeyEvent event) {
        showSuggestions();
    }

    private void showSuggestions() {
        String text = autoCompleteTextField.getText().toLowerCase();

        if (text.isEmpty()) {
            entryMenu.hide();
            return;
        }

        List<MenuItem> items = entries.stream()
                .filter(e -> e.toString().toLowerCase().contains(text))
                .limit(10)
                .map(e -> {
                    MenuItem item = new MenuItem(e.toString());
                    item.setOnAction(ev -> {
                        autoCompleteTextField.setText(e.toString());
                        lastSelectedObject = e;
                        entryMenu.hide();
                    });
                    return item;
                })
                .toList();

        if (items.isEmpty()) {
            entryMenu.hide();
            return;
        }

        entryMenu.getItems().setAll(items);

        Platform.runLater(() ->
                entryMenu.show(autoCompleteTextField, Side.BOTTOM, 0, 0)
        );
    }

    public T getLastSelectedObject() {
        return lastSelectedObject;
    }
}
