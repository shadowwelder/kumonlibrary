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

    public static void main(String[] args) throws SQLException {
        Application.launch(MainController.class);
        new DatabaseManager();
    }
}