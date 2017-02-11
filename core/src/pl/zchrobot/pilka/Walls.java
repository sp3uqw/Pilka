package pl.zchrobot.pilka;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Zybi on 11.02.2017.
 */

public class Walls {

    Color color;
    int sW, sH;


    float BELKA_HEIGHT;
    float BELKA_WIDTH;
    float BELKA_X, BELKA_Y; // srodek belki



    public Walls(Color color, int screenHeight, int screenWidth, World world ) {

        this.color = color;
        this.sW = screenWidth;
        this.sH = screenHeight;
        BELKA_X = sW/2;
        BELKA_Y = sH/2;
        BELKA_HEIGHT = sH/20;
        BELKA_WIDTH = sW/4;


        sciana(world);

        //katy(world);
    }



    public void render(ShapeRenderer renderer) {

        // animacja
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(BELKA_X - (BELKA_WIDTH/2), BELKA_Y - (BELKA_HEIGHT/2), BELKA_WIDTH, BELKA_HEIGHT);
        renderer.end();

    }


    private void katy(World world)   {

        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 1f));

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(sW, 1f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }


    private void sciana(World world)   {

        // -----------------------------------------------------------------------------------------------------------------
        // podloga
        BodyDef gBodyDef0 = new BodyDef();              // Create our body definition
        gBodyDef0.position.set(new Vector2(0, 0));      // Set its world position
        Body groundBody = world.createBody(gBodyDef0);  // Create a body from the defintion and add it to the world

        PolygonShape boxFloor = new PolygonShape ();   // Create a polygon shape
        boxFloor.setAsBox(sW, 0.0f);

        groundBody.createFixture(boxFloor, 0.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxFloor.dispose();                                // Clean up after ourselves

        // -----------------------------------------------------------------------------------------------------------------
        // sciana prawa
        BodyDef gBodyDef1 = new BodyDef();               // Create our body definition
        gBodyDef1.position.set(new Vector2(sW, 0.5f));      // Set its world position
        Body groundBody1 = world.createBody(gBodyDef1);  // Create a body from the defintion and add it to the world

        PolygonShape boxLeft = new PolygonShape ();   // Create a polygon shape
        boxLeft.setAsBox(0.5f, sH);

        groundBody1.createFixture(boxLeft, 0.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxLeft.dispose();                                // Clean up after ourselves

// -----------------------------------------------------------------------------------------------------------------
        // sciana lewa
        BodyDef gBodyDef2 = new BodyDef();               // Create our body definition
        gBodyDef2.position.set(new Vector2(0, 0));      // Set its world position
        Body groundBody2 = world.createBody(gBodyDef2);  // Create a body from the defintion and add it to the world

        PolygonShape boxRight = new PolygonShape ();   // Create a polygon shape
        boxLeft.setAsBox(0.5f, sH);

        groundBody2.createFixture(boxRight, 0.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxLeft.dispose();                                // Clean up after ourselves


        // -----------------------------------------------------------------------------------------------------------------
        // sufit
        BodyDef gBodyDef3 = new BodyDef();              // Create our body definition
        gBodyDef3.position.set(new Vector2(0, sH));   // Set its world position
        Body groundBody3 = world.createBody(gBodyDef3);  // Create a body from the defintion and add it to the world

        PolygonShape boxTop = new PolygonShape ();   // Create a polygon shape
        boxTop.setAsBox(sW, 1f);

        groundBody3.createFixture(boxTop, 0.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxTop.dispose();                                // Clean up after ourselves



        // -----------------------------------------------------------------------------------------------------------------
        // belka
        BodyDef gBodyDef4 = new BodyDef();              // Create our body definition
        gBodyDef4.position.set(new Vector2(BELKA_X, BELKA_Y));   // Set its world position
        Body groundBody4 = world.createBody(gBodyDef4);  // Create a body from the defintion and add it to the world

        PolygonShape boxBelka = new PolygonShape ();   // Create a polygon shape
        boxBelka.setAsBox(BELKA_WIDTH/2, BELKA_HEIGHT/2);

        groundBody4.createFixture(boxBelka, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxBelka.dispose();                                // Clean up after ourselves


    }

}
