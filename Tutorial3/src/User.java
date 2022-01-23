import java.util.ArrayList;

public class User {
    private String userName;
    private boolean online;
    private ArrayList<Song> songList;

    public User() {
        this("");
    }

    public User(String u) {
        userName = u;
        online = false;
        songList = new ArrayList<Song>();
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public boolean isOnline() {
        return online;
    }

    //returns a name, the number of songs, and if the user is online or not
    public String toString() {
        String s = "" + userName + ": " + songList.size() + " songs (";
        if (!online) s += "not ";
        return s + "online)";
    }

    //adds a song to the end of a list
    public void addSong(Song s) {
        songList.add(s);
        s.setOwner(this);
    }

    //gets total song time in seconds
    public int totalSongTime() {
        int totalTime = 0;
        for (Song s : songList) {
            totalTime += s.getDuration();
        }
        return totalTime;
    }

    public void register(MusicExchangeCenter m) {
        m.registerUser(this);
    }

    public void logon() {
        online = true;
    }

    public void logoff() {
        online = false;
    }

    //returns a list of strings in the desired format
    public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m) {
        ArrayList<String> songList = new ArrayList<String>();
        int lineNumber = 1;
        songList.add("    " + String.format("%-30S%-17S%-7S%-20S", "TITLE", "ARTIST", "TIME", "OWNER"));
        songList.add("");
        for (Song s : m.allAvailableSongs()) {
            songList.add(String.format("%2d", lineNumber) + String.format("%-32s%-17s%-7s%-20s", ". " + s.getTitle(), s.getArtist(),
                    s.getMinutes() + ":" + String.format("%02d", s.getSeconds()), s.getOwner().getUserName()));
            lineNumber++;
        }
        return songList;
    }

    public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist) {
        ArrayList<String> songList = new ArrayList<String>();
        int lineNumber = 1;
        songList.add("    " + String.format("%-30S%-17S%-7S%-20S", "TITLE", "ARTIST", "TIME", "OWNER"));
        songList.add("");
        for (Song s : m.availableSongsByArtist(artist)) {
            songList.add(String.format("%2d", lineNumber) + String.format("%-32s%-17s%-7s%-20s", ". " + s.getTitle(), s.getArtist(),
                    s.getMinutes() + ":" + String.format("%02d", s.getSeconds()), s.getOwner().getUserName()));
            lineNumber++;
        }
        return songList;
    }

    public void downloadSong(MusicExchangeCenter m, String title, String ownerName) {
        Song downloadedSong = m.getSong(title, ownerName);
        if(downloadedSong != null){
            this.addSong(downloadedSong);
        }
    }
}
