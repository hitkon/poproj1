package agh.ics.oop;

import agh.ics.oop.gui.App;
import agh.ics.oop.gui.AppStage;
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
    private AppStage gui;
    private IVariables vars;
    private boolean isRun;

    public void startSimulation(){
        isRun = true;
    }
    public void stopSimulation(){
        isRun = false;
    }
    public boolean getIsRun(){
        return isRun;
    }

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, AppStage gui, int moveDelay, IVariables vars){
        this.vars = vars;
        this.map = map;
        this.positions = positions;
        this.gui = gui;
        this.moveDelay = moveDelay;
        animals = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {

            Animal animal = new Animal(map, positions[i], vars);
            animal.addObserver(map);
            animal.addObserver(this);
            animals.add(animal);
        }
    }

    @Override
    public Animal[] getAnimals(){return (Animal[]) animals.toArray();};

    public void removeAnimal(Animal animal){
        animal.removeObserver(this);
        animal.removeObserver(map);
        animals.remove(animal);
    }

    @Override
    public void run() {

        while (true) {
            if(!isRun){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
                continue;
            }


            for(int i = 0; i < animals.size(); i++){
                if(animals.get(i).getEnergy()==0){
                    animals.get(i).die();
                    animals.remove(i);
                    i--;
                }
            }
            System.out.println(animals.size());
            if(animals.isEmpty())
                break;

            for (int i = 0; i < animals.size(); i++) {
                animals.get(i).move();
//            Platform.runLater(()->{gui.drawGrid2();});
            }
            for(int i = 0; i < map.getRightUpCorner().x; i++){
                for(int j = 0; j < map.getRightUpCorner().y; j++)
                    map.startDayRutine(new Vector2d(i,j));
            }
            for (int i = 0; i < vars.getPlantEnergy(); i++)
                map.addNewGrass();
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
    }


    @Override
    public void update(Message message){
        switch (message.getText()){
            case "PositionChanged": {
                break;
            }
            case "NewAnimal":{
                Animal animal = (Animal) message.getAttachment();
                animal.addObserver(map);
                animal.addObserver(this);
                animals.add(animal);
                break;
            }
            default:{
                //throw new IllegalArgumentException("Unable to read message:" + message.getText());
            }
        }
    }
}
