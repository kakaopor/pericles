package hu.pericles.kakaopor;

import hu.pericles.kakaopor.OccupancyGridPiece;

public class OccupancyGrid {
    public int baseX;
    public int baseY;
    public int height;
    public int width;
    public OccupancyGridPiece[][] grid;



    public OccupancyGrid(int baseX, int baseY, int width, int height) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.width = width;
        this.height = height;
        grid = new OccupancyGridPiece[width][height];
        makeGrid();
    }

    private void makeGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new OccupancyGridPiece(i, j);
            }
        }
//        those would be the walls, or anything
        /*UI buttons*/
        for (int i = 0; i < 12; i++) {
            grid[0][i].isFilled = true;
        }
    }

    public void destinationDetermination() {
        int[][] queue = new int[height*width*2][2];
        queue[0][0] = baseX;
        queue[0][1] = baseY;

        boolean stop = false;
        grid[baseX][baseY].isDestined = true;
        grid[baseX][baseY].desX = grid[baseX][baseY].posX;
        grid[baseX][baseY].desY = grid[baseX][baseY].posY;

        int queueCounter = 0;
        int innerCounter = 1;

        while (!stop) {
            stop = true;
            if (queue[queueCounter][0] + 1 < width && !grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].isFilled) {
                if (!grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].isDestined) {
                    stop = false;
                    grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].isDestined = true;
                    grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].desX = grid[queue[queueCounter][0]][queue[queueCounter][1]].posX;
                    grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].desY = grid[queue[queueCounter][0]][queue[queueCounter][1]].posY;
                }
                if (!grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].isQueued) {
                    stop = false;
                    queue[innerCounter][0] = grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].posX;
                    queue[innerCounter][1] = grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].posY;
                    grid[queue[queueCounter][0] + 1][queue[queueCounter][1]].isQueued = true;
                    innerCounter++;
                }
            }
            if (queue[queueCounter][1] - 1 >= 0 && !grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].isFilled) {
                if (!grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].isDestined) {
                    stop = false;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].isDestined = true;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].desX = grid[queue[queueCounter][0]][queue[queueCounter][1]].posX;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].desY = grid[queue[queueCounter][0]][queue[queueCounter][1]].posY;
                }
                if (!grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].isQueued) {
                    stop = false;
                    queue[innerCounter][0] = grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].posX;
                    queue[innerCounter][1] = grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].posY;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] - 1].isQueued = true;
                    innerCounter++;
                }
            }
            if (queue[queueCounter][0] - 1 >= 0 && !grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].isFilled) {
                if (!grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].isDestined) {
                    stop = false;
                    grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].isDestined = true;
                    grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].desX = grid[queue[queueCounter][0]][queue[queueCounter][1]].posX;
                    grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].desY = grid[queue[queueCounter][0]][queue[queueCounter][1]].posY;
                }
                if (!grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].isQueued) {
                    stop = false;
                    queue[innerCounter][0] = grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].posX;
                    queue[innerCounter][1] = grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].posY;
                    grid[queue[queueCounter][0] - 1][queue[queueCounter][1]].isQueued = true;
                    innerCounter++;
                }
            }
            if (queue[queueCounter][1] + 1 < height && !grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].isFilled) {
                if (!grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].isDestined) {
                    stop = false;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].isDestined = true;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].desX = grid[queue[queueCounter][0]][queue[queueCounter][1]].posX;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].desY = grid[queue[queueCounter][0]][queue[queueCounter][1]].posY;
                }
                if (!grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].isQueued) {
                    stop = false;
                    queue[innerCounter][0] = grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].posX;
                    queue[innerCounter][1] = grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].posY;
                    grid[queue[queueCounter][0]][queue[queueCounter][1] + 1].isQueued = true;
                    innerCounter++;
                }
            }
            if (innerCounter > queueCounter) stop = false;
            queueCounter++;
        }
    }

//feel free to make getter setters

}




