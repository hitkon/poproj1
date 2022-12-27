package agh.ics.oop;

public class Variables implements IVariables{

    public Variables(int map_h, int map_l, int plants, int plant_energy, int plants_growth,
                     int animals, int start_energy, int ready_energy, int breed_energy, int minMutations, int maxMutations, int genom){
        this.map_h = map_h;
        this.map_l = map_l;
        this.plants = plants;
        this.plant_energy = plant_energy;
        this.plants_growth = plants_growth;
        this.animals = animals;
        this.start_energy = start_energy;
        this.ready_energy = ready_energy;
        this.breed_energy = breed_energy;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genom = genom;
    }
    //wysokość i szerokość mapy
    private int map_h=5;
    private int map_l=5;

    //startowa liczba roślin
    private int plants=6;

    //energia zapewniana przez zjedzenie jednej rośliny
    private int plant_energy=4;

    //liczba roślin wyrastająca każdego dnia
    private int plants_growth=2;

    //startowa liczba zwierzaków
    private int animals=4;

    //startowa energia zwierzaków
    private int start_energy=8;

    //energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania)
    private int ready_energy=3;

    //energia rodzica zużywana by stworzyć potomka
    private int breed_energy=1;

    //minimalna i maksymalna liczba mutacji u potomków (może być równa 0),
    private int minMutations=0;
    private int maxMutations=1;

    //długość genomu zwierzaków,
    private int genom=8;


    @Override
    public int getMapH() {
        return map_h;
    }

    @Override
    public int getMapL() {
        return map_l;
    }

    @Override
    public int getPlants() {
        return plants;
    }

    @Override
    public int getPlantEnergy() {
        return plant_energy;
    }

    @Override
    public int getPlantGrowth() {
        return plants_growth;
    }

    @Override
    public int getAnimals() {
        return animals;
    }

    @Override
    public int getStartEnergy() {
        return start_energy;
    }

    @Override
    public int getReadyEnergy() {
        return ready_energy;
    }

    @Override
    public int getBreedEnergy() {
        return breed_energy;
    }

    @Override
    public int getMinMutations() {
        return minMutations;
    }

    @Override
    public int getMaxMutations() {
        return maxMutations;
    }

    @Override
    public int getGenom() {
        return genom;
    }
}
