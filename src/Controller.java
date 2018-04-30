package src;

import MAS_classes.MovePolicyBasic;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * De controller die het hele spel coordineert, Deze klasse gebruikt de klasse: Model, GroteView, Werker, Object,Panel.
 * Deze wordt aangemaakt bij het aanmaken van een game object.
 * Deze klasse zal zorgen dat de dat de juiste werkers of objecten een parameter veranderd wanneer de muis er
 * over hangt of er op geklikt wordt en dat de juiste handelingen worden uitgevoerdt bij het bewegen van werkers.
 * Er wordt een MouseListener en een MouseMotionListener geimplementeert
 * 
 * 
 * 
 * @author Christiaan Vanbergen 
 * @version V9
 */
public class Controller implements MouseListener, MouseMotionListener
{
    // instance variables - replace the example below with your own
    private Model model;
    private GroteView view;

    //private int schermVerplaatsing;
    private Panel panel;

    /**
     * Constructor voor objecten van controller, 
     * deze klasse voegt zichzelf toe aan de view als MouseListener en als MouseMotionListener
     * 
     * 
     * @param m Model       Dit is het model dat gemaakt wordt in de game contructor
     * @param v GroteView   Dit is de GroteView die gemaakt wordt in de game constructor
     */
    public Controller(Model m, GroteView v)
    {
        model = m;
        view = v;
        view.addMouseListener(this);
        view.addMouseMotionListener(this);

        //set the object of MAS system in the environment
        model.set_MAS_Objects(v);
    }

    /**
     * Method mouseClicked activeert wanneer er geklikt wordt op eender welke knop op de muis wordt niet gebruikt in deze applicatie
     *
     * @param e A parameter
     */
    public void mouseClicked(MouseEvent e)
    {
    }

    /**
     * Method mousePressed activeert wanneer er geklikt wordt op eender welke knop op de muis 
     * voert de juiste acties uit wanneer er geklikt over een object of werkers of lege plaats rekening houdend met de bouwmode, eventueel alreeds geselecteerde object,eventueel alreeds geselecteerde werkers.
     * De bepaling van welk object of welke werkers er word aangeklikt wordt gedaan foor te controleren of een parameter in de werkers of Object true is.
     * in bouwmode zal deze methode al dan niet gebouwen aanmaken of balken toevoegen aan gebouwen.
     * Hij zal de geselecteerde werkers beweging laten doen en in sommige gevallen een verzamelcyclus initialiseren.
     * Pas op als de werkers aan al aan het bewegen is en de Rechtermuis knop wordt in gedrukt zal de werkers stoppen met bewegen om een nieuwe bewegen te doen
     * moet er nog een eens op rechtermuis knop worden geduwd
     * 
     * @param e MouseEvent een object die informatie bevat over waar de muis heeft geklikt, in deze methode wordt alleen de x en y positie gebruikt hieruit
     */
    public void mousePressed(MouseEvent e)
    {
        System.out.println(e.getX() + " , " + e.getY());
        boolean hovering = false;
        int Z = view.getZ();
        if(e.getButton()== 1 )
        {
            /*
            Grond grond = model.getLandschap().getGrond(e.getX()/50);
            if(model.isBouwMode())
            {
                if(grond.isBezet() == false && grond.getRichting()== GrondRichting.recht)
                {
                    model.addObject(new Gebouw(e.getX()/50,grond.getHoogte()+1,null,null,null,Functie.opslag));
                }
            }
            */

            java.util.Iterator<Object> iterator=model.getIterator2();
            while (iterator.hasNext()) 
            {
                Object o = iterator.next();
                if(model.isBouwMode())
                {
                    if( o instanceof Gebouw)
                    {
                        Gebouw b = (Gebouw) o;
                        if (e.getX()> b.getX()*Z && e.getX()<b.getX()*Z +15 && e.getY()< view.getSizeY()-((b.getY()-1)*Z+90) && e.getY()> view.getSizeY()-(b.getY()*Z-15+90))
                        {
                            if(model.getGrondstofmode() == Grondstof.hout)
                            {
                                if(model.getHoeveelheidHout() -5 >= 0)
                                {
                                    b.setLinkerBalk(0,Grondstof.hout);
                                    model.setHoeveelheidHout(-5);
                                    view.updateParameters();
                                }
                            }
                            else if (model.getGrondstofmode() == Grondstof.steen)
                            {
                                if(model.getHoeveelheidSteen()-5 > 0)
                                {
                                    b.setLinkerBalk(0,Grondstof.steen);
                                    model.setHoeveelheidSteen(-5);
                                    view.updateParameters();
                                }
                            }
                        }
                        else if (e.getX()> (b.getX()+1)*Z -15 && e.getX()<(b.getX()+1)*Z && e.getY()< view.getSizeY()-((b.getY()-1)*Z+90) && e.getY()> view.getSizeY()-(b.getY()*Z-15+90))
                        {
                            if(model.getGrondstofmode() == Grondstof.hout)
                            {
                                if(model.getHoeveelheidHout() -5 >= 0)
                                {

                                    b.setRechterBalk(0,Grondstof.hout);
                                    model.setHoeveelheidHout(-5);
                                    view.updateParameters();
                                }
                            }
                            else if (model.getGrondstofmode() == Grondstof.steen)
                            {
                                if(model.getHoeveelheidSteen()-5 >= 0)
                                {
                                    b.setRechterBalk(0,Grondstof.steen);
                                    model.setHoeveelheidSteen(-5);
                                    view.updateParameters();
                                }
                            }
                        }
                        else if (e.getX()>(b.getX())*Z  && e.getX()<(b.getX()+1)*Z && e.getY()< view.getSizeY()-((b.getY())*Z-15+90) && e.getY()> view.getSizeY()-(b.getY()*Z+90))
                        {
                            if(model.getGrondstofmode() == Grondstof.hout)
                            {
                                if(model.getHoeveelheidHout() -10 >= 0)
                                {
                                    b.setbovenBalk(0,Grondstof.hout);
                                    model.setHoeveelheidHout(-10);
                                    view.updateParameters();
                                }
                            }
                            else if (model.getGrondstofmode() == Grondstof.steen)
                            {
                                if(model.getHoeveelheidSteen() -10 >= 0)
                                {
                                    b.setbovenBalk(0,Grondstof.steen);
                                    model.setHoeveelheidSteen(-10);
                                    view.updateParameters();
                                }
                            }
                        }
                    }
                }
                else 
                {
                    if(o.getHoverOver()&& model.isActive() == false)
                    {
                        if(model.getSelectedObject() != o && model.getSelectedObject() != null)
                        {
                            model.getSelectedObject().select(false);
                        }
                        o.select(true);
                        model.setSelectedObject(o);
                    }
                    else if(model.getSelectedObject() == o)
                    {
                        o.select(false);
                        model.setSelectedObject(null);

                    }

                }
            }
        }
        if(model.isBouwMode() ==false)
        {
            java.util.Iterator<Werker> iterator2=model.getIterator();
            while (iterator2.hasNext()) 
            {
                Werker w = iterator2.next();
                hovering = w.getHoverOver() ;
            }
            java.util.Iterator<Werker> iterator3=model.getIterator();
            while (iterator3.hasNext()) 
            {
                Werker w = iterator3.next();
                int coX2 = e.getX();
                int coY2 = view.getSizeY() - e.getY();
                int coX1 = w.getX();
                if(w.getHoverOver()== true && e.getButton()== 1)
                {
                    if (model.getSelectedWerker() == w || model.getSelectedWerker() == null)
                    {
                        w.select(true);
                        model.setSelectedWerker(w);
                    }
                    else
                    {
                        model.getSelectedWerker().select(false);
                        w.select(true);
                        model.setSelectedWerker(w);
                    }
                }
                else
                {
                    if(e.getButton() == 1)
                    {
                        w.select(false);
                          model.setSelectedWerker(null);
                    }
                }

                if (w.isSelected() ==true && hovering == false && e.getButton()== 3)
                {
                    if(w.isMoving() == false)
                    {
                        if(w.inStructuur())
                        {
                            w.setInStructuur(false);
                        }

                        if(model.getHoveringObject() != null)
                        {

                            if(model.getHoveringObject() instanceof Gebouw)
                            {
                                Gebouw g = (Gebouw) model.getHoveringObject();
                                if(g.getFunctie() == Functie.hoofdgebouw || g.getFunctie() == Functie.opslag)
                                {
                                }
                                else
                                {
                                    w.setHuidigWerk(g);
                                }
                            }
                            else
                            {
                                w.setHuidigWerk(model.getHoveringObject());
                            }
                            Object object = model.getHoveringObject();
                            coX2 = object.getX();
                            coY2 = object.getY();
                            System.out.println("movethread to building activated");
                            //MoveThread m = new MoveThread(coX2,coY2 ,w,view,object,model);
                            MoveThread m = new MoveThread(w,view,object,model);
                        }
                        else
                        {
                            w.setHuidigWerk(null);
                            //MoveThread m = new MoveThread(coX2,coY2 ,w,view,null,model);
                            MoveThread m = new MoveThread(w,view,null,model);
                        }
                    } 
                    else
                    {
                        w.getMovePolicy().setTarget_x(e.getX());
                        w.getMovePolicy().setTarget_y(model.getSizeY()-e.getY());
                        w.setCurrDirection(w.getAngleToPoint(e.getX(),view.getSizeY()-e.getY()));
                        //w.setHuidigWerk(null);
                        //w.setMoving(false);
                    }
                }

            }
        }

    }

    /**
     * Method mouseReleased activeert wanneer eenderwelke muisknop losgelaten wordt wordt niet gebruikt in deze applicatie.
     *
     * @param e A parameter
     */
    public void mouseReleased(MouseEvent e)
    {
    }

    /**
     * Method mouseEntered activeert wanneer de cursor het scherm binnen komt wordt niet gebruikt in deze applicatie.
     *
     * @param e A parameter
     */
    public void mouseEntered(MouseEvent e)
    {
    }

    /**
     * Method mouseExited activeert wanneer de cursor het scherm verlaat wordt niet gebruikt in deze applicatie.
     *
     * @param e A parameter
     */
    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * Method mouseMoved activeert telkens als de muis van positie veranderd
     * gaat na of de muis boven een werkers of een object hangt door middel van de methodes: overObject en overWerker
     *
     * @param e MouseEvent  een object die informatie bevat over waar de muis heeft geklikt, in deze methode wordt alleen de x en y positie gebruikt hieruit
     */
    public void mouseMoved(MouseEvent e)
    {
        view.getView().setMouseX(e.getX());
        view.getView().setMouseY(e.getY());

        //nteger[] werkers = model.getWerkers().toArray(new Integer[model.getWerkers().size()]);

        try
        {
            //for(int i = 0;i<werkers.length;i++)
            java.util.Iterator<Werker> iterator=model.getIterator();
            while (iterator.hasNext())
            {
                Werker w = iterator.next();
                overWerker(e,30,30,w);
            }
        }
        catch(java.util.ConcurrentModificationException ex)
        {

        }



        //Integer[] objecte = model.getObjecten().toArray(new Integer[model.getObjecten().size()]);
        //for(int i = 0;i<werkers.length;i++)
        try
        {
            java.util.Iterator<Object> iterator1 = model.getIterator2();
            while (iterator1.hasNext()) {
                Object o = iterator1.next();


                if (o instanceof Grondstoffen) {
                    Grondstoffen b = (Grondstoffen) o;
                    if (b.getStof() == Grondstof.hout) {
                        overObject(e, 50, 100, b, 2);
                    } else if (b.getStof() == Grondstof.steen) {
                        overObject(e, 50, 50, b, 1);
                    } else if (b.getStof() == Grondstof.voedsel) {
                        overObject(e, 50, 50, b, 1);
                    }
                } else {
                    if (o instanceof Gebouw) {
                        Gebouw b = (Gebouw) o;

                        overObject(e, 50, 50, b, 1);

                    }

                }
            }

        }
        catch(java.util.ConcurrentModificationException ex)
        {

        }

    }

    /**
     * Method mouseExited activeert wanneer een muisknop wordt ingedrukt en dan de muis beweegt wordt niet gebruikt in deze applicatie.
     *
     * @param e A parameter
     */
    public void mouseDragged(MouseEvent e)
    {
    }

    /**
     * Method overObject wordt gebruikt door de mousePressed methode uit deze klasse om op te vervragen of de muis boven een object hangt of niet 
     * zo ja als het opbject nog wordt overgehangen of er geen werkers voor staat dan wordt er parameter hoverover in het betreffende object true en hoveringObject in model wordt het object o
     * zo nee als het opbject wordt overgehange is wordt er parameter hoverover in het betreffende object false en hoveringObject in model wordt null
     * 
     * @param e De MouseEvent die bij de mousePressed gegeven werdt
     * @param grootteX de grote van het object waarop geklikt wordt in de x richting
     * @param grootteY de grote van het object waarop geklikt wordt in de y richting
     * @param o Het Object waar de test op gedaan moet worden
     * @param hoogte de hoogte in roostercoordinaten
     */
    public void overObject (MouseEvent e, int grootteX, int grootteY, Object o,int hoogte)
    {
        //if(  e.getX() >= (o.getX()) && e.getX() <= (o.getX())+grootteX && e.getY()>= view.getSizeY()-((o.getY())+ (grootteY-50)+90) && e.getY() <= view.getSizeY()-(o.getY())-40)
        if(o.isWithinObject(e.getX(),view.getSizeY()-e.getY()))
        {

            if (o.getHoverOver() == false && model.isActive() == false)
            {
                o.HoverOver(true);
                //view.repaint();
                model.setHoveringObject(o);
            }
        }
        else
        {
            if (o.getHoverOver()== true)
            {
                o.HoverOver(false);
                //view.repaint();
                model.setHoveringObject(null);
            }
        }
    }

    /**
     * Method overWerker wordt gebruikt door de mousePressed methode uit deze klasse om op te vervragen of de muis boven een bepaalde werkers hangt of niet
     * zo ja als de werkers nog niet wordt overgehangen of er geen werkers voor staat en de werkers niet in een structuur zit
     * dan wordt er parameter hoverOver in het betreffende werkers true en active in model wordt het true
     * zo nee als het werkers nog wordt overgehangen is wordt er parameter hoverOver in het betreffende  werkers false en active in model wordt false
     * 
     * @param e De MouseEvent die bij de mousePressed gegeven werdt
     * @param grootteX de grote van de werkers waarop geklikt wordt in de x richting
     * @param grootteY de grote van de werkers waarop geklikt wordt in de y richting
     * @param w de Werker waar de test op gedaan moet worden
     */
    public void overWerker (MouseEvent e,int grootteX, int grootteY, Werker w)
    {
    
        if(  e.getX() >= (w.getX()-15) && e.getX() <= w.getX()-15 + grootteX && (view.getSizeY()- e.getY())>= w.getCoY()-15 && (view.getSizeY()-e.getY()) <= w.getCoY() - 15 + grootteY)
        {
            if (w.getHoverOver() == false && model.isActive() == false  && w.inStructuur() == false)
            {
                model.setActive(true);
                w.HoverOver(true);
            }
        }
        else
        {
            if (w.getHoverOver()== true)
            {
                model.setActive(false);
                w.HoverOver(false);
            }
        }
             

    }

    public void startWerkers()
    {
        ArrayList<Werker> werkers = model.getWerkers();
        for(int i=0;i<model.getSizeWerkers();i++)
        {
            Werker w = werkers.get(i);
            w.setMovePolicy(new MovePolicyBasic(w,50));
            MoveThread m = new MoveThread(w,view,null,model);

        }

    }

}
