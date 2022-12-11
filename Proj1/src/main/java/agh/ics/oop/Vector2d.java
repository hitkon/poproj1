package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    boolean precedes(Vector2d vec){
        if(x <= vec.x && y <= vec.y)
            return true;
        return false;
    }

    boolean follows(Vector2d vec){
        if(x >= vec.x && y >= vec.y)
            return true;
        return false;
    }

    Vector2d add(Vector2d vec){
        return new Vector2d(this.x + vec.x, this.y + vec.y);
    }

    Vector2d subtract(Vector2d vec){
        return new Vector2d(this.x - vec.x, this.y - vec.y);
    }

    Vector2d upperRight(Vector2d vec){
        return new Vector2d(Math.max(x, vec.x), Math.max(y, vec.y));
    }

    Vector2d lowerLeft(Vector2d vec){
        return new Vector2d(Math.min(x, vec.x), Math.min(y, vec.y));
    }

    Vector2d opposite(){
        return new Vector2d(-x, -y);
    }

    public boolean equals(Object other){
        if(this == other)
            return true;
        if(!(other instanceof Vector2d))
            return false;
        Vector2d vec = (Vector2d) other;

        if(x == vec.x && y == vec.y)
            return true;
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
