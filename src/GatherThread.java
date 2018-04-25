package src;

/**
 * Deze klasse zorgt voor de initiatie van de GatherAnimatie.
 * erft van Thread
 * wordt gebruikt in MoveAnimation.
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */
public class GatherThread extends Thread
{
    private GatherAnimation a; // da animatie om een werker te laten verzamelen
    private Thread t;   // de thread die da animatie uitvoert
    
    /**
     * Constructor voor objecten van GatherThread een nieuwe GatherAnimation wordt gemaakt en ook een
     * nieuwe Thread die de GatherAnimation gebruikt, De thread wordt gestart.
     * 
     * @param w int,    de werker die grondstoffen verzameld
     * @param o Object, Het object waar de werker aan het verzamelen is
     * @param model Model, het model waartoe de werker behoort.
     * @param view GroteView, De GroteView  die upgedate moet worden
     * 
     */
    public GatherThread(Werker w, Object o, Model model,GroteView view)
    {
        a = new GatherAnimation(w,o,model,view);
        t = new Thread(a);
        t.start();
    }

}
