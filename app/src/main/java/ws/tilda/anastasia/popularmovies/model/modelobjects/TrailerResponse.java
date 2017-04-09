package ws.tilda.anastasia.popularmovies.model.modelobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerResponse implements Parcelable {

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

    public TrailerResponse() {
    }

    protected TrailerResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.trailers = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Parcelable.Creator<TrailerResponse> CREATOR = new Parcelable.Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel source) {
            return new TrailerResponse(source);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };
}
