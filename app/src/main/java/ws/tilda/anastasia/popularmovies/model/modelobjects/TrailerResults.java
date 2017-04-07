
package ws.tilda.anastasia.popularmovies.model.modelobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerResults implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(this.trailers);
    }

    public TrailerResults() {
    }

    protected TrailerResults(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.trailers = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Parcelable.Creator<TrailerResults> CREATOR = new Parcelable.Creator<TrailerResults>() {
        @Override
        public TrailerResults createFromParcel(Parcel source) {
            return new TrailerResults(source);
        }

        @Override
        public TrailerResults[] newArray(int size) {
            return new TrailerResults[size];
        }
    };
}
