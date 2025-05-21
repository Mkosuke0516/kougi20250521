package jp.ac.gifu_u.info.magane.myapplication20250521;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private class CameraView extends SurfaceView implements SurfaceHolder.Callback {

        private Camera cam;
        private SurfaceHolder holder;

        public CameraView(Context context) {
            super(context);

            // ホルダーのコールバックに関する設定
            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // カメラを起動しプレビューを表示
            cam = Camera.open(0);
            try {
                cam.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // プレビューを開始
            cam.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // カメラを解放
            if (cam != null) {
                cam.setPreviewCallback(null);
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // タイトルバーなしで全画面表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // カメラビューを作成して表示
        CameraView camview = new CameraView(this);
        setContentView(camview);
    }
}
