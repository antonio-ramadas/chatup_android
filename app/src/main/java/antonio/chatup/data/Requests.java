package antonio.chatup.data;

/**
 * Created by Antonio on 25-05-2016.
 */
public enum Requests {
    CREATE_ROOM("CreateRoom"),
    LEAVE_ROOM("LeaveRoom"),
    JOIN_ROOM("JoinRoom"),
    USER_LOGIN("UserLogin"),
    USER_DISCONNECT("UserDisconnect"),
    SEND_MESSAGE("SendMessage");

    private String request;

    Requests(String req) {
        request = req;
    }

    @Override
    public String toString() {
        return request;
    }
}
