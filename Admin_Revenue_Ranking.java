package sist;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Admin_Revenue_Ranking extends JFrame {

	JPanel contentPane;
	JTextField textField1;
	JTextField textField2;
	JTable table1, table2, table3;
	DefaultTableModel model1, model2, model3;
	
	Connection con = null;						// DB와 연결하는 객체.
	PreparedStatement pstmt1, pstmt2, pstmt3 = null;				// SQL문을 DB에 전송하는 객체.
	ResultSet rs1, rs2, rs3 = null;						// SQL문 실행 결과를 가지고 있는 객체.
	String d1, d2;

	/**
	 * Launch the application.
	 */
	
	public Admin_Revenue_Ranking(String d1, String d2) {
		this.d1 = d1;
		this.d2 = d2;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(44, 21, 579, 72);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("선택 기간");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel.setBounds(69, 23, 87, 29);
		panel.add(lblNewLabel);
		
		textField1 = new JTextField();
		textField1.setHorizontalAlignment(SwingConstants.CENTER);
		textField1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField1.setBounds(169, 21, 156, 34);
		panel.add(textField1);
		textField1.setColumns(10);
		textField1.setEditable(false);
		textField1.setText(d1);
		
		textField2 = new JTextField();
		textField2.setHorizontalAlignment(SwingConstants.CENTER);
		textField2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField2.setBounds(374, 21, 143, 34);
		panel.add(textField2);
		textField2.setColumns(10);
		textField2.setEditable(false);
		textField2.setText(d2);
		
		JLabel lblNewLabel_1 = new JLabel("-");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(334, 30, 38, 15);
		panel.add(lblNewLabel_1);
		
		String[] header = {"메뉴", "주문수량", "매출"};
		
		model1 = new DefaultTableModel(header, 0);  // row count 일단 타이틀만 만들기 위해 0으로 설정
		model2 = new DefaultTableModel(header, 0);
		model3 = new DefaultTableModel(header, 0);
		
		
		table1 = new JTable(model1);
		JScrollPane jsp1 = new JScrollPane(
				table1,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp1.setBounds(44, 136, 192, 192);
		contentPane.add(jsp1);
		
		table2 = new JTable(model2);
		JScrollPane jsp2 = new JScrollPane(
				table2,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setBounds(238, 136, 192, 192);
		getContentPane().add(jsp2);
		
		table3 = new JTable(model3);
		JScrollPane jsp3 = new JScrollPane(
				table3,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp3.setBounds(431, 136, 192, 192);
		getContentPane().add(jsp3);
		
		JButton a0_Button = new JButton("안주");
		a0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		a0_Button.setBackground(new Color(255, 228, 225));
		a0_Button.setBounds(44, 103, 192, 35);
		contentPane.add(a0_Button);
		
		JButton b0_Button = new JButton("음료");
		b0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		b0_Button.setBackground(new Color(255, 228, 225));
		b0_Button.setBounds(238, 103, 192, 35);
		contentPane.add(b0_Button);
		
		JButton c0_Button = new JButton("주류");
		c0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		c0_Button.setBackground(new Color(255, 228, 225));
		c0_Button.setBounds(431, 103, 192, 35);
		contentPane.add(c0_Button);
		
		JLabel lblNewLabel_2 = new JLabel("안주");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setIcon(null);
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBackground(new Color(255, 228, 225));
		lblNewLabel_2.setBounds(44, 103, 192, 35);
		contentPane.add(lblNewLabel_2);
		
		connect();
		select(d1, d2);
	}

	// DB와 연동하는 메서드
	void connect() {
		String driver = "oracle.jdbc.driver.OracleDriver"; 
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String user = "web"; 
		String password = "1234";
				
		try {
			// 1. 접속할 오라클 드라이버를 메모리로 로딩.
			Class.forName(driver);
					
			// 2. 오라클 데이터베이스와 연결 작업 진행.
			con = DriverManager.getConnection(url, user, password);
					
		} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "DB연결 에러발생");
		}
	}  // connect() 메서드 end
	void select(String d1, String d2) {
		try {
			String sqlsame = "SELECT MENU_NAME, SUM(MENU_COUNT) AS COUNT, SUM(MENU_COUNT* MENU_PRICE) as total " + 
					"FROM A_OORDER o left join A_MENU m " + 
					"ON O.MENU_ID = M.MENU_ID " + 
					"where o.paid = 'O' and o.order_time between to_date('"+d1+"' , 'YYYY/MM/DD') and to_date('"+d2+"' || ' 23:59:59', 'YYYY/MM/DD HH24:MI:SS') " + 
					"and o.menu_id like";
			String sql1 = sqlsame+ " 'a0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			
			String sql2 = sqlsame+ " 'b0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			
			String sql3 = sqlsame+ " 'c0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			pstmt1 = con.prepareStatement(sql1);
			pstmt2 = con.prepareStatement(sql2);
			pstmt3 = con.prepareStatement(sql3);
			
			rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				String mname = rs1.getString("menu_name");
				int count = rs1.getInt("count");
				int total = rs1.getInt("total");
				
				
				Object[] data = {mname, count, total};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model1.addRow(data);
				}
			
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) {
				String mname = rs2.getString("menu_name");
				int count = rs2.getInt("count");
				int total = rs2.getInt("total");
				
				
				Object[] data = {mname, count, total};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model2.addRow(data);
				}
			
			rs3 = pstmt3.executeQuery();
			while(rs3.next()) {
				String mname = rs3.getString("menu_name");
				int count = rs3.getInt("count");
				int total = rs3.getInt("total");
				
				
				Object[] data = {mname, count, total};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model3.addRow(data);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}