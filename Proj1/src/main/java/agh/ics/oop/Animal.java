package agh.ics.oop;

import com.sun.security.auth.NTSid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal extends AbstractMapElement{
    private IWorldMap map;
    private List<IObserver> Observers;

    private int[] genom; //= new int[Variables.genom];

    private int active_gen=0;
    private MapDirection animalDir;
    private int energy;
    private int children;
    private int age;

    private IVariables vars;

    public Animal(IWorldMap map, Vector2d initialPos, IVariables vars){
        super(initialPos);
        this.vars = vars;
        animalDir = MapDirection.NORTH;
        animalDir = animalDir.new_direction(new Random().nextInt(8));
        Observers = new ArrayList<>();
        this.genom = new int[vars.getGenom()];
        this.map = map;
        map.place(this);
        this.energy = vars.getStartEnergy();
        int rand= new Random().nextInt();
        for (int i=0;i<vars.getGenom();i++){
            genom[i]=Math.abs(rand%8);
            rand= new Random().nextInt();
        }
        this.children = 0;
        this.age = 0;
    }

    private int[] getNewGenom(Animal animal1, Animal animal2){
        int[] newGen = new int[vars.getGenom()];
        int num1 = animal1.energy / (animal1.energy + animal2.energy);
        for (int i = 0; i < num1; i++)
            newGen[i] = animal1.genom[i];
        for(int i = num1; i < vars.getGenom(); i++)
            newGen[i] = animal2.genom[i];
        return newGen;
    }

    private void mutate(){
        Random rand = new Random();
        int mutationsNum = rand.nextInt (vars.getMaxMutations() - vars.getMinMutations() + 1) + vars.getMinMutations();
        for(int i = 0; i < mutationsNum; i++){
            genom[rand.nextInt(vars.getGenom())] = rand.nextInt(8);
        }
    }


    public Animal(IWorldMap map, Vector2d initialPos, Animal father, Animal mother, IVariables vars){
        super(initialPos);
        this.vars = vars;
        animalDir = MapDirection.NORTH;
        this.genom = new int[vars.getGenom()];
        animalDir = animalDir.new_direction(new Random().nextInt(8));
        Observers = new ArrayList<>();
        this.map = map;
        map.place(this);
        this.energy = vars.getStartEnergy();
        int rand= new Random().nextInt();
        if(new Random().nextInt() % 2 == 0)
            genom = getNewGenom(father, mother);
        else
            genom = getNewGenom(mother, father);
        mutate();
        this.children = 0;
        this.age = 0;

    }

    public int[] show_genom(){
        return genom;
    }
    public void addObserver(IObserver observer){
        Observers.add(observer);
    }
    public void removeObserver(IObserver observer){
        Observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos){
        Vector2d[] positions = {oldPos, newPos};
        Observers.forEach(observer -> observer.update( new Message("PositionChanged", positions)));
    }

    public void addChild(){
        children++;
    }
    public void makeNewAnimal(Animal pair){
        Animal newAnimal = new Animal(map, getPosition(), this, pair, vars);
        children+=1;
        pair.children+=1;
        spendEnergy(vars.getBreedEnergy());
        pair.spendEnergy(vars.getBreedEnergy());
        Observers.forEach(observer -> observer.update( new Message("NewAnimal", newAnimal)));
    }

    public void die(){
        Observers.forEach(observer -> observer.update( new Message("Died", this)));
    }

    public boolean isEnoughEnergy(){
        return energy > vars.getReadyEnergy();
    }

    public int getEnergy(){return energy;}
    public void spendEnergy(int energy){
        this.energy -= energy;
    }
    public void addEnergy(int energy){
        this.energy += energy;
    }
    public boolean isAt(Vector2d position){
        return getPosition().equals(position);
    }

    public void move(){
//        if(energy == 0) {
//            die();
//            return;
//        }
//        if(map.objectAt(getPosition().add(animalDir.toUnitVector())) instanceof Animal){
//            Animal pair = (Animal)map.objectAt(getPosition().add(animalDir.toUnitVector()));
//            if(this.isEnoughEnergy() && pair.isEnoughEnergy()){
//                this.addChildren();
//                pair.addChildren();
//
//            }
//        }
        age+=1;
        active_gen=(active_gen+1)%vars.getGenom();
        animalDir=animalDir.new_direction(genom[active_gen]);
        if(map.canMoveTo(this, getPosition().add(animalDir.toUnitVector()))) {
            positionChanged(getPosition(), getPosition().add(animalDir.toUnitVector()));
            spendEnergy(1);
        }
//        active_gen=(active_gen+1)%Variables.genom;
//        animalDir=animalDir.new_direction(genom[active_gen]);

    }


    public String toString(){
        return animalDir.toString();
    }

    @Override
    public String getImage() {
        switch (this.animalDir){
            case EAST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/right.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/right1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/right3.png";
                else return "src/main/resources/right2.png";
            }
            case NORTHEAST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/upRight.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/upRight1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/upRight3.png";
                else return "src/main/resources/upRight2.png";
            }
            case WEST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/left.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/left1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/left3.png";
                else return "src/main/resources/left2.png";
            }
            case NORTHWEST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/upLeft.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/upLeft1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/upLeft3.png";
                else return "src/main/resources/upLeft2.png";
            }
            case NORTH -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/up.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/up1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/up3.png";
                else return "src/main/resources/up2.png";
            }
            case SOUTHEAST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/downRight.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/downRight1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/downRight3.png";
                else return "src/main/resources/downRight2.png";
            }
            case SOUTHWEST -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/downLeft.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/downLeft1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/downLeft3.png";
                else return "src/main/resources/downLeft2.png";
            }
            case SOUTH -> {
                if (this.energy<(vars.getReadyEnergy()/2))
                    return "src/main/resources/down.png";
                else if (this.energy<vars.getReadyEnergy())
                    return "src/main/resources/down1.png";
                else if (this.energy>2*vars.getReadyEnergy())
                    return "src/main/resources/down3.png";
                else return "src/main/resources/down2.png";
            }
        }
        return null;
    }

    @Override
    public String getLabelText() {
        return "A" + this.getPosition().toString();
    }

    public Animal comparator(Animal b){
        if(b == null)
            return this;
        if (this.energy>b.energy){
            return this;
        }
        else if (this.energy<b.energy){
            return b;
        }
        else if (this.age>b.age){
            return this;
        }
        else if (this.age<b.age){
            return b;
        }
        else if (this.children>b.children){
            return this;
        }
        else return b;

    }

}
