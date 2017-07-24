package com.zhy.sample.permission;

/**
 * @author 邱永恒
 * @time 2017/7/24  13:36
 * @desc 运行时权限的回调
 */

public interface PermissionsResultListener {
    // 申请成功
    void onPermissionGranted();

    // 申请失败
    void onPermissionDenied();
}
