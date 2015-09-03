package com.groovehook.cronometro;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//Importando os bot�es da API do android
import android.view.View;
import android.widget.Button;
//Importando as fun��es do cronometro da API do android
import android.widget.Chronometer;

public class MainActivity extends ActionBarActivity {
    //Declarando os objetos que irei usar no cronometro...
    Chronometer m_chronometer;
    // Instanciando os bot�es do layout como objetos Button
    Button btIniciar, btPausar, btResetar;
    // Define is click pause para fazer valer o evento
    boolean isClickPause = false;
    //Variavel que espera o tempo de quando o cronometro � parado
    long timeSetWhenStopped = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recuperando as views dos layouts...
        isClickPause = false;
        m_chronometer = (Chronometer) findViewById(R.id.chronometer);
        btIniciar = (Button) findViewById(R.id.btIniciar);
        btPausar = (Button) findViewById(R.id.btPausar);
        btResetar = (Button) findViewById(R.id.btResetar);

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isClickPause) {
                    //Usando o metodo setbase() no objeto m_chronometer ajusta o tempo de base. Para isso
                    //usa como parametro os milisegundos que se passaram desde o boot do sistema e o tempo de parada.
                    m_chronometer.setBase(SystemClock.elapsedRealtime() + timeSetWhenStopped);
                    // O metodo start() inicia o cronometro a partir a base ajustada
                    m_chronometer.start();
                    timeSetWhenStopped = 0;
                    isClickPause = false;
                } else {
                    m_chronometer.setBase(SystemClock.elapsedRealtime());
                    m_chronometer.start();
                    timeSetWhenStopped = 0;
                }
            }
        });

        btPausar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isClickPause == false) {//entra para false
                    timeSetWhenStopped = m_chronometer.getBase() - SystemClock.elapsedRealtime();
                }
                m_chronometer.stop();
                isClickPause = true;
            }
        });

        btResetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                m_chronometer.stop();
                m_chronometer.setText("Total: (00:00)");
                timeSetWhenStopped = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
