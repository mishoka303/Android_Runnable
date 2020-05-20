package tu.android_runnable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;

public class TimeActivity extends AppCompatActivity {
    Button Bpause, Breturn;
    TextView timeText;
    boolean pauseFlag = false;
    final Thread thread = new Thread();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_view);

        Bpause = findViewById(R.id.time_pauseButton);
        Breturn = findViewById(R.id.time_returnButton);
        timeText = findViewById(R.id.time_timeText);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void ReturnView(View v) { startActivity(new Intent(TimeActivity.this, MainActivity.class)); }

    @SuppressLint("SetTextI18n")
    public void Pause(View view) throws InterruptedException {
        pauseFlag = !pauseFlag;
    }

    Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            for(int i = 0; i < Integer.MAX_VALUE; i++) {
                if (!pauseFlag) {
                    final int timerInt = i;
                    handler.post(new Runnable() { @Override public void run() { timeText.setText(timerInt + " seconds have passed."); } });
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                if (pauseFlag) {
                    i -= 1;
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        }
    };
}
