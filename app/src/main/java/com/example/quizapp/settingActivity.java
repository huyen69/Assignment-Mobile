package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class settingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        Button btnContactSupport = findViewById(R.id.btnContactSupport);
        ImageButton btnBack = findViewById(R.id.btnBack);

//        // Xử lý gửi email hỗ trợ
//        btnContactSupport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendSupportEmail();
//            }
//        });

        // Xử lý nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng activity hiện tại và quay lại màn hình trước
            }
        });
    }

    private void sendSupportEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:support@quizapp.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hỗ trợ QuizApp");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Xin chào đội ngũ hỗ trợ, tôi cần giúp đỡ về...");

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}
