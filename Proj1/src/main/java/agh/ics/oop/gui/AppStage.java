package agh.ics.oop.gui;

//import agh.ics.oop.*;

import agh.ics.oop.*;
import java.time.LocalDateTime;
import agh.ics.oop.Variables;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

public class AppStage extends Stage implements IObserver{

    private AbstractWorldMap map;
    private GridPane panel;
    private GridPane panel2;

    private GridPane panel3;

    private GridPane stats;
    private Scene scene;
    private IVariables vars;

    private UUID fileUUID;


    private boolean isEngineCreated = false;
    public AppStage(IVariables vars) throws Exception {
        this.vars = vars;
        init();
    }

    private Vector2d[] genAnimals(int n){
        List<Vector2d> positions = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            Vector2d position;
            position = new Vector2d(rand.nextInt(vars.getMapH()), rand.nextInt(vars.getMapL()));
            positions.add(position);
        }
        return positions.toArray(new Vector2d[positions.size()]);
    }

    public void stats_update(GridPane stats){
        stats.getChildren().clear();
        Label statsTitle = new Label("STATISTICS");
        //statsTitle.setLayoutY(200.0);
        int[] pom;
        pom= this.map.get_stats();

        Label animals = new Label("Animals:");
        Label animals_amount = new Label(Integer.toString(pom[0]));

        Label grasses = new Label("Grasses:");
        Label grasses_amount = new Label(Integer.toString(pom[1]));

        Label average_age = new Label("Average age:");
        float avg_age;
        if (pom[2]!=0)
            avg_age=pom[3]/pom[2];
        else
            avg_age=0;
        Label average_age_value = new Label(Float.toString(avg_age));

        Label free_cells = new Label("Free spots on map:");
        Label free_cells_value = new Label(Integer.toString(pom[4]));

        Label average_energy = new Label("Average animal's energy:");
        float avg_e;
        if (pom[0]!=0 && pom[5]!=0)
            avg_e=pom[5]/pom[0];
        else
            avg_e=0;
        Label average_energy_value = new Label(Float.toString(avg_e));
        //stats.getChildren().addAll(statsTitle,animals,animals_amount,grasses,grasses_amount);
        stats.addColumn(0,statsTitle,animals,animals_amount,grasses,grasses_amount,average_age,average_age_value,free_cells,free_cells_value,average_energy,average_energy_value);

        //saving data to csv file
        try
        {
            FileWriter fw = new FileWriter("stats"+fileUUID+".csv",true); //the true will append the new data
            fw.write("\n"+Integer.toString(pom[0])+","+Integer.toString(pom[1])+","+Integer.toString(pom[4])+","+Float.toString(avg_age)+","+Float.toString(avg_e));//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    public void init() throws Exception {
        //preparing csv file
        fileUUID = UUID.randomUUID();
        FileWriter fileWriter = new FileWriter("stats"+fileUUID+".csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("Animals,Grasses,Free_spots,Avgerage age of dead animals,Avg energy of living animals");
        printWriter.close();

        InputStream stream = new FileInputStream("src/main/resources/legend.png");
        Image image = new Image(stream);
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);
        VBox vBox=new VBox (imageView);

        panel3 = new GridPane();
        panel3.addRow(0,vBox);
        stats = new GridPane();
        panel3.add(stats,1,0);
        panel = new GridPane();
        panel2 = new GridPane();
//        TextField textField = new TextField();
        Button startButton = new Button();
        startButton.setText("Start/Resume");
        Button stopButton = new Button();
        stopButton.setText("Pause");
        panel2.add(startButton, 0,1);
        panel2.add(stopButton, 1 , 1);
        panel2.add(panel3, 0, 0);
        panel2.add(panel, 0, 2);
        panel2.setAlignment(Pos.CENTER);
        panel.getColumnConstraints().add(new ColumnConstraints(40));
        panel.getRowConstraints().add(new RowConstraints(40));
        panel.setAlignment(Pos.CENTER);
        panel.setGridLinesVisible(true);


        //MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw());
//        map = new GrassField( 15);
        map = new PortalMap(vars.getMapH(), vars.getMapL(), vars.getPlants(), vars);
        Vector2d[] positions = genAnimals(vars.getAnimals());

        SimulationEngine engine = new SimulationEngine(map, positions, this, 1000, vars);
        Thread engineThread = new Thread(engine);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isEngineCreated) {
                    engineThread.start();
                    isEngineCreated = true;
                }
                else{
                    if(!engine.getIsRunning())
                        engine.setIsRunning(true);
                }
            }
        });

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(engine.getIsRunning() && isEngineCreated)
                    engine.setIsRunning(false);
            }
        });
        this.setOnHiding( event -> {engineThread.interrupt();} );
        scene = new Scene(panel2, 800, 800);
        this.setScene(scene);
        this.show();
        drawGrid2();

    }

    public void start(Stage primaryStage) {

        drawGrid2();
        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void drawGrid2(){

        panel.setGridLinesVisible(false);
        panel.getChildren().clear();
        //System.out.println(map);
        Vector2d leftDown = map.getLeftDownCorner(), upRight = map.getRightUpCorner();
        String[] buf = map.toString().split("\n");
        int panelHeight = buf.length, panelWidth = buf[0].split(" ").length;
        //String[][] stringMap = new String[panelWidth][panelHeight];
        for (int i = 0; i < panelHeight; i++){
            List<String> buf3 = new ArrayList<>();
            String[] buf2;
            if(i == 0){
                buf2 = buf[i].split(" ");
            } else if (i == 1 || i == panelHeight-1) {
                continue;
            }
            else{
                buf2 = buf[i].split("\\|");
            }

            for (String s : buf2){
                if(!s.equals("") && !s.equals("|") && !s.equals(":") && !s.equals("\r"))
                    buf3.add(s);
            }

            if(i == 0){
                Label label0 = new Label("y/x");
                label0.setAlignment(Pos.CENTER);
                label0.setPrefSize(60, 60);
                panel.add(label0, 0 , 0);
                int cnt = 1;
                for(int j = leftDown.x; j <= upRight.x; j++){
                    Label label = new Label(Integer.toString(j));
                    label.setAlignment(Pos.CENTER);
                    label.setPrefSize(60, 60);
                    panel.add(label, cnt++ , 0);

                }
            }
            else{
                for(int j =0; j < buf3.size(); j++){
                    Label label;
                    if(j == 0) {
                        label = new Label(buf3.get(j).split(":")[0]);
                        label.setAlignment(Pos.CENTER);
                        label.setPrefSize(60, 60);
                        panel.add(label, j, i-1);
                    }
                    else {
                        if(!buf3.get(j).equals(" ")) {
                            //System.out.println("" + (leftDown.x + j - 1 ) + ":" + (upRight.y - (i-2)) +":"+ buf3.get(j));
                            IMapElement el = ((IMapElement) map.objectAt(new Vector2d((leftDown.x + j - 1 ), (upRight.y - (i-2)))));
                            if(el != null) {
                                GuiElementBox element = null;
                                try {
                                    element = new GuiElementBox(el.getImage(), el.getLabelText());
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                panel.add(element.getVBox(), j, i - 1);
                            }
                        }
                        else{
                            label = new Label("");
                            label.setAlignment(Pos.CENTER);
                            label.setPrefSize(60, 60);
                            panel.add(label, j, i-1);
                        }
                    }
                }
            }
        }
        panel.setGridLinesVisible(true);
        stats_update(stats);
    }
    private void drawGrid(GridPane panel, AbstractWorldMap map ){
        //System.out.println(map);
        Vector2d leftDown = map.getLeftDownCorner(), upRight = map.getRightUpCorner();
        String[] buf = map.toString().split("\n");
        int panelHeight = buf.length, panelWidth = buf[0].split(" ").length;
        //String[][] stringMap = new String[panelWidth][panelHeight];
        for (int i = 0; i < panelHeight; i++){
            List<String> buf3 = new ArrayList<>();
            String[] buf2;
            if(i == 0){
                buf2 = buf[i].split(" ");
            } else if (i == 1 || i == panelHeight-1) {
                continue;
            }
            else{
                buf2 = buf[i].split("\\|");
            }

            for (String s : buf2){
                if(!s.equals("") && !s.equals("|") && !s.equals(":") && !s.equals("\r"))
                    buf3.add(s);
            }

            if(i == 0){
                Label label0 = new Label("y/x");
                label0.setAlignment(Pos.CENTER);
                label0.setPrefSize(40, 40);
                panel.add(label0, 0 , 0);
                int cnt = 1;
                for(int j = leftDown.x; j <= upRight.x; j++){
                    Label label = new Label(Integer.toString(j));
                    label.setAlignment(Pos.CENTER);
                    label.setPrefSize(40, 40);
                    panel.add(label, cnt++ , 0);

                }
            }
            else{
                for(int j =0; j < buf3.size(); j++){
                    Label label;
                    if(j == 0)
                        label = new Label(buf3.get(j).split(":")[0]);
                    else
                        label = new Label(buf3.get(j));
                    label.setAlignment(Pos.CENTER);
                    label.setPrefSize(40, 40);
                    panel.add(label, j, i-1);
                }
            }
        }
    }

    @Override
    public void update(Message message) {
        Platform.runLater(()->{drawGrid2();});
    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        Platform.runLater(()->{drawGrid2();});
//    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        panel.getChildren().clear();
//        drawGrid2(panel, map);
//    }
}
