package droidlymobilegames.ca.thelegendofsteve.Entities;

import android.graphics.Canvas;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;
import droidlymobilegames.ca.thelegendofsteve.R;
import droidlymobilegames.ca.thelegendofsteve.Tools.Helpers;

public class MultiPlayer extends EntityInfo{

    public MultiPlayer(GameviewActivity game){
        this.game = game;
        entityWidth = 160;
        entityHeight = 160;
        setupSpritesheet(R.drawable.character1_spritesheet1);
        screenX = new Helpers(game).getDisplayWidth()/2 - 80;
        screenY = new Helpers(game).getDisplayHeight()/2 - 80;
        defaultImg = sprites[0];
        posX = 160 * 10;
        walkSpeed = 10;
    }
    public void update(){
        updateEntityAnimations();
    }
    public void draw(Canvas canvas){
        screenX = posX + game.player.screenX - game.player.posX;
        screenY = posY + game.player.screenY - game.player.posY;
        if (defaultImg != null) {
            canvas.drawBitmap(defaultImg, screenX, screenY, null);
        }
    }
    public void updateEntityAnimations() {
            animCount++;
            if (animCount > 12) {
                if (animNum == 1) {
                    animNum = 2;
                } else if (animNum == 2) {
                    animNum = 3;
                } else if (animNum == 3) {
                    animNum = 4;
                } else if (animNum == 4) {
                    animNum = 1;
                }
                animCount = 0;
            }


        if (entityDirection.equals("down")) {
            if (animNum == 1 || animNum == 3) {
                defaultImg = sprites[0];
            }
            if (animNum == 2) {
                defaultImg = sprites[1];
            }
            if (animNum == 4) {
                defaultImg = sprites[2];
            }
        }
        if (entityDirection.equals("up")) {
            if (animNum == 1 || animNum == 3) {
                defaultImg = sprites[9];
            }
            if (animNum == 2) {
                defaultImg = sprites[10];
            }
            if (animNum == 4) {
                defaultImg = sprites[11];
            }
        }
        if (entityDirection.equals("right")) {
            if (animNum == 1 || animNum == 3) {
                defaultImg = sprites[6];
            }
            if (animNum == 2 || animNum == 4) {
                defaultImg = sprites[7];
            }

        }
        if (entityDirection.equals("left")) {
            if (animNum == 1 || animNum == 3) {
                defaultImg = sprites[3];
            }
            if (animNum == 2 || animNum == 4) {
                defaultImg = sprites[4];
            }
        }

        if (entityDirection.equals("none")) {
            if (entityDefaultDirection.equals("up")) {
                defaultImg = sprites[9];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultImg = sprites[0];
            }
            if (entityDefaultDirection.equals("right")) {
                defaultImg = sprites[6];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultImg = sprites[3];
            }
        }
    }

    public void checkDirection(int moveDirectionID){
        if (moveDirectionID == 2){
            entityDirection = "left";
            entityDefaultDirection = "left";
            //KEEP GOING RIGHT
        }
        if (moveDirectionID == 3) {
            entityDirection = "right";
            entityDefaultDirection = "right";
            //KEEP GOING RIGHT
        }
        if (moveDirectionID == 4){
            entityDirection = "up";
            entityDefaultDirection = "up";
            //KEEP GOING RIGHT
        }
        if (moveDirectionID == 5){
            entityDirection = "down";
            entityDefaultDirection = "down";
            //KEEP GOING RIGHT
        }
        if (moveDirectionID == 1){
            entityDirection = "none";
            //KEEP GOING RIGHT
        }
    }
}
