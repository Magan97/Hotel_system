import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysql.jdbc.Statement;
import com.sun.glass.events.MouseEvent;


public class pay extends JFrame{
	JPanel panel1;
	JLabel title, total, total_v;
	ArrayList sum = new ArrayList();
	Button payit, back;
	int result=0;
	pay(){		
	}
	int mypay(int rno){
		//int result = 0;
		setSize(300,350);
        setTitle("Hotel System -- settle accounts");
        this.setLocation(450,180);
        panel1 = new JPanel();
        panel1.setLayout(null);
        title = new JLabel("Settle accounts");
        title.setBounds(100, 30, 100, 40);
        panel1.add(title);
        
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
			int i=1;
			try{
				Statement stmt1 = (Statement) con.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select count(*) from services where rno="+rno+"");
				if(rs1.next()){
					int n = rs1.getInt(1);
					data1 = new Object[n+1][3];
				}
				Statement stmt2 = (Statement) con.createStatement();
				ResultSet rs2 = stmt1.executeQuery("select * from rooms where rno="+rno+"");
				if(rs2.next()){
					String rt = rs2.getString("rtype");
					Statement stmt3 = (Statement) con.createStatement();
					ResultSet rs3 = stmt1.executeQuery("select * from tarrif where rtype like '"+rt+"'");
					if(rs3.next()){
						int p = rs3.getInt("price");
						data1[0][0] = "Room";
						data1[0][1] = rt;
						data1[0][2] = p;
						sum.add(p);
					}
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
			System.out.println("Can¡¯t load the Driver");
		}
        final JTable table = new JTable(data1, columnName);
        table.setPreferredScrollableViewportSize(new Dimension(260,100));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 260, 100);
        total = new JLabel("Total : ");
        total.setBounds(150, 180, 50, 30);
        panel1.add(total);
        int s=0;
        for(int i=0;i<sum.size();i++){
        	s += (int) sum.get(i);
        	System.out.println(i);
        }
        String su = s+"";
        total_v = new JLabel(su);
        total_v.setBounds(200, 180, 30, 30);
        panel1.add(total_v);
        panel1.add(scrollPane);
        payit = new Button("Pay");
        payit.setBounds(80, 220, 50, 30);
        panel1.add(payit);
        payit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	result = 1; 
            
            	dispose();
            }
        });
        back = new Button("Back");
        back.setBounds(170, 220, 50, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	result = 2;       
            	dispose();
            }
        });
        panel1.add(back);
        this.add(panel1);
        if(result == 1){
        	return 1;
        }
        else if(result == 2){
        	return 2;
        }
        else
        	return 0;
	}
}
