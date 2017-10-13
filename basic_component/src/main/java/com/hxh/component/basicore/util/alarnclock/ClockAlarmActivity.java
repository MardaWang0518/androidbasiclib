package com.hxh.component.basicore.util.alarnclock;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.hxh.component.basicore.R;


public class ClockAlarmActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private AlertView dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_alarm);
        String message = this.getIntent().getStringExtra("msg");
        int flag = this.getIntent().getIntExtra("flag", 0);
        showDialogInBroadcastReceiver(message, flag);
    }

    private void showDialogInBroadcastReceiver(String message, final int flag) {
        if (flag == 1 || flag == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }
        dialog = new AlertView(null, "\n" + message, null, null, new String[]{"取消", "确定"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (flag == 1 || flag == 2) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                if (flag == 0 || flag == 2) {
                    vibrator.cancel();
                }
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
//        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
//        dialog.show();
//        dialog.setTitle("闹钟提醒");
//        dialog.setMessage(message);
//        dialog.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
//                    if (flag == 1 || flag == 2) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                    }
//                    if (flag == 0 || flag == 2) {
//                        vibrator.cancel();
//                    }
//                    dialog.dismiss();
//                    finish();
//                }
//            }
//        });


    }

}
