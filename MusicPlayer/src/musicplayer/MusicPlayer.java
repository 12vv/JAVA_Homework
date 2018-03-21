package musicplayer;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MusicPlayer extends JApplet implements ActionListener{
	//������Ƶ����
	AudioClip audioClip;	
	JComboBox audioList;
	//��ť
	JButton buttonplay;
	JButton buttonloop;
	JButton buttonstop;
	//��ʼ��
	public void init(){
		buttonplay = new JButton("����");
		buttonloop = new JButton("ѭ��");
		buttonstop = new JButton("ֹͣ");
		
		//��Ӽ����¼�
		buttonplay.addActionListener(this);
		buttonloop.addActionListener(this);
		buttonstop.addActionListener(this);

		//music�б�
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
		//���д��ڴ�С
		this.setSize(600,100);
	}
	//�¼�����
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == buttonplay){
			//��������ڲ���״̬������ֹͣ
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
	//����ѡ�ж���
	private AudioClip getSelectedAudioClip() {
		// TODO Auto-generated method stub
		System.out.println(audioList.getSelectedItem());		
		String selected = audioList.getSelectedItem().toString();

		//����ѡ�������
		return getAudioClip(getCodeBase(),"music/" + selected + ".wav");
	}
}
