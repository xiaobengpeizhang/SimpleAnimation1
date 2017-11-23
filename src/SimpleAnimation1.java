import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class SimpleAnimation1 {
	
	int x = 70;
	int y = 70;
	
	static MyDrawPanel panel;
	static JFrame f = new JFrame("My First Music Video");
	
	public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception ex) {
			
		}
		return event;
	}
	
	public static void main(String[] args) {
		 SimpleAnimation1 gui = new SimpleAnimation1();
		 // gui.go();
		 gui.play();
	}
	
	public void play() {
		setUpGui();
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			int[] eventsIWant = {127};
			sequencer.addControllerEventListener(panel, eventsIWant);
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			
			int r = 0;
			
			for (int i = 0; i < 60; i += 4) {
				r = (int)(Math.random() * 50 + 1);
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, r, 100, i + 2));
			}
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setUpGui() {
		panel = new MyDrawPanel();
		f.setContentPane(panel);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);
	}
	
	
	public void go() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyDrawPanel drawPanel = new MyDrawPanel();
		frame.getContentPane().add(drawPanel);
		frame.setSize(800, 800);
		frame.setVisible(true);
		
		for(int i = 0; i < 330; i++) {
			x++;
			y++;
			
			drawPanel.repaint();
			
			try {
				Thread.sleep(50);
			} catch (Exception ex) {}
		}
	}

	class MyDrawPanel extends JPanel implements ControllerEventListener{
		
		boolean msg = false;
		
		public void paintComponent(Graphics g) {
			if (msg) {
				Graphics2D g2 = (Graphics2D) g;
				int r = (int)(Math.random() * 250);
				int gc = (int)(Math.random() * 250);
				int b = (int)(Math.random() * 250);
				g2.setColor(new Color(r, gc, b));
				
				int x = (int)(Math.random() * 40 + 10);
				int y = (int)(Math.random() * 40 + 10);
				int ht = (int)(Math.random() * 120 + 10);
				int width = (int)(Math.random() * 120 +10);
				
				g2.fillRect(x, y, width, ht);
				msg = false;
			}
			// g.setColor(Color.gray);
			// g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			// g.setColor(Color.green);
			// g.fillOval(x, y, 40, 40);
		}
		
		public void controlChange(ShortMessage event) {
			msg = true;
			repaint();
			try {
				Thread.sleep(500);
			} catch (Exception ex) {}
		}
	}

}


