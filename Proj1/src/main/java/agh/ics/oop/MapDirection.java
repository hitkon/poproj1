package agh.ics.oop;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTEAST,
    SOUTHEAST,
    SOUTHWEST,
    NORTHWEST
    ;

    public String toString(){
        switch(this) {
            case EAST:
                return "E";
            case WEST:
                return "W";
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
        }
        return "";
    }

    public MapDirection next(){
        switch(this) {
            case EAST:
                return MapDirection.SOUTH;
            case WEST:
                return MapDirection.NORTH;
            case NORTH:
                return MapDirection.EAST;
            case SOUTH:
                return MapDirection.WEST;
        }
        return MapDirection.NORTH;
    }

    public MapDirection previous(){
        switch(this) {
            case EAST:
                return MapDirection.NORTH;
            case WEST:
                return MapDirection.SOUTH;
            case NORTH:
                return MapDirection.WEST;
            case SOUTH:
                return MapDirection.EAST;
        }
        return MapDirection.NORTH;
    }

    public Vector2d toUnitVector(){
        switch(this) {
            case EAST:
                return new Vector2d(1,0);
            case WEST:
                return new Vector2d(-1,0);
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SOUTHEAST:
                return new Vector2d(1,-1);
            case NORTEAST:
                return new Vector2d(1,1);
            case SOUTHWEST:
                return new Vector2d(-1,-1);
            case NORTHWEST:
                return new Vector2d(-1,1);
        }
        return new Vector2d(0,1);
    }

    public MapDirection new_direction(int gen){
        switch (gen){
            case 0: return this;
            case 1: return this.next();
            case 2: return this.next().next();
            case 3: return this.next().next().next();
            case 4: return this.next().next().next().next();
            case 5: return this.previous().previous().previous();
            case 6: return this.previous().previous();
            case 7: return this.previous();

        }
        return this;
    }

}
