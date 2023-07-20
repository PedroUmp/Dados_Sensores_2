package com.example.sematividade;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;



public class mainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView displayAcelerometro;
    private TextView displayGiroscopio;
    private Sensor acelerometro;
    private Sensor giroscopio;
    private String giroscopioText;
    private String acelerometroText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //
        giroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        setContentView(R.layout.main_activity);
        displayAcelerometro = findViewById(R.id.acele);
        displayGiroscopio = findViewById(R.id.giros);


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acelerometroText = "X: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2];
        giroscopioText = "X: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2];
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            displayAcelerometro.setText(acelerometroText);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            displayGiroscopio.setText(giroscopioText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor acelerometro , int acuracia) {

    }
}
