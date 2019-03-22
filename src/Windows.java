import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.slf4j.Logger;

import com.sourceforge.snap7.moka7.S7Client;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import si.trina.moka7.live.PLC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourceforge.snap7.moka7.S7;
import com.sourceforge.snap7.moka7.S7Client;
import java.awt.Font;
import javax.swing.JComboBox;


public class Windows {

	private JFrame frame;
	private JTextField DB1;
	private JTextField Datatype1;
	private JTextField value1;
	private JTextField DB2;
	private JTextField value2;
	private JTextField datatype2;
	private JTextField txtPlcStatus;
	byte[] db206buffer = new byte[14];
	 static Logger logger;
	 private static S7Client client;
	 private static int result;
	 private JTextField txtValueToSave;
	 private JButton SaveButt;
	 private JTextField ValToSave1;
	 private byte[] dataWrite = new byte[4];
	 private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windows window = new Windows();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Windows() {
		initialize();
		
		Connection();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 382);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DB");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 62, 24);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDatatype = new JLabel("Datatype");
		lblDatatype.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatatype.setBounds(92, 11, 62, 24);
		frame.getContentPane().add(lblDatatype);
		
		JButton btnNewButton = new JButton("Show values");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(result == 0) // == 0 connection is set, is ok
				{
					if(DB1.getText().isEmpty() || Datatype1.getText().isEmpty())
					{
					JOptionPane.showMessageDialog(null, "One of the parameters is empty!");
	
					}
					else
					{
						result = client.ReadArea(S7.S7AreaDB, Integer.parseInt(DB1.getText()), Integer.parseInt(Datatype1.getText()), 4, db206buffer);
						System.out.print("1: " + Integer.parseInt(DB1.getText()) + " 2 : " + Integer.parseInt(Datatype1.getText()));
						System.out.println(" vlaue:" +  S7.GetWordAt(db206buffer, 2));
						value1.setText(String.valueOf(S7.GetWordAt(db206buffer,2)));
						
						ValToSave1.setEditable(true);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "There is no valid connection to PLC");

				}
			}
		});
		btnNewButton.setBounds(10, 103, 111, 23);
		frame.getContentPane().add(btnNewButton);
		
		DB1 = new JTextField();
		DB1.setBounds(10, 41, 62, 20);
		frame.getContentPane().add(DB1);
		DB1.setColumns(10);
		
		Datatype1 = new JTextField();
		Datatype1.setBounds(92, 41, 62, 20);
		frame.getContentPane().add(Datatype1);
		Datatype1.setColumns(10);
		
		value1 = new JTextField();
		value1.setEditable(false);
		value1.setBounds(173, 41, 86, 20);
		frame.getContentPane().add(value1);
		value1.setColumns(10);
		
		JLabel lblValue = new JLabel("Current value");
		lblValue.setBounds(173, 16, 86, 14);
		frame.getContentPane().add(lblValue);
		
		DB2 = new JTextField();
		DB2.setColumns(10);
		DB2.setBounds(10, 72, 62, 20);
		frame.getContentPane().add(DB2);
		
		value2 = new JTextField();
		value2.setEditable(false);
		value2.setColumns(10);
		value2.setBounds(173, 72, 86, 20);
		frame.getContentPane().add(value2);
		
		datatype2 = new JTextField();
		datatype2.setColumns(10);
		datatype2.setBounds(92, 73, 62, 20);
		frame.getContentPane().add(datatype2);
		
		txtPlcStatus = new JTextField();
		txtPlcStatus.setEditable(false);
		txtPlcStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlcStatus.setText("PLC status");
		txtPlcStatus.setBounds(370, 313, 127, 20);
		frame.getContentPane().add(txtPlcStatus);
		txtPlcStatus.setColumns(10);
		
		txtValueToSave = new JTextField();
		txtValueToSave.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueToSave.setEditable(false);
		txtValueToSave.setText("Value to save");
		txtValueToSave.setBounds(269, 13, 86, 20);
		frame.getContentPane().add(txtValueToSave);
		txtValueToSave.setColumns(10);
		
		SaveButt = new JButton("Save val");
		SaveButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(ValToSave1.getText().isEmpty() || ValToSave1.getText().contains("-"))
				{
					JOptionPane.showMessageDialog(null, "negative value or NULL value are forbidden!");
				}
				else
				{
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to save new value?");
					if(dialogResult == JOptionPane.YES_OPTION){
						S7.SetWordAt(dataWrite, 2, Integer.parseInt(ValToSave1.getText()));
						result = client.WriteArea(S7.S7AreaDB, Integer.parseInt(DB1.getText()), Integer.parseInt(Datatype1.getText()), 4, dataWrite);
					}
				}
			}
		});
		SaveButt.setBounds(373, 40, 86, 23);
		frame.getContentPane().add(SaveButt);
		
		ValToSave1 = new JTextField();
		ValToSave1.setEditable(false);
		ValToSave1.setBounds(269, 41, 86, 20);
		frame.getContentPane().add(ValToSave1);
		ValToSave1.setColumns(10);
		
		btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection(); // reset connection
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_1.setBounds(298, 312, 62, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("ENG");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(10, 287, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("DEU");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(10, 312, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
	}
	

	
	
	private void Connection()
	{
		client = new S7Client();
		result = client.ConnectTo("192.168.90.125", 0,2);
		
		if(result == 0)
		{
			System.out.println("Connected");
			txtPlcStatus.setForeground(Color.GREEN);
			txtPlcStatus.setText("PLC is Connected");
		}
		else
		{
			System.out.println("Something wrong in connection");
			txtPlcStatus.setForeground(Color.RED);
			txtPlcStatus.setText("PLC is not connected");
		}
	}
}
