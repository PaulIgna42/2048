package com.example.lastminute;

import java.util.Objects;

public class Coordinate2D {
    int x, y;

    public Coordinate2D(int x, int y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinate values must be greater than or equal to 0");
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof Coordinate2D) {
            Coordinate2D that = (Coordinate2D) o;
            if(this.x == that.x && this.y == that.y)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
}

