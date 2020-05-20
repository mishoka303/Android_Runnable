package tu.android_runnable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView errMsg;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        submit = findViewById(R.id.login_buttonSubmit);
        errMsg = findViewById(R.id.login_message);
    }

    public void ReturnView(View v) { startActivity(new Intent(LoginActivity.this, MainActivity.class)); }

    public void Login(View v) { new Thread(LoginTask).start(); }

    Runnable LoginTask = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            boolean globalFlag = false;
            String strCheck = "!@#$%&*";

            // If both empty
            if(password.getText().toString().length() == 0 || email.getText().toString().length() == 0) {
                globalFlag = true;
                runOnUiThread(new Runnable() { @Override public void run() { errMsg.setText("You have an empty field!"); }} );
            }
            // If password > 16 symbols
            if(password.getText().toString().length() > 16) {
                globalFlag = true;
                runOnUiThread(new Runnable() { @Override public void run() { errMsg.setText("Password cannot be more than 16 symbols!"); }} );
            }
            // If email > 64 symbols
            if(email.getText().toString().length() > 64) {
                globalFlag = true;
                runOnUiThread(new Runnable() { @Override public void run() { errMsg.setText("Email cannot be more than 64 symbols!"); }} );
            }
            // If password 7th, 14th symbol not uppercase
            if (password.getText().length() >= 7) {
                if (password.getText().length() >= 14) {
                    if(!Character.isUpperCase(password.getText().charAt(6)) || !Character.isUpperCase(password.getText().charAt(13))) {
                        globalFlag = true;
                        runOnUiThread(new Runnable() { @Override public void run() { errMsg.setText("Your 7th or 14th symbol must be uppercase!"); }});
                    }
                }
                else if(!Character.isUpperCase(password.getText().charAt(6))) {
                    globalFlag = true;
                    runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("Your 7th symbol must be uppercase!"); } });
                }
            }
            // Check every 3rd symbol if Digit
            for (int i = 2; i < password.getText().length(); i+=3) {
                if (!Character.isDigit(password.getText().charAt(i))) {
                    final String var1 = String.valueOf(i+1);
                    globalFlag = true;
                    runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("Your " + var1 + "'s symbol is not a number!"); } });
                    break;
                }
            }
            // Check for 8th/16th symbol if special character
            if (password.getText().length() >= 8) {
                if (password.getText().length() >= 16) {
                    boolean flag1 = false;
                    boolean flag2 = false;
                    for (int i = 0; i < strCheck.length(); i++) {
                        if (password.getText().charAt(7) == strCheck.charAt(i)) flag1 = true;
                        if (password.getText().charAt(15) == strCheck.charAt(i)) flag2 = true;
                    }
                    if (!flag1) { globalFlag = true; runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("8th symbol allowed characters - !,@,#,$,%,&,*"); } }); }
                    if (!flag2) { globalFlag = true; runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("16th symbol allowed characters - !,@,#,$,%,&,*"); } }); }
                }
                else {
                    boolean flag1 = false;
                    for (int i = 0; i < strCheck.length(); i++) { if (password.getText().charAt(7) == strCheck.charAt(i)) flag1 = true; }
                    if (!flag1) { globalFlag = true; runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("8th's symbol allowed characters - !,@,#,$,%,&,*"); } }); }
                }
            }
            if (!globalFlag) { runOnUiThread(new Runnable() { @Override  public void run() { errMsg.setText("Everything looks good! Task finished!"); } }); }
        }
    };
}
