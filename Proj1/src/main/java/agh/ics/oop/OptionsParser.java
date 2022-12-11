package agh.ics.oop;

import java.util.List;

public class OptionsParser {
    public MoveDirection[] parse(String[] args){
        int size = 0;
        for(String arg : args)
            if(arg.equals("l") || arg.equals("r") || arg.equals("f") || arg.equals("b"))
                size++;
        MoveDirection[] ans = new MoveDirection[size];
        for(int i = 0; i < args.length; i++){
            switch (args[i]){
                case "f" -> ans[i] = MoveDirection.FORWARD;
                case "b" -> ans[i] = MoveDirection.BACKWARD;
                case "l" -> ans[i] = MoveDirection.LEFT;
                case "r" -> ans[i] = MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(args[i] + " is not a valid argument");
            }
        }
        return ans;
    }
    public MoveDirection[] parse(List<String> args){
        int size = 0;
        for(String arg : args)
            if(arg.equals("l") || arg.equals("r") || arg.equals("f") || arg.equals("b"))
                size++;
        MoveDirection[] ans = new MoveDirection[size];
        for(int i = 0; i < args.size(); i++){
            switch (args.get(i)){
                case "f" -> ans[i] = MoveDirection.FORWARD;
                case "b" -> ans[i] = MoveDirection.BACKWARD;
                case "l" -> ans[i] = MoveDirection.LEFT;
                case "r" -> ans[i] = MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(args.get(i) + " is not a valid argument");
            }
        }
        return ans;
    }
}
