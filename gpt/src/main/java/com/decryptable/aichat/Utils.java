package com.decryptable.aichat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utils {
    private static final String CIPHER_KEY = "03790257ca9d3ed1";

    public static String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registerUser(String uid, OnTaskCompletedListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                String md5Input = uid + CIPHER_KEY;
                String randKeyInput = timestamp + uid + CIPHER_KEY;

                String md5 = generateMD5(md5Input);
                String randKey = generateMD5(randKeyInput);

                // Create payload
                JSONObject payload = new JSONObject();
                payload.put("uid", uid);
                payload.put("md5", md5);

                // Send POST request
                HttpURLConnection connection = getConnection("https://chat.emoji-keyboard.com/api/v1/RegisterUser", uid, timestamp, randKey);
                OutputStream os = connection.getOutputStream();
                os.write(payload.toString().getBytes());
                os.close();

                UserData userData = getUserData(connection);

                // Call listener on the main thread
                if (listener != null) {
                    listener.onTaskCompleted(userData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onTaskCompleted(null);
                }
            }
        });
    }

    private static HttpURLConnection getConnection(String url, String uid, String timestamp, String randKey) throws IOException {
        URL conn = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) conn.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Uid", uid);
        connection.setRequestProperty("Version", "164");
        connection.setRequestProperty("Timestamp", timestamp);
        connection.setRequestProperty("Randkey", randKey);
        connection.setRequestProperty("User-Agent", "aichat Android 164");

        connection.setDoOutput(true);
        return connection;
    }

    private static UserData getUserData(HttpURLConnection connection) throws IOException, JSONException {
        InputStreamReader in = new InputStreamReader(connection.getInputStream());
        int data = in.read();
        StringBuilder response = new StringBuilder();
        while (data != -1) {
            response.append((char) data);
            data = in.read();
        }

        JSONObject responseJson = new JSONObject(response.toString());
        UserData userData = new UserData();
        userData.setRet(responseJson.getInt("ret"));
        userData.setMsg(responseJson.getString("msg"));

        JSONObject userJson = responseJson.getJSONObject("data");
        User user = new User();
        user.setUid(userJson.getString("uid"));
        user.setName(userJson.getString("name"));
        user.setPhoneNumber(userJson.getString("phone_number"));
        userData.setData(user);
        return userData;
    }

    public static void getBotResponse(String uid, String message, String conversationId, String sysPrompt, OnTaskCompletedListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                    int model = 5;

                    String md5Input = conversationId + model + message + uid + CIPHER_KEY;
                    String randKeyInput = timestamp + uid + CIPHER_KEY;

                    String md5 = generateMD5(md5Input);
                    String randKey = generateMD5(randKeyInput);

                    // Create payload
                    JSONObject payload = new JSONObject();
                    payload.put("uid", uid);
                    payload.put("msg", message);
                    payload.put("md5", md5);
                    payload.put("sys_prompt", sysPrompt);
                    payload.put("model", model);
                    payload.put("conversation_id", conversationId);

                    // Send POST request
                    HttpURLConnection connection = getConnection("https://chat.emoji-keyboard.com/api/v1/Chat", uid, timestamp, randKey);
                    OutputStream os = connection.getOutputStream();
                    os.write(payload.toString().getBytes());
                    os.close();

                    ChatData chatData = getChatData(connection);

                    // Call listener on the main thread
                    if (listener != null) {
                        listener.onTaskCompleted(chatData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onTaskCompleted(null);
                    }
                }
            }

            private ChatData getChatData(HttpURLConnection connection) throws IOException, JSONException {
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                int data = in.read();
                StringBuilder response = new StringBuilder();
                while (data != -1) {
                    response.append((char) data);
                    data = in.read();
                }

                // Parse response
                JSONObject responseJson = new JSONObject(response.toString());
                ChatData chatData = new ChatData();
                chatData.setRet(responseJson.getInt("ret"));
                chatData.setMsg(responseJson.getString("msg"));

                JSONObject chatJson = responseJson.getJSONObject("data");
                Chat chat = new Chat();
                chat.setMsgId(chatJson.getString("msg_id"));
                chat.setConversationId(chatJson.getString("conversation_id"));
                chat.setTime(chatJson.getLong("time"));
                chat.setAnswer(chatJson.getString("answer"));

                chatData.setData(chat);
                return chatData;
            }
        });
    }

    public interface OnTaskCompletedListener {
        void onTaskCompleted(Object data);
    }
}
