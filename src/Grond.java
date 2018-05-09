package src;

/**
 * Deze klasse zijn de grond objecten die in het landschap zitten en waarover het model zijn pad maakt voor de workers, ook alle objecten baseren hun
 * Y positie op de hoogteGrond uit deze klasse
 * Deze klasse wordt gebruikt door View en landschap.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class Grond 
{
    private GrondRichting richting = GrondRichting.recht; // de richting van de grond is initeel altijd recht
    private int hoogteGrond;    // de hoogte in rooster coordinaten die het grond object inneemt
    private boolean bezet; // staat er al een object boven het grond object?
    
    /**
     * Constructor voor object van Grond de begininstellingen zijn: de grond is niet bezet.
     * 
     * @param hoogteGrond int,  de hoogte van de grond in roostercoordinaten
     * @param richting GrondRichting,   de richting van het grond object
     */
    public Grond(int hoogteGrond,GrondRichting richting)
    {
      this.hoogteGrond = hoogteGrond;
      this.richting = richting;
      bezet = false;
      
    }

    /**
     * Method setRichting veranderd de richting van het grond Object
     *
     * @param r GrondRichting,  de nieuwe richting van de grond
     */
    public void setRichting(GrondRichting r)
    {
        richting = r;
    }
    /**
     * Method getRichting geeft de richting van het Grond object
     *
     * @return richting GrondRichting,  de richting van het Grond object
     */
    public GrondRichting getRichting()
    {
        return richting;
    }
    /**
     * Method getHoogte geeft de grondhoogte in roostercoordinaten
     *
     * @return hoogteGrond int, de hoogte van de grond in roostercoordinaten
     */
    public int getHoogte()
    {
        return hoogteGrond;
    }
     /**
      * Method isBezet zegt of het Grond object bezet
      *
      * @return bezet boolean, is er als een object boven de grond?true of false
      */
     public boolean isBezet()
    {
        return bezet;
    }
     /**
      * Method setBezet verander de status van het grond object of het al bezet is of niet.
      *
      * @param b nieuw object boven de Grond: true, het object boven de grond wordt verwijderd: false
      */
     public void setBezet(boolean b)
     {
        bezet = b;
    }
}

