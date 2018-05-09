package src;

import MAS_classes.MovePolicyBasic;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Deze klasse zorgt voor de rangeschikking en het maken van de knoppen, labels en tekstvelden
 * De MainThread,GatherThread,MoveThread en AnEnigneersQuest klassen gebruiken dit object
 * Deze klasse gebruikt de klassen View en Model.
 * erft van Panel en if i mplementeert ActionListener.
 * belangerijkste methode is performed()
 * 
 * 
 * @author Christiaan Vanbergen 
 * @version V12
 */
public class WindowView extends Panel implements ActionListener
{

    protected View view;    // de view van het spel
    protected Model model;  // de model van het spel
    private Panel panelboven;   // het panel met labels in het noorden 
    private Panel panelonder;   // het panel met knoppen, labels en tekstvelden in het zuiden
    private Button bouwMode;    // de knop voor de bouwmode aan en uit te schakelen
    private Button maakWerker;  // de knop om een workers aan te maken
    private Button help;        // de knop om de help functie aan te zetten of afzetten
    private TextField naamWerker;   // het veld waar de naam van een nieuwe workers kan in gevoert worden
    private Label infoNaamWerker;   // extra informatie over het vorige tekst veld
    private Button hout;    // de knop die de grondstofmode naar hout zet of af zet 
    private Button steen;   // de knop die de gronstofmode naar steen zet of af zet
    private Label  infoGrondstof;   // extra informatie over de 2 vorige knoppen
    private Label hout1;    // het label die de hoeveelheid hout weergeeft
    private Label  steen1;  // het label die de hoeveelheid steen weergeeft
    private Label  voedsel; // het label die de hoeveelheid voedsel weergeeft
    private Button restart; // de knop om opnieuw te beginnnen
    private Button start; // de knop om te beginnnen

    /**
     * Constructor voor objecten van WindowView, deze maakt een nieuwe View aan en zet zijn eigen layout
     * naar borderLayout, Maakt alle knoppen,label en tekst velden aan met de beginteksten erbij, voegt 
     * aan de knoppen en de actionListener(dit object) toe en voegt ieder component aan de juiste panel
     * en voegt de panels op hun beurt toe aan de juist coordinatie en de view in het midden deze krijgt
     * alle resterende plaats.
     *
     * @param m het model dat aangemaakt wordt in MAS_autoAntIndustry
     */
    public WindowView(Model m)
    {
        view = new View(m);
        model = m;
        this.setLayout(new BorderLayout());
        bouwMode = new Button("Bouwmode: off");
        maakWerker = new Button("Maak workers kost: 10 voedsel");
        help = new Button(" help: on ");
        naamWerker = new TextField("workers 1",15);
        infoNaamWerker = new Label("De naam van de nieuwe workers: ");
        hout = new Button(" Hout: off ");
        steen = new Button(" Steen: off ");
        infoGrondstof = new Label ("Resource: ");
        hout1 = new Label ("Hout: "+ String.valueOf(model.getAmountWood()));
        steen1 = new Label ("Steen: "+ String.valueOf(model.getAmountStone()));
        voedsel = new Label ("Voedsel: "+ String.valueOf(model.getAmountFood()));
        restart = new Button("Restart");
        start = new Button("start");
        bouwMode.addActionListener(this);
        maakWerker.addActionListener(this);
        hout.addActionListener(this);
        steen.addActionListener(this);
        help.addActionListener(this);
        restart.addActionListener(this);
        start.addActionListener(this);
        panelboven = new Panel();
        panelboven.setLayout(new FlowLayout());
        panelboven.add(hout1);
        panelboven.add(steen1);
        panelboven.add(voedsel);
        this.add(panelboven,BorderLayout.NORTH);

        panelonder = new Panel();
        panelonder.setLayout(new FlowLayout());
        panelonder.add(start);
        //panelonder.add(restart);
        panelonder.add(bouwMode);
        panelonder.add(maakWerker);
        panelonder.add(infoNaamWerker);
        panelonder.add(naamWerker);
        panelonder.add(infoGrondstof);
        panelonder.add(hout);
        panelonder.add(steen);
        panelonder.add(help);
        help.setBackground(Color.blue);

        this.add(panelonder,BorderLayout.SOUTH);
        this.add(view);
    }

    /**
     * Method actionPerformed de methode die reageert op het klikken van de knoppen.
     * Bij het drukken van maakWerker moet het hoofdgebouw geselecteerd zijn en dan wordt er 10 voedsel 
     * af getrokken van de hoeveelheid voedsel in model, een nieuwe workers gemaakt op de coordinaten van
     * het hoofdgebouw en de parameters up gedate, de naam van de workers wordt + 1 gedaan.
     * bij het drukken van bouwmode als bouwmode af is, wordt die aan en wordt de knop rood, 
     * als bouwmode aan is wordt deze af en wordt de knop wit.
     * bij het drukken van hout wordt grondstofmode in model hout en wordt de knop bruin en knop steen
     * wordt wit.
     * bij het drukken van steen gebeurt het tegenovergestelde van hout alleen wordt steen grijs en wordt 
     * de gronstof mode in model steen, 2 keer klikken op een knop maakt geeen enkele grondstof actief.
     * bij elke knop veranderd de tekst op de knop mee met de status "on" of "off".
     *
     * @param e ActionEvent,    een actie door eenderwelke soort component
     */
    public void actionPerformed(ActionEvent e)
    {
        if(model.getSelectedObject() instanceof Building)
        {
            Building b = (Building) model.getSelectedObject();
            if( e.getSource() == maakWerker && b.getFunction() == Function.hoofdgebouw)
            {
                if(model.getAmountFood()-10 >= 0)
                {
                    model.addWorker(b.getX()*view.getZ(),naamWerker.getText());
                    model.setAmountFood(-10);
                    updateParameters();
                    naamWerker.setText("workers " + String.valueOf(model.getSizeWerkers()+1));
                }
            }
        }
        if( e.getSource() == bouwMode && model.isBouwMode()== false)
        {
            model.setBouwMode(true);
            bouwMode.setLabel("Bouwmode: on");
            bouwMode.setBackground(Color.RED);
        }
        else if(e.getSource() == bouwMode)
        {
            model.setBouwMode(false)  ;
            bouwMode.setLabel("Bouwmode: off");
            bouwMode.setBackground(Color.WHITE);
        }
        if( e.getSource() == hout && model.getGrondstofmode()!= Task.hout)
        {
            model.setTaskMode(Task.hout);
            hout.setLabel("hout: on");
            hout.setBackground(new Color(139,90,43 ));
            steen.setLabel("steen: off");
            steen.setBackground(Color.WHITE);
        }
        else if(e.getSource() == hout)
        {
            model.setTaskMode(null)  ;
            hout.setLabel("hout: off");
            hout.setBackground(Color.WHITE);
        }
        if( e.getSource() == steen && model.getGrondstofmode()!= Task.steen)
        {
            model.setTaskMode(Task.steen);
            steen.setLabel("steen: on");
            steen.setBackground(Color.gray);
            hout.setLabel("hout: off");
            hout.setBackground(Color.WHITE);
        }
        else if(e.getSource() == steen)
        {
            model.setTaskMode(null)  ;
            steen.setLabel("steen: off");
            steen.setBackground(Color.WHITE);

        }
        if(e.getSource() == help)
        {
            if(model.isHulp())
            {
                model.setHulp(false);
                help.setLabel(" help: off ");
                help.setBackground(Color.WHITE);
            }
            else
            {
                model.setHulp(true);
                help.setLabel(" help: on ");
                help.setBackground(Color.blue);
            }
        }
        if(e.getSource() == restart)
        {
            model.restart();
            view.repaint();
            hout.setLabel("hout: off");
            hout.setBackground(Color.WHITE);
            steen.setLabel("steen: off");
            steen.setBackground(Color.WHITE);
            bouwMode.setLabel("Bouwmode: off");
            bouwMode.setBackground(Color.WHITE);
            view.maakPad();
            updateParameters();
            
        }
        if(e.getSource() == start)
        {
            ArrayList<Worker> workers = model.getWorkers();
            for(int i=0;i<model.getSizeWerkers();i++)
            {
                Worker w = workers.get(i);
                w.setMovePolicy(new MovePolicyBasic(w,50));
                MoveThread m = new MoveThread(w,this,null,model);

            }
        }

    }

    /**
     * Method addMouseListener voegt een MouseListener toe aan de view
     *
     * @param a MouseListener,  de MouseListener die moet tegevoegt worden
     *
     */
    public void addMouseListener(MouseListener a)
    {
        view.addMouseListener(a);

    }

    /**
     * Method addMouseMotionListener voegt een MouseMotionListener toe aan de view
     *
     * @param a MouseMotionListener,  de MouseMotionListener die moet tegevoegt worden
     *
     */
    public void addMouseMotionListener(MouseMotionListener a)
    {
        view.addMouseMotionListener(a);

    }

    /**
     * Method addMouseKeyListener voegt een MouseKeyListener toe aan de view
     *
     * @param a MouseKeyListener,  de MouseKeyListener die moet tegevoegt worden
     *
     */
    public void addKeyListener (KeyListener a)
    {
        view.addKeyListener(a);

    }

    /**
     * Method repaint voert de repaint methode op de view uit
     *
     */
    public void repaint()
    {
        view.repaint();
    }

    /**
     * Method setBackground veranderd de achtergrond van de view naar de bijgegeven kleur.
     *
     * @param c Color, de nieuwe kleur
     */
    public void setBackground(Color c)
    {
        view.setBackground(c);
    }

    /**
     * Method getZ geeft de Z uit de view terug
     *
     * @return view.getZ() int,   de zijde van het rooster
     */
    public int getZ()
    {
        return view.getZ();
    }

    /**
     * Method getSizeY geeft de SizeY uit de view terug
     *
     * @return de grootte van het scherm in de y richting
     */
    public int getSizeY()
    {
        return view.getSizeY();
    }

    /**
     * Method updateParameters vernieuwt alle parameters in de labelen in panelboven
     *
     */
    public void updateParameters()
    {
        hout1.setText("Hout: "+ String.valueOf(model.getAmountWood()));
        steen1.setText("Steen: "+ String.valueOf(model.getAmountStone()));
        voedsel.setText("Voedsel: "+ String.valueOf(model.getAmountFood()));
    }

    /**
     * Method getPadWaarde geeft de y waarde die bij een x waarde hoort. wordt door de controler en view gebruikt
     *
     * @param x int    de x waarde waar de y waarde van geweten wil worden tussen 0 en landschap.getX()*50
     * @return pad[x] int   de y waarde die bij x hoort tussen 70-770  
     */
    public int getPadWaarde( int x)
    {
        return view.getPadWaarde(x);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
