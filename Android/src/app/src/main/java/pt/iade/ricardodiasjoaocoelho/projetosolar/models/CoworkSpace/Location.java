package pt.iade.ricardodiasjoaocoelho.projetosolar.models.CoworkSpace;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Location implements Parcelable {

    private final int  id;
    private final String name;
    private final String address;
    private final double occupationRate;

    public String getName() {
        return name;
    }

    public double getOccupanctRate() {
        return occupationRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(occupationRate);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(@NonNull Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private Location(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        occupationRate = in.readDouble();
    }

    public int getId() {
        return id;
    }
}
