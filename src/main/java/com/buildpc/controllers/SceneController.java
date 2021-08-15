package com.buildpc.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

public class SceneController extends StackPane {
    private HashMap<String, Node> screens = new HashMap<>();

    public SceneController() {
        super();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/buildpc/" +resource));
            Parent loadScreen = (Parent) loader.load();
            ControlledScreen sceneController = ((ControlledScreen) loader.getController());
            sceneController.setScreenParent(this);

            addScreen(name, loadScreen);

            return true;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
            } else {
                getChildren().add(0, screens.get(name));
            }
            return true;
        } else {
            return false;
        }
    }
}
