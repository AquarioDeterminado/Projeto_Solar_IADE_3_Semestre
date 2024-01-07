package pt.iade.ricardodiasjoaocoelho.projetosolar.views;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import pt.iade.ricardodiasjoaocoelho.projetosolar.R;

public class MainPage extends AppCompatActivity {

    CircleMenuView circleMenu;

    /* --- Fragments --- */
    Main_Fragment mainFragment = new Main_Fragment();
    Usr_Subscriptions usrSpacesFragment = new Usr_Subscriptions();
    Company_Plan_Selelector_Fragment planSelectorFragment = new Company_Plan_Selelector_Fragment();
    Cowork_Id coworkIdFragment = new Cowork_Id();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage_events);
        setFragment(R.id.mainpage_fragment_frame, mainFragment);

        circleMenu = findViewById(R.id.circle_menu);
        circleMenu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int index) {
                changeFragment(index);
            }
            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                changeFragment(index);
            }
        });
    }
    private void changeFragment(int id) {
        if (id == 0) {
            setFragment(R.id.mainpage_fragment_frame, mainFragment);
        } else if (id == 1) {
            setFragment(R.id.mainpage_fragment_frame, usrSpacesFragment);
        } else if (id == 2) {
            setFragment(R.id.mainpage_fragment_frame, planSelectorFragment);
        } else if (id == 3) {
            setFragment(R.id.mainpage_fragment_frame, coworkIdFragment);
        } else if (id == 4) {
            Intent intent = new Intent(MainPage.this, Profile.class);
            startActivity(intent);
        } else throw new IllegalStateException("Unexpected value: " + id);
    }
    private void setFragment(int id , Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }
}