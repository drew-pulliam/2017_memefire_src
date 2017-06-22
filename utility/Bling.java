package utility;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Bling {
	private static I2C i2c;
	public static void initialize() {
		i2c = new I2C(Port.kOnboard,4);
	}
	public static void sendData(byte value){
		 byte[] toSend = new byte[1];
    			toSend[0] = value;
    		i2c.transaction(toSend, 1, null, 0);
    		System.out.println(toSend[0]);
    	}
	}
	

