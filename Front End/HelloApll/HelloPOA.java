package HelloApll;


/**
* HelloApll/HelloPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from G:/ECLP/PROJ/src/Hello.idl
* Tuesday, November 27, 2018 3:18:22 PM EST
*/

public abstract class HelloPOA extends org.omg.PortableServer.Servant
 implements HelloApll.HelloOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sayhello", new java.lang.Integer (0));
    _methods.put ("enrolcourse", new java.lang.Integer (1));
    _methods.put ("classSchedule", new java.lang.Integer (2));
    _methods.put ("removeCourse", new java.lang.Integer (3));
    _methods.put ("swapClasses", new java.lang.Integer (4));
    _methods.put ("addCourse", new java.lang.Integer (5));
    _methods.put ("removeCourseA", new java.lang.Integer (6));
    _methods.put ("listCourseAvailability", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // HelloApll/Hello/sayhello
       {
         String $result = null;
         $result = this.sayhello ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // HelloApll/Hello/enrolcourse
       {
         String studentid = in.read_string ();
         String courseid = in.read_string ();
         String sem = in.read_string ();
         String $result = null;
         $result = this.enrolcourse (studentid, courseid, sem);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // HelloApll/Hello/classSchedule
       {
         String studentid = in.read_string ();
         String $result = null;
         $result = this.classSchedule (studentid);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // HelloApll/Hello/removeCourse
       {
         String studentid = in.read_string ();
         String courseid = in.read_string ();
         String sem = in.read_string ();
         String $result = null;
         $result = this.removeCourse (studentid, courseid, sem);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // HelloApll/Hello/swapClasses
       {
         String studentid = in.read_string ();
         String courseid = in.read_string ();
         String sem = in.read_string ();
         String newcourseid = in.read_string ();
         String $result = null;
         $result = this.swapClasses (studentid, courseid, sem, newcourseid);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // HelloApll/Hello/addCourse
       {
         String advisorid = in.read_string ();
         String courseid = in.read_string ();
         String sem = in.read_string ();
         int capacity = in.read_long ();
         String $result = null;
         $result = this.addCourse (advisorid, courseid, sem, capacity);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // HelloApll/Hello/removeCourseA
       {
         String advisorid = in.read_string ();
         String courseID = in.read_string ();
         String semester = in.read_string ();
         String $result = null;
         $result = this.removeCourseA (advisorid, courseID, semester);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // HelloApll/Hello/listCourseAvailability
       {
         String advisorid = in.read_string ();
         String semester = in.read_string ();
         String $result = null;
         $result = this.listCourseAvailability (advisorid, semester);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:HelloApll/Hello:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Hello _this() 
  {
    return HelloHelper.narrow(
    super._this_object());
  }

  public Hello _this(org.omg.CORBA.ORB orb) 
  {
    return HelloHelper.narrow(
    super._this_object(orb));
  }


} // class HelloPOA