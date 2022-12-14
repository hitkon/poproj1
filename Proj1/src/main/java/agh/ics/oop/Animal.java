package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private IWorldMap map;
    private List<IPositionChangeObserver> Observers;
    private MapDirection animalDir;
    private int Energy;
    public Animal(IWorldMap map, Vector2d initialPos){
        super(initialPos);
        animalDir = MapDirection.NORTH;
        Observers = new ArrayList<>();
        this.map = map;
        map.place(this);
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


    public void move(MoveDirection dir){
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
            case WEST -> {
                return "src/main/resources/left.png";
            }
            case NORTH -> {
                return "src/main/resources/up.png";
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
