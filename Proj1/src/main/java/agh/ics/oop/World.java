package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
//    public static void run(Direction[] directions){
//        for (int i = 0;  i< directions.length; i++){
//            if(directions[i] == null)
//                continue;
//            switch(directions[i]){
//                case FORWARD:
//                    System.out.println("Zwierzak idzie do przodu");
//                    break;
//                case BACKWARD:
//                    System.out.println("Zwierzak idzie do tyłu");
//                    break;
//                case LEFT:
//                    System.out.println("Zwierzak skręca w lewo");
//                    break;
//                case RIGHT:
//                    System.out.println("Zwierzak skręca w prawo");
//                    break;
//            }
//        }
//    }
//
//    public static Direction[] processInput(String[] args){
//
//        Direction[] directions = new Direction[args.length];
//        for(int i = 0; i < args.length; i++){
//            switch(args[i]){
//                case "f":
//                    directions[i] = Direction.FORWARD;
//                    break;
//                case "b":
//                    directions[i] = Direction.BACKWARD;
//                    break;
//                case "l":
//                    directions[i] = Direction.LEFT;
//                    break;
//                case "r":
//                    directions[i] = Direction.RIGHT;
//                    break;
//            }
//        }
//        return directions;
//    }

    public static void main(String[] args){
//        Vector2d pos1 = new Vector2d(1, 2);
//        System.out.println(pos1);
//        Vector2d pos2 = new Vector2d(-2, 1);
//        System.out.println(pos2);
//        System.out.println(pos1.add(pos2));

        Application.launch(App.class, args);

//        try {
//            MoveDirection[] directions = new OptionsParser().parse(args);
//            AbstractWorldMap map = new RectangularMap(10, 5);
//            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(2, 2)};
//            IEngine engine = new SimulationEngine(directions, map, positions);
//            engine.run();
//            System.out.println(map);
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
    }
}
