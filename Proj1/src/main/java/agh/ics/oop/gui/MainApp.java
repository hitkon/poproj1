package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Scene scene;
    private GridPane panel;
    private Button button;

    @Override
    public void init() throws Exception {
        super.init();
        panel = new GridPane();
        Platform.setImplicitExit(false);

        panel.setAlignment(Pos.CENTER);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(panel, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        TextField tmap_h = new TextField("5"), tmap_l = new TextField("5"), tplants = new TextField("6"),
                tplant_energy = new TextField("4"), tplants_growth = new TextField("2"), tanimals = new TextField("4"),
                tstart_energy = new TextField("8"), tready_energy = new TextField("4"),
                tbreed_energy = new TextField("2"), tmaxMutations = new TextField("1"), tminMutations = new TextField("0"), tgenom = new TextField("8");

        Label lmap_h = new Label("Map height");
        Label lmap_l = new Label("Map length");
        Label lplants = new Label("Initial plants number");
        Label lplant_energy = new Label("Plant energy");
        Label lplants_growth = new Label("Number of plants growths every day");
        Label lanimals = new Label("Initial number of animals");
        Label lstart_energy = new Label("Animal start energy");
        Label lready_energy = new Label("Ready animal energy amount");
        Label lbreed_energy = new Label("Amount of energy spends for an action");
        Label lminMutations = new Label("Minimum genom mutations");
        Label lmaxMutations = new Label("Maximum genom mutations");
        Label lgenom = new Label("Genom length");


        button = new Button();
        button.setText("Start");

        panel.add(button, 0,0);
        panel.add(lmap_h, 0, 1);
        panel.add(tmap_h, 0, 2);
        panel.add(lmap_l, 0, 3);
        panel.add(tmap_l, 0, 4);
        panel.add(lplants, 0, 5);
        panel.add(tplants, 0, 6);
        panel.add(lplant_energy, 0, 7);
        panel.add(tplant_energy, 0, 8);
        panel.add(lplants_growth, 0, 9);
        panel.add(tplants_growth, 0, 10);
        panel.add(lanimals, 0, 11);
        panel.add(tanimals, 0, 12);
        panel.add(lstart_energy, 0, 13);
        panel.add(tstart_energy, 0, 14);
        panel.add(lready_energy, 0, 15);
        panel.add(tready_energy, 0, 16);
        panel.add(lbreed_energy, 0, 17);
        panel.add(tbreed_energy, 0, 18);
        panel.add(lminMutations, 0, 19);
        panel.add(tminMutations, 0, 20);
        panel.add(lmaxMutations, 0, 21);
        panel.add(tmaxMutations, 0, 22);
        panel.add(lgenom, 0, 23);
        panel.add(tgenom, 0, 24);

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        int map_h = Integer.parseInt(tmap_h.getText());
                        int map_l = Integer.parseInt(tmap_l.getText());
                        int plants = Integer.parseInt(tplants.getText());
                        int plants_energy = Integer.parseInt(tplant_energy.getText());
                        int plants_growth = Integer.parseInt(tplants_growth.getText());
                        int animals = Integer.parseInt(tanimals.getText());
                        int start_energy = Integer.parseInt(tstart_energy.getText());
                        int ready_energy = Integer.parseInt(tready_energy.getText());
                        int breed_energy = Integer.parseInt(tbreed_energy.getText());
                        int minMut = Integer.parseInt(tminMutations.getText());
                        int maxMut = Integer.parseInt(tmaxMutations.getText());
                        int genom = Integer.parseInt(tgenom.getText());

                        IVariables vars = new Variables(map_h, map_l, plants, plants_energy, plants_growth, animals,
                                start_energy, ready_energy, breed_energy, minMut, maxMut, genom);
                        Label secondLabel = new Label("I'm a Label on new Window");

                        StackPane secondaryLayout = new StackPane();
                        secondaryLayout.getChildren().add(secondLabel);

                        Scene secondScene = new Scene(secondaryLayout, 230, 100);

                        // New window (Stage)
                        Stage newWindow = null;
                        try {
                            newWindow = new AppStage(vars);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
//                        new App().start(newWindow);
                        newWindow.setTitle("Second Stage");
//                        newWindow.setScene(secondScene);

                        // Specifies the modality for new window.
                        newWindow.initModality(Modality.NONE);

                        // Specifies the owner Window (parent) for new window
                        newWindow.initOwner(primaryStage);

                        // Set position of second window, related to primary window.
//                        newWindow.setX(primaryStage.getX() + 200);
//                        newWindow.setY(primaryStage.getY() + 100);

                        newWindow.show();
                    }
                });
            }
        });
    }
}
