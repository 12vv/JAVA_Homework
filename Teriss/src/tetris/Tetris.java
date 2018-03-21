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
	//������
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
			int t = JOptionPane.showConfirmDialog(null, "��ʼ����Ϸ��","����Ϸ",JOptionPane.YES_NO_OPTION);
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
			JOptionPane.showMessageDialog(null, "��Ϸ����\n'��'�ı䷽����̬ \n'��'�ӿ췽�������ٶ�\n'��'���Ʒ�������\n'��'���Ʒ�������");
  }
}

	public static void main(String[] args) {
		Tetris frame = new Tetris();
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu set = new JMenu("����");
		JMenu help = new JMenu("����");
		JMenu gameui = new JMenu("��Ϸ����");
		JMenuItem item1 = new JMenuItem("����Ϸ");
		JMenuItem item2 = new JMenuItem("��Ϸ����");
		JMenuItem item3 = new JMenuItem("����");
		JMenuItem item7 = new JMenuItem("���а�");
		JMenuItem item4 = new JMenuItem("Windows���");
		JMenuItem item5 = new JMenuItem("Nimbus���");
		JMenuItem item6 = new JMenuItem("Motif���");
		item1.addActionListener(new newGame());
		item2.addActionListener(new introGame());
		JPanel panel2 = new JPanel();
		JLabel label3 = new JLabel("����:");
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
				JOptionPane.showMessageDialog(null, "���ڣ�\n@author Garrick\nversion:1.0.0\n�������һ�� Ҷ���� 3216004762");
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

//����˹������
class TetrisBlock extends JPanel implements KeyListener{
	public Timer timer;
	//��������
    private int blockType = -1;
    //����״̬
    private int blockState = -1;    
    //��һ���������ͺ�״̬
    private int nextblockType;    
    private int nextblockState;    
    //�÷�
    private static int score = 0;
    
    
    //��������
    private int x;
    private int y;
    
    private int i = 0;
    private int j = 0;
    
    static //��¼��Ϸ״̬
    int flag = 0;
	int level = 1; 
    //12*22��ͼ
    int[][] map = new int[13][23];
    
    int delay;
    
    //�������
    private String name;
    
	//���췽��
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
	//���ַ��鼰����ת��ʽ
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
           
    //�����·���            
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
    		
    		String name = JOptionPane.showInputDialog("��Ĵ�����");
    		if(name == ""){JOptionPane.showMessageDialog(null, "����Ϊ��", "��Ϣ", JOptionPane.ERROR_MESSAGE);}
    		else
    		{	
    			Datab.insertdata(name,score);
    			int t = JOptionPane.showConfirmDialog(null, "GAME OVER!�Ƿ����¿�ʼ��","��Ϸ����",JOptionPane.YES_NO_OPTION);
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
    //�߿�
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
    //map��ʼ��
    public void drawMap(){
    	for(i = 0; i < 12; i++){
    		for(j = 0; j < 22; j++){
    			map[i][j] = 0;
    		}
    	}
    }
    
    //��ת
    public void turn(){
    	int temp = blockState;
    	blockState = (blockState + 1) % 4;
    	if(execute(x, y, blockType, blockState) == 0){
    		blockState = temp;
    	}
    }

    //����
    public void left(){
    	if(execute(x - 1, y, blockType, blockState) == 1){
    		x -= 1;
    	}
    	repaint();
    }
    //����
    public void right(){
    	if(execute(x + 1, y, blockType, blockState) == 1){
    		x += 1;
    	} 
    	repaint();
    }
    //����
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
    
    //�Ƿ�ִ��
    public int execute(int x, int y, int blockType, int blockState){
    	for(int a = 0; a < 4; a++){
    		for(int b = 0; b < 4; b++){
    			if(((shapes[blockType][blockState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1)) || ((shapes[blockType][blockState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2))) 
    			{
    				//System.out.println("����ִ���ˣ�");
    				return 0;
    			}
    		}
    	}
    	return 1;
    }
    //����
    public void decline(){
        int c = 0;
        for (int b = 0; b < 22; b++) {
            for (int a = 1; a < 12; a++) {
                if (map[a][b] == 1) { 
                    c = c + 1;
                    //��b������
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
    //��Ϸ����
    public int over(int x, int y){
        if (execute(x, y, blockType, blockState) == 0) {
        	System.out.println("��Ϸ������");
            return 1;
        }
        return 0;    	
    }
    //������ӵ�map
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
  
    //������
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //��ǰ����
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
            	//�����Ѿ����µķ���
                if (map[i][j] == 1) {
                    g.fillRect(i * 10, j * 10, 10, 10);
 
                }
                //Χǽ
                if (map[i][j] == 2) {
                    g.drawRect(i * 10, j * 10, 10, 10);
 
                }
            }   
            Font f=new Font("",Font.BOLD,15);
            g.setFont(f);
            g.drawString("����:" + getScore(), 135, 100);
            g.drawString("��ǰ����:" + level, 135, 125);
            g.drawString("��һ����:" , 135, 20);
            

            g.drawString("[A]����Ϸ" , 135, 165);
            g.drawString("[S]��ͣ" , 135, 190);
            g.drawString("[D]����" , 135, 215);
        }    
        //������һ����
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
	//���̼����¼�
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
			int t = JOptionPane.showConfirmDialog(null, "��ʼ����Ϸ��","����Ϸ",JOptionPane.YES_NO_OPTION);
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
//				//��Ϸ����ֱ��ֹͣ
//				if(flag == 0) return;
//				//���¿�ʼ��Ϸ
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
			//���¿�ʼ��Ϸ
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

