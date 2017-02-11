package pl.zchrobot.pilka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

/**
 * Created by Zybi on 05.02.2017.
 */

public class Ball {

    Vector2 p;
    Vector2 v;
    float radius;
    int screenWidth;
    int screenHeight;
    Color color;

    // box2d
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    Body body;
    Fixture fixture;

    //  tworzymy obiekt Pilka
    public Ball(Vector2 position, float radius, Color color, int screenHeight, int screenWidth, World world )
    {
        this.p = position;
        this.v = new Vector2();
        this.radius = radius;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.color = color;


        // box2d
        // Body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(p.x, p.y);
        bodyDef.linearDamping = 0f;

        body = world.createBody(bodyDef);

        // body shape
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        //
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        fixtureDef.density = 9.4f;
        fixtureDef.friction = 0.8f; // tarcie
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit

        fixture = body.createFixture(fixtureDef);
        circle.dispose();
    }




    public void update(float delta){

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

        float x = this.body.getPosition().x;
        float y = this.body.getPosition().y;

        renderer.circle(x,y,radius,256);
        renderer.end();

    }


}
