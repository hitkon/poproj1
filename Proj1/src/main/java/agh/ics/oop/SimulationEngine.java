package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements IEngine, IObserver, Runnable{

    //private MoveDirection[] args;
    private AbstractWorldMap map;
    private Vector2d[] positions;
    private List<Animal> animals;
    private int moveDelay;
    private App gui;

//    public SimulationEngine( AbstractWorldMap map, Vector2d[] positions, App gui, int moveDelay){
//        //this.args = args;
//        this.map = map;
//        this.positions = positions;
//        this.gui = gui;
//        this.moveDelay = moveDelay;
//        animals = new ArrayList<>();
//        //animals = new Animal[positions.length];
//        for (int i = 0; i < positions.length; i++) {
//
//            Animal animal = new Animal(map, positions[i]);
//            animal.addObserver(map);
//            animal.addObserver(this);
//            animals.add(animal);
//            //animals.get(i).addObserver(map);
//            //animals[i].addObserver(map.getBounds());
//            //animals[i].addObserver(gui);
//        }
//    }
    public SimulationEngine( AbstractWorldMap map, Vector2d[] positions, App gui, int moveDelay){
        //this.args = args;
        this.map = map;
        this.positions = positions;
        this.gui = gui;
        this.moveDelay = moveDelay;
        animals = new ArrayList<>();
        //animals = new Animal[positions.length];
        for (int i = 0; i < positions.length; i++) {

            Animal animal = new Animal(map, positions[i]);
            animal.addObserver(map);
            animal.addObserver(this);
            animals.add(animal);
            //animals.get(i).addObserver(map);
            //animals[i].addObserver(map.getBounds());
            //animals[i].addObserver(gui);
        }
    }

    @Override
    public Animal[] getAnimals(){return (Animal[]) animals.toArray();};

    public void removeAnimal(Animal animal){
        animal.removeObserver(this);
        animal.removeObserver(map);
        animals.remove(animal);
    }


//    public void setArgs(MoveDirection[] args){
//        this.args = args;
//    }
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
//
//        if (map instanceof GrassField)
//            map.getBounds().forceUpdate();

        //gui.drawGrid2();

        //System.out.println(map);
        while (true) {

            if(animals.isEmpty())
                break;

            for (int i = 0; i < Variables.plants_growth; i++)
                map.addNewGrass();
            for (int i = 0; i < animals.size(); i++) {
                animals.get(i).move();
//            Platform.runLater(()->{gui.drawGrid2();});
            }
            for(int i = 0; i < map.getRightUpCorner().x; i++){
                for(int j = 0; j < map.getRightUpCorner().y; j++)
                    map.startDayRutine(new Vector2d(i,j));
            }
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println("Engine thread has stopped");
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                gui.drawGrid2();
            });
        }
//        for (int i = 0; i < numberOfAnimals; i++) {
//            animals[i].removeObserver(map);
//            animals[i].removeObserver(map.getBounds());
//            //animals[i].removeObserver(gui);
//        }
    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        Platform.runLater(()->{gui.drawGrid2();});
//    }

    @Override
    public void update(Message message){
        switch (message.getText()){
            case "PositionChanged": {
                break;
            }
            case "Died": {
                Animal animal = (Animal)message.getAttachment();
                //remove(animal);
                removeAnimal(animal);
                break;
            }
            case "NewAnimal":{
                Animal animal = (Animal) message.getAttachment();
                animal.addObserver(map);
                animal.addObserver(this);
                animals.add(animal);
                //map.place(animal);
                break;
            }
            default:{
                //throw new IllegalArgumentException("Unable to read message:" + message.getText());
            }
        }
    }
}
