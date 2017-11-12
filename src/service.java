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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class service extends JFrame{
	int index,all;
	String cstype;
	JPanel panel;
	JLabel title,roomno,ctime,amount;
	JComboBox<Object> choose_type;
	JTextField desc,amounto;
	JButton check,edit,save,back,first,last,next,end;
	service(int in){
		index = in;
		setSize(800,600);
        setTitle("Hotel System -- service information");
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
        title = new JLabel("Service information");
        title.setBounds(270, 50, 200, 50);
        panel.add(title);
        String[] name = {"Room number : ", "Date : ", "Service type : ","Amount : "};
        for(int i=0;i<4;i++){
        	JLabel li = new JLabel(name[i]);
        	li.setBounds(200,120+50*i, 100, 50);
        	panel.add(li);
        }
        roomno = new JLabel();
        roomno.setBounds(350, 130, 100, 30);
        panel.add(roomno);        
        ctime = new JLabel();
        ctime.setBounds(350, 180, 100, 20);
        panel.add(ctime);
        String[] type = {"restaurant-R","laundry-L","transportation-T","wifi-W","others-O"};
        choose_type = new JComboBox<Object>(type);
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
        			if(choose_type.getSelectedItem().toString().indexOf("others-O")!=-1){
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
        
		look(index);
		getser();
		check = new JButton("check");
        check.setBounds(200, 380, 70, 30);
        panel.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                look(index);
            }
        });
        edit = new JButton("edit");
        edit.setBounds(280, 380, 70, 30);
        panel.add(edit);
        edit.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			@Override
            public void actionPerformed(ActionEvent e) {
                look(index);
                String select = choose_type.getSelectedItem().toString();
                if(choose_type.getItemCount() == 5){
                	choose_type.addItem("delete-D");
                	System.out.println("choose_type have "+choose_type.getItemCount());
                }
                	
                if(select.indexOf("others-O")!=-1){
                	desc.setVisible(true);
                	desc.setEditable(true);
                	amounto.setEditable(true);
                	//choose_type.addItem("delete-D");
                }
            }
        });
        save = new JButton("save");
        save.setBounds(360, 380, 70, 30);
        panel.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(choose_type.getSelectedItem().toString().indexOf("others-O")!=-1 && desc.getText()==""){
                	System.out.println("not full info");              	
                }
                else{
                	String srno = roomno.getText();
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
            				if(choose_type.getSelectedItem().toString().indexOf("delete-D")!=-1){ 	
            					System.out.println(srno);
            					System.out.println(sdate);
            					System.out.println(cstype);
            					System.out.println(describe);
            					System.out.println(samount);
            					int rs = stmt.executeUpdate("delete from services where rno='"+srno+"' and datetype='"+sdate+"' and stype='"+cstype+"' and sdesc='"+describe+"' and amount='"+samount+"'");
                				if(rs > 0)
                					System.out.println("delete success");
                				else{
                					System.out.println("delete failed");
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
        first = new JButton("first");
        first.setBounds(200, 420, 70, 30);
        panel.add(first);
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//rid = minID(); 
            	index = 0;
            	look(index);
            }
        });
        last = new JButton("last");
        last.setBounds(280, 420, 70, 30);
        panel.add(last);
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(index > 0){
            		index--;
                	look(index);
            	}           
            }
        });
        next = new JButton("next");
        next.setBounds(360, 420, 70, 30);
        panel.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(index+1 <= all){
            		index++;
            		look(index);
            	}
            }
        });
        end = new JButton("end");
        end.setBounds(440, 420, 70, 30);
        panel.add(end);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	index = all;
            	look(index);
            }
        });
        this.add(panel);
	}
	public int look(int index){
		try {
			Class.forName(com.mysql.jdbc.Driver.class.getName());
			String url = "jdbc:mysql://localhost/hotel system";
			String login = "root";
			String password = "";
			Connection con;
			con = DriverManager.getConnection(url, login, password);
			con.setAutoCommit(false);
			int i=-1;
			try{
				Statement stmt = (Statement) con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from services");
				while(rs.next()){
					i++;
					if(i == index){
						String rno = rs.getInt(1)+"";
						roomno.setText(rno);
						Date time = rs.getDate(2);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String mytime = sdf.format(time);
						ctime.setText(mytime);
						String type = rs.getString("stype");
						cstype = type;
						String desc1 = "";
						String am = rs.getInt(5)+"";
						amount.setVisible(true);
						amount.setText(am);
						if(type.indexOf("L")!=-1)
							type = "laundry-L";
						if(type.indexOf("R")!=-1)
							type = "restaurant-R";
						if(type.indexOf("T")!=-1)
							type = "transportation-T";
						if(type.indexOf("W")!=-1)
							type = "wifi-W";
						if(type.indexOf("O")!=-1){
							type = "others-O";
							desc1 = rs.getString("sdesc");
							desc.setVisible(true);
							desc.setText(desc1);
							desc.setEditable(false);
							amount.setVisible(false);
	        	        	amounto.setVisible(true);
	        	        	amounto.setEditable(false);
	        	        	amounto.setText(am);
						}
						/*
						if(type.indexOf("O")!=-1){
							amounto.setVisible(true);
							amounto.setEditable(false);
							
							amount.setVisible(true);
							amount.setText(am);
							//desc.setVisible(false);
						}*/
						choose_type.setSelectedItem(type);
					}
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
	public int getser(){
		all = 0;
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
					all++;					
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
