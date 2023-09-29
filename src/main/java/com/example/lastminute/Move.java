package com.example.lastminute;

import java.util.Objects;

public class Move {
    Coordinate2D from;
    Coordinate2D to;
    int oldValue;
    int newValue;

    public Move(Coordinate2D from, Coordinate2D to, int oldValue, int newValue){
        if(oldValue < 1 || newValue < 1)
            throw new IllegalArgumentException();

        this.from = from;
        this.to = to;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Move(Coordinate2D from, Coordinate2D to, int value){
        if(value < 1)
            throw new IllegalArgumentException();
        this.from = from;
        this.to = to;
        this.newValue = value;
        this.oldValue = value;
    }

    public Coordinate2D getFrom(){
        return from;
    }

    public Coordinate2D getTo(){
        return to;
    }

    public int getOldValue(){
        return oldValue;
    }

    public int getNewValue(){
        return newValue;
    }

    public boolean isMerge(){
        return oldValue != newValue;
    }

    public String toString(){
        StringBuilder out = new StringBuilder("(" + from.x + "," + from.y + ") = " + oldValue + " -> " + "(" + to.x + "," + to.y + ") = " + newValue);
        if(isMerge())
            out.append(" (M)");
        return out.toString();
    }

    public int hashCode(){
        return Objects.hash(from, to, oldValue, newValue);
    }

    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof Move) {
            Move that = (Move) o;
            return this.from.equals(that.from) && this.to.equals(that.to) && this.newValue == that.newValue && this.oldValue == that.oldValue;
        }
        return false;
    }

}
