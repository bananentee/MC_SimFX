package com.example.mc_simfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {

    private static long frameNs;

    public static long getFrameNs() {
        return frameNs;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Minecraft Simulator");
        stage.setScene(scene);
        Image icon = new Image("file:ass/minecraft_logo.png");
        stage.getIcons().add(icon);


        stage.setOnShown(e -> {
            Screen screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()).get(0);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice d = ge.getScreenDevices()[Screen.getScreens().indexOf(screen)];

            int r = d.getDisplayMode().getRefreshRate();
            System.out.println("Screen refresh rate : " + r);
            //Calculate frame duration in nanoseconds
            frameNs = 1_000_000_000L / r; //Store it as it better suits you
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


