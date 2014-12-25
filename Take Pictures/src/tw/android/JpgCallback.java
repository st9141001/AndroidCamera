package tw.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;

public class JpgCallback implements PictureCallback {

    private static final String TAG = "JpgCallback";
    private String picPath;
    private TakeAPhotoActivity act;
    int number;
    public JpgCallback(TakeAPhotoActivity act) {
        super();
        this.act = act;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(TAG, "�B�z JPG ��ơA��X jpg ��...");
        FileOutputStream os = null;
        try {
            File pic = this.createPicFile();
            os = new FileOutputStream(pic);
            os.write(data);
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (IOException e) {
                }
            }
        }
        Log.d(TAG, "��X JPG ����");
        // ��ܷӤ�
        this.act.showPhoto(this.picPath);
    }

    @SuppressLint("DefaultLocale")
	private File createPicFile() {
        File sdDir = Environment.getExternalStorageDirectory();
        Log.d(TAG, "sdDir" + sdDir);
        File picDir = new File(sdDir, "takePic"); //�إߩ�m�Ӥ���Ƨ�
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        long ctm =System.currentTimeMillis(); //�Ӥ��W
        
        /* SharedPreferences */
        try {
			SharedPreferences preferencesGet = this.act
					.getSharedPreferences("takePic",
							android.content.Context.MODE_PRIVATE);
			number=preferencesGet.getInt("number", 0); //�Ӥ��ƶq
			
		} catch (Exception e) {

		}
        
		SharedPreferences preferencesSave = this.act
				.getSharedPreferences("takePic",
						android.content.Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencesSave.edit();
		
		Log.d(TAG,number+"");
		number++; //�Ӥ��ƶq+1
		editor.putInt("number", number);
		editor.putLong(Integer.toString(number), ctm); //�x�s�Ӥ��W
		editor.commit();
		
		
        String fileName = String.format("%d.jpg", ctm);
        File pic = new File(picDir, fileName);
        this.picPath = pic.getAbsolutePath();
        Log.d(TAG, "�Ӥ����|�G" + this.picPath);
        return pic;
    }
}