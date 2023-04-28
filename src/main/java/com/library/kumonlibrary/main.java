/*
Jafar Hashim
AP CS-A | Cloud Computing
April 27 2023
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;

public class main {

    public static void main(String[] args) throws SQLException, IOException {


        Application.launch(MainController.class);
        //new DatabaseManager();
    }
}