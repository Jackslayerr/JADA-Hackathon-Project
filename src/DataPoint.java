public class DataPoint
{
    public final double x;
    public final double y;
    public final boolean isExtrapolated;

    public DataPoint(double x, double y, boolean isExtrapolated)
    {
        this.x = x;
        this.y = y;
        this.isExtrapolated = isExtrapolated;
    }
}
