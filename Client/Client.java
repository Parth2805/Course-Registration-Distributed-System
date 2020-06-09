package HelloApll;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import java.util.*;

import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;

public class Client {

	static Hello helloImpl;
	public static void main(String[] args) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		
		
		
		ORB orb = ORB.init(args, null);
		
//	Client a = new Client();
//	a.test(args);
		
		int status=0;
		do{
		     
		
		    
		 //   Logger logger= Logger.getLogger("Mylog");
        //      logger.setUseParentHandlers(false);
         //     FileHandler fh=null;
		    int port = 0,choice,capacity,index=0;
		    String newcourseid;
         
		    Scanner sc = new Scanner(System.in);
		    System.out.println("Enter ID:");
		    
		    String studentid=sc.nextLine();
		    String id=studentid.substring(4,9);
		    
		    
		    String server = studentid.substring(0, 4);
		    char person = studentid.charAt(4);
        //      System.out.println(server+" "+person);
		    
 
		    
		    
         /*     if("COMP".equals(server))
		    {   
		    	org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello1";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		       
		    }    
		    if("SOEN".equals(server))
		    {  
		    	org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello2";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		        
		    }    
		    if("INSE".equals(server))
		    {
		    	org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello3";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		       
		    }*/
   
		    Thread t1 = new Thread(()->{
		    	
		    	org.omg.CORBA.Object obj;
		    	try {
					obj = orb.resolve_initial_references("NameService");
				
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello1";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		        
				} catch (InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	System.out.println("t1"+helloImpl.enrolcourse("COMPS6001", "COMP7003", "Fall"));
		    	
		
		    });
			
			
		    Thread t2 = new Thread(()->{
		    	
		    	
		    	org.omg.CORBA.Object obj;
				try {
					obj = orb.resolve_initial_references("NameService");
				
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello1";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		        
				} catch (InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	System.out.println("t2"+helloImpl.enrolcourse("SOENS6001", "COMP7001", "Fall"));
		
		    });
		    
		    
		    Thread t3 = new Thread(()->{
		    	org.omg.CORBA.Object obj;
				try {
					obj = orb.resolve_initial_references("NameService");
				
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello1";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		        
				} catch (InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	System.out.println("t3"+helloImpl.enrolcourse("INSES6001", "COMP7001", "Fall"));
		
		    });
		    
		    
		    
		    Thread t4 = new Thread(()->{
		    	
		    	org.omg.CORBA.Object obj;
				try {
					obj = orb.resolve_initial_references("NameService");
				
				
				// Use NamingContextExt instead of NamingContext. This is 
		        // part of the Interoperable naming Service.  
		        NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
		 
		        // resolve the Object Reference in Naming
		        String name = "Hello1";
		        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
		        
				} catch (InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	System.out.println("t4"+helloImpl.removeCourseA("COMPA1111", "COMP7001", "Fall"));
		
		    	
		    	
		    });
		    org.omg.CORBA.Object obj;
			
		    
		    try {
				obj = orb.resolve_initial_references("NameService");  
				NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);        
				String name = "Hello1";
				helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
			} catch (InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
           t1.start();
			t2.start();
			t3.start();
			t4.start();
		    
	/*	    helloImpl.enrolcourse("COMPS6001", "COMP7001", "Fall");
		    helloImpl.enrolcourse("SOENS6001", "COMP7001", "Fall");
		    helloImpl.enrolcourse("INSES6001", "COMP7001", "Fall");
		    helloImpl.removeCourseA("COMPA1111", "COMP7001", "Fall");*/
		    
        
		    String courseid,semester,msg="";
		    
		    if(person=='S'){
		        
		        
		        choice=0;
		        do{
		            
		            
		            System.out.println("1.Enroll for a course");
		            System.out.println("2.Drop a course");
		            System.out.println("3.Swap Course");
		            System.out.println("4.Class Schedule");
		            System.out.println("5.Logout");
		            System.out.println("Enter Choice");
		            
		            choice=sc.nextInt();
		            
		            switch(choice){
		                
		                case 1: 
		                		//logger.info("Request for enrolling in a course");
		                        System.out.print("Enter CourseID:");
		                        Scanner sc1=new Scanner(System.in);
		                        courseid=sc1.nextLine();
		                        System.out.print("Enter  Semester:");
		                        semester=sc1.nextLine();                                                            
		                     //   logger.info("Called method enrolcourse with parameters:Studentid-"+studentid+" courseid:-"+courseid+" Semseter:"+semester);
		                        msg= helloImpl.enrolcourse(studentid, courseid, semester);
		                      //  logger.info("Server replied:"+msg);
		                        System.out.println(msg);                                  
		                        
		                    break;
		                
		                case 2:// logger.info("Request for dropping a course");
		                        System.out.println("Enter CourseID to drop:");
		                        Scanner sc2=new Scanner(System.in);
		                        courseid=sc2.nextLine();
		                        System.out.println("Enter Semester to drop");
		                        semester=sc2.nextLine();
		                     //   logger.info("Called method removecourse with parameters:Studentid-"+studentid+" courseid:-"+courseid+" Semseter:"+semester);
		                        msg=helloImpl.removeCourse(studentid, courseid, semester);
		                     //   logger.info("Server replied:"+msg);
		                        System.out.println(msg);
		                       
		                    break;
		                    
		                case 3:System.out.println("Enter Courseid to Remove:");
		                	   Scanner sc3= new Scanner(System.in);
		                	   courseid=sc3.nextLine();
		                	   System.out.println("Enter Courseid to Add");
		                	   Scanner sc4= new Scanner(System.in);
		                	   newcourseid= sc4.nextLine();
		                	   System.out.println("Enter sem");
		                	   Scanner sc5= new Scanner(System.in);
		                	   semester=sc5.nextLine();
		                	   msg=helloImpl.swapClasses(studentid, courseid, semester, newcourseid);
		                	   System.out.println(msg);
		                	
		                	break;
		                case 4:// logger.info("Called method classSchedule with parameters:Studentid:"+studentid);
		                        
		                        msg=helloImpl.classSchedule(studentid);
		                        System.out.println(msg);                             
		                      //  logger.info("Server replied:"+msg);
		                    break;
		                
		                case 5:// logger.info("Logged out");
		                        break;    
		                                  
		            }
		            

		        }while(choice!=5);
		    }
		    else{
		        
		        choice=0;
		        do{
		            
		            
		            System.out.println("1.Add course");
		            System.out.println("2.Remove a course");
		            System.out.println("3.Course List");
		            System.out.println("4.Logout");
		            System.out.println("Enter Choice");
		            
		            choice=sc.nextInt();
		            
		            switch(choice){
		                
		                case 1: //logger.info("Request for Adding a course");
		                        System.out.println("Adding course for student(y/n)?");
		                        Scanner sc4= new Scanner(System.in);
		                        String ans1=sc4.nextLine();                                    
		                        System.out.println("Enter CourseID:");
		                        Scanner sc1=new Scanner(System.in);
		                        courseid=sc1.nextLine();
		                        System.out.println("Enter  Semester:");
		                        semester=sc1.nextLine();
		                        
		                        if("y".equals(ans1)){
		                            System.out.println("Enter Stduentid:");
		                            Scanner sc3 = new Scanner(System.in);                                      
		                            studentid=sc3.nextLine();
		                         //   logger.info("Adding for student:"+studentid);
		                            msg=helloImpl.enrolcourse(studentid,courseid,semester);
		                        //    logger.info("Server Replied");
		                            System.out.println(msg);
		                            
		                        }
		                        else{
		                            
		                            System.out.println("Enter Capacity");
		                            Scanner sc3= new Scanner(System.in);
		                            capacity=sc3.nextInt();
		                        //    logger.info("Adding course for semester:"+semester+" Capacity:"+capacity);
		                            msg=helloImpl.addCourse(studentid,courseid, semester,capacity);
		                        //    logger.info("Server Replied:");
		                            System.out.println(msg);                                        
		                        }
		                        
		                        
		                    break;
		                
		                case 2:// logger.info("Request for dropping a course");
		                        System.out.println("Removing course for student(y/n)?");
		                        Scanner sc7= new Scanner(System.in);
		                        String ans2 = sc7.nextLine();
		                       // sc7.close();
		                        System.out.println("Enter CourseID to drop:"); 
		                        Scanner sc2= new Scanner(System.in);
		                        courseid = sc2.nextLine();
		                        System.out.println("Enter semester:");
		                        semester=sc2.nextLine();
		                        
		                        if("y".equals(ans2)){
		                            
		                            System.out.println("Enter Student id:");                                       
		                            Scanner sc8= new Scanner(System.in);
		                            studentid=sc8.nextLine();
   //                                     logger.info("Dropping course for student:"+studentid+" for semester:"+semester);
		                            msg=helloImpl.removeCourse(studentid, courseid, semester);
   //                                     logger.info("Server Replied: "+msg);
		                        }
		                        else{
   //                                    logger.info("Dropping course "+courseid + "for semester: "+semester);
		                              msg=helloImpl.removeCourseA(studentid,courseid, semester);
		                        	  System.out.println(msg);
   //                                    logger.info("Server Replied: "+msg);
		                        }
		                        
		                    break;
		                    
		                case 3: 
		                        System.out.println("Enter Semester:");
		                        Scanner sc9= new Scanner(System.in);
		                        semester= sc9.nextLine();
   //                                 logger.info("Requesting for course availability for semester: "+semester);
		                        msg=helloImpl.listCourseAvailability(studentid,semester);
		                        System.out.println(msg);
 //                                   logger.info("Server Replied:"+msg);
		                       
		                    break;
		                
		                case 4:break;    
		            }
		        }while(choice!=4);
		    }
		    System.out.println("Press 1 to LOG IN/2 to Close the Application  :");
		    status = sc.nextInt();
      
      }while(status!=2);
		System.out.println(helloImpl.sayhello());
		
		
		
		
		

	}

	
/*void test(String[] args){
		
		ORB orb = ORB.init(args, null);
		
		
	}*/
	
	
	
	
}
	
	
	



