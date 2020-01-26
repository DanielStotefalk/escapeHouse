/**
 * Dit is de GameMain klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 *
 * Door middel van deze klasse kan je het spel van 
 * buiten blueJ spelen.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class GameMain
{
    private int x;
    
    /**
     * Constructor voor objecten van klasse GameMain
     */
    public GameMain()
    {
        x = 0;
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}
