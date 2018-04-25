package src;

import java.awt.*;
/**
 * deze klasse is behoort tot het model en creert een coordinaat die een parabool door het spel maakt
 * wat dan een hemellichaam moet voorstellen zon of maan hangt van de dag parameter af, de coordinaten 
 * volgen een rooste met nulpunt links onder en elke pixel is 1 eenheid.
 * wordt door het model gebruikt maar doet geen interactie met andere klasse behalve de MainAnimation.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */
public class HemelLichaam
{
    private double x;   // de x positie het hemellichaam in pixel coordinaten(double om berekeningen te kunnen maken)
    private double y;   // de y positie het hemellichaam in pixel coordinaten(double om berekeningen te kunnen maken)
    private boolean dag; // is het dag= true is het nacht = false
    
    /**
     * Constructor voor objecten van HemelLichaam: begin instellingen zijn: de x coordinaat is -100 en  de y is 0,
     * het is dag.
     */
    public HemelLichaam()
    {
        x = -100; 
        y = 0;
        dag = true;
    }

    /**
     * Method beweegLichaam zolang als de x coordinaat van het hemel lichaam binnen het bord blijft zal de x coordinaat
     * 0,5 stijgen en de y coordinaat f(x) volgen, als hij buiten het bord zit word de x coordinaat 0 en de dag
     * parameter geinverteert en naargelang de kleur van de achtergrond veranderd.
     * 
     *
     * @param model het model waar het hemelLichaam toe behoort 
     * @param view  de Grote View die het hemelLichaam moet tekenen
     */
    public void beweegLichaam(Model model,GroteView view)
    {
        x = x +0.5;
        y = 100+(Math.pow((x-500)/30,2));
        
        if(x>model.getSizeX())
        {
            x = 0;
            
            if(dag)
            {
                dag = false;
                view.setBackground(new Color(25,25,112  ));
            }
            else
            {
                dag = true;
                 view.setBackground(new Color(176,226,255 ));
            }
        }
    }
    
    /**
     * getX Zet de x coordinaat om in een integer en geeft deze terug
     * 
     * @return     e int,   de x coordinaat van het HemelLichaam als integer
     */
    public int getX()
    {

        int e = (int) x;
        return e;
    }
     
    /**
     * Method getY Zet de y coordinaat om in een integer en geeft deze terug
     * 
     * @return     e int,   de y coordinaat van het HemelLichaam als integer
     */
    public int getY()
    {

        int e = (int) y;
        return e;
    }
    
    /**
     * Method isDag zegt of het dag is of niet.
     *
     * @return True: het is dag, False: het is nacht
     */
    public boolean isDag()
    {

        return dag;
    }
}

