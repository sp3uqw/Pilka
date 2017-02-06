package pl.zchrobot.pilka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Zybi on 05.02.2017.
 */

public class Ball
{

    Vector2 p;
    Vector2 v;
    float radius;
    int screenWidth;
    int screenHeight;
    Color color;

    //  tworzymy obiekt Pilka
    public Ball(Vector2 position, float radius, Color color, int screenHeight, int screenWidth )
    {
        this.p = position;
        this.v = new Vector2();
        this.radius = radius;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.color = color;
    }


    public Ball()
    {
        long seed = System.currentTimeMillis();
        Random r = new Random(seed);

        this.p= new Vector2((r.nextInt(400)-200),(r.nextInt(400)-200));

        this.v = new Vector2();
        this.radius = radius;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.color = color;
    }



    public void update(float delta){
        //int screenHeight = PilkaGame.screenHeight;

        // read accelerometer
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        //float accelZ = Gdx.input.getAccelerometerZ();

        v.x = v.x + (accelY * delta * Const.V_WOLNO );
        v.y = v.y - (accelX * delta * Const.V_WOLNO);

        p.x = p.x + v.x * delta;
        p.y = p.y + v.y * delta;

        // odbicie od sciany
        // posX
        if (p.x <= radius) {
            p.x = radius;
            v.x = -v.x * Const.WSP_ODBICIA;
        }

        if (p.x >= (screenWidth- radius) ){
            p.x = screenWidth - radius;
            v.x = -v.x * Const.WSP_ODBICIA;
        }

        // p.y
        if (p.y <= radius ){
            p.y = radius;
            v.y = -v.y * Const.WSP_ODBICIA;
        }

        if (p.y >= (screenHeight - radius ))        {
            p.y = screenHeight  - radius;
            v.y = -v.y * Const.WSP_ODBICIA;
        }
    }

    public void render(ShapeRenderer renderer) {

        // animacja
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.circle(p.x, p.y, radius);
        renderer.end();

    }


}
