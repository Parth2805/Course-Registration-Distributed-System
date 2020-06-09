package Main;

import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*; 
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.SortingFocusTraversalPolicy;

import Main.*;


public class COMP_Server implements SA_Interface
{
	        static int unique_id=0;
	        int crashCounter=0;
	        String msg1="";
            Map<String, ArrayList<String>> Comp_Fall= null;
            Map<String, ArrayList<String>> Comp_Winter= null;
            Map<String, ArrayList<String>> Comp_Summer= null;
            Logger log=null;
            FileHandler file;
            
            Map<String,HashMap<String,Integer>> Database=null;
	        HashMap<String,Integer> Fall=null;
            HashMap<String,Integer> Winter=null;
	        HashMap<String,Integer> Summer=null;
            ArrayList<String> Advisor = null;
            Map<String, List<Integer>> Counter=null; 
            private final Object lockA ;
            

        COMP_Server() 
	    {	
        	lockA = new Object();
            Comp_Fall= new HashMap<>();
            Comp_Winter= new HashMap<>();
            Comp_Summer= new HashMap<>();
            
            Database=new HashMap<>();
            Fall=new HashMap<>();
            Winter=new HashMap<>();
            Summer=new HashMap<>();    
            
            Counter= new HashMap<>();
            
            try
            {
                
                log=Logger.getLogger("COMP Server");
                file=new FileHandler("G:/Logs/COMP_Server.log",true);
                log.addHandler(file);
                log.setUseParentHandlers(false);
                SimpleFormatter ft=new SimpleFormatter();
                file.setFormatter(ft);
            
            }catch(IOException e){e.printStackTrace();}
            
            
            Advisor = new ArrayList<>();
            Advisor.add("COMPA1111");
            Advisor.add("COMPA2222");
            Advisor.add("COMPA3333");
    
            Fall.put("COMP7001",3);
            Fall.put("COMP7002",3);
            Fall.put("COMP7003",3);
            Database.put("Fall",Fall);

            Winter.put("COMP7004",3);
            Winter.put("COMP7005",3);
            Winter.put("COMP7001",3);
            Database.put("Winter",Winter);

            Summer.put("COMP7006",3);
            Summer.put("COMP7007",3);
            Summer.put("COMP7002",3);
            Database.put("Summer",Summer);
            
	}
        
        void reset(ArrayList<String> list)
        {
            Fall.put("COMP7001",3);
            Fall.put("COMP7002",3);
            Fall.put("COMP7003",3);
            Database.put("Fall",Fall);

            Winter.put("COMP7004",3);
            Winter.put("COMP7005",3);
            Winter.put("COMP7001",3);
            Database.put("Winter",Winter);

            Summer.put("COMP7006",3);
            Summer.put("COMP7007",3);
            Summer.put("COMP7002",3);
            Database.put("Summer",Summer);
            
            Comp_Fall.clear();
            Comp_Winter.clear();
            Comp_Summer.clear();
            Counter.clear();
            String reply="";
            String fall="";
            String winter="";
            String summer="";
            for (String string : list) 
            {
            	StringTokenizer st=new StringTokenizer(string,",");
				int temp=Integer.parseInt(st.nextToken());
				String id=st.nextToken().trim();
				String method=st.nextToken().trim();
				
				if(method.equals("Enrol"))
				{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						reply =enrollCourse(id.trim(), courseId.trim(), sem.trim());
						//System.out.println(reply);
				}
				else if(method.equals("Drop"))
				{
					String courseId=st.nextToken();
					String sem=st.nextToken();
					reply=dropCourse(id.trim(),courseId.trim(),sem.trim());
					//System.out.println(reply);
				}
				else if(method.equals("Swap"))
				{
					String courseId=st.nextToken();
					String sem=st.nextToken();
					String newCourseId=st.nextToken();
					reply=swapClasses(id.trim(), sem.trim(), courseId.trim(), newCourseId.trim());
					//System.out.println(reply);
				}
				else if("Get".equals(method.trim()))
				{
					reply=getClassShedule(id.trim().toString());
					//System.out.println(reply);
				}
				else if(method.equals("Add"))
				{
					String courseId=st.nextToken();
					String sem=st.nextToken();
					int cap=Integer.parseInt(st.nextToken().trim());
					reply=addCourse(id.trim(), courseId.trim(), sem.trim(), cap);
					
				}
				else if(method.equals("Remove"))
				{
					String courseId=st.nextToken();
					String sem=st.nextToken();
					reply=removeCourse(courseId.trim(), sem.trim());
					//System.out.println(reply);
				}
				else if(method.equals("List"))
				{
					String sem=st.nextToken();
					reply=listCourseAvailability(id.trim(), sem.trim());
					//System.out.println(reply);	
				}	
		    	
			}
            
            
            System.out.println("Data after Crash\n");
            
            for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
            {     
                 int check=0;
                 
                 String P_Key=pair.getKey();
                 
                 if(P_Key.equalsIgnoreCase("Fall"))
                 {
                     HashMap<String,Integer> map =pair.getValue();
                     fall="\n _______Fall_______\n";
                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                     {
                          fall=fall+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                     }
                 }
                 if(P_Key.equalsIgnoreCase("Winter"))
                 {
                     HashMap<String,Integer> map =pair.getValue();
                     winter="\n _______Winter_______\n";
                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                     {
                          winter=winter+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                     }
                 }
                 if(P_Key.equalsIgnoreCase("Summer"))
                 {
                     HashMap<String,Integer> map =pair.getValue();
                     summer="\n _______Summer_______\n";
                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                     {
                          summer=summer+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                     }
                 }
                 
            }
            
            System.out.println(fall+"\n"+winter+"\n"+summer+"\n");
            
            
            System.out.println("COMP Fall");
            for (Map.Entry<String, ArrayList<String>> entry : Comp_Fall.entrySet()) 
            {
            	
            	System.out.println(entry.getKey()+"-->"+entry.getValue());
            }
            System.out.println("\nCOMP Winter");
            for (Map.Entry<String, ArrayList<String>> entry : Comp_Winter.entrySet()) 
            {
            	
            	System.out.println(entry.getKey()+"-->"+entry.getValue());
            }
            System.out.println("\nCOMP Summer");
            for (Map.Entry<String, ArrayList<String>> entry : Comp_Summer.entrySet()) 
            {
            	
            	System.out.println(entry.getKey()+"-->"+entry.getValue());
            }
            
        }
       
    	
        String lookfor(String CourseID,String Semester)
        {
        	   if(Semester.trim().toString().equalsIgnoreCase("Fall"))
	            {
	                String removed="";
	                for (Map.Entry<String, ArrayList<String>> entry : Comp_Fall.entrySet()) 
	                {
	                     if(entry.getValue().contains(CourseID))
	                     {
	                         Comp_Fall.get(entry.getKey()).remove(CourseID.trim().toString());
	                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("COMP"))
	                         {
	                             Counter.get(entry.getKey()).set(0,Counter.get(entry.getKey()).get(0)+1); 
	                             removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                         else
	                         {
	                            Counter.get(entry.getKey()).set(1,Counter.get(entry.getKey()).get(1)+1);  
	                            removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                     }
	                 }
	                return removed; 
	            }
	            
	           if(Semester.equalsIgnoreCase("Winter")) 
	           {
	               String removed="";
	                for (Map.Entry<String, ArrayList<String>> entry : Comp_Winter.entrySet()) 
	                {
	                     if(entry.getValue().contains(CourseID))
	                     {
	                         Comp_Winter.get(entry.getKey()).remove(CourseID.trim().toString());
	                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("COMP"))
	                         {
	                            Counter.get(entry.getKey()).set(2,Counter.get(entry.getKey()).get(2)+1); 
	                             removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                         else
	                         {
	                            Counter.get(entry.getKey()).set(3,Counter.get(entry.getKey()).get(3)+1);  
	                            removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                        
	                     }
	                 }
	                return removed;       
	           }
	           if(Semester.equalsIgnoreCase("Summer"))
	           {
	                String removed="";
	                for (Map.Entry<String, ArrayList<String>> entry : Comp_Summer.entrySet()) 
	                {
	                     if(entry.getValue().contains(CourseID))
	                     {
	                         Comp_Summer.get(entry.getKey()).remove(CourseID.trim().toString());
	                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("COMP"))
	                         {
	                            Counter.get(entry.getKey()).set(4,Counter.get(entry.getKey()).get(5)+1); 
	                             removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                         else
	                         {
	                            Counter.get(entry.getKey()).set(5,Counter.get(entry.getKey()).get(5)+1);  
	                            removed=removed+entry.getKey()+" -> "+entry.getValue();
	                         }
	                     }
	                 }
	                
	                return removed;
	           }
	
	         return " ";
        }

        boolean ifExist(String StudentID,String CourseID,String Semester)
        {
          for(Map.Entry<String,ArrayList<String>> pair: Comp_Fall.entrySet())
          {
              if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
              {
                  return true;
              }
          }
          for(Map.Entry<String,ArrayList<String>> pair: Comp_Winter.entrySet())
          {
              if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
              {
                  return true;
              }
          }    
          for(Map.Entry<String,ArrayList<String>> pair: Comp_Summer.entrySet())
          {
              if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
              {
                  return true;
              }
          }    
            
           return false;
        }
        
        boolean ifExist_drop(String StudentID,String CourseID,String Semester)
        {
          if(Semester.equalsIgnoreCase("Fall"))
          {
        	   for(Map.Entry<String,ArrayList<String>> pair: Comp_Fall.entrySet())
               {
                   if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                   {
                       return true;
                   }
               }
          }
          
          if(Semester.equalsIgnoreCase("Winter"))
          {
        	  for(Map.Entry<String,ArrayList<String>> pair: Comp_Winter.entrySet())
              {
                  if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                  {
                      return true;
                  }
              }  
          }
          
          if(Semester.equalsIgnoreCase("Summer"))
          {
        	  for(Map.Entry<String,ArrayList<String>> pair: Comp_Summer.entrySet())
              {
                  if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                  {
                      return true;
                  }
              }  
          }
           return false;
        }
        
  
        String drop_external(String CourseID,String Semester)
        {
        	synchronized(lockA)
            {
	            String r;
	            int i=Database.get(Semester.trim().toString()).get(CourseID.trim().toString());
	            i=i+1;
	            Database.get(Semester.trim().toString()).put(CourseID.trim().toString(),i);
	            r="yes";
	            return r;
            }
        }

        public String enrollCourse(String StudentID,String CourseID,String Semester) 
        {  
        	
        	
        	synchronized(lockA)
            {
         	String r="default";
        	try
        	{                
                if(CourseID.substring(0,4).equalsIgnoreCase("COMP"))
                {
                    if(StudentID.substring(0,5).equalsIgnoreCase("COMPS"))
                    {     
                                int check=0;
                                if(Semester.equalsIgnoreCase("Fall"))
                                {
                                
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                        return "EC5";
                                    }
                                    else
                                    {
                                            if(Database.get(Semester).containsKey(CourseID))
                                            {
                                            	
                                                int value1=Database.get(Semester).get(CourseID);
                                                if(value1!=0)
                                                { 
                                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                        int i=Counter.get(StudentID).get(0)+Counter.get(StudentID).get(1);
                                                
                                                            if(i<3)
                                                            {
                                                            	
                                                                Comp_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                                                Comp_Fall.get(StudentID).add(CourseID); 
                                                                System.out.println();
                                                                Counter.get(StudentID).set(0,Counter.get(StudentID).get(0)+1);
                                                                Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);

                                                                r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                                log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});

                                                             //   return r;
                                                                return "EC1";
                                                            }
                                                            else
                                                            {
                                                                r="You have reached maxmium limit of course in this term.";
                                                                return "EC3";
                                                            }
                                                            
                                                     
                                                }
                                                else
                                                {
                                                   // r="Course is full";
                                                    return "EC4";
                                                }
                                             }
	                                         else                         
	                                         {
	                                            // r="Course is not availible in this term";
	                                             return "EC2";                                                
	                                          }
	                    
                                    }
                                }
                                if(Semester.equalsIgnoreCase("Summer"))
                                {
                                    
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                      return "EC5";
                                    }
                                    else
                                    {
                                            if(Database.get(Semester).containsKey(CourseID))
                                            {   
                                                int value1=Database.get(Semester).get(CourseID);
                                                if(value1!=0)
                                                { 
                                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                        int i=Counter.get(StudentID).get(4)+Counter.get(StudentID).get(5);
                                                        if(i<3)
                                                        {
                                                            Comp_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                                            Comp_Summer.get(StudentID).add(CourseID); 
                                                            Counter.get(StudentID).set(4,Counter.get(StudentID).get(4)+1);
                                                            Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);
                                                            r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                            log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                                            return "EC1";
                                                        }
                                                        else
                                                        {
                                                            r="You have reached maxmium limit of course in this term.";
                                                            return "EC3";
                                                        }                                           
                                                }
                                                else
                                                {
                                                   // r="Course is full";
                                                    check=1;
                                                    return "EC4";
                                                }
                                            }
                                            else                         
                                            {
                                             //  r="Course is not availible in this term";
                                               //check=1;
                                               return "EC2";
                                            }
                                    
                                }
                                }
                                if(Semester.equalsIgnoreCase("Winter"))
                                { 
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                      return "EC5";
                                      
                                    }
                                    else
                                    {       
                                                System.out.println("Inside Winter");
                                                if(Database.get(Semester).containsKey(CourseID))
                                                {
                                                    
                                                    int value1=Database.get(Semester).get(CourseID);
                                                    if(value1!=0)
                                                    { 
                                                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                            int i=Counter.get(StudentID).get(2)+Counter.get(StudentID).get(3);
                                                            if(i<3)
                                                            {
                                                                Comp_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                                                Comp_Winter.get(StudentID).add(CourseID); 

                                                                Counter.get(StudentID).set(2,Counter.get(StudentID).get(2)+1);
                                                                Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);
                                                                r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                                log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                                                return "EC1";
                                                            }
                                                            else
                                                            {
                                                                r="You have reached maxmium limit of course in this term.";
                                                                return "EC3";
                                                            }                                           
                                                    }
                                                    else
                                                    {
                                                       // r="Course is full";
                                                       
                                                        return "EC4";
                                                    }
                                                }
                                                else                         
                                                {
                                                  // r="Course is not availible in this term";
                                                  
                                                    return "EC2";                                                  
                                                }
                                    } 
                                }
                              
                       // }
               }
               else
               {         
            	          System.out.println("Inside else of enroll for "+StudentID);
            	          try
                             {
            	        	    if(Database.get(Semester.trim().toString()).containsKey(CourseID.trim().toString()))
                         	    {
                                     int i=Database.get(Semester.trim().toString()).get(CourseID.trim().toString());
                                     if(i==0)
                                     {    

                           	                  return "EC4";
                                     }
                                     else
                                     {

                           	               i=i-1;
                                           Database.get(Semester.trim().toString()).put(CourseID.trim().toString(), i);
                                           return "yes";
                                     }
                         	    }
            	        	    else
            	        	    {
            	        		  return "EC2";
            	        	    }
                             }catch(Exception e)
                             {
                                 System.out.println(e.getStackTrace());
                             }
               }
            }
                
            if(CourseID.substring(0,4).equalsIgnoreCase("SOEN"))
            {
                    DatagramSocket asocket=new DatagramSocket();
                    InetAddress aHost=InetAddress.getByName("localhost");
                    
                    if(ifExist(StudentID,CourseID,Semester))
                    {
                      return "EC5";
                    }
                    
                    else
                    {   
                        if(Semester.equalsIgnoreCase("Fall"))
                        {             
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(0)+Counter.get(StudentID).get(1);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5);
                            
                            if(i<3)
                            {
                                if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,4444);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to SOEN Server for Enroll");
                                    byte[] buffer=new byte[1000];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                    if("yes".equalsIgnoreCase(temp.trim().toString()))
                                    {
                                         Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                         Comp_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                         Comp_Fall.get(StudentID).add(CourseID);  
                                         Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)+1);
                                         log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                         return "EC1";
                                    }
                                    else
                                    {
                                        return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                    r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                }
                            }
                            else
                            {
                                    r="You have reached your maximum limit of Subs in this semester";
                                    return "EC3";
                            }
                        }
                        else if(Semester.equalsIgnoreCase("Summer"))
                        {
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(4)+Counter.get(StudentID).get(5);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5); 
                            if(i<3)
                            {
                                if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,4444);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to SOEN Server for Enroll");
                                    byte[] buffer=new byte[100];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                   if("yes".equalsIgnoreCase(temp.trim().toString()))
                                   {
                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                        Comp_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                        Comp_Summer.get(StudentID).add(CourseID);  
                                        Counter.get(StudentID).set(5,Counter.get(StudentID).get(5)+1);
                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                       // return "You are enrollled in "+CourseID+" Successfully for "+Semester+" "; 
                                        return "EC1";
                                   }
                                   else
                                   {
                                	   return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                    r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                }
                            }   
                            else
                            {
                                r="You have reached your maximum limit of Subs in this semester";
                                return "EC3";
 
                            }    
                        }   
                        else if(Semester.equalsIgnoreCase("Winter"))
                        {
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(2)+Counter.get(StudentID).get(3);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5);
                            
                            if(i<3)
                            {
                               
                                if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,4444);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to SOEN Server for Enroll");
                                    byte[] buffer=new byte[100];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                   if("yes".equalsIgnoreCase(temp.trim().toString()))
                                   {
                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                        Comp_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                        Comp_Winter.get(StudentID).add(CourseID);  
                                        Counter.get(StudentID).set(3,Counter.get(StudentID).get(3)+1);
                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                        //return "You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                        return "EC1";
                                   }
                                   else
                                   {
                                	   return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                     r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                }
                             
                            }
                            else
                            {
                                r="You have reached your maximum limit of Subs in this semester";
                                return "EC3";
                            }    
                        }   
                }
            }
            if(CourseID.substring(0,4).equalsIgnoreCase("INSE"))
            {
                    DatagramSocket asocket=new DatagramSocket();
                    InetAddress aHost=InetAddress.getByName("localhost");
                    if(ifExist(StudentID,CourseID,Semester))
                    {
                        return "EC5";
                    }
                    else
                    {   
                        if(Semester.equalsIgnoreCase("Fall"))
                        {             
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(0)+Counter.get(StudentID).get(1);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5);
                                
                            if(i<3)
                            {
                               if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,5555);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to INSE Server for Enroll");
                                    byte[] buffer=new byte[100];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                   if("yes".equalsIgnoreCase(temp.trim().toString()))
                                   {
                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                        Comp_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                        Comp_Fall.get(StudentID).add(CourseID);  
                                        Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)+1);
                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                     //   return "You are enrollled in "+CourseID+" Successfully for "+Semester+" ";  
                                        return "EC1";
                                   }
                                   else
                                   {
                                	   return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                    r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                    
                                }
                            }
                            else
                            {
                                r="You have reached your maximum limit of Subs in this semester";
                                return "EC3";  
                            }
                               
                        }
                        else if(Semester.equalsIgnoreCase("Summer"))
                        {
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(4)+Counter.get(StudentID).get(5);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5);
                            
                            if(i<3)
                            {
                                if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,5555);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to INSE Server for Enroll");
                                    byte[] buffer=new byte[100];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                   if("yes".equalsIgnoreCase(temp.trim().toString()))
                                   {
                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                        Comp_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                        Comp_Summer.get(StudentID).add(CourseID);  
                                        Counter.get(StudentID).set(5,Counter.get(StudentID).get(5)+1);
                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                        //return "You are enrollled in "+CourseID+" Successfully for "+Semester+" "; 
                                        return "EC1";
                                   }
                                   else
                                   {
                                	   return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                    r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                }
                            }
                            else
                            {
                                r="You have reached your maximum limit of Subs in this semester";
                                return "EC3"; 
                            }    
                        }   
                        else if(Semester.equalsIgnoreCase("Winter"))
                        {
                            Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                            int i=Counter.get(StudentID).get(2)+Counter.get(StudentID).get(3);
                            int j=Counter.get(StudentID).get(1)+Counter.get(StudentID).get(3)+Counter.get(StudentID).get(5);
                            if(i<3)
                            {
                                if(j<2)
                                {
                                    String s="Enroll"+" "+StudentID+" "+CourseID+" "+Semester;
                                    byte[] reply=s.getBytes();
                                    DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,5555);
                                    asocket.send(req);
                                    
                                    System.out.println("Sent to INSE Server for Enroll");
                                    byte[] buffer=new byte[100];
                                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                    asocket.receive(Rec);
                                    String temp=new String(Rec.getData());
                                    
                                   if("yes".equalsIgnoreCase(temp.trim().toString()))
                                   {
                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                        Comp_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                        Comp_Winter.get(StudentID).add(CourseID);  
                                        Counter.get(StudentID).set(3,Counter.get(StudentID).get(3)+1);
                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                      //  return "You are enrollled in "+CourseID+" Successfully for "+Semester+" ";  
                                        return "EC1";
                                   }
                                   else
                                   {
                                	   return temp.trim().toString();
                                    }                                
                                }
                                else
                                {
                                    r="You are already enrolled in two courses of other department";
                                    return "EC7";
                                }
                            }
                            else
                            {
                                r="You have reached your maximum limit of Subs in this semester";
                                return "EC3";  
                            }    
                        }   
                }
            }
            
        	
        	}
        	catch(Exception e)
        	{
        		
        	}
            return r;
          }
        }
		public String getClassShedule(String StudentID) 
		{	   
	            System.out.println("COMP Get Schedule");
	            
	            String msg="Course\tSemester\n"+Comp_Fall.get(StudentID)
	                    +"\tFall"+"\n"+Comp_Winter.get(StudentID)
	                    +"\tWinter"+"\n"+Comp_Summer.get(StudentID)
	                    +"\tSummer";
	            msg=msg.replace("null","[]");
	            return msg;
		}
        

        public String dropCourse(String StudentID,String CourseID,String Semester) 
        {    
        	synchronized(lockA)
            {
            String r="";
            try
            {
            if(CourseID.substring(0, 4).equalsIgnoreCase("COMP"))
            {    
                   if(ifExist_drop(StudentID,CourseID,Semester))
                   {    
                        if(Semester.equalsIgnoreCase("Fall"))
                        {  
                                  Comp_Fall.get(StudentID).remove(CourseID);
                                  Counter.get(StudentID).set(0,Counter.get(StudentID).get(0)-1);
                                  Database.get(Semester).put(CourseID, Database.get(Semester).get(CourseID)+1);
                                  System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                  log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                  //return "Course "+CourseID+" Dropped Successfully";
                                  return "DC1";
                        }    
                        if(Semester.equalsIgnoreCase("Winter"))
                        {      
                                   Comp_Winter.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(2,Counter.get(StudentID).get(2)-1);
                                   Database.get(Semester).put(CourseID, Database.get(Semester).get(CourseID)+1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                   //return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                        if(Semester.equalsIgnoreCase("Summer"))
                        {
                                   Comp_Summer.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(4,Counter.get(StudentID).get(4)-1);
                                   Database.get(Semester).put(CourseID, Database.get(Semester).get(CourseID)+1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                   //return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }          
                    }
                    else
                    {
                        r="You are not enrolled in this course";
                        return "DC2";
                    }
               }     
            
            else
            {
              DatagramSocket asocket=new DatagramSocket();
              InetAddress aHost=InetAddress.getByName("localhost");
              
              if(ifExist_drop(StudentID,CourseID,Semester))
                {
                    String s="Drop"+" "+CourseID+" "+Semester;
                    byte[] reply=s.getBytes();
                    if(CourseID.substring(0, 4).equalsIgnoreCase("SOEN"))
                    {
                        DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,4444);
                        asocket.send(req);    
                        System.out.println("Request sent to SOEN");
                    }
                    else if(CourseID.substring(0, 4).equalsIgnoreCase("INSE"))
                    {
                        DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,5555);
                        asocket.send(req);
                        System.out.println("Request sent to INSE");
                    }
                    
                    byte[] buffer=new byte[1000];
                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                    asocket.receive(Rec);
                    System.out.println("Request Received");
                    String temp=new String(Rec.getData());
                   
                    if("yes".equalsIgnoreCase(temp.trim().toString()))
                    {
                        if(Semester.equalsIgnoreCase("Fall"))
                        {        
                                  Comp_Fall.get(StudentID).remove(CourseID);
                                  Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)-1);
                                  System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                  log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                              //  return "Course "+CourseID+" Dropped Successfully"; 
                                  return "DC1";
                        } 
                        if(Semester.equalsIgnoreCase("Winter"))
                        {      
                                   Comp_Winter.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(3,Counter.get(StudentID).get(3)-1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                               //  return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                        if(Semester.equalsIgnoreCase("Summer"))
                        {
                                   Comp_Summer.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(5,Counter.get(StudentID).get(5)-1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                               //  return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                    }
                    else
                    {
                       return "DC2";    
                    }
                }
                else
                {
                    return "DC2";
                }
            }
            }
            catch(Exception e){}        
            return r;
            }
            
        }
        
    
    	public String swapClasses(String StudentID, String Semester, String CourseID, String NewCourseID)
    	{
    		synchronized(lockA)
            {
		    			String drop=dropCourse(StudentID, CourseID, Semester);
						String compare_drop="DC1";
						if(drop.equalsIgnoreCase(compare_drop))
						{
							String result= enrollCourse(StudentID, NewCourseID, Semester);
							String compare="EC1";
							if(result.equalsIgnoreCase(compare))
							{
								String r="Swapping from "+CourseID+" to "+NewCourseID+" Successful";
								log.log(Level.INFO, "Student with ID: {0} Swapped Course {1} to {2}", new Object[]{StudentID, CourseID, NewCourseID});
								//return r;
								return "SS1";
							}
							else
							{
								enrollCourse(StudentID, CourseID, Semester);
								//return "Swap Failed, Unable to enroll in desired course because"+result;
								return result;
							}
						}
						else
						{
						    //return "Swap Failed, Unable to drop from current course because "+drop;	
							return drop;
						}
            }
    		
    	}
    	
    	
        //Advisor Methods
        
        public String addCourse(String AdvisorID,String CourseID,String Semester,int Capacity)
        {
            if(Advisor.contains(AdvisorID))
            {
                if(Database.get(Semester).containsKey(CourseID))
                {
                    return "AC2";
                }
                else
                {       
                    Database.get(Semester).put(CourseID,Capacity);
                    log.info("Course "+CourseID+" added in "+Semester);
                    //return "Course "+CourseID+" Added in "+Semester; 
                    return "AC1";
                }
            }
            else
            {
                //return "You are not a registered Advisor";
                return "AC3";
            }
        } 
        
        public String removeCourse(String CourseID,String Semester)
        {
             String r="";
        	     if(CourseID.substring(0, 4).equalsIgnoreCase("COMP"))
	             {
	                 if(Database.get(Semester).containsKey(CourseID))
	                 {
	                	 String result=lookfor(CourseID,Semester);
	                	 Database.get(Semester).remove(CourseID,Database.get(Semester).get(CourseID));
	                     r="RC1";
	                     log.log(Level.INFO, "Course {0} removed from {1}", new Object[]{CourseID, Semester});
	                     DatagramSocket asocket=null;
	                     try
	                     {
	                    	 	//SOEN_SEND_RECEIVE
	                            asocket=new DatagramSocket();
	                            InetAddress aHost=InetAddress.getByName("localhost");
	                            String request="Remove"+" "+CourseID+" "+Semester;
	                            byte[] buf=new byte[1000];
	                            buf=request.getBytes();
	                            DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,4444);
	                            asocket.send(req);    
	                            System.out.println("Request sent to SOEN");
	
//	                            byte[] buf1=new byte[1000];
//	                            DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
//	                            asocket.receive(rep);
//	                            String soen_return=new String(rep.getData());
//	                            r=r+"\n"+soen_return;
	                            
	                            //INSE_SEND_RECEIVE
	                            String request2="Remove"+" "+CourseID+" "+Semester;
	                            byte[] buf2=new byte[1000];
	                            buf2=request2.getBytes();
	                            DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,5555);
	                            asocket.send(req2);    
	                            System.out.println("Request sent to INSE");
	
//	                            byte[] buf3=new byte[1000];
//	                            DatagramPacket rep2=new DatagramPacket(buf3,buf3.length);
//	                            asocket.receive(rep2);
//	                            String inse_return=new String(rep2.getData());
//	                            r=r+"\n"+inse_return;
                           
	                     }catch(Exception e)
	                     {
	                         
	                     }finally
	                     {
	                        if (asocket != null)
	                         {
	                              	asocket.close();
	                         }  
	                     } 
	                   return "RC1";
	                 }  
	                 else
	                 {
	                	 return "RC2";
	                 }                     
	             }
             
             if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("SOEN") || CourseID.trim().toString().substring(0,4).equalsIgnoreCase("INSE"))
             {
                String result=lookfor(CourseID.trim().toString(),Semester.trim().toString());
                r=result;
                log.log(Level.INFO, "Course {0} removed from {1}", new Object[]{CourseID, Semester});
                return r;
             }
           return r;  
        
        }
        
        public String listCourseAvailability(String AdvisorID,String Semester)
        {
             String r="";
                 if(Semester.trim().toString().equalsIgnoreCase("Fall"))
                 {
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("COMPA"))
                     {
                        for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                        {     
                             int check=0;
                             String fall="";
                             String P_Key=pair.getKey();
                             if(P_Key.equalsIgnoreCase("Fall"))
                             {
                                 HashMap<String,Integer> map =pair.getValue();
                                 fall="\n _______Fall_______\n";
                                 for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                 {
                                      fall=fall+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                 }
                                 DatagramSocket asocket=null;
                                 try
                                 {
                                        //SOEN_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,4444);
                                        asocket.send(req);    
                                        System.out.println("Request sent to SOEN");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String soen_return=new String(rep.getData()).trim();
                                        fall=fall+soen_return;
                                        
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,5555);
                                        asocket.send(req2);    
                                        System.out.println("Request sent to INSE");

                                        byte[] buf3=new byte[1000];
                                        DatagramPacket rep2=new DatagramPacket(buf3,buf3.length);
                                        asocket.receive(rep2);
                                        String inse_return=new String(rep2.getData()).trim();
                                        
                                        fall=fall+"\n"+inse_return;
                                       // System.out.println(fall);
                                        return fall;

                                }catch(Exception e)
                                {

                                }  
                                finally
                                {
                                     if (asocket != null)
                                     {
                                          	asocket.close();
                                     }
                                }
                             }

                           }
                      }
                      else
                      {
                            for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                            {     
                                 int check=0;
                                 String fall="";
                                 String P_Key=pair.getKey();
                                 if(P_Key.equalsIgnoreCase("Fall"))
                                 {
                                     HashMap<String,Integer> map =pair.getValue();
                                     fall="\n _______Fall_______\n";
                                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                     {
                                          fall=fall+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                     }
                                     System.out.println("Returning to SOEN from COMP");
                                     return fall;
                                 }
                             }
                     }
                 }
                 if(Semester.trim().toString().equalsIgnoreCase("Winter"))
                 {
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("COMPA"))
                     {
                        for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                        {     
                             int check=0;
                             String winter="";
                             String P_Key=pair.getKey();
                             if(P_Key.equalsIgnoreCase("Winter"))
                             {
                                 HashMap<String,Integer> map =pair.getValue();
                                 winter="\n _______Winter_______\n";
                                 for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                 {
                                      winter=winter+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                 }
                                 DatagramSocket asocket=null;
                                 try
                                 {
                                        //SOEN_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,4444);
                                        asocket.send(req);    
                                        System.out.println("Request sent to SOEN");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String soen_return=new String(rep.getData()).trim();
                                        winter=winter+soen_return;
                                        
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,5555);
                                        asocket.send(req2);    
                                        System.out.println("Request sent to INSE");

                                        byte[] buf3=new byte[1000];
                                        DatagramPacket rep2=new DatagramPacket(buf3,buf3.length);
                                        asocket.receive(rep2);
                                        String inse_return=new String(rep2.getData()).trim();
                                        
                                        winter=winter+"\n"+inse_return;
                                        //System.out.println(winter);
                                        return winter;

                                }catch(Exception e)
                                {

                                }
                                finally
                                {
                                       if (asocket != null)
                                       {
                                          	asocket.close();
                                       }
                                } 
                             }

                           }
                      }
                      else
                      {
                            for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                            {     
                                 int check=0;
                                 String winter="";
                                 String P_Key=pair.getKey();
                                 if(P_Key.equalsIgnoreCase("Winter"))
                                 {
                                     HashMap<String,Integer> map =pair.getValue();
                                     winter="\n _______Winter_______\n";
                                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                     {
                                          winter=winter+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                     }
                                     System.out.println("Returning to SOEN from COMP");
                                     return winter;
                                 }
                             }
                     }
                 }
                 if(Semester.trim().toString().equalsIgnoreCase("Summer"))
                 {
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("COMPA"))
                     {
                        for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                        {     
                             String summer="";
                             String P_Key=pair.getKey();
                             if(P_Key.equalsIgnoreCase("Summer"))
                             {
                                 HashMap<String,Integer> map =pair.getValue();
                                 summer="\n _______Summer_______\n";
                                 for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                 {
                                      summer=summer+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                 }
                                 DatagramSocket asocket=null;
                                 try
                                 {
                                        //SOEN_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,4444);
                                        asocket.send(req);    
                                        System.out.println("Request sent to SOEN");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String soen_return=new String(rep.getData()).trim();
                                        summer=summer+soen_return;
                                        
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,5555);
                                        asocket.send(req2);    
                                        System.out.println("Request sent to INSE");

                                        byte[] buf3=new byte[1000];
                                        DatagramPacket rep2=new DatagramPacket(buf3,buf3.length);
                                        asocket.receive(rep2);
                                        String inse_return=new String(rep2.getData()).trim();
                                       
                                        summer=summer+"\n"+inse_return;
                                        //System.out.println(summer);
                                        return summer;

                                }catch(Exception e)
                                {

                                }
                                finally
                                {
                                       if (asocket != null)
                                       {
                                          	asocket.close();
                                       }
                                } 
                             }

                           }
                      }
                      else
                      {
                            for(Map.Entry<String,HashMap<String,Integer>> pair: Database.entrySet())
                            {     
                                 int check=0;
                                 String summer="";
                                 String P_Key=pair.getKey();
                                 if(P_Key.equalsIgnoreCase("Summer"))
                                 {
                                     HashMap<String,Integer> map =pair.getValue();
                                     summer="\n _______Summer_______\n";
                                     for(Map.Entry<String, Integer> pair1 : map.entrySet())
                                     {
                                          summer=summer+"Course: "+pair1.getKey()+" Capacity: "+String.valueOf(pair1.getValue())+"\n";
                                     }
                                     System.out.println("Returning to SOEN from COMP");
                                     return summer;
                                 }
                             }
                     }                     
                 }
           return r; 
            
        }
     
        
    public static void main(String args[])
    {
    	int crashCounter; 
    	ArrayList<String> queue= new ArrayList<String>() ;
        try
        { 
          
   	      COMP_Server cs=new COMP_Server();
   	      System.out.println("COMP Server Up & Running"); 
   	      
   	      
   	      //SOEN Receive
   	      Runnable task = () ->
                {
                    DatagramSocket aSocket=null;
                    while(true)
                    { 
                        try
                        {  
                            aSocket= new DatagramSocket(2222);
                            byte[] buffer=new byte[1000]; 
                            DatagramPacket Reply=new DatagramPacket(buffer,buffer.length);
                            
                            aSocket.receive(Reply);
                            String rec=new String(Reply.getData());
                            System.out.println("\nRequest "+rec);
                             
                            String[] splited = rec.split("\\s+");
                           
                            System.out.println("Operation "+splited[0]);
                            if(splited[0].equalsIgnoreCase("Enroll"))
                            {
                                String ret=cs.enrollCourse(splited[1],splited[2],splited[3]);
                                System.out.println(splited[2]+" Enroll: "+ret);
                                byte[] temp=new byte[1000];
                                temp=ret.getBytes();
                                DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                aSocket.send(se);
                              
                            }
                           if(splited[0].equalsIgnoreCase("Drop"))
                           {
                               String ret=cs.drop_external(splited[1],splited[2]);
                               byte[] temp=new byte[1000];
                               temp=ret.getBytes();
                               DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                               aSocket.send(se);
                               aSocket.close();
                             
                            }
                            if(splited[0].equalsIgnoreCase("list"))
                            {
                                String ret=cs.listCourseAvailability(splited[1],splited[2]);
                                byte[] temp=new byte[1000];
                                temp=ret.getBytes();
                                DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                aSocket.send(se);
                              
                             }
                             if(splited[0].equalsIgnoreCase("Remove"))
                             {
                                String ret=cs.removeCourse(splited[1],splited[2]);
                                byte[] temp=new byte[1000];
                                temp=ret.getBytes();
                                DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                aSocket.send(se);
                               
                             }
                            
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                        finally
                        {
                           if (aSocket != null)
                            {
                                aSocket.close();
                            }
                        }
                      }
                  };
                  
         	      //INSE Receive
                  Runnable task2 = () ->
                  {
                      DatagramSocket aSocket=null;
                      while(true)
                      { 
                          try
                          {  
                              aSocket= new DatagramSocket(3333);
                              byte[] buffer=new byte[1000]; 
                              DatagramPacket Reply=new DatagramPacket(buffer,buffer.length);
                              
                              aSocket.receive(Reply);
                              String rec=new String(Reply.getData());
                              System.out.println("\nRequest "+rec);
                               
                              String[] splited = rec.split("\\s+");
                             
                              System.out.println("Operation "+splited[0]);
                              if(splited[0].equalsIgnoreCase("Enroll"))
                              {
                              	  String ret=cs.enrollCourse(splited[1],splited[2],splited[3]);
                              	  
                                  System.out.println(splited[2]+" Enroll: "+ret);
                              	  byte[] temp=new byte[1000];
                                  temp=ret.getBytes();
                                  DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                  aSocket.send(se);
                                
                              }
                             if(splited[0].equalsIgnoreCase("Drop"))
                             {
                                 String ret=cs.drop_external(splited[1],splited[2]);
                                 System.out.println(ret);  
                                 byte[] temp=new byte[1000];
                                 temp=ret.getBytes();
                                 DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                 aSocket.send(se);
                                 aSocket.close();
                               
                              }
                              if(splited[0].equalsIgnoreCase("list"))
                              {
                                  String ret=cs.listCourseAvailability(splited[1],splited[2]);
                                  byte[] temp=new byte[1000];
                                  temp=ret.getBytes();
                                  DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                  aSocket.send(se);
                                
                               }
                               if(splited[0].equalsIgnoreCase("Remove"))
                               {
                                  String ret=cs.removeCourse(splited[1],splited[2]);
                                  byte[] temp=new byte[1000];
                                  temp=ret.getBytes();
                                  DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                  aSocket.send(se);
                                 
                               }
                              
                          }catch(Exception e)
                          {
                              System.out.println(e.getMessage());
                          }
                          finally
                          {
                             if (aSocket != null)
                              {
                                  aSocket.close();
                              }
                          }
                        }
                  };
                  
     
      	 Runnable task3 =()->
         {
       	  MulticastSocket ms1=null;
       	  DatagramSocket ds1=null;
       	  while(true)
       	  {
       		  System.out.println("Receiving Replica Message and sending response to FE");
       		  try
       		  {
    					ms1=new MulticastSocket(1700);
    					ds1=new DatagramSocket(6001);
    					InetAddress ip=InetAddress.getByName("230.0.0.1");
    					ms1.joinGroup(ip);
    					byte []buf=new byte[1000];
    					String msg="";
    					String reply="";
    					DatagramPacket dp1=new DatagramPacket(buf,buf.length);
    					ms1.receive(dp1);
    					msg=new String(dp1.getData());
    					cs.msg1=msg;
    					
    					queue.add(msg.trim());
    					System.out.println();
    					System.out.println(msg);
    					StringTokenizer st=new StringTokenizer(msg,",");
    					cs.unique_id=Integer.parseInt(st.nextToken());
    					String id=st.nextToken();
    					String method=st.nextToken();
    					
    					if(method.equals("Enrol"))
    					{
    						if(cs.crashCounter==2)
    						{
	    						System.out.println("Server Crashed\n");
    						}
    						else
    						{
    							String courseId=st.nextToken();
	    						String sem=st.nextToken();
	    						reply =cs.enrollCourse(id.trim(), courseId.trim(), sem.trim());
	    						System.out.println(reply);
	    						//reply="C2"+reply;
	    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	    						ds1.send(dp1);
	    						cs.crashCounter++;
    						}
	    				}
    					else if(method.equals("Drop"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						
    						reply=cs.dropCourse(id.trim(),courseId.trim(),sem.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
    						ds1.send(dp1);
    					}
    					else if(method.equals("Swap"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						String newCourseId=st.nextToken();
    						reply=cs.swapClasses(id.trim(), sem.trim(), courseId.trim(), newCourseId.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
    						ds1.send(dp1);
    					}
    					else if("Get".equals(method.trim()))
    					{
							reply=cs.getClassShedule(id.trim().toString());
							System.out.println(reply);
							InetAddress ip1=InetAddress.getByName("132.205.93.58");
							dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
							ds1.send(dp1);
    					}
    					else if(method.equals("Add"))
    					{
    						String courseId=st.nextToken();
    						String sem=st.nextToken();
    						int cap=Integer.parseInt(st.nextToken().trim());
    						reply=cs.addCourse(id.trim(), courseId.trim(), sem.trim(), cap);
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
    						ds1.send(dp1);
    					}
    					else if(method.equals("Remove"))
    					{
							String courseId=st.nextToken();
    						String sem=st.nextToken();
    						reply=cs.removeCourse(courseId.trim(), sem.trim());
    						System.out.println(reply);
    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
    						ds1.send(dp1);
    					}
    					else if(method.equals("List"))
    					{
    						String sem=st.nextToken();
    						reply=cs.listCourseAvailability(id.trim(), sem.trim());
    						System.out.println(reply);	
    						InetAddress ip1=InetAddress.getByName("132.205.93.58");
    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
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
         };
         
         
         
         Runnable task4 =()->
         {
        	 DatagramSocket ds2=null;
        	 while(true)
        	 {
        		 System.out.println("Receiving crash Message from FE");
        		 try
        		 {
    				ds2=new DatagramSocket(2000);
    				byte buf[]=new byte[1000];
    				DatagramPacket dp2=new DatagramPacket(buf,buf.length);
    				ds2.receive(dp2);
    				System.out.println("Message From FE:"+new String(dp2.getData()));
					cs.reset(queue);
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.print("Thread 3: "+e.getMessage());
        		 }
        		 finally
        		 {
        			 if(ds2!=null)
        			 {
        				 ds2.close();
        			 }
        		 }
        	 }
         };
         
         Thread t=new Thread(task);
         Thread t2=new Thread(task2);
         Thread t3=new Thread(task3);
         Thread t4=new Thread(task4);    
         
         t.start();
         t2.start();
         t3.start();
         t4.start();
         
        }catch(Exception e){};
  

        }



            
}
