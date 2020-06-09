package Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;




public class Inse{

HashMap<String,HashMap<String,ArrayList<String>>> st_en= new HashMap<>();
    
	static int unique_id=0;
    //Student course count
    HashMap<String,HashMap<String,Integer>> cm_cnt= new HashMap<>();
    HashMap<String,Integer> ct1_cnt= new HashMap<>();
    HashMap<String,Integer> ct2_cnt= new HashMap<>();
    HashMap<String,Integer> ct3_cnt= new HashMap<>();
//    HashMap<String,HashMap<String,Integer>> co_cnt= new HashMap<>();
    HashMap<String,Integer> cot1_cnt= new HashMap<>();
    HashMap<String,Integer> cot2_cnt= new HashMap<>();
    HashMap<String,Integer> cot3_cnt= new HashMap<>();
    
    
    
    //Array list for each course individual
    ArrayList<String> a1= new ArrayList<>();
    ArrayList<String> a2 = new ArrayList<>();
    ArrayList<String> a3= new ArrayList<>();
    ArrayList<String> b1= new ArrayList<>();
    ArrayList<String> b2 = new ArrayList<>();
    ArrayList<String> b3= new ArrayList<>();
    ArrayList<String> c1= new ArrayList<>();
    ArrayList<String> c2 = new ArrayList<>();
    ArrayList<String> c3= new ArrayList<>();
    

    //Course 
    //sem-courseid-capacity
    HashMap<String,HashMap<String,Integer>> c_av= new HashMap<>();
    HashMap<String,Integer> c_avw= new HashMap<>();
    HashMap<String,Integer> c_avs= new HashMap<>();
    HashMap<String,Integer> c_avf= new HashMap<>();
    HashMap<String,HashMap<String,Integer>> c_sencnt= new HashMap<>();
    HashMap<String,Integer> c_sencntw= new HashMap<>();
    HashMap<String,Integer> c_sencnts= new HashMap<>();
    HashMap<String,Integer> c_sencntf= new HashMap<>();
  //  HashMap<String,Integer> c_sencnt=new HashMap<>();
    
    HashMap<String,ArrayList<String>> cfall = new HashMap<>();
    HashMap<String,ArrayList<String>> cwinter = new HashMap<>();
    HashMap<String,ArrayList<String>> csummer = new HashMap<>();
    HashMap<String,ArrayList<String>> sem1 = new HashMap<>();
    HashMap<String,ArrayList<String>> sem2 = new HashMap<>();
    HashMap<String,ArrayList<String>> sem3 = new HashMap<>();
    
    HashMap<String,Integer> count = new HashMap<>();
    
    String Students[]= {"INSES6001","INSES6002","INSES6003"};
    
    String semester[]={"Fall","Winter","Summer"};
    int port;
    
    
    public void intialize_main(){

        /*a1.add("");
        b1.add("");
        c1.add("");
        a2.add("");
        b2.add("");
        c2.add("");
        a3.add("");
        b3.add("");
        c3.add("");*/
        sem1.put("Fall", a1);
        sem1.put("Winter",a2);
        sem1.put("Summer",a3);
        sem2.put("Fall",b1);
        sem2.put("Winter",b2);
        sem2.put("Summer",b3);
        sem3.put("Fall", c1);
        sem3.put("Winter",c2);
        sem3.put("Summer",c3); 
        st_en.put("INSES6001",sem1);
        st_en.put("INSES6002",sem2);
        st_en.put("INSES6003",sem3);
        
        c_avf.put("INSE8001", 3);
        c_avf.put("INSE8002",3);
        c_avf.put("INSE8003",3);
        c_av.put("Fall", c_avf);
        c_avw.put("INSE8004",3);
        c_avw.put("INSE8005",3);
        c_avw.put("INSE8001",3);
        c_av.put("Winter", c_avw);
        c_avs.put("INSE8006",3);
        c_avs.put("INSE8007",3);
        c_avs.put("INSE8002",3);
        c_av.put("Summer", c_avs);
        
       
        c_sencntf.put("INSE8001",0);
        c_sencntf.put("INSE8002",0);
        c_sencntf.put("INSE8003",0);
        c_sencnt.put("Fall", c_sencntf);
        c_sencntw.put("INSE8004",0);
        c_sencntw.put("INSE8005",0);
        c_sencntw.put("INSE8001",0);
        c_sencnt.put("Winter", c_sencntw);
        c_sencnts.put("INSE8006",0);
        c_sencnts.put("INSE8007",0);
        c_sencnts.put("INSE8002",0);
        c_sencnt.put("Summer", c_sencnts);
        
        ct1_cnt.put("Fall",0);
        ct1_cnt.put("Winter",0);
        ct1_cnt.put("Summer",0);
        cm_cnt.put("INSES6001",ct1_cnt);
        ct2_cnt.put("Fall",0);
        ct2_cnt.put("Winter",0);
        ct2_cnt.put("Summer",0);
        cm_cnt.put("INSES6002",ct2_cnt);
        ct3_cnt.put("Fall",0);
        ct3_cnt.put("Winter",0);
        ct3_cnt.put("Summer",0);
        cm_cnt.put("INSES6003",ct3_cnt);
        
   /*     cot1_cnt.put("Fall",0);
        cot1_cnt.put("Winter",0);
        cot1_cnt.put("Summer",0);
        co_cnt.put("INSES6001",cot1_cnt);
        cot2_cnt.put("Fall",0);
        cot2_cnt.put("Winter",0);
        cot2_cnt.put("Summer",0);
        co_cnt.put("INSES6002",cot2_cnt);
        cot3_cnt.put("Fall",0);
        cot3_cnt.put("Winter",0);
        cot3_cnt.put("Summer",0);
        co_cnt.put("INSES6003",cot3_cnt);*/
        
        count.put("INSES6001", 0);
        count.put("INSES6002",0);
        count.put("INSES6003", 0);
        
    }
    
    
    void receive(){
        
        
    	try {
            
            DatagramSocket ds= new DatagramSocket(9003);
        while(true){
            
            byte rpurpose[] = new byte[1000];
            byte rcourseid[] = new byte[1000];
            byte rsem[] = new byte[1000];
            
            String msg="";
            DatagramPacket dp = new DatagramPacket(rpurpose,rpurpose.length);
            ds.receive(dp);
            String purpose = new String(dp.getData());
            System.out.println("Purpose:-"+purpose);
            dp = new DatagramPacket(rcourseid,rcourseid.length);
            ds.receive(dp);
            String courseid = new String(dp.getData());
            System.out.println("Course:-"+courseid);
            dp = new DatagramPacket(rsem,rsem.length);
            ds.receive(dp);
            String sem = new String(dp.getData());
            System.out.println("Sem:-"+sem);
            
            if("Enroll".equals(purpose.trim().toString())){
                
                msg=enroll(courseid.trim().toString(),sem.trim().toString());
                
                
            }
                
           if("Remove".equals(purpose.trim().toString())){
               
               msg=remove(courseid.trim().toString(),sem.trim().toString());        
            }
           
           if("Notify".equals(purpose.trim().toString())){
               
               msg=notify(courseid.trim().toString(),sem.trim().toString());
               
           }
           if("Getcourses".equals(purpose.trim().toString())){
               
               msg=getCourses(sem.trim().toString());
            
            
           }
            byte[] msgs = new byte[1000];
            msgs=msg.getBytes();
            DatagramPacket dps = new DatagramPacket(msgs,msgs.length,dp.getAddress(),dp.getPort());                      
            ds.send(dps);
                
            
            System.out.println("Not in if");
        
        }
        
            
              
    } catch (SocketException ex) {
 	   
 	   System.out.println(ex);
        
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
        
   }
   
    synchronized String enroll(String courseid,String sem){
       
       
       String msg;
      
       if(c_av.get(sem).containsKey(courseid)){
                           
                           if(c_sencnt.get(sem).get(courseid)==c_av.get(sem).get(courseid)){
                               
                              // msg="Course Capacity Full";
                        	   msg="EC4";
                           }
                           else{
                               
                               int update=c_sencnt.get(sem).get(courseid)+1;
                               c_sencnt.get(sem).put(courseid, update);
                               
                               msg="Enrolled";
                           }
                           
                       }
                       else{
                           
                          // msg="Course not available for the Semester";
                    	   msg="EC2";
                       }
       return msg;
   }
   
   synchronized String remove(String courseid,String sem){
       
       String msg="Removed";
       
       int update=c_sencnt.get(sem).get(courseid)-1;
       c_sencnt.get(sem).put(courseid,update);
       return msg;
   }
   
   synchronized String notify(String courseid,String sem){
       
       String msg="Notified";
       System.out.println("Inside notify");
       
      
       for(int i=0;i<3;i++){
           
           if(st_en.get(Students[i]).get(sem).contains(courseid)){
               
               st_en.get(Students[i]).get(sem).remove(courseid);
      
               int update=count.get(Students[i])-1;
               count.put(Students[i],update);
               int update2=cm_cnt.get(Students[i]).get(sem)-1;
               cm_cnt.get(Students[i]).put(sem,update2);
                   
               
               
               
           }
           
           
       }
       
       
       
       return msg;
               
       
   }
   synchronized String getCourses(String sem){
       
       String msg= "Course Enrolled"+c_sencnt.get(sem).toString()+"\n";
       String msg2="Course Capacity"+c_av.get(sem).toString();
       
       return msg+msg2;
       
   }
   

   String call_other(String purpose,String courseid,String sem,int port){
       String msg="";
       try {
           DatagramSocket asocket= new DatagramSocket();
           byte[] scourseid = new byte[1000];
           byte[] spurpose = new byte[1000];
           byte[] ssem = new byte[1000];
           InetAddress addr= InetAddress.getByName("localhost");
           
           
           scourseid=courseid.getBytes();
           spurpose=purpose.getBytes();
           ssem= sem.getBytes();
           DatagramPacket packet = new DatagramPacket(spurpose,spurpose.length,addr,port);
           asocket.send(packet);
           packet = new DatagramPacket(scourseid,scourseid.length,addr,port);
           asocket.send(packet);
           packet = new DatagramPacket(ssem,ssem.length,addr,port);
           asocket.send(packet);
           byte[] buff1= new byte[1000];
           DatagramPacket packet1= new DatagramPacket(buff1,buff1.length);
           asocket.receive(packet1);
           msg = new String(packet1.getData());
           msg=msg.trim().toString();
           System.out.println("Message recieved:"+msg);
           
           
       } catch (Exception ex) {
           System.out.println(ex);
       } 
       
      return  msg;
   } 

	
	
	public Inse() {
				
		intialize_main();
		Thread t = new Thread(()->{
            
	           receive();
	            
	            
	        });
	        t.start();
	}
	

	public synchronized String enrolcourse(String studentid, String courseid, String sem) {
		
		 String msg="";
		    /*    for(Map.Entry<String,HashMap<String,Integer>> pair:cm_cnt.entrySet())
		        {
		            HashMap<String,Integer> map=pair.getValue();
		            System.out.println(pair.getKey());
		            for(Map.Entry<String,Integer> pair1:map.entrySet())
		            {
		                System.out.println(pair1.getKey()+" ::"+pair1.getValue());
		            }
		        }*/
		        
		      System.out.println("Entered registration"+studentid+courseid+sem+" "+cm_cnt.get(studentid).get(sem));
		        System.out.println(studentid+courseid+sem);
		        if(st_en.get(studentid).get(sem).contains(courseid)){
		            
		            
		            
		       //     msg="Already Enrolled";
		        	msg="EC5";
		        }
		            
		        else
		        {
		            for(int i=0;i<3;i++){
		                
		                if(st_en.get(studentid).get(semester[i]).contains(courseid)){
		                    
		                    if(!(semester[i]==sem)){
		                     
		                           //return "Already Enrolled in different Semester";
		                    		return "EC5";
		                    }
		                    
		                }
		                
		                
		            }
		        if(cm_cnt.get(studentid).get(sem)==3){
		            
		          //  msg = "Already Enrolled in 3 subjects";
		            msg="EC3";
		        }
		        else
		        {    
		            if(courseid.charAt(0)=='C'|| courseid.charAt(0)=='S'){
		            
		                if(count.get(studentid)==2){
		                
		                   // msg = "Already enrolled in 2 elective subjects";
		                	msg="EC7";
		                }
		            
		                else{
		                    if(courseid.charAt(0)=='C'){
		                        
		                        port=9001;
		                        String purpose="Enroll";
		                        
		                        msg=call_other(purpose,courseid,sem,port);
		                        
		                        if(msg.equals("Enrolled")){
		                            
		                            st_en.get(studentid).get(sem).add(courseid);                  
		                            int update1=count.get(studentid)+1;
		                            count.put(studentid, update1);
		                            int update2=cm_cnt.get(studentid).get(sem)+1;
		                            cm_cnt.get(studentid).put(sem, update2);
		                            //return msg;
		                            return "EC1";
		                            
		                        }
		                        else{ 
		                            System.out.println("not Enrolled");
		                            return msg;
		                        }
		                    }
		                    else{
		                        if(courseid.charAt(0)=='S'){
		                        
		                        port=9002;
		                        String purpose="Enroll";
		                        
		                        msg=call_other(purpose,courseid,sem,port);
		                        
		                        if(msg.equals("Enrolled")){
		                            
		                            st_en.get(studentid).get(sem).add(courseid);                  
		                            int update1=count.get(studentid)+1;
		                            count.put(studentid, update1);
		                            System.out.println("Enrolled");
		                            int update2=cm_cnt.get(studentid).get(sem)+1;
		                            cm_cnt.get(studentid).put(sem, update2);
		                            //return msg;
		                            return "EC1";
		                            
		                        }
		                        else{ 
		                            System.out.println("not Enrolled");
		                            return msg;
		                        }
		                        
		                        
		                        }    
		                    }
		                    
		                    
		                
		               
		                }
		            }
		            else{
		            
		                if(c_av.get(sem).containsKey(courseid)){
		                
		                    if(c_sencnt.get(sem).get(courseid)==c_av.get(sem).get(courseid)){
		                    
		                        //msg = "Course Capacity is full";
		                        msg="EC4";
		                    
		                    }
		                    else{
		                
		                    if(st_en.get(studentid).get(sem).contains(courseid)){
		                    
		                        msg = "Already Enrolled..Cant enroll twice";
		                        msg="EC5";
		                    }
		                    
		                    else{
		                        System.out.println("Before:"+c_sencnt.get(courseid)+""+st_en.get(studentid).get(sem));
		                        int update= c_sencnt.get(sem).get(courseid)+1;
		                        c_sencnt.get(sem).put(courseid, update);
		                        st_en.get(studentid).get(sem).add(courseid);
		                    
		                        int update1=cm_cnt.get(studentid).get(sem)+1;
		                        cm_cnt.get(studentid).put(sem, update1);
		                    
		                        msg="EC1";
		                        System.out.println("After:"+c_sencnt.get(sem).get(courseid)+""+st_en.get(studentid).get(sem));
		                    
		                    }
		                    }
		                
		                }
		                else{
		                
		                //    msg = "Course Not Available for"+ sem;
		                	msg="EC2";
		                }
		            
		            
		            
		            }
		        }
		        }
		        return msg;
	
	}

	
    public synchronized String removeCourse(String studentid, String courseid, String sem){
        
        String msg="";
        if(cm_cnt.get(studentid).get(sem)==0){
            
            msg = "You are not enrolled in Anycourse for the given sem...Cart Already Empty";
            msg="DC2";
            
        }
        else{
            
            if(courseid.charAt(0)=='C'||courseid.charAt(0)=='S'){
                
                if(!(st_en.get(studentid).get(sem).contains(courseid))){
                
                 //   msg = "You havent enroll in this course yet";
                	msg="DC2";
                    return msg;
                }
                else
                if(courseid.charAt(0)=='C'){
                        
                        port=9001;
                        String purpose="Remove";
                        
                        msg=call_other(purpose,courseid,sem,port);
                        
                        if(msg.equals("Removed")){
                            
                            st_en.get(studentid).get(sem).remove(courseid);                  
                            int update1=count.get(studentid)-1;
                            count.put(studentid, update1);
                            int update2=cm_cnt.get(studentid).get(sem)-1;
                            cm_cnt.get(studentid).put(sem, update2);
                            
                         //   return msg;
                            return "DC1";
                            
                        }
                        else{ 
                            
                            return msg;
                        }
                    }
                    else{
                        if(courseid.charAt(0)=='S'){
                        
                        port=9002;
                        String purpose="Remove";
                        
                        msg=call_other(purpose,courseid,sem,port);
                        
                        if(msg.equals("Removed")){
                            
                            st_en.get(studentid).get(sem).remove(courseid);                  
                            int update1=count.get(studentid)-1;
                            count.put(studentid, update1);
                            int update2=cm_cnt.get(studentid).get(sem)-1;
                            cm_cnt.get(studentid).put(sem, update2);
                            //return msg;
                            return "DC1";
                            
                        }
                        else{ 
                           
                            return msg;
                        }
                        
                        
                        }    
                    }
                
                    
                    
                    //talk to INSE or inse
                
            }
            else{
            
                if(!(st_en.get(studentid).get(sem).contains(courseid))){
                
                   // msg = "You havent enroll in this course yet";
                    msg="DC2";
       
                }
                else{
                    
                    st_en.get(studentid).get(sem).remove(courseid);
                
                    int update=c_sencnt.get(sem).get(courseid)-1;
                    c_sencnt.get(sem).put(courseid,update);
                
                    int update1=cm_cnt.get(studentid).get(sem)-1;
                    cm_cnt.get(studentid).put(sem,update1);
                
                  //  msg="Removed";
                    msg="DC1";
                }
            
            
                
                
                
            }
            
        }    
        return msg;
        
       
    }
	
	
	public synchronized String swapClasses(String studentid,String courseid,String sem,String courseidnew) {
		
		
		System.out.println("Inside Swap");
		String msg2,msg3,msg="";
		msg2=removeCourse(studentid, courseid, sem);
		System.out.println(msg2);
		if(msg2.trim().toString().equals("DC1")) {
			
			System.out.println("Inside Inner Swap");
			msg3=enrolcourse(studentid, courseidnew, sem);
			System.out.println(msg3);
			if(msg3.trim().toString().equals("EC1")) {
				
				msg="Swap Successful";
			//	return msg;
				return "SS1";
				
			}
			else {
				enrolcourse(studentid, courseid, sem);
				return msg3;
			}
			
		}
		else {
			msg=msg2;
			
		}
			
			

		
		return msg;
	}
    
    

	
	public String classSchedule(String studentid) {
		System.out.println("INSE");
        String msg="Course	Semester\n"+st_en.get(studentid).get("Fall")
                +"\tFall"+"\n"+st_en.get(studentid).get("Winter")
                +"\tWinter"+"\n"+st_en.get(studentid).get("Summer")
                +"\tSummer";
        
        return msg;
	}
	

	
	public String addCourse(String courseid,String sem,int capacity){
    
		String msg;
    
    
    
		if(c_av.get(sem).containsKey(courseid)){
        
			//msg="Course Already Exists";
			msg = "AC2";
        
        
		}
		else{
        
			c_av.get(sem).put(courseid, capacity); 
			c_sencnt.get(sem).put(courseid, 0);
			//msg="Succesfully Added";
			msg= "AC1";
		}
    
		return msg;
    
	}

	
	public String removeCourseA(String courseid,String sem){
    
		String msg;
    
    
		if(c_av.get(sem).containsKey(courseid)){
        
			c_av.get(sem).remove(courseid);
			c_sencnt.get(sem).remove(courseid);
			
			for(int i=0;i<3;i++){
            
				if(st_en.get(Students[i]).get(sem).contains(courseid)){
                
					st_en.get(Students[i]).get(sem).remove(courseid);                   
					int update=cm_cnt.get(Students[i]).get(sem)-1;                    
					cm_cnt.get(Students[i]).put(sem, update);                   
                
                
				}
			}
			call_other("Notify",courseid,sem,9001);
			call_other("Notify",courseid,sem,9002);
        
			//msg="Course removed";
			msg="RC1";
        
		}
		else{
        
			//msg="Course Does not exist..Cant Drop";
			msg="RC2";
        
		}
    
    
		return msg;
	}

	
	synchronized public String listCourseAvailability (String sem){
    
    
    
		String INSEcourses=call_other("Getcourses","null",sem,9001);        
		String insecourses=call_other("Getcourses","null",sem,9002);
		String msg= "Course available for : "+sem+"\n"+
                "Enrolled:"+c_sencnt.get(sem)+"\n"+ 
                "Capacity:"+c_av.get(sem)+"\n"+INSEcourses+"\n"+insecourses;
                        
		return msg;
	}




	

	public static void main(String[] args) {
		System.out.println("INSE Server Started...");
		Inse s1 = new Inse();
		
		while(true) {
			
		  MulticastSocket ms1=null;
       	  DatagramSocket ds1=null;
       	  while(true)
       	  {
       		  System.out.println("Receiving Replica Message and sending response to FE");
       		  try
       		  {
    					ms1=new MulticastSocket(1700);
    					ds1=new DatagramSocket(4003);
    					InetAddress ip=InetAddress.getByName("230.0.0.3");
    					ms1.joinGroup(ip);
    					byte []buf=new byte[1000];
    					String msg="";
    					String reply="";
    					DatagramPacket dp1=new DatagramPacket(buf,buf.length);
    					ms1.receive(dp1);
    					msg=new String(dp1.getData());
    					System.out.println();
    					System.out.println("MSG from Replica Manager: "+msg);
    					StringTokenizer st=new StringTokenizer(msg,",");
    					s1.unique_id=Integer.parseInt(st.nextToken());
    					String id=st.nextToken();
    					String method=st.nextToken();
    					System.out.println("unique id:"+s1.unique_id+" ID:"+id+" Method:"+method);
    					
    					if(method.equals("Enrol"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						System.out.println("courseId:"+courseId+" sem:"+sem);
    						reply = s1.enrolcourse(id.trim(),courseId.trim(),sem.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}
    					else if(method.equals("Drop"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						System.out.println(msg);
    						reply=s1.removeCourse(id.trim(),courseId.trim(),sem.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}
    					else if(method.equals("Swap"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						String newCourseId=st.nextToken();
    						reply=s1.swapClasses(id.trim(),courseId.trim(),sem.trim(),newCourseId.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}
    					else if(method.trim().equals("Get"))
    					{
    								
    						reply=s1.classSchedule(id.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    						
    					}
    					else if(method.equals("Add"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						int cap=Integer.parseInt(st.nextToken().trim());
    						reply=s1.addCourse(courseId.trim(),sem.trim(),cap);
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}
    					else if(method.equals("Remove"))
    					{

    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						reply=s1.removeCourseA(courseId.trim(),sem.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}
    					else if(method.equals("List"))
    					{
    						String sem=st.nextToken();
    						reply=s1.listCourseAvailability(sem.trim());
    						InetAddress ip1=InetAddress.getByName("localhost");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7001);
    						ds1.send(dp1);
    					}	
       		  }
       		  catch(Exception e)
       		  {
       			  System.out.println("Thread Exception: "+e.getMessage());
       		  }
       		  finally
       		  {
       			  if(ds1!=null)
       				  ds1.close();
       			  if(ms1!=null)
       				  ms1.close();
       		  }
       	  }	
			
			
		}
		
}

}