package src;

import java.awt.*;
import java.awt.event.*;
/**
 * Deze klasse is een verzamelklassen voor het hele spel hij gebruikt de klassen: Model, GroteView,MainThread,Controller
 * De hoofdbedoeling van deze klasse is dat met de main methode het spel venster kan geopend worden
 * 
 * @author Christiaan Vanbergen
 * @version V9
 */
public class AnEngineersQuest extends Frame
{
    private Model model;
    private GroteView view;
    private Controller controller;
    private MainThread main;


    /**
     * Constructor voor de Game, er word een niew Model, Grote View,MainThread, Controller aangemaakt
     * de view wordt tegevoegt aan deze frame
     * de grootte van het scherm wordt ingesteld
     * de achtergrond wordt direct al naar de dag kleur gezet
     * er wordt een windowslistener met een methode om alles weg te doen en het scherm zonder fouten te sluiten aan deze frame toegevoegt 
     * 
     */
    public AnEngineersQuest()
    {
        model = new Model();
        view = new GroteView(model);
        main = new MainThread(model,view);
        add(view);
        controller = new Controller(model,view);
        this.setSize(1000,view.getSizeY()) ;
        view.setBackground(new Color(240,120,70 ));

        controller.startWerkers();

        this.addWindowListener( new WindowAdapter() 
            {
                public void windowClosing(WindowEvent e) 
                {
                    dispose();
                    System.exit(0);
                }
            });
    }

    /**
     * Method main: voor het uitvoeren van het spelletje
     * hierin word een nieuw game aangemaakt en de title is "An Engineers Quest" het scherm wordt zichtbaar gezet, De venstergrootte is niet veranderbaar
     *
     * @param args A parameter
     */
    public static void main(String[] args)
    {
        AnEngineersQuest venster = new AnEngineersQuest();
        venster.setTitle(" Autonomous ant colonisation");
        venster.setVisible(true);
        venster.setResizable(false);
    }
    
}
