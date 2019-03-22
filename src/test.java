
import si.trina.moka7.live.PLC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourceforge.snap7.moka7.S7;
import com.sourceforge.snap7.moka7.S7Client;



public class test extends Thread{
	
	 static Logger logger;
	 private static S7Client client;
	 private static int result;
	
	
	public static void main(String[] args) throws Exception
	{
		
		client = new S7Client();
		result = client.ConnectTo("192.168.90.125", 0,2);
		
		if(result == 0)
		{
			System.out.println("Connected");
		}
		else
		{
			System.out.println("Something wrong in connection");
		}
		
		byte[] db206buffer = new byte[14];
      //  result = client.DBRead(13, 0, 2, db206buffer);
		result = client.ReadArea(S7.S7AreaDB, 205, 10, 4, db206buffer);


		System.out.println("vlaue:" +  S7.GetWordAt(db206buffer, 2));
		
		
		 /// WRITE MODULE ///
		byte[] dataWrite = new byte[4];
		S7.SetWordAt(dataWrite, 2, 2300);
		
	result = client.WriteArea(S7.S7AreaDB, 205, 10, 4, dataWrite);

	result = client.ReadArea(S7.S7AreaDB, 205, 10, 4, db206buffer);
		System.out.println("vlau12e:" +  S7.GetWordAt(db206buffer, 2));

		
		//sprawdzenie jeszcze raz dla innego parametru 205
		result = client.ReadArea(S7.S7AreaDB, 206, 4, 4, db206buffer);
		System.out.println("vlaue:" +  S7.GetWordAt(db206buffer, 0));

		
		
		client.Disconnect();


	}
}