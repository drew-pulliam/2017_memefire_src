package utility;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoyButton extends Button{
	private JoyDir joyDir;
	private Joystick stick;
	private int axis;
		
	public enum JoyDir
	{ 
		DOWN,UP;
	}
		
	/**
	 * Uses Joystick as  a button input.
	 * @param joystick
	 * @param direction
	 * @param axis
	 */
	public JoyButton(Joystick joystick, JoyDir direction, int axis)
	{
		joyDir = direction;
		stick = joystick;
		this.axis = axis;
	}
	@Override
	public boolean get() 
	{
		switch(joyDir)
		{
			case UP:
				if(stick.getRawAxis(axis) <=-.5)
				{
					return true;
				}
				break;
			case DOWN:
				if(stick.getRawAxis(axis) >= .5)
				{
					return true;
				}
				break;
		}
		return false;
	}
}
