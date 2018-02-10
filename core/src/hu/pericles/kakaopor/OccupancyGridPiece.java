package hu.pericles.kakaopor;

public class OccupancyGridPiece {
    int posX;
    int posY;
    public int desX;
    public int desY;
    public boolean isFilled;
    boolean isDestined;
    boolean isQueued;

    public OccupancyGridPiece(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        isFilled = false;
        isDestined = false;
        isQueued = false;
    }
}
