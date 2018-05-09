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
    private Task topBeam; // de bovenste balk van de constructie
    private Task leftBeam; // de linker balk van de constructie
    private Task rightBeam; // de rechter balk van de constructie
        
    /**
     * Constructor voor objecten van Construction
     * 
     * @param xPos int, De positie in x richting ten opzichte van de x coordinaat van het gebouw in rooster coordinaten
     * @param yPos int, De positie in y richting ten opzichte van de y coordinaat van het gebouw in rooster coordinaten
     * @param bBalk Task,  de Task waar de bovenste balk uit gemaakt is
     * @param lBalk Task,  de Task waar de rechter balk uit gemaakt is
     * @param rBalk Task,  de Task waar de linker balk uit gemaakt is
     * 
     */
    public Construction(int xPos, int yPos, Task bBalk, Task lBalk, Task rBalk)
    {
        topBeam = bBalk;
        leftBeam = lBalk;
        rightBeam = rBalk;
        xPositionBuilding = xPos;
        yPositionBuilding = yPos;
      
    }

    /**
     * Method setTopBeam als er nog geen bovenbalk is wordt de bovenbalk uit de meegeven Task gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setTopBeam(Task g)
    {
         if(topBeam == null)
         {
             topBeam = g;
            }
    }
    
    /**
     * Method setRightBeam als er nog geen Rechterbalk is wordt de Rechterbalk uit de meegeven Task gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setRightBeam(Task g)
    { 
        if(rightBeam == null)
        {
         rightBeam = g;
        }
    }
    
    /**
     * Method setLeftBeam als er nog geen linkerbalk is wordt de linkerbalk uit de meegeven Task gemaakt
     *
     * @param g De grondstof waar de nieuwe balk uit gemaakt moet worden
     */
    public void setLeftBeam(Task g)
    {
         if(leftBeam == null)
         {
        leftBeam = g;
    }
    }
    
    /**
     * Method getTopBeam geeft de Task waar de bovenste balk uitgemaakt is
     *
     * @return bovenbalk Task,  de Task waar de bovenste balk uitgemaakt is
     */
    public Task getTopBeam()
    {
        return topBeam;
    }

    /**
     * Method getRightBeam geeft de Task waar de rechter balk uitgemaakt is
     *
     * @return bovenbalk Task,  de Task waar de rechter balk uitgemaakt is
     */
    public Task getRightBeam()
    {
        return rightBeam;
    }

    /**
     * Method getLeftBeam geeft de Task waar de linker balk uitgemaakt is
     *
     * @return linkerbalk Task,  de Task waar de linker balk uitgemaakt is
     */
    public Task getLeftBeam()
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
