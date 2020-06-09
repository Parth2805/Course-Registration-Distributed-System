package Servers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CompServer
{
	static int unique_id=0;
	static int flag1=0;
	Map<String,HashMap<String,Integer>> outer=new HashMap<String,HashMap<String,Integer>>();	
	HashMap<String,Integer> fall=new HashMap<String,Integer>();
	HashMap<String,Integer> winter=new HashMap<String,Integer>();
	HashMap<String,Integer> summer=new HashMap<String,Integer>();
	
	HashMap<String,ArrayList<Integer>> studCourseCount=new HashMap<String,ArrayList<Integer>>();
	HashMap<String,ArrayList<String>> studFall=new HashMap<String, ArrayList<String>>();
	HashMap<String,ArrayList<String>> studWin=new HashMap<String, ArrayList<String>>();
	HashMap<String,ArrayList<String>> studSum=new HashMap<String, ArrayList<String>>();
	
	Logger logger=Logger.getLogger("COMP Server Logs");
	FileHandler fh;
	
	ArrayList<String> advisor=new ArrayList<String>() {{
		add("COMPA1111");
		add("COMPS2222");
		add("COMPS3333");
	}};

	private final Object lock;
	CompServer()
	{
		fall.put("COMP7001",3);
		fall.put("COMP7002",3);
		fall.put("COMP7003",3);
		outer.put("Fall",fall);
		
		winter.put("COMP7004",3);
		winter.put("COMP7005",3);
		winter.put("COMP7001",3);
		outer.put("Winter",winter);
		
		summer.put("COMP7006",3);
		summer.put("COMP7007",3);
		summer.put("COMP7002",3);
		outer.put("Summer",summer);
		
		lock=new Object();
		try 
		{
			fh=new FileHandler("G:/Server/COMPLogFile.log",true);
			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			SimpleFormatter sf=new SimpleFormatter();
			fh.setFormatter(sf);
			logger.info("Logs Initiated");
			
			for(Map.Entry<String,HashMap<String,Integer>> pair: outer.entrySet())
			{
				String key=pair.getKey();
				System.out.print("Outer Key: "+key+"\n");
				System.out.println("Outer Value:[");
				HashMap<String,Integer> map =pair.getValue();
				for(Map.Entry<String, Integer> pair1 : map.entrySet())
				{
					String key1=pair1.getKey();
					int value1=pair1.getValue();
					System.out.println("(Inner Key:"+key1+" Inner Value:"+value1+")");
				}
				System.out.println("]\n");
			} 
		}
		catch(Exception e)
		{
			System.out.println("Contructor File Handler Exception: "+e.getMessage());
		}
	}
	
	public String list(String sem)
	{
		String list="";
		
		if("Fall".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,Integer> entry:fall.entrySet())
			{
				list+=entry.getKey()+" "+entry.getValue()+" ";
			}
			return list;
		}
		else if("Winter".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,Integer> entry:winter.entrySet())
			{
				list+=entry.getKey()+" "+entry.getValue()+" ";
			}
			return list;
		}
		else if("Summer".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,Integer> entry:summer.entrySet())
			{
				list+=entry.getKey()+" "+entry.getValue()+" ";
			}
			return list;
		}
		return "No Courses for "+sem;
	}
	
	public void remove(String courseId,String sem)
	{
		System.out.println("Remove Method Called");
		if("Fall".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,ArrayList<String>> entry: studFall.entrySet())
			{
				String key=entry.getKey();
				if(studFall.get(key).contains(courseId))
				{
					studFall.get(key).remove(courseId);
					studCourseCount.get(key).set(0,studCourseCount.get(key).get(0)-1);
					studCourseCount.get(key).set(3,studCourseCount.get(key).get(3)-1);
				}
			}
		}
		else if("Winter".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,ArrayList<String>> entry: studWin.entrySet())
			{
				String key=entry.getKey();
				if(studWin.get(key).contains(courseId))
				{
					studWin.get(key).remove(courseId);
					studCourseCount.get(key).set(1,studCourseCount.get(key).get(1)-1);
					studCourseCount.get(key).set(3,studCourseCount.get(key).get(3)-1);
				}
			}
		}
		else if("Summer".equals(sem.trim().toString()))
		{
			for(Map.Entry<String,ArrayList<String>> entry: studSum.entrySet())
			{
				String key=entry.getKey();
				if(studSum.get(key).contains(courseId))
				{
					studSum.get(key).remove(courseId);
					studCourseCount.get(key).set(2,studCourseCount.get(key).get(2)-1);
					studCourseCount.get(key).set(3,studCourseCount.get(key).get(3)-1);
				}
			}
		}
	}
	public boolean search(String studId,String courseId)
	{
		for(Map.Entry<String,ArrayList<String>> pair: studFall.entrySet())
		{
			if(pair.getKey().equals(studId) && pair.getValue().contains(courseId))
			{
				return true;
			}	
		} 
		for(Map.Entry<String,ArrayList<String>> pair: studWin.entrySet())
		{
			if(pair.getKey().equals(studId) && (pair.getValue().contains(courseId)))
			{
				return true;
			}	
		}
		for(Map.Entry<String,ArrayList<String>> pair: studSum.entrySet())
		{
			if(pair.getKey().equals(studId) && pair.getValue().contains(courseId))
			{
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean searchFall(String studId,String courseId)
	{
		for(Map.Entry<String,ArrayList<String>> pair: studFall.entrySet())
		{
			if(pair.getKey().equals(studId) && pair.getValue().contains(courseId))
			{
				return true;
			}	
		} 
		return false;
	}
	
	public boolean searchWin(String studId,String courseId)
	{
		for(Map.Entry<String,ArrayList<String>> pair: studWin.entrySet())
		{
			if(pair.getKey().equals(studId) && pair.getValue().contains(courseId))
			{
				return true;
			}	
		} 
		return false;
	}
	
	public boolean searchSum(String studId,String courseId)
	{
		for(Map.Entry<String,ArrayList<String>> pair: studSum.entrySet())
		{
			if(pair.getKey().equals(studId) && pair.getValue().contains(courseId))
			{
				return true;
			}	
		} 
		return false;
	}
	
	
	public String addCourse(String adId,String courseId, String sem,int cap)
	{
		if(advisor.contains(adId))
		{
			if(!outer.get(sem.trim()).containsKey(courseId.trim()))
			{
			if(sem.equals("Fall"))
			{
				fall.put(courseId,cap);
				outer.put(sem,fall);
			}
			else if(sem.equals("Winter"))
			{
				winter.put(courseId,cap);
				outer.put(sem,winter);
			}
			else if(sem.equals("Summer"))
			{
				summer.put(courseId,cap);
				outer.put(sem,summer);
			}
			logger.info(adId+" Added New Course: "+courseId+" for "+sem+" semester"+" with capacity: "+cap);
			//return "Successful adding "+courseId+" with capacity "+cap+" for "+sem;
			return "AC1";
			}
			else
			{
				//return "Course Already Present";
				return "AC2";
			}
		}
		else
		{
			logger.info("Unregistered Advisor: "+adId+" tried to add new course: "+courseId);
			//return "You are not a registered Advisor";
			return "AC3";
		}
	}
	
	
	public String remCourse(String adId,String courseId, String sem) 
	{
		DatagramSocket ds=null;
		if(advisor.contains(adId))
		{
			if(outer.get(sem.trim()).containsKey(courseId.trim()))
			{
				try 
				{
					
					DatagramPacket dp,dp1;
					byte[]buf=new byte[1000];
					String msg;
					
					if("Fall".equals(sem))
					{
						fall.remove(courseId);
						outer.put(sem,fall);
						
						for(Map.Entry<String,ArrayList<String>> entry: studFall.entrySet())
						{
							String key=entry.getKey();
							if(studFall.get(key).contains(courseId))
							{
								studFall.get(key).remove(courseId);
								studCourseCount.get(key).set(0,studCourseCount.get(key).get(0)-1);
								//studCourseCount.get(key).set(1,studCourseCount.get(key).get(1)-1);
							}
						}
					}
					else if("Winter".equals(sem))
					{
						winter.remove(courseId);
						outer.put(sem,winter);
						for(Map.Entry<String,ArrayList<String>> entry: studWin.entrySet())
						{
							String key=entry.getKey();
							if(studWin.get(key).contains(courseId))
							{
								studWin.get(key).remove(courseId);
								studCourseCount.get(key).set(1,studCourseCount.get(key).get(1)-1);
								//studCourseCount.get(key).set(3,studCourseCount.get(key).get(3)-1);
							}
						}
					}
					else if("Summer".equals(sem))
					{
						summer.remove(courseId);
						outer.put(sem,summer);
						for(Map.Entry<String,ArrayList<String>> entry: studSum.entrySet())
						{
							String key=entry.getKey();
							if(studSum.get(key).contains(courseId))
							{
								studSum.get(key).remove(courseId);
								studCourseCount.get(key).set(2,studCourseCount.get(key).get(2)-1);
								//studCourseCount.get(key).set(5,studCourseCount.get(key).get(5)-1);
							}
						}
					}
					
					/*for(Map.Entry<String,HashMap<String,Integer>> pair: outer.entrySet())
					{
						String key=pair.getKey();
						System.out.print("outer Key:"+key+" Outer Value:( ");
						HashMap<String,Integer> map =pair.getValue();
						for(Map.Entry<String, Integer> pair1 : map.entrySet())
						{
							String key1=pair1.getKey();
							int value1=pair1.getValue();
							System.out.println("inner Key:"+key1+" Inner Value:"+value1+")");
						}
					} 
					
					for(Map.Entry<String,ArrayList<String>> entry:studFall.entrySet())
					{
						System.out.println(entry.getKey()+": "+entry.getValue());
					}
					
					for(Map.Entry<String,ArrayList<String>> entry:studWin.entrySet())
					{
						System.out.println(entry.getKey()+": "+entry.getValue());
					}
					
					for(Map.Entry<String,ArrayList<String>> entry:studSum.entrySet())
					{
						System.out.println(entry.getKey()+": "+entry.getValue());
					}*/
					
					ds=new DatagramSocket();
					InetAddress ip=InetAddress.getByName("localhost");
					msg="Remove "+courseId+" "+sem;
					dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
					ds.send(dp);
					
					dp1=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
					ds.send(dp1);
					ds.close();
					logger.info(adId+" removed "+courseId+" from "+sem);
					//return "Successfully removed "+courseId+" from "+sem;
					return "RC1";
				}
				catch(Exception e)
				{
					System.out.println("Remove Course Exception: "+e.getMessage());
				}
				finally
				{
					if(ds!=null)
						ds.close();
				}
			}
			else
			{
				//return "Course Doesnt Exist";
				return "RC2";
			}
		}
		else
		{
			logger.info("Unregistered Advisor: "+adId+" tried to remove course: "+courseId);
			//return "You are not a Registred Advisor";
			return "RC3";
		}
		return "";
	}


	public String listCourse(String adId,String sem) 
	{
		DatagramSocket ds=null;
		if(advisor.contains(adId))
		{
			try
			{
				byte []buf=new byte[1000];
				byte []buf1=new byte[1000];
				DatagramPacket dp,dp1;
				String list="";
				String list1="";
				String list2="";
				String msg;
				InetAddress ip=InetAddress.getByName("localhost");
				if("Fall".equals(sem.trim().toString()))
				{
					for(Map.Entry<String,Integer> entry:fall.entrySet())
					{
						list+=entry.getKey()+" "+entry.getValue()+" ";
					}
					ds=new DatagramSocket();
					msg="List "+sem;
					dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
					ds.send(dp);
					dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					list1+=new String(dp.getData());
					
					dp1=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
					ds.send(dp1);
					dp1=new DatagramPacket(buf1,buf1.length);
					ds.receive(dp1);
					list2+=new String(dp1.getData());
				}
				
				else if("Winter".equals(sem.trim().toString()))
				{
					for(Map.Entry<String,Integer> entry:winter.entrySet())
					{
						list+=entry.getKey()+" "+entry.getValue()+" ";
					}
					ds=new DatagramSocket();
					msg="List "+sem;
					dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
					ds.send(dp);
					dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					list1+=new String(dp.getData());
					
					dp1=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
					ds.send(dp1);
					dp1=new DatagramPacket(buf1,buf1.length);
					ds.receive(dp1);
					list2+=new String(dp1.getData());
				}
				
				else if("Summer".equals(sem.trim().toString()))
				{
					for(Map.Entry<String,Integer> entry:summer.entrySet())
					{
						list+=entry.getKey()+" "+entry.getValue()+" ";
					}
					ds=new DatagramSocket();
					msg="List "+sem;
					dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
					ds.send(dp);
					dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					list1+=new String(dp.getData());
					
					dp1=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
					ds.send(dp1);
					dp1=new DatagramPacket(buf1,buf1.length);
					ds.receive(dp1);
					list2+=new String(dp1.getData());
				}
				logger.info(adId+" Called List Course method for "+sem);
				list=list+"\n"+list1+"\n"+list2;
				list=list.replaceAll("\0", "");
				return list;
			}
			catch(Exception e)
			{
				System.out.println("List Course Methods Exception:"+e.getMessage());
			}
			finally
			{
				if(ds!=null)
					ds.close();
			}	
		}
		else
		{
			logger.info("Unregistered Advisor: "+adId+" tried to List Courses");
			return "You are not a Registered Advisor";
		}
		return "";
	}
	
	
	public String enrolCourse(String studId, String courseId, String sem)
	{ 
		
		synchronized(lock)
		{
			DatagramSocket ds=null;
			try
			{
				String msg;
				DatagramPacket dp;
				byte []buf=new byte[1000];
				System.out.println("COMP Enrol Course");
	
				if(studId.startsWith("C") && search(studId,courseId))
				{
					//return "You are already enroled in this course: "+courseId;
					return "EC5";
				}
				
					if(courseId.startsWith("C"))
					{
						if(outer.get(sem.trim()).containsKey(courseId.trim()))
						{
						if(studId.startsWith("C"))
						{
							/*if(search(studId,courseId))
								return "You are already Registered for this course: "+courseId;*/
							
							studCourseCount.putIfAbsent(studId, new ArrayList<Integer>() {{
								add(0,0);
								add(1,0);
								add(2,0);
								add(3,0);
							}});
							
								if(sem.equals("Fall"))
								{
									studFall.putIfAbsent(studId,new ArrayList<String>());
									if(outer.get(sem).get(courseId)>0)
									{
										if(studCourseCount.get(studId).get(0)<=2)
										{
											studCourseCount.get(studId).set(0,studCourseCount.get(studId).get(0)+1);
											studCourseCount.put(studId,studCourseCount.get(studId));
											
											studFall.get(studId).add(courseId);
											studFall.put(studId,studFall.get(studId));
											
											fall.put(courseId, fall.get(courseId)-1);
											outer.put(sem,fall);
											logger.info(studId+" enroled in "+courseId+" for "+sem);
											//return "You are successfully enroled in "+courseId+" for "+sem;
											return "EC1";
										}
										else
										{
											logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
											//return "You are enroled in max number of subjects(3)";
											return "EC3";
										}
									}
									else
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Course Capacity Reached");
										//return "Course "+courseId +" is Full for semester "+sem;
										return "EC4";
									}
								}
								
								else if(sem.equals("Winter"))
								{
									studWin.putIfAbsent(studId,new ArrayList<String>());
									if(outer.get(sem).get(courseId)>0)
									{
										if(studCourseCount.get(studId).get(1)<=2)
										{
											studCourseCount.get(studId).set(1,studCourseCount.get(studId).get(1)+1);
											studCourseCount.put(studId,studCourseCount.get(studId));
											
											studWin.get(studId).add(courseId);
											studWin.put(studId,studWin.get(studId));
											
											winter.put(courseId, winter.get(courseId)-1);
											outer.put(sem,winter);
											logger.info(studId+" enroled in "+courseId+" for "+sem);
											//return "You are successfully enroled in "+courseId+" for "+sem;
											return "EC1";
										}
										else
										{
											logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
											//return "You are enroled in max number of subjects(3)";
											return "EC3";
										}
									}
									else
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Course Capacity Reached");
										//return "Course "+courseId +" is Full for semester "+sem;
										return "EC4";
									}
								}
								
								else if(sem.equals("Summer"))
								{
									studSum.putIfAbsent(studId,new ArrayList<String>());
									if(outer.get(sem).get(courseId)>0)
									{
										if(studCourseCount.get(studId).get(2)<=2)
										{
											studCourseCount.get(studId).set(2,studCourseCount.get(studId).get(2)+1);
											studCourseCount.put(studId,studCourseCount.get(studId));
											
											studSum.get(studId).add(courseId);
											studSum.put(studId,studSum.get(studId));
											
											summer.put(courseId, summer.get(courseId)-1);
											outer.put(sem,summer);
											logger.info(studId+" enroled in "+courseId+" for "+sem);
											//return "You are successfully enroled in "+courseId+" for "+sem;
											return "EC1";
										}
										else
										{
											logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
											//return "You are enroled in max number of subjects(3)";
											return "EC3";
										}
									}
									else
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Course Capacity Reached");
										//return "Course "+courseId +" is Full for semester "+sem;
										return "EC4";
									}
								}
						}
						else
						{
							System.out.println(sem);
							System.out.println(courseId);
							System.out.println(studId);
							
							if("Fall".equals(sem.trim().toString()))
							{
								if(outer.get(sem.trim().toString()).get(courseId.trim().toString())>0)
								{
									fall.put(courseId.trim().toString(),fall.get(courseId.trim().toString())-1);
									outer.put(sem.trim().toString(),fall);
									logger.info(studId+" enroled in "+courseId+" for "+sem);
									return "Yes";
								}
							}
							else if("Winter".equals(sem.trim().toString()))
							{
								if(outer.get(sem.trim().toString()).get(courseId.trim().toString())>0)
								{
									winter.put(courseId.trim().toString(),winter.get(courseId.trim().toString())-1);
									outer.put(sem.trim().toString(),winter);
									logger.info(studId+" enroled in "+courseId+" for "+sem);
									return "Yes";
								}
							}
							else if("Summer".equals(sem.trim().toString()))
							{
								if(outer.get(sem.trim().toString()).get(courseId.trim().toString())>0)
								{
									summer.put(courseId.trim().toString(),summer.get(courseId.trim().toString())-1);
									outer.put(sem.trim().toString(),summer);
									logger.info(studId+" enroled in "+courseId+" for "+sem);
									return "Yes";
								}
							}
							else
							{
								logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Course Capacity Reached");
								return "Full";
							}							
						}
					}
					else
					{
						//return "Course Doesnt Exist In this Term";
						return "EC2";
					}
					}
					
					else if(courseId.startsWith("S") || courseId.startsWith("I"))
					{
						msg="Enrol "+studId+" "+courseId+" "+sem;
						if(courseId.startsWith("S"))
						{
							System.out.println("SOEN Enrol from COMP Server");
							ds=new DatagramSocket();
							InetAddress ip=InetAddress.getByName("localhost");
							dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
							ds.send(dp);
							dp=new DatagramPacket(buf,buf.length);
							ds.receive(dp);
							msg=new String(dp.getData());
						}
						else
						{
							System.out.println("INSE Enrol from COMP Server");
							ds=new DatagramSocket();
							InetAddress ip=InetAddress.getByName("localhost");
							dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
							ds.send(dp);
							dp=new DatagramPacket(buf,buf.length);
							ds.receive(dp);
							msg=new String(dp.getData());
						}
						System.out.println(msg);
						
						if("Yes".equals(msg.trim().toString()))
						{
							studCourseCount.putIfAbsent(studId, new ArrayList<Integer>() {{
								add(0,0);
								add(1,0);
								add(2,0);
								add(3,0);
							}});
							
							if("Fall".equals(sem.trim().toString()))
							{
								if(studCourseCount.get(studId).get(0)<3 && studCourseCount.get(studId).get(3)<2)
								{	
									studFall.putIfAbsent(studId,new ArrayList<String>());
									studCourseCount.get(studId).set(0,studCourseCount.get(studId).get(0)+1);
									studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)+1);
									studCourseCount.put(studId,studCourseCount.get(studId));
									
									studFall.get(studId).add(courseId);
									studFall.put(studId,studFall.get(studId));
									System.out.println(studFall.get(studId));
									logger.info(studId+" enroled in "+courseId+" for "+sem);
									//return "You are successfully enroled in "+courseId+" for "+sem;
									return "EC1";
								}
								else 
								{
									if(studCourseCount.get(studId).get(0)>=3)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
										//return "You are already Enroled in max number of courses for "+sem;
										return "EC3";
									}
									if(studCourseCount.get(studId).get(3)>=2)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Other Courses Enroled");
										//return "You are already Enroled in max number of other courses";
										return "EC7";
									}
								}
							}
							else if("Winter".equals(sem.trim().toString()))
							{
								if(studCourseCount.get(studId).get(1)<3 && studCourseCount.get(studId).get(3)<2)
								{
										studWin.putIfAbsent(studId,new ArrayList<String>());
										studCourseCount.get(studId).set(1,studCourseCount.get(studId).get(1)+1);
										studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)+1);
										studCourseCount.put(studId,studCourseCount.get(studId));
										
										studWin.get(studId).add(courseId);
										studWin.put(studId,studWin.get(studId));
										logger.info(studId+" enroled in "+courseId+" for "+sem);
										//return "You are successfully enroled in "+courseId+" for "+sem;
										return "EC1";
								}
								else 
								{
									if(studCourseCount.get(studId).get(1)>=3)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
										//return "You are already Enroled in max number of courses for "+sem;
										return "EC3";
									}
									if(studCourseCount.get(studId).get(3)>=2)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Other Courses Enroled");
										//return "You are already Enroled in max number of other courses";
										return "EC7";
									}
								}
							}
							else if("Summer".equals(sem.trim().toString()))
							{
								if(studCourseCount.get(studId).get(2)<3 && studCourseCount.get(studId).get(3)<2)
								{
										studSum.putIfAbsent(studId,new ArrayList<String>());
										studCourseCount.get(studId).set(2,studCourseCount.get(studId).get(2)+1);
										studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)+1);
										studCourseCount.put(studId,studCourseCount.get(studId));
										
										studSum.get(studId).add(courseId);
										studSum.put(studId,studSum.get(studId));
										logger.info(studId+" enroled in "+courseId+" for "+sem);
										//return "You are successfully enroled in "+courseId+" for "+sem;
										return "EC1";
								}
								else 
								{
									if(studCourseCount.get(studId).get(2)>=3)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Courses Enroled");
										//return "You are already Enroled in max number of courses for "+sem;
										return "EC3";
									}
									if(studCourseCount.get(studId).get(3)>=2)
									{
										logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Max Other Courses Enroled");
										//return "You are already Enroled in max number of other courses";
										return "EC7";
									}
								}
							}
						}
						else if("Full".equals(msg.trim().toString()))
						{
							logger.info(studId+" could not register for "+courseId+ " for "+sem+" as Course Capacity Reached");
							//return "Course "+courseId +" is Full for semester "+sem;
							return "EC4";
						}
						else
						{
							//return "Course "+courseId +" doesnt not exist for: "+sem;
							return "EC2";
						}
						
					}
			}
			catch(Exception e)
			{
				System.out.println("Enrol Course Method Exception: "+e.getMessage());
			}
			finally
			{
				if(ds!=null)
					ds.close();
			}
			return "";
		}
	}
	
	
	public String dropCourse(String studId,String courseId,String sem) 
	{
		synchronized(lock)
		{
			DatagramSocket ds=null;
			try
			{
				DatagramPacket dp;
				String msg;
				byte[]buf=new byte[1000];
	
				System.out.println("COMP Drop Course");
				if(courseId.startsWith("C"))
				{
					if(studId.startsWith("C"))
					{
						if("Fall".equals(sem.trim().toString()))
						{
							if(searchFall(studId,courseId))
							{
								studFall.get(studId).remove(courseId);
								studCourseCount.get(studId).set(0,studCourseCount.get(studId).get(0)-1);
								
								fall.put(courseId,fall.get(courseId)+1);
								outer.put(sem,fall);
								logger.info(studId+" Dropped from "+ courseId+" for "+sem);
								//return "You are dropped from "+courseId+" for "+sem;
								return "DC1";
							}
							else
							{
								//return "You are not enroled in "+courseId+" for "+sem;
								return "DC2";
							}
						}
						else if("Winter".equals(sem.trim().toString()))
						{
							if(searchWin(studId,courseId))
							{
								studWin.get(studId).remove(courseId);
								studCourseCount.get(studId).set(1,studCourseCount.get(studId).get(1)-1);
								
								winter.put(courseId,winter.get(courseId)+1);
								outer.put(sem,winter);
								logger.info(studId+" Dropped from "+ courseId+" for "+sem);
								//return "You are dropped from "+courseId+" for "+sem;
								return "DC1";
							}
							else
							{
								//return "You are not enroled in "+courseId+" for "+sem;
								return "DC2";
							}
						}
						else if("Summer".equals(sem.trim().toString()))
						{
							if(searchSum(studId,courseId))
							{
								studSum.get(studId).remove(courseId);
								studCourseCount.get(studId).set(2,studCourseCount.get(studId).get(2)-1);
								
								summer.put(courseId,summer.get(courseId)+1);
								outer.put(sem,summer);
								logger.info(studId+" Dropped from "+ courseId+" for "+sem);
								//return "You are dropped from "+courseId+" for "+sem;
								return "DC1";
							}
							else
							{
								//return "You are not enroled in "+courseId+" for "+sem;
								return "DC2";
							}
						}
					}
					else
					{
						if("Fall".equals(sem.trim().toString()))
						{
								fall.put(courseId.trim().toString(),fall.get(courseId.trim().toString())+1);
								outer.put(sem.trim().toString(),fall);
						}
						else if("Winter".equals(sem.trim().toString()))
						{
								winter.put(courseId.trim().toString(),winter.get(courseId.trim().toString())+1);
								outer.put(sem.trim().toString(),fall);
						}
						else if("Summer".equals(sem.trim().toString()))
						{
								summer.put(courseId.trim().toString(),summer.get(courseId.trim().toString())+1);
								outer.put(sem.trim().toString(),summer);
						}
						logger.info(studId+" Dropped from "+ courseId+" for "+sem);
						return "Yes";
					}
				}
				else if(courseId.startsWith("S") || courseId.startsWith("I"))
				{
					if(courseId.startsWith("S"))
					{
						System.out.println("SOEN Drop from COMP Server");
						
						ds=new DatagramSocket();
						InetAddress ip=InetAddress.getByName("localhost");
						msg="Drop "+studId+" "+courseId+" "+sem;
						dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1232);
					}
					else
					{
						System.out.println("INSE Drop from COMP Server");
						
						ds=new DatagramSocket();
						InetAddress ip=InetAddress.getByName("localhost");
						msg="Drop "+studId+" "+courseId+" "+sem;
						dp=new DatagramPacket(msg.getBytes(),msg.length(),ip,1237);
					}
					
					if("Fall".equals(sem.trim().toString()))
					{
						if(searchFall(studId,courseId))
						{
							ds.send(dp);
							dp=new DatagramPacket(buf,buf.length);
							ds.receive(dp);
							msg=new String(dp.getData());
							studCourseCount.get(studId).set(0,studCourseCount.get(studId).get(0)-1);
							studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)-1);
							studFall.get(studId).remove(courseId);
							logger.info(studId+" Dropped from "+ courseId+" for "+sem);
							//return "You are dropped from "+courseId+" for "+sem;
							return "DC1";
						}
						else
						{
							return "DC2";
							//return "You are not enroled in "+courseId+" for "+sem;
						}
					}
					if("Winter".equals(sem.trim().toString()))
					{
						if(searchWin(studId,courseId))
						{
							ds.send(dp);
							dp=new DatagramPacket(buf,buf.length);
							ds.receive(dp);
							msg=new String(dp.getData());
							studCourseCount.get(studId).set(1,studCourseCount.get(studId).get(1)-1);
							studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)-1);
							studWin.get(studId).remove(courseId);
							logger.info(studId+" Dropped from "+ courseId+" for "+sem);
							//return "You are dropped from "+courseId+" for "+sem;
							return "DC1";
						}
						else
						{
							//return "You are not enroled in "+courseId+" for "+sem;
							return "DC2";
						}
					}
					if("Summer".equals(sem.trim().toString()))
					{
						if(searchSum(studId,courseId))
						{
							ds.send(dp);
							dp=new DatagramPacket(buf,buf.length);
							ds.receive(dp);
							msg=new String(dp.getData());
							studCourseCount.get(studId).set(2,studCourseCount.get(studId).get(2)-1);
							studCourseCount.get(studId).set(3,studCourseCount.get(studId).get(3)-1);
							studSum.get(studId).remove(courseId);
							logger.info(studId+" Dropped from "+ courseId+" for "+sem);
							//return "You are dropped from "+courseId+" for "+sem;
							return "DC1";
						}
						else
						{
							//return "You are not enroled in "+courseId+" for "+sem;
							return "DC2";
						}
					}
				
				}
			}
			catch(Exception e)
			{
				System.out.println("Drop Method Exception: "+e.getMessage());
			}
			finally
			{
				if(ds!=null)
					ds.close();
			}
			return "";
		}
	}
	
	
	public String getSchedule(String studId)
	{
		try
		{
			System.out.println("Get Schedule");
			String schedule="Course\tSemester\n"
							+ studFall.get(studId)+"\tFall\n"
							+ studWin.get(studId)+"\tWinter\n"
							+ studSum.get(studId)+"\tSummer";
			schedule=schedule.replace("null","[]");
			return schedule;
		}
		catch(Exception e)
		{
			System.out.println("Get Schedule Method Exception: "+e.getMessage());
		}
		return "";
	}
	

	public String swapCourse(String studId, String courseId, String sem, String newCourseId) 
	{
		try
		{
			synchronized(lock)
			{
				String result=dropCourse(studId,courseId,sem);
				String compare="DC1";
				if(result.equals(compare))
				{
					String result1=enrolCourse(studId,newCourseId,sem);
					String compare1="EC1";
					
					if(result1.equals(compare1))
						return "SS1";
					else
					{
						enrolCourse(studId,courseId,sem);
						return result1;
					}
				}
				else
					return result;
			}
		}
		catch(Exception e)
		{
			System.out.println("Swap Method Exception: "+e.getMessage());
		}
		return "";
	}
	
	
	public static void main(String[] args) 
	{
		CompServer cs = new CompServer();
		System.out.println("COMP ready and waiting ...");
		try
        { 
   	      	Runnable task =()->
   	      	{
			DatagramSocket ds=null;
			while(true)
			{
				try 
				{
					System.out.println("Receiving INSE Request");
					ds=new DatagramSocket(1235);
					byte []buf=new byte[1000];
					String msg="";
					String reply="";
					DatagramPacket dp=new DatagramPacket(buf,buf.length);
					ds.receive(dp);
					msg=new String(dp.getData());
					StringTokenizer st=new StringTokenizer(msg," ");
					String method=st.nextToken();
					if(method.equals("Enrol"))
					{
						String studId=st.nextToken();
						String courseId=st.nextToken();
						String sem=st.nextToken();
						System.out.println(msg);
						reply = cs.enrolCourse(studId,courseId,sem);
						System.out.println(reply);
						dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
						ds.send(dp);
					}
					else if(method.equals("Drop"))
					{
						String studId=st.nextToken();
						String courseId=st.nextToken();
						String sem=st.nextToken();
						System.out.println(msg);
						reply=cs.dropCourse(studId,courseId,sem);
						System.out.println(reply);
						dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
						ds.send(dp);
					}
					else if(method.equals("Remove"))
					{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						cs.remove(courseId,sem);
					}
					else if(method.equals("List"))
					{
						String sem=st.nextToken();
						reply=cs.list(sem);
						dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
						ds.send(dp);
					}
				}
				catch(Exception e)
				{
					System.out.println("Thread Exception: "+e.getMessage());
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
		Thread t=new Thread(task);
		t.start();
		
		  Runnable task1 =()->
	      {
	    	  DatagramSocket ds=null;
	    	  while(true)
	    	  {
	    		  System.out.println("Receiving SOEN Request");
	    		  try
	    		  {
						ds=new DatagramSocket(1238);
						byte []buf=new byte[1000];
						String msg="";
						String reply="";
						DatagramPacket dp=new DatagramPacket(buf,buf.length);
						ds.receive(dp);
						msg=new String(dp.getData());
						System.out.println();
						System.out.println("=="+msg);
						StringTokenizer st=new StringTokenizer(msg," ");
						String method=st.nextToken();
						System.out.println("++"+method);
						
						if(method.equals("Enrol"))
						{
							String studId=st.nextToken();
							String courseId=st.nextToken();
							String sem=st.nextToken();
							System.out.println(msg);
							reply = cs.enrolCourse(studId,courseId,sem);
							System.out.println(reply);
							System.out.println(reply);
							dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
							ds.send(dp);
						}
						else if(method.equals("Drop"))
						{
							String studId=st.nextToken();
							String courseId=st.nextToken();
							String sem=st.nextToken();
							System.out.println(msg);
							reply=cs.dropCourse(studId,courseId,sem);
							System.out.println(reply);
							dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
							ds.send(dp);
						}
						else if(method.equals("Remove"))
						{
							String courseId=st.nextToken();
							String sem=st.nextToken();
							cs.remove(courseId,sem);
						}
						else if(method.equals("List"))
						{
							String sem=st.nextToken();
							reply=cs.list(sem);
							System.out.println(reply);
							dp=new DatagramPacket(reply.getBytes(),reply.length(),dp.getAddress(),dp.getPort());
							ds.send(dp);
						}	
	    		  }
	    		  catch(Exception e)
	    		  {
	    			  System.out.println("Thread Exception: "+e.getMessage());
	    		  }
	    		  finally
	    		  {
	    			  if(ds!=null)
						ds.close();
	    		  }
	    	  }		
	      };
	Thread t1=new Thread(task1);
	t1.start(); 
	
	 Runnable task2 =()->
     {
   	  MulticastSocket ms1=null;
   	  DatagramSocket ds1=null;
   	  while(true)
   	  {
   		  System.out.println("Receiving Replica Message and sending response to FE");
   		  try
   		  {
					ms1=new MulticastSocket(1700);
					ds1=new DatagramSocket(5001);
					InetAddress ip=InetAddress.getByName("230.0.0.1");
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
					cs.unique_id=Integer.parseInt(st.nextToken());
					String id=st.nextToken();
					String method=st.nextToken();
					System.out.println("unique id:"+cs.unique_id+" ID:"+id+" Method:"+method);
					if(method.equals("Enrol"))
					{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						System.out.println("courseId:"+courseId+" sem:"+sem);
						reply = cs.enrolCourse(id.trim(),courseId.trim(),sem.trim());
						System.out.println(reply);
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
						ds1.send(dp1);
					}
					else if(method.equals("Drop"))
					{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						System.out.println(msg);
						reply=cs.dropCourse(id.trim(),courseId.trim(),sem.trim());
						System.out.println(reply);
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
						ds1.send(dp1);
					}
					else if(method.equals("Swap"))
					{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						String newCourseId=st.nextToken();
						reply=cs.swapCourse(id.trim(),courseId.trim(),sem.trim(),newCourseId.trim());
						System.out.println(reply);
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
						ds1.send(dp1);
					}
					else if("Get".equals(method.trim()))
					{
						System.out.println("Inside GET METHOD");
						if(cs.flag1==0)
						{
							System.out.println(cs.flag1);
							reply=cs.getSchedule(id.trim());
							System.out.println(reply);
							InetAddress ip1=InetAddress.getByName("132.205.93.58");
							dp1=new DatagramPacket("1".getBytes(),"1".length(),ip1,7002);
							ds1.send(dp1);
						}
						else
						{
							reply=cs.getSchedule(id.trim());
							System.out.println(reply);
							InetAddress ip1=InetAddress.getByName("132.205.93.58");
							dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
							ds1.send(dp1);
						}
					}
					else if(method.equals("Add"))
					{
						String courseId=st.nextToken();
						String sem=st.nextToken();
						int cap=Integer.parseInt(st.nextToken().trim());
						System.out.println("CourseId:"+courseId+" Semester:"+sem+" Capacity:"+cap);
						reply=cs.addCourse(id.trim(),courseId.trim(),sem.trim(),cap);
						System.out.println(reply);
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
						ds1.send(dp1);
					}
					else if(method.equals("Remove"))
					{

						String courseId=st.nextToken();
						String sem=st.nextToken();
						reply=cs.remCourse(id.trim(),courseId.trim(),sem.trim());
						System.out.println(reply);
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
						ds1.send(dp1);
					}
					else if(method.equals("List"))
					{
						String sem=st.nextToken();
						reply=cs.listCourse(id.trim(), sem.trim());
						InetAddress ip1=InetAddress.getByName("132.205.93.58");
						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
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
     Thread t2=new Thread(task2);
     t2.start(); 

     Runnable task3 =()->
     {
    	 DatagramSocket ds2=null;
    	 while(true)
    	 {
    		 System.out.println("Receiving SW bug Message from RM");
    		 try
    		 {
				ds2=new DatagramSocket(2000);
				byte buf[]=new byte[1000];
				DatagramPacket dp2=new DatagramPacket(buf,buf.length);
				ds2.receive(dp2);
				System.out.println("Messsage from RM: "+new String(dp2.getData()));
				cs.flag1=1;
    		 }
    		 catch(Exception e)
    		 {
    			 System.out.print("Thread 3: "+e.getMessage());
    		 }
    		 finally
    		 {
    			 ds2.close();
    		 }
    	 }
     };
     Thread t3=new Thread(task3);
     t3.start(); 
		
        }
		catch(Exception e)
		{
			System.out.println("Main Method Exception: "+e.getStackTrace());
		}
	}

}
