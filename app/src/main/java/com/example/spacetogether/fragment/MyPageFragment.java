package com.example.spacetogether.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.LoginActivity;
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.data.History;
import com.example.spacetogether.data.Lecture;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.Schedule;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.Date;

public class MyPageFragment extends Fragment {

    private TextView username;
    private TextView userId;
    private TimetableView timetableView;

    private String getUserNameFromID(String id) {
        String ret = "";
        if (id.equals(MainActivity.app_user.get_id())) {
            return "나";
        }
        if (MainActivity.app_user.getFriendsUser() != null) {
            for (User friend : MainActivity.app_user.getFriendsUser()) {
                if (friend.get_id().equals(id)) {
                    return friend.getUsername() + "님";
                }
            }
        }
        return ret;
    }

    public ArrayList<ArrayList<com.github.tlaabs.timetableview.Schedule>> getSchedules() {
        int j = 0;
        ArrayList<ArrayList<com.github.tlaabs.timetableview.Schedule>> ret = new ArrayList<>();
        for (Lecture lecture : MainActivity.app_user.getTimetable()) {
            ArrayList<com.github.tlaabs.timetableview.Schedule> schedules = new ArrayList<>();
            for (int i = 0; i < lecture.getSchedule().size(); i++) {
                Schedule schedule = lecture.getSchedule().get(i);
                com.github.tlaabs.timetableview.Schedule viewSchedule = new com.github.tlaabs.timetableview.Schedule();
                viewSchedule.setClassTitle(lecture.getLectureName());
                viewSchedule.setDay(schedule.getStartDate().getDay() - 1);
                viewSchedule.setStartTime(new Time(schedule.getStartDate().getHours(), schedule.getStartDate().getMinutes()));
                viewSchedule.setEndTime(new Time(schedule.getEndDate().getHours(), schedule.getEndDate().getMinutes()));
                schedules.add(viewSchedule);
            }
            ret.add(schedules);
        }
        return ret;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);
        setHasOptionsMenu(true);
        username = root.findViewById(R.id.username);
        username.setText(MainActivity.app_user.getUsername());
        userId = root.findViewById(R.id.user_id);
        userId.setText("ID: " + MainActivity.app_user.getUserId());
        LinearLayout linearLayout = root.findViewById(R.id.mypage_history_list);
        for (History history : MainActivity.app_user.getHistory()) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_history, null);
            String s = "";
            for (String user : history.getMeeting()) {
                String username = getUserNameFromID(user);
                s = s + username + " ";
            }
            s += "과 함께";
            ((TextView) view.findViewById(R.id.item_history_subtitle)).setText(s);
            ((TextView) view.findViewById(R.id.item_history_title)).setText(history.getRestaurantInfo());
            ((TextView) view.findViewById(R.id.item_history_time)).setText(history.getVisitedAt().toString());
            linearLayout.addView(view);
        }

        timetableView = root.findViewById(R.id.timetable);
        for (ArrayList<com.github.tlaabs.timetableview.Schedule> s : getSchedules()) {
            timetableView.add(s);
        }

        root.findViewById(R.id.add_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlg = new Dialog(getContext());
                boolean days[] = {false, false, false, false, false};
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dlg.setContentView(R.layout.dialog_add_schedule);
                dlg.show();
                dlg.findViewById(R.id.dialog_cancel).setOnClickListener(v1 -> dlg.dismiss());
                dlg.findViewById(R.id.dialog_ok).setOnClickListener(v12 -> {
                    String lectureName = ((TextView) dlg.findViewById(R.id.dialog_lecture_name)).getText().toString();
                    for (int i = 0; i < 5; i++) {
                        if (days[i]) {
                            Lecture l = null;
                            for (Lecture lecture : MainActivity.app_user.getTimetable()) {
                                if (lecture.getLectureName().equals(lectureName)) {
                                    l = lecture;
                                    break;
                                }
                            }
                            if (l == null) {
                                l = new Lecture(lectureName, new ArrayList<>());
                                l.setLectureName(lectureName);
                                MainActivity.app_user.getTimetable().add(l);
                            }
                            Date start = new Date();
                            start.setDate(start.getDate() + (1 + i) - start.getDay());
                            start.setHours(Integer.parseInt(((TextView) dlg.findViewById(R.id.start_hour)).getText().toString()));
                            start.setMinutes(Integer.parseInt(((TextView) dlg.findViewById(R.id.start_minute)).getText().toString()));
                            Date end = new Date();
                            end.setDate(end.getDate() + (1 + i) - end.getDay());
                            end.setHours(Integer.parseInt(((TextView) dlg.findViewById(R.id.end_hour)).getText().toString()));
                            end.setMinutes(Integer.parseInt(((TextView) dlg.findViewById(R.id.end_minute)).getText().toString()));
                            Schedule schedule = new Schedule(start, end);
                            l.getSchedule().add(schedule);
                        }
                    }
                    RetrofitClient.getOdysseyService().setUser(PreferenceManager.getString(getContext(), "token"), PreferenceManager.getString(getContext(), "token"), MainActivity.app_user).enqueue(new Callback<Result<String>>() {
                        @Override
                        public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                            Toast.makeText(getContext(), "강의를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                            timetableView.removeAll();
                            for (ArrayList<com.github.tlaabs.timetableview.Schedule> s : getSchedules()) {
                                timetableView.add(s);
                            }
                            dlg.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Result<String>> call, Throwable t) {
                            int a = 0;
                        }
                    });
                });
                dlg.findViewById(R.id.schedule_day1).setOnClickListener(v13 -> {
                    days[0] = !days[0];
                    toggleTextView((TextView) v13, days[0]);
                });
                dlg.findViewById(R.id.schedule_day2).setOnClickListener(v14 -> {
                    days[1] = !days[1];
                    toggleTextView((TextView) v14, days[1]);
                });
                dlg.findViewById(R.id.schedule_day3).setOnClickListener(v15 -> {
                    days[2] = !days[2];
                    toggleTextView((TextView) v15, days[2]);
                });
                dlg.findViewById(R.id.schedule_day4).setOnClickListener(v16 -> {
                    days[3] = !days[3];
                    toggleTextView((TextView) v16, days[3]);
                });
                dlg.findViewById(R.id.schedule_day5).setOnClickListener(v17 -> {
                    days[4] = !days[4];
                    toggleTextView((TextView) v17, days[4]);
                });
            }
        });

        return root;
    }

    public void toggleTextView(TextView textView, boolean value) {
        if (value) {
            textView.setBackground(getResources().getDrawable(R.drawable.round_circle));
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.round_circle_border));
            textView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mypage_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                PreferenceManager.setString(getContext(), "token", "");
                MainActivity.app_user = null;
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ScheduleView extends View {
        private int width;
        private int height;
        private int hourHeight;
        private int hourWidth;
        private int gap = 40;
        private Paint schedulePaint = new Paint();
        private String colorSet[] = {"#F79377", "#84E1A8", "#ACABFB", "#7DC5F2", "#6396D5", "#84CFD5", "#E78286"};
        private Canvas canvas;

        public ScheduleView(Context context) {
            super(context);
        }

        public ScheduleView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            this.canvas = canvas;
            drawFrame();
            int i = 0;
            for (Lecture lecture : MainActivity.app_user.getTimetable()) {
                schedulePaint.setColor(Color.parseColor(colorSet.length <= i ? "#707070" : colorSet[i]));
                drawLecture(lecture);
                i++;

            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            width = w;
            height = h;
            hourHeight = (height - gap) / 12;
            hourWidth = (width - gap) / 5;
        }

        private void drawFrame() {
            Paint paint = new Paint();
            paint.setTextSize(80);
            paint.setColor(Color.parseColor("#D2DCE5"));
            paint.setStrokeWidth(3);
            paint.setAntiAlias(true);
            Paint textPaint = new Paint();
            textPaint.setTextSize(30);
            textPaint.setColor(Color.parseColor("#80929E"));
            textPaint.setAntiAlias(true);
            int baseStart = gap;
            int time = 0;
            String[] days = {"월", "화", "수", "목", "금"};
            for (int i = 0; i < 5; i++) {
                canvas.drawLine(baseStart, 0, baseStart, height, paint);
                canvas.drawText(days[time++], baseStart + (width - gap) / 10 - 15, gap / 5 * 4, textPaint);
                baseStart += hourWidth;
            }
            baseStart = gap;
            time = 8;
            for (int i = 0; i < 12; i++) {
                canvas.drawLine(0, baseStart, width, baseStart, paint);
                canvas.drawText(String.valueOf((time++) % 12 + 1), 20 + (((time - 1) % 12 + 1) >= 10 ? -15 : 0), baseStart + 30, textPaint);
                baseStart += hourHeight;
            }
        }

        private void drawLecture(Lecture lecture) {
            for (int i = 0; i < lecture.getSchedule().size(); i++) {
                Schedule schedule = lecture.getSchedule().get(i);
                drawSchedule(schedule, lecture.getLectureName());
            }
        }


        private void drawSchedule(Schedule schedule, String lectureName) {
            if (schedule.getStartDate().getDay() == 0 || schedule.getStartDate().getDay() == 6 ||
                    schedule.getEndDate().getDay() != schedule.getStartDate().getDay())
                return;
            if (schedule.getStartDate().getHours() < 9 || schedule.getStartDate().getHours() > 20 ||
                    schedule.getEndDate().getHours() < 9 || schedule.getEndDate().getHours() > 20)
                return;

            canvas.drawRect(gap + (schedule.getStartDate().getDay() - 1) * hourWidth, gap + (schedule.getStartDate().getHours() - 9) * hourHeight,
                    gap + schedule.getStartDate().getDay() * hourWidth, gap + (schedule.getEndDate().getHours() - 9) * hourHeight, schedulePaint);

        }
    }

}

