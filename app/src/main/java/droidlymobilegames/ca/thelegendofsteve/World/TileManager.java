package droidlymobilegames.ca.thelegendofsteve.World;

import android.graphics.Canvas;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;
import droidlymobilegames.ca.thelegendofsteve.R;

public class TileManager extends TileInfo{

    public TileInfo[] tiles = new TileInfo[1000];

    public TileManager (GameviewActivity game){
        this.game = game;
        setupTilesheet(R.drawable.worldtiles);
        worldTileNum = new int[game.worldXSize][game.worldYSize];//max world size x and y
        setupTileDetails();
        loadWorldMap("worldtest");
    }

    public void drawTiles(Canvas canvas){
        int tileCol = 0;
        int tileRow = 0;
        while (tileCol < game.worldXSize && tileRow < game.worldYSize){
            tileNum = worldTileNum[tileCol][tileRow];//Gets the tileNum at the XY position from the txt data
            int tileWorldX = tileCol * game.tileSize;//Sets the tile at the position X in the world times the scaled tilesize 160 in example
            int tileWorldY = tileRow * game.tileSize;//Sets position Y times scaled tilesize
            int tileScreenX = tileWorldX - game.player.posX + game.player.screenX;
            int tileScreenY = tileWorldY - game.player.posY + game.player.screenY;

            if (tileWorldX + game.tileSize > game.player.posX - game.player.screenX
                    && tileWorldX - game.tileSize< game.player.posX + game.player.screenX
                    && tileWorldY + game.tileSize> game.player.posY - game.player.screenY
                    && tileWorldY - (game.tileSize*2)< game.player.posY + game.player.screenY){//Camera 64 is added to the bottom because of the dumb navbar
                if (tiles[tileNum].defaultTileImg != null) {
                    canvas.drawBitmap(tiles[tileNum].defaultTileImg, tileScreenX, tileScreenY, null);
                }
            }
            tileCol ++;

            if (tileCol == game.worldXSize){//Check if tileCol reaches the end in this case 100 tiles then resets back to 0 then increases rows
                tileCol = 0;
                tileRow++;
            }
        }

    }

    public void loadWorldMap(final String _mapname){//Used to load map from the raw folder in res
        try {
            inputStream = game.getContext().getResources().openRawResource(
                    game.getContext().getResources().getIdentifier(
                            _mapname,"raw", game.getContext().getPackageName()));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< game.worldXSize && row < game.worldYSize){
                String line = bufferedReader.readLine();
                while (column < game.worldXSize){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    worldTileNum[column][row]= num;
                    column ++;
                }
                if (column == game.worldXSize){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){
        }
    }
    private void setupTileDetails() {
        //collisionTiles.add(String.valueOf(1));
         for (int tileID = 0; tileID<tilesList.size(); tileID++){
        tiles[tileID] = new TileInfo();
        tiles[tileID].defaultTileImg = tileImgs[tileID];
        tiles[tileID].tileWidth = game.tileSize;
        tiles[tileID].tileHeight = game.tileSize;
        // if (collisionTiles.contains(String.valueOf((int)tileID))){
        //    tileInfo[tileID].tileCollision = true;
        // }
         }

    }
}
