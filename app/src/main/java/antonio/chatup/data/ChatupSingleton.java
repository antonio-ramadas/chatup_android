package antonio.chatup.data;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 26-05-2016.
 */
public class ChatupSingleton {
    private static ChatupSingleton ourInstance = new ChatupSingleton();

    public static ChatupSingleton getInstance() {
        return ourInstance;
    }

    private final static String HTTP_S = "http://";
    private final static String IP = "172.30.4.230";
    private final static String PORT = "8080";
    private final static String INIT_URL = HTTP_S + IP + ":" + PORT + "/";

    private String userEmail;
    private String userToken;
    private List<Room> rooms = new ArrayList<Room>();

    private ChatupSingleton() {
    }

    public int getRoomIndex(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).compareID(id))
                return i;
        }

        return -1;
    }

    public void set(String email, String token) {
        userEmail = email;
        userToken = token;
        getRooms().add(new Room(0, "", "teste", Room.PUBLIC_ROOM, 5));
    }

    public void add(Room room) {
        getRooms().add(room);
    }

    public JSONObject createJSON(Requests request, String... args) throws JSONException, IllegalArgumentException {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException();
        }

        JSONObject json = new JSONObject();

        JSONObject valuesJSON = new JSONObject();
        for (int i = 0; i < args.length; i += 2) {
            valuesJSON.put(args[i], args[i+1]);
        }

        json.put(request.toString(), valuesJSON);

        return json;
    }

    public String createGet(HTTP_Directories directory, String... args) throws IllegalArgumentException {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException();
        }

        String dir = INIT_URL + directory;

        if (args.length == 0) {
            return dir;
        }

        dir += "?";
        boolean first = true;
        for (int i = 0; i < args.length; i += 2) {
            if (first) {
                first = false;
            } else {
                dir += "&";
            }

            dir += (args[i] + "=" + args[i]);
        }

        return dir;
    }

    public JSONObject get(HTTP_Directories dir, HTTP_Methods method, JSONObject arg) {
        //open connection
        HttpURLConnection urlConnection = open(dir.toString());
        if (urlConnection == null) {
            return null;
        }

        //send args
        if (!method.equals(HTTP_Methods.GET)) {
            configConnection(urlConnection, method);
            write(urlConnection, arg);
        }

        //read response
        JSONObject response = read(urlConnection);

        //disconnect
        urlConnection.disconnect();

        return response;
    }

    private JSONObject read(HttpURLConnection urlConnection) {
        JSONObject response = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String received = convertStreamToString(br);
            response = new JSONObject(received);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void write(HttpURLConnection outputStream, JSONObject arg) {
        try {
            OutputStream out = new BufferedOutputStream(outputStream.getOutputStream());
            out.write(arg.toString().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configConnection(HttpURLConnection urlConnection, HTTP_Methods method) {
        try {
            urlConnection.setRequestMethod(method.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
    }

    private HttpURLConnection open(String url_str) {
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(INIT_URL + url_str);
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return urlConnection;
    }

    @NonNull
    private String convertStreamToString(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        return sb.toString();
    }

    public Room getRoom(int index) {
        return getRooms().get(index);
    }

    public int sizeRooms() {
        return getRooms().size();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserToken() {
        return userToken;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
