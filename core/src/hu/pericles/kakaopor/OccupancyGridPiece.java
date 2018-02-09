package hu.pericles.kakaopor;

public class OccupancyGridPiece {
    int posX;
    int posY;
    int desX;
    int desY;
    public boolean isFilled;
    boolean isDestined;
    //boolean hasDestined;
    boolean isQueued;

    public OccupancyGridPiece(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        isFilled = false;
        isDestined = false;
        //hasDestined = false;
        isQueued = false;
    }
}

