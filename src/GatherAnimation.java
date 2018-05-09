package src;

/**
 * Deze klasse zorgt voor het regelmatig afgeven van grondstoffen van het object naar de Worker of voor het afdroppen van Resource.
 * implementeert Runnable.
 * Wordt door GatherThread gebruikt.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class GatherAnimation implements Runnable
{
    private Worker w;   // de workers die gronstoffen Verzameld
    private Object o;   // het Object waar grondstoffen verzameld worden of afgedropt worden
    private WindowView view; // de WindowView die upgedate moet worden
    private Model model;    // Het model waartoe de workers behoort


    /**
     * Constructor voor Objecten van GatherAnimation
     * 
     * @param w int,    de workers die grondstoffen verzameld
     * @param o Object, Het object waar de workers aan het verzamelen is
     * @param model Model,  het model waartoe de workers behoort.
     * @param view WindowView,   De WindowView  die upgedate moet worden
     * 
     */
    public GatherAnimation(Worker w, Object o, Model model, WindowView view)
    {
        this.w = w;
        this.o = o;
        this.model = model;
        this.view = view;

    }

    /**
     * Method run wanneer het object waar de workers in zit van het type grondstoffen is dan zal er zolang als de workers in de structuur zit, de workers
     * niet vol is, de workers dezelfde type van lading al heeft als het type van het object of de workers geen lading heeft en zolang er nog stoffen in
     * het object zitten. zal de workers om de 2,5 seconden een eenheid van grondstoffen van het object krijgen. na de cyclus wordt er gechecked of het
     * ontgonnen object leeg is of niet , zo ja wordt het verwijderd anders niks. daarna wordt de workers uit het object gezet. als het object een gebouw is
     * met functie hoofhdgebouw of opslag dan zal de lading van de workers bij de juist grondstof worden opgeteld in het model en verliest de workers zijn lading
     * De groteview wordt geupdate en de workers wordt uit het object gezet.
     * Als de workers een huidig werk heeft en als deze in het object zit wat zijn werk is zal een move thread naar het dichtbij zijnde opslag of hoofdgebouw
     * geiniteert worden en als de workers niet in het object van huidig werk zit dan wordt een moveThread naar het huidige werk geïniteert.
     *
     */
    public void run()
    {
        try
        {
        if(o instanceof Resource)
        {
            Resource b = (Resource)o;
            boolean struct = w.inStructuur();
            boolean vol = w.isVol();
            int hoev =  b.getAmount();
            Task grondstof = b.getStof();
            Task task = w.getTask();

            while(w.inStructuur() == true && w.isVol() == false && b.getAmount() > 0 && (b.getStof() == w.getTask() || w.getTask() == null || w.getTask() == Task.explorer))
            {

                if(b.getStof() == Task.hout)
                {
                    w.addLading(Task.hout);
                    b.verlaagHoeveelheid();
                }
                if(b.getStof() == Task.steen)
                {
                    w.addLading(Task.steen);
                    b.verlaagHoeveelheid();
                }
                if(b.getStof() == Task.voedsel)
                {
                    w.addLading(Task.voedsel);

                    b.verlaagHoeveelheid();
                }
                try
                {
                    Thread.sleep(2500);
                }
                catch(InterruptedException e)
                {
                }
            }

            if(b.getAmount() <= 0)
            {
                model.verwijderLegeObjecten();
                w.setCurrentJob(null);
            }

            o.deleteWerker(w);
        
        } 
        else
        {
            Building b = (Building) o;
            if(b.getFunction() == Function.hoofdgebouw || b.getFunction() == Function.opslag)
            {

                try
                {
                    Thread.sleep(2500);
                }
                catch(InterruptedException e)
                {
                }
                if(w.inStructuur())
                {
                    if(w.getTask() == Task.hout)
                    {
                        model.setAmountWood(w.getLoad());
                    }
                    else if(w.getTask() == Task.steen)
                    {
                        model.setAmountStone(w.getLoad());
                    }
                    else if(w.getTask() == Task.voedsel)
                    {
                        model.setAmountFood(w.getLoad());
                    }

                    w.dropLading();
                    view.updateParameters();
                }

                o.deleteWerker(w);


            }

        }
        //make the workers drop a pheromone
        w.setDistLastPheroDrop(w.getPheromonePolicy().getDropDistance()+1);
        //remove all the pheromones that were detected and visited in the way towards
        w.removeAllDetectedPhero();
        w.removeAllVisitedPhero();

        model.findPheromones(w);
        w.setMoving(true);
       /*
        if (w.getCurrentJob() != null)
        {
            if(w.getCurrentJob()== o)
            {
                //Building opslag = model.dichsteOpslag(w);
                Building opslag = model.getHQ();
                MoveThread m = new MoveThread(opslag.getX(),opslag.getY(),w,view,opslag,model);
            }
            else
            {
                MoveThread m = new MoveThread((w.getCurrentJob().getX()),(w.getCurrentJob().getY()),w,view,w.getCurrentJob(),model);
            }
        }
        */
    }
    catch(NullPointerException v)
    {
    }

}


}
