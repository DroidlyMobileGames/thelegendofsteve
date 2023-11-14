package droidlymobilegames.ca.thelegendofsteve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import droidlymobilegames.ca.thelegendofsteve.GameController.Dpad;
import droidlymobilegames.ca.thelegendofsteve.GameController.Joystick;

public class MainActivity extends AppCompatActivity {

    GameviewActivity game;
    public ArrayList<String> buttonlist = new ArrayList<>();
    public ArrayList<String> buttonkeylist = new ArrayList<>();
    public Dpad dpad = new Dpad();
    private JoystickListener mJoystickListener = new Joystick();
    int previousDpadButton = KeyEvent.KEYCODE_DPAD_CENTER;
    int previousButton = KeyEvent.KEYCODE_DPAD_CENTER;
    float[] previousJoystick = {0, 0, 0, 0, 0, 0};
    boolean isJoyStick = false, isGamepad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new GameviewActivity(this);
        setContentView(game);
        getGameControllerIds();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fullscreen();
    }
    @Override
    protected void onPause() {
        super.onPause();
        game.gameLoop.stopLoop();
    }
    public void fullscreen(){
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | Window.FEATURE_NO_TITLE|View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }

    private void checkButtonPressed(int press) {
        System.out.println("FUCK YOU " + press);
        switch (press){
            case 21:
                /*game.player.entityLeft = true;
                game.player.entityRight =false;
                game.player.entityUp =false;
                game.player.entityDown =false;*/
                game.button = "left";
                game.buttonPressed = true;
                break;
            case 22:
                /*game.player.entityRight = true;
                game.player.entityLeft =false;
                game.player.entityUp =false;
                game.player.entityDown =false;*/
                game.button = "right";
                game.buttonPressed = true;
                break;
            case 19:
                game.player.entityRight = false;
                game.player.entityLeft =false;
                game.player.entityUp =true;
                game.player.entityDown =false;
                game.buttonPressed = true;
                break;
            case 20:
                game.player.entityRight = false;
                game.player.entityLeft =false;
                game.player.entityUp =false;
                game.player.entityDown =true;
                game.buttonPressed = true;
                break;

            case 23:
                 game.player.entityRight =false;
                 game.player.entityDown =false;
                 game.player.entityLeft =false;
                 game.player.entityUp =false;
                 game.buttonPressed = false;
                game.button = "none";
                break;

        }
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        boolean handled = true;
        int press = dpad.getDirectionPressed(motionEvent);

        if (isGamepad && (previousDpadButton != press)) {   //(prevent repetition in data) for example, if the d-pad was centered, then don't call this method again until a different d-pad button is pressed.
            previousDpadButton = press;
            mJoystickListener.onButton(press, true);
            checkButtonPressed(press);
        }else //Adding else statement allows for both dpad and joystick to be used might let player select joystick or dpad for movement then dpad for selecting items
        if (isJoyStick) {
            // Process all historical movement samples in the batch
            final int historySize = motionEvent.getHistorySize();
            // Process the movements starting from the
            // earliest historical position in the batch
            for (int i = 0; i < historySize; i++) {
                // Process the event at historical position i
                processJoystickInput(motionEvent, i);
            }
            // Process the current movement sample in the batch (position -1)
            if (motionEvent.getDevice() != null)
                processJoystickInput(motionEvent, -1);
            handled = true;

        }
        return handled;
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {  //BUTTON PRESSED
                if (previousButton != event.getKeyCode() &&
                        !mJoystickListener.onButton(event.getKeyCode(), true)) {
                    return false;
                } else {
                    previousButton = event.getKeyCode();
                    game.player.handleABXY(previousButton);
                    return true;
                }

            } else if (event.getAction() == KeyEvent.ACTION_UP) {   //BUTTON RELEASED
                if (!mJoystickListener.onButton(event.getKeyCode(), false)) {
                    return false;
                } else {
                    previousButton = -1;
                    game.player.handleABXY(previousButton);
                    return true;
                }
            }

        } else {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {  //if the back press is from the phone then go back
                onBackPressed();
                return true;
            }
        }

        return false;

    }
    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis):
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }

    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {
        InputDevice mInputDevice = event.getDevice();
        float[] newJoystickValues = {
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_X, historyPos),
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_Y, historyPos),
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_Z, historyPos),
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_RZ, historyPos),
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_BRAKE, historyPos),
                getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_GAS, historyPos)
        };

        boolean isDifferent = false;
        for (int i = 0; i < previousJoystick.length; i++) {
            if (Math.abs(previousJoystick[i] - newJoystickValues[i]) > 0.01) {  //check if values are different;
                isDifferent = true;
                break;
            }
        }
        if (isDifferent) {   //If the joysticks are in different positions, then continue sending data.
            for (int i = 0; i < previousJoystick.length; i++) {
                previousJoystick[i] = newJoystickValues[i];  //update our new values
            }
            //This is where we can do what we want with our efficient , accurate joystick data :)
            mJoystickListener.onJoystick(previousJoystick); //here, we pass our data to our listening fragments
        }
        if (newJoystickValues[1] == 1){
            game.player.entityDown = true;game.buttonPressed = true;
        }else
        if (newJoystickValues[1] == -1){
            game.player.entityUp = true;game.buttonPressed = true;
        }else
        if (newJoystickValues[0] == -1){
            game.player.entityLeft = true;game.buttonPressed = true;
        }else
        if (newJoystickValues[0] == 1) {
            game.player.entityRight = true;
            game.buttonPressed = true;
        }else {
            game.player.entityUp = false;
            game.player.entityDown = false;
            game.player.entityLeft = false;
            game.player.entityRight = false;
            game.buttonPressed = false;
        }
    }
    public interface GamepadListener {  //All controllers have a button(Dpads are also buttons)
        boolean onButton(int buttonPress, boolean isPressed);
    }

    public interface JoystickListener extends GamepadListener {  //Joysticks have a joystick and some buttons
        void onJoystick(float[] joystickData);
    }

    public void setJoystickListener(JoystickListener interFace) {
        mJoystickListener = interFace;
    }

    public ArrayList<Integer> getGameControllerIds() {
        ArrayList<Integer> gameControllerDeviceIds = new ArrayList<Integer>();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                // This device is a game controller. Store its device ID.
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
                //isGamepad = true;
                isJoyStick = true;
               /* if ((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)

                if ((sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK)*/

            }
        }
        return gameControllerDeviceIds;
    }
}