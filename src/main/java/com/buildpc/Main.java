package com.buildpc;

import com.buildpc.controllers.SceneController;
import com.buildpc.models.Build;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Build build = new Build();

    @Override
    public void start(Stage stage) throws IOException {
        SceneController sc = new SceneController();
        sc.loadScreen("root", "root.fxml");
        sc.loadScreen("summary", "summary.fxml");
        sc.setScreen("root");

        Group main = new Group();
        main.getChildren().addAll(sc);
        Scene scene = new Scene(main);
        stage.setTitle("Build PC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}