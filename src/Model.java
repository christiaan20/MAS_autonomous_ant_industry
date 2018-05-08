package src;

import MAS_classes.CustomStruct;
import MAS_classes.Pheromone;

import java.util.*;

/**
 * Dit is de klasse die alle aspecten van het spel bij elkaar houd, de algemene parameters bij houden, de verzamelingen van werkers en objecten bijhoud
 * , de grote van het spelbord, welke werkers of object op het huidige moment wordt overhangen en geselecteerd is,
 * of bouwmode aanstaat of niet, met welke grondstof op het moment gebouwd word en of de help aan staat of niet. 
 * Hier worden de grondstof objecten en 1 hoofdgebouw willekeurig gegenereert.
 * inexpliciet wordt er in dit model gebruik gemaakt van een rooster van Y+4 rijen en X aantal kollomen (zie Landschap)de onderste rij is altijd gevuld met Grond objecten 
 * ,deze neemt meer de vakken in en wordt bijgehouden in het landschap en de vakken net boven elke grond wordt gevult met Objecten 
 * deze worden in het model bijgehouden. De werkers liggen als het waren boven dit rooster al zullen zij boven de 1ste rij lopen en door de 2de rij 
 * zij worden in model bijgehouden.
 * De klasse maakt gebruik van de klassen: Werker, Landschap, hemelLichaam,Object,Grondstof;
 * De klasse wordt aangemaakt bij het aanmaken van een MAS_autoAntIndustry Object
 * 
 * @author christiaan Vanbergen 
 * @version V12
 */
public class Model 
{
    private ArrayList<Werker> werkers = new ArrayList<Werker>(); // de werkers die in het model zitten
    private Landschap landschap; // het landschap in het spel
    private ArrayList<Object> objecten = new ArrayList<Object>(); // de objecten die in het model zitten
    private Random random = new Random(); // een random parameter die willekeurige getallen kan genereren waar nodig
    private int hoeveelheidHout; // de hoeveelheid van hout die de speler terbeschikking heeft
    private int hoeveelheidSteen;// de hoeveelheid van steen die de speler terbeschikking heeft
    private int hoeveelheidVoedsel;// de hoeveelheid van voedsel die de speler terbeschikking heeft
    private int sizeX; // de grootte van het bord is x richting deze is nodig om het pad correct te configureren
    private int sizeY; // de grootte van het bord is y richting deze is nodig om het pad correct te configureren
    private HemelLichaam hemel; // het object die de zon en de maan maakt
    private boolean bouwMode; // true als er gebouwen kunnen gezet worden, alle andere functies worden uitgeschakeld, false als de normale functie van het spel kan
    private Grondstof grondstofMode;//
    private Object selectedO = null; // het object die op dat moment geselecteert is
    private Werker selectedW = null; // de werkers die op dat moment geselecteert is
    private Object hoveringObject = null; // true als er al een object is waar de muist overhangt
    private boolean active = false ;// true als er al werkers is waar de muis overhangt
    private boolean hulp; // is de hulp functie aan of niet

    private boolean objectenLocked; //mutex lock for the objecten list

    /**
     * Constructor voor het model, de begin instellingen zijn: bouwmode is af, er is geen object geselecteer, de grote van het bord is 1300 op 850
     * de speler begin met 20 eenheden van elk materiaal, een nieuw Landschap en niew HemelLichaam wordt er gemaakt, er worden grondstoffen en 1 hoofdgebouw gezet
     * er wordt een pad gemaakt naargelang het landschap.
     * 
     * @see
     */
    public Model()
    {
        bouwMode = false;
        selectedO = null;
        sizeX = 1000;
        sizeY = 800;
        hoeveelheidHout = 20;
        hoeveelheidSteen = 20;
        hoeveelheidVoedsel = 20;
        //landschap = new Landschap();
        //hemel = new HemelLichaam();
        //this.setObjecten();
        //this.set_MAS_Objects();
        hulp = false;
        objectenLocked = false;
    }

    public void set_MAS_Objects(GroteView v)
    {
        Gebouw HQ = new Gebouw(400,400,Grondstof.hout,Grondstof.steen,Grondstof.steen,Functie.hoofdgebouw);
        objecten.add( HQ);

        objecten.add( new Grondstoffen(Grondstof.steen,500,500, 3));
        objecten.add( new Grondstoffen(Grondstof.steen,600,500));
        objecten.add( new Grondstoffen(Grondstof.steen,900,500));
        objecten.add( new Grondstoffen(Grondstof.steen,100,100));
        objecten.add( new Grondstoffen(Grondstof.steen,100,200));
        objecten.add( new Grondstoffen(Grondstof.steen,900,900));

        for( int i = 0; i<10;i++)
        {
            this.addWerker(HQ.getX(),HQ.getY(),("werker_" + String.valueOf(i+10)) ,v,this);
        }

    }

    /**
     * Method getLandschap geeft het landschap terug
     *
     * @return landschap Landschap, Het object van het type Landschap
     */
    public Landschap getLandschap()
    {
        return landschap;
    }

    /**
     * Method randomGrondstof genereert willekeurig een waarde van grondstof met 1/5 kans op hout,steen of voedsel en 2/5 kans op null.
     * setObjecten() maakt gebruik van deze methode.
     * 
     * @return b Grondstof, de willekeurig gegenereerde waarde van Grondstof die beslist welk soort grondstoffen object op een bepaalde coordinaat komt,bij null geen object
     */
    public Grondstof randomGrondstof()
    {
        Grondstof b = null; // de waarde van grondstof die uiteindelijk gegeven zal worden
        int a =random.nextInt(10);
        if (a <= 1 )
        {
            b = Grondstof.hout;
        }
        if(a >= 8)
        {
            b = Grondstof.steen;
        }
        if(a >=4 && a<=5)
        {
            b = Grondstof.voedsel;
        }
        return b;
    }

    /**
     * Method randomConstructie genereert willekeurig true of false waarde er is 2/5 kans of true en 3/5 op false.
     * SetObjecten() maakt gebruik van deze methode.
     *
     * @return b boolean,   moet er een gebouw komen als er nog geen is: true, moet er geen gebouw komen: false
     */
    public boolean randomConstructie()
    {
        boolean b = false; // de waarde die uit eindelijk zal gegeven worden
        int a =random.nextInt(10);
        if (a >= 3 && a <= 6  )
        {
            b = true;
        }
        return b;
    }

    /**
     * Method setObjecten gaat alle Grond objecten af en vraagt aan randomGrondstof() of er een grondstoffen object boven moet en welke soort het zou moeten zijn
     * als er geen grondstof op komt vraagt hij aan randomContructie() of er een gebouw moet komen of niet als deze true zegt en er nog geen gebouw is dan wordt
     * er 1 gemaakt boven de huidige grond, er is ook controle die zorgt dat er minstens 1 gebouw wordt gegenereert. Als een Object boven een grond wordt gezet 
     * wordt in dit Grond object parameter bezet op true gezet
     */
    public void setObjecten()
    {
        boolean constructie = false; // zegt of er al een constructie in het landschap staat
        for(int i=0;i<landschap.getX();i++)
        {

            if(landschap.getGrond(i)== null)
            {
            }
            else
            {
                Grond b =landschap.getGrond(i);
                if(b.getRichting()== GrondRichting.recht)
                {
                    Grondstof c = randomGrondstof();
                    if( c == null)
                    {
                        boolean toelating = randomConstructie(); // mag er daar een constructie komen?
                        if(constructie == false)
                        {
                            if(toelating)
                            {
                                objecten.add( new Gebouw(i,b.getHoogte()+1,Grondstof.hout,Grondstof.steen,Grondstof.steen,Functie.hoofdgebouw));
                                constructie = true;
                                b.setBezet(true);
                            }
                            else 
                            {   
                                boolean nogRechteStukken = false ;// parameter die zegt als er nog rechte stukken komen na deze
                                for(int j=i;j<landschap.getX()-8;j++)
                                { 
                                    if (landschap.getGrond(j).getRichting() == GrondRichting.recht)
                                    {
                                        nogRechteStukken = true;
                                        
                                    }
                                }
                                if (nogRechteStukken == false)
                                {
                                    objecten.add( new Gebouw(i,b.getHoogte()+1,Grondstof.hout,Grondstof.steen,Grondstof.steen,Functie.hoofdgebouw));
                                    constructie = true;
                                    b.setBezet(true);
                                }

                            }
                        }
                    }
                    else
                    {
                        objecten.add( new Grondstoffen(c,i,b.getHoogte()+1));
                        b.setBezet(true);

                    }

                }
            }
        }        
    }



    public void createPheromone(int x,int y,Grondstof g ,double expireTime, Werker w)
    {
        Pheromone p = new Pheromone(x,y,expireTime,g,w);
        this.addObject(p);
        w.addVisitedPheromone(p);
    }

    public void deletePheromone(Pheromone p)
    {
        this.verwijderObject(p);
    }

    public void checkExpiredPheromones()
    {
        this.requestObjectenLockBlocking();
        Iterator<Object> iter = objecten.iterator();

        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj instanceof Pheromone)
            {
                if (((Pheromone) obj).getExpireTime() <= 0)
                    iter.remove();
            }

        }
        this.releaseObjectenLock();

    }

    public void tickPheromones(double msec)
    {
        this.requestObjectenLockBlocking();
        for(Object obj: objecten)
        {
            if(obj instanceof Pheromone)
            {
                ((Pheromone) obj).tick(msec);
            }
        }
        this.releaseObjectenLock();
    }


    /**
     *  getSizeWerkers geeft hoeveel werkers er in het spel zijn, wordt in grote view door perform() gebruikt
     *
     * @return     werkers.size() int,  de grote van de arraylist
     */
    public int getSizeWerkers()
    {
        return werkers.size();
    }

    /**
     * Method verwijderLegeObjecten verwijderd het meegegeven object, de grond onder dat object wordt terug vrij om op te bouwen.
     *
     */
    public void verwijderLegeObjecten()
    {
      //  landschap.getGrond(o.getX()).setBezet(false);

       // objecten.remove(o);
        this.requestObjectenLockBlocking();



        Iterator<Object> iter = objecten.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj instanceof Grondstoffen)
            {
                if(((Grondstoffen) obj).getHoeveelheid() <= 0)
                    iter.remove();
            }


        }
        this.releaseObjectenLock();
    }

    public void verwijderObject(Object o)
    {
        //  landschap.getGrond(o.getX()).setBezet(false);

        // objecten.remove(o);
        this.requestObjectenLockBlocking();

        Iterator<Object> iter = objecten.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj.equals(o))
            {
                    iter.remove();
            }


        }
        this.releaseObjectenLock();
    }

    /**
     * Method getHoeveelheidHout geeft de hoeveelheid hout de speler heeft verzamel + begin hoeveelheden.
     * Wordt door Grote View en Controler gebruikt.
     *
     * @return hoeveelheidHout int,  de hoeveelheid hout tussen 0 en oneindig
     */
    public int getHoeveelheidHout()
    {
        return hoeveelheidHout;
    }

    /**
     * Method getHoeveelheidSteen geeft de hoeveelheid Steen de speler heeft verzamel + begin hoeveelheden.
     * Wordt door Grote View en Controler gebruikt.
     *
     * @return hoeveelheidSteen int,  de hoeveelheid Steen tussen 0 en oneindig
     */
    public int getHoeveelheidSteen()
    {
        return hoeveelheidSteen;
    }

    /**
     * Method getHoeveelheidVoedsel geeft de hoeveelheid Voedsel de speler heeft verzamel + begin hoeveelheden.
     * Wordt door Grote View en Controler gebruikt.
     *
     * @return hoeveelheidVoedsel int,  de hoeveelheid Voedsel tussen 0 en oneindig
     */
    public int getHoeveelheidVoedsel()
    {
        return hoeveelheidVoedsel;
    }

    /**
     * Method setHoeveelheidSteen verhoogt de hoeveelheid steen met een bepaald bedrag
     * Wordt door GatherAnimation en Controler gebruikt.
     * 
     * @param bedrag int,   de hoeveelheid dat bij de hoeveelheid steen geteld moet worden tussen -oneindig en oneindig
     */
    public void setHoeveelheidSteen(int bedrag)
    {
        hoeveelheidSteen = hoeveelheidSteen + bedrag;

    }

    /**
     * Method setHoeveelheidHout verhoogt de hoeveelheid Hout met een bepaald bedrag
     * Wordt door GatherAnimation en Controler gebruikt.
     * 
     * @param bedrag int,   de hoeveelheid dat bij de hoeveelheid Hout geteld moet worden tussen -oneindig en oneindig
     */
    public void setHoeveelheidHout(int bedrag)
    {

        hoeveelheidHout = hoeveelheidHout + bedrag;

    }

    /**
     * Method setHoeveelheidVoedsel verhoogt de hoeveelheid Voedsel met een bepaald bedrag 
     * Wordt door GatherAnimation en Controler gebruikt.
     * 
     * @param bedrag int,   de hoeveelheid dat bij de hoeveelheid Voedsel geteld moet worden tussen -oneindig en oneindig
     */
    public void setHoeveelheidVoedsel(int bedrag)
    {
        hoeveelheidVoedsel = hoeveelheidVoedsel + bedrag;

    }

    /**
     * Method dichsteOpslag zoekt de opslag of het hoofdgebouw dat het dichtst bij de werkers zijn positie is in x richting.
     *
     * @param w Werker,     de werkers waar men de het dichtst zijnde opslag of hoofdgebouw zoekt
     * @return dichsteOpslag Gebouw,    het Gebouw dat het dichst ligt bij de gegeven Werker
     */
    public Gebouw dichsteOpslag(Werker w)
    {
        Gebouw dichsteOpslag = null; // Het dichste opslag gebouw tot de werkers tot nu toe
        int kleinsteXVerschil = 1000000; // De kleinste afstand tussen opslag gebouw en werkers tot nu toe
        for(Object o: objecten)
        {
            if( o instanceof Gebouw)
            {
                Gebouw g = (Gebouw) o; 
                if (g.getFunctie() == Functie.hoofdgebouw || g.getFunctie() == Functie.opslag)
                {
                    if( (g.getX()*50)-w.getX() < 0)
                    {
                        if( - ((g.getX()*50)-w.getX()) < kleinsteXVerschil && g.isAf())
                        {
                            kleinsteXVerschil = - ((g.getX()*50)-w.getX());
                            dichsteOpslag = g;
                        }
                    }
                    else if (((g.getX()*50)-w.getX()) < kleinsteXVerschil && g.isAf())
                    {
                        kleinsteXVerschil = (g.getX()*50)-w.getX();
                        dichsteOpslag = g;
                    }
                }

            }
        }

        return dichsteOpslag;
    }

    /**
     * Method getIterator geeft de iterator om de verzameling van werkers door te lopen 
     *
     * @return werks.iterator() Iterator<Werker>, de gevraagde iterator
     */
    public Iterator<Werker> getIterator() 
    {
        return werkers.iterator();    
    }

    /**
     * Method getIterator geeft de iterator om de verzameling van werkers door te lopen 
     *
     * @return werks.iterator() Iterator<Werker>, de gevraagde iterator
     */
    public Iterator<Object> getIterator2() 
    {
        return objecten.iterator();    
    }

    /**
     * Method getSizeX geeft de grootte van het bord in de x richting.
     * Wordt door de view, MAS_autoAntIndustry en Controler gebruikt
     *
     * @return sizeX int,    de grootte van het bord in de x richting
     */
    public int getSizeX()
    {
        return sizeX;

    }

    /**
     * Method getSizeX geeft de grootte van het bord in de y richting.
     * Wordt door de view, MAS_autoAntIndustry en Controler gebruikt
     *
     * @return sizeX int,    de grootte van het bord in de y richting
     */
    public int getSizeY()
    {
        return sizeY;

    }

    /**
     * Method getHemel geeft het hemelLichaam terug wordt door de view gebruikt
     * W
     *
     * @return hemel HemelLichaam, mag nooit null zijn
     */
    public HemelLichaam getHemel()
    {
        return hemel;
    }

    /**
     * Method isBouwMode Geeft terug of het model in bouwmode is of niet
     * wordt door Controler, Groteview en View gebruikt.
     *
     * @return bouwmode boolean, bouwmode aan: true, bouwmode af: false
     */
    public boolean isBouwMode()
    {
        return bouwMode;
    }

    /**
     * Method setBouwMode werander de bouwmode naar de bijgegeven waarde
     * Wordt door GroteView gebruikt
     *
     * @param b boolean, als bouwmode aangezet wordt: true, als bouwmode afgezet wordt: false
     */
    public void setBouwMode(boolean b)
    {
        bouwMode=  b;
    }

    /**
     * Method addObject voegt een object toe aan het model, zet de grond onder het object op bezet.
     *
     * @param object, het toe te voegen object
     */
    public void addObject(Object object)
    {
        objecten.add(object);
       // landschap.getGrond(object.getX()).setBezet(true);
    }

    /**
     * Method sizeObjecten geeft het aantal objecten dat in het model zit
     *
     * @return objecten.size() int,  het aantal Objecten in de ArrayList
     */
    public  int sizeObjecten()
    {
        return objecten.size();
    }

    /**
     * Method getGrondstofmode geeft in welke grondstof er nu gebouwd wordt.
     * Wordt gebruikt door Controler, Groteview.
     *
     * @return grondstofMode Grondstof,  De actieve grondstof, hout of steen
     */
    public Grondstof getGrondstofmode()
    {
        return grondstofMode;
    }

    /**
     * Method setGrondstofMode veranderd in welke grondstof er nu gebouwd wordt
     *
     * @param b Grondstof,  de grondstof waar gebouwd in gaat worden, hout of steen
     */
    public void setGrondstofMode(Grondstof b)
    {
        grondstofMode=  b;
    }

    /**
     * Method getSelectedObject geeft het Object dat nu geselecteert is terug.
     * Wordt gebruikt door Controler
     *
     * @return selectedO Object het Object dat geselecteerd is of null
     */
    public  Object getSelectedObject()
    {
        return selectedO;
    }

    /**
     * Method setSelectedObject veranderd het Object dat nu geselecteert is.
     * Wordt gebruikt door Controler
     * 
     * @param o Object  het nieuwe Object dat geselecteert is of null
     */
    public void setSelectedObject(Object o)
    {
        selectedO = o;
    }

    /**
     * Method addWerker voegt een nieuwe werkers toe aan het model die een bepaalde naam en bepaalde begin x coordinaat heeft
     *
     * @param coX int,  De begin x coördinaat van de nieuwe werkers
     * @param coY int,  De begin y coördinaat van de nieuwe werkers
     * @param naam String,   De naam van de nieuwe werkers
     */
    public void addWerker(int coX, int coY , String naam, GroteView v, Model m)
    {
        Werker werker = new Werker(coX,coY,naam);
        werker.initMovePolicy(v,m);
        werkers.add(werker);


    }



    /**
     * Method addWerker voegt een nieuwe werkers toe aan het model die een bepaalde naam en bepaalde begin x coordinaat heeft
     *
     * @param coX int,  De begin x coördinaat van de nieuwe werkers
     * @param naam String,   De naam van de nieuwe werkers
     */
    public void addWerker(int coX, String naam)
    {
        werkers.add(new Werker(coX,naam));
    }

    /**
     * Method getSelectedWerker geeft de werkers die nu geselecteerd is terug
     * Wordt door de controller gebruikt
     *
     * @return selectedW Werker, de werkers die geselecteerd is of null
     */
    public  Werker getSelectedWerker()
    {
        return selectedW;
    }

    /**
     * Method setSelectedWerker veranderd de geselecteerde werkers
     * Wordt door de controller gebruikt
     * 
     * @param w Werker, De nieuwe werkers die net geselecteerd is of null
     */
    public void setSelectedWerker(Werker w)
    {
        selectedW = w;
    }

    /**
     * Method getHoveringObject geeft het object waar over de muis hangt of null als er geen object is waar de muis over hangt
     * Wordt door de controller gebruikt
     * 
     * @return hoveringObject het object waar de muis over hangt of null
     */
    public  Object getHoveringObject()
    {
        return hoveringObject;
    }

    /**
     * Method setHoveringObject veranderd het object waar over de muis hangt 
     * Wordt door de controller gebruikt
     * 
     * @param o het nieuwe object waar de muis over is gaan hangen of null als er geen object onder de muis is
     */
    public void setHoveringObject(Object o)
    {
        hoveringObject = o;
    }

    /**
     * Method isActive zegt of de muis over een werkers hangt of niet
     * Wordt door de controller gebruikt
     * 
     * @return active boolean, true als de muis over een werkers hangt, false als de muis over geen werkers hangt
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Method setActive veranderd de status of de muis over een werkers hangt of niet
     * Wordt door de controller gebruikt
     * 
     * @param active boolean,   true als de muis over een werkers is gekomen, false als de muis van een werkers afgaat
     */
    public void setActive (boolean active)
    {
        this.active = active;
    }
     /**
     * Method isHulp zegt of er hulp wordt gegeven onf niet
     * Wordt door de GroteView gebruikt
     * 
     * @return hulp boolean, true als er hulp gegeven wordt, false als er geen hulp gegeven wordt
     */
    public boolean isHulp()
    {
        return hulp;
    }

    /**
     * Method setHulp veranderd de status van het model of er hulp wordt gegeven of niet
     * Wordt door de GrotoView gebruikt
     * 
     * @param a boolean,   true als de hulp wordt aangezet, false de hulp wordt afgezet
     */
    public void setHulp (boolean a)
    {
        this.hulp = a;
    }
   
    /**
     * Method Restart brengt alles terug naar begin condities en genereert alles opnieuw
     * De threads van het vorige spel blijven nog draaien, dit betekend dat werkers van het vorige spel die naar de opslagplaats gingen het lading nog gaan afgeven
     * maakt soms alleen het hemellichaam dan moet de methode nog eens opgeroepen worden, gebeurde pas naar het verkleinen van de scherm grootte
     *
     */
    public void restart()
    {
        objecten.removeAll(objecten);
        werkers.removeAll(werkers);
        bouwMode = false;
        selectedO = null;
        sizeX = 1300;
        sizeY = 850;
        hoeveelheidHout = 20;
        hoeveelheidSteen = 20;
        hoeveelheidVoedsel = 20;
        landschap = new Landschap(); 
        hemel = new HemelLichaam();
        this.setObjecten();
        
    }

    public Gebouw getHQ()
    {
        return (Gebouw) objecten.get(0);
    }

    public Object checkOverObjectFromModel(int x, int y) {
        for (int i = 0; i < objecten.size(); i++) {
            if (objecten.get(i).isWithinObject(x, y)) {
                return objecten.get(i);
            }
        }

        return null;
    }

    public int distBetween(int x1, int y1,int x2, int y2)
    {
        int Xdist = x1 - x2;
        int Ydist = y1 - y2;
        return (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));
    }

    public boolean  DetectedPheromone(Werker w, Pheromone p)
    {

        if(distBetween(w.getX(),w.getCoY(),p.getX(),p.getY())<= w.getPheromonePolicy().getDetectDistance())
            return true;
        return false;
    }

    public boolean  DetectedPheromone(Werker w, CustomStruct s)
    {
        Pheromone p = s.getPheromone();
        int dist = distBetween(w.getX(),w.getCoY(),p.getX(),p.getY());

        if(dist<= w.getPheromonePolicy().getDetectDistance())
        {
            s.setDistance(dist);
            return true;
        }
        return false;
    }

    public void updatePheromoneDistances(Werker w)
    {

    }

    public void findPheromones(Werker w)
    {

        for(Object o:objecten)
        {
            if(o instanceof Pheromone)
            {
                CustomStruct s = new CustomStruct((Pheromone) o);
                if(DetectedPheromone(w, s))
                {
                    int dist = s.getDistance();
                    w.addDetectedPheromone(s.getPheromone(), dist);
                }
            }
        }
    }

    public ArrayList<Werker> getWerkers() {
        return werkers;
    }

    public void setWerkers(ArrayList<Werker> werkers) {
        this.werkers = werkers;
    }

    public ArrayList<Object> getObjecten() {

        return objecten;
    }

    public ArrayList<Object> getObjectenBlock() {

        return objecten;
    }

    public void setObjecten(ArrayList<Object> objecten) {
        this.objecten = objecten;
    }

    /**
     * non-blocking method that will return true when the Object list is free to be iterated over
     * @return
     */
    public boolean requestObjectenLock()
    {

        if(objectenLocked == false)
        {
            objectenLocked = true;
            return true;
        }
        else
            return false;

    }

    /**
     * blocking method that will return true when the Object list is free to be iterated over
     * @return
     */
    public boolean requestObjectenLockBlocking()
    {

        while(objectenLocked == true)
        {

        }
        objectenLocked = true;
        return true;

    }

    public void releaseObjectenLock()
    {
        objectenLocked = false;
    }
}
