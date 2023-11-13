package droidlymobilegames.ca.thelegendofsteve.Tools;

import java.util.Random;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;

public class Helpers {
    private GameviewActivity game;
    public Helpers (GameviewActivity game){
        this.game = game;
    }

    public int getDisplayWidth(){
        return game.getContext().getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(){
        return game.getContext().getResources().getDisplayMetrics().heightPixels;
    }
    public int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
