package com.xynotech.goodbye;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Permission;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 314;
    public static final String MESSAGE_ID = "messageId";
    HashMap<String, String> dataMaps = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initMaps();

        ((TextView) findViewById(R.id.scan_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ScanActivity.class), REQUEST_CODE);
            }
        });

    }

    private void initMaps() {
        dataMaps.put("2100000001", "asad");
        dataMaps.put("2100000002", "annis");
        dataMaps.put("2100000003", "qadeer");
        dataMaps.put("2100000004", "waseem");
        dataMaps.put("2100000005", "pawan");
        dataMaps.put("2100000006", "osama");
        dataMaps.put("2100000007", "pawan");
        dataMaps.put("2100000008", "sharjeel");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String messageId = data.getStringExtra(MESSAGE_ID);
        String messageToShow = getString(getStringIdentifier(MainActivity.this, dataMaps.get(messageId)));
        showMaterialDialog(dataMaps.get(messageId), messageToShow);

    }

    private void showMaterialDialog(String name, String messageToShow) {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle("Hi " + name)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setDescription(messageToShow)
                .setPositiveText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                    }
                }).check();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

}
