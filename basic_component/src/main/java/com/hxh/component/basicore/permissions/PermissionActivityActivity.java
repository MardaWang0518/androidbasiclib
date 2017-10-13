package com.hxh.component.basicore.permissions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.util.AppManager;
import com.hxh.component.basicore.util.Utils;

import com.hxh.component.basicannotation.annotationenum.PermissionDenyEnum;

public class PermissionActivityActivity extends AppCompatActivity {

    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS =
            "permissions"; // 权限参数
    private static final String EXTRA_PERMISSIONSDENYTYPE =
            "denyType"; // 权限参数


    private PermissionsChecker mChecker; // 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测

    // 启动当前权限页面的公开接口
    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionActivityActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    public static void startSelf(Activity activity,PermissionDenyEnum permissionDenyEnum, String... permissions) {
        Intent intent = new Intent(activity, PermissionActivityActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        intent.putExtra(EXTRA_PERMISSIONSDENYTYPE,permissionDenyEnum);

        ActivityCompat.startActivity(activity, intent, null);

    }

    private PermissionDenyEnum mPermissionDenyEnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permission);
        mPermissionDenyEnum = (PermissionDenyEnum) getIntent().getSerializableExtra(EXTRA_PERMISSIONSDENYTYPE);


        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (mChecker.checkPermissions(permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        System.out.println("--->正在获取权限"+permissions);
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        new AlertView(null, "当前应用缺少必要权限。请点击\"设置\"-\"权限\"打开所需权限"+ (mPermissionDenyEnum == PermissionDenyEnum.FINSH?"":",如果您忽略了，那么会导致某些功能无法使用！"),
                mPermissionDenyEnum == PermissionDenyEnum.FINSH ? "离开" : "忽略", null, null, AppManager.getCurrentActivity(),
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if(-1 == position)
                {
                    //当拒绝时候，直接结束掉应用
                    if(mPermissionDenyEnum == PermissionDenyEnum.FINSH)AppManager.finshAllActivity();
                    else if(mPermissionDenyEnum == PermissionDenyEnum.IGNORE)finish();//当决绝时候，关闭掉这个Activity，并继续
                }else
                {
                    startActivity(Utils.IntentUtil.getSystem_SettingIntent());
                }
            }
        }).show();
    }
}
