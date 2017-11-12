import java.awt.Button;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


public class room extends JFrame{
	int rid;
	JPanel panel;
	JLabel title,floor;
	JTextField rno;
	ArrayList<String> rtype = new ArrayList<String>();
	ArrayList allrooms = new ArrayList();
	int rindex;
	//ArrayList<String> allrooms = new ArrayList<String>();
	JComboBox<Object> roomtype;
	JComboBox<Object> status;
	JButton check,edit,save,back,first,last,next,end;
	room(int rrid){
		rid = rrid;
		setSize(800,600);
        setTitle("Hotel System -- room information");
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
        title = new JLabel("Room information");
        title.setBounds(270, 50, 200, 50);
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
        roomtype = new JComboBox<Object>(srtype);
        roomtype.setBounds(350, 180, 100, 30);
        panel.add(roomtype);
        floor = new JLabel("");
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
        status = new JComboBox<Object>(sta);
        status.setBounds(350, 280, 100, 30);
        panel.add(status);
		look(rid);
		check = new JButton("check");
        check.setBounds(200, 380, 70, 30);
        panel.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(rid);
            }
        });
        edit = new JButton("edit");
        edit.setBounds(280, 380, 70, 30);
        panel.add(edit);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(rid);
                //rno.setEditable(true);
                status.addItem("delete-D");
            }
        });
        save = new JButton("save");
        save.setBounds(360, 380, 70, 30);
        panel.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rno.getText()==""){
                	System.out.println("not full info");              	
                }
                else{
                	String roomno = rno.getText();
                	String rotype = roomtype.getSelectedItem().toString();
                	String floorno = floor.getText();
                	String rostatus = status.getSelectedItem().toString();
                	if(rostatus.indexOf("available-Y")!=-1)
                		rostatus = "Y";
                	if(rostatus.indexOf("occupied-N")!=-1)
                		rostatus = "N";
                	if(rostatus.indexOf("maintenance-M")!=-1)
                		rostatus = "M";
                	if(rostatus.indexOf("delete-D")!=-1)
                		rostatus = "D";               		
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
            				if(rostatus != "D"){
            					System.out.println(rostatus);         		
            					int rs = stmt.executeUpdate("update rooms set rtype='"+rotype+"',floor='"+floorno+"',status='"+rostatus+"' where rno="+roomno);
                				if(rs > 0)
                					System.out.println("add success");
                				else{
                					System.out.println("add failed");
                				}
                				con.commit();
            				}
            				else{
            					int rs = stmt.executeUpdate("delete from rooms where rno="+roomno);
                				if(rs > 0)
                					System.out.println("delete success");
                				else{
                					System.out.println("delete failed");
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
        back = new JButton("back");
        back.setBounds(440, 380, 70, 30);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	TestFlow myflow = new TestFlow();
                myflow.setVisible(true);
                myflow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        getRooms();
        rindex = allrooms.indexOf(rid);
        first = new JButton("first");
        first.setBounds(200, 420, 70, 30);
        panel.add(first);
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//rid = minID(); 
            	rindex = 0;
            	rid = (int) allrooms.get(rindex);
            	look(rid);
            }
        });
        last = new JButton("last");
        last.setBounds(280, 420, 70, 30);
        panel.add(last);
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rindex > 0){
            		rindex--;
                	rid = (int)allrooms.get(rindex);
                	look(rid);
            	}           
            }
        });
        next = new JButton("next");
        next.setBounds(360, 420, 70, 30);
        panel.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rindex+2 <= allrooms.size()){
            		rindex++;
            		rid = (int)allrooms.get(rindex);
            		look(rid);
            	}
            }
        });
        end = new JButton("end");
        end.setBounds(440, 420, 70, 30);
        panel.add(end);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rindex = allrooms.size()-1;
            	rid = (int)allrooms.get(rindex);
            	look(rid);
            }
        });
        this.add(panel);
        
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
	public int look(int rid){
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
				ResultSet rs = stmt.executeQuery("select * from rooms where rno = "+rid);
				while(rs.next()){
					String myid = rid+"";
					rno.setText(myid);
					String rotype = rs.getString("rtype");
					roomtype.setSelectedItem(rotype);
					rno.setEditable(false);
					String f1 = rs.getInt(3)+"";
					floor.setText(f1);
					String st = rs.getString("status");
					
					String thisst="";
					if(st.indexOf("Y")!=-1)
						thisst = "available-Y";
					if(st.indexOf("N")!=-1)
						thisst = "occupied-N";
					if(st.indexOf("M")!=-1)
						thisst = "maintenance-M";
					System.out.println(thisst);		
					status.setSelectedItem(thisst);
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
	public int minID(){
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
				ResultSet rs = stmt.executeQuery("select * from rooms where rno=(select min(rno) from customer)");
				while(rs.next()){
					int id = rs.getInt(1);
					System.out.println(id);
					return id;
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
	public int getRooms(){
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
				ResultSet rs = stmt.executeQuery("select * from rooms");
				while(rs.next()){
					int id = rs.getInt(1);
					allrooms.add(id);
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
				ResultSet rs = stmt.executeQuery("select * from rooms where rno=(select max(rno) from customer)");
				while(rs.next()){
					int id = rs.getInt(1);
					System.out.println(id);
					return id;
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
}
