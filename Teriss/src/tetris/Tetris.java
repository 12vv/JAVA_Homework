package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Tetris extends JFrame {
	//构造器
	public Tetris(){
		
		TetrisBlock tblock = new TetrisBlock();
//		Runnable timer = tblock.new timerunner();
//		Thread t = new Thread(timer);
//		t.start();	
		addKeyListener(tblock);
		add(tblock);
	}
  static class newGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TetrisBlock.flag = 0;
			int t = JOptionPane.showConfirmDialog(null, "开始新游戏？","新游戏",JOptionPane.YES_NO_OPTION);
			if(t == 0){
				TetrisBlock.flag = -1;				
			}
  	}
  }
  static class stopGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TetrisBlock.flag = 0;
  }
}
  static class introGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//TetrisBlock.flag = 0;
			JOptionPane.showMessageDialog(null, "游戏规则：\n'↑'改变方块形态 \n'↓'加快方块下落速度\n'←'控制方块左移\n'→'控制方块右移");
  }
}

	public static void main(String[] args) {
		Tetris frame = new Tetris();
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu set = new JMenu("设置");
		JMenu help = new JMenu("帮助");
		JMenu gameui = new JMenu("游戏界面");
		JMenuItem item1 = new JMenuItem("新游戏");
		JMenuItem item2 = new JMenuItem("游戏规则");
		JMenuItem item3 = new JMenuItem("关于");
		JMenuItem item7 = new JMenuItem("排行榜");
		JMenuItem item4 = new JMenuItem("Windows风格");
		JMenuItem item5 = new JMenuItem("Nimbus风格");
		JMenuItem item6 = new JMenuItem("Motif风格");
		item1.addActionListener(new newGame());
		item2.addActionListener(new introGame());
		JPanel panel2 = new JPanel();
		JLabel label3 = new JLabel("级别:");
		JPanel panel1 = new JPanel();
		JLabel label2 = new JLabel("");
		frame.add(panel1,BorderLayout.EAST);
		set.add(item1);
		help.add(item2);
		help.add(item3);
		menu.add(set);
		menu.add(help);
		menu.add(gameui);
		menu.add(item7);
		gameui.add(item4);
		gameui.add(item5);
		gameui.add(item6);
		
		item3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "关于：\n@author Garrick\nversion:1.0.0\n软件工程一班 叶嘉怡 3216004762");
			}
			
		});
		
		item7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Datab.mydata();
			}
			
		});
		
		item4.addActionListener(new ActionListener(){		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
		});
		item5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
		});
		item6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Tetris");
		frame.setSize(250,350);
		frame.setVisible(true);
	}

}

//俄罗斯方块类
class TetrisBlock extends JPanel implements KeyListener{
	public Timer timer;
	//方块类型
    private int blockType = -1;
    //方块状态
    private int blockState = -1;    
    //下一个方块类型和状态
    private int nextblockType;    
    private int nextblockState;    
    //得分
    private static int score = 0;
    
    
    //方块坐标
    private int x;
    private int y;
    
    private int i = 0;
    private int j = 0;
    
    static //记录游戏状态
    int flag = 0;
	int level = 1; 
    //12*22地图
    int[][] map = new int[13][23];
    
    int delay;
    
    //玩家名字
    private String name;
    
	//构造方法
	TetrisBlock(){
        newBlock();
		drawMap();
		outline();
		delay = 1000 - 200 * (level-1);		
//		Runnable timer = new timerunner();
//		Thread t = new Thread(timer);
//		t.start();
        Timer timer = new Timer(delay, new TimerListener());
        timer.start();
        flag = 1;

	}
	//七种方块及其旋转样式
    private final int shapes[][][] = new int[][][] {
            // i
            { { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
              { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
            // s
            { { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
            // z
            { { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
            // j
            { { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
              { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // o
            { { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // l
            { { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
              { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // t
            { { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
              { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
              { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } 
           };
           
    //生成新方块            
    public void newBlock(){
    	if(blockType == -1 || blockState == -1){
    		blockType = (int)(Math.random() * 1000) % 7; 
    		blockState = (int)(Math.random() * 1000) % 4; 
    	}else{
    		blockType = nextblockType;
    		blockState = nextblockState;
    	}
    	x = 4;
    	y = 0;
    	if (over(x, y) == 1) {
    		flag = 0;
    		
    		String name = JOptionPane.showInputDialog("你的大名？");
    		if(name == ""){JOptionPane.showMessageDialog(null, "不能为空", "消息", JOptionPane.ERROR_MESSAGE);}
    		else
    		{	
    			Datab.insertdata(name,score);
    			int t = JOptionPane.showConfirmDialog(null, "GAME OVER!是否重新开始？","游戏结束",JOptionPane.YES_NO_OPTION);
	    		if(t == 0){
	//    			timer.restart();   
	    			flag = 1;
	    			drawMap();
	    			outline();    		
	    			setScore(0);    			
	    		}	    			
    		}
    	}
		nextblockType = (int)(Math.random() * 1000) % 7; 
		nextblockState = (int)(Math.random() * 1000) % 4;    	
    }
    //边框
    public void outline(){
        for (i = 0; i < 12; i++) {
            map[i][21] = 2;
 //           map[i][0] = 2;
        }
        for (j = 0; j < 22; j++) {
            map[11][j] = 2;
            map[0][j] = 2;
        }    	
    }
    //map初始化
    public void drawMap(){
    	for(i = 0; i < 12; i++){
    		for(j = 0; j < 22; j++){
    			map[i][j] = 0;
    		}
    	}
    }
    
    //旋转
    public void turn(){
    	int temp = blockState;
    	blockState = (blockState + 1) % 4;
    	if(execute(x, y, blockType, blockState) == 0){
    		blockState = temp;
    	}
    }

    //左移
    public void left(){
    	if(execute(x - 1, y, blockType, blockState) == 1){
    		x -= 1;
    	}
    	repaint();
    }
    //右移
    public void right(){
    	if(execute(x + 1, y, blockType, blockState) == 1){
    		x += 1;
    	} 
    	repaint();
    }
    //下落
    public void down(){
    	if(execute(x, y+1, blockType, blockState) == 1){
    		y += 1;
    	} 
    	decline();
        if (execute(x, y+1, blockType, blockState) == 0) {
            add(x, y, blockType, blockState);
            newBlock();
            decline();
        }
        repaint();
    }
    
    //是否执行
    public int execute(int x, int y, int blockType, int blockState){
    	for(int a = 0; a < 4; a++){
    		for(int b = 0; b < 4; b++){
    			if(((shapes[blockType][blockState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1)) || ((shapes[blockType][blockState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2))) 
    			{
    				//System.out.println("不能执行了！");
    				return 0;
    			}
    		}
    	}
    	return 1;
    }
    //消行
    public void decline(){
        int c = 0;
        for (int b = 0; b < 22; b++) {
            for (int a = 1; a < 12; a++) {
                if (map[a][b] == 1) { 
                    c = c + 1;
                    //第b行满了
                    if (c == 10) {          
                        setScore(getScore() + 10);
                        level = getScore() / 100 +1;
                        for (int d = b; d > 0; d--) {
                            for (int e = 0; e < 11; e++) {
                                map[e][d] = map[e][d - 1]; 
                            }
                        }
                    }
                }
            }
            c = 0;
        }  
    }
    //游戏结束
    public int over(int x, int y){
        if (execute(x, y, blockType, blockState) == 0) {
        	System.out.println("游戏结束！");
            return 1;
        }
        return 0;    	
    }
    //方块添加到map
    public void add(int x, int y, int blockType, int blockState){
    	j = 0;
    	for(int a = 0; a < 4; a++){
    		for(int b = 0; b < 4; b++){
    			if(map[x + b + 1][y + a] == 0){
    				map[x + b + 1][y + a] = shapes[blockType][blockState][j];
    			}
    			j++;
    		}
    	}
    }
  
    //画方块
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //当前方块
        for(j = 0; j < 16; j++){
        	if(shapes[blockType][blockState][j] == 1){
        		Color c1 = new Color(130,130,130);
        		g.setColor(c1);
        		g.fillRect((j % 4 + x + 1) * 10, (j / 4 + y) * 10, 10, 10);
        		Color c2 = new Color(0,0,0);
        		g.setColor(c2);
        	}
        }
        for (j = 0; j < 22; j++) {
            for (i = 0; i < 12; i++) {
            	//保持已经落下的方块
                if (map[i][j] == 1) {
                    g.fillRect(i * 10, j * 10, 10, 10);
 
                }
                //围墙
                if (map[i][j] == 2) {
                    g.drawRect(i * 10, j * 10, 10, 10);
 
                }
            }   
            Font f=new Font("",Font.BOLD,15);
            g.setFont(f);
            g.drawString("分数:" + getScore(), 135, 100);
            g.drawString("当前级别:" + level, 135, 125);
            g.drawString("下一方块:" , 135, 20);
            

            g.drawString("[A]新游戏" , 135, 165);
            g.drawString("[S]暂停" , 135, 190);
            g.drawString("[D]继续" , 135, 215);
        }    
        //绘制下一方块
        j = 0;
    	for(int a = 4; a < 8; a++){
    		for(int b = 14; b < 18; b++){
    			if(shapes[nextblockType][nextblockState][j] == 1){
    				g.fillRect(b * 10, a * 10, 10, 10);
    			}
    			j++;
    		}
    	}
    }
	//键盘监听事件
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			down();
			break;
		case KeyEvent.VK_UP:
			turn();
			break;
		case KeyEvent.VK_RIGHT:
			right();
			break;
		case KeyEvent.VK_LEFT:
			left();
			break;
		case 83:
			flag = 0;
			break;
		case 65:
		    flag = 0;
			int t = JOptionPane.showConfirmDialog(null, "开始新游戏？","新游戏",JOptionPane.YES_NO_OPTION);
			if(t == 0){
				flag = -1;				
			}
			break;
		case 68:
			flag = 1;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
//	class timerunner implements Runnable{
//
//		@Override
//		public void run() {
//			System.out.println("running");
//			// TODO Auto-generated method stub
//		while(true){
//			System.out.println("i'm in");
//            try {
//				repaint();
//				//游戏结束直接停止
//				if(flag == 0) return;
//				//重新开始游戏
//		    	if(flag == -1){
//		    		repaint();
//					flag = 1;
//					System.out.println("a");
//			        newBlock();
//					drawMap();
//					outline();  		
//					setScore(0);	
//					return;
//		    	}
//	            if (execute(x, y + 1, blockType, blockState) == 1) {
//	                y = y + 1;
//	            }
//	            if (execute(x, y + 1, blockType, blockState) == 0) {
//	                add(x, y, blockType, blockState);                       
//	                newBlock();
//	                }
//	            decline();            	
//				Thread.sleep(delay);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}            
//            }
//		}
//		}
//
//	}
	
	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			repaint();
			
			if(flag == 0) return;
			//重新开始游戏
	    	if(flag == -1){
	    		repaint();
				flag = 1;
		        newBlock();
				drawMap();
				outline();  		
				setScore(0);	
				return;
	    	}
            if (execute(x, y + 1, blockType, blockState) == 1) {
                y = y + 1;
            }
            if (execute(x, y + 1, blockType, blockState) == 0) {
                add(x, y, blockType, blockState);                       
                newBlock();
                }
            decline();
            }			
	}

}

