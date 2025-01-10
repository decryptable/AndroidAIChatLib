package com.decryptable.chatai;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.decryptable.aichat.ChatData;
import com.decryptable.aichat.UserData;
import com.decryptable.aichat.Utils;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private UserData registeredUser;
    private ChatData botResponse;
    private TextView textResponse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResponse = findViewById(R.id.textResponse);

        String androidID = UUID.randomUUID().toString();

        Utils.registerUser(androidID, userData -> {
            if (userData != null) {
                registeredUser = (UserData) userData;
                Log.d("MainActivity", "User Registered: " + registeredUser.getData().getUid());
            } else {
                Log.e("MainActivity", String.valueOf(R.string.failed_to_register_user));

                runOnUiThread(() -> {
                    textResponse.setText(R.string.failed_to_register_user);
                });
            }
        });

        Utils.getBotResponse(androidID, "Hello, bot!", UUID.randomUUID().toString(), "", chatData -> {
            if (chatData != null) {
                botResponse = (ChatData) chatData;
                String botAnswer = botResponse.getData().getAnswer();
                Log.d("MainActivity", "Bot Response: " + botAnswer);
                runOnUiThread(() -> {
                    textResponse.setText(String.format("\uD83E\uDD16 BOT: %s", botAnswer));
                });
            } else {
                Log.e("MainActivity", String.valueOf(R.string.failed_to_get_bot_response));

                runOnUiThread(() -> {
                    textResponse.setText(R.string.failed_to_get_bot_response);
                });
            }
        });
    }
}
