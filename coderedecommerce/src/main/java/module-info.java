module com.codered.ecomerce {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.codered.ecomerce to javafx.fxml;
    exports com.codered.ecomerce;
}
