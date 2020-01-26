import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private int roomID;
    private int buildingID;
    private String RoomName;
    private String description;
    private int itemRequirement;
    private HashMap<String, Room> exits;        // stores exits of this room.
    ArrayList<Item> items = new ArrayList<Item>();
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "Je bent in " + description + ".\n" + getExitString(); 
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
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
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Get items from room
     */
    public Item getItem(int index)
    {
        return items.get(index);
    }
    
     /**
     * Get items from room String
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
     * Remove items from room
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
     * Set a paticular item in the room.
     */
    public void setItem(Item newitem)
    {
        items.add(newitem);
    }
    /**
     * Get a description of the items in a room.
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

