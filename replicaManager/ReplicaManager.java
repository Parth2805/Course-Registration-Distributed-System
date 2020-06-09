package replicaManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class ReplicaManager
{
	static int swcount=0;
	DatagramSocket ds=null;
	DatagramPacket dp=null;
	public void multicast(String msg,InetAddress ip)
	{
		try
		{	
			System.out.print(msg);
			ds=new DatagramSocket();
			//sending msg to replicas on 1700 port number
			dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1700);
			ds.send(dp);
		}
		catch(Exception e)
		{
			System.out.println("Sequencer Multicast Exception: "+e.getMessage());
		}
		finally
		{
			if(ds!=null)
			{
				ds.close();
			}
		}	
	}
	
	public static void main(String[] args)
	{
		  ReplicaManager rm=new ReplicaManager();
		  Runnable task =()->
		  {
				DatagramSocket ds=null;
				DatagramSocket ds1=null;
				while(true)
				{
					try 
					{
						System.out.println("Receiving Sequencer Request");
					//receiving sequencer msg on 1600 port number
					ds=new DatagramSocket(1600);
					byte []buf=new byte[1000];
					String msg="";
					DatagramPacket dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					msg=new String(dp.getData());
					System.out.println("Msg from Seq: "+msg);
					StringTokenizer st=new StringTokenizer(msg,",");
					int i=Integer.parseInt(st.nextToken());
					System.out.println("IIIII"+i);
					String id=st.nextToken();
					
					
					char choice=id.charAt(0);
					switch(choice)
					{
					case 'C':
						InetAddress ip=InetAddress.getByName("230.0.0.1");
						rm.multicast(msg, ip);
						break;
					case 'S':
						ip=InetAddress.getByName("230.0.0.2");
						rm.multicast(msg, ip);
						break;
					case 'I':
						ip=InetAddress.getByName("230.0.0.3");
						rm.multicast(msg, ip);
						break;
					default:
						break;
					}
				}
				catch(Exception e)
				{
					System.out.println("Main: "+e.getMessage());
				}
				finally
				{
					if(ds!=null)
					{
						ds.close();
					}
				}
			}
		};
		Thread t2=new Thread(task);
		t2.start(); 
		
		Runnable task3 =()->
		  {
				DatagramSocket ds=null;
				while(true)
				{
					try 
					{
					System.out.println("Receiving FE SW Bug Message");
					ds=new DatagramSocket(3000);
					byte []buf=new byte[1000];
					String msg="";
					DatagramPacket dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					msg=new String(dp.getData());
					rm.swcount++;
					if(rm.swcount==3)
					{
						InetAddress ip=InetAddress.getByName("localhost");
						ds.send(new DatagramPacket("Bug".getBytes(),"Bug".length(),ip,2000));
					}
					
				}
				catch(Exception e)
				{
					System.out.println("Main: "+e.getMessage());
				}
				finally
				{
					if(ds!=null)
					{
						ds.close();
					}
				}
			}
		};
		Thread t3=new Thread(task3);
		t3.start(); 
	}

}
