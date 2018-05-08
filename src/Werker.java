package src;

import MAS_classes.MovePolicyBasic;
import MAS_classes.Pheromone;
import MAS_classes.PheromonePolicyBasic;
import MAS_classes.CustomStruct;

import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * de werkers is een object dat boven het rooster van het landschap uitloopt, zijn functie in het programma
 * is om grondstoffen te verzamelen van objecten en die terug brengen naar een opslag of hoofdgebouw.
 * de klassen model, moveThread, moveAnimatie, gatherThread, gatherAnimation en controler gebruik 
 * 
 * @author Christiaan Vanbergen 
 * @version v11
 * 
 */
public class Werker {
    private int coX;// de x positie van de werkers
    private int coY; //NEW Y-position of the worker
    private double currDirection; // the direction the worker is facing at them moment
    private int size;
    private String naam; //de naam van de werkers

    private int lading; //de lading die een werkers draagt
    private int maxLading;// de maximum lading die een werkers kan dragen
    private boolean selected; //true als de werkers geselecteerd is, false als hij niet geselecteerd is
    private boolean hoverOver; // true als de muis boven de werkers hangt, false als de muis niet over de werkers hangt
    private Grondstof task; //het type grondstof van de lading die de werkers draagt.
    private boolean moving; //true als de werkers aan het bewegen is, false als de werkers niet aan het bewegen is
    private boolean inStructuur; // true als de werkers in een object zit, false als hij niet in een object zit
    private boolean vol; //  true als de lading even groot is als de maximum lading, false als dat niet zo is

    private Object huidigWerk; // het huidige object waar de werkers gaat verzamelen
    private int workerSpeed;
    private Random random = new Random(); // Random object to generate directions of travel


    //parameters that impact the behavior of the werkers
    private MovePolicyBasic movePolicyObject;
    private boolean auto;   //whether the werkers works via the policies
    private MoveThread m = null; //the thread attached to the werkers when in auto mode
    private Animation a = null; //in case you are not working with a thread

    private PheromonePolicyBasic PheromonePolicy;
    private Pheromone currPheromone;
    private ArrayList<CustomStruct> detectedPheromones = new ArrayList<CustomStruct>();
    private ArrayList<Pheromone> visitedPheromones = new ArrayList<Pheromone>();

    private int distLastPheroDrop;
    /**
     * Constructor voor objecten van de klasse Werker dit zijn de begin instellingen:
     * De beginlading is 0, de maximum lading is 5, een werkers is nog niet geselecteerd,
     * heeft nog geen lading en dus geen type van lading, de muis hangt nog niet over de werkers,
     * de werkers is niet aan het bewegen, nog niet in een structuur en nog niet vol.
     *
     * @param naam een String die uiteindelijk de naam van de werkers gaat zijn
     */
    public Werker(String naam) {
        this.naam = naam;
        coX = 100;
        coY = 200;
        size = 20;

        lading = 0;
        maxLading = 5;
        selected = false;
        task = Grondstof.explorer;
        hoverOver = false;
        moving = false;

        inStructuur = false;
        vol = false;
        workerSpeed = 10;
        currDirection = random.nextDouble()*Math.PI*2; //start in a random direction

        PheromonePolicy = new PheromonePolicyBasic(this);
        currPheromone = null;

        distLastPheroDrop = 0;

     //   movePolicyObject = new MovePolicyBasic(this, 50);
    }

    /**
     * Constructor voor objecten van de klasse Werker dit zijn de begin instellingen:
     * De beginlading is 0, de maximum lading is 5, een werkers is nog niet geselecteerd,
     * heeft nog geen lading en dus geen type van lading, de muis hangt nog niet over de werkers,
     * de werkers is niet aan het bewegen, nog niet in een structuur en nog niet vol.
     *
     * @param x    een Int die de x positie van de werkers gaat zijn
     * @param naam een String die uiteindelijk de naam van de werkers gaat zijn
     */
    public Werker(int x, String naam) {
        this.naam = naam;
        coX = x;
        coY = 200;
        size = 20;

        lading = 0;
        maxLading = 5;
        selected = false;
        task = Grondstof.explorer;
        hoverOver = false;
        moving = false;
        inStructuur = false;
        vol = false;
        workerSpeed = 10;
        currDirection = random.nextDouble()*Math.PI*2; //start in a random direction

        PheromonePolicy = new PheromonePolicyBasic(this);
        currPheromone = null;

        distLastPheroDrop = 0;

      //  this.movePolicyObject = new MovePolicyBasic(this, 50);
    }

    /**
     * Constructor voor objecten van de klasse Werker dit zijn de begin instellingen:
     * De beginlading is 0, de maximum lading is 5, een werkers is nog niet geselecteerd,
     * heeft nog geen lading en dus geen type van lading, de muis hangt nog niet over de werkers,
     * de werkers is niet aan het bewegen, nog niet in een structuur en nog niet vol.
     *
     * @param x    een Int die de x positie van de werkers gaat zijn
     * @param naam een String die uiteindelijk de naam van de werkers gaat zijn
     */
    public Werker(int x, int y, String naam) {
        this.naam = naam;
        coX = x;
        coY = y;
        size = 20;

        lading = 0;
        maxLading = 5;
        selected = false;
        task = Grondstof.explorer;
        hoverOver = false;
        moving = true;
        inStructuur = false;
        vol = false;
        workerSpeed = 5;
        auto = true;
        currDirection = random.nextDouble()*Math.PI*2; //start in a random direction

        PheromonePolicy = new PheromonePolicyBasic(this);
        currPheromone = null;

        distLastPheroDrop = 0;
       // movePolicyObject = new MovePolicyBasic(this, 50);
       // movePolicyObject = policy;

    }

    public void tick(Model model)
    {
        //moving is when the worker is active
        if(this.isMoving())
        {
            //procede to move to the assigned coordinates
            //System.out.println("step X" +w.getX() + "Y " + w.getCoY());

            //move agent, if the agent has reached this current target true is returned and it looks for new pheromones
            if(this.move(model.getSizeX(),model.getSizeY()))
            {
                this.removeAllDetectedPhero();
                model.findPheromones(this);
            }


            ArrayList<Object> objects = model.getObjecten();

            for( int i = 0;(i < objects.size()); i++)
            {
                Object obj = objects.get(i);
                if(obj.isWithinObject(this.getX(),this.getCoY()) == true)
                {
                    //this thread relays to the movingpolicy of the agent which will decide what to do with and will return true if it will go inside the object and start a gather thread
                    if(this.foundObject(obj))
                    {
                        obj.addWerker(this);
                    }
                }
            }

        }
        if(this.dropPheromone())
            model.createPheromone(this.getX(),this.getCoY(),this.getTask(),this.getPheromonePolicy().getExpireTime(),this);


    }


    /**
     * Method addLading voegt 1 eenheid van een bepaalde stof toe aan de werkers en zet het task
     * dat de werkers draagt naar de stof die toegevoegt wordt, als de lading de maximumlading bereikt
     * heeft dan wordt de werkers als status vol gezet.
     *
     * @param stof Grondstof, het type van grondstof dat de werkers bij krijgt
     */
    public void addLading(Grondstof stof)
    {
        lading++;
        task = stof;
        if(lading >= maxLading)
        {
            vol = true;
        }
    }

    /**
     * Method dropLading de werkers zijn lading wordt tot null ge reduceert het type lading dat de werkers
     * draagt is null
     *
     */
    public void dropLading()
    {
        lading = 0;
        //task = null;
        vol = false;
    }

    /**
     * Method getLading geeft de lading die de werkers op dit moment heeft terug.
     *
     * @return lading int, dit is de hoeveelheid die de werkers draagt tussen 0 en 5
     */
    public int getLading()
    {
        return lading;
    }

    /**
     * Method isSelected zegt of de werkers geselecteert is of niet.
     *
     * @return selected boolean, is de werkers geselecteerd?true of false
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * Method select hiermee kan de werkers geselecteerd of gedeselecteerd worden.
     *
     * @param a wordt de werkers geselecteer: true , wordt de werkers gedeselecteerd: false
     */
    public void select(boolean a)
    {
        selected =  a;
    }

    /**
     * Method moveRight verplaatst de werkers 10 stappen naar rechts.
     *
     */
    public void moveRight()
    {
        coX = coX + workerSpeed;
    }

    /**
     * Method moveLeft verplaatst de werkers 10 stappen naar links.
     *
     */
    public void moveLeft()
    {
        coX = coX -workerSpeed;
    }

    public int moveTo(int x, int y)
    {
        int Xdist = x - coX;
        int Ydist = y - coY;
        int dist = (int)Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));

        int newX = x;
        int newY = y;

        int distWalked = 0 ;

        if (dist > workerSpeed)
        {
            double corner = getaTanCorner(Xdist, Ydist);


            double cos = Math.cos(corner);

            newX = coX +  (int) (cos*workerSpeed);

            double sin = Math.sin(corner);
            newY = coY + (int) (sin*workerSpeed);

            distWalked = (int) (Math.sqrt(Math.pow(newX-coX,2)+Math.pow(newY-coY,2)));


//            System.out.println("Xdist  "+ Xdist + " Ydist " + Ydist);
//            System.out.println("corner is " + corner + " cos  "+ Math.cos(corner)*workerSpeed + " sin " + Math.sin(corner)*workerSpeed);

        }

        this.setCoX(newX);
        this.setCoY(newY);

        distLastPheroDrop =  distLastPheroDrop + distWalked;


        return distWalked;
    }

    public double getAngleToPoint(int x, int y)
    {
        return getaTanCorner(x-this.getX(),y-this.getCoY());
    }

    public double getaTanCorner(int xdist, int ydist) {
        double corner = 0;
        if(xdist < 0)
            corner = Math.PI+ Math.atan((double) ydist /(double) xdist) ;
        else
        {
            if(ydist < 0)
                corner = 2*Math.PI+ Math.atan((double) ydist /(double) xdist) ;
            else
                corner = Math.atan((double) ydist /(double) xdist) ;
        }
        return corner;
    }


    public void moveCornerTo(double corner, int dist)
    {
        int newX = coX +  (int) (Math.cos(corner)*dist);;
        int newY = coY + (int) (Math.sin(corner)*dist);

        if (dist > workerSpeed)
        {
            double cos = Math.cos(corner);

            newX = coX +  (int) (cos*workerSpeed);

            double sin = Math.sin(corner);
            newY = coY + (int) (sin*workerSpeed);


//            System.out.println("Xdist  "+ Xdist + " Ydist " + Ydist);
//            System.out.println("corner is " + corner + " cos  "+ Math.cos(corner)*workerSpeed + " sin " + Math.sin(corner)*workerSpeed);

        }

        this.setCoX(newX);
        this.setCoY(newY);
    }

    public boolean dropPheromone()
    {
        if(distLastPheroDrop > getPheromonePolicy().getDropDistance())
        {
            distLastPheroDrop = 0;
            if(this.getPheromonePolicy().isEnablePheroDrop())
                return true;
        }
        return false;
    }


    public boolean move(int SizeX,int SizeY)
    {
        return movePolicyObject.move( SizeX, SizeY);
    }

    public boolean foundObject(Object obj)
    {
        return movePolicyObject.foundObject(obj);
    }

    public void initMovePolicy(GroteView view, Model model)
    {
        movePolicyObject = new MovePolicyBasic(this,50);
        //m = new MoveThread(this,view,null,model);
        a = new Animation(this,view,null,model);
    }

    /**
     * Method getX geeft de x coordinaat van de werkers
     *
     * @return coX int 0 tot 1600
     */
    public int getX()
    {
        return coX;
    }

    /**
     * Method HoverOver maakt hoverover true of false
     *
     * @param a boolean, als de muis boven de werkers hangt : true, als dat niet zo is : false
     */
    public void HoverOver(boolean a)
    {
        hoverOver = a;
    }

    /**
     * Method getHoverOver zegt of de muis boven de werkers hangt of niet
     *
     * @return hoverover boolean, true of false
     */
    public boolean getHoverOver()
    {
        return hoverOver;
    }

    /**
     * Method isMoving zegt of de werkers aan het bewegen is of niet
     *
     * @return moving boolean, true of false
     */
    public boolean isMoving()
    {
        return moving;
    }

    /**
     * Method setMoving zet de werkers op status moving true of false
     *
     * @param moving als de werkers gaat bewegen: true, als de werkers stopt: false
     */
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }

    /**
     * Method setVol zet de werkers op status vol true of false
     *
     * @param vol 
     */
    public void setVol(boolean vol)
    {
        this.vol = vol;
    }

    /**
     * Method isVol zegt of de werkers vol zit of niet
     *
     * @return vol boolean, true of false
     */
    public boolean isVol()
    {
        return vol;
    }

    /**
     * Method setInStructuur zet de werkers op status "in een structuur" true of false
     *
     * @param a gaat een werkers in een structuur: true, gaat een werkers uit een structuur: false
     */
    public void setInStructuur(boolean a)
    {
        inStructuur = a;

    }

    /**
     * Method inStructuur zegt of de werkers in een structuur zit of niet
     *
     * @return inStructuur boolean,true of false
     */
    public boolean inStructuur()
    {
        return inStructuur;
    }

    /**
     * Method getTask geeft het type grondstof dat de werkers draagt
     *
     * @return task Grondstof, voedsel - steen - hout - null
     */
    public Grondstof getTask()
    {
        return task;
    }

    public void setTask(Grondstof task) {
        this.task = task;
    }

    /**
     * Method getHuidigWerk geeft het object waar de werkers aan het verzamelen is
     *
     * @return huidigWerk Object, een Object of null
     */
    public Object getHuidigWerk()
    {
        return huidigWerk;
    }

    /**
     * Method setHuidigWerk geeft de huidige werkers een object of geen object waar hij moet gaan verzamelen
     *
     * @param object Object, een Object of null
     */
    public void setHuidigWerk(Object object)
    {
        huidigWerk = object;
    }

    /**
     * Method getNaam geeft de naam van de werkers
     *
     * @return naam String, kan null zijn
     */
    public String getNaam()
    {
        return naam;
    }

    public int getCoY() {
        return coY;
    }

    public void setCoY(int y) {
        this.coY = y;
    }

    public int getWorkerSpeed() {
        return workerSpeed;
    }

    public void setWorkerSpeed(int workerSpeed) {
        this.workerSpeed = workerSpeed;
    }

    public void setCoX(int coX) {
        this.coX = coX;
    }

    public MovePolicyBasic getMovePolicy() {
        return movePolicyObject;
    }

    public void setMovePolicy(MovePolicyBasic movePolicy) {
        this.movePolicyObject = movePolicy;
    }

    public double getCurrDirection() {
        return currDirection;
    }

    public void setCurrDirection(double currDirection) {
        this.currDirection = currDirection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Animation getAnimation() {
        return a;
    }

    public void setAnimation(Animation a) {
        this.a = a;
    }

    public PheromonePolicyBasic getPheromonePolicy() {
        return PheromonePolicy;
    }

    public void setPheromonePolicy(PheromonePolicyBasic pheromonePolicy) {
        PheromonePolicy = pheromonePolicy;
    }

    public void addDetectedPheromone(Pheromone p)
    {

        if(!isVisited(p))
            detectedPheromones.add(new CustomStruct(p));
    }

    public void addDetectedPheromone(Pheromone p, int dist)
    {

        if(!isVisited(p))
            detectedPheromones.add(new CustomStruct(p,dist));
    }

    public void addDetectedPheromone(CustomStruct s)
    {

        if(!isVisited(s.getPheromone()))
            detectedPheromones.add(s);
    }


    public void removeAllDetectedPhero()
    {
       /* Iterator<Pheromone> iter = detectedPheromones.iterator();
        while (iter.hasNext()) {

                iter.remove();

        }*/

        detectedPheromones.clear();
    }

    public void addVisitedPheromone(Pheromone p)
    {
        removePheroFromDetectedPheromones(p);
        visitedPheromones.add(p);
    }

    public void removePheroFromDetectedPheromones(Pheromone p)
    {
        //if(detectedPheromones.contains(p))
        //{
            Iterator<CustomStruct> iter = detectedPheromones.iterator();
            while (iter.hasNext()) {
                CustomStruct phero = iter.next();
                if(phero.getPheromone().equals(p) )
                    iter.remove();

            }
        //}

    }

    public void removeAllVisitedPhero()
    {
        visitedPheromones.clear();
        /*Iterator<Pheromone> iter = visitedPheromones.iterator();
        while (iter.hasNext()) {

            iter.remove();

        }*/
    }

    public boolean isVisited(Pheromone p)
    {
        return visitedPheromones.contains(p);
    }

    public Pheromone getCurrPheromone() {
        return currPheromone;
    }

    public void setCurrPheromone(Pheromone currPheromone) {
        this.currPheromone = currPheromone;
    }

    public int getDistLastPheroDrop() {
        return distLastPheroDrop;
    }

    public void setDistLastPheroDrop(int distLastPheroDrop) {
        this.distLastPheroDrop = distLastPheroDrop;
    }

    public ArrayList<CustomStruct> getDetectedPheromones() {
        return detectedPheromones;
    }

    public void setDetectedPheromones(ArrayList<CustomStruct> detectedPheromones) {
        this.detectedPheromones = detectedPheromones;
    }

    public ArrayList<Pheromone> getVisitedPheromones() {
        return visitedPheromones;
    }

    public void setVisitedPheromones(ArrayList<Pheromone> visitedPheromones) {
        this.visitedPheromones = visitedPheromones;
    }
}
