package tw.android;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class TakeAPhotoActivity extends Activity {

	private static final String TAG = "TakeAPhotoActivity";
	private SurfaceView sv;
	// private ImageView iv;
	private Button takePhotoBtn, hidePhotoBtn, autoFocus,picList;
	// �۾� callback
	private CameraCallback cc;
	// �֪� callback
	private ShCallback sc;
	// �B�z raw data callback
	private RawCallback rc;
	// �B�z jpg callback
	private JpgCallback jc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_a_photo_activity);

		this.sv = (SurfaceView) this.findViewById(R.id.sv);
		this.takePhotoBtn = (Button) this.findViewById(R.id.takePhotoBtn);
		this.hidePhotoBtn = (Button) this.findViewById(R.id.hidePhotoBtn);
		this.autoFocus = (Button) this.findViewById(R.id.autoFocus);
		this.picList= (Button) this.findViewById(R.id.picList);
		
		this.cc = new CameraCallback();
		this.sc = new ShCallback();
		this.rc = new RawCallback();
		this.jc = new JpgCallback(this);

		Log.d(TAG, "�]�w�w������...");
		SurfaceHolder sh = this.sv.getHolder();
		sh.addCallback(this.cc);
		sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Log.d(TAG, "�]�w��ӭ���...");
		this.hidePhotoBtn.setVisibility(View.GONE);

		// ���s�~���۰ʹ�J
		autoFocus.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				cc.getCarema().autoFocus(new AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean success, Camera camera) {

					}
				});
			}
		});
		//�e���Ӥ��C��
		picList.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				goToPicList();
			}
		});
		
	}

	public void takePhoto(View v) {
		Log.d(TAG, "���...");
		// �ݭn�T�� callback�G�֪��B�B�z raw data�B�B�z jpg
		// ��Ӯɦ۰ʹ�J
		this.cc.getCarema().autoFocus(new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				if (success) {
					camera.takePicture(sc, rc, jc);
				}
			}
		});
	}

	public void hidePhoto(View v) {
		Log.d(TAG, "�]�w��ӭ���...");
		this.takePhotoBtn.setVisibility(View.VISIBLE);// ��ܩ�ӫ��s
		this.hidePhotoBtn.setVisibility(View.GONE); // ���í�����s

		Log.d(TAG, "�^���ӥ\��A�ݭ��s�Ұʹw��...");
		this.cc.getCarema().startPreview();
	}

	public void showPhoto(String picPath) {
		Log.d(TAG, "���o�Ӥ����|�G" + picPath);
		Log.d(TAG, "�]�w�Ӥ�����...");
		this.takePhotoBtn.setVisibility(View.GONE);
		this.hidePhotoBtn.setVisibility(View.VISIBLE);

	}

	//�e���Ӥ��C��
	 public void goToPicList(){
		 Intent intent=new Intent(this,PhotoList.class);
		 startActivity(intent);
	 }

}
