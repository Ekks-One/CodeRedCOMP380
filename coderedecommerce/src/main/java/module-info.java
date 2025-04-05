module com.codered.ecomerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.codered.ecomerce to javafx.fxml;
    exports com.codered.ecomerce;
}
