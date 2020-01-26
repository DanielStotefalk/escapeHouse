import java.util.Scanner;

/**
 * Dit is de Parser klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Deze parser leest gebruikersinvoer en probeert deze te interpreteren als een "Adventure"
 * commando. Telkens wanneer het wordt aangeroepen, leest het een regel uit de terminal en
 * probeert de regel te interpreteren als een commando van twee woorden. Het retouneert het commando
 * als een object van klasse Command.
 * 
 * De parser heeft een set bekende commando woorden. Het controleert gebruikersinvoer tegen
 * de bekende commando's, en als de invoer niet een van de bekende commando's is, 
 * retouneert het een commando object dat is gemarkeerd als een unknown commando.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class Parser 
{
    private CommandWords commands; 
    private Scanner reader;      

    /**
     * Maakt een parser om het terminal window te lezen.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return Het volgende commando van de gebruiker.
     */
    public Command getCommand() 
    {
        String inputLine;   
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     

        inputLine = reader.nextLine();

        
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      
               
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * Print een lijst van geldige commando woorden.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
