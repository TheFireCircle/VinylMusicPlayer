package com.poupa.vinylmusicplayer.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import androidx.annotation.NonNull;

import com.poupa.vinylmusicplayer.R;
import com.poupa.vinylmusicplayer.loader.TopAndRecentlyPlayedTracksLoader;
import com.poupa.vinylmusicplayer.model.Playlist;
import com.poupa.vinylmusicplayer.model.Song;
import com.poupa.vinylmusicplayer.provider.SongPlayCountStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class MyTopTracksPlaylist extends AbsSmartPlaylist {

    public MyTopTracksPlaylist(@NonNull Context context) {
        super(context.getString(R.string.my_top_tracks), R.drawable.ic_trending_up_white_24dp);
    }

    @NonNull
    @Override
    public ArrayList<Song> getSongs(@NonNull Context context) {
        return TopAndRecentlyPlayedTracksLoader.getTopTracks(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        SongPlayCountStore.getInstance(context).clear();
        super.clear(context);
    }

    @Override
    public boolean canImport() {return true;}

    @Override
    public void importPlaylist(@NonNull Context context, @NonNull Playlist playlist) {
        //SongPlayCountStore.getInstance(context).clear();
        List<Song> songs = playlist.getSongs(context);
        List<Long> songIds = new ArrayList<>(songs.size());
        for (Song song : songs) {
            songIds.add(song.id);
            Log.i(MyTopTracksPlaylist.class.getName(), song.title);
        }

        SongPlayCountStore.getInstance(context).addSongIds(songIds);
        super.importPlaylist(context, playlist);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected MyTopTracksPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<MyTopTracksPlaylist> CREATOR = new Creator<MyTopTracksPlaylist>() {
        public MyTopTracksPlaylist createFromParcel(Parcel source) {
            return new MyTopTracksPlaylist(source);
        }

        public MyTopTracksPlaylist[] newArray(int size) {
            return new MyTopTracksPlaylist[size];
        }
    };
}
