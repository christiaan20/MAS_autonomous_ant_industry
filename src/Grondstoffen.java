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
        hoeveelheid = random.nextInt(50)+5;
        werkers = new Werker[5];
    }

    public Grondstoffen(Grondstof stof, int X, int Y, int hoeveelheid)
    {
        this.stof = stof;
        super.hoverOver = false;
        coX = X;
        coY = Y;
        Xsize = 50;
        Ysize = 50;
        random = new Random();
        this.hoeveelheid = hoeveelheid;
        werkers = new Werker[5];
    }


    @Override
    public void tickGathering(Model model)
    {
        for(Werker w: werkers)
        {
            try
            {

                if(w.inStructuur() == true && w.isVol() == false && this.getHoeveelheid() > 0 && (this.getStof() == w.getTask() || w.getTask() == null || w.getTask() == Grondstof.explorer))
                {

                    if(this.getStof() == Grondstof.hout)
                    {
                        w.addLading(Grondstof.hout);
                        this.verlaagHoeveelheid();
                    }
                    if(this.getStof() == Grondstof.steen)
                    {
                        w.addLading(Grondstof.steen);
                        this.verlaagHoeveelheid();
                    }
                    if(this.getStof() == Grondstof.voedsel)
                    {
                        w.addLading(Grondstof.voedsel);

                        this.verlaagHoeveelheid();
                    }

                }

                if(w.isVol() == true)
                    w.setInStructuur(false);

                if(this.getHoeveelheid() <= 0)
                {
                 //   model.verwijderLegeObjecten(this);
                   // w.setHuidigWerk(null);
                    w.setInStructuur(false);
                }


                if(w.inStructuur() == false)
                {
                    this.deleteWerker(w);
                    this.atExitOfWorker(w,model);

                }


            }
            catch(NullPointerException v)
            {
            }
        }

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
    