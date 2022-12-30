package agh.ics.oop;

public abstract class AbstractWorldMap implements IWorldMap, IObserver{

    protected MapBoundary bounds;

    public abstract void addNewGrass();

    public abstract Vector2d getLeftDownCorner();

    public abstract void setBounds(MapBoundary bounds);
    public abstract MapBoundary getBounds();


    public abstract Vector2d getRightUpCorner();

    public abstract int[] get_stats();


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

    public abstract void startDayRutine(Vector2d pos);

//    public abstract void removeFromAnimalList(Animal animal);

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//
//        Animal animal = (Animal)objectAt(oldPosition);
//        remove(animal);
//        animal.setPosition(newPosition);
//        place(animal);
//    }
    @Override
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
                //removeFromAnimalList(animal);
                break;
            }
            case "NewAnimal":{
                Animal animal = (Animal) message.getAttachment();
                place(animal);
                break;
            }
            default:{
                //throw new IllegalArgumentException("Unable to read message:" + message.getText());
            }
        }
    }
}
