package Audio;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SoundDemo extends JFrame {

	public SoundDemo() {
		SoundEffect.init();
		SoundEffect.volume = SoundEffect.Volume.ON;
		final SoundTrack backgroundMusic = SoundTrack.TRACK_ONE;

		// Set up UI components
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnSound1 = new JButton("Select");
		btnSound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundEffect.SELECT.play();
			}
		});
		cp.add(btnSound1);
		
		JButton btnSound2 = new JButton("Swap");
		btnSound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundEffect.SWAP.play();
			}
		});
		cp.add(btnSound2);
		
		JButton btnSound3 = new JButton("Play");
		btnSound3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.play();
			}
		});
		cp.add(btnSound3);
		
		JButton btnSound4 = new JButton("Stop");
		btnSound4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.stop();
			}
		});
		cp.add(btnSound4);
		
		JButton btnSound5 = new JButton("Pause");
		btnSound5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.pause();
			}
		});
		cp.add(btnSound5);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test SoundEffect");
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SoundDemo();
	}
}