package HelloApll;


/**
* HelloApll/_HelloStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from G:/ECLP/PROJ/src/Hello.idl
* Tuesday, November 27, 2018 3:18:22 PM EST
*/

public class _HelloStub extends org.omg.CORBA.portable.ObjectImpl implements HelloApll.Hello
{

  public String sayhello ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sayhello", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sayhello (        );
            } finally {
                _releaseReply ($in);
            }
  } // sayhello

  public String enrolcourse (String studentid, String courseid, String sem)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("enrolcourse", true);
                $out.write_string (studentid);
                $out.write_string (courseid);
                $out.write_string (sem);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return enrolcourse (studentid, courseid, sem        );
            } finally {
                _releaseReply ($in);
            }
  } // enrolcourse

  public String classSchedule (String studentid)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("classSchedule", true);
                $out.write_string (studentid);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return classSchedule (studentid        );
            } finally {
                _releaseReply ($in);
            }
  } // classSchedule

  public String removeCourse (String studentid, String courseid, String sem)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeCourse", true);
                $out.write_string (studentid);
                $out.write_string (courseid);
                $out.write_string (sem);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return removeCourse (studentid, courseid, sem        );
            } finally {
                _releaseReply ($in);
            }
  } // removeCourse

  public String swapClasses (String studentid, String courseid, String sem, String newcourseid)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("swapClasses", true);
                $out.write_string (studentid);
                $out.write_string (courseid);
                $out.write_string (sem);
                $out.write_string (newcourseid);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return swapClasses (studentid, courseid, sem, newcourseid        );
            } finally {
                _releaseReply ($in);
            }
  } // swapClasses

  public String addCourse (String advisorid, String courseid, String sem, int capacity)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCourse", true);
                $out.write_string (advisorid);
                $out.write_string (courseid);
                $out.write_string (sem);
                $out.write_long (capacity);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addCourse (advisorid, courseid, sem, capacity        );
            } finally {
                _releaseReply ($in);
            }
  } // addCourse

  public String removeCourseA (String advisorid, String courseID, String semester)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeCourseA", true);
                $out.write_string (advisorid);
                $out.write_string (courseID);
                $out.write_string (semester);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return removeCourseA (advisorid, courseID, semester        );
            } finally {
                _releaseReply ($in);
            }
  } // removeCourseA

  public String listCourseAvailability (String advisorid, String semester)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listCourseAvailability", true);
                $out.write_string (advisorid);
                $out.write_string (semester);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return listCourseAvailability (advisorid, semester        );
            } finally {
                _releaseReply ($in);
            }
  } // listCourseAvailability

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:HelloApll/Hello:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _HelloStub