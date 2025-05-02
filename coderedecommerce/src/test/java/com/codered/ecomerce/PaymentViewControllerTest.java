package com.codered.ecomerce;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codered.ecomerce.PaymentViewController;

public class PaymentViewControllerTest {

    private PaymentViewController controller;

    private TextField cardNumTextBox;
    private TextField securityNumTextBox;
    private TextField cardHolderTextBox;
    private ChoiceBox<String> cardTypeChoiceBox;
    private ChoiceBox<String> cardDateMonthChoiceBox;
    private ChoiceBox<String> cardDateYearChoiceBox;
    private TextField zipCodeTextBox;

    @BeforeAll
    static void initToolkit() throws Exception {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already initialized
        }
    }

    @BeforeEach
    void setUp() {
        controller = new PaymentViewController();

        // Use real JavaFX components
        cardNumTextBox = new TextField("4111111111111111");
        securityNumTextBox = new TextField("123");
        cardHolderTextBox = new TextField("John Doe");
        cardTypeChoiceBox = new ChoiceBox<>();
        cardDateMonthChoiceBox = new ChoiceBox<>();
        cardDateYearChoiceBox = new ChoiceBox<>();
        zipCodeTextBox = new TextField("12345");

        // Populate choices
        cardTypeChoiceBox.getItems().addAll("Visa", "MasterCard");
        cardTypeChoiceBox.setValue("Visa");

        cardDateMonthChoiceBox.getItems().addAll("01", "02", "12");
        cardDateMonthChoiceBox.setValue("12");

        cardDateYearChoiceBox.getItems().addAll("2024", "2025", "2026");
        cardDateYearChoiceBox.setValue("2025");

        // Inject components into the controller
        controller.setCardNumTextBox(cardNumTextBox);
        controller.setSecurityNumTextBox(securityNumTextBox);
        controller.setCardHolderTextBox(cardHolderTextBox);
        controller.setCardTypeChoiceBox(cardTypeChoiceBox);
        controller.setCardDateMonthChoiceBox(cardDateMonthChoiceBox);
        controller.setCardDateYearChoiceBox(cardDateYearChoiceBox);
        controller.setZipCodeTextBox(zipCodeTextBox);
    }

    @Test
    void testConfirmPayment() {
        try {
            controller.confirmPayment(null);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "confirmPayment threw an exception";
        }
    }//end testConfirmPayment
    
}//end class PaymentViewControllerTest