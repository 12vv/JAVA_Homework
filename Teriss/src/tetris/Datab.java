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
	 
	            // ����mysql��������
	            Class.forName("com.mysql.jdbc.Driver");
	            // ����localhost�ϵ�mysql,��ָ��ʹ��test���ݿ�
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/mdata?useUnicode=true&characterEncoding=utf-8&useSSL=false",
	                                               "root", "Akashi12");
	            if (!conn.isClosed()) {
	                System.out.println("Connected database successfully..."); //��֤�Ƿ����ӳɹ�
	            }
	 
	            Statement statement = conn.createStatement();
	            //��ѯ����
	            ResultSet rs = statement.executeQuery("select * from record order by player_score DESC limit 0,3");
	 
	            int i = 0;
	            String te = "";
	            //��������������.net�е�DataSet/DataTable��
	            while (rs.next()) {
	            	te += "��" + ++i + "��" + "  name��" + rs.getString("player_name") + "  score��" + rs.getInt("player_score");
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
	 
	            // ����mysql��������
	            Class.forName("com.mysql.jdbc.Driver");
	            // ����localhost�ϵ�mysql,��ָ��ʹ��test���ݿ⣬�û���Ϊroot,����Ϊ***
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/mdata?useUnicode=true&characterEncoding=utf-8&useSSL=false",
	                                               "root", "Akashi12");
	            if (!conn.isClosed()) {
	                System.out.println("���ݿ����ӳɹ���"); //��֤�Ƿ����ӳɹ�
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

