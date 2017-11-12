import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class add_room extends JFrame{
	JPanel panel;
	JLabel title,floor;
	JTextField rno;
	ArrayList<String> rtype = new ArrayList<String>();
	Button submit,reset,back;
	add_room(){
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
        setTitle("Hotel System -- add room");
        this.setLocation(200,50);
        //panel = new JPanel();
        panel.setLayout(null);
		title = new JLabel("Add room");
		title.setBounds(300, 50, 200, 50);
		panel.add(title);
		String[] name = {"Room number : ", "Type : ", "Floor : ","Status : "};
        for(int i=0;i<4;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,120+50*i, 100, 50);
        	panel.add(li);
        }
        rno = new JTextField("");
        rno.setBounds(350, 130, 150, 30);
        getRtype();
        int size = rtype.size();
        String[] srtype = new String[size];
        for(int i=0;i<size;i++){
        	srtype[i] = (String)rtype.get(i); 
        }
        JComboBox<Object> roomtype = new JComboBox<Object>(srtype);
        roomtype.setBounds(350, 180, 100, 30);
        panel.add(roomtype);
        floor = new JLabel("0");
        floor.setBounds(350, 230, 100, 30);
        panel.add(floor);
        rno.addKeyListener(new KeyListener(){//can write number
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			//enter
        			if(rno.getText() != ""){
        				String strno = rno.getText();
            			System.out.println(strno);
            			int i = Integer.parseInt(strno);
            			System.out.println(i);
            			
            			if(i>1000 && i<=9999){
            				int j = i/1000;
            				String f = j+"";
            				floor.setText(f);
            			}
        			}
        		}
        		else if(temp >= 48 && temp <= 57){
        			//number
        		}
        		else{
        			//no
        			e.consume();
        		}
        	}
        	@Override
        	public void keyReleased(KeyEvent e){
        		
        	}
        	@Override
        	public void keyPressed(KeyEvent e){
        		
        	}
        	
        });
        panel.add(rno);
        String[] sta = {"available-Y","occupied-N","maintenance-M"};
        JComboBox<Object> status = new JComboBox<Object>(sta);
        status.setBounds(350, 280, 100, 30);
        panel.add(status);
		this.add(panel);
		submit = new Button("submit");
        submit.setBounds(250, 360, 70, 40);
        panel.add(submit);     
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit");
                if(rno.getText()==""||floor.getText()==""){
                	System.out.println("not full info");
                }
                else{
                	//System.out.println(cname.getText());
                	String srno = rno.getText();
                	String sfloor = floor.getText();
                	String sroomtype = roomtype.getSelectedItem().toString();
                	String sstatus = status.getSelectedItem().toString();  
                	if(sstatus == "available-Y")
                		sstatus = "Y";
                	else if(sstatus == "occupied-N")
                		sstatus = "N";
                	else if(sstatus == "maintenance-M")
                		sstatus = "M";
                	try {
            			Class.forName(com.mysql.jdbc.Driver.class.getName());
            			String url = "jdbc:mysql://localhost/hotel system";
            			String login = "root";
            			String password = "";
            			Connection con;
            			con = DriverManager.getConnection(url, login, password);
            			con.setAutoCommit(false);
            			try{
            				Statement stmt1 = (Statement)con.createStatement();
            				ResultSet rs1 = stmt1.executeQuery("select * from rooms where rno = '"+srno+"'");
            				if(rs1.next()){
            					String s = rno+"";
            					reminder();
            				}
            				else{
            					Statement stmt = (Statement) con.createStatement();
                				int rs = stmt.executeUpdate("insert into rooms (rno,rtype,floor,status) values('"+srno+"','"+sroomtype+"','"+sfloor+"','"+sstatus+"')");
                				if(rs > 0){
                					System.out.println("add success");
                					reminder("successfully");
                				}
                					
                				else{
                					System.out.println("add failed");
                					reminder("failed");
                				}
                				con.commit();
            				}
            				
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
        reset = new Button("Reset");
        reset.setBounds(350, 360, 70, 40);
        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rno.setText("");
                floor.setText("");
            }
        });
        back = new Button("Back");
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
	}
	public void reminder(){
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
        l1.setText("This room already exist !");
        l1.setBounds(80, 30, 200, 50);
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
	public int getRtype(){
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
				ResultSet rs = stmt.executeQuery("select * from tarrif");
				while(rs.next()){
					String type = rs.getString("rtype");
					System.out.println(type);
					rtype.add(type);
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
