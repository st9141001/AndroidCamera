package tw.android;

import java.io.IOException;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;


public class CameraCallback implements Callback {

    private static final String TAG = "CameraCallback";
    private Camera carema;

    public Camera getCarema() {
        return this.carema;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "�Ұʬ۾�...");
        this.carema = Camera.open();
        try {
            Log.d(TAG, "�]�w�w������");
            this.carema.setPreviewDisplay(holder);
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d(TAG, "�}�l�w��...");
        this.carema.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "����w��...");
        this.carema.stopPreview();
        Log.d(TAG, "����۾��귽...");
        this.carema.release();
        this.carema = null;
    }

	
}
