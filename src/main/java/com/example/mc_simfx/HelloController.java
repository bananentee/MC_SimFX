package com.example.mc_simfx;

import javafx.animation.Animation;
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
import java.util.*;

import com.example.mc_sim_data.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController implements Initializable {

    /* FXML attributes */
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

    /* local attributes */
    private Welt world;
    private Spieler player;

    private double progress = 0;
    private Animation animation = new Animation() {
        @Override
        public void playFromStart() {

        }
        public void doPlayTo(long var1, long var3) {

        }
        public void doJumpTo(long var1, long var3, boolean var5) {

        }
    }


    // [UPDATE] runs every frame

    private final AnimationTimer timer = new AnimationTimer() {

        private long lastRun = 0;

        @Override
        public void handle(long now) {
            if (lastRun == 0) {
                lastRun = now;
                return;
            }
            // If we had 2 JFX frames for 1 screen frame, save a cycle
            if (now <= lastRun) {
                return;
            }
            // Calculate remaining time until next screen frame (next multiple of frameNs)
            long rest = now % HelloApplication.getFrameNs();
            long nextFrame = now;
            if (rest != 0) {//Fix timing to next screen frame
                nextFrame += HelloApplication.getFrameNs() - rest;
            }
            // Animate
            double elapsed = (nextFrame - lastRun) / 1e9;
            double elapsedDecimal = elapsed - (long)elapsed;
            System.out.println(elapsedDecimal);
            // Actual code
            increaseProgress(elapsedDecimal);
        }
    };

    // [START] runs only one time at the start of the programm
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* image innit */
        wood_image.setImage(new Image("file:ass/wood_block.png"));
        stone_image.setImage(new Image("file:ass/stone_block.png"));
        iron_image.setImage(new Image("file:ass/iron_ore.png"));
        coin_image.setImage(new Image("file:ass/coin.png"));

        Spiel game = new Spiel();
        world = game.getWorld();
        player = game.getPlayer();

        game.play();
        timer.start();

        /* beginning of barchart innit */
        XYChart.Series<String, Integer> worldgen = new XYChart.Series<>();

        XYChart.Data<String, Integer> wood = new XYChart.Data<>("Wood", world.getGenHolz());
        XYChart.Data<String, Integer> stone = new XYChart.Data<>("Stone", world.getGenStein());
        XYChart.Data<String, Integer> iron = new XYChart.Data<>("Iron", world.getGenEisen());

        worldgen.setName("World Generation");

        worldgen.getData().add(wood);
        worldgen.getData().add(stone);
        worldgen.getData().add(iron);

        barchart.getData().addAll(worldgen);

        // disable the function of all buttons when the delay is loading
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

    /* FXML methods */
    //TODO: Implement btnCraftAxe and Logic in @mc_sim_data
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

    /* local methods */
    public void loadNewData() {
        // updating of the barchart:
        // first index -> group of data (world generation);
        // second index -> elements of data of world generation
        barchart.getData().get(0).getData().get(0).setYValue(world.getGenHolz());
        barchart.getData().get(0).getData().get(1).setYValue(world.getGenStein());
        barchart.getData().get(0).getData().get(2).setYValue(world.getGenEisen());

        // updating of all Labels to their corresponding @mc_sim_data values
        wood_display.setText(Integer.toString(player.getHolz()));
        stone_display.setText(Integer.toString(player.getStein()));
        iron_display.setText(Integer.toString(player.getEisen()));
        coin_display.setText(Integer.toString(player.getCoins()));
    }

    public void increaseProgress(double elapsedTime) {

        if (progress > 1) {
            progress = 0;
            progressBar.setProgress(progress);
            timer.stop();
            return;
        }
        progress += 0.005 * elapsedTime;
        progressBar.setProgress(progress);
    }

    // use of a ChangeListener to implement the actual delay of the progressbar
    private class MiningListener implements ChangeListener<Number> {

        private final Property<Number> propertyToRemoveFrom;

        private MiningListener(Property<Number> propertyToRemoveFrom) {
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