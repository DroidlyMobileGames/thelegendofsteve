package droidlymobilegames.ca.thelegendofsteve.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;
import droidlymobilegames.ca.thelegendofsteve.R;

public class EntityInfo {

    public GameviewActivity game;

    public int posX,posY,worldX,worldY,screenX,screenY = 0;
    public int animCount,animSpeed = 0;
    public int animNum = 1;
    public int walkSpeed = 0;
    public int entityWidth,entityHeight = 0;

    public String entityDirection = "none";
    public String entityDefaultDirection = "down";

    public boolean entityRight,entityLeft,entityUp,entityDown = false;

    public Bitmap defaultImg = null;
    public Bitmap[] sprites = new Bitmap[100];

    public void draw(Canvas canvas){
        if (defaultImg != null) {
            canvas.drawBitmap(defaultImg, screenX, screenY, null);
        }
    }

    public void setupSpritesheet(int drawable){
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(game.getResources(),
                drawable,
                bitmapOptions);
        int maxColumns = spritesheet1.getWidth()/16;
        int maxRows = spritesheet1.getHeight()/16;
        while (currentRow<maxRows){
            sprites[numberOftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                            currentColumn * 16,
                            currentRow * 16,
                            16,
                            16),entityWidth,
                    entityHeight,false);
            currentColumn ++;
            if (currentColumn == maxColumns){
                currentColumn = 0;
                currentRow ++;
            }
            numberOftiles ++;
        }
    }
}
