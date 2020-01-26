import java.util.ArrayList;
import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 26
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
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
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<Room>();
    }

    public static void main (String[] args)
    {
        Game game = new Game();
        game.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        slaapkamer = new Room("de slaapkamer waar je wakker bent geworden. Er is een oud bed met gebaricadeerde ramen en de muur ziet er zwak uit");
        opbergkast = new Room("de ruimte waar je bent gekomen door, door de muur heen te kruipen. Het blijkt een opbergkast te zijn");
        overloop = new Room("de overloop op de eerste verdieping, je ziet een trap naar beneden, een badkamer, de opbergkast en een dicht getimmerde deur van de slaapkamer");
        badkamer = new Room("de badkamer op de eerste verdieping. Je ziet een glazen kast opgehangen aan de muur, het lijkt alsof er iets glimt in de kast");
        glazenkast = new Room("staat om alles te doen om te ontsnappen. Je pakt je hamer en slaat de glazen kast in 1000 scherven! Er is een glimmend voorwerp op de grond gevallen... hier ligt een sleutel!");
        hal = new Room("de hal op de begaande grond, je ziet de trap van boven, een deur van de kelder dat op slot is, de voordeur dat op slot is met een gouden slot, de keuken en de woonkamer");
        woonkamer = new Room("de woonkamer, het ziet er erg oud uit en het lijkt alsof de meubelen uit een ander eeuw komen. Wanneer je door de kamer stapt merk je dat de vloer erg zwak is, je zegt tegen jezelf: 'Gelukkig ben ik afgevallen anders was ik heir echt door de vloer gezakt'");
        keuken = new Room("de keuken van het huis, het ziet er verlaten uit en het lijkt alsof er al in jaren niks meer gekookt is. Je ziet wat borden en bestek overal liggen en een grote oude fles wijn");
        aanrecht = new Room("een nieuwschierige bui gaan kijken naar het fles wijn op het aanrecht, hij is erg zwaar");
        kelder = new Room("een pijnlijke positie terecht gekomen in de kelder van het huis... door het gewicht van de fles wijn ben je door de vloer gezakt! ");
        gKelder = new Room("een geheime ruimte in de kelder met een wijnkast. Hey! Het valt je op dat er precies 1 fles wijn mist uit de wijnkast");
        buiten = new Room("het einde van het spel");

        // initialise room exits

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
        //kelder.setExit("boven", woonkamer);
        kelder.setExit("geheim", gKelder);
        kelder.setExit("trap", hal);
        gKelder.setExit("kelder", kelder);

        currentRoom = slaapkamer;  // start het spel in de slaapkamer

        previousRoom= slaapkamer;

        //inventory.add(new Item("Breekijzer"));
        slaapkamer.setItem(new Item("hamer"));
        glazenkast.setItem(new Item("sleutel"));
        aanrecht.setItem(new Item("fleswijn"));
        gKelder.setItem(new Item("goudensleutel"));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Bedankt voor het spelen.  Tot de volgende keer.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welkom in het Escape House");
        System.out.println("Je wordt wakker in een onbekende kamer waar de ramen dicht gebaricadeerd zijn.");
        System.out.println("Het doel is om te ontsnappen uit het huis.");
        System.out.println("Typ '" + CommandWord.HELP + "' als je hulp nodig hebt.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
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

    // implementations of user commands:

    private void goBack()
    {
        if(roomHistory.empty())
        { System.out.println ("Je kan niet verder terug");
        } else {
            currentRoom = roomHistory.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean checkInventory(String string) {

        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getDescription() == string){
                return true;
            } 

        }
        return false;
    }

    /**
     * Als de speler probeert naar een richting te gaan, heeft die eerst een voorwerp nodig om door te kunnen.
     * Checkt in welke kamer je bent en naar welke kamer je wil en of je het voorwerp hebt.
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
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
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
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Waarheen?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
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
     * Get an item from the room if its there.
     */

    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Pak wat op?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("Dat voorwerp is hier niet!"); 
        }
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println(item + " opgepakt.");
        }
    }

    /**
     * Drop an item if you have it.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Laat wat vallen?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = null;
        int index = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getDescription().equals(item)) {
                newItem = inventory.get(i);
                index = i;
            }
        }

        if (newItem == null) {
            System.out.println("Dat voorwerp zit niet in je inventaris!");
        }
        else {
            inventory.remove(index);
            currentRoom.setItem(new Item(item));
            System.out.println(item + " laten vallen.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Wilt u stoppen?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
