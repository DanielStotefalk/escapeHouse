import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

/**
 * Dit is de hoofd klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * Gebruikers kunnen rondlopen in het huis en kunnen
 * voorwerpen oppakken om zo verder te komen.
 * 
 * Om te beginnen met spelen creëer je een object van deze
 * klasse en roep je de functie "play" aan.
 * 
 * Deze hoofd klasse voegt alle andere klasses bij elkaar
 * zodat er een spel wordt gemaakt aan de hand van alle 
 * klasses. Het maakt alle kamers aan, de parser en start
 * het spel. De commando's die de parser opslaat voert die
 * uit.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class Game 
{
    private Parser parser;
    private Room trackRoom;
    private Room previousRoom;
    private Stack<Room> roomHistory;
    private Room currentRoom;
    ArrayList<Item> inventory = new ArrayList<Item>();
    Room slaapkamer, opbergkast, overloop, badkamer, glazenkast, hal, woonkamer, keuken, aanrecht, gKelder, kelder, buiten;

    /**
     * Maakt het spel aan en initialiseert de interne map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<Room>();
    }
    
    /**
     * Laat je het spel van buiten blueJ starten.
     * Dit kan via de Opdracht prompt.
     */
    public static void main (String[] args)
    {
        Game game = new Game();
        game.play();
    }

    /**
     * Maakt alle kamers aan en koppelt hun uitgangen aan elkaar.
     */
    private void createRooms()
    {
        // maak de kamers aan
        slaapkamer = new Room("de slaapkamer waar je wakker bent geworden. Er is een oud bed met gebaricadeerde ramen en de muur ziet er zwak uit");
        opbergkast = new Room("de ruimte waar je bent gekomen door, door de muur heen te kruipen. Het blijkt een opbergkast te zijn");
        overloop = new Room("de overloop op de eerste verdieping, je ziet een trap naar beneden, een badkamer, de opbergkast en een dicht getimmerde deur van de slaapkamer");
        badkamer = new Room("de badkamer op de eerste verdieping. Je ziet een glazen kast opgehangen aan de muur, het lijkt alsof er iets glimt in de kast");
        glazenkast = new Room("staat om alles te doen om te ontsnappen. Je pakt je hamer en slaat de glazen kast in 1000 scherven!" + "\n" + "Er is een glimmend voorwerp op de grond gevallen... hier ligt een sleutel!");
        hal = new Room("de hal op de begaande grond, je ziet de trap van boven, een deur van de kelder dat op slot is, de voordeur dat op slot is met een gouden slot, de keuken en de woonkamer");
        woonkamer = new Room("de woonkamer, het ziet er erg oud uit en het lijkt alsof de meubelen uit een ander eeuw komen. Wanneer je door de kamer stapt merk je dat de vloer erg zwak is, je zegt tegen jezelf: 'Gelukkig ben ik afgevallen anders was ik heir echt door de vloer gezakt'");
        keuken = new Room("de keuken van het huis, het ziet er verlaten uit en het lijkt alsof er al in jaren niks meer gekookt is. Je ziet wat borden en bestek overal liggen en een grote oude fles wijn");
        aanrecht = new Room("een nieuwschierige bui gaan kijken naar het fles wijn op het aanrecht, hij is erg zwaar");
        kelder = new Room("een pijnlijke positie terecht gekomen in de kelder van het huis... door het gewicht van de fles wijn ben je door de vloer gezakt! ");
        gKelder = new Room("een geheime ruimte in de kelder met een wijnkast. Hey! Het valt je op dat er precies 1 fles wijn mist uit de wijnkast");
        buiten = new Room("het einde van het spel");

        // initialiseert de kamer uitgangen
        slaapkamer.setExit("muur", opbergkast);
        opbergkast.setExit("slaapkamer", slaapkamer);
        opbergkast.setExit("overloop", overloop);
        overloop.setExit("opbergkast", opbergkast);
        overloop.setExit("trap", hal);
        overloop.setExit("badkamer", badkamer);
        badkamer.setExit("overloop", overloop);
        badkamer.setExit("glazenkast", glazenkast);
        glazenkast.setExit("badkamer", badkamer);
        hal.setExit("trap", overloop);
        hal.setExit("woonkamer", woonkamer);
        hal.setExit("kelder", kelder);
        hal.setExit("voordeur", buiten);
        hal.setExit("keuken", keuken);
        keuken.setExit("hal", hal);
        keuken.setExit("aanrecht", aanrecht);
        aanrecht.setExit("keuken", keuken);
        woonkamer.setExit("hal", hal);
        woonkamer.setExit("vloer", kelder);
        kelder.setExit("geheim", gKelder);
        kelder.setExit("trap", hal);
        gKelder.setExit("kelder", kelder);
        
        // start het spel in de slaapkamer
        currentRoom = slaapkamer;
        
        // brengt je terug naar de vorige kamer waar je bent geweest
        previousRoom= slaapkamer;
        
        // plaatst een voorwerp in een kamer, (naam, gewicht, oppakbaar)
        slaapkamer.setItem(new Item("hamer", 1, true));
        slaapkamer.setItem(new Item("speelgoedpop", 0, false));
        slaapkamer.setItem(new Item("kast", 0, false));
        opbergkast.setItem(new Item("kleren", 0, false));
        opbergkast.setItem(new Item("doos", 0, false));
        overloop.setItem(new Item("lamp", 0, false));
        overloop.setItem(new Item("schilderij", 0, false));
        badkamer.setItem(new Item("toiletborstel", 1, false));
        badkamer.setItem(new Item("badeend", 0, false));
        hal.setItem(new Item("stoel", 0, false));
        hal.setItem(new Item("kapstok", 0, false));
        hal.setItem(new Item("jas", 0 , false));
        hal.setItem(new Item("plant", 0 ,false));
        keuken.setItem(new Item("mes", 1, true));
        keuken.setItem(new Item("koelkast", 0, false));
        keuken.setItem(new Item("bier", 1, true));
        keuken.setItem(new Item("fleswijn", 50, true));
        woonkamer.setItem(new Item("tv", 0, false));
        woonkamer.setItem(new Item("kaarsen", 1, true));
        woonkamer.setItem(new Item("magazine", 0, false));
        gKelder.setItem(new Item("goudensleutel", 1, true));
        gKelder.setItem(new Item("kruk", 0, false));
        gKelder.setItem(new Item("handboeien", 0, false));
        gKelder.setItem(new Item("doek", 0, false));
        gKelder.setItem(new Item("botten", 0 ,false));
        kelder.setItem(new Item("chefmes", 0, false));
        kelder.setItem(new Item("etenstray", 0, false));
        glazenkast.setItem(new Item("sleutel", 1, true));
    }

    /**
     *  Hoofd speel routine. Lust tot het einde van het spel.
     */
    public void play() 
    {            
        printWelcome();
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Bedankt voor het spelen.  Tot de volgende keer.");
    }

    /**
     * Drukt de opgeningszin van het spel af voor de speler.
     * En vraagt om de naam van de speler.
     */
    private void printWelcome()
    {
        Scanner myScanner;
        myScanner = new Scanner(System.in);
        String playerName;
        
        System.out.println();
        System.out.println("Welkom in het Escape House");
        System.out.println("Je wordt wakker in een onbekende kamer waar de ramen dicht gebaricadeerd zijn.");
        System.out.println("Het doel is om te ontsnappen uit het huis.");
        System.out.println("Vul je naam in: ");
        playerName = myScanner.nextLine();
        System.out.println("Welkom " + playerName + ", lukt het jou om te ontsnappen uit het huis?");
        
        System.out.println("Typ '" + CommandWord.HELP + "' als je hulp nodig hebt.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
       
        
    }

    /**
     * Gegeven een commando, verwerk (dat wil zeggen: voer uit) het commando.
     * @param command Het commando die uitgevoerd moet worden.
     * @return true Als het commando het spel eindigt, anders false.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
            System.out.println("Ik weet niet wat je bedoelt...");
            break;

            case HELP:
            printHelp();
            break;

            case GO:
            goRoom(command);
            break;

            case INVENTORY:
            printInventory();
            break;

            case GET:
            getItem(command);
            break;

            case DROP:
            dropItem(command);
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            case BACK:
            goBack();
            break;
        }
        return wantToQuit;
    }

    // implementaties van gebruikersopdrachten:
    
    /**
     * Als de speler terug wil gaan terwijl de speler nog niet verder is gekomen dan de slaapkamer
     * dan kan de speler niet verder terug.
     * Anders gaat de speler terug naar de vorige bezochte kamer.
     */
    private void goBack()
    {
        if(roomHistory.empty())
        { System.out.println ("Je kan niet verder terug");
        } else {
            currentRoom = roomHistory.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Als de speler probeert naar een richting te gaan, heeft die eerst een voorwerp nodig om door te kunnen.
     * Kijkt in welke kamer je bent en naar welke kamer je wil en of je het voorwerp hebt.
     */
    private void goSlaapkamer2()
    {
        if((currentRoom == slaapkamer) && (checkInventory("hamer"))){
            currentRoom = opbergkast;
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("Er is nergens een deur te bekennen... De muren van het huis zijn oud...");
        }
    }

    private void goGlazenkast() 
    {
        if((currentRoom == overloop) && (checkInventory("sleutel"))){
            currentRoom = hal;
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("Je hebt een sleutel nodig om de deur naar bedenen te openen! ");
        }
    }

    private void goKelder() 
    {
        if((currentRoom == woonkamer) && (checkInventory("fleswijn"))){
            currentRoom = kelder;
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("Je loopt over de vloer... en bedenkt je dat als je zwaarder was je er misschien wel doorheen zakt! ");
        }
    }

    private void goBuiten()
    {
        if((currentRoom == hal) && (checkInventory("goudensleutel"))){
            currentRoom = buiten;
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("De deur zit op slot met een gouden slot.");
        }
    }

     /**
     * Print het voorwerp dat de speler in zijn inventaris heeft uit.
     */
    private void printInventory()
    {
        String output = "";
        for(int i = 0; i < inventory.size(); i++)
        {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("Je draagt bij je:");
        System.out.println(output);
    }

     /**
     * Kijkt of de speler iets in zijn inventaris heeft.
     * @param string Het item string dat gecheckt moet worden.
     * @return True als de speler een voorwerp in zijn inventaris heeft
     * anders false.
     */
    private boolean checkInventory(String string) {

        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getDescription() == string){
                return true;
            } 

        }
        return false;
    }
    
    /** 
     * Als je "help" intypt, krijg je dit bericht.
     */
    private void printHelp() 
    {
        System.out.println("Je bent helemaal alleen in een onbekend huis.");
        System.out.println("En je moet weten te ontsnappen.");
        System.out.println();
        System.out.println("De commands dat je kan gebruiken zijn:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * De speler probeert een kant op te gaan, als er een uitgang is, dan
     * komt de speler in de nieuwe kamer. Als er geen uitgang is print er 
     * een error bericht. 
     * 
     * Als de speler een voorwerp bij zich heeft en in de juiste kamer is
     * kan die door naar de volgende, indien de speler geen voorwerp heeft of
     * de speler is niet in de juiste kamer, gaat de speler niet naar de volgende kamer.
    */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // als er geen woord acher ga staat, stuur dit bericht
            System.out.println("Waarheen?");
            return;
        }

        String direction = command.getSecondWord();

        // probeer om de huidige kamer te verlaten
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Hier is geen deur!");
        }

        else if((currentRoom == slaapkamer) && (nextRoom == opbergkast)){
            goSlaapkamer2();
        } else if((currentRoom == overloop) && (nextRoom == hal)) {
            goGlazenkast(); 
        } else if((currentRoom == woonkamer) && (nextRoom == kelder)) {
            goKelder();
        } else if ((currentRoom == hal) && (nextRoom == buiten)) {
            goBuiten();
        } else {
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Pakt een voorwerp uit een kamer als er een voorwerp is.
     * @param command Het commando dat uitgevoerd moet worden.
    */
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Pak wat op?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);
/*
        int maxweight = 100;
        int totalweight = 0;

        for (int i = 0; 1 < inventory.size(); i++)
        {
            totalweight += inventory.get(i).getWeight();
        }
*/
        if (newItem == null) 
        {
            System.out.println("\n" + "Dat voorwerp is hier niet!"); 
        } 
        else if (newItem.getPickup() == false)
        {   
            System.out.println("\n" + "Dat voorwerp kan niet opgepakt worden.");
        } 
/*        else if (totalweight >= maxweight)
        {
            System.out.println("\n" + "Je inventaris zit vol, laat eerst iets achter om ruimte te maken.");
        } */
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("\n" + item + " opgepakt.");
        }
    }

    /**
     * Laat een voorwerp vallen, indien je het voorwerp hebt.
     * @param command Het commando dat uitgevoerd moet worden.
    */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Laat wat vallen?");
            return;
        } 
        else
        {
            String item = command.getSecondWord();
            Item newItem = null;
            int index = 0;
            int weight = 0;

            for (int i = 0; i < this.inventory.size(); i++)
            {
                if (((Item)this.inventory.get(i)).getDescription().equals(item)) 
                {
                    newItem = (Item)this.inventory.get(i);
                    index = i;
                    weight = ((Item)this.inventory.get(i)).getWeight();
                }
            }

            if (newItem == null) {
                System.out.println("Dat voorwerp zit niet in je inventaris!");
            }
            else {
                inventory.remove(index);
                currentRoom.setItem(new Item(item, weight, true));
                System.out.println(item + " laten vallen.");
            }
        }
    }

    /**
     * "Stop" is ingevoerd. Bekijkt de rest van het commando om te zien
     * of de speler echt wil stoppen.
     * @param command Het commando dat uitgevoerd moet worden.
     * @return true, als het commando het spel stopt, anders false.
    */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Wilt u stoppen?");
            return false;
        }
        else {
            return true;  // signaal dat de speler wil stoppen
        }
    }
}
