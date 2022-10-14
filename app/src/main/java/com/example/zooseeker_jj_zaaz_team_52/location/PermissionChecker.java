package com.example.zooseeker_jj_zaaz_team_52.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import java.util.Arrays;

/**
 * PermissionChecker Class: Single Responsibility - To ensure that app asks for permission to use
 * the user's current location
 */
public class PermissionChecker {
    private final ComponentActivity activity;
    final ActivityResultLauncher<String[]> requestPermissionLauncher;

    public PermissionChecker(ComponentActivity activity) {
        this.activity = activity;
        requestPermissionLauncher = activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                perms -> perms.forEach((perm, isGranted)
                        -> Log.i("Zooseeker", String.format("Permission %s granted: %s", perm, isGranted))));
    }

    public boolean checkAndRequestPermissions() {
        var requiredPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        var hasNoLocationPerms = Arrays.stream(requiredPermissions)
                .map(perm -> ContextCompat.checkSelfPermission(activity, perm))
                .allMatch(status -> status == PackageManager.PERMISSION_DENIED);

        if (hasNoLocationPerms) {
            requestPermissionLauncher.launch(requiredPermissions);
            // Note: the activity will be restarted when permission change!
            // This entire method will be re-run, but we won't get stuck here.
            return true;
        }
        return false;
    }

    public boolean hasLocationPermission() {
        var requiredPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        return Arrays.stream(requiredPermissions)
                .map(perm -> ContextCompat.checkSelfPermission(activity, perm))
                .allMatch(status -> status == PackageManager.PERMISSION_GRANTED);
    }
}
