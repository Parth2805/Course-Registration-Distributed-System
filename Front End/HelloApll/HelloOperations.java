package HelloApll;


/**
* HelloApll/HelloOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from G:/ECLP/PROJ/src/Hello.idl
* Tuesday, November 27, 2018 3:18:22 PM EST
*/

public interface HelloOperations 
{
  String sayhello ();
  String enrolcourse (String studentid, String courseid, String sem);
  String classSchedule (String studentid);
  String removeCourse (String studentid, String courseid, String sem);
  String swapClasses (String studentid, String courseid, String sem, String newcourseid);
  String addCourse (String advisorid, String courseid, String sem, int capacity);
  String removeCourseA (String advisorid, String courseID, String semester);
  String listCourseAvailability (String advisorid, String semester);
} // interface HelloOperations
