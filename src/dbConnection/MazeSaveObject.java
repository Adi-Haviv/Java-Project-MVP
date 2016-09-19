package dbConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import algorithms.mazeGenerators.Maze3d;



public class MazeSaveObject {
	
    public Maze3d maze=null;

    public Maze3d getMaze() {
        return maze;
    }

    public void setMaze(Maze3d Maze) {
        this.maze = Maze;
    }
    
    public  void saveObject(String name) throws Exception
    {
        try{
        Connection conn= DriverManager.getConnection("jdbc:mysql://54.69.232.190:3306/damn-java", "root", "password");
        PreparedStatement ps=null;
        String sql=null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(maze);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();


        sql="insert into mazes (name,maze) values (?,?)";
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


    public Maze3d getObject(String name) throws Exception
    {
        Connection conn = DriverManager.getConnection("jdbc:mysql://54.69.232.190:3306/damn-java", "root", "password");
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="select * from mazes where name LIKE '?'";

        ps=conn.prepareStatement(sql);
        ps.setObject(1, name);
        rs=ps.executeQuery();

        if(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {

            bais = new ByteArrayInputStream(rs.getBytes("maze"));

            ins = new ObjectInputStream(bais);

            Maze3d maze = (Maze3d) ins.readObject();

            ins.close();
            
            return maze;
            }
            catch (Exception e) {

            e.printStackTrace();
            }

        }

        return null;
    }
}