package com.lqk.effecteam.common.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;

/**
 * 日历Activity
 */
public class CalenderActivity extends BaseActivity {

    private TitleBar titleBar;
    private CalendarView calendarView;

    private static final int PICK_DATE_RESULT = 0;
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.calendar_title_bar);
        titleBar.setLeftClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("dateString", dateString);
            setResult(PICK_DATE_RESULT, intent);
            finish();
        });
        titleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                Intent intent = new Intent();
                intent.putExtra("dateString", dateString);
                setResult(PICK_DATE_RESULT, intent);
                finish();
            }
        });
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                Calendar selectedCalendar = calendarView.getSelectedCalendar();
                dateString = selectedCalendar.getYear() + "年" + selectedCalendar.getMonth() + "月" + selectedCalendar.getDay() + "日";
                titleBar.setTitle(dateString);
            }
        });
    }

}