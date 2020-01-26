/**
 * Dit is de Command klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Deze klasse bevat informatie over een commando die is uitgegeven door de speler.
 * Een commando bestaat momenteel uit twee delen: een commandWord en een string.
 * (bijvoorbeeld, als het commando "pak sleutel" is, dan zijn de twee delen
 * PAK en "sleutel").
 * 
 * Als het commando alleen één woord bevat, is het tweede woord <null>
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;

    /**
     * Maakt een commando object aan. Eerste en tweede woorden moeten worden verstrekt, maar
     * het tweede woord mag null zijn.
     * @param commandWord Het CommandWord. UNKNOWN als het commando niet herkend werd.
     * @param secondWord Het tweede woord van de het commando. Mag null zijn.
     */
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Retourneer het commando woord (het eerste woord) van dit commando.
     * @return Het commando woord.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return Het tweede woord van dit commando. Retouneert null als er geen
     * tweede woord is.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true als het commando niet werd begrepen.
     */
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * @return true als het commando een tweede woord bevat.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

