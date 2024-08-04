package com.example.mc_simfx;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    public Button sell_button;
    /* FXML attributes */
    @FXML
    private BarChart<String, Integer> barchart;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btn_axe, btn_pickaxe, btn_abbauen, btn_delay_upgrade, btn_worldGen_upgrade;
    @FXML
    private Label wood_display, coin_display, stone_display, iron_display, worldGen_timer_display;
    @FXML
    private TextField pickaxe_inputField, axe_inputField;
    @FXML
    private ImageView wood_image, stone_image, iron_image, coin_image, axe_image, pickaxe_image;

    /* local attributes */
    private Welt world;
    private Spieler player;

    private double progress;
    private double progressFactor;
    private int level;
    private int total_sec = 5;

    // [UPDATE] runs every frame
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            increaseProgress();
        }
    };

    private final Timer countdown_timer = new Timer();
    private final TimerTask countdown_task = new TimerTask() {
        int seconds = 60;
        int minutes = total_sec / 60;

        public synchronized void run() {
            if (total_sec < 60) {
                seconds = total_sec;
            }
            if (total_sec > 0) {
                if (total_sec % 60 == 0) {
                    minutes--;
                    seconds = 60;
                }
                if (seconds != 0) {
                    total_sec--;
                    seconds--;
                    Platform.runLater(() -> worldGen_timer_display.setText(minutes + ":" + getSeconds(seconds)));
                    Platform.runLater(() -> btn_worldGen_upgrade.setDisable(true));
                }
            } else {
                Platform.runLater(() -> worldGen_timer_display.setText("0:00"));
                total_sec = 0;
                countdown_timer.cancel(); //TODO Fix that a new TimerTask can be scheduled to the Timer (line 220)
                countdown_timer.purge();
                Platform.runLater(() -> btn_worldGen_upgrade.setDisable(false));
            }
        }

        private String getSeconds(int seconds) {
            if (seconds < 10) {
                return "0" + seconds;
            }
            return Integer.toString(seconds);
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

        /* innit of local attributes */
        Spiel game = new Spiel(200, 500);
        world = game.getWorld();
        player = game.getPlayer();
        SimpleIntegerProperty integerProperty = new SimpleIntegerProperty(total_sec);
        integerProperty.add(total_sec);

        progress = 0;
        progressFactor = 1;
        level = 0;
        player.setCoins(10000);

        btn_delay_upgrade.setText("Upgrade Cost: 200");
        btn_worldGen_upgrade.setText("Cost: 500");

        /* start of the timers */
        timer.start();
        countdown_timer.scheduleAtFixedRate(countdown_task, 0, 1000);

        /* barchart innit */
        XYChart.Series<String, Integer> worldGen = new XYChart.Series<>();

        XYChart.Data<String, Integer> wood = new XYChart.Data<>("Wood", world.getGenHolz());
        XYChart.Data<String, Integer> stone = new XYChart.Data<>("Stone", world.getGenStein());
        XYChart.Data<String, Integer> iron = new XYChart.Data<>("Iron", world.getGenEisen());

        worldGen.setName("World Generation");

        worldGen.getData().add(wood);
        worldGen.getData().add(stone);
        worldGen.getData().add(iron);
        barchart.getData().add(worldGen);

        /* bindings */
        // disable the function of all buttons when the delay is loading
        BooleanBinding progress_binding = new BooleanBinding() {
            {
                super.bind(progressBar.progressProperty());
            }

            @Override
            protected boolean computeValue() {
                return progressBar.progressProperty().getValue() > 0;
            }
        };

        btn_pickaxe.disableProperty().bind(progress_binding);
        btn_axe.disableProperty().bind(progress_binding);
        btn_abbauen.disableProperty().bind(progress_binding);
    }

    /* FXML methods */
    @FXML
    public void btnCraftPickaxe() {
        System.out.println("PICKAXE: " + pickaxe_inputField.getText());
        player.crafteSpitzhacke(pickaxe_inputField.getText());
        if (player.getPickaxe() != null) {
            switch (player.getPickaxe().getMaterial()) {
                case "Holz" -> pickaxe_image.setImage(new Image("file:ass/wooden_pickaxe.png"));
                case "Stein" -> pickaxe_image.setImage(new Image("file:ass/stone_pickaxe.png"));
                case "Eisen" -> pickaxe_image.setImage(new Image("file:ass/iron_pickaxe.png"));
            }
        } else System.out.println("[SYSTEM] Keine Spitzhacke vorhanden");
        pickaxe_inputField.clear();
        loadNewData();
    }

    @FXML
    public void btnCraftAxe() {
        System.out.println("AXE: " + axe_inputField.getText());
        player.crafteAxt(axe_inputField.getText());
        if (player.getAxe() != null) {
            switch (player.getAxe().getMaterial()) {
                case "Holz" -> axe_image.setImage(new Image("file:ass/wooden_axe.png"));
                case "Stein" -> axe_image.setImage(new Image("file:ass/stone_axe.png"));
                case "Eisen" -> axe_image.setImage(new Image("file:ass/iron_axe.png"));
            }
        } else System.out.println("[SYSTEM] Keine Axt vorhanden");
        axe_inputField.clear();
        loadNewData();
    }

    @FXML
    public void abbauDelay() {
        timer.start();
        MiningListener listener = new MiningListener(progressBar.progressProperty());
        progressBar.progressProperty().addListener(listener);
        loadNewData();
    }

    @FXML
    public void btnSellAll() {
        player.verkaufe("Alles", -1);
        loadNewData();
    }

    @FXML
    public void btnUpgradeDelay() {
        if (player.kaufe(200 + 200 * (level * level))) {
            level++;
            progressFactor = progressFactor + level;
            btn_delay_upgrade.setText("Upgrade Cost: " + (200 + 200 * (level * level)));
            btn_worldGen_upgrade.setText("Cost: " + (500 + 500 * level));
        } else {
            System.out.println("[SYSTEM] Nicht genug Coins!");
        }
        loadNewData();
    }

    @FXML
    public void btnUpgradeWorldGen() {
        if (player.kaufe(500 + 500 * level)) {
            world.generieren(200 + (200 * level) / 2, 500 + (500 * level) / 2);
            Platform.runLater(() -> countdown_timer.scheduleAtFixedRate(countdown_task, 0, 1000));
        } else {
            System.out.println("[SYSTEM] Nicht genug Coins!");
        }
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
            timer.stop();
            return;
        }
        progress += 0.005 * progressFactor;
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