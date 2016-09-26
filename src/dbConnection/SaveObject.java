package dbConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaveObject {

    public Object javaObject=null;

    public Object getJavaObject() {
        return javaObject;
    }


    public void setJavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }


    public  void saveObject(String table, String name) throws Exception
    {
        try{
	    Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        String sql=null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(javaObject);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();


        sql="insert into ? (name, maze) values(?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1, table);
        ps.setObject(2, name);
        ps.setObject(3, data);
        ps.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    public Object getObject(String table, String name) throws Exception
    {
        Object rmObj=null;
        Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="select * from ? where name LIKE ?";
        
        ps=conn.prepareStatement(sql);
        ps.setObject(1, table);
        ps.setObject(2, name);
        rs=ps.executeQuery();

        if(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {

            bais = new ByteArrayInputStream(rs.getBytes("javaObject"));

            ins = new ObjectInputStream(bais);

            Object obj = ins.readObject();

            ins.close();
            
            return obj;

            }
            catch (Exception e) {

            e.printStackTrace();
            }

        }

        return rmObj;
    }
}