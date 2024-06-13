package com.example.mc_simfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.mc_sim_data.*;
import javafx.scene.control.TextField;

public class HelloController implements Initializable {

    /* fxml attributes */
    @FXML
    private BarChart<String, Integer> barchart;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnPickaxe;
    @FXML
    private Button btnAxe;
    @FXML
    private Button sell_button;
    @FXML
    private Label wood_display;
    @FXML
    private Label coin_display;
    @FXML
    private TextField pickaxe_inputField;

    private Welt world;
    private Spieler player;

    private double progress = 0;

    private final AnimationTimer timer = new AnimationTimer() { // runs every frame
        @Override
        public void handle(long l) {
            increaseProgress();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //runs only one time at the start of the programm
        Spiel game = new Spiel();
        world = game.getWorld();
        player = game.getPlayer();

        game.play();
        timer.start();

        /* beginning of gui innit */
        initBarchart();

//        BooleanBinding binding = new BooleanBinding() {
//            @Override
//            protected boolean computeValue() {
//                return progressBar.progressProperty().getValue() > 0;
//            }
//        };
//        btnPickaxe.disableProperty().bind(binding);
//        btnAxe.disableProperty().bind(binding);
    }


    @FXML
    public void btnSpCraftenClick(ActionEvent event) {
        System.out.println(pickaxe_inputField.getText());
        player.crafteSpitzhacke(pickaxe_inputField.getText());
        pickaxe_inputField.clear();
        loadNewData();

    }

    @FXML
    public void abbauDelay(ActionEvent event) {
        btnPickaxe.setDisable(true);
        btnAxe.setDisable(true);
        timer.start();
        player.abbauen();
        loadNewData();
    }

    @FXML
    public void btnSellAll(ActionEvent event) {
        player.verkaufe("Alles", -1);
        loadNewData();
    }

    public void initBarchart() {
        XYChart.Series<String, Integer> worldgen = new XYChart.Series<>();

        XYChart.Data<String, Integer> wood = new XYChart.Data<>("Wood", world.getGenHolz());
        XYChart.Data<String, Integer> stone = new XYChart.Data<>("Stone", world.getGenStein());
        XYChart.Data<String, Integer> iron = new XYChart.Data<>("Iron", world.getGenEisen());

        worldgen.setName("World Generation");

        worldgen.getData().add(wood);
        worldgen.getData().add(stone);
        worldgen.getData().add(iron);

        barchart.getData().addAll(worldgen);
    }

    public void loadNewData() {
        barchart.getData().clear();
        initBarchart();
        wood_display.setText(Integer.toString(player.getHolz()));
        coin_display.setText(Integer.toString(player.getCoins()));
    }

    public void increaseProgress() {
        if (progress > 1) {
            System.out.println("is full");
            progress = 0;
            progressBar.setProgress(progress);
            timer.stop();
            btnPickaxe.setDisable(false);
            btnAxe.setDisable(false);
        }
        progress += 0.005;
        progressBar.setProgress(progress);
    }
}

//TODO fix the function of the Pickaxe