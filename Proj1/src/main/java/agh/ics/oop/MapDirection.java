package agh.ics.oop;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHEAST,
    SOUTHEAST,
    SOUTHWEST,
    NORTHWEST
    ;

    public String toString(){
        switch(this) {
            case EAST:
                return "0";
            case WEST:
                return "1";
            case NORTH:
                return "2";
            case SOUTH:
                return "3";
            case NORTHEAST:
                return "4";
            case NORTHWEST:
                return "5";
            case SOUTHEAST:
                return "6";
            case SOUTHWEST:
                return "7";
        }
        return "";
    }

    public MapDirection next(){
        switch(this) {
            case EAST:
                return MapDirection.SOUTHEAST;
            case SOUTHEAST:
                return MapDirection.SOUTH;
            case SOUTH:
                return MapDirection.SOUTHWEST;
            case SOUTHWEST:
                return MapDirection.WEST;
            case WEST:
                return MapDirection.NORTHWEST;
            case NORTHWEST:
                return MapDirection.NORTH;
            case NORTH:
                return MapDirection.NORTHEAST;
            case NORTHEAST:
                return MapDirection.EAST;
        }
        return MapDirection.NORTH;
    }

    public MapDirection previous(){
        switch(this) {
            case EAST:
                return MapDirection.NORTHEAST;
            case NORTHEAST:
                return MapDirection.NORTH;
            case NORTH:
                return MapDirection.NORTHWEST;
            case NORTHWEST:
                return MapDirection.WEST;
            case WEST:
                return MapDirection.SOUTHWEST;
            case SOUTHWEST:
                return MapDirection.SOUTH;
            case SOUTH:
                return MapDirection.SOUTHEAST;
            case SOUTHEAST:
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
            case NORTHEAST:
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
