package controller;

import model.Card;
import observer.Observer;
import view.Memory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartaController {

    private List<Observer> obss = new ArrayList<>();
    private int compareCards = 0;
    private int holdCardRow = 0;
    private int holdCardColumn = 0;
    private ImageIcon versoCarta;
    private boolean isFirst = true;
    private int win = 0;

    public static final int ROWS = 2;
    public static final int COLUMNS = 3;
    private Card[] cards = new Card[COLUMNS * ROWS];

    public void addObserver(Observer o) {
        obss.add(o);
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void setVersoCarta(ImageIcon versoCarta) {
        this.versoCarta = versoCarta;
    }

    public ImageIcon getVersoCarta() {
        return versoCarta;
    }


    public int getHoldCardRow() {
        return holdCardRow;
    }

    public int getHoldCardColumn() {
        return holdCardColumn;
    }

    public int getWin() {
        return win;
    }

    public void modificaContador() {
        win++;
    }

    public int getCompareCards() {
        return compareCards;
    }

    public void alterarVariaveis(int imgNum, int r, int c) {
        compareCards = imgNum;
        holdCardRow = r;
        holdCardColumn = c;
        isFirst= false;
    }

    public void embaralhar(){
        Random rgen = new Random();
        int z = 1;

        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(-1, -1, z, false);
            if (i % 2 == 1) {
                z++;
            }
        }

        // --- embaralhar
        for (int i = 0; i < cards.length; i++) {
            int randomPosition = rgen.nextInt(cards.length);
            Card temp = cards[i];

            cards[i] = cards[randomPosition];
            cards[i].setX(i / COLUMNS);
            cards[i].setY(i % COLUMNS);

            cards[randomPosition] = temp;

            temp.setX(randomPosition / COLUMNS);
            temp.setY(randomPosition % COLUMNS);
        }
    }

    public Card[] getCards() {
        return cards;
    }
}
