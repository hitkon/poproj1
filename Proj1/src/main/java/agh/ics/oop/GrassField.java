package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap implements IWorldMap{

    //private List<IMapElement> Objects;
    private Map<Vector2d, IMapElement> Objects;

    private int grassSpawnLength;

    private Vector2d getRandomFreePlace(){
        Random rand = new Random();
        Vector2d res;
        do{
            res = new Vector2d(rand.nextInt(grassSpawnLength), rand.nextInt(grassSpawnLength));
        }
        while (this.isOccupied(res));
        return res;
    }
    public void setBounds(MapBoundary bounds){
        this.bounds = bounds;
        this.bounds.setMap(Objects);
    }

    public MapBoundary getBounds(){
        return bounds;
    }

    public GrassField(int n){
        Objects = new HashMap<>();
        setBounds(new MapBoundary());

        grassSpawnLength = (int)(Math.sqrt(n * 10));
        Objects.put(new Vector2d(2,3 ), new Grass(new Vector2d(2, 3)));
        for (int i = 0; i < n-1; i++){
            Vector2d newGrassPosition = getRandomFreePlace();
            Objects.put(newGrassPosition, new Grass(newGrassPosition));
        }
        bounds.forceUpdate();
    }

    @Override
    public boolean canMoveTo(Animal animal, Vector2d position) {
        Object buf =  objectAt(position);
        return buf == null || (buf != null && buf instanceof Grass);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal, animal.getPosition())){
            if(isOccupied((animal.getPosition()))){
                Objects.remove(objectAt(animal.getPosition()));
                bounds.remove(animal.getPosition());
                Vector2d newGrassPosition = getRandomFreePlace();
                bounds.add(newGrassPosition);
                Objects.put(newGrassPosition, new Grass(newGrassPosition));
            }
            Objects.put(animal.getPosition(), animal);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " can't place on position");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return Objects.get(position);
    }

    @Override
    public void remove(Animal animal) {
        Objects.remove(animal.getPosition());
    }

    public Vector2d getLeftDownCorner(){
        return bounds.getLeftDown();
//        Vector2d leftDown = new Vector2d(0, 0);
//        for(IMapElement Obj : Objects.values()){
//            leftDown = Obj.getPosition().lowerLeft(leftDown);
//        }
//        return leftDown;
    }

    public Vector2d getRightUpCorner(){
        return bounds.getRightUp();
//        Vector2d rightUp = new Vector2d(grassSpawnLength-1, grassSpawnLength-1);
//        for(IMapElement Obj : Objects.values()){
//            rightUp = Obj.getPosition().upperRight(rightUp);
//        }
//        return  rightUp;
    }
}
