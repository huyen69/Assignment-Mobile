package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import android.os.CountDownTimer;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class PlayActivity extends AppCompatActivity {
    ArrayList<Question> questionList = new ArrayList<>();
    ArrayList<Question> selectedQuestions = new ArrayList<>();

    TextView cpt_question, text_question, timerTextView;
    Button btn_choose1, btn_choose2, btn_choose3, btn_choose4, btn_next;

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 giây mỗi câu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);
        timerTextView = findViewById(R.id.timerTextView); // Thêm TextView hiển thị thời gian

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        findViewById(R.id.image_back).setOnClickListener(a -> finish());

        populateQuestions();
        Collections.shuffle(questionList);
        selectedQuestions.addAll(questionList.subList(0, 20)); // Chọn 10 câu hỏi ngẫu nhiên

        loadQuestion();
        startTimer(); // Bắt đầu đếm giờ

        btn_next.setOnClickListener(view -> {
            if (isclickBtn) {
                isclickBtn = false;

                if (!valueChoose.equals(selectedQuestions.get(currentQuestion).getCorrectAnswer())) {
                    showPopup("Sai rồi! Câu trả lời đúng là: " + selectedQuestions.get(currentQuestion).getCorrectAnswer(), false);
                    btn_click.setBackgroundResource(R.drawable.background_btn_erreur);
                } else {
                    showPopup("Chính xác!", true);
                    btn_click.setBackgroundResource(R.drawable.background_btn_correct);
                    scorePlayer++;
                }

                new Handler().postDelayed(() -> {
                    if (currentQuestion < 9) {
                        currentQuestion++;
                        loadQuestion();
                        resetButtonColors();
                        resetTimer(); // Reset lại bộ đếm thời gian
                    } else {
                        Intent intent = new Intent(PlayActivity.this, ResulteActivity.class);
                        intent.putExtra("Result", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(PlayActivity.this, "Chọn đáp án trước khi tiếp tục!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timerTextView.setText("Thời gian: " + (millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                showPopup("Hết thời gian! Câu trả lời đúng là: " + selectedQuestions.get(currentQuestion).getCorrectAnswer(), false);

                new Handler().postDelayed(() -> {
                    if (currentQuestion < 9) {
                        currentQuestion++;
                        loadQuestion();
                        resetButtonColors();
                        resetTimer(); // Reset lại bộ đếm thời gian
                    } else {
                        Intent intent = new Intent(PlayActivity.this, ResulteActivity.class);
                        intent.putExtra("Result", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 2000); // Chuyển câu sau 2 giây
            }

        }.start();
    }


    private void resetTimer() {
        countDownTimer.cancel();
        timeLeftInMillis = 30000;
        startTimer();
    }

    private void showPopup(String message, boolean isCorrect) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isCorrect ? "Chính xác!" : "Sai rồi!")
                .setMessage(message)
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    void populateQuestions() {
        questionList.add(new Question("Ca sĩ nào đã bị đồn là bỏ con ?", "Jack", "J97", "Trịnh Trần Phương Tuấn", "Tất cả đều đúng", "Tất cả đều đúng"));
        questionList.add(new Question("Bài hát 'Enchanted' là của ai?", "Taylor Swift", "Ed Sheeran", "Adele", "Justin Bieber", "Taylor Swift"));
        questionList.add(new Question("Cristiano Ronaldo giành bao nhiêu Champions League tại Real Madrid?", "1", "2", "4", "5", "4"));
        questionList.add(new Question("Thủ đô của Pháp là gì?", "Berlin", "Madrid", "Paris", "Rome", "Paris"));
        questionList.add(new Question("3 x 3 = ?", "6", "9", "12", "15", "9"));
        questionList.add(new Question("Ai là tác giả của 'Harry Potter'?", "J.K. Rowling", "Stephen King", "Agatha Christie", "George R.R. Martin", "J.K. Rowling"));
        questionList.add(new Question("Nước nào có dân số đông nhất thế giới?", "Ấn Độ", "Mỹ", "Trung Quốc", "Nga", "Trung Quốc"));
        questionList.add(new Question("Màu nào có bước sóng dài nhất?", "Đỏ", "Xanh", "Tím", "Vàng", "Đỏ"));
        questionList.add(new Question("Quốc gia nào có diện tích lớn nhất thế giới?", "Mỹ", "Canada", "Nga", "Trung Quốc", "Nga"));
        questionList.add(new Question("Nguyên tố hóa học nào có ký hiệu 'O'?", "Oxy", "Vàng", "Bạc", "Đồng", "Oxy"));
        questionList.add(new Question("5 x 5 = ?", "10", "20", "25", "30", "25"));
        questionList.add(new Question("Nguyên tố nào là kim loại lỏng ở nhiệt độ phòng?", "Sắt", "Thủy ngân", "Nhôm", "Kẽm", "Thủy ngân"));
        questionList.add(new Question("Thành phố nào có biệt danh 'Big Apple'?", "Los Angeles", "New York", "Chicago", "San Francisco", "New York"));
        questionList.add(new Question("Năm nào Việt Nam giành chức vô địch AFF Cup lần đầu tiên?", "2008", "2010", "2016", "2018", "2008"));
        questionList.add(new Question("Hành tinh nào lớn nhất trong Hệ Mặt Trời?", "Sao Hỏa", "Sao Kim", "Sao Mộc", "Sao Thổ", "Sao Mộc"));
        questionList.add(new Question("Loài động vật nào ngủ ít nhất?", "Ngựa", "Hươu cao cổ", "Dơi", "Cá heo", "Hươu cao cổ"));
        questionList.add(new Question("Năm nào xảy ra Cách mạng tháng 8 ở Việt Nam?", "1930", "1941", "1945", "1954", "1945"));
        questionList.add(new Question("Bộ phim nào giành nhiều giải Oscar nhất?", "Titanic", "Avatar", "La La Land", "Parasite", "Titanic"));
        questionList.add(new Question("Ai phát minh ra bóng đèn?", "Nikola Tesla", "Albert Einstein", "Thomas Edison", "Isaac Newton", "Thomas Edison"));
        questionList.add(new Question("Ngôn ngữ lập trình nào phổ biến nhất năm 2023?", "C++", "Python", "Java", "JavaScript", "Python"));
        questionList.add(new Question("Mèo có bao nhiêu râu trung bình?", "8", "12", "24", "32", "24"));
        questionList.add(new Question("Loài chim nào có thể bay ngược?", "Chim sẻ", "Chim ruồi", "Chim đại bàng", "Chim bồ câu", "Chim ruồi"));
        questionList.add(new Question("Bảng tuần hoàn hóa học có bao nhiêu nguyên tố?", "108", "118", "128", "138", "118"));
        questionList.add(new Question("Sông nào dài nhất thế giới?", "Amazon", "Nile", "Mekong", "Mississippi", "Nile"));
        questionList.add(new Question("Môn thể thao nào phổ biến nhất thế giới?", "Bóng rổ", "Bóng chày", "Bóng đá", "Quần vợt", "Bóng đá"));
        questionList.add(new Question("Biểu tượng hóa học của Vàng là gì?", "Ag", "Au", "Pb", "Fe", "Au"));
        questionList.add(new Question("Loài động vật nào có trí nhớ tốt nhất?", "Voi", "Cá heo", "Chó", "Mèo", "Voi"));
        questionList.add(new Question("Tổ chức nào điều hành Thế vận hội?", "FIFA", "IOC", "UEFA", "NBA", "IOC"));
        questionList.add(new Question("Cấu trúc dữ liệu nào hoạt động theo nguyên tắc LIFO?", "Queue", "Stack", "Array", "Linked List", "Stack"));
        questionList.add(new Question("Ai là người đầu tiên đặt chân lên Mặt Trăng?", "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "John Glenn", "Neil Armstrong"));
        questionList.add(new Question("Bộ vi xử lý đầu tiên của Intel có tên là gì?", "Pentium", "Core i3", "4004", "8086", "4004"));
        questionList.add(new Question("Ứng dụng mạng xã hội nào ra mắt đầu tiên?", "Facebook", "Twitter", "MySpace", "Instagram", "MySpace"));
        questionList.add(new Question("Bảng mã ASCII có bao nhiêu ký tự?", "128", "256", "512", "1024", "128"));
        questionList.add(new Question("Tốc độ ánh sáng là bao nhiêu?", "300,000 km/h", "300,000 m/s", "299,792,458 m/s", "150,000 km/s", "299,792,458 m/s"));
        questionList.add(new Question("Quốc gia nào sản xuất cà phê nhiều nhất?", "Colombia", "Việt Nam", "Brazil", "Ethiopia", "Brazil"));
        questionList.add(new Question("Google được thành lập vào năm nào?", "1995", "1998", "2001", "2004", "1998"));
        questionList.add(new Question("Trong tiếng Anh, từ nào dài nhất không có chữ cái lặp lại?", "Uncopyrightable", "Strengths", "Pneumonoultramicroscopicsilicovolcanoconiosis", "Supercalifragilisticexpialidocious", "Uncopyrightable"));
        questionList.add(new Question("Loài cá nào có thể sống trên cạn?", "Cá mập", "Cá rô phi", "Cá thòi lòi", "Cá chép", "Cá thòi lòi"));
        questionList.add(new Question("Trái Đất quay quanh Mặt Trời mất bao lâu?", "365 ngày", "366 ngày", "24 giờ", "30 ngày", "365 ngày"));
        questionList.add(new Question("Mạng xã hội nào có nhiều người dùng nhất năm 2023?", "Instagram", "Twitter", "Facebook", "TikTok", "Facebook"));
        questionList.add(new Question("Nước nào có nhiều múi giờ nhất?", "Mỹ", "Nga", "Pháp", "Trung Quốc", "Pháp"));

    }

    void loadQuestion() {
        cpt_question.setText((currentQuestion + 1) + "/10");
        text_question.setText(selectedQuestions.get(currentQuestion).getQuestion());

        btn_choose1.setText(selectedQuestions.get(currentQuestion).getOption1());
        btn_choose2.setText(selectedQuestions.get(currentQuestion).getOption2());
        btn_choose3.setText(selectedQuestions.get(currentQuestion).getOption3());
        btn_choose4.setText(selectedQuestions.get(currentQuestion).getOption4());
    }

    public void ClickChoose(View view) {
        btn_click = (Button) view;
        resetButtonColors();
        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }

    void resetButtonColors() {
        btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
    }
}

class Question {
    private final String question, option1, option2, option3, option4, correctAnswer;

    public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getCorrectAnswer() { return correctAnswer; }
}
