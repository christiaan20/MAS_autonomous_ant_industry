package src;

/**
 * Deze klasse is een verzamel klasse voor objecten die in het spel voorkomen en die daar een workers
 * kunnen betreden worden.
 * Deze klasse wordt door model,GatherThread,MainThread, GatherAnimation en MoveAnimation gebruikt
 * 
 * @author Christiaan Vanbergen
 * @version V11f
 */
public abstract class Object
{
    protected  boolean hoverOver; // hangt de muis boven dit object? true of false
    protected  boolean selected;    // is dit object geselecteerd? true of false
    protected  int coX; // de X coordinaat van het Object
    protected  int coY; // de Y coordinaat van het Object
    protected  int Xsize; // NEW the size of the object in X direction
    protected  int Ysize; // NEW the size of the object in Y direction
    protected Worker[] workers;  // een array van workers die in het object aanwezig zijn
    protected int aantalWerkers;    // het aantal workers in het object

    public void tickGathering(Model model)
    {

    }

    public void atExitOfWorker(Worker w, Model model)
    {
        //make the workers drop a pheromone
        w.setDistLastPheroDrop(w.getPheromonePolicy().getDropDistance()+1);
        //remove all the pheromones that were detected and visited in the way towards
        w.removeAllDetectedPhero();
        w.removeAllVisitedPhero();

        model.findPheromones(w);
        w.setMoving(true);
    }



    /**
     * Method HoverOver veranderd de status van het object of het overhangen wordt door de muis of niet
     *
     * @param a boolean,    de muis komt over het object: true, de muis gaat van het object af: false
     */
    public void HoverOver(boolean a)
    {
        hoverOver = a;
    }

    /**
     * Method getHoverOver geeft of het object overhangen wordt door de muis of niet
     *
     * @return hoverover boolean,   true= muis hangt erover,false: muis hangt er niet over
     */
    public boolean getHoverOver()
    {
        return hoverOver;
    }

    /**
     * Method isSelected geeft of het object geselecteerd is of niet
     *
     * @return selected boolean,    true: is geselecteerd,false; is niet geselecteerd
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * Method select veranderd de status van het object of het geselecteer is of niet
     *
     * @param a boolean, wordt geselecteer:true, wordt gedeselecteerd: false.
     */
    public void select(boolean a)
    {
        selected =  a;
    }

    /**
     * Method getX geeft de x positie van het object in roosterCoordinaten
     *
     * @return coX int, de x coordinaat van het object
     */
    public int getX ()
    {
        return coX;
    }
    
    /**
     * Method getY geeft de y positie van het object
     *
     * @return coY int, de y coordinaat van het object
     */
    public int getY ()
    {
        return coY;
    }

    /**
     * Method addWorker voegt een workers toe in de eerst beschikbare plaats en zet de status van die workers
     * op in structuur.
     *
     * @param w Worker, De workers die wordt toegevoegt
     */
    public void  addWerker(Worker w)
    {
        if(aantalWerkers < 5)
        {
            boolean geplaatst = false; // plaatselijke parameter die zegt of de workers al gepaatst is of niet
            for(int i=0; i<5;i++)
            {
                
                if( geplaatst == false)
                {
                    if(workers[i] == null)
                    {
                        workers[i] = w;
                        geplaatst = true;
                    }
                }
            }
            aantalWerkers++;
            w.setInStructure(true);
        }
    }

    /**
     * Method deleteWerker verwijderd een workers uit het object, zet de status van deze workers op uit structuur
     *
     * @param w Worker, de te verwijderen workers.
     */
    public void deleteWerker(Worker w)
    {
        if(aantalWerkers > 0)
        {
            for(int i=0; i<5;i++)
            {
                if(workers[i] == w)
                {
                    workers[i] = null;
                }
            }
            w.setInStructure(false);
            aantalWerkers--;
        }
    }
    
    /**
     * Method getAantalWerkers geeft het aantal workers dat in een object zit
     *
     * @return aantalWerker int,    het aantal workers in het object
     */
    public int getAantalWerkers()
    {
        return aantalWerkers;
    }


    /**
     * Method isWithinObject returns whether given coordinates fall within the borders of this object
     *
     *
     * @param x The x-coordinates to be checked (starting from the left edge of the field/screen)
     * @param y The y-coordinates to be checked (STARTING from the bottom edge of the fiels/screen, default screen coordinates are from the top down => needs to be converted before inputting here)
     */
    public boolean isWithinObject(int x, int y)
    {
        if( x > coX-Xsize/2 &&
            x < coX+Xsize/2 &&
            y > coY-Ysize/2 &&
            y < coY+Ysize/2 )
            return true;
        else
            return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Object object = o;

        if (coX != object.coX) return false;
        if (coY != object.coY) return false;
        if (getXsize() != object.getXsize()) return false;
        return getYsize() == object.getYsize();
    }

    @Override
    public int hashCode() {
        int result = coX;
        result = 31 * result + coY;
        result = 31 * result + Xsize;
        result = 31 * result + Ysize;
        return result;
    }

    public int getXsize() {
        return Xsize;
    }

    public void setXsize(int xsize) {
        Xsize = xsize;
    }

    public int getYsize() {
        return Ysize;
    }

    public void setYsize(int ysize) {
        Ysize = ysize;
    }
}
