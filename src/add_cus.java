import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;



public class add_cus extends JFrame{
	JPanel panel;
	JLabel title;
	JLabel id,ctime;
	JTextField cname,addr,other_p,pno,advance,t6,t7,t8,t9;
	ArrayList<String> rtype = new ArrayList<String>();
	ArrayList<String> rno = new ArrayList<String>();
	Button submit,reset,back;
	public add_cus(){
        setTitle("Hotel System -- add customer");
        this.setLocation(200,50);
        panel = new JPanel(){	
        	public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("3.jpg");
                g.drawImage(icon.getImage(), 0, 0,                  
                this.getSize().width,
                this.getSize().height,
                this);
            }
        };
       
        panel.setLayout(null);
        title = new JLabel("Add customer");
        title.setBounds(270, 50, 200, 50);
        panel.add(title);
        String[] name = {"Customer id : ", "Name : ", "Address : ","Purpose : ","Phone number : ",
        		"Room type : ","Checkin time : ", "Advance : "};
        for(int i=0;i<8;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,100+30*i, 100, 50);
        	panel.add(li);
        }
        String s = maxID()+"";
        id = new JLabel(s);
        id.setBounds(350, 100, 50, 30);
        panel.add(id);
        cname = new JTextField();
        cname.setBounds(350, 130, 150, 30);
        panel.add(cname);
        cname.addKeyListener(new KeyListener(){//only can write char
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			//enter
        		}
        		else if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)){
        			//char
        			
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
        addr = new JTextField();
        addr.setBounds(350, 160, 150, 30);
        panel.add(addr);
        addr.addKeyListener(new KeyListener(){//can write char,number, -
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			//enter
        		}
        		else if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)){
        			//char	
        		}
        		else if(temp >= 48 && temp <= 57){
        			//number
        		}
        		else if(temp == 45){
        			//-
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
        String[] pur = {"Personal","Company","Others"};
        JComboBox<Object> choose_pur = new JComboBox<Object>(pur);
        choose_pur.setBounds(350, 190, 100, 30);
        panel.add(choose_pur);
        other_p = new JTextField();
    	other_p.setBounds(500, 190, 150, 30);
    	panel.add(other_p);
    	other_p.setVisible(false);
        choose_pur.addItemListener(new ItemListener(){
        	@Override
        	public void itemStateChanged(ItemEvent e){
        		if(e.getStateChange() == ItemEvent.SELECTED){
        			if(choose_pur.getSelectedItem().toString() == "Others"){
        	        	other_p.setVisible(true);
        	        }
        	        else{
        	        	other_p.setVisible(false);
        	        }
        		}
        	}
        });
        pno = new JTextField();
        pno.setBounds(350, 220, 150, 30);
        panel.add(pno);
        pno.addKeyListener(new KeyListener(){//can write number, -
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			//enter
        		}
        		else if(temp >= 48 && temp <= 57){
        			//number
        		}
        		else if(temp == 43){
        			//+
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
        getRtype();
        int size = rtype.size();
        String[] srtype = new String[size];
        for(int i=0;i<size;i++){
        	srtype[i] = (String)rtype.get(i); 
        }
        JComboBox<Object> roomtype = new JComboBox<Object>(srtype);
        roomtype.setBounds(350, 250, 100, 30);
        panel.add(roomtype);
        JComboBox<Object> rnobox = new JComboBox<Object>();
        rnobox.setBounds(500, 250, 100, 30);
        panel.add(rnobox);
        
        roomtype.addItemListener(new ItemListener(){
        	@Override
        	public void itemStateChanged(ItemEvent e){
        		if(e.getStateChange() == ItemEvent.SELECTED){
        			getRno(roomtype.getSelectedItem().toString());
        			rnobox.removeAllItems();
        			int size = rno.size();
        			String[] srno = new String[size];
        			for(int i=0;i<size;i++){
        	        	srno[i] = (String)rno.get(i); 
        	        	System.out.println(srno[i]);        	        	
        	        }
        			rnobox.removeAllItems();
        			for(int i=0;i<size;i++){
        				rnobox.addItem(srno[i]);
        			}
        		}
        	}
        });
        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String current = sdf.format(time);
        System.out.println(current);
        ctime = new JLabel(current);
        ctime.setBounds(350, 290, 100, 20);
        panel.add(ctime);
        advance = new JTextField();
        advance.setBounds(350, 310, 150, 30);
        panel.add(advance);
        advance.addKeyListener(new KeyListener(){//can write char,number, -
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			//enter
        		}
        		else if(temp >= 48 && temp <= 57){
        			//number
        		}
        		else if(temp == 46){
        			//.
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
        submit = new Button("submit");
        submit.setBounds(250, 360, 70, 40);
        panel.add(submit);
        
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit");
                if(cname.getText()==""||addr.getText()==""||choose_pur.getSelectedItem().toString()==""
                		||pno.getText()==""||roomtype.getSelectedItem().toString()==""||advance.getText()==""){
                	System.out.println("not full info");
                	
                }
                else{
                	System.out.println(cname.getText());
                	String cid = id.getText();
                	String name = cname.getText();
                	String add = addr.getText();
                	String purpose = choose_pur.getSelectedItem().toString();
                	if(purpose == "Others")
                		purpose = other_p.getText();
                	String phoneno = pno.getText();
                	String sroomtype = roomtype.getSelectedItem().toString();
                	String roomnumber = rnobox.getSelectedItem().toString();
                	String checkin = ctime.getText();
                	String adv = advance.getText();
                	String status = "Y";
                	
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
            				int rs = stmt.executeUpdate("insert into customer (rno,custid,cname,address,purpose,phoneNo,checkinTime,advance,status) "
            						+ "values('"+roomnumber+"','"+cid+"','"+name+"','"+add+"','"+purpose+"','"+phoneno+"','"+checkin
            						+"','"+adv+"','"+status+"')");
            				Statement stmt1 = (Statement) con.createStatement();
            				int rs1 = stmt1.executeUpdate("update rooms set status='N' where rno='"+roomnumber+"'");
            				if(rs > 0){
            					System.out.println("add success");
            					reminder("successsfully");
            				}
            					
            				else{
            					System.out.println("add failed");
            					reminder("failed");
            				}
            				if(rs1 > 0)
            					System.out.println("change room success");
            				else{
            					System.out.println("change room failed");
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
        reset = new Button("Reset");
        reset.setBounds(350, 360, 70, 40);
        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cname.setText("");
                addr.setText("");
                pno.setText("");
                advance.setText("");
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
        this.add(panel);
        //this.setVisible(false);
        
	}
	public int maxID(){
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
				ResultSet rs = stmt.executeQuery("select * from customer where custid=(select max(custid) from customer)");
				while(rs.next()){
					int id = rs.getInt(2);
					System.out.println(id);
					return id+1;
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
	public int getRno(String rt){
		try {
			Class.forName(com.mysql.jdbc.Driver.class.getName());
			String url = "jdbc:mysql://localhost/hotel system";
			String login = "root";
			String password = "";
			Connection con;
			con = DriverManager.getConnection(url, login, password);
			con.setAutoCommit(false);
			rno.clear();
			try{
				System.out.println(rt);
				Statement stmt = (Statement) con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from rooms where rtype like '"+rt+"' and status='Y'");
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
