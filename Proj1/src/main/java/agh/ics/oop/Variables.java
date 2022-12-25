package agh.ics.oop;

public class Variables {

    //wysokość i szerokość mapy
    public static int map_h=5;
    public static int map_l=5;

    //startowa liczba roślin
    public static int plants=4;

    //energia zapewniana przez zjedzenie jednej rośliny
    public static int plant_energy=4;

    //liczba roślin wyrastająca każdego dnia
    public static int plants_growth=1;

    //startowa liczba zwierzaków
    public static int animals=3;

    //startowa energia zwierzaków
    public static int start_energy=30;

    //energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania)
    public static int ready_energy=3;

    //energia rodzica zużywana by stworzyć potomka
    public static int breed_energy=1;

    //minimalna i maksymalna liczba mutacji u potomków (może być równa 0),
    public static int minMutations=0;
    public static int maxMutations=1;

    //długość genomu zwierzaków,
    public static int genom=8;


}
