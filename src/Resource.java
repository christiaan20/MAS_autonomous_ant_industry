package src;

import java.util.*;
/**
 * deze klasse is een onderverdeling van Object deze kan grondstoffen geven aan workers
 * erft van object.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */
public class Resource extends Object
{
    // instance variables - replace the example below with your own
    private Resource_types stof; // de soort stof die de grondstoffen bezit
    private int amount;    // hoeveel stof dat in het grondstoffen object aanwezig is
    private Random random;  // een Random object om willekeurige hoeveelheden van stof te genereren
    /**
     * Constructor voor objecten van Resource, begin instellingen zijn: de muis hangt niet over
     * het object, er wordt een nieuw random object aangemaakt en de amount is een willekeurige
     * amount tussen 5 en 20 eenheden, er kunnen 5 workers in het object.
     */
    public Resource(Resource_types stof, int X, int Y)
    {
        this.stof = stof;
        super.hoverOver = false;
        coX = X;
        coY = Y;
        Xsize = 50;
        Ysize = 50;
        random = new Random();
        amount = random.nextInt(50)+5;
        workers = new Worker[5];
    }

    public Resource(Resource_types stof, int X, int Y, int amount)
    {
        this.stof = stof;
        super.hoverOver = false;
        coX = X;
        coY = Y;
        Xsize = 50;
        Ysize = 50;
        random = new Random();
        this.amount = amount;
        workers = new Worker[5];
    }


    @Override
    public void tickGathering(Model model)
    {
        for(Worker w: workers)
        {
            try
            {

                if(w.inStructuur() == true && w.isVol() == false && this.getAmount() > 0 && (this.getStof() == w.getResourcetypes() || w.getResourcetypes() == null || w.getResourcetypes() == Resource_types.explorer))
                {

                    if(this.getStof() == Resource_types.hout)
                    {
                        w.addLading(Resource_types.hout);
                        this.verlaagHoeveelheid();
                    }
                    if(this.getStof() == Resource_types.steen)
                    {
                        w.addLading(Resource_types.steen);
                        this.verlaagHoeveelheid();
                    }
                    if(this.getStof() == Resource_types.voedsel)
                    {
                        w.addLading(Resource_types.voedsel);

                        this.verlaagHoeveelheid();
                    }

                }

                if(w.isVol() == true)
                    w.setInStructure(false);

                if(this.getAmount() <= 0)
                {
                 //   model.verwijderLegeObjecten(this);
                   // w.setCurrentJob(null);
                    w.setInStructure(false);
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

    public Resource_types getStof()
    {
        return stof;
    }
   
    public int getAmount()
    {
        return amount;
    }
    public void verlaagHoeveelheid()
    {
        amount--;
    }
}
    