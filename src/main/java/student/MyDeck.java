package student;

import ias.Deck;
import ias.GameException;

import java.util.ArrayList;

public class MyDeck implements Deck {
    private final ArrayList<Card> cardsInDeck = new ArrayList<>();
    private final ArrayList<Card> allCards;
    private final ArrayList<String[]> rulesInt;
    private final ArrayList<String[]> rulesString;
    private final ArrayList<String[]> declaredProperties;

    /**
     * @param myGame game variable
     */
    public MyDeck(MyGame myGame) {
        allCards = myGame.getCards();
        rulesInt = myGame.getRulesInt();
        rulesString = myGame.getRulesString();
        declaredProperties = myGame.getProperties();
    }

    @Override
    public void addCard(String cardName) throws GameException {
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) {
                cardsInDeck.add(card);
                return;
            }
        }
        throw new GameException("Theres no card defined with this Card Name");
    }

    @Override
    public String[] getAllCards() {
        String[] allCardNames = new String[cardsInDeck.size()];
        int i = 0;
        for (Card card : cardsInDeck) {
            allCardNames[i] = card.getName();
            i++;
        }
        return allCardNames;
    }

    @Override
    public String[] getMatchingCards(String propertyName, int value) throws GameException {
        for (String[] property : declaredProperties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("string")) {
                    throw new GameException("Property type expected is string, integer was given");
                }
            }
        }

        ArrayList<String> matchingCardNames = new ArrayList<>();
        for (Card card : cardsInDeck) {
            ArrayList<String[]> cardProperties = card.getProperties();
            for (String[] property : cardProperties) {
                if (propertyName.equals(property[0]) && Integer.toString(value).equals(property[1])) {
                    matchingCardNames.add(card.getName());
                    break;
                }
            }
        }
        return matchingCardNames.toArray(new String[0]);
    }

    @Override
    public String[] getMatchingCards(String propertyName, String value) throws GameException {
        for (String[] property : declaredProperties) {
            if (property[0].equals(propertyName)) {
                if (property[1].equals("integer")) {
                    throw new GameException("Property type expected is integer, string was given");
                }
            }
        }

        ArrayList<String> matchingCardNames = new ArrayList<>();
        for (Card card : cardsInDeck) {
            ArrayList<String[]> cardProperties = card.getProperties();
            for (String[] property : cardProperties) {
                if (propertyName.equals(property[0]) && value.equals(property[1])) {
                    matchingCardNames.add(card.getName());
                    break;
                }
            }
        }
        return matchingCardNames.toArray(new String[0]);
    }


    @Override
    public String[] selectBeatingCards(String opponentCard) throws GameException {
        Card oppCard = null;
        for (Card card : allCards) {
            if (card.getName().equals(opponentCard)) {
                oppCard = card;
            }
        }
        if (oppCard == null) {
            throw new GameException("No valid Card name was given");
        }

        ArrayList<String> winningCardNames = new ArrayList<>();
        for (Card newCard : cardsInDeck) {
            int currentCardWins = 0;
            int currentCardLosses = 0;
            if (newCard.getName().equals(opponentCard)) {
                continue;
            }

            ArrayList<String[]> propertiesOpponent = oppCard.getProperties();
            ArrayList<String[]> propertiesMyself = newCard.getProperties();

            for (String[] propertyDecl : declaredProperties) {
                String propName = propertyDecl[0];
                String propType = propertyDecl[1];
                String propValueOpponent = null;
                String propValueMyself = null;

                for (String[] propOpp : propertiesOpponent) {
                    if (propOpp[0].equals(propName)) {
                        propValueOpponent = propOpp[1];
                    }
                }
                for (String[] propMyself : propertiesMyself) {
                    if (propMyself[0].equals(propName)) {
                        propValueMyself = propMyself[1];
                    }
                }

                if (propValueOpponent != null && propValueMyself != null) {
                    if (propType.equals("integer")) {
                        String operation = null;

                        for (String[] intRule : rulesInt) {
                            String ruleName = intRule[0];
                            String ruleOperation = intRule[1];
                            if (ruleName.equals(propName)) {
                                operation = ruleOperation;
                            }
                        }

                        assert operation != null;
                        if (operation.equals(">")) {
                            if (Integer.parseInt(propValueMyself) > Integer.parseInt(propValueOpponent)) {
                                currentCardWins++;
                            } else if (Integer.parseInt(propValueMyself) < Integer.parseInt(propValueOpponent)) {
                                currentCardLosses++;
                            }
                        } else if (operation.equals("<")) {
                            if (Integer.parseInt(propValueMyself) > Integer.parseInt(propValueOpponent)) {
                                currentCardLosses++;
                            } else if (Integer.parseInt(propValueMyself) < Integer.parseInt(propValueOpponent)) {
                                currentCardWins++;
                            }
                        }
                    } else if (propType.equals("string")) {
                        for (String[] stringRule : rulesString) {
                            String ruleName = stringRule[0];
                            String ruleFirstValue = stringRule[1];
                            String ruleSecondValue = stringRule[2];

                            if (ruleName.equals(propName)) {
                                if (ruleFirstValue.equals(propValueMyself)
                                        && ruleSecondValue.equals(propValueOpponent)) {
                                    currentCardWins++;
                                } else if (ruleFirstValue.equals(propValueOpponent)
                                        && ruleSecondValue.equals(propValueMyself)) {
                                    currentCardLosses++;
                                }
                            }
                        }
                    }
                }


            }
            if (currentCardWins > currentCardLosses) {
                winningCardNames.add(newCard.getName());
            }
        }
        return winningCardNames.toArray(new String[0]);
    }

}
