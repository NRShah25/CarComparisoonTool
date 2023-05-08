module list.carcomparisontool {
    
    requires javafx.controls;
    requires com.google.gson;
    requires java.sql;
    requires javafx.fxml;

    opens list.carcomparisontool to javafx.fxml,com.google.gson;
    exports list.carcomparisontool;
}
