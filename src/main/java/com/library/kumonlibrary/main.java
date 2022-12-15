/*
Jafar Hashim
CS50 AP
May 18 2022
 */

package com.library.kumonlibrary;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

public class main {

    final StringBuffer barcode = new StringBuffer();
    long lastEventTimeStamp = 0L;
    long threshold = 100;
    final int minBarcodeLength = 8;


//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("main-view.fxml"));
//        Parent root = fxmlLoader.load();
//        MainController mainController = new MainController();
//        Scene scene = new Scene(root);
//        stage.getIcons().add(new Image("file:fish.png"));
//        stage.setTitle("Library");
//        stage.setScene(scene);
//        stage.show();

//        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
//            mainController.initialize(event);
//        });


    //}

    public static void main(String[] args) throws SQLException {
        Application.launch(MainController.class);
        new DatabaseManager();


    }
}