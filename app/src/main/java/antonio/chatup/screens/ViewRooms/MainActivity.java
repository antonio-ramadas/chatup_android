package antonio.chatup.screens.ViewRooms;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import antonio.chatup.R;
import antonio.chatup.data.ChatupSingleton;
import antonio.chatup.data.HTTP_Directories;
import antonio.chatup.data.HTTP_Methods;
import antonio.chatup.data.Requests;
import antonio.chatup.data.Room;
import antonio.chatup.screens.Login.LoginInitialActivity;
import antonio.chatup.screens.Room.ChatRoom;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RoomFragment.OnListFragmentInteractionListener,
        DialogCreateRoomFragment.OnFragmentInteractionListener,
        DialogAccessRoomFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                new DialogCreateRoomFragment().show(getFragmentManager(), "createRoomDialogTag");
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        createDrawers(navigationView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.getMenu().findItem(R.id.room_item_id).getSubMenu().add("teste");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            RoomFragment rf = new RoomFragment();

            fragmentTransaction.add(R.id.fragment, rf);

            fragmentTransaction.commit();
        }
    }

    private void createDrawers(NavigationView navigationView) {
        SubMenu subMenu = navigationView.getMenu().findItem(R.id.room_item_id).getSubMenu();
        for (Room room : ChatupSingleton.getInstance().getRooms()) {
            MenuItem mItem = subMenu.add(Menu.NONE, room.getId(), room.getId(), room.getName());
            if (room.isPrivate()) {
                mItem.setIcon(android.R.drawable.ic_secure);
            } else {
                mItem.setIcon(android.R.drawable.ic_partial_secure);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //setTitle(item.getTitle());

        if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            chatupLogOut();

        } else if (id != R.id.nav_view_rooms) {
            Intent i = new Intent(getApplicationContext(), ChatRoom.class);
            //pass as argument the room title
            i.putExtra(String.valueOf(R.string.room_index_param), ChatupSingleton.getInstance().getRoomIndex(item.getItemId()));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chatupLogOut() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatupSingleton chatup = ChatupSingleton.getInstance();
                try {
                    JSONObject obj;
                    obj = chatup.createJSON(Requests.USER_DISCONNECT, "email", chatup.getUserEmail(), "token", chatup.getUserToken());
                    JSONObject response = chatup.get(HTTP_Directories.USER_SERVICE, HTTP_Methods.DELETE, obj);

                    Intent i = new Intent(getApplicationContext(), LoginInitialActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    Log.e("Main Activity", "JSON exception", e);
                }
            }
        }).start();
    }

    @Override
    public void onListFragmentInteraction(Room item) {

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
