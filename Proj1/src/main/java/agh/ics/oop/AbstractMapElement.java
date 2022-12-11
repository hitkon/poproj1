package agh.ics.oop;

public abstract class AbstractMapElement implements IMapElement{
    private Vector2d position;
    public AbstractMapElement(Vector2d pos){
        position = pos;
    }
    @Override
    public Vector2d getPosition(){return position;}

    @Override
    public abstract String getImage();


    public void setPosition(Vector2d pos){position=pos;}

}
