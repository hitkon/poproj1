package agh.ics.oop;

import javax.swing.text.Position;
import java.util.*;

public class PortalMap extends AbstractWorldMap {

    private int length, width;
    private MapCell[][] map;
    private HashSet<Vector2d> deadCells;

    private boolean isFreeDeadCellExist() {
        if (deadCells.isEmpty())
            return false;

        Vector2d[] cells = deadCells.toArray(new Vector2d[deadCells.size()]);
        for (int i = 0; i < deadCells.size(); i++) {
            if (!(map[cells[i].x][cells[i].y].isGrassHere()))
                return true;
        }
        return false;
    }

    private Vector2d returnFreeDeadPosition() {
        Vector2d[] cells = deadCells.toArray(new Vector2d[deadCells.size()]);
        for (int i = 0; i < deadCells.size(); i++) {
            if (!(map[cells[i].x][cells[i].y].isGrassHere()))
                return cells[i];
        }
        return null;
    }

    public void addNewGrass() {
        Random rand = new Random();
        Vector2d position;

        if (rand.nextInt(10) <= 1) {
            if (isFreeDeadCellExist()) {
                position = returnFreeDeadPosition();
            } else {
                return;
            }
        } else {
            if (deadCells.size() == width * length) {
                return;
                //position = returnFreeDeadPosition();
            } else {
                do {
                    position = new Vector2d(rand.nextInt(width), rand.nextInt(length));
                }
                while (deadCells.contains(position) || (map[position.x][position.y].isGrassHere()));
            }
        }
        map[position.x][position.y].setGrassHere();
//        if(objectAt(position) instanceof Animal){
//            ((Animal)objectAt(position)).addEnergy(Variables.breed_energy);
//            return;
    }
//        Grass newGrass = new Grass(position);



    public PortalMap(int width, int length, int grassAmount){
        this.width = width;
        this.length = length;
        map = new MapCell[width][length];
        for (int i = 0 ; i < width; i++){
            for (int j = 0; j < length; j++){
                map[i][j] = new MapCell(i,j);
            }
        }
        deadCells = new HashSet<>();
        for (int i = 0; i < grassAmount; i++){
            addNewGrass();
        }
    }
    @Override
    public Vector2d getLeftDownCorner() {
        return new Vector2d(0,0);
    }

    @Override
    public void setBounds(MapBoundary bounds) {

    }

    @Override
    public MapBoundary getBounds() {
        return null;
    }

    @Override
    public Vector2d getRightUpCorner() {
        return new Vector2d(width-1,length-1);
    }

    private boolean isOutOfBounds(Vector2d position){
        return position.x < 0 || position.x >= width || position.y < 0 || position.y>=length;
    }

    public boolean canMoveTo(Animal animal, Vector2d position) {
        if (isOutOfBounds(position))
            return animal.isEnoughEnergy();
        return true; //Objects[position.x][position.y] == null || !(Objects[position.x][position.y] instanceof Animal);
    }

    private Vector2d getRandomFreePosition(){
        Random rand = new Random();
        Vector2d newPosition = new Vector2d(rand.nextInt(width), rand.nextInt(length));
//        do{
//
//        }
//        while(isOccupied(newPosition));
        return newPosition;
    }

    @Override
    public boolean place(Animal animal) {
//        boolean isGrassEaten = false;
        if(isOutOfBounds(animal.getPosition()))
        {
            Vector2d newRandomPosition = getRandomFreePosition();
            animal.setPosition(newRandomPosition);
//            animal.spendEnergy(Variables.breed_energy);
//            if(isOccupied(newRandomPosition))
//                isGrassEaten = true;
            map[newRandomPosition.x][newRandomPosition.y].addAnimal(animal);
        }
        else{
            map[animal.getPosition().x][animal.getPosition().y].addAnimal(animal);
//        else {
//            if(isOccupied(animal.getPosition()))
//                isGrassEaten = true;
//            Objects[animal.getPosition().x][animal.getPosition().y] = animal;
//        }
//        if(isGrassEaten){
//            animal.addEnergy(Variables.plant_energy);
            //addNewGrass();
        }

        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return map[position.x][position.y].isGrassHere() || map[position.x][position.y].isAnimalsHere();
        //return Objects[position.x][position.y] != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return map[position.x][position.y].getTopObject();
    }

    @Override
    public void remove(Animal animal) {
        map[animal.getPosition().x][animal.getPosition().y].removeAnimal(animal);
    }

    public void update(Message message){
        switch (message.getText()){
            case "PositionChanged": {
                Vector2d[] positions = (Vector2d[]) message.getAttachment();
                Vector2d oldPosition = positions[0], newPosition = positions[1];
                Animal animal = (Animal)objectAt(oldPosition);
                remove(animal);
                animal.setPosition(newPosition);
                place(animal);
                break;
            }
            case "Died": {
                Animal animal = (Animal)message.getAttachment();
                remove(animal);
                deadCells.add(animal.getPosition());
                break;
            }
            default:{
                //throw new IllegalArgumentException("Unable to read message:" + message.getText());
            }
        }
    }
}
