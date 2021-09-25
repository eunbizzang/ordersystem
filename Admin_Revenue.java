package sist;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;



public class Admin_Revenue extends JFrame {
	Connection con = null;						// DB와 연결하는 객체.
	PreparedStatement pstmt1, pstmt2 = null;				// SQL문을 DB에 전송하는 객체.
	ResultSet rs1, rs2 = null;						// SQL문 실행 결과를 가지고 있는 객체.
	
	JPanel contentPane;
	DefaultTableModel model;
	JTable table;
	JLabel Label;
	JLabel Total_Label;
	//java.sql.Date sqldate1, sqldate2;
	String d1, d2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Admin_Revenue();
	}
	/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_Revenue frame = 
					frame.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Admin_Revenue() {
		connect();
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setBounds(27, 0, 859, 67);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		// 매출 조회 라벨
		JLabel Revenue = new JLabel("매출조회");
		Revenue.setBounds(12, 27, 110, 40);
		Revenue.setHorizontalAlignment(SwingConstants.CENTER);
		Revenue.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		panel1.add(Revenue);
		
		// 뒤로가기 버튼
		JButton btnNewButton = new JButton("뒤로가기");
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(255, 228, 225));
		btnNewButton.setBounds(746, 36, 101, 30);
		panel1.add(btnNewButton);
		
		// 테이블 헤더 작성
		String[] header = {"주문번호", "메뉴명", "가격", "주문수량", "합계", "주문시간"};
		
		model = new DefaultTableModel(header, 0);  // row count 일단 타이틀만 만들기 위해 0으로 설정
		
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		JScrollPane jsp = new JScrollPane(
				table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(37, 148, 841, 370);
		getContentPane().add(jsp);
		
		// 기간 선택 부분 올릴 panel
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setBounds(27, 66, 859, 82);
		contentPane.add(panel3);
		panel3.setLayout(null);
		
		// 기간 - 라벨
		Label = new JLabel("-");
		Label.setBounds(173, 41, 40, 33);
		Label.setHorizontalAlignment(SwingConstants.CENTER);
		Label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		panel3.add(Label);
		
		// 조회 버튼
		JButton Check_Button = new JButton("조회");
		Check_Button.setBounds(405, 45, 79, 29);
		Check_Button.setBackground(new Color(255, 228, 225));
		Check_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel3.add(Check_Button);
		
		// 기간 선택 라벨
		JLabel Period = new JLabel("조회 기간을 선택해 주세요");
		Period.setBounds(12, 10, 257, 33);
		Period.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel3.add(Period);
		
		// 첫번째 날짜
		JDateChooser dateChooser1 = new JDateChooser();
		dateChooser1.getCalendarButton().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		dateChooser1.setBounds(10, 45, 151, 29);
		panel3.add(dateChooser1);
		
		// 두번째 날짜
		JDateChooser dateChooser2 = new JDateChooser();
		dateChooser2.getCalendarButton().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		dateChooser2.setBounds(223, 45, 151, 29);
		panel3.add(dateChooser2);
		
		// 메뉴별 주문조회 버튼
		JButton Menucheck_Button = new JButton("메뉴별 주문조회");
		Menucheck_Button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		Menucheck_Button.setBackground(new Color(255, 228, 225));
		Menucheck_Button.setBounds(496, 45, 146, 29);
		panel3.add(Menucheck_Button);
		
		Total_Label = new JLabel("매출 총계  :  ");
		Total_Label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		Total_Label.setBounds(687, 528, 191, 35);
		contentPane.add(Total_Label);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 629);
		setVisible(true);
		
		Check_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(dateChooser1.getDate() == null || dateChooser2.getDate() == null) {
					JOptionPane.showMessageDialog(null, "기간을 선택 해 주세요.");
				}else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				d1 = dateFormat.format(dateChooser1.getDate());
				d2 = dateFormat.format(dateChooser2.getDate());
				//Date d2 = dateChooser2.getDate();
				
				
				//String date1= dateFormat.format(d1);
				//String date2= dateFormat.format(d2);
				
				//sqldate1 = java.sql.Date.valueOf(date1);
				//sqldate2 = java.sql.Date.valueOf(date2);
				
				model.setRowCount(0);   // 전체 테이블의 화면을 지워주는 메서드.
				select();
				}
			}
		});
		
		Menucheck_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new Admin_Revenue_Ranking(d1,d2);
				
			}
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Admin_Main();
				dispose();
			}
		});
		
	}// 생성자 end
	
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
		
	// dept 테이블의 전체 내역을 조회하는 메서드.

	void select() {
			
		try {
			// 1. 데이터베이스에 SQL문을 전송하기 위한 쿼리문 작성.
			String sql1 = "select order_id, menu_name, menu_price, menu_count, (menu_price * menu_count) as fprice, order_time from A_ORDER o left join A_MENU m "
					+ "on m.menu_id = o.menu_id "
					+ "where o.paid = 'O' and o.order_time between to_date('"+d1+"' , 'YYYY/MM/DD') and to_date('"+d2+"' || ' 23:59:59', 'YYYY/MM/DD HH24:MI:SS')"
					+ "order by order_time desc";
			
			String sql2 = "select sum(menu_price * menu_count) as total from A_ORDER o left join A_MENU m "
					+ "on m.menu_id = o.menu_id "
					+ "where o.paid = 'O' and o.order_time between to_date('"+d1+"', 'YYYY/MM/DD') and to_date('"+d2+"' || ' 23:59:59', 'YYYY/MM/DD HH24:MI:SS')";
			
			// 2. 쿼리문을 전송을 하자.
			pstmt1 = con.prepareStatement(sql1);
			pstmt2 = con.prepareStatement(sql2);
			// 3. 실제로 DB상에서 쿼리문을 실행하도록 하자.
			rs1 = pstmt1.executeQuery();
			rs2 = pstmt2.executeQuery();

			// 4. 레코드 수 만큼 반복하여 데이터를 추출하여 model 객체에 저장해 주자.
			while(rs1.next()) {
				int orderid = rs1.getInt("order_id");
				String mname = rs1.getString("menu_name");
				int mprice = rs1.getInt("menu_price");
				int mcount = rs1.getInt("menu_count");
				String fprice = rs1.getString("fprice");
				String ordertime = rs1.getString("order_time");
				
				Object[] data = {orderid, mname, mprice, mcount, fprice, ordertime};
					
				// 저장한 한 개의 레코드(data)를 model에 추가해 주면 됨.
				model.addRow(data);
				}
				
			if(rs2.next()) {
				String total = String.valueOf(rs2.getInt("total"));
				Total_Label.setText("매출 총계  :  " + total + " 원");
			}
				// 5. 연결되어 있던 객체 닫기.
				rs1.close(); rs2.close(); pstmt1.close(); pstmt2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "에러발생");
			e.printStackTrace();
		}
	}	// select()메서드의 end	
}
