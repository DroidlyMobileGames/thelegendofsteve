package droidlymobilegames.ca.thelegendofsteve.Entities;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;
import droidlymobilegames.ca.thelegendofsteve.R;
import droidlymobilegames.ca.thelegendofsteve.Tools.Helpers;

public class Player extends EntityInfo{

    public Player(GameviewActivity game){
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
        //updatePlayerDirection();
        checkPlayerAction();
        updateEntityAnimations();
        checkDpadJoystickHandleMovement();
    }
    public void updatePlayerOnlineStatus(){
        if (game.gameClient != null) {
            game.gameClient.sendData("03" + "," + posX + "," + posY + "," + game.moveDirectionID);
        }
    }
    public void updatePlayerDirection(){
        if (entityLeft){
            entityDirection = "left";
            entityDefaultDirection = "left";
        }else if (entityRight){
            entityDirection = "right";
            entityDefaultDirection = "right";
        }else if (entityUp){
            entityDirection = "up";
            entityDefaultDirection = "up";
        }else if (entityDown){
            entityDirection = "down";
            entityDefaultDirection = "down";
        }else if (!game.buttonPressed){
            entityDirection = "none";
            animNum = 1;
            animCount = 0;
        }
    }
    public void updateEntityAnimations() {
        updatePlayerOnlineStatus();
        if (game.moveDirectionID == 1){
            entityDirection = "none";
        }
        if (game.buttonPressed) {
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

        } else {
            if (animCount < 12 + 1) {
                animCount = 0;
                animNum = 1;
                if (animNum == 1) {
                    animNum = 2;
                } else if (animNum == 2) {
                    animNum = 3;
                } else if (animNum == 3) {
                    animNum = 4;
                }
            }
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
            if (animNum == 2) {
                defaultImg = sprites[7];
            }
            if (animNum == 4) {
                defaultImg = sprites[8];
            }
        }
        if (entityDirection.equals("left")) {
            if (animNum == 1 || animNum == 3) {
                defaultImg = sprites[3];
            }
            if (animNum == 2) {
                defaultImg = sprites[4];
            }
            if (animNum == 4) {
                defaultImg = sprites[5];
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
    public void checkPlayerAction(){
        switch (entityDirection){
            case "right":
                posX += walkSpeed;
                break;
            case "left":
                posX -= walkSpeed;
                break;
            case "down":
                posY += walkSpeed;
                break;
            case "up":
                posY -= walkSpeed;
                break;
        }
    }


    private void handleYbutton() {

    }

    private void handleXbutton() {

    }

    private void handleBbutton() {

    }

    private void handleAbutton() {

    }

    private void checkDpadJoystickHandleMovement() {

        if (game.moveDirectionID == 2){
            entityDirection = "left";
            entityDefaultDirection = "left";
            game.buttonPressed = true;
            //KEEP GOING RIGHT
        }
        if (game.moveDirectionID == 3) {
            entityDirection = "right";
            entityDefaultDirection = "right";
            game.buttonPressed = true;
            //KEEP GOING RIGHT
        }
        if (game.moveDirectionID == 4){
            entityDirection = "up";
            entityDefaultDirection = "up";
            game.buttonPressed = true;
            //KEEP GOING RIGHT
        }
        if (game.moveDirectionID == 5){
            entityDirection = "down";
            entityDefaultDirection = "down";
            game.buttonPressed = true;
            //KEEP GOING RIGHT
        }
        if (game.moveDirectionID == 1){
            entityDirection = "none";
            game.buttonPressed = false;
            //KEEP GOING RIGHT
        }
    }
}
