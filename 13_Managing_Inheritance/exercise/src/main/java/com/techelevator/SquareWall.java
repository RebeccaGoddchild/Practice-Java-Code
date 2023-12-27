package com.techelevator;

public class SquareWall extends RectangleWall{
    private int sideLength;
    public SquareWall(String name,String color,int sideLength){
        super(name,color,sideLength,sideLength);
        this.sideLength=sideLength;
    }
    public int getSideLenght(){
        return sideLength;
    }
    @Override
    public String toString(){
        return getName()+" ("+getSideLenght()+"x"+getSideLenght()+")"+" square";
    }

    public int getArea(){
        return getSideLenght()*getSideLenght();
    }
}
