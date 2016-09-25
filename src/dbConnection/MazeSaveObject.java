package dbConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;



public class MazeSaveObject {
	
    public byte[] maze=null;

    public byte[] getMaze() {
        return maze;
    }

    public void setMaze(byte[] Maze) {
        this.maze = Maze;
    }
    
    public  void saveObject(String name) throws Exception
    {
        try{
	        Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
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
        Connection conn= DriverManager.getConnection("jdbc:mysql://52.37.202.40:3306/javadb", "javauser", "Ab123456!");
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="select * from mazes where name LIKE ?";

        ps=conn.prepareStatement(sql);
        ps.setObject(1, name);
        rs=ps.executeQuery();

        if(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {
            	
            bais = new ByteArrayInputStream(rs.getBytes("maze"));
            ArrayList<Byte> mazeByte = new ArrayList<Byte>();
            ins = new ObjectInputStream(bais);
            for (byte b : (byte[]) ins.readObject()) {
            	mazeByte.add(new Byte(b));
            }
            byte[] mazeArr = new byte[mazeByte.size()];
            int index = 0;
            for (Byte b : mazeArr){
            	mazeArr[index] = b.byteValue();
            	index++;
            }
            Maze3d maze = new Maze3d(mazeArr);

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