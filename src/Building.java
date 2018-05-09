package src;

import java.util.*;
/**
 * Deze klasse is een onderverdeling van Object en dient ofwel als afdropplaats voor workers ofwel als verwerkingsgebouwen. In deze versie zullen gebouwen
 * alleen voor opslag plaats en voor hoofdgebouw gebruikt worden.1 gebouw kan uit meerdere constructies bestaan dat is in deze versie nog niet uitgewerkt.
 * erft van Object.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */

public class Building extends Object
{
    private Resource_types bovenBalk; // de grondstof waar de bovenste balk uit gemaakt is
    private Resource_types linkerBalk; // de grondstof waar de linkerbalk uit gemaakt is
    private Resource_types rechterBalk;// de grondstof waar de rechter balk uit gemaakt is
    private Function function;    // de function van het gebouw
    private ArrayList<Construction> onderdelen = new ArrayList<Construction>(); // delen waar een gebouw uit bestaat
    private boolean af; // is het gebouw compleet, true of false
    /**
     * Constructor voor objecten van Gebouwn, begin instelling zijn: een nieuw contructie in onderdelen, de muis hangt niet over het gebouw, er kunnen 5 
     * workers in het gebouw, eerste wordt af op false gezet en daarna wordt er met de methode afcontrole() gecontroleert of het gebouw af is of niet.
     * (geen enkel balk in de 1st constructie is nog null => af = true)
     */
    public Building(int x, int y, Resource_types bBalk, Resource_types lBalk, Resource_types rBalk, Function function)
    {
        onderdelen.add(new Construction(0,0,bBalk,lBalk,rBalk));
        this.function = function;
        coX = x;
        coY = y;
        Xsize = 100;
        Ysize = 100;
        super.hoverOver = false;
        workers = new Worker[5];
        af = false;
        afcontrole();
    }

    @Override
    public void tickGathering(Model model)
    {
        try
        {
            for(Worker w: workers)
            {
                if(this.getFunction() == Function.hoofdgebouw || this.getFunction() == Function.opslag)
                {

                    if(w.inStructuur())
                    {
                        if(w.getResourcetypes() == Resource_types.hout)
                        {
                            model.setAmountWood(w.getLoad());
                        }
                        else if(w.getResourcetypes() == Resource_types.steen)
                        {
                            model.setAmountStone(w.getLoad());
                        }
                        else if(w.getResourcetypes() == Resource_types.voedsel)
                        {
                            model.setAmountFood(w.getLoad());
                        }

                        w.dropLading();

                    }
                    this.deleteWerker(w);


                }


                //make the workers drop a pheromone
                w.setDistLastPheroDrop(w.getPheromonePolicy().getDropDistance()+1);
                //remove all the pheromones that were detected and visited in the way towards
                w.removeAllDetectedPhero();
                w.removeAllVisitedPhero();

                model.findPheromones(w);
                w.setMoving(true);
            }

        }
        catch(NullPointerException v)
        {
        }
    }


    /**
     * Method afcontrole kijkt of het gebouw af is, door te kijken of alle balken niet null zijn als dit is dan is af = true
     *
     */
    public void afcontrole()
    {
        Construction con = onderdelen.get(0); // de hoofdconstructie van het gebouw
        if(con.getTopBeam() != null && con.getRightBeam() != null && con.getLeftBeam() != null)
        {
            af = true;
        }

    }

    
    
    /**
     * Method getFunction geeft de function van het gebouw
     *
     * @return function Function,     de function van het gebouw
     */
    public Function getFunction()
    {
        return function;
    }

    /**
     * Method getConstructie geeft de 1ste contructie terug
     *
     * @return onderdelen.get(0) Construction, De eerste constructie
     */
    public Construction getConstructie()
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
     * Method setTopBeam  veranderd het soortmateriaal waaruit de bovenste balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Resource_types,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setbovenBalk(int con, Resource_types g)
    {
        if(af == false)
        {
            onderdelen.get(con).setTopBeam(g);
            afcontrole();
        }
    }
    /**
     * Method setRightBeam  veranderd het soort materiaal waaruit de rechter balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Resource_types,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setRechterBalk(int con, Resource_types g)
    {
        if(af == false)
        {
            onderdelen.get(con).setRightBeam(g);
            afcontrole();
        }
    }
    /**
     * Method setLeftBeam  veranderd het soortmateriaal waaruit de bovenste balk gemaakt is van een gekozen construcite maar enkel als het gebouw nog niet af is
     * na het toevoegen wordt eer gecontroleert of het gebouw af is met afcontrole()
     *
     * @param con Int,   de positie van de constructie in de ArrayList
     * @param g Resource_types,  Het soort grondstof waaruit de balk gemaakt wordt
     */
    public void setLinkerBalk(int con, Resource_types g)
    {
        if(af == false)
        {
            onderdelen.get(con).setLeftBeam(g);
            afcontrole();
        }
    }
    /**
     * Method getIterator geeft de iterator om de Constructies in onderdelen door telopen
     *
     * @return onderdelen.iterator Iterator<Construction>,   de iterator
     */
    public Iterator<Construction> getIterator()
    {
        return onderdelen.iterator();    
    }
}

