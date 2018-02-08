package edu.bsu.cs222.mapGenerator;

import edu.bsu.cs222.mapGenerator.map.Map;
import javafx.application.Application;
import javafx.stage.Stage;

public class MapGeneratorApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
       Map map = new Map();
        GUI gui = new GUI(map);
        gui.start(primaryStage);
    }
}