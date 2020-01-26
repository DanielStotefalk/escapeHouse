/**
 * Dit is de CommandWord klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Hier staan alle geldige commando's van het spel
 * samen met een string met de nederlandse taal.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public enum CommandWord
{
    
    GO("ga"), 

    QUIT("stop"), 

    HELP("help"), 

    INVENTORY("inventaris"),

    GET("pak"),

    DROP("drop"),

    BACK("terug"),

    UNKNOWN("?");

    private String commandString;

    /**
     * Initialiseer met het bijbehorende commando.
     * @param commandString Het commando string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return Het commando als een string.
     */
    public String toString()
    {
        return commandString;
    }
}
