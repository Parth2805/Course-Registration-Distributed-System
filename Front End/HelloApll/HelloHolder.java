package HelloApll;

/**
* HelloApll/HelloHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from G:/ECLP/PROJ/src/Hello.idl
* Tuesday, November 27, 2018 3:18:22 PM EST
*/

public final class HelloHolder implements org.omg.CORBA.portable.Streamable
{
  public HelloApll.Hello value = null;

  public HelloHolder ()
  {
  }

  public HelloHolder (HelloApll.Hello initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = HelloApll.HelloHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    HelloApll.HelloHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return HelloApll.HelloHelper.type ();
  }

}
