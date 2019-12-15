package com.tglonkowski.homework3.vadin;


import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.service.RestService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@Route
public class MainView extends VerticalLayout {

    private RestService restService;
    private Grid<Car> grid;
    private Button addModifyButton;
    private Button deleteButton;
    private TextField textFieldID;
    private TextField textFieldColor;
    private DialogDelete dialogDelete;
    private HorizontalLayout crudLayout;
    private HorizontalLayout verticalLayout;


    public MainView() {
        H1 header = new H1("Rest Api Cars");
        restService = new RestService();
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        initializeLayouts();
        add(header, grid, crudLayout, verticalLayout);
    }

    private void initializeLayouts() {
        initializeGridLayout();
        initializeCrudLayout();
        initializeSearchLayout();
    }

    private void initializeSearchLayout() {
        verticalLayout = new HorizontalLayout();
        H2 h2 = new H2("PRESS ENTER TO FIND CAR:");
        initializeTextFields();
        verticalLayout.addAndExpand(h2, textFieldID, textFieldColor);
    }

    private void initializeTextFields() {
        textFieldID = new TextField("ID");
        textFieldColor = new TextField("COLOR");
        addListeners();
    }

    private void addListeners() {
        addListenerToTextField(textFieldID);
        addListenerToTextField(textFieldColor);
    }

    private void addListenerToTextField(TextField textField) {
        textField.addKeyPressListener(Key.ENTER, keyPressEvent -> {
            if (textField.isEmpty()) {
                getNotification("YOU HAVE TO FILL ONE OF THIS FIELDS!");
            } else if (textField.equals(textFieldID)) {
                getCarById();
            } else {
                getCarByColor();
            }
        });
    }

    private void getCarByColor() {
        try {
            List<Car> car = restService.getCar(textFieldColor.getValue());
            grid.setItems(car);
        } catch (HttpClientErrorException | NullPointerException e) {
            getNotification("WRONG COLOR");
        }
    }

    private void getCarById() {
        try {
            Car car = restService.getCar(Long.parseLong(textFieldID.getValue()));
            grid.setItems(car);
        } catch (HttpClientErrorException e) {
            getNotification("WRONG ID");
        }
    }

    private void getNotification(String message) {
        Notification.show(message, 4000, Notification.Position.MIDDLE);
        grid.setItems(restService.getAllCars());
    }

    private void initializeCrudLayout() {
        crudLayout = new HorizontalLayout();
        initializeModifyButtonAndDialog();
        initializeDeleteButtonAndDialog();
        crudLayout.addAndExpand(addModifyButton, deleteButton);
    }

    private void initializeModifyButtonAndDialog() {
        addModifyButton = new Button("Add / Modify Car");
        DialogAdd dialogAdd = new DialogAdd();
        addModifyButton.addClickListener(buttonClickEvent -> dialogAdd.open());
        dialogAdd.addDialogCloseActionListener(dialogCloseActionEvent -> grid.setItems(restService.getAllCars()));
        dialogAdd.addDetachListener(detachEvent -> refreshGrid());
    }

    private void initializeDeleteButtonAndDialog() {
        deleteButton = new Button("Delete Car");
        dialogDelete = new DialogDelete();
        dialogDelete.addDetachListener(detachEvent -> refreshGrid());
        deleteButton.addClickListener(buttonClickEvent -> dialogDelete.open());
    }

    private void refreshGrid() {
        grid.setItems(restService.getAllCars());
    }


    private void initializeGridLayout() {
        grid = new Grid<>();
        grid.setItems(restService.getAllCars());
        grid.addColumn(Car::getId).setHeader("ID").setSortable(true);
        grid.addColumn(Car::getMark).setHeader("MARK").setSortable(true);
        grid.addColumn(Car::getModel).setHeader("MODEL").setSortable(true);
        grid.addColumn(Car::getColor).setHeader("COLOR").setSortable(true);
    }
}
