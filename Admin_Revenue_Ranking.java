package sist;

import java.awt.*;
import java.sql.C*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class Admin_Revenue_Ranking extends JFrame {

	JPanel contentPane;
	JTable table1, table2, table3;
	DefaultTableModel model1, model2, model3;
	
	Connection con = null;						// DB와 연결하는 객체.
	PreparedStatement pstmt1, pstmt2, pstmt3 = null;		// SQL문을 DB에 전송하는 객체.
	ResultSet rs1, rs2, rs3 = null;					// SQL문 실행 결과를 가지고 있는 객체.
	String d1, d2;							// Admin_Revenue페이지에서 받아온 date값을 저장하는 String객체.

	/**
	 * Launch the application.
	 */
	
	public Admin_Revenue_Ranking(String d1, String d2) {
		this.d1 = d1;
		this.d2 = d2;
		connect();
		
		// 창 닫았을 때 기간선택화면으로 돌아가기 위해 주석처리
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(53, 50, 556, 41);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// 선택 기간 라벨
		JLabel Period_Label = new JLabel("선택 기간");
		Period_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Period_Label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		Period_Label.setBounds(0, 10, 87, 29);
		panel.add(Period_Label);
		
		// 메뉴별 매출 라벨
		JLabel MainLabel = new JLabel("메뉴별 매출");
		MainLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		MainLabel.setBounds(54, 10, 310, 41);
		contentPane.add(MainLabel);

		// 날짜 선택 (첫번째)
		JDateChooser dateChooser1 = new JDateChooser();
		dateChooser1.getCalendarButton().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		dateChooser1.setBounds(99, 10, 143, 29);
		panel.add(dateChooser1);

		// - 라벨
		JLabel Label = new JLabel("-");
		Label.setFont(new Font("굴림", Font.PLAIN, 18));
		Label.setHorizontalAlignment(SwingConstants.CENTER);
		Label.setBounds(254, 17, 38, 15);
		panel.add(Label);
		
		// 날짜 선택 (두번째)
		JDateChooser dateChooser2 = new JDateChooser();
		dateChooser2.getCalendarButton().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		dateChooser2.setBounds(300, 10, 143, 29);
		panel.add(dateChooser2);
		
		// 검색 버튼
		JButton Check_Button = new JButton("검색");
		Check_Button.setBackground(new Color(255, 228, 225));
		Check_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		Check_Button.setBounds(473, 10, 71, 28);
		panel.add(Check_Button);
		
		// 검색결과를 표시 할 테이블의 헤더설정
		String[] header = {"메뉴", "주문수량", "매출"};
		
		model1 = new DefaultTableModel(header, 0);  // row count 일단 타이틀만 만들기 위해 0으로 설정
		model2 = new DefaultTableModel(header, 0);
		model3 = new DefaultTableModel(header, 0);
		
		// 안주 메뉴 주문량을 순서대로 표시 할 테이블
		table1 = new JTable(model1);
		JScrollPane jsp1 = new JScrollPane(
				table1,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp1.setBounds(44, 136, 192, 192);
		contentPane.add(jsp1);
		
		// 음료 메뉴 주문량을 순서대로 표시 할 테이블
		table2 = new JTable(model2);
		JScrollPane jsp2 = new JScrollPane(
				table2,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setBounds(238, 136, 192, 192);
		getContentPane().add(jsp2);
		
		// 주류 메뉴 주문량을 순서대로 표시 할 테이블
		table3 = new JTable(model3);
		JScrollPane jsp3 = new JScrollPane(
				table3,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp3.setBounds(431, 136, 192, 192);
		getContentPane().add(jsp3);
		
		// 안주 문구 표시 버튼
		JButton a0_Button = new JButton("안주");
		a0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		a0_Button.setBackground(new Color(255, 228, 225));
		a0_Button.setBounds(44, 103, 192, 35);
		contentPane.add(a0_Button);
		
		// 음료 문구 표시 버튼
		JButton b0_Button = new JButton("음료");
		b0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		b0_Button.setBackground(new Color(255, 228, 225));
		b0_Button.setBounds(238, 103, 192, 35);
		contentPane.add(b0_Button);
		
		// 주류 문구 표시 버튼
		JButton c0_Button = new JButton("주류");
		c0_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		c0_Button.setBackground(new Color(255, 228, 225));
		c0_Button.setBounds(431, 103, 192, 35);
		contentPane.add(c0_Button);
		
		// d1에 값이 들어있는지 확인 후 값이 있을 경우, 그 값을 dateChooser에 표시 
		if(d1 != null) {
			try {
				SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
				Date date1 = fm.parse(d1);
				Date date2 = fm.parse(d2);
				dateChooser1.setDate(date1);
				dateChooser2.setDate(date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// select기능을 불러옴
			select(d1, d2);
		}
		// 새로운 날짜가 입력되고 버튼이 눌리게 되면 테이블 내용을 리셋하고 새로운 date값을 입력해서 테이블 내용을 select
		Check_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				String newd1 = dateFormat.format(dateChooser1.getDate());
				String newd2 = dateFormat.format(dateChooser2.getDate());
				model1.setRowCount(0);
				model2.setRowCount(0);
				model3.setRowCount(0);
				select(newd1,newd2);
			}
		});
		
		
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
	
	// d1,d2 사이 기간의 주문 내역을 불러오는 메서드
	void select(String d1, String d2) {
		try {
			// 안주, 음료, 주류 테이블에 반복적으로 사용되는 sql문
			String sqlsame = "SELECT MENU_NAME, SUM(MENU_COUNT) AS COUNT, SUM(MENU_COUNT* MENU_PRICE) as total " + 
					"FROM A_OORDER o left join A_MENU m " + 
					"ON O.MENU_ID = M.MENU_ID " + 
					"where o.paid = 'O' and o.order_time between to_date('"+d1+"' , 'YYYY/MM/DD') and to_date('"+d2+"' || ' 23:59:59', 'YYYY/MM/DD HH24:MI:SS') " + 
					"and o.menu_id like";
			// 안주 테이블에 표시 할 안주 주문 내역을 불러오는 sql문
			String sql1 = sqlsame+ " 'a0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			// 음료 테이블에 표시 할 음료 주문 내역을 불러오는 sql문
			String sql2 = sqlsame+ " 'b0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			// 주류 테이블에 표시 할 주류 주문 내역을 불러오는 sql문
			String sql3 = sqlsame+ " 'c0%' " + 
					"GROUP BY M.MENU_NAME " + 
					"ORDER BY SUM(MENU_COUNT) DESC";
			// sql1,2,3을 연결
			pstmt1 = con.prepareStatement(sql1);
			pstmt2 = con.prepareStatement(sql2);
			pstmt3 = con.prepareStatement(sql3);
			
			// sql문 실행(안주 테이블)
			rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				String mname = rs1.getString("menu_name");
				int count = rs1.getInt("count");
				int total = rs1.getInt("total");
				
				
				Object[] data = {mname, count, total};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model1.addRow(data);
				}
			
			// sql문 실행(음료 테이블)
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) {
				String mname = rs2.getString("menu_name");
				int count = rs2.getInt("count");
				int total = rs2.getInt("total");
				
				
				Object[] data = {mname, count, total};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model2.addRow(data);
				}
			
			// sql문 실행(주류 테이블)
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
