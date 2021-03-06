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
    private WindowView view;

    /**
     * Constructor voor objecten van MainAnimation
     * 
     * @Param model Model,   het model die door de mainAnimatie gestuurd wordt
     * @Param view View,    de WindowView die door de mainAnimatie gestuurd wordt
     * 
     */
    public MainAnimation(Model m, WindowView v)
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
       int countGather = 0;
       int countGatherThres = 100;
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
                        for(Worker w: model.getWorkers())
                        {
                            w.tick(model);
                           // w.getAnimation().runAutoThread();


                            //System.out.println("added pheromone too visited list?");
                        }
                        view.updateParameters();

                        count = 0;

                    }
                    if(countPreroThreshold <= countPheromon  )
                    {
                        model.tickPheromones(countPreroThreshold*25);
                        model.checkExpiredPheromones();
                        countPheromon = 0;
                    }
                    if(countGatherThres <= countGather)
                    {
                        model.requestObjectenLockBlocking();
                        for(Object o: model.getObjecten())
                        {
                            o.tickGathering(model);

                        }
                        model.releaseObjectenLock();
                        model.verwijderLegeObjecten();
                        countGather = 0;
                    }

                    countGather++;
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
