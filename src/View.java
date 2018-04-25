package src;

import MAS_classes.Pheromone;

import java.awt.*;
import java.lang.*;

/**
 * De view teken alles wat er in het model staat op zo een manier dat het overzichtelijk is en elk object herkenbaar is. hierin wordt het pad waar de werkers
 * overlopen gemaakt.
 * De belangrijkste methodes zijn paint() en teken().
 * De GroteView gebruikt deze klasse en maakt ook een object van deze klasse in zijn constructor.
 * Deze klasse erft van Canvas
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class View extends Canvas
{
    private Model model; // het model dat de view moet tekenen
    private int Z ;// de lengte van de zijden van 1 rasterveld
    private int sizeX; // de grootte van het scherm is x richting
    private int sizeY; // de grootte van het scherm is y richting
    private int xVerschuiving; // het aantal pixels dat het landschap naar links verschoven is om mooier in het beeld te passen
    private int yVerschuiving; // het aantal pixels dat het landschap naar boven verschoven is om mooier in het beeld te passen
    private Image buffer; // een image die als buffer dient voor een snelle repaint.
    private int[] pad = new int[1600]; // het pad waar de werkers overlopen

    private int mouseX;
    private int mouseY;

    /**
     * Constructor voor een object View, de begininstellingen zijn: de grote van het scherm in x en y richiting worden uit het model gehaald, de Z is standaard 50
     * de verschuiving in x richting is 0 en in y richting is 90 pixel
     */
    public View(Model model)
    {
        this.model = model;
        sizeX = model.getSizeX();
        sizeY = model.getSizeY();
        Z =50;
        xVerschuiving = 0;
        yVerschuiving = 90;
    //    maakPad();
    }

    /**
     * Method maakPad Deze methode gaat alle Grond objecten af en vult ,naar gelang de parameters van elke Grond object, de volgende 50 posities van de pad array 
     * met de juiste y waardes zodat de werker lijkt over het landschap te lopen. wordt door de contructor van dit object gebruikt
     *
     */
    public void maakPad()
    {
        Landschap landschap = model.getLandschap();
        for(int i=0;i< landschap.getX();i++)
        {

            if (landschap.getGrond(i) == null)
            {
            }
            else
            {
                Grond b = landschap.getGrond(i);

                if (b.getRichting() == GrondRichting.onder)
                {
                    for(int k=0;k<50;k++)
                    {
                        int c = (i*50+k);
                        pad[c]= sizeY-(b.getHoogte()*50+175-k);
                    }
                }
                else
                {
                    if(b.getRichting() == GrondRichting.boven)
                    {
                        for(int k=0;k<50;k++)
                        {
                            int c = (i*50+k);
                            pad[c]= sizeY-(b.getHoogte()*50+77+k);
                        }
                    }
                    else
                    {
                        for(int k=0;k<50;k++)
                        {
                            int c = (i*50+k);
                            pad[c]= sizeY-(b.getHoogte()*50+120);
                        }
                    }

                }
            }
        }

    }

    /**
     * Method getPadWaarde geeft de y waarde die bij een x waarde hoort. wordt door de controler en view gebruikt
     *
     * @param x int    de x waarde waar de y waarde van geweten wil worden tussen 0 en landschap.getX()*50
     * @return pad[x] int   de y waarde die bij x hoort tussen 70-770  
     */
    public int getPadWaarde( int x)
    {
        return pad[x];
    }

    /**
     * Method paint als er nog geen buffer is dan wordt die gemaakt en anders wordt op de bestaande buffer de nieuw beelden getekend en deze worden op het scherm gezet.
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void paint(Graphics g) 
    {
        if (buffer == null) 
        {
            buffer = createImage(getWidth(), getHeight());
        }
        teken(buffer.getGraphics());
        g.drawImage(buffer,0,0,this);

    }


    /**
     * Method update een herdefinitie van de update methode zodat deze alleen maar teken en niks wist.
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void update(Graphics g) 
    {
        paint(g);
    }  

    /**
     * Method teken eerst wordt heel het scherm leeg gemaakt en dan wordt langs een bepaalde volgorde de verschillend elementen van het model getekend
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public  void teken(Graphics g)
    {
        g.clearRect(0, 0, sizeX, sizeY);
//        tekenHemel(g);
        //paintObjecten(g);
        paint_MAS_Objecten(g);
   //     paintLand(g);
        paintWerker(g);
        tekenMouseCoordinates(g);
 //       tekenHulp(g);


    }

    /**
     * Method paintWerker gaat alle werkers uit het model af en tekend ze als ze niet in een structuur zitten, de geselecteerde  werker wordt deze rood gekleurd,
     * en wordt er boven de werker zijn naam, hoeveelheid lading en lading type weergegeven. als de muis boven de werker hangt wordt er een rechthoek rond de werker getekend
     * als de hulp in model aan is zal er ondersteunend tekst boven een werker komen
     * 
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public  void paintWerker(Graphics g)
    {


        java.util.Iterator<Werker> iterator=model.getIterator();
        while (iterator.hasNext()) 
        {
            Werker w = iterator.next();
            int size = w.getSize();
            if(w.inStructuur()== false)
            {
                //int coYW =pad[w.getX()];
                int coYW = sizeY- w.getCoY();
                if(w.isSelected()== true)
                {
                    g.setColor(Color.red);
                    g.drawString(w.getNaam(), w.getX()-10, coYW - size/2  - 5);
                    g.drawString(String.valueOf(w.getLading()), w.getX()-10, coYW - size/2  - 15);
                    if(w.getTask()!= null)
                    {
                        g.drawString(String.valueOf(w.getTask()), w.getX()-10, coYW- size/2 - 25);
                    }
                    String point = '(' + String.valueOf(w.getX()) + ',' + String.valueOf(w.getCoY()) + ')';
                    g.drawString(point, w.getX()-10, coYW - size/2 -35);

                    String count = "(" + String.valueOf(w.getDetectedPheromones().size()) + ") pheros";
                    g.drawString(count, w.getX()-10, coYW - size/2 -45);

                    if(w.getCurrPheromone() != null)
                    {
                        String currPheropoint = '(' + String.valueOf(w.getCurrPheromone().getX()) + ',' + String.valueOf(w.getCurrPheromone().getY()) + ')';
                        g.drawString(currPheropoint, w.getX() - 10, coYW - size / 2 - 55);

                        g.setColor(Color.pink);
                        int pointSize = 7;
                        g.fillOval(w.getCurrPheromone().getX()-pointSize/2, sizeY-w.getCurrPheromone().getY()-pointSize/2, pointSize, pointSize);
                    }
                }
                else
                {
                    if(model.isHulp())
                    {
                        g.setColor(Color.blue);
                        g.drawString("klik linkermuis knop", w.getX()-10, coYW-size/2 - 25);
                        g.drawString("om te selecteren", w.getX()-10, coYW-size/2 - 10);
                    }
                    g.setColor(Color.black);
                }
                if (w.getHoverOver() == true)
                {
                    g.drawRect(w.getX()-size/2, coYW-size/2, size, size);
                }
                g.fillOval(w.getX()-size/2, coYW-size/2, size, size);
                g.setColor(Color.black);
            }
        }
    }

    /**
     * Method paintLand overal waar een grond object is wordt een zwarte balk met daarboven een kleinere groene balk getekend in de methode kan de tredegrootte
     * aangepast worden hierdoor zal de trap bij het boven of onder gaan ruwer worden
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void paintLand(Graphics g)
    {
        for(int i=0;i< model.getLandschap().getX();i++)
        {
            int TredeGrootte = 1; // hoeveel pixels 1 trede is als het land naar boven of onder gaat
            if(model.getLandschap().getGrond(i)== null)
            {

            }
            else
            {

                Grond b = model.getLandschap().getGrond(i);

                if(b.getRichting()== GrondRichting.onder)
                {
                    for(int d= 0; d*TredeGrootte<Z;d++)
                    {
                        g.setColor(Color.green);

                        g.fillRect(-xVerschuiving+(i*Z)+d*TredeGrootte,sizeY-yVerschuiving-((b.getHoogte()+1)*Z)+d*TredeGrootte , TredeGrootte, 50-d*TredeGrootte);
                    }
                    g.setColor(Color.black);
                    g.fillRect(-xVerschuiving+(i*Z),sizeY-yVerschuiving-((b.getHoogte())*Z) , Z, (b.getHoogte()+1)*Z+90);
                }
                else
                {
                    if(b.getRichting()== GrondRichting.boven)
                    {

                        for(int d= 0; d*TredeGrootte<Z;d++)
                        {
                            g.setColor(Color.green);

                            g.fillRect(-xVerschuiving+(i*Z)+d*TredeGrootte,sizeY-yVerschuiving-((b.getHoogte()-1)*Z)-d*TredeGrootte ,TredeGrootte,d*TredeGrootte);
                        }
                        g.setColor(Color.black);
                        g.fillRect(-xVerschuiving+(i*Z),sizeY-yVerschuiving-(b.getHoogte()-1)*Z,Z,(b.getHoogte())*Z+40 );
                    }
                    else
                    {
                        g.setColor(Color.green);
                        g.fillRect(-xVerschuiving+(i*Z),sizeY-yVerschuiving-(b.getHoogte()*Z) , Z, 20);
                        g.setColor(Color.black);
                        g.fillRect(-xVerschuiving+(i*Z),sizeY-yVerschuiving-(b.getHoogte()*Z)+20 , Z, (b.getHoogte())*Z+90);

                    }
                }
                g.setColor(Color.black);
            }

        }
    }

    /**
     * Method paintObjecten gaat alle objecten af en teken ze naar gelang hun type en positie,het geselecteerde  object wordt rood gekleurd en bij grondstoffen
     * wordt de nog aanwezige hoeveelheid in de grondstoffen weergeven, als de muis boven het object hangt wordt er een rechthoek rond het object getekend.
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void paint_MAS_Objecten(Graphics g)
    {

        Object[] ObjectenArr = new Object[model.getObjecten().size()];
        ObjectenArr = model.getObjecten().toArray(ObjectenArr);

        //java.util.Iterator<Object> iterator=model.getIterator2();
        //while (iterator.hasNext())
        for(Object o: ObjectenArr)
        {

            //Object o = iterator.next();


            if (o instanceof Grondstoffen) {
                Grondstoffen b = (Grondstoffen) o;
                int x = b.getX(); // de x coordinaat van b
                int y = b.getY(); // de y coordinaat van b

                if (b.getStof() == Grondstof.hout) {
                    if (b.isSelected() == true) {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x + 20, sizeY - (y + 1) - 5);
                        g.drawString("hout", x + 15, sizeY - y - 20);
                    } else {
                        g.setColor(new Color(139, 90, 43));
                    }
                    g.fillRect(((x)), sizeY - y, 14, 50);
                    g.fillRect(((x) + 18), sizeY - y, 14, 50);
                    g.fillRect(((x)) + 35, sizeY - y, 14, 50);
                    g.setColor(Color.green);
                    g.fillOval(((x)) - 15, sizeY - y + 1, 44, 50);
                    g.fillOval(((x)) + 3, sizeY - y + 1, 44, 50);
                    g.fillOval(((x)) + 20, sizeY - y + 1, 44, 50);
                    if (b.getHoverOver() == true) {
                        g.drawRect(x, sizeY - y + 1, 50, 100);
                    }
                } else if (b.getStof() == Grondstof.steen) {
                    int objSizeX = b.getXsize();
                    int objSizeY = b.getYsize();

                    if (b.isSelected() == true) {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x + 20 - objSizeX / 2, sizeY - y - 5 - objSizeY / 2);
                        g.drawString("Steen", x + 9 - objSizeX / 2, sizeY - y - 20 - objSizeY / 2);
                    } else {
                        g.setColor(Color.gray);

                    }
                    g.fillOval(x - objSizeX / 2, sizeY - y - b.getYsize() / 2, objSizeX, objSizeY);
                    //System.out.println("x " + (x) + "y " + y);
                    //System.out.println("x " + (x) + "y " + (sizeY-y));

                    if (b.getHoverOver() == true) {
                        g.drawRect(x - objSizeX / 2, sizeY - (y) - b.getYsize() / 2, objSizeX, objSizeY);
                    }
                } else if (b.getStof() == Grondstof.voedsel) {
                    if (b.isSelected() == true) {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x + 20, sizeY - y - 5);
                        g.drawString("voedsel", x + 5, sizeY - y - 20);
                    } else {
                        g.setColor(Color.green);
                    }

                    g.fillOval(x, sizeY - y + 35, 20, 20);
                    g.fillOval(x + 10, sizeY - y + 20, 30, 50);
                    g.fillOval(x + 30, sizeY - y + 30, 20, 30);
                    g.setColor(Color.RED);
                    g.fillOval(x + 10, sizeY - y + 40, 5, 5);
                    g.fillOval(x + 25, sizeY - y + 35, 5, 5);
                    g.fillOval(x + 28, sizeY - y + 28, 5, 5);
                    g.fillOval(x + 40, sizeY - y + 43, 5, 5);
                    g.fillOval(x + 20, sizeY - y + 25, 5, 5);
                    g.fillOval(x + 19, sizeY - y + 45, 5, 5);
                    if (b.getHoverOver() == true) {
                        g.drawRect(x, sizeY - (y), 50, 50);
                    }

                }

                g.setColor(Color.black);

            } else if (o instanceof Pheromone) {
                Pheromone p = (Pheromone) o;

                int objXsize = p.getXsize();
                int objYsize = p.getXsize();
                int x = p.getX();
                int y = p.getY();

                if (p.getTask() == Grondstof.steen) {
                    g.setColor(Color.GRAY);
                } else if (p.getTask() == Grondstof.explorer) {
                    g.setColor(Color.BLUE);

                }
                g.fillOval(x - objXsize / 2, sizeY - y - objYsize / 2, objXsize, objYsize);
            } else {
                Gebouw b = (Gebouw) o;
                int x = b.getX(); // de x coordinaat van b
                int y = b.getY();
                java.util.Iterator<Constructie> iterator1 = b.getIterator();
                while (iterator1.hasNext()) {
                    Constructie c = iterator1.next();
                    if (c != null) {
                        int xPos = c.getXPositieGebouw();
                        int yPos = c.getYPositieGebouw();
                        if (b.isSelected() == true) {
                            g.setColor(Color.red);

                        }

                        if (c.getBovenBalk() == null) {
                            if (model.isBouwMode()) {
                                g.drawRect((x + xPos), sizeY - (y + yPos), 50, 15);
                            }
                        } else {
                            if (b.isSelected() == true) {
                                g.setColor(Color.red);
                                if (b.getFunctie() == Functie.hoofdgebouw) {

                                    g.fillRoundRect((b.getX()) + 15, sizeY - (b.getY()) - 10, 20, 20, 3, 3);

                                }
                            } else if (c.getBovenBalk() == Grondstof.hout) {
                                if (b.getFunctie() == Functie.hoofdgebouw) {
                                    g.setColor(Color.GRAY);
                                    g.fillRoundRect((b.getX()) + 15, sizeY - (b.getY()) - 10, 20, 20, 3, 3);

                                }
                                g.setColor(new Color(139, 90, 43));
                            } else {
                                if (b.getFunctie() == Functie.hoofdgebouw) {
                                    g.setColor(new Color(139, 90, 43));
                                    g.fillRoundRect((b.getX()) + 15, sizeY - (b.getY()) - 10, 20, 20, 3, 3);

                                }
                                g.setColor(Color.gray);
                            }
                            g.fillRect((x + xPos), sizeY - (y + yPos), 50, 15);
                        }
                        if (c.getRechterBalk() == null) {
                            if (model.isBouwMode()) {
                                g.drawRect((x + xPos) + 35, sizeY - (y + yPos) + 15, 15, 35);
                            }
                        } else {
                            if (c.getRechterBalk() == Grondstof.hout) {
                                g.setColor(new Color(139, 90, 43));
                            } else {
                                g.setColor(Color.gray);

                            }
                            g.fillRect((x + xPos) + 35, sizeY - (y + yPos) + 15, 15, 35);
                        }
                        if (c.getLinkerBalk() == null) {
                            if (model.isBouwMode()) {
                                g.drawRect((x + xPos), sizeY - (y + yPos) + 15, 15, 35);
                            }
                        } else {
                            if (c.getLinkerBalk() == Grondstof.hout) {
                                g.setColor(new Color(139, 90, 43));
                            } else {
                                g.setColor(Color.gray);

                            }
                            g.fillRect((x + xPos), sizeY - (y + yPos) + 15, 15, 35);
                        }

                        if (b.getHoverOver() == true) {
                            g.setColor(Color.BLACK);
                            g.drawRect((x + xPos), sizeY - (y + yPos), 50, 50);
                        }

                    }
                }

            }

        }

    }

    /**
     * Method paintObjecten gaat alle objecten af en teken ze naar gelang hun type en positie,het geselecteerde  object wordt rood gekleurd en bij grondstoffen
     * wordt de nog aanwezige hoeveelheid in de grondstoffen weergeven, als de muis boven het object hangt wordt er een rechthoek rond het object getekend.
     *
     * @param g Graphics, de grafische beelden die aangepast worden
     */
    public void paintObjecten(Graphics g)
    {

        java.util.Iterator<Object> iterator=model.getIterator2();
        while (iterator.hasNext())
        {
            Object o = iterator.next();
            if (o instanceof Grondstoffen)
            {
                Grondstoffen b = (Grondstoffen)o;
                int x = b.getX(); // de x coordinaat van b
                int y = b.getY(); // de y coordinaat van b

                if(b.getStof() == Grondstof.hout)
                {
                    if(b.isSelected()== true)
                    {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x+20,sizeY-((y+1)*Z)-yVerschuiving-5);
                        g.drawString("hout", x+15,sizeY-((y+1)*Z)-yVerschuiving-20);
                    }
                    else
                    {
                        g.setColor(new Color(139,90,43 ));
                    }
                    g.fillRect((Z*(x))-xVerschuiving, sizeY-(y)*Z-yVerschuiving, 14, 50);
                    g.fillRect((Z*(x)-xVerschuiving+18), sizeY-(y)*Z-yVerschuiving, 14, 50);
                    g.fillRect((Z*(x))-xVerschuiving+35, sizeY-(y)*Z-yVerschuiving, 14, 50);
                    g.setColor(Color.green);
                    g.fillOval((Z*(x))-xVerschuiving-15,sizeY-(y+1)*Z-yVerschuiving , 44, 50);
                    g.fillOval((Z*(x))-xVerschuiving+3,sizeY-(y+1)*Z-yVerschuiving, 44, 50);
                    g.fillOval((Z*(x))-xVerschuiving+20,sizeY-(y+1)*Z-yVerschuiving, 44, 50);
                    if (b.getHoverOver() == true)
                    {
                        g.drawRect(x,sizeY-(y+1)*Z-yVerschuiving, 50, 100);
                    }
                }
                else if(b.getStof() == Grondstof.steen)
                {
                    if(b.isSelected()== true)
                    {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x+20,sizeY-((y)*Z)-yVerschuiving-5);
                        g.drawString("Steen", x+9,sizeY-((y)*Z)-yVerschuiving-20);
                    }
                    else
                    {
                        g.setColor(Color.gray);

                    }
                    g.fillOval(x,sizeY-((y)*Z)-yVerschuiving, 50, 75);
                    if (b.getHoverOver() == true)
                    {
                        g.drawRect(x,sizeY-(y)*50-yVerschuiving , 50, 50);
                    }
                }
                else if(b.getStof() == Grondstof.voedsel)
                {
                    if(b.isSelected()== true)
                    {
                        g.setColor(Color.red);
                        g.drawString(String.valueOf(b.getHoeveelheid()), x+20,sizeY-((y)*Z)-yVerschuiving-5);
                        g.drawString("voedsel", x+5,sizeY-((y)*Z)-yVerschuiving-20);
                    }
                    else
                    {
                        g.setColor(Color.green);
                    }

                    g.fillOval(x,sizeY-((y)*Z)-yVerschuiving+35, 20, 20);
                    g.fillOval(x+10,sizeY-((y)*Z)-yVerschuiving+20, 30, 50);
                    g.fillOval(x+30,sizeY-((y)*Z)-yVerschuiving+30, 20, 30);
                    g.setColor(Color.RED);
                    g.fillOval(x+10,sizeY-((y)*Z)-yVerschuiving+40, 5,5);
                    g.fillOval(x+25,sizeY-((y)*Z)-yVerschuiving+35, 5,5);
                    g.fillOval(x+28,sizeY-((y)*Z)-yVerschuiving+28, 5,5);
                    g.fillOval(x+40,sizeY-((y)*Z)-yVerschuiving+43, 5,5);
                    g.fillOval(x+20,sizeY-((y)*Z)-yVerschuiving+25, 5,5);
                    g.fillOval(x+19,sizeY-((y)*Z)-yVerschuiving+45, 5,5);
                    if (b.getHoverOver() == true)
                    {
                        g.drawRect(x*Z-xVerschuiving,sizeY-(y)*50-yVerschuiving , 50, 50);
                    }

                }

                g.setColor(Color.black);

            }
            else
            {
                Gebouw b = (Gebouw)o;
                int x = b.getX(); // de x coordinaat van b
                int y = b.getY();
                java.util.Iterator<Constructie> iterator1=b.getIterator();
                while (iterator1.hasNext())
                {
                    Constructie c = iterator1.next();
                    if(c != null)
                    {
                        int xPos = c.getXPositieGebouw();
                        int yPos = c.getYPositieGebouw();
                        if(b.isSelected()== true)
                        {
                            g.setColor(Color.red);

                        }

                        if(c.getBovenBalk() == null)
                        {
                            if (model.isBouwMode())
                            {
                                g.drawRect((x+xPos)*Z-xVerschuiving,sizeY-(y+yPos)*Z-yVerschuiving , 50, 15);
                            }
                        }
                        else
                        {
                            if(b.isSelected()== true)
                            {
                                g.setColor(Color.red);
                                if(b.getFunctie() == Functie.hoofdgebouw)
                                {

                                    g.fillRoundRect((b.getX())*Z-xVerschuiving+15, sizeY-(b.getY())*50-yVerschuiving-10, 20, 20, 3, 3);

                                }
                            }
                            else if(c.getBovenBalk() == Grondstof.hout)
                            {
                                if(b.getFunctie() == Functie.hoofdgebouw)
                                {
                                    g.setColor(Color.GRAY);
                                    g.fillRoundRect((b.getX())*Z-xVerschuiving+15, sizeY-(b.getY())*50-yVerschuiving-10, 20, 20, 3, 3);

                                }
                                g.setColor(new Color(139,90,43 ));
                            }
                            else
                            {
                                if(b.getFunctie() == Functie.hoofdgebouw)
                                {
                                    g.setColor(new Color(139,90,43 ));
                                    g.fillRoundRect((b.getX())*Z-xVerschuiving+15, sizeY-(b.getY())*50-yVerschuiving-10, 20, 20, 3, 3);

                                }
                                g.setColor(Color.gray);
                            }
                            g.fillRect((x+xPos)*Z-xVerschuiving,sizeY-(y+yPos)*Z-yVerschuiving , 50, 15);
                        }
                        if(c.getRechterBalk() == null)
                        {
                            if (model.isBouwMode())
                            {
                                g.drawRect((x+xPos)*Z-xVerschuiving+35,sizeY-(y+yPos)*Z-yVerschuiving+15 , 15, 35);
                            }
                        }
                        else
                        {
                            if(c.getRechterBalk() == Grondstof.hout)
                            {
                                g.setColor(new Color(139,90,43 ));
                            }

                            else
                            {
                                g.setColor(Color.gray);

                            }
                            g.fillRect((x+xPos)*Z-xVerschuiving+35,sizeY-(y+yPos)*50-yVerschuiving+15 , 15, 35);
                        }
                        if(c.getLinkerBalk() == null)
                        {
                            if (model.isBouwMode())
                            {
                                g.drawRect((x+xPos)*Z-xVerschuiving,sizeY-(y+yPos)*Z-yVerschuiving+15 , 15, 35);
                            }
                        }
                        else
                        {
                            if(c.getLinkerBalk() == Grondstof.hout)
                            {
                                g.setColor(new Color(139,90,43 ));
                            }
                            else
                            {
                                g.setColor(Color.gray);

                            }
                            g.fillRect((x+xPos)*Z-xVerschuiving,sizeY-(y+yPos)*50-yVerschuiving+15 , 15, 35);
                        }

                        if (b.getHoverOver() == true)
                        {
                            g.setColor(Color.BLACK);
                            g.drawRect((x+xPos)*Z-xVerschuiving,sizeY-(y+yPos)*Z-yVerschuiving, 50, 50);
                        }

                    }
                }

            }
        }

    }

    /**
     * Method getSizeX geeft de grote van het scherm in x richting terug
     *
     * @return sizeX int,    de grote van het scherm
     */
    public int getSizeX()
    {
        return sizeX;
    }

    /**
     * Method getZ geeft de grootte van de zijde van het rooster
     *
     * @return Z int,   de grootte van een zijde van een rooster
     */
    public int getZ()
    {
        return Z;
    }

    /**
     * Method getSizeY geeft de grote van het scherm in x richting terug
     *
     * @return sizeY int,    de grote van het scherm
     */
    public int getSizeY()
    {
        return sizeY;
    }

    /**
     * Method tekenHemel teken de zon als het dag is en de maan en sterren als het nacht is.
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void tekenHemel(Graphics g)
    {

        if(model.getHemel().isDag())
        {

            g.setColor(Color.yellow);
            g.fillOval(model.getHemel().getX()-50, model.getHemel().getY()-50, 100, 100);
            g.setColor(Color.black);
        }
        else
        {
            g.setColor(Color.white);
            g.fillOval(model.getHemel().getX()-50, model.getHemel().getY()-50, 70, 70);
            g.fillOval(200, 300, 10,10);
            g.fillOval(1200, 420, 10,10);
            g.fillOval(50, 50, 10,10);
            g.fillOval(800,90, 10,10);
            g.fillOval(560, 50, 10,10);
            g.fillOval(880, 89, 10,10);
            g.fillOval(440, 250, 10,10);
            g.fillOval(300, 350, 10,10);
        }
    }

    /**
     * Method tekenHulp tekend de hulp text als hulpmode aan staat. bij verschillende statusen van parameters in het model worden er andere zinnne op ander plaatsen gezet
     *als het nacht wordt wordt het meeste van de tekst wit
     *
     * @param g Graphics, de grafische beelden die aangepast worden 
     */
    public void tekenHulp(Graphics g)
    {
        if( model.isHulp() == true )
        {
            g.drawString("je kan linkermuisknop op een grondstof doen om de hoeveelheidaanwezige stof te bekijken", 50, 20);
            java.util.Iterator<Object> iterator=model.getIterator2();

            if(model.getHemel().isDag() == true)
            {
                g.setColor(Color.black);
            }
            else
            {
                g.setColor(Color.white);
            }
            
            if( model.getSelectedObject() instanceof Gebouw)
            {
                Gebouw b = (Gebouw) model.getSelectedObject();
                if(b.getFunctie() == Functie.hoofdgebouw)
                {
                    g.setColor(Color.white);
                    g.drawString("Nu kan je een werker met deze knop maken ", 230, 730);
                    g.setColor(Color.black);
                }
            }

            else if( model.getSelectedWerker() != null)
            {
                g.drawString("Nu kan je eender welke grondstoffen aan klikken met linkermuisknop om de werker te sturen ", 50, 40);
            }
            else if(model.isBouwMode())
            {
                g.drawString("Je bent in bouwmode alle andere functies zijn nu uitgeschakeld ", 50, 40);
                g.drawString("Rechtermuis knop boven een leeg rechte stuk om het geraamte van een gebouw te plaats ", 50, 60);
                g.drawString("Daarna rechtermuis knop op de verschillende balken om ze te maken uit de actieve stof ", 50, 80);
                g.drawString("de bovenbalk kost 10 en de 2 eenheden zij balken kost beide 5 eenheden", 50, 100);
                g.setColor(Color.white);
                g.drawString("Selecteer een van deze stoffen om in te bouwen ", 700, 690);

            }
            else if(model.getSelectedObject() == null)
            {
                g.setColor(Color.white);
                g.drawString("druk op deze knop om in bouwmode te gaan ", 40, 690);
                g.drawString("druk op deze knop om alle hulp te verbergen ", 750, 690);
                g.fillRect(950, 695, 15, 30);
                g.setColor(Color.black);

                while (iterator.hasNext()) 
                {
                    Object o = iterator.next();

                    if (o instanceof Gebouw)
                    {
                        Gebouw b = (Gebouw)o;
                        int x = b.getX(); // de x coordinaat van b
                        int y = b.getY();
                        if(b.getFunctie() == Functie.hoofdgebouw)
                        {
                            int verschuivingX = -50; // de verschuiving van de tekst tenopzichte van de coordinaaten van het gebouw in de x richting 
                            if( model.isHulp() == true && b.isSelected() == false && model.getSelectedWerker() == null)
                            {
                                if(b.getX()<2)
                                {
                                    verschuivingX = 20;
                                }
                                else if(b.getX()> model.getLandschap().getX()-2 )
                                {
                                    verschuivingX = -200;
                                }
                                g.drawString("Dit is jouw hoofdgebouw ", (b.getX())+verschuivingX, sizeY-(b.getY())*50-yVerschuiving-80);
                                g.drawString("klik linkermuisknop op dit gebouw om het te selecteren", (b.getX())*Z-xVerschuiving+verschuivingX, sizeY-(b.getY())*50-yVerschuiving-65);
                                g.fillRect((b.getX())*Z-xVerschuiving+ 20, sizeY-(b.getY())*50-yVerschuiving-50, 10, 35);

                            }
                        }
                    }
                }
            }
        }
    }

    public void tekenMouseCoordinates(Graphics g)
    {
        String point = '(' + String.valueOf(mouseX) + ',' + String.valueOf(sizeY-mouseY) + ')';
        g.drawString(point,mouseX,  mouseY );
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}

