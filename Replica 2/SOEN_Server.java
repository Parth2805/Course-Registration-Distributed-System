package Main;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class SOEN_Server implements SA_Interface
{
	static int unique_id=0;
    Map<String, ArrayList<String>> Soen_Fall= null;
    Map<String, ArrayList<String>> Soen_Winter= null;        
    Map<String, ArrayList<String>> Soen_Summer= null;
    Logger log=null;
    FileHandler file;
    
    Map<String,HashMap<String,Integer>> Database=null;
    HashMap<String,Integer> Fall=null;
    HashMap<String,Integer> Winter=null;
    HashMap<String,Integer> Summer=null;
    ArrayList<String> Advisor = null;
    Map<String, List<Integer>> Counter=null; 
    private final Object lockA ;
    
    
        SOEN_Server()
        {
        	lockA = new Object();
            Soen_Fall= new HashMap<>();
            Soen_Winter= new HashMap<>();
            Soen_Summer= new HashMap<>();
            
            Database=new HashMap<>();
            Fall=new HashMap<>();
            Winter=new HashMap<>();
            Summer=new HashMap<>();
            Counter= new HashMap<>();
            
            try
            {
                log=Logger.getLogger("SOEN Server");
                file=new FileHandler("G:/Logs/SOEN_Server.log",true);
                log.addHandler(file);
                log.setUseParentHandlers(false);
                SimpleFormatter ft=new SimpleFormatter();
                file.setFormatter(ft);
            
            }catch(IOException e){e.printStackTrace();}
            
            Advisor = new ArrayList<String>();
            Advisor.add("SOENA1111");
            Advisor.add("SOENA2222");
            Advisor.add("SOENA3333");

            Fall.put("SOEN6001",3); //for Multithreading purpose
      	    Fall.put("SOEN6002",3);
            Fall.put("SOEN6003",3);
            Database.put("Fall",Fall);
		
            Winter.put("SOEN6004",3);
	        Winter.put("SOEN6005",3);
            Winter.put("SOEN6001",3);
            Database.put("Winter",Winter);
		
            Summer.put("SOEN6006",3);
       	    Summer.put("SOEN6007",3);
            Summer.put("SOEN6002",3);
	        Database.put("Summer",Summer);            
        
        }
        
  
        String lookfor(String CourseID,String Semester)
        {
            if(Semester.trim().toString().equalsIgnoreCase("Fall"))
            {
                String removed="";
                for (Map.Entry<String, ArrayList<String>> entry : Soen_Fall.entrySet()) 
                {
                     if(entry.getValue().contains(CourseID.trim().toString()))
                     {
                         Soen_Fall.get(entry.getKey()).remove(CourseID.trim().toString());                        
                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("SOEN"))
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
                for (Map.Entry<String, ArrayList<String>> entry : Soen_Winter.entrySet()) 
                {
                     if(entry.getValue().contains(CourseID))
                     {
                         Soen_Winter.get(entry.getKey()).remove(CourseID.trim().toString());
                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("SOEN"))
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
                for (Map.Entry<String, ArrayList<String>> entry : Soen_Summer.entrySet()) 
                {
                     if(entry.getValue().contains(CourseID))
                     {
                         Soen_Summer.get(entry.getKey()).remove(CourseID.trim().toString());
                         if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("SOEN"))
                         {
                            Counter.get(entry.getKey()).set(4,Counter.get(entry.getKey()).get(4)+1); 
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
           for(Map.Entry<String,ArrayList<String>> pair: Soen_Fall.entrySet())
          {
              if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
              {
                  return true;
              }
          }
          for(Map.Entry<String,ArrayList<String>> pair: Soen_Winter.entrySet())
          {
              if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
              {
                  return true;
              }
          }    
          for(Map.Entry<String,ArrayList<String>> pair: Soen_Summer.entrySet())
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
        		 for(Map.Entry<String,ArrayList<String>> pair: Soen_Fall.entrySet())
                 {
                     if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                     {
                         return true;
                     }
                 }
            }
            if(Semester.equalsIgnoreCase("Winter"))
            {
            	for(Map.Entry<String,ArrayList<String>> pair: Soen_Winter.entrySet())
                {
                    if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                    {
                        return true;
                    }
                }
            }
            if(Semester.equalsIgnoreCase("Summer"))
            {
            	for(Map.Entry<String,ArrayList<String>> pair: Soen_Summer.entrySet())
                {
                    if(pair.getKey().equals(StudentID)&&pair.getValue().contains(CourseID))
                    {
                        return true;
                    }
                }       
            }
            
           return false;
        }
        
        public String drop_external(String CourseID,String Semester) 
        {
        	synchronized(lockA)
            {
            String r;
            int i=Database.get(Semester.trim().toString()).get(CourseID.trim().toString());
            i=i+1;
            Database.get(Semester.trim().toString()).put(CourseID.trim().toString(),i);
            r="yes";
            System.out.println("Inside Drop");
            return r;
            }
        }
        
        
        public String enrollCourse(String StudentID,String CourseID,String Semester) 
        {   
        	
           synchronized(lockA)
           {
              String r="";                
              try
              {
                if(CourseID.substring(0,4).equalsIgnoreCase("SOEN"))
                {
                    if(StudentID.substring(0,5).equalsIgnoreCase("SOENS"))
                    {       
                                                                
                                if(Semester.equalsIgnoreCase("Fall"))
                                {
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                        //return "You are already enrolled in this course";
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
                                                        Soen_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                                        Soen_Fall.get(StudentID).add(CourseID); 
                                                        Counter.get(StudentID).set(0,Counter.get(StudentID).get(0)+1);
                                                        Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);
                                                        r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                        log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                                        //return r;
                                                        return "EC1";
                                                    }
                                                    else
                                                    {
                                                        r="You have reached maxmium limit of course in this term.";
                                                       // return r;
                                                        return "EC3";
                                                    }                                           
                                            }
                                            else
                                            {
                                                r="Course is full";
                                                //return r;
                                                return "EC4";
                                            }
                                        }
                                        else                         
                                        {
                                           r="Course is not availible in this term";
                                          // return r;     
                                           return "EC2";
                                        }
                        
                                    }
                                }
                                
                                if(Semester.equalsIgnoreCase("Summer"))
                                {
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                      //return "You are already enrolled in this course";
                                      return "EC5";
                                    }
                                    else
                                    {
                                            System.out.println("inSummer");
                                            if(Database.get(Semester).containsKey(CourseID))
                                            {   
                                                int value1=Database.get(Semester).get(CourseID);
                                                if(value1!=0)
                                                { 
                                                        Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                        int i=Counter.get(StudentID).get(4)+Counter.get(StudentID).get(5);
                                                        if(i<3)
                                                        {
                                                            Soen_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                                            Soen_Summer.get(StudentID).add(CourseID); 
                                                            Counter.get(StudentID).set(4,Counter.get(StudentID).get(4)+1);
                                                            Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);
                                                            r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                            log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                                          //  return r;
                                                            return "EC1";
                                                        }
                                                        else
                                                        {
                                                            r="You have reached maxmium limit of course in this term.";
                                                            //return r;
                                                            return "EC3";
                                                        }                                           
                                                }
                                                else
                                                {
                                                    r="Course is full";                                                 
                                                    //return r;
                                                    return "EC4";
                                                }
                                            }
                                            else                         
                                            {
                                               r="Course is not availible in this term";
                                               //return r;
                                               return "EC2";
                                            }
                                    
                                   }
                                }
                                if(Semester.equalsIgnoreCase("Winter"))
                                { 
                                    if(ifExist(StudentID,CourseID,Semester))
                                    {
                                      //return "You are already enrolled in this course";
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
                                                            int i=0;
                                                            i=Counter.get(StudentID).get(2)+Counter.get(StudentID).get(3);
                                                            
                                                            System.out.println(Counter.get(StudentID).get(2)+"  "+Counter.get(StudentID).get(3));
                                                            if(i<3)
                                                            {
                                                                Soen_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                                                Soen_Winter.get(StudentID).add(CourseID); 
                                                                int j=Counter.get(StudentID).get(2);
                                                                Counter.get(StudentID).set(2,j+1);
                                                               // System.out.println("    "+Counter.get(StudentID).get(3));
                                                                Database.get(Semester).put(CourseID,Database.get(Semester).get(CourseID)-1);
                                                                r="You are enrollled in "+CourseID+" Successfully for "+Semester+" ";
                                                                log.log(Level.INFO, "Student with ID: {0} Enrolled in Course {1} For {2}", new Object[]{StudentID, CourseID, Semester});
                                                                //return r;
                                                                return "EC1";
                                                            }
                                                            else
                                                            {
                                                                r="You have reached maxmium limit of course in this term.";
                                                                //return r;
                                                                return "EC3";
                                                            }                                           
                                                    }
                                                    else
                                                    {
                                                        r="Course is full";
                                                        return "EC4";
                                                       // return r;
                                                    }
                                                }
                                                else                         
                                                {
                                                   r="Couse is not availible in this term";
                                                    return "EC2";
                                                   // return r;                                                  
                                                }
                                      
                                    } 
                                }
                       
                    }
                    else
                    {
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
                                           //return "You are Enrolled Successfully";
                                           return "yes";
                                     } 
                            	 }
                            	 else
                            	 {
                            		 return "EC2";
                            	 }
                                 
                             }catch(Exception e)
                             {
                                 System.out.println(e.getMessage());
                             }
                    }
                }
                    if(CourseID.substring(0,4).equalsIgnoreCase("COMP"))
                    {
                            DatagramSocket asocket=new DatagramSocket();
                            InetAddress aHost=InetAddress.getByName("localhost");
                            if(ifExist(StudentID,CourseID,Semester))
                            {
                                //return "You are already enrolled in this course";
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
                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,2222);
                                            asocket.send(req);

                                            System.out.println("Sent to COMP Server for enroll");
                                            byte[] buffer=new byte[100];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Fall.get(StudentID).add(CourseID);  
                                                // int j=Counter.get(StudentID).get(0)+Counter.get(StudentID).get(1);
                                                 Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)+1);
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
                                           // return r;
                                            return "EC7";
                                        }
                                    } 
                                    else
                                    {
                                        r="You have reached your maximum limit of Subs in this semester";
                                        //return r;
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
                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,2222);
                                            asocket.send(req);

                                            System.out.println("Sent to COMP Server for Enroll");
                                            byte[] buffer=new byte[100];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Summer.get(StudentID).add(CourseID);  
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
                                             //return r;
                                             return "EC7";
                                         }
                                    }
                                    else
                                    {
                                        r="You have reached your maximum limit of Subs in this semester";
                                        //return r;
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
                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,2222);
                                            asocket.send(req);

                                            System.out.println("Sent to SOEN Server");
                                            byte[] buffer=new byte[100];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Winter.get(StudentID).add(CourseID);  
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
                                             //return r;
                                             return "EC7";
                                         }
                                    }
                                    else
                                    {
                                       r="You have reached your maximum limit of Subs in this semester";
                                       //return r;
                                       return "EC3";
                                    }    
                                }   
                            }
                    }
                    if(CourseID.substring(0,4).equalsIgnoreCase("INSE"))
                    {
                        DatagramSocket asocket=null;
                        asocket=new DatagramSocket();
                        InetAddress aHost=InetAddress.getByName("localhost");
                        if(ifExist(StudentID,CourseID,Semester))
                        {
                            //return "You are already enrolled in this course";
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
                                            System.out.println(s);
                                            byte[] reply=s.getBytes();

                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,7777);
                                            asocket.send(req);
                                            System.out.println("Sent to INSE Server for enroll");

                                            byte[] buffer=new byte[10000];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Fall.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Fall.get(StudentID).add(CourseID);  
                                                 //int j=Counter.get(StudentID).get(0)+Counter.get(StudentID).get(1);
                                                 Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)+1);
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
                                            // return r;
                                             return "EC7";
                                         }
                                    }
                                    else
                                    {
                                         r="You have reached your maximum limit of Subs in this semester";
                                         //return r;
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
                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,7777);
                                            asocket.send(req);

                                            System.out.println("Sent to INSE Server for Enroll");
                                            byte[] buffer=new byte[100];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Summer.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Summer.get(StudentID).add(CourseID);  
                                               //  int j=Counter.get(StudentID).get(4)+Counter.get(StudentID).get(5);
                                                 Counter.get(StudentID).set(5,Counter.get(StudentID).get(5)+1);
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
                                            // return r;
                                             return "EC7";
                                         }
                                    }else
                                    {
                                        r="You have reached your maximum limit of Subs in this semester";
                                      //  return r;   
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
                                            DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,7777);
                                            asocket.send(req);

                                            System.out.println("Sent to INSE Server for Enroll");
                                            byte[] buffer=new byte[100];
                                            DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                                            asocket.receive(Rec);
                                            String temp=new String(Rec.getData());

                                            if("yes".equalsIgnoreCase(temp.trim().toString()))
                                            {
                                                 Counter.putIfAbsent(StudentID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0)));
                                                 Soen_Winter.putIfAbsent(StudentID,new ArrayList<>());
                                                 Soen_Winter.get(StudentID).add(CourseID);  
                                                 Counter.get(StudentID).set(3,Counter.get(StudentID).get(3)+1);
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
                                            //return r;
                                            return "EC7";
                                         }
                                    }
                                    else
                                    {
                                        r="You have reached your maximum limit of Subs in this semester";
                                      //  return r;   
                                        return "EC3";
                                    }    
                            }   
                        }
                    }
              	  
                }catch(Exception e)
                {
              	  
                }      
            return r;
           }
        }
        
        
		public String getClassShedule(String StudentID)
		{	
				//	            String r;  
				//	            r="";
				//	         
				//	            if(Soen_Fall.get(StudentID)!=null)
				//	            {
				//	                r=r+"Fall: "+Soen_Fall.get(StudentID);
				//	            }
				//	            if(Soen_Winter.get(StudentID)!=null)
				//	            {
				//	              
				//	                r=r+"Winter: "+Soen_Winter.get(StudentID);
				//	            }
				//	            if(Soen_Summer.get(StudentID)!=null)
				//	            {
				//	                r=r+"Summer: "+Soen_Summer.get(StudentID);
				//	            }
				//	           
				//	            System.out.println(r);
				//	            
				//	            return r;
				//	            
	            System.out.println("SOEN Get Schedule");
	            
	            String msg="Course\tSemester\n"+Soen_Fall.get(StudentID)
	                    +"\tFall"+"\n"+Soen_Winter.get(StudentID)
	                    +"\tWinter"+"\n"+Soen_Summer.get(StudentID)
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
            if(CourseID.substring(0, 4).equalsIgnoreCase("SOEN"))
            {    
                if(ifExist_drop(StudentID,CourseID,Semester))
                {  
                        if(Semester.equalsIgnoreCase("Fall"))
                        {
                                  Soen_Fall.get(StudentID).remove(CourseID);
                                  Counter.get(StudentID).set(0,Counter.get(StudentID).get(0)-1);
                                  Database.get(Semester).put(CourseID, Database.get(Semester).get(CourseID)+1);
                                  System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                  log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                  //return "Course "+CourseID+" Dropped Successfully";
                                  return "DC1";
                        } 
                        if(Semester.equalsIgnoreCase("Winter"))
                        {      
                                   Soen_Winter.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(2,Counter.get(StudentID).get(2)-1);
                                   Database.get(Semester).put(CourseID, Database.get(Semester).get(CourseID)+1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                   //return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                        if(Semester.equalsIgnoreCase("Summer"))
                        {
                                   Soen_Summer.get(StudentID).remove(CourseID);
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
                    //return r;
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
                    if(CourseID.substring(0, 4).equalsIgnoreCase("COMP"))
                    {
                        DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,2222);
                        asocket.send(req);      
                    }
                    else if(CourseID.substring(0, 4).equalsIgnoreCase("INSE"))
                    {
                        DatagramPacket req=new DatagramPacket(reply,reply.length,aHost,7777);
                        asocket.send(req);
                    }
                    
                    byte[] buffer=new byte[1000];
                    DatagramPacket Rec=new DatagramPacket(buffer,buffer.length);
                    asocket.receive(Rec);
                    
                    String temp=new String(Rec.getData());
                    
                    if("yes".equalsIgnoreCase(temp.trim().toString()))
                    {
                        if(Semester.equalsIgnoreCase("Fall"))
                        {
                                  
                                  Soen_Fall.get(StudentID).remove(CourseID);
                                  Counter.get(StudentID).set(1,Counter.get(StudentID).get(1)-1);
                                  System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                  log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                  //return "Course "+CourseID+" Dropped Successfully";
                                  return "DC1";
                        } 
                        if(Semester.equalsIgnoreCase("Winter"))
                        {      
                                   Soen_Winter.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(3,Counter.get(StudentID).get(3)-1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                  // return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                        if(Semester.equalsIgnoreCase("Summer"))
                        {
                                   Soen_Summer.get(StudentID).remove(CourseID);
                                   Counter.get(StudentID).set(5,Counter.get(StudentID).get(5)-1);
                                   System.out.println(CourseID+" Dropped by "+StudentID+"-Capacity Remaining -> "+Database.get(Semester).get(CourseID));
                                   log.log(Level.INFO, "Student with ID: {0} Dropped Course: {1} From term {2}", new Object[]{StudentID, CourseID, Semester});
                                   //return "Course "+CourseID+" Dropped Successfully";
                                   return "DC1";
                        }
                    }
                    else
                    {
                       return new String(Rec.getData());    
                    }
                }
                else
                {
                   // return "You are not enrolled in this Course for this term";
                    return "DC2";
                }
            }
            }
            catch(Exception e){}        
            return r;
          } 
        }
        
        @Override
		public String swapClasses(String StudentID, String Semester, String CourseID, String NewCourseID)
        {
          synchronized(lockA)
          {
        	/*
        	if(CourseID.substring(0, 4).equalsIgnoreCase("SOEN") && !NewCourseID.substring(0, 4).equalsIgnoreCase("SOEN"))
    		{
	    			if(Semester.equalsIgnoreCase("Fall"))
					{
						if(Counter.get(StudentID).get(1)<2)
						{
							String drop=dropCourse(StudentID, CourseID, Semester);
							String compare_drop="Course "+CourseID+" Dropped Successfully";
							if(drop.equalsIgnoreCase(compare_drop))
							{
								String result= enrollCourse(StudentID, NewCourseID, Semester);
	    						String compare="You are enrollled in "+NewCourseID+" Successfully for "+Semester+" ";
	    						if(result.equalsIgnoreCase(compare))
	    						{
	    							String r="Swapping from "+CourseID+" to "+NewCourseID+" Successful";
	    							log.log(Level.INFO, "Student with ID: {0} Swapped Course {1} to {2}", new Object[]{StudentID, CourseID, NewCourseID});
	    							return r;
	    						}
	    						else
	    						{
	    							enrollCourse(StudentID, CourseID, Semester);
	    							return "Swap Failed, Unable to enroll in desired course";
	    						}
							}
							else
							{
								return "Swap Failed, Unable to drop from current course";	
							}
							
						}
						else
						{
							return "Swap not possible becoz you are already enrolled in 2 subjects from other department";
						}
						
				    }
					if(Semester.equalsIgnoreCase("Winter"))
					{
						if(Counter.get(StudentID).get(3)<2)
						{
							String drop=dropCourse(StudentID, CourseID, Semester);
							String compare_drop="Course "+CourseID+" Dropped Successfully";
							if(drop.equalsIgnoreCase(compare_drop))
							{
								String result= enrollCourse(StudentID, NewCourseID, Semester);
	    						String compare="You are enrollled in "+NewCourseID+" Successfully for "+Semester+" ";
	    						if(result.equalsIgnoreCase(compare))
	    						{
	    							String r="Swapping from "+CourseID+" to "+NewCourseID+" Successful";
	    							log.log(Level.INFO, "Student with ID: {0} Swapped Course {1} to {2}", new Object[]{StudentID, CourseID, NewCourseID});
	    							return r;
	    						}
	    						else
	    						{
	    							enrollCourse(StudentID, CourseID, Semester);
	    							return "Swap Failed, Unable to enroll in desired course";
	    						}
							}
							else
							{
								return "Swap Failed, Unable to drop from current course";
							}
							
						}
						else
						{
							return "Swap not possible becoz you are already enrolled in 2 subjects from other department";
						}
						
					}
					if(Semester.equalsIgnoreCase("Summer"))
					{
						if(Counter.get(StudentID).get(5)<2)
						{
							String drop=dropCourse(StudentID, CourseID, Semester);
							String compare_drop="Course "+CourseID+" Dropped Successfully";
							if(drop.equalsIgnoreCase(compare_drop))
							{
								String result= enrollCourse(StudentID, NewCourseID, Semester);
	    						String compare="You are enrollled in "+NewCourseID+" Successfully for "+Semester+" ";
	    						if(result.equalsIgnoreCase(compare))
	    						{
	    							String r="Swapping from "+CourseID+" to "+NewCourseID+" Successful";
	    							log.log(Level.INFO, "Student with ID: {0} Swapped Course {1} to {2}", new Object[]{StudentID, CourseID, NewCourseID});
	    							return r;
	    						}
	    						else
	    						{
	    							enrollCourse(StudentID, CourseID, Semester);
	        						return "Swap Failed, Unable to enroll in desired course";
	    						}
							}
							else
							{
								return "Swap Failed, Unable to drop from current course";
							}
						
						}
						else
						{
							return "Swap not possible becoz you are already enrolled in 2 subjects from other department";	
						}
						
					}
    		   }
    		   else
    		   {*/
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
							//return "Swap Failed, Unable to enroll in desired course because "+result;
							return result;
						}
					}
					else
					{
						//enrollCourse(StudentID, CourseID, Semester);
						//return "Swap Failed, Unable to drop from current course because "+drop;	
						return drop;
					}
    		    //}
        	//return "swap fail";
          }	
		}
	
        
        public String addCourse(String AdvisorID,String CourseID,String Semester,int Capacity)
        {
            if(Advisor.contains(AdvisorID))
            {
                if(Database.get(Semester).containsKey(CourseID))
                {
                    //return "Course Already Exist in this Semester";
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
                //return "You are not Registered Advisor";
                return "AC3";
            }
        }   
        
        public String removeCourse(String CourseID,String Semester)
        {
             String r="";
             if(CourseID.substring(0, 4).equalsIgnoreCase("SOEN"))
             {
                 String result=lookfor(CourseID,Semester);
                 if(Database.get(Semester).containsKey(CourseID))
                 {
                 if(Database.get(Semester).remove(CourseID,Database.get(Semester).get(CourseID)))
                 {
                	 r="\n Course Successfully Removed \n"+"\n__Records Affected__\n"+result;
                     log.log(Level.INFO, "Course {0} removed from {1}", new Object[]{CourseID, Semester});
                     DatagramSocket asocket=null;
                                 try
                                 {
                                        //COMP_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="Remove"+" "+CourseID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,2222);
                                        asocket.send(req);    
                                        System.out.println("Request sent to SOEN");

//                                        byte[] buf1=new byte[1000];
//                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
//                                        asocket.receive(rep);
//                                        String comp_return=new String(rep.getData());
//                                        r=r+comp_return;
                                        
                                        //INSE_SEND_RECEIVE
                                        String request2="Remove"+" "+CourseID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,7777);
                                        asocket.send(req2);    
                                        System.out.println("Request sent to INSE");

//                                        byte[] buf3=new byte[1000];
//                                        DatagramPacket rep2=new DatagramPacket(buf3,buf3.length);
//                                        asocket.receive(rep2);
//                                        String inse_return=new String(rep2.getData());
//                                        r=r+inse_return;
                                     
                                 }catch(Exception e)
                                 {
                                     
                                 }finally
                                 {
                                    if (asocket != null)
                                     {
                                          	asocket.close();
                                     }  
                                 }  
                 }        
                 //return r;  
                 return "RC1";
                 }
                 else
                 {
                	 return "RC2";
                 }
             }
             if(CourseID.trim().toString().substring(0,4).equalsIgnoreCase("COMP") || CourseID.trim().toString().substring(0,4).equalsIgnoreCase("INSE"))
             {
                System.out.println("Inside RemoveCourse");
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
                     System.out.println("Inside Fall");
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("SOENA"))
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
                                      //COMP_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,2222);
                                        asocket.send(req);    
                                        System.out.println("Request sent to COMP");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String comp_return=new String(rep.getData()).trim();
                                        fall=fall+comp_return;
                                       
                                     
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,7777);
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
                                    return e.getMessage();
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
                            System.out.println("Inside comp/inse part of SOEN server");
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
                                     return fall;
                                 }
                             }
                     }
                 }
                 if(Semester.trim().toString().equalsIgnoreCase("Winter"))
                 {
                     System.out.println("Inside Winter");
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("SOENA"))
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
                                      //COMP_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,2222);
                                        asocket.send(req);    
                                        System.out.println("Request sent to COMP");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String comp_return=new String(rep.getData()).trim();
                                        winter=winter+comp_return;
                                        
                                     
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,7777);
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
                                    return e.getMessage();
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
                            System.out.println("Inside comp/inse part of SOEN server");
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
                                     return winter;
                                 }
                             }
                     }
                 }
                 if(Semester.trim().toString().equalsIgnoreCase("Summer"))
                 {
                                        
                     if(AdvisorID.substring(0,5).equalsIgnoreCase("SOENA"))
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
                                 
                                 DatagramSocket asocket=null;
                                 try
                                 {
                                      //COMP_SEND_RECEIVE
                                        asocket=new DatagramSocket();
                                        InetAddress aHost=InetAddress.getByName("localhost");
                                        String request="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf=new byte[1000];
                                        buf=request.getBytes();
                                        DatagramPacket req=new DatagramPacket(buf,buf.length,aHost,2222);
                                        asocket.send(req);    
                                        System.out.println("Request sent to COMP");

                                        byte[] buf1=new byte[1000];
                                        DatagramPacket rep=new DatagramPacket(buf1,buf1.length);
                                        asocket.receive(rep);
                                        String comp_return=new String(rep.getData()).trim();
                                        summer=summer+comp_return;
                                       
                                     
                                        //INSE_SEND_RECEIVE
                                        String request2="list"+" "+AdvisorID+" "+Semester;
                                        byte[] buf2=new byte[1000];
                                        buf2=request2.getBytes();
                                        DatagramPacket req2=new DatagramPacket(buf2,buf2.length,aHost,7777);
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
                                    return e.getMessage();
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
                                     return summer;
                                 }
                             }
                     }  
                 }
           return r;                    
        }
        
        
        public static void main(String args[])
        {
            
            try
            {           
                  SOEN_Server soen= new SOEN_Server();
                  
        	      System.out.println("SOEN Server Up and Running");

        	      //COMP Receive
        	      Runnable task = () ->
                    {
                        DatagramSocket aSocket=null;
                        while(true)
                        { 
                            try
                            {  
                                    aSocket= new DatagramSocket(4444);
                                    byte[] buffer=new byte[1000]; 
                                    DatagramPacket Reply=new DatagramPacket(buffer,buffer.length);
                                    aSocket.receive(Reply);
                                    String rec=new String(Reply.getData());
                                    String[] splited=rec.split("\\s+");
                                   
                                    if(splited[0].equalsIgnoreCase("Enroll"))
                                    {   
                                        String ret=soen.enrollCourse(splited[1],splited[2],splited[3]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                    }

                                    if(splited[0].equalsIgnoreCase("Drop"))
                                    {
                                        String ret=soen.drop_external(splited[1],splited[2]);
                                        System.out.println("Inside drop"+ ret);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                   
                                     }

                                    if(splited[0].equalsIgnoreCase("list"))
                                    {
                                        String ret=soen.listCourseAvailability(splited[1],splited[2]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                     }
                                    if(splited[0].equalsIgnoreCase("Remove"))
                                    {
                                        String ret=soen.removeCourse(splited[1],splited[2]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                     }
                                    

                            }catch(Exception e)
                            {

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
                                    aSocket= new DatagramSocket(6666);
                                    byte[] buffer=new byte[1000]; 
                                    DatagramPacket Reply=new DatagramPacket(buffer,buffer.length);
                                    aSocket.receive(Reply);
                                    String rec=new String(Reply.getData());
                                    String[] splited=rec.split("\\s+");
                                   
                                    if(splited[0].equalsIgnoreCase("Enroll"))
                                    {   
                                        String ret=soen.enrollCourse(splited[1],splited[2],splited[3]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                    }

                                    if(splited[0].equalsIgnoreCase("Drop"))
                                    {
                                        String ret=soen.drop_external(splited[1],splited[2]);
                                        System.out.println("Inside drop"+ ret);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                   
                                     }

                                    if(splited[0].equalsIgnoreCase("list"))
                                    {
                                        String ret=soen.listCourseAvailability(splited[1],splited[2]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                     }
                                    if(splited[0].equalsIgnoreCase("Remove"))
                                    {
                                        String ret=soen.removeCourse(splited[1],splited[2]);
                                        byte[] temp=new byte[1000];
                                        temp=ret.getBytes();
                                        DatagramPacket se=new DatagramPacket(temp,temp.length,Reply.getAddress(),Reply.getPort());
                                        aSocket.send(se);
                                     }
                                    

                            }catch(Exception e)
                            {

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
	            					ds1=new DatagramSocket(6002);
	            					InetAddress ip=InetAddress.getByName("230.0.0.2");
	            					ms1.joinGroup(ip);
	            					byte []buf=new byte[1000];
	            					String msg="";
	            					String reply="";
	            					DatagramPacket dp1=new DatagramPacket(buf,buf.length);
	            					ms1.receive(dp1);
	            					msg=new String(dp1.getData());
	            					System.out.println();
	            					System.out.println(msg);
	            					StringTokenizer st=new StringTokenizer(msg,",");
	            					soen.unique_id=Integer.parseInt(st.nextToken());
	            					String id=st.nextToken();
	            					String method=st.nextToken();
	            					
	            					if(method.equals("Enrol"))
	            					{
	            						String courseId=st.nextToken();
	            						String sem=st.nextToken();
	            						reply = soen.enrollCourse(id.trim(), courseId.trim(), sem.trim());
	            						System.out.println(reply);
	            						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	            						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	            						ds1.send(dp1);
	            					}
	            					else if(method.equals("Drop"))
	            					{
	            						String courseId=st.nextToken();
	            						String sem=st.nextToken();
	            						System.out.println(msg);
	            						reply=soen.dropCourse(id.trim(),courseId.trim(),sem.trim());
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
	            						reply=soen.swapClasses(id.trim(), sem.trim(), courseId.trim(), newCourseId.trim());
	            						System.out.println(reply);
	            						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	            						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	            						ds1.send(dp1);
	            					}
	            					else if("Get".equals(method.trim()))
	            					{
	            						reply=soen.getClassShedule(id);
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
	            						reply=soen.addCourse(id.trim(), courseId.trim(), sem.trim(), cap);
	            						System.out.println(reply);
	            						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	            						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	            						ds1.send(dp1);
	            					}
	            					else if(method.equals("Remove"))
	            					{
	            						String courseId=st.nextToken();
	            						String sem=st.nextToken();
	            						reply=soen.removeCourse(courseId.trim(), sem.trim());
	            						System.out.println(reply);
	            						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	            						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	            						ds1.send(dp1);
	            					}
	            					
	            					else if(method.equals("List"))
	            					{
	            						String sem=st.nextToken();
	            						reply=soen.listCourseAvailability(id.trim(), sem.trim());
	            						System.out.println(reply);
	            						InetAddress ip1=InetAddress.getByName("132.205.93.58");
	            						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7003);
	            						ds1.send(dp1);
	            					}	
	               		  }
	               		  catch(Exception e)
	               		  {
	               			  System.out.println("Thread Exception1111: "+e.getMessage());
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
               
                    
                    
                    
               Thread t=new Thread(task);
               t.start();
               
               Thread t2=new Thread(task2);
               t2.start();
               
               Thread t3=new Thread(task3);
               t3.start();  
               
             

            }catch(Exception e){System.out.println(e.getMessage());}
      

         }

}
