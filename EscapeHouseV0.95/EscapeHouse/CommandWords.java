import java.util.HashMap;

/**
 * Dit is de CommandWords klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Deze klasse bevat een opsomming van alle commando woorden die het spel kent.
 * Het wordt gebruikt om opdrachten te herkennen tijdens het typen.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialiseer de commando woorden.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Zoek het CommandWord dat is gekoppeld aan een commando woord.
     * @param commandWord Het woord om op te zoeken.
     * @return Het CommandWord komt overeen met commandWord, of UNKNOWN
     *         als het geen geldig commando is.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }

    /**
     * Controleer of een gegeven String een geldig commando woord is.
     * @return true als het waar is, false als het niet waar is.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print alle geldige commandos naar System.out.
     */
    public void showAll() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
