
/**
 * class Item - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Item
{
    
    String description;
    int weigth;
    
    //constructor
    public Item(String newdescription)
    {
        description = newdescription;
    }
    
    public String getDescription()
    {
        return description;
    }
}