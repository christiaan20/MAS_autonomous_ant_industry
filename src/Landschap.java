package src;

import java.util.*;
/**
 * Dit is een klasse die een parameter is in het Model en die zorgt voor het Landschap waar alle grond objecten in zitten en ook het landschap aanmaakt
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class Landschap
{

    private int X ;// aantal delen dat de x-ass verdeelt is
    private int Y; // aantal delen dat de y-ass verdeelt is
    private Grond[] landschap; // een de Array van grond objecten die het landschap op maken 
    private int hoogteGrond; // de hoogte van de grond
    private Random random = new Random(); // Random object om willekeurig helling te generen

    /**
     * Constructor voor objecten van de klasse Landschap met begininstelling: 20 Grondobjecten met een maximum hoogte van 10, de beginhoogte van de Grond  wordt 
     * willekeurig gegenereert tussen 2 en 15, er wordt een niewe array gemaakt met X aantal plaatsen, de methode setLand() wordt gebruikt om het relief te bepalen.
     */
    public Landschap()
    {
        X = 20;
        Y = 10;
        hoogteGrond = random.nextInt(11)+2;
        landschap = new Grond[X];
        this.setLand();

    }

    /**
     * Method setLand gaat voor elke plaats in de landschap array aan veranderHoogte() vragen of de Grond naar omhoog, naar onder of recht moet zijn. bij onder wordt
     * de grond hoogte voor de volgende Grond objecten ook verlaagt met 1 en bij omhoog wordt dit verhoogt met 1, er is een controle ingebouwd zodat er geen 2 verschillende
     * helling komen na elkaar en ook een controle die er voor zorgt dat de grond 2 rooster eenheden van de onderkant en de bovenkant van het scherm blijft.
     *
     */
    public void setLand()
    {
        if( hoogteGrond <= Y)
        {
            GrondRichting vorige = null; // plaatselijke variable die zorgt dat er niet 2 keer achtereen een ander helling komt.
            for(int i=0;i< X;i++)
            {
                GrondRichting r = this.veranderHoogte(); // de richting van de huidig grond
                if (r == GrondRichting.onder && vorige != GrondRichting.boven)
                {
                    if(hoogteGrond-1 >= 2)
                    {
                        hoogteGrond--;
                        vorige = GrondRichting.onder;
                        this.addGrond( new Grond(hoogteGrond,GrondRichting.onder),i);

                    }
                    else
                    {
                        this.addGrond( new Grond(hoogteGrond,GrondRichting.recht),i);
                        vorige = GrondRichting.recht;
                    }
                }
                else if (r==GrondRichting.boven && vorige != GrondRichting.onder)
                {   
                    if(hoogteGrond+1 <= Y)
                    {
                        hoogteGrond++;
                        vorige = GrondRichting.boven;
                        this.addGrond( new Grond(hoogteGrond,GrondRichting.boven),i);

                    }
                    else
                    {
                        this.addGrond( new Grond(hoogteGrond,GrondRichting.recht),i);
                        vorige = GrondRichting.recht;
                    }
                }
                else 
                {
                    this.addGrond( new Grond(hoogteGrond,GrondRichting.recht),i);
                    vorige = GrondRichting.recht;
                }
            }
        }   
    }

    /**
     * Method gethoogteGrond geeft de hoogte van een Grond object op een bepaalde plaats in het landschap
     *
     * @param x int,    De  x coordingaat van de plaats in het landschap waar men de hoogte van de grond wilt weten
     * @return landschap[x].gethoogte() int,    de hoogte van de grond op plaats x
     */
    public int gethoogteGrond(int x)
    {
        return landschap[x].getHoogte();
    }

    /**
     * Method getGrond geeft het object grond op een bepaalde plaats in het landschap
     *
     * @param x int,    de x coordinaat van de plaats in het landschap waar men het Grond object wilt van hebben
     * @return landschap[x] Grond,  het gevraagde grond object
     */
    public Grond getGrond(int x)
    {  
        return landschap[x];      
    }

    

    /**
     * Method addGrond zet een gegeven object grond op een gegeven x coordinaat in het landschap
     *
     * @param a Grond,  het Grond object dat geplaatst moet worden
     * @param x int,     de X coordinaat waar het grond object geplaats moet worden
     */
    public void addGrond ( Grond a, int x)
    {
        landschap[x] = a;
    }

    
    /**
     * Method getX geeft het aantal kolomen in het rooster terug
     *
     * @return X int,   het aantal kolomen in het rooster
     */
    public int getX()
    {
        return X;
    }

    /**
     * Method getY  geeft de maximale hoogte van de grond objecten in rooster eenheden
     *
     * @return Y int,   de maximale hoogte van de grond in rooster eenheden
     */
    public int getY()
    {
        return Y;
    }

    /**
     * Method veranderHoogte genereert een willekeurige Grondrichtings waarde er is 3/10 kans dat het onder of boven is en 4/10 kans dat het rechts
     * Deze methode wordt door setLand() gebruikt om de richting van een Grond object te bepalen.
     *
     * @return r int,   een willekeurige GrondRichting, kan recht, boven of onder zijn
     */
    public GrondRichting veranderHoogte()
    {
        int a = random.nextInt(10);
        GrondRichting r = null;
        if (a <=2 )
        {
            r = GrondRichting.onder;
        }
        else
        {
            if (a >= 7 )
            {
                r = GrondRichting.boven;
            }
            else
            {
                r = GrondRichting.recht;
            }
        }
        return r;
    }

}
