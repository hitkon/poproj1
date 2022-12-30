package agh.ics.oop;

import javax.swing.text.Position;
import java.util.*;

public class PortalMap extends AbstractWorldMap {

    private int animals,grasses,dead_animals=0,age_sum=0,free_cells,energy_sum;
    private int length, width;
    private MapCell[][] map;
    private HashSet<Vector2d> deadCells;
    private IVariables vars;

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

    private Vector2d[] getFreeCells(){
        List<Vector2d> ret = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                if(!map[i][j].isGrassHere() && !deadCells.contains(new Vector2d(i, j)))
                    ret.add(new Vector2d(i, j));
            }
        }
        return ret.toArray(new Vector2d[ret.size()]);
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
            Vector2d[] freeCells = getFreeCells();
            if (freeCells.length == 0) {
                return;
            } else {
                position = freeCells[rand.nextInt(freeCells.length)];
            }
        }
        map[position.x][position.y].setGrassHere();
        grasses+=1;

    }




    public PortalMap(int width, int length, int grassAmount, IVariables vars){
        this.vars = vars;
        this.width = width;
        this.length = length;
        map = new MapCell[width][length];
        for (int i = 0 ; i < width; i++){
            for (int j = 0; j < length; j++){
                map[i][j] = new MapCell(i,j, vars);
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
        return true;
    }

    private Vector2d getRandomFreePosition(){
        Random rand = new Random();
        Vector2d newPosition = new Vector2d(rand.nextInt(width), rand.nextInt(length));
        return newPosition;
    }

    @Override
    public boolean place(Animal animal) {
        if(isOutOfBounds(animal.getPosition()))
        {
            Vector2d newRandomPosition = getRandomFreePosition();
            animal.setPosition(newRandomPosition);
            animal.spendEnergy(vars.getBreedEnergy());
            map[newRandomPosition.x][newRandomPosition.y].addAnimal(animal);
        }
        else{
            map[animal.getPosition().x][animal.getPosition().y].addAnimal(animal);
        }
        animals+=1;
        return true;
    }

    public void startDayRutine(Vector2d pos){
        if(map[pos.x][pos.y].isAnimalsHere()) {
            boolean b = map[pos.x][pos.y].dayRutine();
            if (b==true)
                this.grasses-=1;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return map[position.x][position.y].isGrassHere() || map[position.x][position.y].isAnimalsHere();
    }

    @Override
    public Object objectAt(Vector2d position) {
        return map[position.x][position.y].getTopObject();
    }

    @Override
    public void remove(Animal animal) {
        map[animal.getPosition().x][animal.getPosition().y].removeAnimal(animal);
        this.animals-=1;
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
                dead_animals+=1;
                age_sum+=animal.getAge();
                deadCells.add(animal.getPosition());
                break;
            }
            default:{
                //throw new IllegalArgumentException("Unable to read message:" + message.getText());
            }
        }
    }

    public int[] get_stats(){
        free_cells=0;
        energy_sum=0;
        for (int i=0;i<this.length;i++)
            for (int j=0;j<this.width;j++){
                if (!map[j][i].isGrassHere() && !map[j][i].isAnimalsHere())
                    free_cells+=1;
                energy_sum+=map[j][i].all_energy();
            }

        int[] pom = new int[6];
        pom[0]=animals;
        pom[1]=grasses;
        pom[2]=dead_animals;
        pom[3]=age_sum;
        pom[4]=free_cells;
        pom[5]=energy_sum;
        return pom;
    }
}
