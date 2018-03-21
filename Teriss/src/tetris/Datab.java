package tetris;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.Connection;

public class Datab {
	private TetrisBlock temp;
	
	public TetrisBlock gettemp() {
		return temp;
	}
	public void settemp(TetrisBlock temp) {
		this.temp = temp;
	}	
	
	static Datab b = new Datab();
	
	 public static void mydata() {
	        Connection conn = null;
	        try {
	 
	            // 加载mysql驱动程序
	            Class.forName("com.mysql.jdbc.Driver");
	            // 连接localhost上的mysql,并指定使用test数据库
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/mdata?useUnicode=true&characterEncoding=utf-8&useSSL=false",
	                                               "root", "Akashi12");
	            if (!conn.isClosed()) {
	                System.out.println("Connected database successfully..."); //验证是否连接成功
	            }
	 
	            Statement statement = conn.createStatement();
	            //查询数据
	            ResultSet rs = statement.executeQuery("select * from record order by player_score DESC limit 0,3");
	 
	            int i = 0;
	            String te = "";
	            //输出结果集（类似.net中的DataSet/DataTable）
	            while (rs.next()) {
	            	te += "第" + ++i + "名" + "  name：" + rs.getString("player_name") + "  score：" + rs.getInt("player_score");
	            	te = te + "\n";
	            }
	            JOptionPane.showMessageDialog(null, te);	            
	            rs.close();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                    conn = null;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	    }
	 public static void insertdata(String name,int score) {
		 
	        Connection conn = null;
	        try {
	 
	            // 加载mysql驱动程序
	            Class.forName("com.mysql.jdbc.Driver");
	            // 连接localhost上的mysql,并指定使用test数据库，用户名为root,密码为***
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/mdata?useUnicode=true&characterEncoding=utf-8&useSSL=false",
	                                               "root", "Akashi12");
	            if (!conn.isClosed()) {
	                System.out.println("数据库连接成功！"); //验证是否连接成功
	            }
	 
	            Statement statement = conn.createStatement();
	            
	            String sql = "INSERT INTO record VALUES (null,'" + name+ "',"+ score + ")";
	            statement.executeUpdate(sql);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                    conn = null;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	    }
	 
}

