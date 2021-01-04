package com.naman14.timber.dataloaders;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.naman14.timber.models.Tune;
import com.naman14.timber.utils.PreferencesUtility;

import java.util.ArrayList;
import java.util.List;
public class TuneLoader {
    private static final long[] sEmptyList = new long[0];
    public static ArrayList<Tune> getTunesForCursor(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                long id = cursor.getLong(0);
                String title = cursor.getString(1);

                arrayList.add(new Tune(id, title,""));
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;
    }
    public static Tune getTuneForCursor(Cursor cursor) {
        Tune tune = new Tune();
        if ((cursor != null) && (cursor.moveToFirst())) {
            long id = cursor.getLong(0);
            String title = cursor.getString(1);
            tune = new Tune(id, title, "");
        }
        if (cursor != null)
            cursor.close();
        return tune;
    }

    public static final long[] getTuneListForCursor(Cursor cursor) {
        if (cursor == null) {
            return sEmptyList;
        }
        final int len = cursor.getCount();
        final long[] list = new long[len];
        cursor.moveToFirst();
        int columnIndex = -1;
        try {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID);
        } catch (final IllegalArgumentException notaplaylist) {
            columnIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID);
        }
        for (int i = 0; i < len; i++) {
            list[i] = cursor.getLong(columnIndex);
            cursor.moveToNext();
        }
        cursor.close();
        cursor = null;
        return list;
    }

    public static Tune getTuneFromPath(String songPath, Context context) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA;
        String[] selectionArgs = {songPath};
        String[] projection = new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"};
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = cr.query(uri, projection, selection + "=?", selectionArgs, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            Tune tune = getTuneForCursor(cursor);
            cursor.close();
            return tune;
        }
        else return new Tune();
    }

    public static ArrayList<Tune> getAllTunes(Context context) {
        return getTunesForCursor(makeTuneCursor(context, null, null));
    }
    public static long[] getSongListInFolder(Context context, String path) {
        String[] whereArgs = new String[]{path + "%"};
        return getTuneListForCursor(makeTuneCursor(context, MediaStore.Audio.Media.DATA + " LIKE ?", whereArgs, null));
    }
    public static Tune getTuneForID(Context context, long id) {
        return getTuneForCursor(makeTuneCursor(context, "_id=" + String.valueOf(id), null));
    }

    public static List<Tune> searchTunes(Context context, String searchString, int limit) {
        ArrayList<Tune> result = getTunesForCursor(makeTuneCursor(context, "title LIKE ?", new String[]{searchString + "%"}));
        if (result.size() < limit) {
            result.addAll(getTunesForCursor(makeTuneCursor(context, "title LIKE ?", new String[]{"%_" + searchString + "%"})));
        }
        return result.size() < limit ? result : result.subList(0, limit);
    }


    public static Cursor makeTuneCursor(Context context, String selection, String[] paramArrayOfString) {
        final String songSortOrder = PreferencesUtility.getInstance(context).getSongSortOrder();
        return makeTuneCursor(context, selection, paramArrayOfString, songSortOrder);
    }

    private static Cursor makeTuneCursor(Context context, String selection, String[] paramArrayOfString, String sortOrder) {
        String selectionStatement = "is_music=1 AND title != ''";

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = selectionStatement + " AND " + selection;
        }
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"}, selectionStatement, paramArrayOfString, sortOrder);

    }

    public static Tune TuneFromFile(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(filePath);
        return new Tune(
                -1,
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                "");
    }
}
