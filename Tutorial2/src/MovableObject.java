public abstract class MovableObject extends GameObject{
    protected int direction;
    protected int speed;

    public MovableObject(int d, int s, Point2D loc) {
        super(loc);
    }
    public int getDirection() {
        return direction;
    }
    public int getSpeed() {
        return speed;
    }
    public void setDirection(int newDirection) {
        direction = newDirection;
    }
    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }
    public void update() {
        moveForward();
        draw();
    }
    public void moveForward() {
        if (speed > 0){
            location = location.add((int)
                    (Math.cos(Math.toRadians(direction)) * speed), (int)
                    (Math.sin(Math.toRadians(direction)) * speed));
        }
    }
    public String toString() {
        return super.toString() + " facing " + direction + " degrees and moving at " + getSpeed() + " pixels per second";
    }
    public abstract void draw();
}
