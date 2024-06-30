package com.example.mc_simfx;

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
    private TextField pickaxe_inputField, axe_inputField;
    @FXML
    private ImageView wood_image, stone_image, iron_image, coin_image, axe_image, pickaxe_image;

    /* local attributes */
    private Welt world;
    private Spieler player;

    private double progress = 0;
    private long frameCounter = 0;


    // [UPDATE] runs every frame
//    private final AnimationTimer timer = new AnimationTimer() {
//        @Override
//        public void handle(long l) {
//
//            frameCounter++;
//        }
//    };

    private final Timer timer = new Timer();
    private final TimerTask progress_task = new TimerTask() {
        public void run(){
            increaseProgress();
        }
    };

    private final TimerTask countdown_task = new TimerTask() {
        int i = 60;
        public void run(){
            if (i >= 0) {
                System.out.println("Timer " + i--);
            }
        }
    };


    // [START] runs only one time at the start of the program
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

        /* start of the timers */

        timer.scheduleAtFixedRate(progress_task, 0, 16);
        timer.scheduleAtFixedRate(countdown_task, 0, 1000);

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
    @FXML
    public void btnSpCraftenClick(ActionEvent event) {
        System.out.println("PICKAXE: " + pickaxe_inputField.getText());
        player.crafteSpitzhacke(pickaxe_inputField.getText());
        if (player.getPickaxe() != null) {
            switch (player.getPickaxe().getMaterial()) {
                case "Holz" -> pickaxe_image.setImage(new Image("file:ass/wooden_pickaxe.png"));
                case "Stein" -> pickaxe_image.setImage(new Image("file:ass/stone_pickaxe.png"));
                case "Eisen" -> pickaxe_image.setImage(new Image("file:ass/iron_pickaxe.png"));
            }
        } else {
            System.out.println("[SYSTEM] Keine Spitzhacke vorhanden");
        }
        pickaxe_inputField.clear();
        loadNewData();
    }

    @FXML
    public void btnAxeCraftenClick(ActionEvent event) {
        System.out.println("AXE: " + axe_inputField.getText());
        player.crafteAxt(axe_inputField.getText());
        if (player.getAxe() != null) {
            switch (player.getAxe().getMaterial()) {
                case "Holz" -> axe_image.setImage(new Image("file:ass/wooden_axe.png"));
                case "Stein" -> axe_image.setImage(new Image("file:ass/stone_axe.png"));
                case "Eisen" -> axe_image.setImage(new Image("file:ass/iron_axe.png"));
            }
        } else {
            System.out.println("[SYSTEM] Keine Axt vorhanden");
        }
        axe_inputField.clear();
        loadNewData();
    }

    @FXML
    public void abbauDelay(ActionEvent event) {
        // TODO after a random amount of clicks the world should be regenerated
//        timer.start();
        MiningListener listener = new MiningListener(progressBar.progressProperty());
        progressBar.progressProperty().addListener(listener);
        loadNewData();
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
        world.checkGeneration();
        barchart.getData().get(0).getData().get(0).setYValue(world.getGenHolz());
        barchart.getData().get(0).getData().get(1).setYValue(world.getGenStein());
        barchart.getData().get(0).getData().get(2).setYValue(world.getGenEisen());

        // updating of all Labels to their corresponding @mc_sim_data values
        wood_display.setText(Integer.toString(player.getHolz()));
        stone_display.setText(Integer.toString(player.getStein()));
        iron_display.setText(Integer.toString(player.getEisen()));
        coin_display.setText(Integer.toString(player.getCoins()));
    }

    public void increaseProgress() {
        if (progress > 1) {
            progress = 0;
            progressBar.setProgress(progress);
            countdown_task.cancel();
            return;
        }
        timer.scheduleAtFixedRate(countdown_task,0,16);
        progress += 0.005;
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