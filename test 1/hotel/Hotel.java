package hotel;

public class Hotel implements Comparable<Hotel> {
    private String cityName;
    private String name;
    private int stars;

    public Hotel(String recordBookNumber, String name, int groupNumber) {
        this.cityName = recordBookNumber;
        this.name = name;
        this.stars = groupNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return cityName + ";" + name + ";" + stars;
    }

    @Override
    public int compareTo(Hotel other) {
        int sityCompare = this.cityName.compareTo(other.cityName);
        if (sityCompare == 0)
        {
            return -Integer.compare(this.stars, other.stars);
        }
        return sityCompare;
    }
}
