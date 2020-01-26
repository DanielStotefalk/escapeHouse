/**
 * Dit is de Item klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Een "Item" staat voor een voorwerp in het spel.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class Item
{
    private String description;
    private int weight;
    private boolean pickup;
    
    /**
     * Maakt een voorwerp aan met een gewicht.
     * @param newdescription De beschrijving het voorwerp.
     *        newweight Het gewicht van het voorwerp.
     *        newpickup Kan je het voorwerp oppakken, ja of nee.
     */
    public Item(String newdescription, int newweight, boolean newpickup)
    {
        description = newdescription;
        weight = newweight;
        pickup = newpickup;
    }
    
    
    /**
     * Geeft een beschrijving van het voorwerp.
     * @return beschrijving van het voorwerp.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Geeft het gewicht van het voorwerp.
     * @return gewicht van het voorwerp.
     */    
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Geeft aan of je het voorwerp op kan pakken, ja of nee.
     * @return ja of nee of je hebt voorwerp kan oppakken.
     */      
    public boolean getPickup()
    {
        return pickup;
    }

}