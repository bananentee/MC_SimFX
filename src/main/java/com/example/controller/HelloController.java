package com.example.controller;

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

import com.example.model.*;

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

    private int i;
    private double progress = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {//runs only one time at the start of the programm
        initBarchart();
        new Spiel().play();

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
        Spitzhacke spitzhacke = new Spitzhacke("Holz");
        wood.setYValue(wood.getYValue() - 10);

    }

    @FXML
    void abbauDelay(ActionEvent event) {
        btnPickaxe.setDisable(true);
        btnAxe.setDisable(true);
        timer.start();
    }

    public void initBarchart() {

        barchart.getData().clear();

        wood = new XYChart.Data<>("Wood", 50);
        stone = new XYChart.Data<>("Stone", 80);
        iron = new XYChart.Data<>("Iron", 20);

        worldgen = new XYChart.Series<>();
        worldgen.setName("World Generation");
        wood.setYValue(20);

        worldgen.getData().add(wood);
        worldgen.getData().add(stone);
        worldgen.getData().add(iron);
        i = 0;
        barchart.getData().clear();
        barchart.getData().addAll(worldgen);

    }

    public void loadBarchart(XYChart.Series<String, Integer> worldgen) {
        i++;
        barchart.getData().clear();
        barchart.getData().addAll(worldgen);
        //wood.setYValue(i);
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
