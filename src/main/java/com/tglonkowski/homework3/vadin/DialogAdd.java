package com.tglonkowski.homework3.vadin;

import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.model.Color;
import com.tglonkowski.homework3.service.RestService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;
import java.util.EnumSet;

public class DialogAdd extends Dialog {

    private TextField iD;
    private TextField mark;
    private TextField model;
    private ComboBox<Color> colorComboBox;
    private Button addButton;
    private Button modifyButton;
    private RestService restService;

    public DialogAdd() {
        restService = new RestService();
        initializeMainView();
        initializeListeners();
    }

    private void initializeMainView() {
        this.setWidth("950px");
        this.setHeight("200px");
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout dataLayout = new HorizontalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        iD = new TextField("ID");
        mark = new TextField("MARK");
        model = new TextField("MODEL");
        colorComboBox = new ComboBox<>("COLOR");
        colorComboBox.setItems(EnumSet.allOf(Color.class));
        addButton = new Button("Add Car");
        modifyButton = new Button("Modify Car");
        dataLayout.addAndExpand(iD, mark, model, colorComboBox);
        buttons.addAndExpand(addButton, modifyButton);
        verticalLayout.add(dataLayout, buttons);
        add(verticalLayout);
    }

    private void initializeListeners() {
        addButtonListener();
        modifyButtonListener();
    }

    private void modifyButtonListener() {
        modifyButton.addClickListener(buttonClickEvent -> {
            if (isAllFieldsFill()) {
                getValueAndModifyCar();
                this.close();
            } else
                getNotification("You have to put all fields", 3000);
        });
    }

    private void addButtonListener() {
        addButton.addClickListener(buttonClickEvent -> {
            if (isAllFieldsFill()) {
                getValueAndSaveCar();
                this.close();
            } else
                getNotification("You have to put all fields", 3000);
        });
    }

    private void getValueAndModifyCar() {
        String iDValue = iD.getValue();
        String markValue = mark.getValue();
        String modelValue = model.getValue();
        Color comboBoxValue = colorComboBox.getValue();
        modifyCar(iDValue, markValue, modelValue, comboBoxValue);
    }

    private void getValueAndSaveCar() {
        String iDValue = iD.getValue();
        String markValue = mark.getValue();
        String modelValue = model.getValue();
        Color comboBoxValue = colorComboBox.getValue();
        addCar(iDValue, markValue, modelValue, comboBoxValue);
    }


    private boolean isAllFieldsFill() {
        return !iD.isEmpty() & !mark.isEmpty() & !model.isEmpty() & !colorComboBox.isEmpty();
    }

    private void addCar(String iDValue, String markValue, String modelValue, Color color) {
        try {
            saveCar(iDValue, markValue, modelValue, color);
            getNotification("Car has been added", 4000);
        } catch (Exception e) {
            getNotification("Car has not added\n" + e.getMessage(), 4000);
            e.printStackTrace();
        }
    }

    private void saveCar(String iDValue, String markValue, String modelValue, Color color) throws URISyntaxException {
        restService.addCar(Car.builder()
                .id(Long.parseLong(iDValue))
                .mark(markValue)
                .model(modelValue)
                .color(color)
                .build());
    }

    private void modifyCar(String idValue, String markValue, String modelValue, Color color) {
        try {
            updateCar(idValue, markValue, modelValue, color);
            getNotification("Car has been modified", 4000);
        } catch (Exception e) {
            getNotification("Car has not modified\n" + e.getMessage(), 4000);
            e.printStackTrace();
        }
    }

    private void updateCar(String idValue, String markValue, String modelValue, Color color) {
        restService.updateCar(Car.builder()
                .id(Long.parseLong(idValue))
                .mark(markValue)
                .model(modelValue)
                .color(color)
                .build());
    }

    private void getNotification(String message, int time) {
        Notification.show(message, time, Notification.Position.MIDDLE);
    }
}
