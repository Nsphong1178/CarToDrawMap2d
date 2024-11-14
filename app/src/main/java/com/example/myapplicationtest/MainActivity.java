package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Nút Forward
        Button forwardButton = findViewById(R.id.buttonforward);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kích hoạt yêu cầu đến server
                new SendRequestTask(MainActivity.this).execute("http://192.168.0.11/forward");
            }
        });

        // Nút Backward
        Button backwardButton = findViewById(R.id.buttonbackward);
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kích hoạt yêu cầu đến server
                new SendRequestTask(MainActivity.this).execute("http://192.168.0.11/backward");
            }
        });

        // Nút Left
        Button leftButton = findViewById(R.id.buttonLeft);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kích hoạt yêu cầu đến server
                new SendRequestTask(MainActivity.this).execute("http://192.168.0.11/left");
            }
        });

        // Nút Right
        Button rightButton = findViewById(R.id.buttonRight);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kích hoạt yêu cầu đến server
                new SendRequestTask(MainActivity.this).execute("http://192.168.0.11/right");
            }
        });
    }

    // AsyncTask để gửi yêu cầu đến server trong nền
    private static class SendRequestTask extends AsyncTask<String, Void, Boolean> {
        private Context context;

        // Constructor để nhận Context
        public SendRequestTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                // Kiểm tra mã phản hồi từ server
                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK; // Trả về true nếu mã phản hồi là 200
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            // Hiển thị thông báo dựa trên kết quả kết nối
            if (success) {
                Toast.makeText(context, "Kết nối thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Kết nối thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
