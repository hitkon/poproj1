package agh.ics.oop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenomTest {
    PortalMap m = new PortalMap(5,5,5);
    Animal rufus= new Animal(m,new Vector2d(1,1));


    @Test
    public void genomprint()

    {
        int genom[] = rufus.show_genom();
        for (int gen: genom){
            System.out.println(gen + " ");}
        Assertions.assertEquals(1, 1);

    }
}
