package src;

import java.util.*;
/**
 * deze klasse is een onderverdeling van Object deze kan grondstoffen geven aan werkers 
 * erft van object.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */
public class Grondstoffen extends Object
{
    // instance variables - replace the example below with your own
    private Grondstof stof; // de soort stof die de grondstoffen bezit
    private int hoeveelheid;    // hoeveel stof dat in het grondstoffen object aanwezig is
    private Random random;  // een Random object om willekeurige hoeveelheden van stof te genereren
    /**
     * Constructor voor objecten van Grondstoffen, begin instellingen zijn: de muis hangt niet over 
     * het object, er wordt een nieuw random object aangemaakt en de hoeveelheid is een willekeurige
     * hoeveelheid tussen 5 en 20 eenheden, er kunnen 5 werkers in het object.
     */
    public Grondstoffen(Grondstof stof, int X, int Y)
    {
        this.stof = stof;
        super.hoverOver = false;
        coX = X;
        coY = Y;
        Xsize = 50;
        Ysize = 50;
        random = new Random();
        hoeveelheid = random.nextInt(15)+5;
        werker = new Werker[5];
    }
    public Grondstof getStof()
    {
        return stof;
    }
   
    public int getHoeveelheid()
    {
        return hoeveelheid;
    }
    public void verlaagHoeveelheid()
    {
        hoeveelheid--;
    }
}
    