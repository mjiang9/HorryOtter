package kinesthesis;

import java.util.logging.Logger;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

public class LeapListener extends Listener {

	private LeapController controller;

	private boolean keyTap=  false;
  public boolean getKeyTap() { return keyTap; }

	private boolean swipe = false;
  public boolean getSwipe() { return swipe; }

  public Vector getAngle() { return new Vector(pitch, yaw, roll); }

	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;

	public float getRoll() {
		return roll;
	}

	public float getYaw() {
		return yaw;
	}
	public float getPitch() {
		return pitch;
	}

	public LeapListener(LeapController c) {
		this.controller = c;
	}

  public void onFrame(Controller controller) {
  	Frame frame = controller.frame();
    if (!frame.hands().isEmpty()) {
    		Hand hand = frame.hands().get(0);
    		if (hand.isValid() && (hand.fingers().count() == 1))  {
    			pitch = hand.direction().pitch();
					yaw = hand.direction().yaw();
					roll = hand.palmNormal().roll();
    	}
    }
    keyTap = false;
	swipe = false;
    for(Gesture gesture : frame.gestures())
    {
        switch (gesture.type()) {
            case TYPE_KEY_TAP:
                //Handle key tap gestures
            	keyTap = true;
                break;
            case TYPE_SWIPE:
                //Handle swipe gestures
            	SwipeGesture swipeG = new SwipeGesture(gesture);
            	Vector swipeDirection = swipeG.direction();
            	if (Math.abs(swipeDirection.getX()) > Math.abs(swipeDirection.getY())) {
                	swipe = true;
                	break;
            	}
            default:
                //Handle unrecognized gestures
                break;
        }
    }

  }
}
