package bankmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener{
    String pinNumber;
    JButton hundred,fivehundred,onethousand,tenthousand, twothousand, fivethousand,exit;
    FastCash(String pinNumber){
        this.pinNumber = pinNumber;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT:");
        text.setBounds(210,300,700,35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD,16));
        image.add(text); 
        
        hundred = new JButton("Rs 100");
        hundred.setBounds(170,415,150,30);
        hundred.addActionListener(this);
        image.add(hundred);
        
        fivehundred = new JButton("Rs 500");
        fivehundred.setBounds(355,415,150,30);
        fivehundred.addActionListener(this);
        image.add(fivehundred);
        
        onethousand = new JButton("Rs 1000");
        onethousand.setBounds(170,450,150,30);
        onethousand.addActionListener(this);
        image.add(onethousand);
        
        twothousand = new JButton("Rs 2000");
        twothousand.setBounds(355,450,150,30);
        twothousand.addActionListener(this);
        image.add(twothousand);
        
        fivethousand = new JButton("Rs 5000");
        fivethousand.setBounds(170,485,150,30);
        fivethousand.addActionListener(this);
        image.add(fivethousand);
        
        tenthousand = new JButton("Rs 10000");
        tenthousand.setBounds(355,485,150,30);
        tenthousand.addActionListener(this);
        image.add(tenthousand);
        
        exit = new JButton("BACK");
        exit.setBounds(355,520,150,30);
        exit.addActionListener(this);
        image.add(exit);
        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==exit){
            setVisible(false);
            new Transactions(pinNumber).setVisible(true);
        }else {
            String amount = ((JButton)ae.getSource()).getText().substring(3);
            Conn c = new Conn();
            try{
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinNumber+"'");
                int balance = 0;
                while(rs.next()){
                    if(rs.getString("type").equals("Deposit")){
                        balance+=Integer.parseInt(rs.getString("amount"));
                    }else{
                        balance-=Integer.parseInt(rs.getString("amount"));
                    }
                }
                if(ae.getSource()!=exit && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    return;
                }
                
                Date date = new Date();
                String query = "insert into bank values('"+pinNumber+"','"+date+"','Withdrawl','"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null,"Rs "+amount+" Debited Successfully");
                
                setVisible(false);
                new Transactions(pinNumber).setVisible(true);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public static void main(String[] args){
        new FastCash("");
    }
}
