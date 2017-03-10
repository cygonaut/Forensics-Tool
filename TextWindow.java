import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.*;

public class TextWindow {
	public static boolean checked;
	public JTextField window;
	private Callable<?> function;

	public TextWindow(String label) {
		window = new JTextField(label);

	}

	public void keyUpdate(JTextField win) {
		win.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					
					try {
						function.call();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	public  void setCallable(Callable<?> func) {
		function = func;
	}
}
