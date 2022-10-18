package me.egedib.bearlake;

import java.util.ArrayList;

public class Speeds {
    private static final Speeds ourInstance = new Speeds();

    public static Speeds getInstance() {
        return ourInstance;
    }

    public int getHumanSpeed() {
        return humanSpeed;
    }

    public void setHumanSpeed(int humanSpeed) {
        this.humanSpeed = humanSpeed;
    }

    public int getBearSpeed() {
        return bearSpeed;
    }

    public void setBearSpeed(int bearSpeed) {
        this.bearSpeed = bearSpeed;
    }

    private int humanSpeed;
    private int bearSpeed;

    public float getBearX() {
        return bearX;
    }

    public void setBearX(float bearX) {
        this.bearX = bearX;
    }

    public float getBearY() {
        return bearY;
    }

    public void setBearY(float bearY) {
        this.bearY = bearY;
    }

    public double getHumanAngle() {
        return humanAngle;
    }

    public void setHumanAngle(double humanAngle) {
        this.humanAngle = humanAngle;
    }

    private double humanAngle = -Math.PI / 2f;
    private float bearX = 0f;
    private float bearY = 0f;

    public ArrayList<CoordPairs> getCoordPairs() {
        return coordPairs;
    }

    public void setCoordPairs(ArrayList<CoordPairs> coordPairs) {
        this.coordPairs = coordPairs;
    }

    public void addToCoordPairs(CoordPairs coordPairsToBeInserted){
        coordPairs.add(coordPairsToBeInserted);
    }

    private ArrayList<CoordPairs> coordPairs = new ArrayList<>();


    public void reset(){
        humanSpeed = 0;
        bearSpeed = 0;
        humanAngle = 0;
        bearX = 0f;
        bearY = 0f;
        coordPairs = new ArrayList<>();
    }

    private Speeds() {}
}
