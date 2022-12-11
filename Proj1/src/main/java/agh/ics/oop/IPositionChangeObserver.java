package agh.ics.oop;

import java.io.FileNotFoundException;

public interface IPositionChangeObserver {
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
