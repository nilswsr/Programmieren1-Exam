package student;

import java.util.ArrayList;

/**
 *
 */
public class Card {
    private String name;
    private ArrayList<String[]> propertiesCard = new ArrayList<>();

    /**
     * @param name
     */
    public Card(String name) {
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return properties
     */
    public ArrayList<String[]> getProperties() {
        return propertiesCard;
    }

    /**
     *
     * @param value prop
     */
    public void addProperty(String[] value) {
        propertiesCard.add(value);
    }
}