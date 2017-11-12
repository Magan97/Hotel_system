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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class rtype extends JFrame{
	JPanel panel;
	JLabel title;
	JTextField rtype,desc,price;
	JButton check,edit,save,back,first,last,next,end;
	ArrayList<String> allrtype = new ArrayList<String>();
	int rindex;
	String rt;
	rtype(String rrt){
		rt = rrt;
		setSize(800,600);
        setTitle("Hotel System -- room type information");
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
        title = new JLabel("Room type information");
        title.setBounds(270, 50, 200, 50);
        panel.add(title);
        String[] name = {"Room type : ","Description : ","Price : "};
		for(int i=0;i<3;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,120+60*i, 100, 50);
        	panel.add(li);
        }
		rtype = new JTextField();
		rtype.setBounds(350, 130, 150, 30);
		panel.add(rtype);
		desc = new JTextField();
		desc.setBounds(350, 190, 150, 30);
		panel.add(desc);
		price = new JTextField();
		price.setBounds(350, 250, 150, 30);
		panel.add(price);
		price.addKeyListener(new KeyListener(){//can write char,number, -
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
		this.add(panel);
		look(rt);
		check = new JButton("check");
        check.setBounds(200, 380, 70, 30);
        panel.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(rt);
            }
        });
        edit = new JButton("edit");
        edit.setBounds(280, 380, 70, 30);
        panel.add(edit);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(rt);
                //rtype.setEditable(true);
                desc.setEditable(true);
                price.setEditable(true);
            }
        });
        save = new JButton("save");
        save.setBounds(360, 380, 70, 30);
        panel.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rtype.getText()=="" || desc.getText()=="" || price.getText()==""){
                	System.out.println("not full info");              	
                }
                else{
                	String myrtype = rtype.getText();
                	String mydesc = desc.getText();
                	String myprice = price.getText();               		
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
        					int rs = stmt.executeUpdate("update tarrif set rdesc='"+mydesc+"',price='"+myprice+"' where rtype='"+myrtype+"'");
            				if(rs > 0)
            					System.out.println("add success");
            				else{
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
        getRtype();
        rindex = allrtype.indexOf(rt);
        first = new JButton("first");
        first.setBounds(200, 420, 70, 30);
        panel.add(first);
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//rid = minID(); 
            	rindex = 0;
            	rt = allrtype.get(rindex);
            	look(rt);
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
                	rt = allrtype.get(rindex);
                	look(rt);
            	}           
            }
        });
        next = new JButton("next");
        next.setBounds(360, 420, 70, 30);
        panel.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rindex+2 <= allrtype.size()){
            		rindex++;
            		rt = allrtype.get(rindex);
            		look(rt);
            	}
            }
        });
        end = new JButton("end");
        end.setBounds(440, 420, 70, 30);
        panel.add(end);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rindex = allrtype.size()-1;
            	rt = allrtype.get(rindex);
            	look(rt);
            }
        });
	}
	public int look(String rt){
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
				ResultSet rs = stmt.executeQuery("select * from tarrif where rtype like '"+rt+"'");
				while(rs.next()){
					rtype.setText(rt);
					String mydesc = rs.getString("rdesc");
					desc.setText(mydesc);
					String p = rs.getInt(3)+"";
					price.setText(p);
					rtype.setEditable(false);
					desc.setEditable(false);
					price.setEditable(false);
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
					String r = rs.getString("rtype");
					allrtype.add(r);
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
