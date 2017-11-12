import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MyFrame extends JFrame{
	public MyFrame(){
		JFrame hs = new JFrame("Hotel system");
		hs.setSize(300,200);
		hs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hs.setTitle("Hotel System");
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("label"));
		panel.add(new JButton("My Button"));
		panel.add(new JTextField("textfirld 20 cols", 20));
		//setText(), getText()
		panel.add(new JTextArea("JTextArea 10 cols 2 rows",2,10));
		//setText(), getText(), getRow(),setRow(),getColumns(),set...
		panel.add(new JCheckBox("JCheckbox select", true));
		panel.add(new JCheckBox("JCheckbox not select", false));
		//boolean isSelected(), setSelect(), getText(), setText(); 
		//fu xuan kuang
		//panel.add(new ButtonGroup());
		panel.add(new JRadioButton("JRadioButton", true));
		panel.add(new JRadioButton("JRadioButton", false));
		//dan xuan kuang
		
		hs.setContentPane(panel);
	}
}
