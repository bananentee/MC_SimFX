module com.example.mc_simfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mc_simfx to javafx.fxml;
    exports com.example.mc_simfx;
}