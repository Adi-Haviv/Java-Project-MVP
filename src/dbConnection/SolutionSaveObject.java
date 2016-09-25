package dbConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public class SolutionSaveObject {
	
    public byte[] solution=null;

    public byte[] getSolution() {
        return solution;
    }

    public void setSolution(byte[] solution) {
        this.solution = solution;
    }
    
    public  void saveObject(String name) throws Exception
    {
        try{
	        Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
	        PreparedStatement ps=null;
	        String sql=null;
	
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	
	        oos.writeObject(solution);
	        oos.flush();
	        oos.close();
	        bos.close();
	
	        byte[] data = bos.toByteArray();
	
	
	        sql="insert into solutions (name,solution) values (?,?)";
	        ps=conn.prepareStatement(sql);
	        ps.setObject(1, name);
	        ps.setObject(2, data);
	        ps.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    public Solution<Position> getObject(String name) throws Exception
    {
        Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="select * from solutions where name LIKE '?'";

        ps=conn.prepareStatement(sql);
        ps.setObject(1, name);
        rs=ps.executeQuery();

        if(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {

            bais = new ByteArrayInputStream(rs.getBytes("solution"));

            ins = new ObjectInputStream(bais);

            Solution<Position> sol = (Solution<Position>)ins.readObject();

            ins.close();
            
            return sol;
            }
            catch (Exception e) {

            e.printStackTrace();
            }

        }

        return null;
    }
}