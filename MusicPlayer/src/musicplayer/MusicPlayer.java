package musicplayer;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MusicPlayer extends JApplet implements ActionListener{
	//定义音频对象
	AudioClip audioClip;	
	JComboBox audioList;
	//按钮
	JButton buttonplay;
	JButton buttonloop;
	JButton buttonstop;
	//初始化
	public void init(){
		buttonplay = new JButton("播放");
		buttonloop = new JButton("循环");
		buttonstop = new JButton("停止");
		
		//添加监听事件
		buttonplay.addActionListener(this);
		buttonloop.addActionListener(this);
		buttonstop.addActionListener(this);

		//music列表
		String name1 = "Faded";
		String name2 = "Good Life";
		String name3 = "Rolling in the Deep";

			audioList = new JComboBox();		

			audioList.addItem(name1);
			audioList.addItem(name2);
			audioList.addItem(name3);

		JPanel panel = new JPanel();

		panel.add(audioList);
		panel.add(buttonplay);
		panel.add(buttonstop);
		panel.add(buttonloop);		

		add(panel,BorderLayout.NORTH);
		//运行窗口大小
		this.setSize(600,100);
	}
	//事件监听
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == buttonplay){
			//如果还处于播放状态，则先停止
			if(audioClip != null){
				audioClip.stop();
			}
			audioClip = getSelectedAudioClip();
			audioClip.play();
		}
		if(e.getSource() == buttonstop){
			if(audioClip != null){
				audioClip.stop();
			}
			audioClip = getSelectedAudioClip();
			audioClip.stop();
		}
		if(e.getSource() == buttonloop){
			if(audioClip != null){
				audioClip.stop();
			}
			audioClip = getSelectedAudioClip();
			audioClip.loop();
		}
	}
	//返回选中对象
	private AudioClip getSelectedAudioClip() {
		// TODO Auto-generated method stub
		System.out.println(audioList.getSelectedItem());		
		String selected = audioList.getSelectedItem().toString();

		//返回选中项对象
		return getAudioClip(getCodeBase(),"music/" + selected + ".wav");
	}
}
