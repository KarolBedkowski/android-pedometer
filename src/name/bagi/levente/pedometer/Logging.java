package name.bagi.levente.pedometer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * Save measured distance, speed etc to file.
 * @author Karol Będkowski
 * Changes: +2011-09-14
 */
public class Logging {
	private static final String TAG = "Pedometer";

	/**
	 * Add line to CSV log file (/sdcard/pedometer.csv).
	 * Create file with header when necessary.
	 * @param context
	 * @param date
	 * @param mSteps
	 * @param mPace
	 * @param mDistance
	 * @param mSpeed
	 * @param mCalories
	 */
	public static void writeDataToLog(Context context, Date date, int mSteps,
			int mPace, float mDistance, float mSpeed, float mCalories) {
		if (mSteps <= 0) {
			return;
		}
		//		Log.d(TAG, "Logging.writeDataToLog " + date.toString() + " steps="
		//				+ mSteps);
		File fOut = new File(Environment.getExternalStorageDirectory(),
				context.getString(R.string.app_name) + ".csv");
		boolean newFile = !fOut.exists();
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(fOut, true);
			if (newFile) {
				fWriter.append(context.getString(R.string.csv_header));
				fWriter.append("\r\n");
			}
			DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
			fWriter.append("\"" + sdf.format(date) + "\",");
			fWriter.append("\"" + String.valueOf(mSteps) + "\",");
			fWriter.append("\"" + String.valueOf(mPace) + "\",");
			NumberFormat nfmtr = DecimalFormat.getInstance(Locale.getDefault());
			fWriter.append("\"" + nfmtr.format(mDistance) + "\",");
			fWriter.append("\"" + nfmtr.format(mSpeed) + "\",");
			fWriter.append("\"" + nfmtr.format(mCalories) + "\"");
			fWriter.append("\r\n");
			fWriter.flush();
		} catch (IOException e) {
			Toast.makeText(context, R.string.error_writting_file,
					Toast.LENGTH_LONG);
			Log.w(TAG, "Logging.writeDataToLog error", e);
		} finally {
			if (fWriter != null) {
				try {
					fWriter.close();
				} catch (IOException e) {
					Toast.makeText(context, R.string.error_writting_file,
							Toast.LENGTH_LONG);
					Log.w(TAG, "Logging.writeDataToLog error close", e);
				}
			}
		}
	}

}
