public class Trap extends StationaryObject implements Harmful{

    public Trap(Point2D loc) {
        super(loc);
    }

    public String toString() {
        return "Trap" + super.toString();
    }
    public int getDamageAmount(){
        return -50;
    }
}