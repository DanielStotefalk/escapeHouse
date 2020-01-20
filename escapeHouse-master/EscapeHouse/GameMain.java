
/**
 * class GameMain - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class GameMain
{
    // instance variables - vervang deze door jouw variabelen
    private int x;

    /**
     * Constructor voor objects van class GameMain
     */
    public GameMain()
    {
        // geef de instance variables een beginwaarde
        x = 0;
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}
