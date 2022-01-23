public class Ball extends MovableObject implements Harmful{
    private boolean isBeingHeld;

    public Ball(Point2D loc) {
        super(0, 0, loc);
        isBeingHeld = false;
    }

    // The get/set methods
    public boolean isBeingHeld() {
        return isBeingHeld;
    }
    public void setIsBeingHeld(boolean newHoldStatus) {
        isBeingHeld = newHoldStatus;
    }

    @Override
    public String toString() {
        return "Ball" + super.toString();
    }

    public void draw(){
        System.out.println("Ball is at " + getLocation() + " facing "+ getDirection() +
                " degrees and moving at " + getSpeed() + " pixels per second");
    }

    @Override
    public void update() {
        if(speed > 0){
        moveForward();
        speed--;
        }
        draw();
    }

    public int getDamageAmount(){
        return -200;
    }
}