module com.example.mc_simfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.mc_simfx to javafx.fxml;
    exports com.example.mc_simfx;
}