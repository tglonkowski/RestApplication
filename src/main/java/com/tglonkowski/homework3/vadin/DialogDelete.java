package com.tglonkowski.homework3.vadin;

import com.tglonkowski.homework3.service.RestService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URISyntaxException;

public class DialogDelete extends Dialog {

    public DialogDelete() {
        TextField textField = new TextField("PUT ID to delete CAR");
        Button button = new Button("CONFIRM");
        button.addClickListener(buttonClickEvent -> initializeDeleteCar(textField));
        add(textField, button);
    }

    private void initializeDeleteCar(TextField textField) {
        if (textField.isEmpty()) {
            Notification.show("YOU HAVE TO PUT ID", 4000, Notification.Position.MIDDLE);
        } else {
            try {
                RestService restService = new RestService();
                String value = textField.getValue();
                restService.deleteCar(Long.parseLong(value));
                Notification.show("CAR HAS BEEN REMOVED", 4000, Notification.Position.MIDDLE);
                this.close();
            } catch (HttpClientErrorException | URISyntaxException e) {
                Notification.show("WRONG ID", 4000, Notification.Position.MIDDLE);
            }
        }
    }
}
