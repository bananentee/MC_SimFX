package com.example.mc_simfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.mc_sim_data.*;

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

    /* local attributes */
    private XYChart.Series<String, Integer> worldgen;
    private XYChart.Data<String, Integer> wood;
    private XYChart.Data<String, Integer> stone;
    private XYChart.Data<String, Integer> iron;

    private Spiel game;
    private Welt world;
    private Spieler player;

    private double progress = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //runs only one time at the start of the programm
        initBarchart();
        game = new Spiel();
        world = game.getWorld();
        player = game.getPlayer();

        game.play();
        world.generieren();


        timer.start();

//        BooleanBinding binding = new BooleanBinding() {
//            @Override
//            protected boolean computeValue() {
//                return progressBar.progressProperty().getValue() > 0;
//            }
//        };
//        btnPickaxe.disableProperty().bind(binding);
//        btnAxe.disableProperty().bind(binding);
    }

    private final AnimationTimer timer = new AnimationTimer() { // runs every frame
        @Override
        public void handle(long l) {
            increaseProgress();
        }
    };


    @FXML
    public void btnSpCraftenClick(ActionEvent event) {
        player.crafteSpitzhacke("Holz"); // type needs to be the input
        // display changes at inventory
    }

    @FXML
    public void abbauDelay(ActionEvent event) {
        btnPickaxe.setDisable(true);
        btnAxe.setDisable(true);
        timer.start();
        player.abbauen();
        initBarchart();
        // display changes at inventory
    }

    public void initBarchart() {

        wood = new XYChart.Data<>("Wood", world.getGenHolz());
        stone = new XYChart.Data<>("Stone", world.getGenStein());
        iron = new XYChart.Data<>("Iron", world.getGenEisen());

        worldgen = new XYChart.Series<>();
        worldgen.setName("World Generation");

        worldgen.getData().add(wood);
        worldgen.getData().add(stone);
        worldgen.getData().add(iron);

        barchart.getData().addAll(worldgen);

    }

    public void loadBarchart(XYChart.Series<String, Integer> worldgen) {
        barchart.getData().clear();
        initBarchart();
//        wood.setYValue();
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

//TODO implement the real code
