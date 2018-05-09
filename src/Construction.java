package src;

/**
 * De objecten van deze klassen zijn onderdelen van een gebouw, deze vult altijd 1 vak in het rooster en bestaat uit 3 balken.
 * Wordt door Building gebruikt.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class Construction
{
    private int xPositionBuilding; // de x positie ten opzichte de x positie van het gebouw in  rooster coordinaten
    private int yPositionBuilding; // de y positie tenopzichte de y positie van het gebouw in  rooster coordinaten
    private Resource_types topBeam; // de bovenste balk van de constructie
    private Resource_types leftBeam; // de linker balk van de constructie
    private Resource_types rightBeam; // de rechter balk van de constructie
        
    /**
     * Constructor voor objecten van Construction
     * 
     * @param xPos int, De positie in x richting ten opzichte van de x coordinaat van het gebouw in rooster coordinaten
     * @param yPos int, De positie in y richting ten opzichte van de y coordinaat van het gebouw in rooster coordinaten
     * @param bBalk Resource_types,  de Resource_types waar de bovenste balk uit gemaakt is
     * @param lBalk Resource_types,  de Resource_types waar de rechter balk uit gemaakt is
     * @param rBalk Resource_types,  de Resource_types waar de linker balk uit gemaakt is
     * 
     */
    public Construction(int xPos, int yPos, Resource_types bBalk, Resource_types lBalk, Resource_types rBalk)
    {
        topBeam = bBalk;
        leftBeam = lBalk;
        rightBeam = rBalk;
        xPositionBuilding = xPos;
        yPositionBuilding = yPos;
      
    }

    /**
     * Method setTopBeam als er nog geen bovenbalk is wordt de bovenbalk uit de meegeven Resource_types gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setTopBeam(Resource_types g)
    {
         if(topBeam == null)
         {
             topBeam = g;
            }
    }
    
    /**
     * Method setRightBeam als er nog geen Rechterbalk is wordt de Rechterbalk uit de meegeven Resource_types gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setRightBeam(Resource_types g)
    { 
        if(rightBeam == null)
        {
         rightBeam = g;
        }
    }
    
    /**
     * Method setLeftBeam als er nog geen linkerbalk is wordt de linkerbalk uit de meegeven Resource_types gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setLeftBeam(Resource_types g)
    {
         if(leftBeam == null)
         {
        leftBeam = g;
    }
    }
    
    /**
     * Method getTopBeam geeft de Resource_types waar de bovenste balk uitgemaakt is
     *
     * @return bovenbalk Resource_types,  de Resource_types waar de bovenste balk uitgemaakt is
     */
    public Resource_types getTopBeam()
    {
        return topBeam;
    }

    /**
     * Method getRightBeam geeft de Resource_types waar de rechter balk uitgemaakt is
     *
     * @return bovenbalk Resource_types,  de Resource_types waar de rechter balk uitgemaakt is
     */
    public Resource_types getRightBeam()
    {
        return rightBeam;
    }

    /**
     * Method getLeftBeam geeft de Resource_types waar de linker balk uitgemaakt is
     *
     * @return linkerbalk Resource_types,  de Resource_types waar de linker balk uitgemaakt is
     */
    public Resource_types getLeftBeam()
    {
        return leftBeam;
    }
    
    /**
     * Method getXPositieGebouw geeft de x positie ten opzichte van de x positie van het gebouw
     *
     * @return xPositionBuilding int,  de x positie ten opzichte van de x positie van het gebouw
     */
    public int getXPositieGebouw()
    {
        return xPositionBuilding;
    }
    
    /**
     * Method getXPositieGebouw geeft de y positie ten opzichte van de y positie van het gebouw
     *
     * @return xPositionBuilding int,  de y positie ten opzichte van de y positie van het gebouw
     */
    public int getYPositieGebouw()
    {
        return yPositionBuilding;
    }
    
   

}
