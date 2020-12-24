package com.naman14.timber.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.naman14.timber.R;
import com.naman14.timber.dataloaders.LastAddedLoader;
import com.naman14.timber.dataloaders.PlaylistSongLoader;
import com.naman14.timber.dataloaders.SongLoader;
import com.naman14.timber.dataloaders.TopTracksLoader;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.Constants;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.utils.TimberUtils;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import com.naman14.timber.models.Playlist;
import android.widget.ArrayAdapter;

/**
 * Created by naman on 31/10/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    ArrayList<Playlist> playlists;
    public  PlaylistAdapter(@NonNull Context context, ArrayList<Playlist>object){
        this.context = context;
        this.playlists=object;
    }


    int[] foregroundColors = {R.color.pink_transparent, R.color.green_transparent, R.color.blue_transparent, R.color.red_transparent, R.color.purple_transparent};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_playlist,viewGroup,false);
             return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.title.setText(playlist.getPlaylistName());
        holder.id.setText(String.valueOf(playlist.getListId()));
    }




    public int getItemCount() {
        return (null != playlists ? playlists.size() : 0);
    }

    public void updateDataSet(List<Playlist> arraylist) {
        this.playlists.clear();
        this.playlists.addAll(arraylist);
        notifyDataSetChanged();
    }


     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView id;
        protected TextView title;
         protected ImageView albumArt;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.album_title);
            this.albumArt = (ImageView) view.findViewById(R.id.album_art);
            this.id = view.findViewById(R.id.playlistID);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            NavigationUtils.navigateToPlaylistDetail((Activity) context,
                    getPlaylistType(getAdapterPosition()), title.toString(),
                    foregroundColors[0], playlists.get(getAdapterPosition()).listId,
                    null);

        }

    }
    private String getPlaylistType(int position) {
         return Constants.NAVIGATE_PLAYLIST_USERCREATED;
    }
}



