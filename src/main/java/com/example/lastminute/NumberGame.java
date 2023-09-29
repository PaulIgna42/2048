package com.example.lastminute;

import java.util.*;

public class NumberGame {
    int width;
    int height;
    int initialTiles;
    int points;
    Tile[][] tiles;

    public NumberGame(int width, int height){
        this.points = 0;
        tiles = new Tile[width][height];
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = null;
            }
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public NumberGame(int width, int height, int initialTiles){
        if(initialTiles < 0 || initialTiles > width*height || width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.points = 0;
        this.height = height;
        this.width = width;
        tiles = new Tile[width][height];
        this.initialTiles = initialTiles;

        for (int i = 0; i < initialTiles; i++) {
            addRandomTile();
        }
    }

    public int get(Coordinate2D coord){
        if(tiles[coord.getX()][coord.getY()] == null)
            return 0;
        if(coord.y >= height || coord.x >= width || coord.y < 0 || coord.x < 0)
            throw new IndexOutOfBoundsException();
        return tiles[coord.x][coord.y].value;
    }

    public int get(int x, int y){
        if(tiles[x][y] == null)
            return 0;
        if(y >= height || x >= width || y < 0 || x < 0)
            throw new IndexOutOfBoundsException();
        return tiles[x][y].value;
    }

    public int getPoints(){
        return points;
    }

    public boolean isFull(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(tiles[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public Tile addRandomTile(){
        Random random = new Random();
        if (isFull())
            throw new TileExistsException();

        int x = random.nextInt(height);
        int y = random.nextInt(width);

        while (tiles[x][y] != null) {
            x = random.nextInt(height);
            y = random.nextInt(width);
        }

        int percent = random.nextInt(100);
        if (percent < 10) {
            return addTile(x,y,4);

        }
        return addTile(x,y,2);
    }

    public Tile addTile(int x, int y, int value){
        if(tiles[x][y] != null)
            throw new TileExistsException();
        Coordinate2D coordinate2D = new Coordinate2D(x,y);
        tiles[x][y] = new Tile(coordinate2D,value);
        return new Tile(coordinate2D,value);
    }

    public List <Move> move(Direction dir) {
        int startHere;
        boolean vibeCheck = false;
        List <Move> out = new LinkedList<>();

        if (dir == Direction.RIGHT) {
            for (int j = 0; j < width; j++) {
                startHere = width - 1;
                for (int i = height - 2; i >= 0; i--) {
                    if (tiles[i][j] != null) {
                        for (int k = startHere; k > i; k--) {
                            for (int l = i + 1; l < k; l++) {
                                if (tiles[l][j] != null) {
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if (tiles[k][j] == null) {
                                startHere = k;
                                this.addTile(k, j, tiles[i][j].getValue());
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(k, j);
                                out.add(new Move(from, to, tiles[i][j].getValue()));
                                tiles[i][j] = null;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !vibeCheck) {
                                this.points += 2 * tiles[i][j].getValue();
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(k, j);
                                out.add(new Move(from, to, tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[k][j] = new Tile(new Coordinate2D(k, j), 2 * tiles[i][j].getValue());
                                startHere = k - 1;
                                tiles[i][j] = null;
                                break;
                            }
                            if (k - 1 == i)
                                startHere = i;
                            vibeCheck = false;
                        }
                    }

                }
            }
            return out;

        } else if (dir == Direction.LEFT){
            for (int j = 0; j < width; j++) {
                startHere = 0;
                for (int i = 1; i < width; i++) {
                    if (tiles[i][j] != null) {
                        for (int k = startHere; k < i; k++) {
                            for (int l = k + 1; l < i; l++) {
                                if (tiles[l][j] != null) {
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if (tiles[k][j] == null) {
                                startHere = k;
                                this.addTile(k, j, tiles[i][j].getValue());
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(k, j);
                                out.add(new Move(from, to, tiles[i][j].getValue()));
                                tiles[i][j] = null;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !vibeCheck) {
                                this.points += 2 * tiles[i][j].getValue();
                                startHere = k + 1;
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(k, j);
                                out.add(new Move(from , to, tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[k][j] = new Tile(new Coordinate2D(k, j), 2 * tiles[i][j].getValue());
                                tiles[i][j] = null;
                                break;
                            }
                            vibeCheck = false;
                        }
                    }

                }
            }
            return out;
        }else if (dir == Direction.UP) {
            for (int i = 0; i < height; i++) {
                startHere = 0;
                for (int j = 1; j < width; j++) {
                    if (tiles[i][j] != null) {
                        for (int k = startHere; k < j; k++) {
                            for (int l = k + 1; l < j; l++) {
                                if (tiles[i][l] != null) {
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if (tiles[i][k] == null) {
                                this.addTile(i, k, tiles[i][j].getValue());
                                startHere = k;
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(i, k);
                                out.add(new Move(from, to, tiles[i][j].getValue()));
                                tiles[i][j] = null;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[i][k].getValue() && !vibeCheck) {
                                this.points += 2 * tiles[i][j].getValue();
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(i, k);
                                out.add(new Move(from,to, tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[i][k] = new Tile(new Coordinate2D(i, k), 2 * tiles[i][j].getValue());
                                startHere = k + 1;
                                tiles[i][j] = null;
                                break;
                            }
                            vibeCheck = false;
                        }
                    }

                }
            }
            return out;

        } else {
            for (int i = 0; i < height; i++) {
                startHere = width - 1;
                for (int j = width - 2; j >= 0; j--) {
                    if (tiles[i][j] != null) {
                        for (int k = startHere; k > j; k--) {
                            for (int l = j + 1; l < k; l++) {
                                if (tiles[i][l] != null) {
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if (tiles[i][k] == null) {
                                this.addTile(i, k, tiles[i][j].getValue());
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(i, k);
                                out.add(new Move(from, to, tiles[i][j].getValue()));
                                startHere = k;
                                tiles[i][j] = null;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[i][k].getValue() && !vibeCheck) {
                                this.points += 2 * tiles[i][j].getValue();
                                Coordinate2D from = new Coordinate2D(i, j);
                                Coordinate2D to = new Coordinate2D(i, k);
                                out.add(new Move(from, to, tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[i][k] = new Tile(new Coordinate2D(i, k), 2 * tiles[i][j].getValue());
                                startHere = k - 1;
                                tiles[i][j] = null;
                                break;
                            }
                            if (k - 1 == j)
                                startHere = j;
                            vibeCheck = false;
                        }
                    }

                }
            }
            return out;
        }
    }



    public boolean canMove(Direction dir){
        if(dir.equals(Direction.UP)){
            for (int j = 1; j < height; j++) {
                for (int i = 0; i < width; i++){
                    if (tiles[i][j] != null)
                        if(tiles[i][j-1] == null || tiles[i][j-1].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.DOWN)){
            for (int j = height-2; j >= 0; j--) {
                for (int i = 0; i < width; i++){
                    if (tiles[i][j] != null)
                        if(tiles[i][j+1] == null || tiles[i][j+1].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.RIGHT)){
            for (int i = height-2; i >= 0; i--) {
                for (int j = 0; j < width; j++){
                    if(tiles[i][j] != null)
                        if(tiles[i+1][j] == null || tiles[i+1][j].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.LEFT)){
            for (int i = 1; i < height; i++) {
                for (int j = 0; j < width; j++){
                    if (tiles[i][j] != null)
                        if(tiles[i-1][j] == null || tiles[i-1][j].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(){
        return canMove(Direction.UP) || canMove(Direction.DOWN) || canMove(Direction.LEFT) || canMove(Direction.RIGHT);
    }
}
