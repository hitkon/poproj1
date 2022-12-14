package agh.ics.oop;

public class Grass extends AbstractMapElement{
    //private Vector2d position;

    public Grass(Vector2d pos)
    {
        super(pos);
    }

    @Override
    public String getImage() {
        return "src/main/resources/grass.png";
    }

    @Override
    public String getLabelText() {
        return "Grass";
    }

    public String toString(){
        return "*";
    }

}
