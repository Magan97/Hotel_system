import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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






import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class TestFlow extends JFrame{
    JButton search,b2,b3,b4,b5;
    JPanel panel;
    JPanel jp;
    JLabel label,label2,label3;
    JMenuBar menubar;
    JMenu menu1, menu2, menu3;
    JMenuItem mitem1,mitem2,mitem3,mitem4;
    JMenuItem mitem5,mitem6,mitem7,mitem8;
    JTextField text1;
    ButtonGroup bg;
    JRadioButton rb1,rb2,rb3,rb4;
    ArrayList<String> ser = new ArrayList<String>();
	JComboBox<Object> choose_ser;
	int serindex;
    public TestFlow()
    {
        setSize(800,600);
        setTitle("Hotel System");
        this.setLocation(200,50);
        menubar = new JMenuBar();
        
        this.setJMenuBar(menubar);
        menu1 = new JMenu("Add");
        menu2 = new JMenu("Look");
        menu3 = new JMenu("");
        menubar.add(menu1);
        menubar.add(menu3);
        menubar.add(menu2);
        mitem1 = new JMenuItem("cutomer");
        mitem2 = new JMenuItem("room");
        mitem3 = new JMenuItem("service");
        mitem4 = new JMenuItem("room type");
        mitem5 = new JMenuItem("cutomer");
        mitem6 = new JMenuItem("room");
        mitem7 = new JMenuItem("service");
        mitem8 = new JMenuItem("room type");
        menu1.add(mitem1);
        menu1.add(mitem2);
        menu1.add(mitem3);
        menu1.add(mitem4);
        menu2.add(mitem5);
        menu2.add(mitem6);
        menu2.add(mitem7);
        menu2.add(mitem8);
        search=new JButton("Search");
        b2=new JButton("按钮2");
        b3=new JButton("按钮3");
        b4=new JButton("按钮4");
        b5=new JButton("按钮5");
        panel=new JPanel(){
        	public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("2.jpg");
                g.drawImage(icon.getImage(), 0, 0,                  
                this.getSize().width,
                this.getSize().height,
                this);
            }
        };
        panel.setOpaque(false);
        text1 = new JTextField("", 25);
        text1.setBounds(200,120,300,50);
        Dimension dimb = search.getPreferredSize();
        search.setBounds(550,130,dimb.width,dimb.height);
        panel.add(text1);
        panel.add(search);
        bg = new ButtonGroup();
        rb1 = new JRadioButton("customer", true);
        rb1.setOpaque(false);
        rb2 = new JRadioButton("room",false);
        rb2.setOpaque(false);
        rb3 = new JRadioButton("service",false);
        rb3.setOpaque(false);
        rb4 = new JRadioButton("room type",false);
        rb4.setOpaque(false);
        Dimension drb = rb1.getPreferredSize();
        Dimension drb2 = rb2.getPreferredSize();
        Dimension drb3 = rb3.getPreferredSize();
        Dimension drb4 = rb4.getPreferredSize();
        rb1.setBounds(200, 200, drb.width, drb.height);
        rb2.setBounds(280, 200, drb2.width, drb2.height);
        rb3.setBounds(350, 200, drb3.width, drb3.height);
        rb4.setBounds(420, 200, drb4.width, drb4.height);
        panel.setLayout(null);
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        bg.add(rb4);
        panel.add(rb1);
        panel.add(rb2);
        panel.add(rb3);
        panel.add(rb4);
        label = new JLabel("Welcome to MY hotel system :)",JLabel.CENTER);
        label.setFont(new Font("Dialog", 1, 25));
        label.setSize(new Dimension(450,50));
        //Dimension dim = label.getMaximumSize();
        label.setBounds(200, 60, 400, 50);
        label3 = new JLabel("Tel : 13930582191     Address : Tianjin-Hebut", JLabel.CENTER);
        label3.setFont(new Font("Dialog", 1, 15));
        label3.setForeground(Color.cyan);
        label3.setSize(new Dimension(300, 30));
        label3.setBounds(200, 420, 400, 30);
        panel.add(label3);
        label2 = new JLabel("version 1.0 , made by Group",JLabel.CENTER);
        label2.setBounds(250, 450, 300, 50);
        panel.add(label);
        panel.add(label2);
        choose_ser = new JComboBox<Object>();
        choose_ser.setBounds(200,120,310,50);
        panel.add(choose_ser);
        choose_ser.setVisible(false);
        text1.addKeyListener(new KeyListener(){
        	@Override
        	public void keyTyped(KeyEvent e){
        		int temp = e.getKeyChar();
        		//System.out.println(temp);
        		if(temp == 10){
        			if(rb3.isSelected()){
        				String s = text1.getText();
        				choose_ser.removeAllItems();
        				//choose_ser.removeAll();
        				ser.clear();
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
        						ResultSet rs = stmt.executeQuery("select * from services where rno='"+s+"'");
        						while(rs.next()){
        							String ino = rs.getInt(1)+"";
        							Date time = rs.getDate(2);
        							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        							String mytime = sdf.format(time);        							
        							String type = rs.getString("stype");
        							String desc = "";
        							if(type == "L")
        								type = "laundry-L";
        							if(type == "R")
        								type = "restaurant-R";
        							if(type == "T")
        								type = "transportation-T";
        							if(type == "W")
        								type = "wifi-W";
        							if(type == "O"){
        								type = "others-O";
        								desc = rs.getString("sdesc");
        							}
        							String num = rs.getInt(5)+"";
        							String all = ino+"-"+mytime+"-"+type+"-"+desc+"-"+num;
        							choose_ser.setVisible(true);
        							choose_ser.addItem(all);      		
        							ser.add(all);
        						}
        						con.commit();
        					}catch(Exception e1){
        						con.rollback();
        						e1.printStackTrace();
        						System.out.println("failed");
        					}
        					
        				}catch(ClassNotFoundException | SQLException ex){
        					System.out.println("Can’t load the Driver");
        				}
        			}
        		}
        		else if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)){   
        			
        		}
        		else{
        			//no
        			
        		}
        	}
        	@Override
        	public void keyReleased(KeyEvent e){
        		
        	}
        	@Override
        	public void keyPressed(KeyEvent e){
        		
        	}
        	
        });
        //getContentPane().add(panel);
        choose_ser.addItemListener(new ItemListener(){
        	@Override
        	public void itemStateChanged(ItemEvent e){
        		if(e.getStateChange() == ItemEvent.SELECTED){
        			for(int i=0;i<ser.size();i++){
        				if(choose_ser.getSelectedItem().toString().indexOf(ser.get(i))!=-1){
        					serindex = i;
        					text1.setText(ser.get(i));
        				}       						
        			}
        		}
        	}
        });
        this.add(panel);
        mitem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item1");
                dispose();
                add_cus add_cus1 = new add_cus();
                add_cus1.setVisible(true);
                add_cus1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item2");
                dispose();
                add_room ar = new add_room();
                ar.setVisible(true);
                ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item3");
                dispose();
                add_ser as = new add_ser();
                as.setVisible(true);
                as.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item4");
                dispose();
                add_rtype art = new add_rtype();
                art.setVisible(true);
                art.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item5");
                dispose();
                customer cus = new customer(001);
                cus.setVisible(true);
                cus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item6");
                dispose();
                room ro = new room(1001);
				ro.setVisible(true);
		        ro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item7");
                dispose();
                service se = new service(0);
                se.setVisible(true);
		        se.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        mitem8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("item8");
                dispose();
                rtype rt = new rtype("single room");
                rt.setVisible(true);
		        rt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit");
                if(text1.getText()==""){
                	System.out.println("not full info");
                }
                else{
                	//System.out.println(cname.getText());
                	String tname = "";
                	String value = text1.getText();
                	if(rb1.isSelected())               	
                		tname = "customer";             		
                	if(rb2.isSelected())
                		tname = "rooms";
                	if(rb3.isSelected())
                		tname = "serivces";
                	if(rb4.isSelected())
                		tname = "tarrif";
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
            				if(tname == "customer"){
            					ResultSet rs = stmt.executeQuery("select * from customer where cname like '"+value+"'");
            					if(!rs.next()){
                					reminder(tname);
                				}
            					else{
            						Statement stmt1 = (Statement) con.createStatement();
                					ResultSet rs1 = stmt1.executeQuery("select * from customer where cname like '"+value+"'");
                					while(rs1.next()){
                    					int cid = rs1.getInt(2);//customer id
                    					System.out.println(cid);
                    					//String s = cid+"";
                    					dispose();
                    					customer cus = new customer(cid);
                    					cus.setVisible(true);
                    			        cus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    				}
                    				
                    				con.commit();
            					}
            					
            				}
            				else if(tname == "rooms"){
            					
            					ResultSet rs = stmt.executeQuery("select * from rooms where rno = '"+value+"'");
            					if(!rs.next()){
                					reminder(tname);
                				}
            					Statement stmt1 = (Statement) con.createStatement();
            					ResultSet rs1 = stmt1.executeQuery("select * from rooms where rno = '"+value+"'");
                				while(rs1.next()){
                					int rid = rs1.getInt(1);//room id
                					System.out.println(rid);
                					//String s = cid+"";
                					dispose();
                					room ro = new room(rid);
                					ro.setVisible(true);
                			        ro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                				}
                				
                				con.commit();
            				}
            				else if(tname == "tarrif"){
            					ResultSet rs = stmt.executeQuery("select * from tarrif where rtype like '"+value+"'");
                				if(!rs.next()){
                					reminder(tname);
                				}
                				else{
                					//con.commit();
                					//Statement stmt1 = (Statement) con.createStatement();
                					//ResultSet rs1 = stmt1.executeQuery("select * from tarrif where rtype like '"+value+"'");
                					dispose();
                					rtype rt = new rtype(value);
                	                rt.setVisible(true);
                			        rt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                				}
                				
            			        
            				}
            				else if(tname == "serivces"){
            					ResultSet rs = stmt.executeQuery("select * from services where rno = '"+value+"'");
                				if(!rs.next()){
                					reminder(tname);
                				}
                				else{
                					int index = ser.indexOf(choose_ser.getSelectedItem().toString());
                					System.out.println(index);
                					System.out.println(value);
                					value = value.substring(0, 4);
                					int thisvalue = Integer.parseInt(value);
                					System.out.println(value);
                					int allindex = getindex(index,thisvalue);
                					dispose();
                					service se = new service(allindex);
                					se.setVisible(true);
                			        se.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                				}
            					
            				}
            			}catch(Exception e1){
            				con.rollback();
            				e1.printStackTrace();
            				System.out.println("failed");
            			}
            			
            		}catch(ClassNotFoundException | SQLException ex){
            			System.out.println("Can’t load the Driver");
            		}
                	
                }
            }
        });
    }
    public void reminder(String s){
    	JFrame jf = new JFrame();
    	
    	//jf.add(jp);
    	jf.setSize(300,200);
    	jf.setLayout(null);
        jf.setTitle("reminder");
        jf.setLocation(450,180);
        //jp.setLayout(null);;
        jf.setVisible(true);
        jp = new JPanel(){
    		public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("4.jpg");
                g.drawImage(icon.getImage(), 0, 0,                  
                jf.getSize().width,
                jf.getSize().height,
                this);
            }
    	};
    	jp.setVisible(true);
    	jp.setOpaque(false);
    	jf.add(jp);
        JLabel l1 = new JLabel();
        l1.setText("This "+s+" do not exist !");
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
        jf.add(jp);
    }
    public int getindex(int in,int rno){
    	int index=-1;
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
				ResultSet rs = stmt.executeQuery("select * from services");
				while(rs.next()){
					index++;
					int rno1 = rs.getInt(1);
					if(rno1 == rno){
						in--;
					}
					if(in==-1)
						return index;
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
    public static void main(String[] agrs)
    {
    	
        TestFlow myflow = new TestFlow();
        myflow.setVisible(true);
        myflow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
    	add_cus ad = new add_cus();
    	ad.setVisible(true);
    	ad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add_rtype ar = new add_rtype();
        ar.setVisible(true);
        ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    }
}/*
	public static void main(String[] args) {	
		GUI a = new GUI();
		*/
		/*
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
					System.out.println(id);
					
					String type = rs.getString("rtype");
					System.out.println(type);
				}
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
				System.out.println("failed");
			}
			
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Can’t load the Driver");
		}*/
/*	}

}*/
