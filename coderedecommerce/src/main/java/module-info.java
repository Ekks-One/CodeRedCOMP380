module com.codered.ecomerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    
    opens com.codered.ecomerce to javafx.fxml;
    exports com.codered.ecomerce;
}
