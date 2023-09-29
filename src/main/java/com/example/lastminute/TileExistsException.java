package com.example.lastminute;
public class TileExistsException extends RuntimeException {

    String message;

    public TileExistsException(){

    }

    public TileExistsException(String message){
        this.message = message;
    }
}
