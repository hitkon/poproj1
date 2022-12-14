package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class MapCell {
    public boolean isGrassHere(){
        return grassHere;
    }
    public void setGrassHere(){
        grassHere = true;
    }
    private boolean grassHere;
    private List<Animal> Animals;
    private int x,y;
    private IVariables vars;
    public MapCell(int x, int y, IVariables vars){
        this.vars =  vars;
        this.x =x;
        this.y= y;
        Animals = new ArrayList<>();
        grassHere = false;
    }
    public boolean isAnimalsHere(){
        return !Animals.isEmpty();
    }
    public Animal getTopAnimal(){
        if(Animals.isEmpty())
            return null;
        Animal ret = Animals.get(0);
        for(int i = 1; i < Animals.size(); i++)
            ret = ret.comparator(Animals.get(i));
        return ret;
    }

    public Object getTopObject(){
        if(Animals.isEmpty() && grassHere)
            return new Grass(new Vector2d(x,y));
        if(!Animals.isEmpty())
            return getTopAnimal();
        return null;
    }

    public String toString(){
        if(!Animals.isEmpty())
            return getTopAnimal().toString();
        return grassHere ? new Grass(new Vector2d(x,y)).toString() : "";
    }
    public String getImage(){
        if(!Animals.isEmpty())
            return getTopAnimal().getImage();
        return grassHere ? new Grass(new Vector2d(x,y)).getImage() : "";
    }
    public String getLabelText(){
        if(!Animals.isEmpty())
            return getTopAnimal().getLabelText();
        return grassHere ? new Grass(new Vector2d(x,y)).getLabelText() : "";
    }
    public void addAnimal(Animal animal){
        Animals.add(animal);
    }
    public void removeAnimal(Animal animal){
        Animals.remove(animal);
    }
    public boolean dayRutine(){
        boolean grass_eaten=false;
        if(grassHere && !Animals.isEmpty()){
            getTopAnimal().addEnergy(vars.getPlantEnergy());
            grassHere = false;
            grass_eaten=true;
        }
        if(Animals.size()>=2){
            Animal animal = getTopAnimal();
            Animal pair =
                    animal != Animals.get(0) ? Animals.get(0) : Animals.get(1);
            for(int i = 1; i < Animals.size(); i++)
                if(animal != Animals.get(i))
                    pair = pair.comparator(Animals.get(i));
            if(animal.isEnoughEnergy() && pair.isEnoughEnergy())
                animal.makeNewAnimal(pair);
        }
        return grass_eaten;
    }

    public int all_energy(){
        int sum=0;
        for (Animal animal: Animals)
            sum+=animal.getEnergy();
        return sum;
    }
}
