import java.util.*;

public class MusicExchangeCenter {

    private ArrayList<User> users;
    private ArrayList<Song> downloadedSongs;
    private HashMap<String, Float> royalties;

    public MusicExchangeCenter() {
        users = new ArrayList<User>();
        royalties = new HashMap<String, Float>();
        downloadedSongs = new ArrayList<Song>();
    }

    //returns all online users
    public ArrayList<User> onlineUsers() {
        ArrayList<User> onlineUsers = new ArrayList<User>();
        //checks to see if each user is online or not
        for (User u : users) {
            if (u.isOnline()) {
                //if online, add them to a list
                onlineUsers.add(u);
            }
        }
        return onlineUsers;
    }

    //returns all songs from all online users
    public ArrayList<Song> allAvailableSongs() {
        ArrayList<Song> allSongs = new ArrayList<Song>();
        //checks to see if each user is online or not
        for (User u : users) {
            if (u.isOnline()) {
                //if online, put every song they have into a list
                for (Song s : u.getSongList()) {
                    allSongs.add(s);
                }
            }
        }
        return allSongs;
    }

    //returns a user with the same name as s
    public User userWithName(String s) {
        //for every user, if their name is equal to s, return the current user
        for (User u : users) {
            if (u.getUserName().equals(s)) {
                return u;
            }
        }
        return null;
    }

    public void registerUser(User x) {
        if (userWithName(x.getUserName()) == null) {
            users.add(x);
        }
    }

    //returns all songs by the given artist
    public ArrayList<Song> availableSongsByArtist(String artist) {
        ArrayList<Song> artistSongs = new ArrayList<Song>();
        //for every user, and every song they have, if the song is made by the artist add it to the list
        for (Song s : allAvailableSongs()) {
            if (s.getArtist().equals(artist)) {
                artistSongs.add(s);
            }
        }

        return artistSongs;
    }

    //returns a song with the given owner and title
    public Song getSong(String title, String ownerName) {
        //for every user, check to see for a match
        for (User u : onlineUsers()) {
            if (u.getUserName().equals(ownerName)) {
                //for every song in the matching user, check to see for a match
                for (Song s : u.getSongList()) {
                    //if the song and user match, return the song object
                    if (s.getTitle().equals(title)) {
                        downloadedSongs.add(s);
                        if (royalties.containsKey(s.getArtist())) {
                            royalties.put(s.getArtist(), (royalties.get(s.getArtist()) + 0.25f));
                        } else {
                            royalties.put(s.getArtist(), 0.25f);
                        }
                        return s;
                    }
                }
            }
        }
        return null;
    }

    //WIP
    public TreeSet<Song> uniqueDownloads() {
        TreeSet<Song> downloads = new TreeSet<Song>();
        for (Song s : downloadedSongs) {
            if (!downloads.contains(s)) {
                downloads.add(s);
            }
        }
        return downloads;
    }

    //WIP
    public ArrayList<Pair<Integer, Song>> songsByPopularity() {
        ArrayList<Pair<Integer, Song>> downloads = new ArrayList<Pair<Integer, Song>>();
        for (Song s : downloadedSongs) {
            boolean songLogged = false;
            for (Pair<Integer, Song> p : downloads) {
                if (p.getValue().equals(s)) {
                    downloads.set(downloads.indexOf(p),new Pair(p.getKey() + 1, s));
                    songLogged = true;
                }
            }
            if (!songLogged) {
                downloads.add(new Pair(1, s));
            }
        }
        Collections.sort(downloads, new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey() - p1.getKey();
            }
        });
        return downloads;
    }


    public void displayRoyalties() {
        System.out.println("Amount  Artist");
        System.out.println("--------------");
        for (String artist : royalties.keySet()) {
            System.out.println("$" + String.format("%.2f", royalties.get(artist)) + "  " + artist);
        }
    }

    public ArrayList<Song> getDownloadedSongs(){
        return downloadedSongs;
    }

    //overrides the innate toString method
    @Override
    public String toString() {
        return "Music Exchange Center (" + onlineUsers().size() + " users online, "
                + allAvailableSongs().size() + " songs available)";
    }

}
