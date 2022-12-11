package agh.ics.oop;

import javafx.collections.transformation.SortedList;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver {
    private TreeSet<Vector2d> OX, OY;
    private Map<Vector2d, IMapElement> Objects;

    public void setMap(Map<Vector2d, IMapElement> Objects){
        this.Objects = Objects;
    }

    public MapBoundary(){
        OX = new TreeSet<>(new Comparator<Vector2d>() {
            @Override
            public int compare(Vector2d o1, Vector2d o2) {
                return o1.x == o2.x ? o1.y - o2.y : o1.x - o2.x;
            }
        });
        OY = new TreeSet<>(new Comparator<Vector2d>() {
            @Override
            public int compare(Vector2d o1, Vector2d o2) {
                return o1.y == o2.y ? o1.x - o2.x : o1.y - o2.y;
            }
        });
    }

    public void forceUpdate(){
        OX = new TreeSet<>(new Comparator<Vector2d>() {
            @Override
            public int compare(Vector2d o1, Vector2d o2) {
                return o1.x == o2.x ? o1.y - o2.y : o1.x - o2.x;
            }
        });
        OY = new TreeSet<>(new Comparator<Vector2d>() {
            @Override
            public int compare(Vector2d o1, Vector2d o2) {
                return o1.y == o2.y ? o1.x - o2.x : o1.y - o2.y;
            }
        });
        for(IMapElement el :  Objects.values()){
            OX.add(el.getPosition());
            OY.add(el.getPosition());
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        OX.remove(oldPosition);
        OX.add(newPosition);
        OY.remove(oldPosition);
        OY.add(newPosition);
    }

    public Vector2d getRightUp(){
        return new Vector2d(OX.last().x, OY.last().y);
    }
    public Vector2d getLeftDown(){
        return new Vector2d(OX.first().x, OY.first().y);
    }
    public void remove(Vector2d pos){
        OX.remove(pos);
        OY.remove(pos);
    }
    public void add(Vector2d pos){
        OX.add(pos);
        OY.add(pos);
    }
}
