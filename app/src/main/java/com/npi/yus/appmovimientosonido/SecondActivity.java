package com.npi.yus.appmovimientosonido;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/*
    Author: Jesús Sánchez de Castro
    Last Modification: 25/08/2016
    https://github.com/Yussoft
    https://justyusblog.wordpress.com/

    App idea: MovimientoSonido is a very simple application that uses the accelerometer sensor
    to simulate the act of knocking at a door. When the user does the simulated movement three
    times, a opening door sound will be played as feedback.
 */


/*
    SecondActivity class:

    Implements SensorEventListener in order to use the ACCELEROMETER sensor. The app simulates
    knocking at a door, so it will only be using the Z coordinate of the movement.

 */

public class SecondActivity extends AppCompatActivity implements SensorEventListener{

    //Sensors used: Accelerometer
    private SensorManager sensorManager;
    private Sensor accelerometer;

    //MediaPlayer variables: play sounds
    private MediaPlayer door;
    private MediaPlayer knock;

    //Control variables
    private Float z;
    private TextView vZ;
    private int knockC=0;
    private Thread thread;

    private boolean pos1, pos2;

    /**********************************************************************************************/
    /*
        Method called when the Activity is created. Several variable initializations.

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /*
            When the activity is launched, pos1 and 2 are false.
            Position 1: the smartphone is in a vertical position.
            Position 2: the smartphone has simulated knocking at a door.
         */
        pos1 = false; pos2 = false;

        //Sensor initialization
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer,sensorManager.SENSOR_DELAY_NORMAL);

        //MediaPlayer initialization
        door = MediaPlayer.create(this, R.raw.door_open);
        knock = MediaPlayer.create(this,R.raw.knock);
    }

    /**********************************************************************************************/
    /*
        Method called when the app is paused. Stops music/sounds if playing.

     */
    @Override
    protected void onPause(){
        super.onPause();

        knock.stop();
        door.stop();
    }
    /**********************************************************************************************/
    /*
        Method called when the sensor values change. The app is going to get the Z coordinate and
        make some calculations to know if the device is in position 1,2 or both have been done.

     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        //Check is the sensor is the accelerometer
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            //values 0-X, 1-Y, 2-Z.
            z = event.values[2];
            vZ = (TextView)findViewById(R.id.zNumberText);
            vZ.setText(Float.toString(z));
        }

        if(pos1 == false) {
            if (z >= -1 && z <= 3) {
                pos1 = true;
            }
        }
        if(pos1 == true) {
            if (z >= 7 && z <= 10) {
                pos2 = true;
            }
        }
        //When the divice has simulated knocking at a door a variables increases. When knocking
        //three times a door sound will be played.
        if(pos2 == true){
            knock.start();
            knockC++;
            pos1 = false; pos2 = false;
            if(knock.isPlaying()){
                waitX(300);//Wait to play sounds properly
            }
        }

        if(knockC==3){

            waitX(300);
            door.start();
            knockC =0;
        }
    }

    /**********************************************************************************************/
    /*
        Method used to wait using Threads. The parameter is the time in milliseconds.

     */
    private void waitX(final int x_sec){
        thread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(x_sec);
                    }
                }
                catch(InterruptedException ex){
                }

                // TODO
            }
        };
    }

    //Not used.
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO
    }
}
