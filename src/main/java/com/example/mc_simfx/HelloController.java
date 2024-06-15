package com.example.mc_simfx;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController implements Initializable {

    /* fxml attributes */
    @FXML
    private BarChart<String, Integer> barchart;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnAxe, btn_abbauen, btnPickaxe;
    @FXML
    private Label wood_display, coin_display, stone_display, iron_display;
    @FXML
    private TextField pickaxe_inputField;
    @FXML
    private ImageView wood_image, stone_image, iron_image, coin_image;

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

        wood_image.setImage(new Image("file:ass/wood_block.png"));
        stone_image.setImage(new Image("file:ass/stone_block.png"));
        iron_image.setImage(new Image("file:ass/iron_ore.png"));
        coin_image.setImage(new Image("file:ass/coin.png"));

        Spiel game = new Spiel();
        world = game.getWorld();
        player = game.getPlayer();

        game.play();
        timer.start();

        /* beginning of gui innit */
        initBarchart();

        BooleanBinding binding = new BooleanBinding() {
            {
                super.bind(progressBar.progressProperty());
            }

            @Override
            protected boolean computeValue() {
                return progressBar.progressProperty().getValue() > 0;
            }
        };
        btnPickaxe.disableProperty().bind(binding);
        btnAxe.disableProperty().bind(binding);
        btn_abbauen.disableProperty().bind(binding);
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
        timer.start();
        MiningListener listener = new MiningListener(progressBar.progressProperty());
        progressBar.progressProperty().addListener(listener);

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
        stone_display.setText(Integer.toString(player.getStein()));
        iron_display.setText(Integer.toString(player.getEisen()));
        coin_display.setText(Integer.toString(player.getCoins()));
    }

    public void increaseProgress() {
        if (progress > 1) {
            System.out.println("is full");
            progress = 0;
            progressBar.setProgress(progress);
            timer.stop();
            return;
        }
        progress += 0.005;
        progressBar.setProgress(progress);

    }

    private class MiningListener implements ChangeListener<Number> {

        private Property propertyToRemoveFrom;

        private MiningListener(Property propertyToRemoveFrom) {
            this.propertyToRemoveFrom = propertyToRemoveFrom;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue.doubleValue() >= 1) {
                player.abbauen();
                loadNewData();
                propertyToRemoveFrom.removeListener(this);
            }
        }
    }

}