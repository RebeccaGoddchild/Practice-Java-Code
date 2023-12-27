package com.techelevator;
public class FruitTree {
    private String typeOfFruit;
    private int piecesOfFruitLeft;

    public FruitTree(String typeOfFruit, int piecesOfFruitLeft) {
        this.typeOfFruit = typeOfFruit;
        this.piecesOfFruitLeft = piecesOfFruitLeft;
    }

    public String getTypeOfFruit() {
        return typeOfFruit;
    }

    public int getPiecesOfFruitLeft() {
        return piecesOfFruitLeft;
    }

    public boolean pickFruit(int piecesToPick) {
        if (piecesToPick <= piecesOfFruitLeft && piecesToPick > 0) {
            piecesOfFruitLeft -= piecesToPick;
            return true;
        } else {
            return false;
        }
    }

}
