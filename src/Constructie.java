package src;

/**
 * De objecten van deze klassen zijn onderdelen van een gebouw, deze vult altijd 1 vak in het rooster en bestaat uit 3 balken.
 * Wordt door Gebouw gebruikt.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class Constructie 
{
    private int xPositieGebouw; // de x positie ten opzichte de x positie van het gebouw in  rooster coordinaten
    private int yPositieGebouw; // de y positie tenopzichte de y positie van het gebouw in  rooster coordinaten
    private Grondstof bovenBalk; // de bovenste balk van de constructie
    private Grondstof linkerBalk; // de linker balk van de constructie
    private Grondstof rechterBalk; // de rechter balk van de constructie 
        
    /**
     * Constructor voor objecten van Constructie 
     * 
     * @param xPos int, De positie in x richting ten opzichte van de x coordinaat van het gebouw in rooster coordinaten
     * @param yPos int, De positie in y richting ten opzichte van de y coordinaat van het gebouw in rooster coordinaten
     * @param bBalk Grondstof,  de Grondstof waar de bovenste balk uit gemaakt is
     * @param lBalk Grondstof,  de Grondstof waar de rechter balk uit gemaakt is
     * @param rBalk Grondstof,  de Grondstof waar de linker balk uit gemaakt is
     * 
     */
    public Constructie(int xPos, int yPos,Grondstof bBalk, Grondstof lBalk,Grondstof rBalk) 
    {
        bovenBalk = bBalk;
        linkerBalk = lBalk;
        rechterBalk = rBalk;
        xPositieGebouw = xPos;
        yPositieGebouw = yPos;
      
    }

    /**
     * Method setbovenBalk als er nog geen bovenbalk is wordt de bovenbalk uit de meegeven Grondstof gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setbovenBalk(Grondstof g)
    {
         if(bovenBalk == null)
         {
             bovenBalk = g;
            }
    }
    
    /**
     * Method setRechterBalk als er nog geen Rechterbalk is wordt de Rechterbalk uit de meegeven Grondstof gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setRechterBalk(Grondstof g)
    { 
        if(rechterBalk == null)
        {
         rechterBalk = g;
        }
    }
    
    /**
     * Method setLinkerBalk als er nog geen linkerbalk is wordt de linkerbalk uit de meegeven Grondstof gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setLinkerBalk(Grondstof g)
    {
         if(linkerBalk == null)
         {
        linkerBalk = g;
    }
    }
    
    /**
     * Method getBovenBalk geeft de Grondstof waar de bovenste balk uitgemaakt is
     *
     * @return bovenbalk Grondstof,  de Grondstof waar de bovenste balk uitgemaakt is
     */
    public Grondstof getBovenBalk()
    {
        return bovenBalk;
    }

    /**
     * Method getRechterBalk geeft de Grondstof waar de rechter balk uitgemaakt is
     *
     * @return bovenbalk Grondstof,  de Grondstof waar de rechter balk uitgemaakt is
     */
    public Grondstof getRechterBalk()
    {
        return rechterBalk;
    }

    /**
     * Method getLinkerBalk geeft de Grondstof waar de linker balk uitgemaakt is
     *
     * @return linkerbalk Grondstof,  de Grondstof waar de linker balk uitgemaakt is
     */
    public Grondstof getLinkerBalk()
    {
        return linkerBalk;
    }
    
    /**
     * Method getXPositieGebouw geeft de x positie ten opzichte van de x positie van het gebouw
     *
     * @return xPositieGebouw int,  de x positie ten opzichte van de x positie van het gebouw
     */
    public int getXPositieGebouw()
    {
        return xPositieGebouw;
    }
    
    /**
     * Method getXPositieGebouw geeft de y positie ten opzichte van de y positie van het gebouw
     *
     * @return xPositieGebouw int,  de y positie ten opzichte van de y positie van het gebouw
     */
    public int getYPositieGebouw()
    {
        return yPositieGebouw;
    }
    
   

}
