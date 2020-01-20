import java.util.ArrayList;
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
    private Room currentRoom;
    ArrayList<Item> inventory = new ArrayList<Item>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
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
        Room slaapkamer, slaapkamer2, overloop, badkamer, hal, woonkamer, keuken, gKelder, kelder, buiten;
      
        // create the rooms
        slaapkamer = new Room("de slaapkamer waar je wakker bent geworden");
        slaapkamer2 = new Room("de slaapkamer waar je door de muur gekomen bent");
        overloop = new Room("de overloop op de eerste verdieping");
        badkamer = new Room("de badkamer op de eerste verdieping");
        hal = new Room("de hal op de begaande grond");
        woonkamer = new Room("de woonkamer met een krakende vloer");
        keuken = new Room("de keuken van het huis");
        gKelder = new Room("een geheime ruimte in de kelder met een wijnkast");
        kelder = new Room("de kelder van het huis");
        buiten = new Room("het einde van het spel");
        
        // initialise room exits
        
        slaapkamer.setExit("oost", slaapkamer2);
        slaapkamer2.setExit("west", slaapkamer);
        slaapkamer2.setExit("zuid", overloop);
        overloop.setExit("noord", slaapkamer2);
        overloop.setExit("beneden", hal);
        overloop.setExit("west", badkamer);
        badkamer.setExit("oost", overloop);
        hal.setExit("boven", overloop);
        hal.setExit("noord", woonkamer);
        hal.setExit("beneden", kelder);
        hal.setExit("zuid", buiten);
        hal.setExit("west", keuken);
        woonkamer.setExit("zuid", hal);
        kelder.setExit("boven", hal);
        kelder.setExit("noord", gKelder);
        keuken.setExit("oost", hal);
        gKelder.setExit("zuid", kelder);
        buiten.setExit("noord", hal);
        
        /*
        -------------------------------------------------
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        */

        currentRoom = slaapkamer;  // start game outside
        
        //inventory.add(new Item("Breekijzer"));
        slaapkamer.setItem(new Item("breekijzer"));
        keuken.setItem(new Item("fles wijn"));
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
        System.out.println("Welkom bij het spel Escape House");
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
        }
        return wantToQuit;
    }

    // implementations of user commands:
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
        else {
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
            System.out.println("Dat voorwerp is niet hier!");
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
