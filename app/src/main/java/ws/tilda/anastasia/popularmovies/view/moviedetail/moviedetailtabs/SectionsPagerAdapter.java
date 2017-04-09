package ws.tilda.anastasia.popularmovies.view.moviedetail.moviedetailtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;
import ws.tilda.anastasia.popularmovies.view.moviedetail.moviedetails.MovieDetailFragment;
import ws.tilda.anastasia.popularmovies.view.moviedetail.reviews.MovieReviewFragment;
import ws.tilda.anastasia.popularmovies.view.moviedetail.trailers.MovieTrailerFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public static final int TABS_QUANTITY = 3;
    private Movie mMovie;

    SectionsPagerAdapter(FragmentManager fm, Movie movie) {
        super(fm);
        mMovie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieDetailFragment.newInstance(mMovie);
            case 1:
                return MovieTrailerFragment.newInstance(mMovie.getId());
            case 2:
                return MovieReviewFragment.newInstance(mMovie.getId());
        }
        return MovieDetailFragment.newInstance(mMovie);
    }

    @Override
    public int getCount() {
        return TABS_QUANTITY;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "OVERVIEW";
            case 1:
                return "TRAILERS";
            case 2:
                return "REVIEWS";
        }
        return null;
    }
}
