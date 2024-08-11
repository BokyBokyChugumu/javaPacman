public class MapAnalyzer {
    public int[][] map;
    int xFrameSize, yFrameSize;

    public MapAnalyzer(int xFrameSize, int yFrameSize){
        map = new int[xFrameSize][yFrameSize];
        this.yFrameSize = yFrameSize;
        this.xFrameSize = xFrameSize;
    }

    public void addWall(int xCoordinate, int yCoordinate, int xSize, int ySize){
        for(int i = xCoordinate; i < xCoordinate + xSize; i++){
            for(int j = yCoordinate; j < yCoordinate + ySize; j++){
                map[i][j] = 1;
            }
        }
    }

    public void addPoint(int xCoordinate, int yCoordinate, int xSize, int ySize){
        for(int i = xCoordinate; i < xCoordinate + xSize; i++){
            for(int j = yCoordinate; j < yCoordinate + ySize; j++){
                map[i][j] = 2;
            }
        }
    }

    public void addUpgrade(int xCoordinate, int yCoordinate){
        for(int i = xCoordinate + 10; i < xCoordinate + Frame.voxelSize - 10; i++){
            for(int j = yCoordinate + 10; j < yCoordinate + Frame.voxelSize - 10; j++){
                map[i][j] = 3;
            }
        }
    }

    public void removePoint(CollectablePoint point){
        for(int i = point.xPosition; i < point.xPosition + point.xSize; i++){
            for(int j = point.yPosition; j < point.yPosition + point.ySize; j++){
                map[i][j] = 0;
            }
        }
    }

    public void removeUpgrade(UpgradeObject upgrade){
        for(int i = upgrade.xPosition + 10; i < upgrade.xPosition + Frame.voxelSize - 10; i++){
            for(int j = upgrade.yPosition + 10; j < upgrade.yPosition + Frame.voxelSize - 10; j++){
                map[i][j] = 0;
            }
        }
    }

    public boolean checkPointUnder(int xCoordinate, int yCoordinate){
        if(xCoordinate % Frame.voxelSize == 0 && yCoordinate % Frame.voxelSize == 0){
            if(map[xCoordinate + 15][yCoordinate + 15] == 2){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean checkUpgradeUnder(int xCoordinate, int yCoordinate){
        if(xCoordinate % Frame.voxelSize == 0 && yCoordinate % Frame.voxelSize == 0){
            if(map[xCoordinate + 15][yCoordinate + 15] == 3){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public int getRightWallCoordinate(int xCoordinate, int yCoordinate, int xSize, int ySize){
        int ans = yFrameSize;
        for(int i = xCoordinate + xSize - 1; map[i][yCoordinate] == 0; i++){
            if(map[i+1][yCoordinate] == 1 && i - xSize + 1 < ans) ans = i - xSize + 1;
            if(map[i+1][yCoordinate + ySize - 1] == 1 && i - xSize + 1 < ans) ans = i - xSize + 1;
        }

        return ans;
    }

    public int getLeftWallCoordinate(int xCoordinate, int yCoordinate, int xSize, int ySize){
        int ans = 0;
        for(int i = xCoordinate; map[i][yCoordinate] == 0; i--){
            if(map[i-1][yCoordinate] == 1 && i > ans) ans = i;
            if(map[i-1][yCoordinate + ySize - 1] == 1 && i > ans) ans = i;
        }

        return ans;
    }

    public int getUpperWallCoordinate(int xCoordinate, int yCoordinate, int xSize, int ySize){
        int ans = 0;
        for(int i = yCoordinate; map[xCoordinate][i] == 0; i--){
            if(map[xCoordinate][i-1] == 1 && i > ans) ans = i;
            if(map[xCoordinate + xSize - 1][i-1] == 1 && i > ans) ans = i;
        }

        return ans;
    }

    public int getLowerWallCoordinate(int xCoordinate, int yCoordinate, int xSize, int ySize){
        int ans = xFrameSize;
        for(int i = yCoordinate + ySize - 1; map[xCoordinate][i] == 0; i++){
            if(map[xCoordinate][i+1] == 1 && i - ySize + 1 < ans) ans = i - ySize + 1;
            if(map[xCoordinate + xSize - 1][i+1] == 1 && i - ySize + 1 < ans) ans = i - ySize + 1;
        }

        return ans;
    }
}
