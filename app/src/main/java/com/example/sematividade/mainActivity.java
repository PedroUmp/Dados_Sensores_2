package com.example.sematividade;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import android.widget.Button;

import org.w3c.dom.Text;

import java.io.IOException;
public class mainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView displayAcelerometro;
    private TextView displayGiroscopio;
    private TextView displayBpm;
    private Sensor acelerometro;
    private Sensor giroscopio;
    private Sensor bpm;
    private String giroscopioText;
    private String acelerometroDisplayText;
    private String giroscopioDisplayText;
    private String acelerometroText;
    private String bpmText;
    private String bpmDisplayText;
    private Button botao;
    private int pessoaID = 0;
    private TextView displayPessoaiD;

    private Date data = Calendar.getInstance().getTime();
    private SensorEventListener bpmEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            data = Calendar.getInstance().getTime();
            bpmDisplayText = "bpm: " + event.values[0];
            bpmText = "" + data + ", " + event.values[0];
            displayBpm.setText(bpmDisplayText);
            writeToFile("bpm.txt", bpmText);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        giroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        bpm = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);


        setContentView(R.layout.main_activity);
        displayBpm = findViewById(R.id.batim);
        displayAcelerometro = findViewById(R.id.acele);
        displayGiroscopio = findViewById(R.id.giros);
        botao = findViewById(R.id.button);
        displayPessoaiD = findViewById(R.id.id_da_pessoa);


    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(bpmEventListener, bpm, SensorManager.SENSOR_DELAY_NORMAL);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pessoaID = pessoaID + 1;
                displayPessoaiD.setText(pessoaID);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        sensorManager.unregisterListener(bpmEventListener);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        data = Calendar.getInstance().getTime();
        acelerometroDisplayText = "" + "X=" + event.values[0] + "\nY=" + event.values[1] + "\nZ=" + event.values[2];
        giroscopioDisplayText = "" + "X=" + event.values[0] + "\nY=" + event.values[1] + "\nZ=" + event.values[2];
        acelerometroText = "" + data + ", " + event.values[0] + ", " + event.values[1] + ", " + event.values[2];
        giroscopioText = "" + data + ", " + event.values[0] + ", " + event.values[1] + ", " + event.values[2];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            displayAcelerometro.setText(acelerometroDisplayText);
            writeToFile("acelerometro.txt", acelerometroText);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            displayGiroscopio.setText(giroscopioDisplayText);
            writeToFile("giroscopio.txt", giroscopioText);
        }
    }

    private void writeToFile(String filename, String data) {
        File directory = new File("/data/data/com.example.sematividade");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, filename);

        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor acelerometro , int acuracia) {
    }
}