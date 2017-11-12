import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class add_ser extends JFrame{
	JPanel panel;
	JLabel title,ctime,amount;
	ArrayList<String> rno = new ArrayList<String>();
	JTextField desc,amounto;
	JButton submit,reset,back;
	add_ser(){
		panel = new JPanel(){	
        	public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("3.jpg");
                g.drawImage(icon.getImage(), 0, 0,                  
                this.getSize().width,
                this.getSize().height,
                this);
            }
        };
		setSize(800,600);
        setTitle("Hotel System -- add service");
        this.setLocation(200,50);
       
        panel.setLayout(null);
        title = new JLabel("Add service");
		title.setBounds(300, 50, 200, 50);
		panel.add(title);
		String[] name = {"Room number : ", "Date : ", "Service type : ","Amount : "};
        for(int i=0;i<4;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,120+50*i, 100, 50);
        	panel.add(li);
        }
        getRno();
        int size = rno.size();
        String[] srno = new String[size];
        for(int i=0;i<size;i++){
        	srno[i] = (String)rno.get(i); 
        }
        JComboBox<Object> roomno = new JComboBox<Object>(srno);
        roomno.setBounds(350, 130, 100, 30);
        panel.add(roomno);
        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String current = sdf.format(time);
        System.out.println(current);
        ctime = new JLabel(current);
        ctime.setBounds(350, 180, 100, 20);
        panel.add(ctime);
        String[] type = {"restaurant-R","laundry-L","transportation-T","wifi-W","others-O"};
        JComboBox<Object> choose_type = new JComboBox<Object>(type);
        choose_type.setBounds(350, 230, 120, 30);
        panel.add(choose_type);
        desc = new JTextField();
    	desc.setBounds(500, 230, 150, 30);
    	panel.add(desc);
    	desc.setVisible(false);
    	amount = new JLabel();
        amount.setBounds(350, 280, 100, 30);
        panel.add(amount);
        amounto = new JTextField();
        amounto.setBounds(350, 280, 100, 30);
        panel.add(amounto);
        amounto.setVisible(false);
        choose_type.addItemListener(new ItemListener(){
        	@Override
        	public void itemStateChanged(ItemEvent e){
        		if(e.getStateChange() == ItemEvent.SELECTED){
        			if(choose_type.getSelectedItem().toString() == "others-O"){
        	        	desc.setVisible(true);
        	        	amount.setVisible(false);
        	        	amounto.setVisible(true);
        	        }
        	        else{
        	        	desc.setVisible(false);
        	        	amounto.setVisible(false);
        	        	amount.setVisible(true);
        	        	if(choose_type.getSelectedItem().toString() == "restaurant-R")
        	        		amount.setText("40");
        	        	if(choose_type.getSelectedItem().toString() == "laundry-L")
        	        		amount.setText("20");
        	        	if(choose_type.getSelectedItem().toString() == "transportation-T")
        	        		amount.setText("30");
        	        	if(choose_type.getSelectedItem().toString() == "wifi-W")
        	        		amount.setText("10");
        	        }
        		}
        	}
        });
        submit = new JButton("submit");
        submit.setBounds(250, 360, 80, 40);
        panel.add(submit);     
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit");
                if(amount.getText()==""&&amounto.getText()==""){
                	System.out.println("not full info");
                }
                else{
                	//System.out.println(cname.getText());
                	String srno = roomno.getSelectedItem().toString();
                	String sdate = ctime.getText();
                	String sertype = choose_type.getSelectedItem().toString();
                	if(sertype == "restaurant-R")
                		sertype = "R";
                	if(sertype == "laundry-L")
                		sertype = "L";
                	if(sertype == "transportation-T")
                		sertype = "T";
                	if(sertype == "wifi-W")
                		sertype = "W";
                	String describe="";
                	String samount = amount.getText();
                	if(sertype == "others-O"){
                		sertype = "O";
                		describe = desc.getText();
                		samount = amounto.getText();
                	}
                	try {
            			Class.forName(com.mysql.jdbc.Driver.class.getName());
            			String url = "jdbc:mysql://localhost/hotel system";
            			String login = "root";
            			String password = "";
            			Connection con;
            			con = DriverManager.getConnection(url, login, password);
            			con.setAutoCommit(false);
            			try{
            				Statement stmt = (Statement) con.createStatement();
            				int rs = stmt.executeUpdate("insert into services (rno,datetype,stype,sdesc,amount) values('"+srno+"','"+sdate+"','"+sertype+"','"+describe+"','"+samount+"')");
            				if(rs > 0){
            					System.out.println("add success");
            					reminder("successfully");
            				}
            					
            				else{
            					reminder("failed");
            					System.out.println("add failed");
            				}
            				con.commit();
            			}catch(Exception ex){
            				con.rollback();
            				ex.printStackTrace();
            				System.out.println("failed");
            			}
            			
            		}catch(ClassNotFoundException | SQLException ex){
            			System.out.println("Can¡¯t load the Driver");
            		}
                	
                }
            }
        });
        reset = new JButton("Reset");
        reset.setBounds(350, 360, 70, 40);
        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desc.setText("");
            	amounto.setText("");
            	amount.setText("");
            }
        });
        back = new JButton("Back");
        back.setBounds(450, 360, 70, 40);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//System.exit(0);
            	dispose();
            	TestFlow myflow = new TestFlow();
                myflow.setVisible(true);
                myflow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        this.add(panel);
	}
	public int getRno(){
		try {
			Class.forName(com.mysql.jdbc.Driver.class.getName());
			String url = "jdbc:mysql://localhost/hotel system";
			String login = "root";
			String password = "";
			Connection con;
			con = DriverManager.getConnection(url, login, password);
			con.setAutoCommit(false);
			try{
				//System.out.println(rt);
				Statement stmt = (Statement) con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `rooms` WHERE status like 'n' or status like 'N'");
				while(rs.next()){
					int rno1 = rs.getInt(1);
					System.out.println(rno1);
					String s = rno1+"";
					rno.add(s);
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can¡¯t load the Driver");
		}
		return 0;
	}
	public void reminder(String s){
    	JFrame jf = new JFrame();
    	//JPanel jp = new JPanel();
    	//jf.add(jp);
    	jf.setSize(300,200);
    	jf.setLayout(null);
        jf.setTitle("reminder");
        jf.setLocation(450,180);
        //jp.setLayout(null);;
        jf.setVisible(true);
        JLabel l1 = new JLabel();
        l1.setText("Add "+s+" !");
        l1.setBounds(60, 30, 200, 50);
        JButton b1 = new JButton("ok");
        b1.setBounds(120, 100, 60, 30);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //dispose();
            	jf.setVisible(false);
		        //rt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        jf.add(l1);
        jf.add(b1);
    }
}
