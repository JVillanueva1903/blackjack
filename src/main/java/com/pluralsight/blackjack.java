package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Card (Workbook 4a, Section 5-1 Blackjack, Exercise 1)
 *
 * One playing card: a suit, a face value ("A", "K", "10", ...) and whether
 * it is face up. A card that is face down hides everything: the getters
 * return "#" or 0, so the only way to read a card is to flip() it first.
 * That is the class design from the module, the hiding is on purpose.
 *
 * The backing variable is faceUp and the getter is isFaceUp(), the usual
 * name for a boolean getter.
 */
public class Card {
    private String suit;
    private String value;
    private boolean faceUp;

    // Every card starts face down
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }

    public String getSuit() {
        // only return the suit if the card is face up
        if (faceUp) {
            return suit;
        } else {
            return "#";
        }
    }

    public String getValue() {
        // only return the value if the card is face up
        if (faceUp) {
            // this is the string value of the card
            // i.e. A, K, Q, J, 10, 9 ...
            return value;
        } else {
            return "#";
        }
    }

    // Scoring from the exercise: A = 11, K Q J = 10, number cards = their number
    public int getPointValue() {
        // only return the value if the card is face up
        if (faceUp) {
            // determine point value and return it
            switch (value) {
                case "A":
                    return 11;
                case "K":
                case "Q":
                case "J":
                    return 10;
                default:
                    // "2" to "10": the number is in the text, so parse it
                    return Integer.parseInt(value);
            }
        } else {
            return 0;
        }
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    // Turn the card over: up becomes down, down becomes up
    public void flip() {
        faceUp = !faceUp;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
 package com.pluralsight;

import java.util.ArrayList;

/**
 * Hand (Workbook 4a, Section 5-1 Blackjack, Exercise 1)
 *
 * The cards one player holds. The Deck deals a Card, the Hand stores it.
 * The Hand never looks inside a card itself: to score or print it flips
 * each card up, asks the card for its value, and flips it back down, so
 * the cards stay hidden between uses.
 */
public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    // A Card is dealt to the Hand and the Hand is responsible
    // to store the card
    public void deal(Card card) {
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }

    // The Hand uses the methods of each card to determine
    // the value of each card - and adds up all values
    public int getValue() {
        int value = 0;

        for (Card card : cards) {
            card.flip(); // turn the card over to see the value
            value += card.getPointValue();
            card.flip(); // hide the card again
        }

        return value;
    }

    // Same flip / read / flip pattern, printing instead of adding
    public void print() {
        System.out.println("Cards in Hand:");
        for (Card card : cards) {
            card.flip();
            System.out.println(card.getValue() + " of " + card.getSuit());
            card.flip();
        }
    }
}
 package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;   // Collections.shuffle() mixes a list for us

/**
 * Deck (Workbook 4a, Section 5-1 Blackjack, Exercise 1)
 *
 * The 52 cards, built in the constructor by pairing every suit with every
 * value (two nested loops: 4 x 13). shuffle() mixes them, deal() takes
 * the top card off the list, so the deck shrinks as the game goes on.
 */
public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8",
                "9", "10", "J", "Q", "K", "A"};

        // these loops create all the cards in the deck
        // and add them to the ArrayList
        for (String suit : suits) {
            for (String value : values) {
                Card card = new Card(suit, value);
                cards.add(card);
            }
        }
    }

    // No need to write a shuffle by hand: the Collections class has one
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        // deal the top card (if there are any cards left)
        // remove(0) takes the card out of the list and hands it back
        if (cards.size() > 0) {
            Card card = cards.remove(0);
            return card;
        } else {
            return null;
        }
    }

    public int getSize() {
        return cards.size();
    }
}
