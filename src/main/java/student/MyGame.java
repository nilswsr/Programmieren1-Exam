package student;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import ias.GameException;
import ias.Deck;
import ias.Game;

/**
 *
 */
public class MyGame implements Game {
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ArrayList<String[]> properties = new ArrayList<>();
    private final ArrayList<String[]> rulesInt = new ArrayList<>();
    private final ArrayList<String[]> rulesString = new ArrayList<>();
    private final ArrayList<String> cardNames = new ArrayList<>();
    private final ArrayList<String> propertyNames = new ArrayList<>();
    private final String gameName;

    /**
     * @param name Name
     */
    public MyGame(String name) {
        this.gameName = name;
    }

    /**
     * @param name Name
     * @throws GameException Already Defined
     */
    @Override
    public void defineCard(String name) throws GameException {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                throw new GameException("Card had already been defined");
            }
        }
        cardNames.add(name);
        cards.add(new Card(name));
    }

    /**
     * @param name Name
     * @param type int or str
     * @throws GameException already defined or wrong input
     */
    @Override
    public void defineProperty(String name, String type) throws GameException {
        if (!type.equals("integer") && !type.equals("string")) {
            throw new GameException("Type has to be integer or string " + name + " " + type);
        }
        for (String[] property : properties) {
            if (property[0].equals(name)) {
                throw new GameException("Property had already been defined");
            }
        }
        propertyNames.add(name);
        properties.add(new String[]{name, type});
    }

    /**
     * @param cardName     Name
     * @param propertyName propName
     * @param value        New val
     * @throws GameException wrong datatype
     */
    @Override
    public void setProperty(String cardName, String propertyName, String value) throws GameException {
        for (String[] property : properties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("integer")) {
                    throw new GameException("Property type expected is integer, string was given " + value);
                }
            }
        }

        for (Card c : cards) {
            if (c.getName().equals(cardName)) {
                for (String[] property : c.getProperties()) {
                    if (property[0].equals(propertyName)) {
                        throw new GameException("setProperty has already been called with this"
                                + "combination of card name and property");
                    }
                }

                c.addProperty(new String[]{propertyName, value});
            }
        }
    }

    /**
     * @param cardName     cardname
     * @param propertyName propname
     * @param value        propvalue
     * @throws GameException wrong datatype
     */
    @Override
    public void setProperty(String cardName, String propertyName, int value) throws GameException {
        for (String[] property : properties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("string")) {
                    throw new GameException("Property type expected is string, integer was given");
                }
            }
        }

        for (Card c : cards) {
            if (c.getName().equals(cardName)) {
                for (String[] property : c.getProperties()) {
                    if (property[0].equals(propertyName)) {
                        throw new GameException("setProperty has already been called with"
                                + "this combination of card name and property");
                    }
                }
                c.addProperty(new String[]{propertyName, Integer.toString(value)});
            }
        }
    }

    /**
     * @param propertyName porpbame
     * @param operation op
     * @throws GameException wrong datatype
     */
    @Override
    public void defineRule(String propertyName, String operation) throws GameException {
        for (String[] property : properties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("string")) {
                    throw new GameException("Property type expected is integer, string was given");
                }
            }
        }

        if (!operation.equals(">") && !operation.equals("<")) {
            throw new GameException("Operation has to be > or < " + operation);
        }
        for (String[] rule : rulesInt) {
            if (rule[0].equals(propertyName)) {
                throw new GameException("Rule already had been defined");
            }
        }


        rulesInt.add(new String[]{propertyName, operation});
    }

    /**
     * @param propertyName propname
     * @param winningName  winname
     * @param losingName   loosingname
     * @throws GameException hits itself
     */
    @Override
    public void defineRule(String propertyName, String winningName, String losingName) throws GameException {
        if (winningName.equals(losingName)) {
            throw new GameException("Card cant be hitting itself");
        }

        for (String[] property : properties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("integer")) {
                    throw new GameException("Property type expected is string, integer was given");
                }
            }
        }

        for (String[] rule : rulesString) {
            if (rule[0].equals(propertyName) && rule[1].equals(winningName) && rule[2].equals(losingName)) {
                throw new GameException("Rule already had been defined");
            }
        }

        rulesString.add(new String[]{propertyName, winningName, losingName});
    }

    /**
     * @param type type
     * @param name name
     * @return returns get value
     * @throws GameException sth wrong
     */
    @Override
    public String[] get(String type, String name) throws GameException {
        switch (type) {
            case "game":
                return new String[]{gameName};
            case "card":
                if (!name.equals("*")) {
                    for (Card c : cards) {
                        if (c.getName().equals(name)) {
                            return new String[]{name};
                        }
                    }
                    return new String[0];
                }
                return cardNames.toArray(new String[0]);

            case "property":
                if (!name.equals("*")) {
                    for (String[] property : properties) {
                        if (property[0].equals(name)) {
                            return new String[]{name};
                        }
                    }
                    return new String[0];
                }
                return propertyNames.toArray(new String[0]);
            case "rule":
                if (!name.equals("*")) {
                    for (String[] rule : rulesInt) {
                        String ruleStr = rule[0] + rule[1];
                        if (ruleStr.equals(name)) {
                            return new String[]{name};
                        }
                    }
                    for (String[] rule : rulesString) {
                        String ruleStr = rule[0] + ":" + rule[1] + ">" + rule[2];
                        if (ruleStr.equals(name)) {
                            return new String[]{name};
                        }
                    }
                    return new String[0];
                }

                String[] outputArr = new String[rulesInt.size() + rulesString.size()];
                for (int i = 0; i < rulesInt.size(); i++) {
                    outputArr[i] = rulesInt.get(i)[0] + rulesInt.get(i)[1];
                }
                for (int j = 0; j < rulesString.size(); j++) {
                    outputArr[j + rulesInt.size()] = rulesString.get(j)[0] + ":" + rulesString.get(j)[1]
                            + ">" + rulesString.get(j)[2];
                }
                return outputArr;
        }
        return new String[0];
    }

    /**
     * @param path file path
     * @throws GameException no file
     */
    @Override
    public void saveToFile(String path) throws GameException {
        try {
            File myFile = new File(path);
            myFile.createNewFile();
        } catch (IOException e) {
            throw new GameException("Path not correct");
        }

        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write("Game: " + gameName + "\n");

            for (Card card : cards) {
                myWriter.write("Card: " + card.getName() + "\n");
            }

            for (String[] property : properties) {
                myWriter.write("Property: " + property[0] + " | " + property[1] + "\n");
            }

            for (Card card : cards) {
                ArrayList<String[]> cardProperties = card.getProperties();
                for (String[] cardProperty : cardProperties) {
                    myWriter.write("CardProperty: " + card.getName() + " | " + cardProperty[0] + " | "
                            + cardProperty[1] + "\n");
                }
            }

            for (String[] rule : rulesInt) {
                myWriter.write("GameRuleInteger: " + rule[0] + " | " + rule[1] + "\n");
            }

            for (String[] rule : rulesString) {
                myWriter.write("GameRuleString: " + rule[0] + " | " + rule[1] + " | " + rule[2] + "\n");
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param path file path
     * @return mygame
     * @throws GameException wrong input
     */
    public static MyGame loadGame(String path) throws GameException {
        MyGame game;
        try {
            File inputFile = new File(path);
            Scanner myScanner = new Scanner(inputFile);


            String firstLine = myScanner.nextLine();
            if (!firstLine.contains("Game:")) {
                throw new GameException("Game title is not defined in first row");
            }

            game = new MyGame(firstLine.substring(6));

            while (myScanner.hasNextLine()) {
                String line = myScanner.nextLine();

                if (line.contains("Game:")) {
                    throw new GameException("You can assign the game name only once");
                } else if (line.contains("Card:")) {
                    String valName;
                    try {
                        valName = line.substring(6);
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new GameException("Nononono");
                    }
                    game.defineCard(valName);
                } else if (line.contains("CardProperty:")) {
                    String cName, cType, cValue;
                    try {
                        cName = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
                        cType = line.substring(line.indexOf("|") + 2, line.indexOf("|", line.indexOf("|") + 2) - 1);
                        cValue = line.substring(line.indexOf("|", line.indexOf("|") + 2) + 2);
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new GameException("Nononono");
                    }
                    try {
                        game.setProperty(cName, cType, Integer.parseInt(cValue));
                    } catch (NumberFormatException e) {
                        game.setProperty(cName, cType, cValue);
                    }

                } else if (line.contains("Property:")) {
                    String valName, valType;
                    try {
                        valName = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
                        valType = line.substring(line.indexOf("|") + 2);
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new GameException("Nononono");
                    }
                    game.defineProperty(valName, valType);
                } else if (line.contains("GameRuleInteger:")) {
                    String type, operator;
                    try {
                        type = line.substring(16, line.indexOf(" | "));
                        operator = line.substring(line.indexOf("|") + 2);
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new GameException("Nononono");
                    }
                    game.defineRule(type, operator);
                } else if (line.contains("GameRuleString:")) {
                    String type, one, two;
                    try {
                        type = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
                        one = line.substring(line.indexOf("|") + 2, line.indexOf("|", line.indexOf("|") + 2) - 1);
                        two = line.substring(line.indexOf("|", line.indexOf("|") + 2) + 2);
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new GameException("Nonononon");
                    }
                    game.defineRule(type, one, two);
                } else {
                    throw new GameException("Not a line");
                }
            }

        } catch (FileNotFoundException e) {
            throw new GameException("File not found");
        }
        return game;
    }


    /**
     * @return cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }


    /**
     * @return rulesInt
     */
    public ArrayList<String[]> getRulesInt() {
        return rulesInt;
    }

    /**
     * @return rulesString
     */
    public ArrayList<String[]> getRulesString() {
        return rulesString;
    }

    /**
     * @return properties
     */
    public ArrayList<String[]> getProperties() {
        return properties;
    }

    /**
     * @return Deck
     */
    @Override
    public Deck createDeck() {
        return new MyDeck(this);
    }
}
