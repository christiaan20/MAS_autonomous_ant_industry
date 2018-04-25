package src;

/**
 * Deze klasse zorgt voor het regelmatig afgeven van grondstoffen van het object naar de Werker of voor het afdroppen van Grondstoffen.
 * implementeert Runnable.
 * Wordt door GatherThread gebruikt.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class GatherAnimation implements Runnable
{
    private Werker w;   // de werker die gronstoffen Verzameld
    private Object o;   // het Object waar grondstoffen verzameld worden of afgedropt worden
    private GroteView view; // de GroteView die upgedate moet worden
    private Model model;    // Het model waartoe de werker behoort


    /**
     * Constructor voor Objecten van GatherAnimation
     * 
     * @param w int,    de werker die grondstoffen verzameld
     * @param o Object, Het object waar de werker aan het verzamelen is
     * @param model Model,  het model waartoe de werker behoort.
     * @param view GroteView,   De GroteView  die upgedate moet worden
     * 
     */
    public GatherAnimation(Werker w, Object o,Model model, GroteView view)
    {
        this.w = w;
        this.o = o;
        this.model = model;
        this.view = view;

    }

    /**
     * Method run wanneer het object waar de werker in zit van het type grondstoffen is dan zal er zolang als de werker in de structuur zit, de werker
     * niet vol is, de werker dezelfde type van lading al heeft als het type van het object of de werker geen lading heeft en zolang er nog stoffen in 
     * het object zitten. zal de werker om de 2,5 seconden een eenheid van grondstoffen van het object krijgen. na de cyclus wordt er gechecked of het
     * ontgonnen object leeg is of niet , zo ja wordt het verwijderd anders niks. daarna wordt de werker uit het object gezet. als het object een gebouw is
     * met functie hoofhdgebouw of opslag dan zal de lading van de werker bij de juist grondstof worden opgeteld in het model en verliest de werker zijn lading
     * De groteview wordt geupdate en de werker wordt uit het object gezet.
     * Als de werker een huidig werk heeft en als deze in het object zit wat zijn werk is zal een move thread naar het dichtbij zijnde opslag of hoofdgebouw 
     * geiniteert worden en als de werker niet in het object van huidig werk zit dan wordt een moveThread naar het huidige werk geïniteert.
     *
     */
    public void run()
    {
        try
        {
        if(o instanceof Grondstoffen)
        {
            Grondstoffen b = (Grondstoffen)o;
            boolean struct = w.inStructuur();
            boolean vol = w.isVol();
            int hoev =  b.getHoeveelheid();
            Grondstof grondstof = b.getStof();
            Grondstof task = w.getTask();

            while(w.inStructuur() == true && w.isVol() == false && b.getHoeveelheid() > 0 && (b.getStof() == w.getTask() || w.getTask() == null || w.getTask() == Grondstof.explorer))
            {

                if(b.getStof() == Grondstof.hout)
                {
                    w.addLading(Grondstof.hout);
                    b.verlaagHoeveelheid();
                }
                if(b.getStof() == Grondstof.steen)
                {
                    w.addLading(Grondstof.steen);
                    b.verlaagHoeveelheid();
                }
                if(b.getStof() == Grondstof.voedsel)
                {
                    w.addLading(Grondstof.voedsel);

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

            if(b.getHoeveelheid() <= 0)
            {
                model.verwijderLegeObjecten(o);
                w.setHuidigWerk(null);
            }

            o.deleteWerker(w);
        
        } 
        else
        {
            Gebouw b = (Gebouw) o;
            if(b.getFunctie() == Functie.hoofdgebouw || b.getFunctie() == Functie.opslag)
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
                    if(w.getTask() == Grondstof.hout)
                    {
                        model.setHoeveelheidHout(w.getLading());
                    }
                    else if(w.getTask() == Grondstof.steen)
                    {
                        model.setHoeveelheidSteen(w.getLading());
                    }
                    else if(w.getTask() == Grondstof.voedsel)
                    {
                        model.setHoeveelheidVoedsel(w.getLading());
                    }

                    w.dropLading();
                    view.updateParameters();
                }
                o.deleteWerker(w);


            }

        }
            w.setMoving(true);
       /*
        if (w.getHuidigWerk() != null)
        {
            if(w.getHuidigWerk()== o)
            {
                //Gebouw opslag = model.dichsteOpslag(w);
                Gebouw opslag = model.getHQ();
                MoveThread m = new MoveThread(opslag.getX(),opslag.getY(),w,view,opslag,model);
            }
            else
            {
                MoveThread m = new MoveThread((w.getHuidigWerk().getX()),(w.getHuidigWerk().getY()),w,view,w.getHuidigWerk(),model);
            }
        }
        */
    }
    catch(NullPointerException v)
    {
    }

}


}
