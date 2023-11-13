package droidlymobilegames.ca.thelegendofsteve.GameController;

import android.view.KeyEvent;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;
import droidlymobilegames.ca.thelegendofsteve.MainActivity;

public class Joystick implements MainActivity.JoystickListener{

    private int dPadCurrentLocation = KeyEvent.KEYCODE_DPAD_CENTER;

    @Override
    public boolean onButton(int buttonPress, boolean isPressed) {
        boolean handled = true;
        switch (buttonPress){
            case KeyEvent.KEYCODE_BUTTON_A:

                break;
            case KeyEvent.KEYCODE_BUTTON_B:

                break;
            case KeyEvent.KEYCODE_BUTTON_X:

                break;
            case KeyEvent.KEYCODE_BUTTON_Y:

                break;
            case KeyEvent.KEYCODE_BUTTON_START:

                break;
            case KeyEvent.KEYCODE_BUTTON_SELECT:

                break;
            case KeyEvent.KEYCODE_BUTTON_L1:

                break;
            case KeyEvent.KEYCODE_BUTTON_L2:

                break;
            case KeyEvent.KEYCODE_BUTTON_R1:

                break;
            case KeyEvent.KEYCODE_BUTTON_R2:
                break;
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
                break;
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                dPadCurrentLocation = KeyEvent.KEYCODE_DPAD_CENTER;
                break;

            default:
                handled = false;
        }
        return handled;
    }

    @Override
    public void onJoystick(float[] joystickData) {
        float translationX = mapFloat(joystickData[0], -1, 1, -40, 40);
        float translationY = mapFloat(joystickData[1], -1, 1, -40, 40);

        translationX = mapFloat(joystickData[2], -1, 1, -40, 40);
        translationY = mapFloat(joystickData[3], -1, 1, -40, 40);

        int k = (int) mapFloat(joystickData[0], -1, 1, 100, 355);  //I put in terms of 3 digits because the UDP packets must have the same number of digits to be run smoothly
        int l = (int) mapFloat(joystickData[1], -1, 1, 100, 355);
        int z = (int) mapFloat(joystickData[2], -1, 1, 100, 355);
        int x = (int) mapFloat(joystickData[3], -1, 1, 100, 355);

        int q = (int) mapFloat(joystickData[4], -1, 1, 100, 355);
        int r = (int) mapFloat(joystickData[5], -1, 1, 100, 355);

    }
    public static float mapFloat(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
