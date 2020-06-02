package console;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;

public class CustomAdjustmentListener implements AdjustmentListener {

	JFrame frame;
	
	public CustomAdjustmentListener(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		frame.repaint();
	}
}
