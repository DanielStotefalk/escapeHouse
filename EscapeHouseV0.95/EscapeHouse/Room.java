import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Dit is de room klasse van het spel "Escape House".
 * "Escape House" is een text based ontsnappings spel.
 * 
 * Een "Room" staat voor een locatie in het spel. Het is
 * verbonden met andere kamers via uitgangen. Voor elk bestaande
 * uitgang, slaat de room een verwijzing op in de neighbor room.
 * 
 * @auteur  Jelmer Huisman en Daniël Stöterfalk
 * @versie 26-1-2020
 */

public class Room 
{
    private int roomID;
    private int buildingID;
    private String RoomName;
    private String description;
    private int itemRequirement;
    private HashMap<String, Room> exits;        // slaat uitgangen op van deze room
    ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Maakt een kamer aan genaamd "description". Als eerst heeft
     * het geen uitgangen. "description" staat voor bijvoorbeeld
     * "slaapkamer" of "kelder".
     * @param description De beschrijving van de kamer.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Definieer een uitgang vanuit deze kamer.
     * @param direction  De richting van de uitgang.
     * @param neighbor  De kamer waarnaar de uitgang leidt.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return De korste beschrijving van de kamer.
     * (degene die werd gedefinieerd in de constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Retourneer een beschrijving van de kamer in de vorm:
     * Je bent in de slaapkamer.
     * Uitgangen: noord west
     * @return Een lange beschrijving van de kamer.
     */
    public String getLongDescription()
    {
        return "Je bent in " + description + ".\n" + getExitString(); 
    }

    /**
     * Retourneer een string die de uitgangen van de kamer beschrijft
     * "Uitgangen: noord west".
     * @return Details van de uitgangen van de kamer.
     */
    private String getExitString()
    {
        String returnString = "Uitgangen:\n";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        } 

        returnString += "\nVoorwerpen in deze kamer:\n";
        returnString += getRoomItems();

        return returnString;
    }

    /**
     * Keer terug naar de kamer die wordt bereikt als we vanuit deze kamer in de richting gaan
     * "direction". Als er geen ruimte in die richting is, keert null terug.
     * @param direction De richting van de uitgang.
     * @return De kamer in de gegeven richting.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Haal voorwerpen uit een kamer.
     * @param index Welk index het voorwerp staat.
     * @return Het voorwerp op de index.
     */
    public Item getItem(int index)
    {
        return items.get(index);
    }

    /**
     * Haal voorwerpen uit de kamer.
     * @param itemName De naam van het voorwerp.
     * @return Als er een voorwerp is, dan de itemName, anders null.
     */
    public Item getItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                return items.get(i);
            }
        }    
        return null;
    }

    /**
     * Verwijdert voorwerpen uit de kamer.
     * @param itemName De naam van het voorwerp.
     */
    public void removeItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                items.remove(i);
            }
        }
    }

    /**
     * Plaats een specifiek voorwerp in de kamer.
     * @param newItem De naam van het nieuwe voorwerp.
     */
    public void setItem(Item newitem)
    {
        items.add(newitem);
    }

    /**
     * Een beschrijving van de voorwerpen aanwezig in een kamer.
     * @return Als er een item is, de item beschrijving, anders een lege string.
     */
    public String getRoomItems() 
    {
        String output = "";
        for(int i = 0; i < items.size(); i++)
        {
            output += items.get(i).getDescription() + " ";
        }
        return output;
    }
}

