package antonio.chatup.data;

/**
 * Created by Antonio on 25-05-2016.
 */
public enum HTTP_Directories {
    ROOM_SERVICE("RoomService"),
    USER_SERVICE("UserService"),
    MESSAGE_SERVICE("MessageService");

    private String directory;

    HTTP_Directories(String dir) {
        directory = dir;
    }

    @Override
    public String toString() {
        return directory;
    }
}
