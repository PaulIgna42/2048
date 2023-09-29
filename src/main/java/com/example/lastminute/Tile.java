package com.example.lastminute;

import java.util.Objects;

public class Tile {

    Coordinate2D coord;
    int value;

    public Tile(Coordinate2D coord, int value){
        if(value < 1)
            throw new IllegalArgumentException();
        this.coord = coord;
        this.value = value;
    }

    public Coordinate2D getCoordinate(){
        return coord;
    }

    public int getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof Tile) {
            Tile that = (Tile) o;
            return this.coord.equals(that.coord) &&  this.value == that.value;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(coord,value);
    }
}
