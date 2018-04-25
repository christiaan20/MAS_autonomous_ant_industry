package src;

import java.util.*;
/**
 * Deze klasse is een onderverdeling van Object en dient ofwel als afdropplaats voor werkers ofwel als verwerkingsgebouwen. In deze versie zullen gebouwen 
 * alleen voor opslag plaats en voor hoofdgebouw gebruikt worden.1 gebouw kan uit meerdere constructies bestaan dat is in deze versie nog niet uitgewerkt.
 * erft van Object.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */

public class  Gebouw extends Object
{
    private Grondstof bovenBalk; // de grondstof waar de bovenste balk uit gemaakt is 
    private Grondstof linkerBalk; // de grondstof waar de linkerbalk uit gemaakt is
    private Grondstof rechterBalk;// de grondstof waar de rechter balk uit gemaakt is
    private Functie functie;    // de functie van het gebouw
    private ArrayList<Constructie> onderdelen = new ArrayList<Constructie>(); // delen waar een gebouw uit bestaat
    private boolean af; // is het gebouw compleet, true of false
    /**
     * Constructor voor objecten van Gebouwn, begin instelling zijn: een nieuw contructie in onderdelen, de muis hangt niet over het gebouw, er kunnen 5 
     * werkers in het gebouw, eerste wordt af op false gezet en daarna wordt er met de methode afcontrole() gecontroleert of het gebouw af is of niet.
     * (geen enkel balk in de 1st constructie is nog null => af = true)
     */
    public Gebouw(int x, int y,Grondstof bBalk, Grondstof lBalk,Grondstof rBalk, Functie functie) 
    {
        onderdelen.add(new Constructie(0,0,bBalk,lBalk,rBalk));
        this.functie = functie;
        coX = x;
        coY = y;
        Xsize = 100;
        Ysize = 100;
        super.hoverOver = false;
        werker = new Werker[5];
        af = false;
        afcontrole();
    }
        

    /**
     * Method afcontrole kijkt of het gebouw af is, door te kijken of alle balken niet null zijn als dit is dan is af = true
     *
     */
    public void afcontrole()
    {
        Constructie con = onderdelen.get(0); // de hoofdconstructie van het gebouw
        if(con.getBovenBalk() != null && con.getRechterBalk() != null && con.getLinkerBalk() != null)
        {
            af = true;
        }

    }

    
    
    /**
     * Method getFunctie geeft de functie van het gebouw
     *
     * @return functie Functie,     de functie van het gebouw
     */
    public Functie getFunctie()
    {
        return functie;
    }

    /**
     * Method getConstructie geeft de 1ste contructie terug
     *
     * @return onderdelen.get(0) Constructie, De eerste constructie
     */
    public Constructie getConstructie()
    {
        return onderdelen.get(0);
    }

    
    /**
     * Method isAf zegt of een gebouw af is of niet;
     *
     * @return af boolean, gebouw is af : true, gebouw is niet af: false
     */
    public boolean isAf()
    {
        return af;

    }

    /**
     * Method setbovenBalk  veranderd het soortmateriaal waaruit de bovenste balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Grondstof,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setbovenBalk(int con, Grondstof g)
    {
        if(af == false)
        {
            onderdelen.get(con).setbovenBalk(g);
            afcontrole();
        }
    }
    /**
     * Method setRechterBalk  veranderd het soort materiaal waaruit de rechter balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Grondstof,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setRechterBalk(int con,Grondstof g)
    {
        if(af == false)
        {
            onderdelen.get(con).setRechterBalk(g);
            afcontrole();
        }
    }
    /**
     * Method setLinkerBalk  veranderd het soortmateriaal waaruit de bovenste balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Grondstof,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setLinkerBalk(int con,Grondstof g)
    {
        if(af == false)
        {
            onderdelen.get(con).setLinkerBalk(g);
            afcontrole();
        }
    }
    /**
     * Method getIterator geeft de iterator om de Constructies in onderdelen door telopen
     *
     * @return onderdelen.iterator Iterator<Constructie>,   de iterator 
     */
    public Iterator<Constructie> getIterator() 
    {
        return onderdelen.iterator();    
    }
}

