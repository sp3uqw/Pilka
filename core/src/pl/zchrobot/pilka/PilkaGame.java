package pl.zchrobot.pilka;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.Random;

// https://github.com/libgdx/libgdx/wiki/Box2d - description



public class PilkaGame extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
    private FitViewport viewport;

	SpriteBatch batch;
	BitmapFont font;

    public int screenWidth;
    public int screenHeight;

    public int screenWidthOrg;
    public int screenHeightOrg;


    // do predkosci pilki
	//long timePrevious, deltaTime;
	float posX, posY, radius; // pozycja pilki

	Ball b1, b2, b3, b4;

    public Ball balls[] = new Ball[Const.COUNT];

    Walls wall;

    // box2d
    World world;


	@Override
	public void create () {

        //Box2D.init();

        world = new World(new Vector2(0, -10), true);

        // wielkosc ekranu
		screenWidthOrg = Gdx.graphics.getWidth();
		screenHeightOrg = Gdx.graphics.getHeight();

        screenWidth = screenWidthOrg/Const.ASPECT;
        screenHeight = screenHeightOrg/Const.ASPECT;


        // random values
        long seed = System.currentTimeMillis();
        Random r = new Random(seed);

        // poczatkowa pozycja
		radius = (float)screenHeight / 20.0f;
        posX = (float)screenWidth/2.0f;
        posY = (float)screenHeight - radius*2f;


		shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(screenWidth,screenHeight);


		batch = new SpriteBatch();
		font = new BitmapFont();


        b1 = new Ball( new Vector2(posX, posY) ,radius, Color.BLUE, screenHeight, screenWidth, world);

        b2 = new Ball( new Vector2(posX+10f, posY-radius*3f) ,radius, Color.BROWN, screenHeight, screenWidth, world);

        // sciany
        wall = new Walls(Color.BLACK, screenHeight, screenWidth, world);



        // filling table with new objects type: Ball
        //for (int i = 0; i < balls.length ; i++)
        //{
        //    Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f);
        //   balls[i] = new Ball(new Vector2(posX + (r.nextInt(800)-400), posY + (r.nextInt(800)-400) ),radius, c, screenHeight, screenWidth, world);
        //    balls[i] = new Ball(new Vector2(posX + (r.nextInt(80)-40), posY + (r.nextInt(80)-400) ),radius, c, screenHeight, screenWidth, world);
        //}
	}


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
        //world.dispose();
	}

	@Override
	public void render ()
    {
        // Rysujemy
        viewport.apply();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // read accelerometer
        float delta = Gdx.graphics.getDeltaTime();
        float fps = Gdx.graphics.getFramesPerSecond();

        float accelX = Gdx.input.getAccelerometerX() * 1f;
        float accelY = Gdx.input.getAccelerometerY() * 1f;
        float accelZ = Gdx.input.getAccelerometerZ();

        b1.update(delta);
        b1.render(shapeRenderer);

        b2.update(delta);
        b2.render(shapeRenderer);

        wall.render(shapeRenderer);


        //for (Ball b:balls) {
        //    b.update(delta);
        //    b.render(shapeRenderer);
        //}

        // Napisy
        String sAccelX = "X =  " + Float.toString(accelX);
        String sAccelY = "Y =  " + Float.toString(accelY);
        String sAccelZ = "Z =  " + Float.toString(accelZ);
        //String sTime = "dt = " + Long.toString(deltaTime) + ", delta = " + Float.toString(delta);
        String sTime = "delta = " + Integer.toString((int) (delta * 1000.0)) + " ms";


        String sFps = "FPS: " + Float.toString(fps);

        batch.begin();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        font.draw(batch, sAccelX, 10, 90);
        font.draw(batch, sAccelY, 10, 70);
        font.draw(batch, sAccelZ, 10, 50);
        font.draw(batch, sFps, 10, 130);

        Vector2 v1= b1.body.getLinearVelocity();


        int v1X = (int)(v1.x *1000f);
        int v1Y = (int)(v1.y *1000f);

        String sV = "Vx =  " + Integer.toString(v1X) + "; Vy =  " + Integer.toString(v1Y);
        font.draw(batch, sV, 10, 160);
        batch.end();



        world.setGravity(new Vector2(accelY, -accelX));
        world.step(fps, 6, 2);
    }
}
