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
	
    public Solution<Position> solution=null;

    public Solution<Position> getSolution() {
        return solution;
    }

    public void setSolution(Solution<Position> solution) {
        this.solution = solution;
    }
    
    public  void saveObject(String name) throws Exception
    {
        try{
        Connection conn= DriverManager.getConnection("jdbc:mysql://54.69.232.190:3306/damn-java", "root", "password");
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
        Connection conn = DriverManager.getConnection("jdbc:mysql://54.69.232.190:3306/damn-java", "root", "password");
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