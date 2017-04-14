package ws.tilda.anastasia.popularmovies.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ws.tilda.anastasia.popularmovies.model.data.FavouriteMoviesContract.*;


public class FavouriteMoviesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fvouriteMovies.db";
    public static final int DATABASE_VERSION = 1;

    public FavouriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVOURITEMOVIES_TABLE = "CREATE TABLE " +
                FavouriteMoviesEntry.TABLE_NAME + " (" +
                FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_FAVOURITEMOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
