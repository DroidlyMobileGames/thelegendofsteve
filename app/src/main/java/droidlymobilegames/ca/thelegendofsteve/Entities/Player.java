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
        updatePlayerDirection();
        checkPlayerAction();
        updateEntityAnimations();
    }
    public void updatePlayerDirection(){
        if (entityRight){
            entityDirection = "right";
            entityDefaultDirection = "right";
        }else if (entityLeft){
            entityDirection = "left";
            entityDefaultDirection = "left";
        }else if (entityDown){
            entityDirection = "down";
            entityDefaultDirection = "down";
        }else if (entityUp){
            entityDirection = "up";
            entityDefaultDirection = "up";
        }else if (!game.buttonPressed){
            entityDirection = "none";
            animNum = 1;
            animCount = 0;
        }
    }
    public void updateEntityAnimations() {
        if (game.buttonPressed == false){
            entityDirection = "buttonreleased";
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

        if (entityDirection.equals("buttonreleased")) {
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

    public void handleABXY(int previousButton) {
        System.out.println("GET BUTTON " + previousButton);
        switch (previousButton){
            case 96://A
                handleAbutton();
                checkDpadHandleMovement();
                break;
            case 97://B
                handleBbutton();
                checkDpadHandleMovement();
                break;
            case 99://X
                handleXbutton();
                checkDpadHandleMovement();
                break;
            case 100://Y
                handleYbutton();
                checkDpadHandleMovement();
                break;
            case -1://BUTTON RELEASED
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

    private void checkDpadHandleMovement() {
        if (entityRight){
            entityDirection = "right";
            //KEEP GOING RIGHT
        }
        if (entityLeft){
            entityDirection = "left";
            //KEEP GOING RIGHT
        }
        if (entityUp){
            entityDirection = "up";
            //KEEP GOING RIGHT
        }
        if (entityDown){
            entityDirection = "down";
            //KEEP GOING RIGHT
        }
    }
}
