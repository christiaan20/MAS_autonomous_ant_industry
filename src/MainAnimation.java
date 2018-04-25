package src;

/**
 * Deze klasse zorgt voor het bewegen van het hemelLichaam en het verversen van de Graphics in de view
 * implementeert Runnable.
 * Wordt door GatherThread gebruikt.
 * 
 * @author Christiaan Vanbergen
 * @version v11
 */
public class MainAnimation implements Runnable
{
    // instance variables - replace the example below with your own
    private Model model;
    private GroteView view;

    /**
     * Constructor voor objecten van MainAnimation
     * 
     * @Param model Model,   het model die door de mainAnimatie gestuurd wordt
     * @Param view View,    de GroteView die door de mainAnimatie gestuurd wordt
     * 
     */
    public MainAnimation(Model m, GroteView v)
    {
       model = m;
       view = v;
    }

    /**
     * Method run doorheen heel het programma wordt om de 25 miliseconden De view hertekend en
     * het hemelLichaam verplaatst.
     *
     */
    public void run()
    {
       int count = 0;
       int countThres = 5;
       int countPheromon = 0;
       int countPreroThreshold = 1;
      while(true)
      {
          
        try
                {
                    view.repaint();
 //                   model.getHemel().beweegLichaam(model,view);
                    //after going throught the normal loop countThres times then the agents are moved
                    if(countThres <= count)
                    {
                        for(Werker w: model.getWerkers())
                        {
                            w.getAnimation().runAutoThread();
                            if(w.dropPheromone())
                                model.createPheromone(w.getX(),w.getCoY(),w.getTask(),w.getPheromonePolicy().getExpireTime());


                        }
                        model.checkExpiredPheromones();
                        count = 0;

                    }
                    if(countPheromon >= countPreroThreshold)
                    {
                         model.tickPheromones(countPreroThreshold*25);
                    }
                    countPheromon++;
                    count++;



                    Thread.sleep(25);
                   // Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                }
            }   
    }
}
