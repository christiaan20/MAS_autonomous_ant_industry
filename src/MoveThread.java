package src;

/**
 * Deze klasse zorgt voor de initiatie van de bewegings animatie van 1 werker van 1 punt naar een ander punt.
 * deze klasse erft Thread
 * wordt gebruikt door de controler en GatherAnimation
 * 
 * @author christiaan Vanbergen
 * @version (a version number or a date)
 */
public class MoveThread extends Thread
{
    private Animation a; // de animatie
    private Thread t; // de thread die de animatie uitvoert

    /**
     * Constructor voor objecten van MoveThread, deze maakt een nieuwe animatie en een nieuwe thread die de
     * animatie uitvoert en start ook de thread.
     * 
     * @param coX2 int, de X coordinaat van het punt waar de werker heen moet gaan
     * @param w int,    de werker die de beweging uitvoert
     * @param view WindowView, De WindowView waar op de beweging tezien zal zijn
     * @param toObject Object, Het object waar de werker naar toe gaat, kan null zijn
     * @param model Model, het model waartoe de werker behoort.
     * 
     */
    public MoveThread(int coX2, Worker w, WindowView view , Object toObject, Model model)
    {
        a = new Animation(coX2,w,view,toObject,model);
        t = new Thread(a);
        t.start();
    }

    public MoveThread(int coX2, int coY2, Worker w, WindowView view , Object toObject, Model model)
    {
        a = new Animation(coX2,coY2,w,view,toObject,model);
        t = new Thread(a);
        t.start();
    }

    public MoveThread(Worker w, WindowView view , Object toObject, Model model)
    {
        a = new Animation(w,view,toObject,model);
        t = new Thread(a);
        t.start();
    }
}
