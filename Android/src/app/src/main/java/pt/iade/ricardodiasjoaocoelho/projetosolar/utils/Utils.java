package pt.iade.ricardodiasjoaocoelho.projetosolar.utils;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;



public class Utils extends AppCompatActivity {

    /*
    / Creates a launcher that will lunch an activity and show a snack bar with a message
    / @param context: The current active Activity
    / @param parentLayout: The view that will be used to show the snack bar
     */
    public ActivityResultLauncher<Intent> afterLunchSnack(Activity context, View parentLayout) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String message = data.getStringExtra("message");
                        Snackbar confirmation = Snackbar.make(parentLayout , message, Snackbar.LENGTH_LONG);
                        confirmation.setText(message);
                        confirmation.show();
                    }
                }
        );
    }

}
