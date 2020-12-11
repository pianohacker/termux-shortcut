package re.weav.termuxshortcut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {
	@Override
	public void onStart() {
		if (checkSelfPermission("com.termux.permission.RUN_COMMAND") == PERMISSION_GRANTED) {
			Log.e("TERMUX", "FAST LAUNCHIN'");
			launchTermux();
		} else {
			Log.e("TERMUX", "ASKIN'");
			requestPermission.launch("com.termux.permission.RUN_COMMAND");
		}

		super.onStart();
	}

	private ActivityResultLauncher<String> requestPermission =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            // Do something if permission granted
            if (isGranted) {
				launchTermux();
            } else {
                throw new RuntimeException("com.termux.permission.RUN_COMMAND permission denied");
            }
        });

	private void launchTermux() {
 		Intent intent = new Intent();
 		intent.setClassName("com.termux", "com.termux.app.RunCommandService");
 		intent.setAction("com.termux.RUN_COMMAND");
 		intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/pachinko");
 		intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{});
 		intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
 		intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
 		startService(intent);

		finish();
	}
}
