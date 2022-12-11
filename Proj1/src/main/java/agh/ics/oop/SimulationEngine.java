package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.Random;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable{

    private MoveDirection[] args;
    private AbstractWorldMap map;
    private Vector2d[] positions;
    private Animal[] animals;
    private int moveDelay;
    private App gui;

    public SimulationEngine(MoveDirection[] args, AbstractWorldMap map, Vector2d[] positions, App gui, int moveDelay){
        this.args = args;
        this.map = map;
        this.positions = positions;
        this.gui = gui;
        this.moveDelay = moveDelay;
        animals = new Animal[positions.length];
        for (int i = 0; i < positions.length; i++) {
            animals[i] = new Animal(map, positions[i]);
            animals[i].addObserver(map);
            animals[i].addObserver(map.getBounds());
            //animals[i].addObserver(gui);
        }
    }
    public SimulationEngine( AbstractWorldMap map, Vector2d[] positions, App gui, int moveDelay){
        this.args = args;
        this.map = map;
        this.positions = positions;
        this.gui = gui;
        this.moveDelay = moveDelay;
        animals = new Animal[positions.length];
        for (int i = 0; i < positions.length; i++) {
            animals[i] = new Animal(map, positions[i]);
            animals[i].addObserver(map);
            animals[i].addObserver(map.getBounds());
            animals[i].addObserver(gui);
        }
    }

    @Override
    public Animal[] getAnimals(){return animals;};

    public void setArgs(MoveDirection[] args){
        this.args = args;
    }
    @Override
    public void run() {

        int numberOfAnimals = positions.length;
//        animals = new Animal[numberOfAnimals];
//        for (int i = 0; i < numberOfAnimals; i++) {
//            animals[i] = new Animal(map, positions[i]);
//            animals[i].addObserver(map);
//            animals[i].addObserver(map.getBounds());
//            animals[i].addObserver(this);
//        }

        if (map instanceof GrassField)
            map.getBounds().forceUpdate();

        //gui.drawGrid2();

        //System.out.println(map);
        for (int i = 0; i < args.length; i++){
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println("Engine thread has stopped");
                throw new RuntimeException(e);
            }
            animals[i%numberOfAnimals].move(args[i]);
//            Platform.runLater(()->{gui.drawGrid2();});
        }
//        for (int i = 0; i < numberOfAnimals; i++) {
//            animals[i].removeObserver(map);
//            animals[i].removeObserver(map.getBounds());
//            //animals[i].removeObserver(gui);
//        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(()->{gui.drawGrid2();});
    }
}
