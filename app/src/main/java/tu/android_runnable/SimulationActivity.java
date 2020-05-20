package tu.android_runnable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Random;


public class SimulationActivity extends AppCompatActivity {
    TextView downloadResult, loginResult, simResult;
    String loginMsg, downloadMsg;
    boolean loginFlag = false, downloadFlag = false;
    ProgressBar progressBar;
    Handler handler = new Handler();
    Random rand = new Random();
    int loginTime, downloadTime, maxTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_view);

        downloadResult = findViewById(R.id.simulation_task1);
        loginResult = findViewById(R.id.simulation_task2);
        progressBar = findViewById(R.id.simulation_circleBar);
        simResult = findViewById(R.id.simulation_result);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void ReturnView(View v) { startActivity(new Intent(SimulationActivity.this, MainActivity.class)); }

    public void CheckOnClick(View view) {
        DownloadTask dt=new DownloadTask();
        LoginTask lt=new LoginTask();
        new Thread(dt).start();
        new Thread(lt).start();
        loginTime = rand.nextInt(2)+3;
        downloadTime = rand.nextInt(3)+2;

        progressBar.setVisibility(View.VISIBLE);
        simResult.setText("");
        loginFlag = false;
        downloadFlag = false;

        maxTime=(Math.max(downloadTime, loginTime))*1000;
        handler.postDelayed(runnable, maxTime);
    }

    Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n") @Override public void run() {
            progressBar.setVisibility(View.INVISIBLE);
            if (downloadFlag && loginFlag) simResult.setText("Both are successful!");
            else simResult.setText("Some failed, unsuccessful!");
        }
    };

    class DownloadTask implements Runnable {
        @Override public void run() {
            runOnUiThread(new Runnable() {
                @Override public void run()
                {
                    if(String.valueOf((Math.random()<0.5)).equals("true")) { downloadMsg = "It took " + downloadTime + " seconds, success!"; downloadFlag = true; }
                    else { downloadMsg = "It was not successful!"; }
                    downloadResult.setText(downloadMsg);
                }
            });
        }
    }

    class LoginTask implements Runnable
    {
        @Override public void run() {
            runOnUiThread(new Runnable() {
                @Override public void run()
                {
                    if(String.valueOf((Math.random()<0.5)).equals("true")) { loginMsg = "It took " + loginTime + " seconds, success!"; loginFlag = true; }
                    else { loginMsg = "It was not successful!"; }
                    loginResult.setText(loginMsg);
                }
            });
        }
    }
}
