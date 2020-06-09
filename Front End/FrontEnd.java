package HelloApll;

import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.SortingFocusTraversalPolicy;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

class HelloImpl extends HelloPOA{
	
	private ORB orb;
	HashMap<String,String> reply=null;
    static int rcount=0;
    int maxdelay=5000;
    HelloImpl(){
    	
    	reply= new HashMap<String,String>();
    	reply.put("AC1", "Course Added Successfully");
    	reply.put("AC2", "Course Already Exists");
    	reply.put("AC3", "UnAuthorized Advisor");
    	reply.put("RC1", "Course Removed Successfully");
    	reply.put("RC2", "Course Does not Exist");
    	reply.put("RC3", "UnAuthorized Advisor");
    	reply.put("EC1", "Course Enrolled Successfully");
    	reply.put("EC2", "Course Not Available in this Semester");
    	reply.put("EC3", "You are Already Enrolled in Max Number of Subjects for this Semester");
    	reply.put("EC4", "Course is Full");
    	reply.put("EC5", "You are already Enrolled in this Course");
    	reply.put("EC7", "You are Already Enrolled in Max Number of Subjects from other Departments");
    	reply.put("DC1", "Course Dropped Successfully");
    	reply.put("DC2", "You are not Enrolled in this course for this Semester");
    	reply.put("SS1", "Swapping Successfully");
    	
    }
    
    String call_other(String msg){
        
    	
    	
    	int flag=0;
    	DatagramSocket ds1=null;
		DatagramSocket ds2=null;
		DatagramSocket ds3=null;
		DatagramSocket ds=null;
		
		 DatagramPacket dp1=null;
		 DatagramPacket dp2=null;
		 DatagramPacket dp3=null;
		 String rajan="",parth="",kishan="";
		 
    			try {
    			
    				
    			
    			long v1,v2,v3;
    			final long startTime = System.currentTimeMillis();	   			
    			ds1= new DatagramSocket(7001);//parth
    			ds2=new DatagramSocket(7002);//rajan
    			ds3=new DatagramSocket(7003);//kishan
    			ds3.setSoTimeout(maxdelay);

    			ds = new DatagramSocket();
                byte rpurpose[] = msg.getBytes();   
                InetAddress addr;
				
				addr = InetAddress.getByName("132.205.93.51");
				
				//sequencer send
                DatagramPacket dp = new DatagramPacket(rpurpose,rpurpose.length,addr,7005);
                ds.send(dp);
                
                byte received1[] = new byte[1000];                
                dp1= new DatagramPacket(received1,received1.length);                
                ds1.receive(dp1); 
                final long stoptime1= System.currentTimeMillis();
                v1=stoptime1-startTime;
                System.out.println(stoptime1-startTime);
        //        System.out.println(new String(dp1.getData()));
                
                byte received2[] = new byte[1000];
                dp2= new DatagramPacket(received2,received2.length);             
                ds2.receive(dp2);
                final long stoptime2= System.currentTimeMillis();
                v2=stoptime2-startTime;
                System.out.println(stoptime2-startTime);
      //          System.out.println(new String(dp2.getData()));
                
                byte received3[] = new byte[1000];
                dp3= new DatagramPacket(received3,received3.length);             
                ds3.receive(dp3);
                final long stoptime3= System.currentTimeMillis();
                v3=stoptime3-startTime;
                System.out.println(stoptime3-startTime);
       //         System.out.println(new String(dp3.getData()));
                
                
               long min=v1;
               if(min<v2) {
            	   
            	   min=v2;
               }
               else if(min<v3) {
            	   
            	   
            	   min=v3;   
               }
               
               maxdelay=(int) min+10;
                
               System.out.println("Max Delay:"+maxdelay);
                
 
                System.out.println("++++++++++++++++++++");
                parth=new String(dp1.getData()).trim();
       		 	System.out.println(parth);
       		 
       		 	rajan= new String (dp2.getData()).trim();
       		 	System.out.println(rajan);
       		 
       		 	kishan= new String(dp3.getData()).trim();
       		 	System.out.println(kishan);
       		 
       		 	
       		 	if(!rajan.equals(kishan)&&kishan.equals(parth)) {//Majority
       		
       		 		
       		 	
           		 	DatagramPacket dpwarn= new DatagramPacket("1".getBytes(),"1".length(),dp2.getAddress(),3000);//sending msg to RM
    	 			ds.send(dpwarn);
       		 		if(reply.containsKey(parth)) {
       		 		
       		 			return reply.get(parth);
       		 		
       		 		}
       		 		else {
       		 			
       		 			return parth;
       		 		}
       		 		
       		 	}	
       		 /*		System.out.println("Rajan Bug");
       		 		if(bug.equals("1")) {
                	
       		 			if(rcount<3) {
                		 
       		 				System.out.println("bug:"+bug+"Rcount:"+rcount);
       		 				rcount++;
       		 			}
       		 			else {
       		 				System.out.println("inside else");
       		 				DatagramPacket dpwarn= new DatagramPacket("1".getBytes(),"1".length(),dp2.getAddress(),2000);
       		 				ds.send(dpwarn);
       		 				rcount=0;
                	
       		 			} 
       		 			
       		 			return reply.get(parth);
       		 		}*/
       		 	
       		 	
       		 	
       		 	
 //      		 	received = kishan.trim()+parth.trim()+rajan.trim();
 //      		 	System.out.println(received);
//       		 	ds.close();
//   				ds1.close();
//   				ds2.close();
//   				ds3.close();
//                
    			}catch(SocketTimeoutException e) {
    				System.out.println("Timed Out");
    				flag=1;
    				
    				
    			}catch(Exception e) {
    				
    				System.out.println(e.getMessage());
    			}finally {
    				
    				if(flag==1) {
    					
    					try {
    						
    						InetAddress addr = InetAddress.getByName("132.205.93.50");
        					DatagramPacket dp = new DatagramPacket("Crash".getBytes(),"Crash".length(),addr,2000);
    						System.out.println(flag);
							ds.send(dp);
							
							parth=new String(dp1.getData()).trim();
							ds.close();
							ds1.close();
							ds2.close();
							ds3.close();
							flag=0;
							return reply.get(parth);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					
    					
    				//	return reply.get(parth);	
    				}
    				ds.close();
					ds1.close();
					ds2.close();
					ds3.close();
    				
    				
    			}
    		 
    			if(reply.containsKey(parth)) {
       		 		
   		 			return reply.get(parth);
   		 		
   		 		}
   		 		else {
   		 			
   		 			return parth;
   		 		}       
				
                 
    	
   }
   
 
	
	
	public void setORB(ORB orb_val) {
		
		orb = orb_val;
		
	}
	@Override
	public String sayhello() {
		
		return "Hello";
	}




	@Override
	synchronized public String enrolcourse(String studentid, String courseid, String sem) {
		
		System.out.println("Inside enroll");
		String msg=studentid+",Enrol"+","+courseid+","+sem;
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	public String classSchedule(String studentid) {
		
		System.out.println("Inside classSchedule");
		String msg=studentid+",Get";
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	public String removeCourse(String studentid, String courseid, String sem) {
		
		System.out.println("Inside remove course");
		String msg=studentid+",Drop"+","+courseid+","+sem;
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	public String swapClasses(String studentid, String courseid, String sem, String newcourseid) {
		
		System.out.println("Inside Swap");
		String msg=studentid+",Swap"+","+courseid+","+sem+","+newcourseid;
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	public String addCourse(String advisorid,String courseid, String sem, int capacity) {
		
		System.out.println("Inside AddCourse");
		String msg=advisorid+",Add"+","+courseid+","+sem+","+capacity;
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	synchronized public String removeCourseA(String advisorid,String courseid, String sem) {

		System.out.println("Inside Remove Course");
		String msg=advisorid+",Remove"+","+courseid+","+sem;
		String rmsg=call_other(msg);
		return rmsg;
	}




	@Override
	public String listCourseAvailability(String advisorid,String sem) {
		
		System.out.println("Inside Remove Course");
		String msg=advisorid+",List,"+sem;
		String rmsg=call_other(msg);
		return rmsg;
	}




}	


public class FrontEnd {

	public static void main(String[] args) {
		
		  try {
			  
		  
		// create and initialize the ORB
	      ORB orb = ORB.init(args, null);
	      
	      // get reference to rootpoa & activate the POAManager
	      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	      rootpoa.the_POAManager().activate();
	     
	      // create servant and register it with the ORB
	      HelloImpl helloImpl = new HelloImpl();
	      helloImpl.setORB(orb); 
	      
	      // get object reference from the servant
	      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
	      
	      Hello href = HelloHelper.narrow(ref);
	        
	      // get the root naming context
	      // NameService invokes the name service
	     
	      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	      // Use NamingContextExt which is part of the Interoperable
	      // Naming Service (INS) specification.
	      
	      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	      
	      // bind the Object Reference in Naming
	      String name = "Hello1";
	      NameComponent path[] = ncRef.to_name( name );
	      
	      ncRef.rebind(path, href);

	      System.out.println("Front end ready and waiting ...");
	/*      HelloImpl imp = new HelloImpl();
	      imp.intialize_main();*/
	      // wait for invocations from clients
	      orb.run();
		  }
		  catch(Exception e) {
			  System.out.println(e);
			  
		  }

	}

}
