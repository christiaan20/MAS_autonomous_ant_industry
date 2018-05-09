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
    private GatherAnimation a; // da animatie om een workers te laten verzamelen
    private Thread t;   // de thread die da animatie uitvoert
    
    /**
     * Constructor voor objecten van GatherThread een nieuwe GatherAnimation wordt gemaakt en ook een
     * nieuwe Thread die de GatherAnimation gebruikt, De thread wordt gestart.
     * 
     * @param w int,    de workers die grondstoffen verzameld
     * @param o Object, Het object waar de workers aan het verzamelen is
     * @param model Model, het model waartoe de workers behoort.
     * @param view WindowView, De WindowView  die upgedate moet worden
     * 
     */
    public GatherThread(Worker w, Object o, Model model, WindowView view)
    {
        a = new GatherAnimation(w,o,model,view);
        t = new Thread(a);
        t.start();
    }

}
