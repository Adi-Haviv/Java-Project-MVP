package dbConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


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
	    Connection conn= DriverManager.getConnection("jdbc:mysql://52.36.241.167:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        String sql=null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(javaObject);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();
        try{
        	sql="insert into " + table + " (name, data) values(?,?)";
            ps=conn.prepareStatement(sql);
            ps.setObject(1, name);
            ps.setObject(2, data);
            ps.executeUpdate();        	
        }
        catch(MySQLIntegrityConstraintViolationException e){
        	sql="delete from " + table + " where id=(select id from (select * from "+ table + ") as whatever where name like ?)";
            ps=conn.prepareStatement(sql);
            ps.setObject(1, name);
            ps.executeUpdate();
            
            sql="insert into " + table + " (name, data) values(?,?)";
            ps=conn.prepareStatement(sql);
            ps.setObject(1, name);
            ps.setObject(2, data);
            ps.executeUpdate();
        }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    public Object getObject(String table, String name) throws Exception
    {
        Object rmObj=null;
        Connection conn= DriverManager.getConnection("jdbc:mysql://52.36.241.167:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="select * from " + table + " where name LIKE ?";
        
        ps=conn.prepareStatement(sql);
        ps.setObject(1, name);
        rs=ps.executeQuery();

        if(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {

            bais = new ByteArrayInputStream(rs.getBytes("data"));

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