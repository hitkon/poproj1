package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal extends AbstractMapElement{
    private IWorldMap map;
    private List<IPositionChangeObserver> Observers;

    private int[] genom= new int[Variables.genom];

    private int active_gen=0;
    private MapDirection animalDir;
    private int Energy;

    public Animal(IWorldMap map, Vector2d initialPos){
        super(initialPos);
        animalDir = MapDirection.NORTH;
        Observers = new ArrayList<>();
        this.map = map;
        map.place(this);
        int rand= new Random().nextInt();
        for (int i=0;i<Variables.genom;i++){
            genom[i]=Math.abs(rand%Variables.genom);
            rand= new Random().nextInt();
        }
    }


    public int[] show_genom(){
        return genom;
    }
    public void addObserver(IPositionChangeObserver observer){
        Observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        Observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos){
        Observers.forEach(observer -> observer.positionChanged(oldPos, newPos));
    }

    public boolean isEnoughEnergy(){
        return true;
    }
    public void spendEnergy(int Energy){

    }
    public void addEnergy(int Energy){

    }
    public boolean isAt(Vector2d position){
        return getPosition().equals(position);
    }


    public void move(){
        if(map.canMoveTo(this, getPosition().add(animalDir.toUnitVector())))
            positionChanged(getPosition(), getPosition().add(animalDir.toUnitVector()));
        active_gen=(active_gen+1)%Variables.genom;
        animalDir=animalDir.new_direction(genom[active_gen]);
    }

    public void move2(MoveDirection dir){
        switch(dir){
            case LEFT -> animalDir = animalDir.previous();
            case RIGHT -> animalDir = animalDir.next();
            case FORWARD -> {
                if(map.canMoveTo(this, getPosition().add(animalDir.toUnitVector())))
                    positionChanged(getPosition(), getPosition().add(animalDir.toUnitVector()));

            }
            case BACKWARD -> {
                if(map.canMoveTo(this, getPosition().add(animalDir.toUnitVector().opposite())))
                    positionChanged(getPosition(), getPosition().add(animalDir.toUnitVector().opposite()));
            }
        }
    }

    public String toString(){
        return animalDir.toString();
    }

    @Override
    public String getImage() {
        switch (this.animalDir){
            case EAST -> {
                return "src/main/resources/right.png";
            }
            case NORTHEAST -> {
                return "src/main/resources/upRight.png";
            }
            case WEST -> {
                return "src/main/resources/left.png";
            }
            case NORTHWEST -> {
                return "src/main/resources/upLeft.png";
            }
            case NORTH -> {
                return "src/main/resources/up.png";
            }
            case SOUTHEAST -> {
                return "src/main/resources/downRight.png";
            }
            case SOUTHWEST -> {
                return "src/main/resources/downLeft.png";
            }
            case SOUTH -> {
                return "src/main/resources/down.png";
            }
        }
        return null;
    }

    @Override
    public String getLabelText() {
        return "A" + this.getPosition().toString();
    }
}
