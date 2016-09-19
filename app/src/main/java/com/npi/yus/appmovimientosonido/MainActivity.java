package com.npi.yus.appmovimientosonido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*
    *Copyright (C) 2016  Jesús Sánchez de Castro
    *This program is free software: you can redistribute it and/or modify
    *it under the terms of the GNU General Public License as published by
    *the Free Software Foundation, either version 3 of the License, or
    *(at your option) any later version.
    *This program is distributed in the hope that it will be useful,
    *but WITHOUT ANY WARRANTY; without even the implied warranty of
    *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    *GNU General Public License for more details.
    *You should have received a copy of the GNU General Public License
    *along with this program.  If not, see <http://www.gnu.org/licenses/>
    *
    * @author Jesús Sánchez de Castro
    * @version 19.09.2016


    Last Modification: 25/08/2016
    https://github.com/Yussoft
    https://justyusblog.wordpress.com/

    App idea: MovimientoSonido is a very simple application that uses the accelerometer sensor
    to simulate the act of knocking at a door. When the user does the simulated movement three
    times, a opening door sound will be played as feedback.
 */


/*
    MainActivity class:

    Class used for an introduction Activity, giving a little bit of information to the user.

 */
public class MainActivity extends AppCompatActivity{

    /**********************************************************************************************/
    /*
        Method called when the Activity is created. Sets the layout.

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**********************************************************************************************/
    /*
        Method called to start the second Activity where the sensor is implemented.
     */
    protected void initiateSecondActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
