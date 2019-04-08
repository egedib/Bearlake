package me.egedib.bearlake;

// TODO: Igen ez így csúnya -> lehetne így tárolni a medve és embert, ahelyett, hogy get/set lenne koordinátánként.
public class CoordPairs {

    private float humanX;
    private float humanY;
    private float bearX;
    private float bearY;

    public float getHumanX() {
        return humanX;
    }

    public void setHumanX(float humanX) {
        this.humanX = humanX;
    }

    public float getHumanY() {
        return humanY;
    }

    public void setHumanY(float humanY) {
        this.humanY = humanY;
    }

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

    public CoordPairs(float humanX, float humanY, float bearX, float bearY) {
        this.humanX = humanX;
        this.humanY = humanY;
        this.bearX = bearX;
        this.bearY = bearY;
    }

    public void SetCoordPairs(float humanX, float humanY, float bearX, float bearY){
        this.bearX = bearX;
        this.bearY = bearY;
        this.humanX = humanX;
        this.humanY = humanY;
    }

    public CoordPairs() {}
}
