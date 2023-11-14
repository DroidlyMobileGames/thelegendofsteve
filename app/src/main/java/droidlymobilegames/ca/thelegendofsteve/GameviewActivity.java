package droidlymobilegames.ca.thelegendofsteve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import droidlymobilegames.ca.thelegendofsteve.Entities.Player;
import droidlymobilegames.ca.thelegendofsteve.Tools.Helpers;
import droidlymobilegames.ca.thelegendofsteve.World.TileManager;
import droidlymobilegames.ca.thelegendofsteve.net.GameClient;
import droidlymobilegames.ca.thelegendofsteve.net.GameServer;

public class GameviewActivity extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    public GameLoop gameLoop;
    public GameClient gameClient;
    public GameServer gameServer;
    public Player player;
    public int worldXSize, worldYSize;
    public int tileSize, cameraWidth, cameraHeight = 0;
    public int keypressed = -1;
    public TileManager tileManager;
    public boolean buttonPressed = false;
    public String button = "none";

    public Paint textpaint = new Paint();
    public Helpers helpers;


    public GameviewActivity(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);
        helpers = new Helpers(this);
        player = new Player(this);


        worldXSize = 50;
        worldYSize = 50;
        tileSize = 160;
        cameraWidth = helpers.getDisplayWidth();
        cameraHeight = helpers.getDisplayHeight();

        tileManager = new TileManager(this);

        textpaint.setTextSize(50);
        textpaint.setColor(Color.BLUE);
        gameClient = new GameClient(this,"192.168.3.125");
        gameClient.start();
    }

    public void update() {
        gameClient.sendData(button.getBytes());

        switch (keypressed) {
            case 0:
                player.entityUp = true;
                player.entityRight = false;
                player.entityLeft = false;
                player.entityDown = false;
                break;
            case 3:
                player.entityDown = true;
                player.entityRight = false;
                player.entityLeft = false;
                player.entityUp = false;
                break;
            case 1:
                player.entityLeft = true;
                player.entityRight = false;
                player.entityDown = false;
                player.entityUp = false;
                break;
            case 2:
                player.entityRight = true;
                player.entityDown = false;
                player.entityLeft = false;
                player.entityUp = false;
                break;

        }
        player.update();
        //gameClient.sendData("HELLO WORLD".getBytes(StandardCharsets.UTF_8));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        tileManager.drawTiles(canvas);
        player.draw(canvas);
        canvas.drawText(String.valueOf(keypressed), 100, 100, textpaint);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    public class GameLoop extends Thread {
        public static final double MAX_UPS = 240.0;
        private static final double UPS_PERIOD = 1E+3 / MAX_UPS;

        private GameviewActivity game;
        private SurfaceHolder surfaceHolder;

        private boolean isRunning = false;
        private double averageUPS;
        private double averageFPS;

        public GameLoop(GameviewActivity game, SurfaceHolder surfaceHolder) {
            this.game = game;
            this.surfaceHolder = surfaceHolder;
        }

        public double getAverageUPS() {
            return averageUPS;
        }

        public double getAverageFPS() {
            return averageFPS;
        }

        public void startLoop() {
            Log.d("GameLoop.java", "startLoop()");
            isRunning = true;
            start();
        }

        @Override
        public void run() {
            Log.d("GameLoop.java", "run()");
            super.run();

            // Declare time and cycle count variables
            int updateCount = 0;
            int frameCount = 0;

            long startTime;
            long elapsedTime;
            long sleepTime;

            // Game loop
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            while (isRunning) {

                // Try to update and render game
                try {
                    canvas = surfaceHolder.lockHardwareCanvas();
                    synchronized (surfaceHolder) {
                        game.update();
                        updateCount++;
                        game.draw(canvas);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            frameCount++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Pause game loop to not exceed target UPS
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
                if (sleepTime > 0) {
                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Skip frames to keep up with target UPS
                while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                    //game.update();
                    updateCount++;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
                }

                // Calculate average UPS and FPS
                elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= 1000) {
                    averageUPS = updateCount / (1E-3 * elapsedTime);
                    averageFPS = frameCount / (1E-3 * elapsedTime);
                    updateCount = 0;
                    frameCount = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        }

        public void stopLoop() {
            Log.d("GameLoop.java", "stopLoop()");
            isRunning = false;
            try {
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
