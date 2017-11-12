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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class add_rtype extends JFrame{
	JPanel panel;
	JLabel title;
	JTextField rtype,desc,price;
	JButton submit,reset,back;
	add_rtype(){
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
        setTitle("Hotel System -- add room type");
        this.setLocation(200,50);
        
        panel.setLayout(null);
		title = new JLabel("Add room type");
		title.setBounds(300, 50, 200, 50);
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
		submit = new JButton("submit");
        submit.setBounds(250, 360, 80, 40);
        panel.add(submit);     
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit");
                if(rtype.getText()==""||desc.getText()==""||price.getText()==""){
                	System.out.println("not full info");
                }
                else{
                	//System.out.println(cname.getText());
                	String roomtype = rtype.getText();
                	String descr = desc.getText();
                	String sprice = price.getText();
                	if(sprice=="")
                		reminder("failed");
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
            				ResultSet rs1 = stmt1.executeQuery("select * from tarrif where rtype like '"+roomtype+"'");
            				if(rs1.next()){
            					
            					reminder();
            				}
            				else{
            					Statement stmt = (Statement) con.createStatement();
                				int rs = stmt.executeUpdate("insert into tarrif (rtype,rdesc,price) values('"+roomtype+"','"+descr+"','"+sprice+"')");
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
		reset = new JButton("Reset");
        reset.setBounds(350, 360, 70, 40);
        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rtype.setText("");
                desc.setText("");
                price.setText("");
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
        l1.setText("This room type already exist !");
        l1.setBounds(50, 30, 200, 50);
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
