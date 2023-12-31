package pt.iade.ricardodiasjoaocoelho.projetosolar.views.MainPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;

import pt.iade.ricardodiasjoaocoelho.projetosolar.R;
import pt.iade.ricardodiasjoaocoelho.projetosolar.controllers.EventController;
import pt.iade.ricardodiasjoaocoelho.projetosolar.controllers.UserInfoController;
import pt.iade.ricardodiasjoaocoelho.projetosolar.models.Event.Event;
import pt.iade.ricardodiasjoaocoelho.projetosolar.models.User.UserInfo;
import pt.iade.ricardodiasjoaocoelho.projetosolar.models.Utils.CalendarItem;
import pt.iade.ricardodiasjoaocoelho.projetosolar.views.Settings_Page;
import pt.iade.ricardodiasjoaocoelho.projetosolar.views.adapters.CalendarAdapter;

public class Profile_Fragment extends Fragment {

    private RecyclerView calendarView;
    Activity currentActivity;

    public Profile_Fragment() {super(R.layout.profile_fragment);}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        currentActivity = getActivity();
        Intent intent = getActivity().getIntent();
        int userId = intent.getIntExtra("userID", 0);

        //TextViews
        TextView name = view.findViewById(R.id.profile_name_display);
        TextView email = view.findViewById(R.id.profile_email_display);
        //ImageView
        ImageView avatar = view.findViewById(R.id.profile_avatar_display);
        //Settings Button
        Button settingsButton = view.findViewById(R.id.profile_settings_button);
        //Calender
        calendarView = view.findViewById(R.id.profile_calendar);

        //Click Listener for Settings Button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), Settings_Page.class);
                myIntent.putExtra("userID", userId);
                startActivity(myIntent);
            }
        });

        setUserInfo(userId, name, email, avatar);
        setCalendarRecycler(view, userId);

        return view;
    }

    private  void setUserInfo(int userId, TextView name, TextView email, ImageView avatar) {
        UserInfoController.getUserInfo(userId, new UserInfoController.ReturnUserInfo() {
            @Override
            public void response(UserInfo user) {
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(user.getUsername());
                        email.setText(user.getEmail());
                        avatar.setImageResource(R.drawable.avatar_profile_picture);
                    }
                });
            }
        });
    }

    private void setCalendarRecycler(View view, int userId) {
        //adapter
        ArrayList<CalendarItem> calendarDataSet = new ArrayList<>();
        //CalendarItem.makeCalendarDataSet(eventList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        calendarView.setLayoutManager(layoutManager);

        CalendarAdapter adapter = new CalendarAdapter(calendarDataSet.toArray(new CalendarItem[0]));
        calendarView.setAdapter(adapter);

        updateCalendarDataSet(userId);
    }

    private void updateCalendarDataSet(int userId) {
        EventController.getUserEvents(userId, new EventController.ReturnEvents() {
            @Override
            public void response(ArrayList<Event> events) {
                events.sort(new Comparator<Event>() {
                    @Override
                    public int compare(Event event, Event t1) {
                        if (event.getStartTime().before(t1.getStartTime()))
                            return -1;
                        else if (event.getStartTime().after(t1.getStartTime()))
                            return 1;
                        return 0;
                    }
                });

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CalendarItem[] calendarDataSet = CalendarItem.makeCalendarDataSet(events);
                        CalendarAdapter adapter = (CalendarAdapter) calendarView.getAdapter();
                        adapter.setCalendarDataSet(calendarDataSet);
                    }
                });
            }
        });
    }
}
