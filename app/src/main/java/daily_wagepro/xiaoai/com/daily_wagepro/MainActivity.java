package daily_wagepro.xiaoai.com.daily_wagepro;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hxh.component.basicore.util.aspj.PermissionCheck;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @PermissionCheck(permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION
    })
    @Override
    protected void onResume() {
        super.onResume();
    }
}
