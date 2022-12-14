package agh.ics.oop;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortalMap extends AbstractWorldMap{

    private int length, width;
    private IMapElement[][] Objects;
    private int[][] deadAmount;
    private List<Vector2d>jungleCells;
    private boolean[][] isJungleCell;
    public PortalMap(int width, int length){
        this.width = width;
        this.length = length;
        Objects = new IMapElement[width][length];
    }
    private Vector2d getRandomJungleFreePosition(){
        Random rand = new Random();
        Vector2d newPosition;
        do{
            newPosition = new Vector2d(rand.nextInt(width), rand.nextInt(length));
        }
        while(isJungleCell[newPosition.x][newPosition.y]);
        return newPosition;
    }

    public PortalMap(int width, int length, int jungleCellAmount){
        this.width = width;
        this.length = length;
        Objects = new IMapElement[width][length];
        deadAmount = new int[width][length];
        jungleCells = new ArrayList<>();
        isJungleCell = new boolean[width][length];
        for (int i = 0; i < jungleCellAmount; i++){
            Vector2d newPosition = getRandomJungleFreePosition();
            isJungleCell[newPosition.x][newPosition.y] = true;
            jungleCells.add(newPosition);
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
        return Objects[position.x][position.y] == null || !(Objects[position.x][position.y] instanceof Animal);
    }

    private Vector2d getRandomFreePosition(){
        Random rand = new Random();
        Vector2d newPosition;
        do{
            newPosition = new Vector2d(rand.nextInt(width), rand.nextInt(length));
        }
        while(isOccupied(newPosition));
        return newPosition;
    }

    @Override
    public boolean place(Animal animal) {
//        if(canMoveTo(animal, animal.getPosition())){
        if(isOutOfBounds(animal.getPosition()))
        {
            Vector2d newRandomPosition = getRandomFreePosition();
            animal.setPosition(newRandomPosition);
            animal.spendEnergy(0);
            Objects[newRandomPosition.x][newRandomPosition.y] = animal;
        }
        else {
//            if(objectAt(animal.getPosition()) instanceof Animal)
//                throw new IllegalArgumentException("Two animals on one position: " + animal.getPosition());
            Objects[animal.getPosition().x][animal.getPosition().y] = animal;
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return Objects[position.x][position.y] != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return Objects[position.x][position.y];
    }

    @Override
    public void remove(Animal animal) {
        Objects[animal.getPosition().x][animal.getPosition().y] = null;
    }
}
