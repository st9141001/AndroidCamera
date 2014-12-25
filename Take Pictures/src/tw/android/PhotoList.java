package tw.android;

import java.io.File;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class PhotoList extends Activity {

	private Button[] detelPic;
	private String[] dataName;

private int number;

	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_list);
		
		setTable();
	}


	// �ƪ�
	public void setTable() {

		try {
			SharedPreferences preferencesGet = getApplicationContext()
					.getSharedPreferences("takePic",
							android.content.Context.MODE_PRIVATE);
			number=preferencesGet.getInt("number", 0);//���X�Ӥ��ƶq
			dataName = new String[number];
			Log.i("number", number+"");
			for(int i=0;i<number;i++){ 
				dataName[i]=String.valueOf(preferencesGet.getLong(Integer.toString(i+1), 0)); //��J�Ӥ��W��
				Log.i("dataName[i]", dataName[i]+"");
			}

		} catch (Exception e) {

		}
		
		
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);
		t1.removeAllViews();
		TableRow.LayoutParams tP = new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		tP.setMargins(0, 0, 0, 20);

		detelPic = new Button[number];
		for (int i = 0; i < number; i++) { // �C
			TableRow row = new TableRow(this);
			ImageView iv = new ImageView(this);
			String picPath = "/storage/emulated/0/takePic/" + dataName[i]
					+ ".jpg";
			Uri uri = Uri.fromFile(new File(picPath));
			iv.setLayoutParams(tP);
			iv.setImageURI(uri);
			row.addView(iv, 0);

			// �R��button
			detelPic[i] = new Button(this);
			detelPic[i].setText("�R��");
			detelPic[i].setId(i);
			detelPic[i].setOnClickListener(dp); // �ʧ@
			row.addView(detelPic[i], 1);

			t1.addView(row);
		}

	}

	private OnClickListener dp = new OnClickListener() {
		public void onClick(View v) {

			id = v.getId();
			// �R���Ӥ�
			File file = new File("/storage/emulated/0/takePic/" + dataName[id]
					+ ".jpg");
			file.delete();

			
			setTable();//����
		}
	};

}
