package src;

/**
 * Deze klasse zorgt voor de initiatie van de MainAnimatie
 * erft van Thread
 * wordt gebruikt in AnEngineersQuest
 * 
 * @author Christiaan Vanbergen
 * @version V11
 */
public class MainThread extends Thread
{
    private MainAnimation a;
    private Thread t;
    
    /**
     * Constructor voor objecten van MainThread een nieuwe MainAnimation wordt gemaakt en ook een
     * nieuwe Thread die de MainAnimation gebruikt, De thread wordt gestart.
     * 
     * @Param model Model,   het model die door de mainAnimatie gestuurd wordt
     * @Param view View,    de GroteView die door de mainAnimatie gestuurd wordt
     */
    public MainThread(Model model,GroteView view)
    {
        a = new MainAnimation(model,view);
        t = new Thread(a);
        t.start();
    }
}


