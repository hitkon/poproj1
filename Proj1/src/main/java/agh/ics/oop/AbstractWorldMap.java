package agh.ics.oop;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected MapBoundary bounds;

    public abstract Vector2d getLeftDownCorner();

    public abstract void setBounds(MapBoundary bounds);
    public abstract MapBoundary getBounds();


    public abstract Vector2d getRightUpCorner();


    public String toString(){
        return new MapVisualizer(this).draw(getLeftDownCorner(), getRightUpCorner());
    }

    @Override
    public abstract boolean canMoveTo(Animal animal, Vector2d position);

    @Override
    public abstract boolean place(Animal animal);

    @Override
    public abstract boolean isOccupied(Vector2d position);

    @Override
    public abstract Object objectAt(Vector2d position);

    @Override
    public abstract void remove(Animal animal);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal)objectAt(oldPosition);
        remove(animal);
        animal.setPosition(newPosition);
        place(animal);
    }
}
