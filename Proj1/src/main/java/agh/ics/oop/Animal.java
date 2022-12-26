package agh.ics.oop;

import com.sun.security.auth.NTSid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal extends AbstractMapElement{
    private IWorldMap map;
    private List<IObserver> Observers;

    private int[] genom= new int[Variables.genom];

    private int active_gen=0;
    private MapDirection animalDir;
    private int energy;
    private int children;
    private int age;

    public Animal(IWorldMap map, Vector2d initialPos){
        super(initialPos);
        animalDir = MapDirection.NORTH;
        animalDir = animalDir.new_direction(new Random().nextInt(8));
        Observers = new ArrayList<>();
        this.map = map;
        map.place(this);
        this.energy = Variables.start_energy;
        int rand= new Random().nextInt();
        for (int i=0;i<Variables.genom;i++){
            genom[i]=Math.abs(rand%8);
            rand= new Random().nextInt();
        }
        this.children = 0;
        this.age = 0;
    }

    private int[] getNewGenom(Animal animal1, Animal animal2){
        int[] newGen = new int[Variables.genom];
        int num1 = animal1.energy / (animal1.energy + animal2.energy);
        for (int i = 0; i < num1; i++)
            newGen[i] = animal1.genom[i];
        for(int i = num1; i < Variables.genom; i++)
            newGen[i] = animal2.genom[i];
        return newGen;
    }

    private void mutate(){
        Random rand = new Random();
        int mutationsNum = rand.nextInt (Variables.maxMutations - Variables.minMutations + 1) + Variables.minMutations;
        for(int i = 0; i < mutationsNum; i++){
            genom[rand.nextInt(Variables.genom)] = rand.nextInt(8);
        }
    }


    public Animal(IWorldMap map, Vector2d initialPos, Animal father, Animal mother){
        super(initialPos);
        animalDir = MapDirection.NORTH;
        animalDir = animalDir.new_direction(new Random().nextInt(8));
        Observers = new ArrayList<>();
        this.map = map;
        map.place(this);
        this.energy = Variables.start_energy;
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
        Animal newAnimal = new Animal(map, getPosition(), this, pair);
        children+=1;
        pair.children+=1;
        spendEnergy(Variables.breed_energy);
        pair.spendEnergy(Variables.breed_energy);
        Observers.forEach(observer -> observer.update( new Message("NewAnimal", newAnimal)));
    }

    public void die(){
        Observers.forEach(observer -> observer.update( new Message("Died", this)));
    }

    public boolean isEnoughEnergy(){
        return energy > Variables.ready_energy;
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
        active_gen=(active_gen+1)%Variables.genom;
        animalDir=animalDir.new_direction(genom[active_gen]);
        if(map.canMoveTo(this, getPosition().add(animalDir.toUnitVector()))) {
            positionChanged(getPosition(), getPosition().add(animalDir.toUnitVector()));
            spendEnergy(1);
        }

    }


    public String toString(){
        return animalDir.toString();
    }

    @Override
    public String getImage() {
        switch (this.animalDir){
            case EAST -> {
                return "src/main/resources/right.png";
            }
            case NORTHEAST -> {
                return "src/main/resources/upRight.png";
            }
            case WEST -> {
                return "src/main/resources/left.png";
            }
            case NORTHWEST -> {
                return "src/main/resources/upLeft.png";
            }
            case NORTH -> {
                return "src/main/resources/up.png";
            }
            case SOUTHEAST -> {
                return "src/main/resources/downRight.png";
            }
            case SOUTHWEST -> {
                return "src/main/resources/downLeft.png";
            }
            case SOUTH -> {
                return "src/main/resources/down.png";
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
