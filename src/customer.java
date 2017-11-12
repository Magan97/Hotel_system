import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class customer extends JFrame{
	JPanel panel;
	JLabel title,id,ctime;
	JTextField cname,addr,other_p,pno,advance;
	ArrayList<String> rtype = new ArrayList<String>();
	ArrayList<String> rno = new ArrayList<String>();
	JComboBox<Object> choose_pur;
	JComboBox<Object> roomtype;
	JComboBox<Object> rnobox;
	JComboBox<Object> statusbox;
	JButton check,edit,save,back,first,last,next,end,delete;
	int cid;
	String st;
	int oldr,newr;
	JPanel panel1;
	JLabel title1, total, total_v;
	ArrayList sum = new ArrayList();
	Button payit, back1;
	int result=0;
	JLabel lroom;
	customer(int ccid){
		cid = ccid;
		setSize(800,600);
        setTitle("Hotel System -- customer information");
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
        title = new JLabel("Customer information");
        title.setBounds(270, 50, 200, 50);
        panel.add(title);
        String[] name = {"Customer id : ", "Name : ", "Address : ","Purpose : ","Phone number : ",
        		"Room type : ","Checkin time : ", "Advance : ","Status : "};
        for(int i=0;i<9;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,100+30*i, 100, 50);
        	panel.add(li);
        }
        String s = cid+"";
        id = new JLabel(s);
        id.setBounds(350, 100, 50, 30);
        panel.add(id);
        cname = new JTextField();
        cname.setBounds(350, 130, 150, 30);
        panel.add(cname);
        addr = new JTextField();
        addr.setBounds(350, 160, 150, 30);
        panel.add(addr);
        String[] pur = {"Personal","Company","Others"};
        choose_pur = new JComboBox<Object>(pur);
        choose_pur.setBounds(350, 190, 100, 30);
        panel.add(choose_pur);
        other_p = new JTextField();
    	other_p.setBounds(500, 190, 150, 30);
    	panel.add(other_p);
    	other_p.setVisible(false);
    	pno = new JTextField();
        pno.setBounds(350, 220, 150, 30);
        panel.add(pno);
        
        getRtype();
        int size = rtype.size();
        String[] srtype = new String[size];
        for(int i=0;i<size;i++){
        	srtype[i] = (String)rtype.get(i); 
        }
        roomtype = new JComboBox<Object>(srtype);
        roomtype.setBounds(350, 250, 100, 30);
        panel.add(roomtype);
        rnobox = new JComboBox<Object>();
        rnobox.setBounds(500, 250, 100, 30);

        
       
        lroom = new JLabel();
        lroom.setBounds(500, 250, 100, 30);
        panel.add(lroom);
        panel.add(rnobox);
        
        
        //Date time = new Date(System.currentTimeMillis());
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String current = sdf.format(time);
        //System.out.println(current);
        ctime = new JLabel("");
        ctime.setBounds(350, 290, 100, 20);
        panel.add(ctime);
        advance = new JTextField();
        advance.setBounds(350, 310, 150, 30);
        panel.add(advance);
        String[] sta = {"stay-Y","reserve-R","quit-N"};
        statusbox = new JComboBox<Object>(sta);
        statusbox.setBounds(350, 340, 100, 30);
        panel.add(statusbox);
        look(cid);
        rnobox.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
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
                });
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
        /*
        st = statusbox.getSelectedItem().toString();
    	if(st.indexOf("stay-Y")!=-1)
    		st = "Y";
    	if(st.indexOf("reserve-R")!=-1)
    		st = "R";
    	if(st.indexOf("quit-N")!=-1)
    		st = "N";*/
    	statusbox.addItemListener(new ItemListener(){
        	@Override
        	public void itemStateChanged(ItemEvent e){
        		if(e.getStateChange() == ItemEvent.SELECTED){
        			String status = statusbox.getSelectedItem().toString();
                	if(status.indexOf("stay-Y")!=-1)
                		status = "Y";
                	if(status.indexOf("reserve-R")!=-1)
                		status = "R";
                	if(status.indexOf("quit-N")!=-1)
                		status = "N";
                	if(st != "N"){
                		if(status == "N"){
                			String roomnumber = lroom.getText();
                    		int r = Integer.parseInt(roomnumber);
                    		System.out.println("ROOM NUM IS "+r);
                    		int s=0;
                    		System.out.println("st="+st+"  status="+status);
                    		mypay(r);
                		}           		
                	}
                	
        			getRno(roomtype.getSelectedItem().toString());
        			/*
        			rnobox.removeAllItems();
        			int size = rno.size();
        			String[] srno = new String[size];
        			for(int i=0;i<size;i++){
        	        	srno[i] = (String)rno.get(i); 
        	        	//System.out.println(srno[i]);        	        	
        	        }
        			rnobox.removeAllItems();
        			for(int i=0;i<size;i++){
        				rnobox.addItem(srno[i]);
        			}*/
        		}
        	}
        });
        check = new JButton("check");
        check.setBounds(200, 380, 70, 30);
        panel.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(cid);
            }
        });
        edit = new JButton("edit");
        edit.setBounds(280, 380, 70, 30);
        panel.add(edit);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(cid);
                rnobox.setVisible(true);
				lroom.setVisible(false);
                cname.setEditable(true);
                addr.setEditable(true);
                pno.setEditable(true);
                advance.setEditable(true);
            }
        });
        save = new JButton("save");
        save.setBounds(360, 380, 70, 30);
        panel.add(save);
        panel.add(edit);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                	String roomnumber;
                	if(lroom.isVisible()){
                		roomnumber = lroom.getText().toString();
                	}
                	else
                		roomnumber = rnobox.getSelectedItem().toString();
                	String checkin = ctime.getText();
                	String adv = advance.getText();
                	String status = statusbox.getSelectedItem().toString();
                	if(status.indexOf("stay-Y")!=-1)
                		status = "Y";
                	if(status.indexOf("reserve-R")!=-1)
                		status = "R";
                	if(status.indexOf("quit-N")!=-1)
                		status = "N";
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
            				int rs = stmt.executeUpdate("update customer set rno='"+roomnumber+"',cname='"+name+"',address='"+add+"',purpose='"+purpose+"'"
            						+ ",phoneNo='"+phoneno+"',checkinTime='"+checkin+"',advance='"+adv+"',status='"+status+"' where custid="+cid);
            				/*int rs = stmt.executeUpdate("insert into customer (rno,custid,cname,address,purpose,phoneNo,checkinTime,advance,status) "
            						+ "values('"+roomnumber+"','"+cid+"','"+name+"','"+add+"','"+purpose+"','"+phoneno+"','"+checkin
            						+"','"+adv+"','"+status+"')");*/
            				if(rs > 0)
            					System.out.println("add success");
            				else{
            					System.out.println("add failed");
            				}
            				if(st == "N" && status == "Y"){
            					Statement stmt1 = (Statement) con.createStatement();
            					Date time = new Date(System.currentTimeMillis());
            			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            			        String current = sdf.format(time);
            					int rs1 = stmt1.executeUpdate("update customer set checkinTime='"+current+"' where custid="+cid);
            					if(rs1 >0){
            						System.out.println("update time success");
            					}
            					Statement stmt4 = (Statement) con.createStatement();
            					int rs4 = stmt4.executeUpdate("update rooms set status='N' where rno="+roomnumber);
            					if(rs4 >0){
            						System.out.println("update ROOM success");
            					}
            					st = "Y";
            				}
            				if(st == "Y" && status == "N"){//check out room
            					Statement stmt2 = (Statement) con.createStatement();
            					int rs2 = stmt2.executeUpdate("delete from services where rno="+roomnumber);
            					if(rs2 >0){
            						System.out.println("delete service success");
            					}
            					Statement stmt3 = (Statement) con.createStatement();
            					int rs3 = stmt3.executeUpdate("update rooms set status='Y' where rno="+roomnumber);
            					if(rs3 >0){
            						System.out.println("update roomnumber success");
            					}
            					st = "N";
            				}
            				newr = Integer.parseInt(roomnumber);
            				System.out.println("the old room is "+oldr+" the new room is "+newr);
            				if(oldr != newr){ //change room
            					Statement stmt2 = (Statement) con.createStatement();
            					int rs2 = stmt2.executeUpdate("update rooms set status='Y' where rno="+oldr);
            					if(rs2 >0){
            						System.out.println("change room status success");
            					}
            					Statement stmt3 = (Statement) con.createStatement();
            					int rs3 = stmt3.executeUpdate("update rooms set status='N' where rno="+newr);
            					if(rs3 >0){
            						System.out.println("change room status success");
            					}
            					Statement stmt4 = (Statement) con.createStatement();
            					int rs4 = stmt4.executeUpdate("update services set rno="+newr+" where rno="+oldr);
            					if(rs4 >0){
            						System.out.println("change service success");
            					}
            					oldr = newr;
            				}
            				con.commit();
            			}catch(Exception ex){
            				con.rollback();
            				ex.printStackTrace();
            				System.out.println("failed");
            			}
            			
            		}catch(ClassNotFoundException | SQLException ex){
            			System.out.println("Can’t load the Driver");
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
        first = new JButton("first");
        first.setBounds(200, 420, 70, 30);
        panel.add(first);
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cid = minID();
            	
            	look(cid);
            }
        });
        last = new JButton("last");
        last.setBounds(280, 420, 70, 30);
        panel.add(last);
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if((cid-1)>=minID())
            		cid--;
            	look(cid);
            }
        });
        next = new JButton("next");
        next.setBounds(360, 420, 70, 30);
        panel.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if((cid+1)<=maxID())
            		cid++;
            	look(cid);
            }
        });
        end = new JButton("end");
        end.setBounds(440, 420, 70, 30);
        panel.add(end);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cid = maxID();
            	look(cid);
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
			System.out.println("Can’t load the Driver");
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
				ResultSet rs = stmt.executeQuery("select * from rooms where rtype like '"+rt+"' and status like 'Y'");
				while(rs.next()){
					int rno1 = rs.getInt(1);
					System.out.println(rno1);
					String s = rno1+"";
					rno.add(s);
				}
				if(statusbox.getSelectedItem().toString().indexOf("stay-Y")!=-1){
					rno.add(lroom.getText());
					System.out.println("statusbox.getSelectedItem().toString().indexOf(stay-Y)!=-1    "+statusbox.getSelectedItem().toString()+"rno size="+rno.size());
				}				
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can’t load the Driver");
		}
		return 0;
	}
	public int look(int cid){
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
				ResultSet rs = stmt.executeQuery("select * from customer where custid = "+cid);
				while(rs.next()){
					String myid = cid+"";
					id.setText(myid);
					String name = rs.getString("cname");
					cname.setText(name);
					cname.setEditable(false);
					String address = rs.getString("address");
					addr.setText(address);
					addr.setEditable(false);
					String pur = rs.getString("purpose");
					choose_pur.setSelectedItem(rs.getString("purpose"));
					choose_pur.setEditable(false);
					if(pur.indexOf("Personal")!=-1 || (pur.indexOf("Company")!=-1)){
					//if(pur == "Personal" || pur == "Company"){
						choose_pur.setSelectedItem(pur);
						other_p.setVisible(false);
					}
					else{
						pur = "Others";
						String o = rs.getString("purpose");
						other_p.setText(o);
						other_p.setVisible(true);
					}
					choose_pur.setSelectedItem(pur);
					choose_pur.setEditable(false);
					
					String phone = rs.getLong(6)+"";
					other_p.setEditable(false);
					//String p = phone+"";
					pno.setText(phone);
					pno.setEditable(false);
					int room = rs.getInt(1);
					System.out.println("room number should be"+room);
					String r = room+"";
					lroom.setText(r);
					oldr = room;
					rnobox.setVisible(false);
					lroom.setVisible(true);
					System.out.println("room number should be"+r);
					
					getRno(roomtype.getSelectedItem().toString());
	    			rnobox.removeAllItems();
	    			int size = rno.size();
	    			String[] srno = new String[size];
	    			for(int i=0;i<size;i++){
	    	        	srno[i] = (String)rno.get(i); 
	    	        	//System.out.println(srno[i]);        	        	
	    	        }
	    			rnobox.removeAllItems();
	    			for(int i=0;i<size;i++){
	    				rnobox.addItem(srno[i]);
	    			}
	                rnobox.setSelectedItem(lroom.getText());
	               
					//rnobox.addItem(r);
					rnobox.setEditable(false);
					//rnobox.setSelectedItem(r);
					String rt = getrtype(room);
					roomtype.setSelectedItem(rt);
					roomtype.setEditable(false);
					Date time = rs.getDate(7);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String mytime = sdf.format(time);
					ctime.setText(mytime);
					int adv = rs.getInt(8);
					String a = adv+"";
					advance.setText("120");
					advance.setEditable(false);	
					String mystatus = rs.getString("status");
					String sta = ""+mystatus;
					if(mystatus.indexOf("Y")!=-1){
						sta = "stay-Y";
						st = "Y";
					}
					
					if(mystatus.indexOf("R")!=-1){
						sta = "reserve-R";
						st = "R";
					}
					if(mystatus.indexOf("N")!=-1){
						sta = "quit-N";
						st = "N";
					}
					statusbox.setSelectedItem(sta);
					//st = mystatus;
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can’t load the Driver");
		}
		return 0;
	}
	public String getrtype(int rno){
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
				ResultSet rs = stmt.executeQuery("select * from rooms where rno = "+rno);
				while(rs.next()){
					String type = rs.getString("rtype");
					//System.out.println(type);
					return type;
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can’t load the Driver");
		}
		return "";
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
					return id;
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can’t load the Driver");
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
				ResultSet rs = stmt.executeQuery("select * from customer where custid=(select min(custid) from customer)");
				while(rs.next()){
					int id = rs.getInt(2);
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
			System.out.println("Can’t load the Driver");
		}
		return 0;
	}
	@SuppressWarnings("deprecation")
	public void mypay(int rno){
		//int result = 0;
		JFrame l = new JFrame();
		l.setLayout(null);
		l.setSize(300,350);
        l.setTitle("Hotel System -- settle accounts");
        l.setLocation(450,180);
        panel1 = new JPanel();
        panel1.setLayout(null);
        title1 = new JLabel("Settle accounts");
        title1.setBounds(100, 30, 100, 40);
        l.add(title1);
        
        String[] columnName = {"Type", "Desc", "Price"};
        //Object[][] data = new Object[3][n];
        Object[][] data1 = null;
        try {
			//Class.forName(com.mysql.jdbc.Driver.class.getName());
			String url = "jdbc:mysql://localhost/hotel system";
			String login = "root";
			String password = "";
			Connection con;
			con = DriverManager.getConnection(url, login, password);
			con.setAutoCommit(false);
			int i=2;
			sum.clear();
			try{
				Statement stmt1 = (Statement) con.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select count(*) from services where rno="+rno+"");
				if(rs1.next()){
					int n = rs1.getInt(1);
					data1 = new Object[n+2][3];
				}
				Statement stmt2 = (Statement) con.createStatement();
				ResultSet rs2 = stmt1.executeQuery("select * from rooms where rno="+rno+"");
				if(rs2.next()){
					String rt = rs2.getString("rtype");
					Statement stmt3 = (Statement) con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("select * from tarrif where rtype like '"+rt+"'");
					if(rs3.next()){
						int p = rs3.getInt("price");
						//Date time = new Date(System.currentTimeMillis());
				        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        //String current = sdf.format(time);
				        Statement stmt5 = (Statement) con.createStatement();
						ResultSet rs5 = stmt5.executeQuery("select * from customer where rno="+rno+" and status='Y'");
						if(rs5.next()){
							String d = rs5.getString("checkinTime");
							Date time = new Date(System.currentTimeMillis());
					        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					        String current = sdf.format(time);
					        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
					        int date;
					        System.out.println("old -- "+d+"  now -- "+current);
					    
					        try{
					        	java.util.Date d1 = format.parse(d);
					        	java.util.Date d2 = format.parse(current);
					        	date = differentDays(d1,d2);
					        	String pr = p+"*"+date;
					        	int a = p*date;
					        	sum.add(a);
								data1[0][2] = pr;
					        }catch(ParseException e){
					        	e.printStackTrace();
					        }
							
						}
						
						data1[0][0] = "Room";
						data1[0][1] = rt;
						
						
						
					}
				}
				Statement stmt4 = (Statement) con.createStatement();
				ResultSet rs4 = stmt4.executeQuery("select * from customer where rno="+rno+"");
				if(rs4.next()){
					int ad = 0-rs4.getInt("advance");					
					
					data1[1][0] = "Advance";
					data1[1][1] = " -- ";
					data1[1][2] = ad;
					sum.add(ad);
					
				}
				Statement stmt = (Statement) con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from services where rno="+rno+"");
				
				while(rs.next()){
					String t = "Service";
					String type = rs.getString("stype");
					if(type.indexOf("L")!=-1)
						type = "laundry-L";
					if(type.indexOf("R")!=-1)
						type = "restaurant-R";
					if(type.indexOf("T")!=-1)
						type = "transportation-T";
					if(type.indexOf("W")!=-1)
						type = "wifi-W";
					if(type.indexOf("O")!=-1)
						type = rs.getString("sdesc");
					//System.out.println(type);
					int amount = rs.getInt(5);
					System.out.println(amount);
					data1[i][0] = t;
					data1[i][1] = type;
					data1[i][2] = amount;
					sum.add(amount);
					i++;
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(SQLException ex){
			System.out.println("Can’t load the Driver");
		}
        final JTable table = new JTable(data1, columnName);
        table.setPreferredScrollableViewportSize(new Dimension(260,100));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 260, 100);
        total = new JLabel("Total : ");
        total.setBounds(150, 180, 50, 30);
        l.add(total);
        int s=0;
        for(int i=0;i<sum.size();i++){
        	s += (int) sum.get(i);
        	System.out.println(i);
        }
        String su = s+"";
        total_v = new JLabel(su);
        total_v.setBounds(200, 180, 30, 30);
        l.add(total_v);
        l.add(scrollPane);
        payit = new Button("Pay");
        payit.setBounds(80, 220, 50, 30);
        l.add(payit);
        payit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	result = 1; 
            	l.setVisible(false);
            }
        });
        back1 = new Button("Back");
        back1.setBounds(170, 220, 50, 30);
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	result = 2;       
            	l.setVisible(false);
            }
        });
        l.add(back1);
        l.setVisible(true);
        l.add(panel1);
        if(result == 1){
    		System.out.println("pay money status is" + s);
    		statusbox.setSelectedIndex(2);
        }
        else if(result == 2){
        	statusbox.setSelectedIndex(0);
        }

	}
	public static int differentDays(java.util.Date d1,java.util.Date d2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
}
