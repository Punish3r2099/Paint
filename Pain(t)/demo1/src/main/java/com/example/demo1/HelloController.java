package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.util.Stack;

public class HelloController {
    @FXML
    private Label welcomeText;

    public Stack<Image> undoStack = new Stack<Image>();
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }



}