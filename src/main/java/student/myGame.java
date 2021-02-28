import java.util.ArrayList;

public interface MyGame {
    public ArrayList<Card> cards = new ArrayList<Card>();
    public void defineCard(String name) throws GameException{
        cards.add(new Card(name));
    }

    public ArrayList<String[]> properties = new ArrayList<String[]>();
    public void defineProperty(String name, String type) throws GameException{
        properties.add(new String[])
    }
    public void setProperty(String cardName, String propertyName, String value) throws GameException;
    public void setProperty(String cardName, String propertyName, int value) throws GameException;

    public void defineRule(String propertyName, String operation) throws GameException;
    public void defineRule(String propertyName, String winningName, String losingName) throws GameException;

    public String[] get(String type, String name) throws GameException;

    public void saveToFile(String path) throws GameException;
    public Deck createDeck();
}