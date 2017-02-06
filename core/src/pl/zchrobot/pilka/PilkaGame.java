package pl.zchrobot.pilka;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;


public class PilkaGame extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;

    public int screenWidth;
    public int screenHeight;

	// do predkosci pilki
	//long timePrevious, deltaTime;
	float posX, posY, radius; // pozycja pilki

	Ball b1, b2, b3, b4;
    public Ball balls[] = new Ball[Const.COUNT];



	@Override
	public void create () {

		// wielkosc ekranu
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

        // random values
        long seed = System.currentTimeMillis();
        Random r = new Random(seed);

        // poczatkowa pozycja
		posX = (float)screenWidth/2.0f;
		posY = (float)screenHeight/2.0f;

		radius = (float)screenHeight / 20.0f;

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		//b1 = new Ball(new Vector2(posX + (r.nextInt(400)-200), posY + (r.nextInt(400)-200) ),radius, Color.BLUE, screenHeight, screenWidth);
        //b2 = new Ball(new Vector2(posX + (r.nextInt(400)-200), posY + (r.nextInt(400)-200) ),radius, Color.RED, screenHeight, screenWidth);

        // filling table with new objects type: Ball
        for (int i = 0; i < balls.length ; i++)
        {
            Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f);

            balls[i] = new Ball(new Vector2(posX + (r.nextInt(800)-400), posY + (r.nextInt(800)-400) ),radius, c, screenHeight, screenWidth);
        }
	}


	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void render ()
    {

        // Rysujemy
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();

        // read accelerometer
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();

        //b1.update(delta);
        //b1.render(shapeRenderer);


        for (Ball b:balls) {
            b.update(delta);
            b.render(shapeRenderer);
        }



        // Napisy
        String sAccelX = "X =  " + Float.toString(accelX);
        String sAccelY = "Y =  " + Float.toString(accelY);
        String sAccelZ = "Z =  " + Float.toString(accelZ);
        //String sTime = "dt = " + Long.toString(deltaTime) + ", delta = " + Float.toString(delta);
        String sTime = "delta = " + Integer.toString((int) (delta * 1000.0)) + " ms";

        float fps = Gdx.graphics.getFramesPerSecond();
        String sFps = "FPS: " + Float.toString(fps);

        batch.begin();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        font.draw(batch, sAccelX, 10, 90);
        font.draw(batch, sAccelY, 10, 70);
        font.draw(batch, sAccelZ, 10, 50);
        font.draw(batch, sFps, 10, 130);

        batch.end();
    }
}
