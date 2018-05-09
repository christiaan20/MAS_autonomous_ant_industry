package src;

import java.util.ArrayList;

/**
 * Deze klasse zorgt voor de beweging van een workers.
 * deze klasse implementeert Runnable
 * Deze klasse wordt gebruikt door MoveThread.
 * 
 * @author Christiaan VAnbergen
 * @version v11
 */
public class Animation implements Runnable
{
    private int coX2;// de x coordinaat waar de workers heen gaat
    private int coY2;// de y coordinaat waar de workers heen gaat
    private Worker w; // de bewegende workers
    private WindowView view; // de Grote view waar alles op getekend gaat worden
    private Model model;    // het model waar de workers in zit
    private int speed;      //hoeveel miliseconden er tussen elke stap van 10 pixel zit
    private Object toObject; // het object waar de workers heen gaat

    private boolean autom; // parameter if true then the workers will move automatically
    /**
     * De conctructor voor objecten van Animation, de speed blijft voor elke beweging van een workers
     * 25*10, dit betekend dat een workers om de 250 miliseconden 10 pixels vooruit gaat.
     * 
     * @param coX2 int, de X coordinaat van het punt waar de workers heen moet gaan
     * @param w int,    de workers die de beweging uitvoert
     * @param view WindowView, De WindowView waar op de beweging tezien zal zijn
     * @param toObject Object, Het object waar de workers naar toe gaat, kan null zijn
     * @param m Model, het model waartoe de workers behoort.
     * 
     * 
     */
    public Animation(int coX2, Worker w, WindowView view, Object toObject, Model m)
    {
        this.coX2 = coX2;
        this.coY2 = coY2;
        model = m;
        this.w= w;
        this.view = view;
        this.toObject = toObject;
        speed = 25*10;

        this.autom = false;
    }

    public Animation(int coX2, int coY2, Worker w, WindowView view, Object toObject, Model m)
    {
        this.coX2 = coX2;
        this.coY2= coY2;
        model = m;
        this.w= w;
        this.view = view;
        this.toObject = toObject;
        speed = 25*10;

        this.autom = false;
    }

    public Animation(Worker w, WindowView view, Object toObject, Model m)
    {
        this.coX2 = 0;
        this.coY2= 0;
        model = m;
        this.w= w;
        this.view = view;
        this.toObject = toObject;
        speed = 25*10;

        this.autom = true;
    }

    
    /**
     * Method run deze methode wordt uit gevoert bij het starten van de MoveThread.
     * Als er op een object is gedrukt zal de workers naar de coordinaat van dit object +20 gaan, bij
     * gebouwen wordt hier nog eerst gecheckt of het gebouw wel af is. op het einde van deze thread
     * wordt de workers aan het object toegevoegt en wordt de gatherThread gestart als het gebouw vol
     * zit zal de workers wachten tot er plaats is in het object.
     * Voor de eigenlijke beweging wordt gekeken of er links of rechts van de workers geklikt is en
     * aan de hand daar van laat men de workers links of rechts bewegen. de beweging stopt wanneer
     * de positie van de workers gelijk of groter is dan de aan geklikte x coordinaat of als de
     * moving parameter van de workers false wordt.
     * op het einde wordt de moving parameter in workers false gezet
     */
    public void run()
    {
        /*try
        {
            w.setMoving(true);
            if(this.autom == true)
                while(true)
                {
                    this.runAutoThread();
                }
            else
            {
                if (toObject != null )
                {
                    if (toObject instanceof Building)
                    {
                        Building gebouw = (Building) toObject;
                        if(gebouw.isAf())
                        {
                            coX2 = toObject.getX();
                            coY2= toObject.getY();
                        }
                    }
                    else
                    {
                        coX2 = toObject.getX();
                        coY2= toObject.getY();
                    }
                }
                System.out.println("start at X " + w.getX() + " Y "+ w.getCoY());
                System.out.println("go to coX2 " + coX2 + " coY2 " + coY2);
                while( w.getX() != coX2 || w.getCoY() != coY2 && (toObject.isWithinObject(coX2,coY2) != true))
                {
                    if(w.isMoving())
                    {
                        //procede to move to the assigned coordinates
                        System.out.println("step X" +w.getX() + "Y " + w.getCoY());
                        w.moveTo(coX2,coY2);
                        //if the unit is an explorer then it will look for objects
                        // w.move();


                        try
                        {
                            Thread.sleep(speed);
                        }
                        catch(InterruptedException e)
                        {
                        }
                    }

                }

                if (toObject != null && w.isMoving())
                {
                    while(toObject.getAantalWerkers() > 5)
                    {
                    }
                    if (toObject instanceof Building)
                    {
                        Building gebouw = (Building) toObject;
                        if(gebouw.isAf())
                        {
                            toObject.addWorker(w);
                            GatherThread g = new GatherThread(w,toObject,model,view);
                        }
                    }
                    else
                    {
                        toObject.addWorker(w);
                    GatherThread g = new GatherThread(w,toObject,model,view);
                }

                }
            }




            w.setMoving(false);
        }
        catch(NullPointerException v)
        {
        }*/
    }

    public void runAutoThread()
    {
        if(w.isMoving())
        {
            //procede to move to the assigned coordinates
            //System.out.println("step X" +w.getX() + "Y " + w.getCoY());

            //move agent
            if(w.move(model.getSizeX(),model.getSizeY()))
            {
                w.removeAllDetectedPhero();
                model.findPheromones(w);
            }


            ArrayList<Object> objects = model.getObjecten();

            for( int i = 0;(i < objects.size()); i++)
            {
                Object obj = objects.get(i);
                if(obj.isWithinObject(w.getX(),w.getCoY()) == true)
                {
                    //this thread relays to the movingpolicy of the agent which will decide what to do with and will return true if it will go inside the object and start a gather thread
                    if(w.foundObject(obj))
                    {
                        obj.addWerker(w);
                        GatherThread g = new GatherThread(w,obj,model,view);
                    }
                }
            }

            //this is commented out when we are working without seperate threads for all of the works
           /*
            try
            {
               Thread.sleep(speed);
            }
            catch(InterruptedException e)
            {
            }
            */
        }
    }

    /*
    public void run()
    {
        try
        {
        w.setMoving(true);
        if (toObject != null )
        {
            if (toObject instanceof Building)
            {
                Building gebouw = (Building) toObject;
                if(gebouw.isAf())
                {
                    coX2 = toObject.getX()*view.getZ() +20;
                }
            }
            else
            {
                coX2 = toObject.getX()*view.getZ()+20;
            }
        }

        if(coX2> w.getX())
        {
            for( int g = w.getX();g  <= coX2; g = g +10)
            {
                if(w.isMoving())
                {

                    w.moveRight();
                    try
                    {
                        Thread.sleep(speed);
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            }
        }

        else
        {
            for( int g = w.getX();g  >= coX2; g = g-10)
            {
                if(w.isMoving())
                {
                    w.moveLeft();
                    try
                    {
                        Thread.sleep(speed);
                    }
                    catch(InterruptedException e)
                    {
                    }
                }

            }
        }
        if (toObject != null && w.isMoving())
        {
            while(toObject.getAantalWerkers() > 5)
            {
            }
            if (toObject instanceof Building)
            {
                Building gebouw = (Building) toObject;
                if(gebouw.isAf())
                {
                    toObject.addWorker(w);
                    GatherThread g = new GatherThread(w,toObject,model,view);  
                }
            }
            else
            {
                toObject.addWorker(w);
                GatherThread g = new GatherThread(w,toObject,model,view);
            }

        }
   
        
        w.setMoving(false);
        }
    catch(NullPointerException v)
    {
    }
    }*/
}
