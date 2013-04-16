import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SoundDemo extends JFrame {

	public SoundDemo() {
		Sound.init();
		Sound.volume = Sound.Volume.ON;

		// Set up UI components
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnSound1 = new JButton("Select");
		btnSound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sound.SELECT.play();
			}
		});
		cp.add(btnSound1);
		
		JButton btnSound2 = new JButton("Swap");
		btnSound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sound.SWAP.play();
			}
		});
		cp.add(btnSound2);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test Sound");
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SoundDemo();
	}
}