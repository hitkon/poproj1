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
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }

    @Override
    public void remove(Animal animal) {

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal)objectAt(oldPosition);
        remove(animal);
        animal.setPosition(newPosition);
        place(animal);
    }
}
