package com.enterprise.version1.ejercicioactividadesysensores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AcelerometroActivity extends AppCompatActivity {

    private Button regresar;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private int contador = 0;

    private View izquierdo;
    private View derecho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelerometro);

        regresar = findViewById(R.id.btnregresar);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        izquierdo = findViewById(R.id.vIzquierdo);
        derecho = findViewById(R.id.vDerecho);

        if(sensor == null){
            finish();
        }

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float inclinacion = sensorEvent.values[0];
                System.out.println("INCLINACIÓN " + inclinacion);

                if(inclinacion < -5 && contador == 0){
                    contador++;
                    derecho.setBackgroundColor(Color.rgb(51, 128, 255));
                    izquierdo.setBackgroundColor(Color.rgb(255, 255, 255));

                }else if(inclinacion > 5 && contador == 1){
                    contador++;
                    izquierdo.setBackgroundColor(Color.rgb(255, 190, 51));
                    derecho.setBackgroundColor(Color.rgb(255, 255, 255));

                }else if(inclinacion < 1  && inclinacion > -1){
                    izquierdo.setBackgroundColor(Color.rgb(255, 255, 255));
                    derecho.setBackgroundColor(Color.rgb(255, 255, 255));
                }

                if(contador == 2){
                    contador = 0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        start();


    }

    private void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause(){
        stop();
        super.onPause();
    }

}
