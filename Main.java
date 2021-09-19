package sist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends JFrame {
	Connection con = null;						// DB와 연결하는 객체.
	PreparedStatement pstmt = null;				// SQL문을 DB에 전송하는 객체.
	ResultSet rs = null;						// SQL문 실행 결과를 가지고 있는 객체.
	String sql = null;							// SQL문 저장하는 문자열 변수.
	String id;									// ID 변수 선언.
	String pw = null;							// PW 변수 선언.
	
	JPanel contentPane;
	JTextField ID_Text;
	JPasswordField PW_Field;

	// Frame 작성
	public Main() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
	
		// 관리자 로그인 문구 표시 패널 생성
		JPanel panel1 = new JPanel();
		panel1.setBounds(12, 67, 412, 490);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
	/*	
		// 관리자 로그인 문구 표시 라벨
		JLabel Title_Label = new JLabel("관리자 로그인");
		Title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Title_Label.setFont(new Font("맑은 고딕", Font.BOLD, 45));
		Title_Label.setBounds(266, 10, 317, 65);
		panel1.add(Title_Label);
	*/
		
		
		ImageIcon logo = new ImageIcon("images/projectlogo.jpg"); // 이미지 아이콘
		
		
		JLabel lblNewLabel_1 = new JLabel(logo);
		lblNewLabel_1.setBounds(35, 58, 342, 339);
		panel1.add(lblNewLabel_1);
		
		// ID, PW 입력 표시 패널 생성
		JPanel panel2 = new JPanel();
		panel2.setBounds(427, 67, 494, 490);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		// ID 라벨
		JLabel ID_Label = new JLabel("ID");
		ID_Label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		ID_Label.setBounds(50, 129, 104, 49);
		panel2.add(ID_Label);
		
		// PW 라벨
		JLabel PW_Label = new JLabel("PW");
		PW_Label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		PW_Label.setBounds(50, 210, 104, 49);
		panel2.add(PW_Label);
		
		// ID 입력 텍스트필드
		ID_Text = new JTextField();
		ID_Text.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		ID_Text.setColumns(15);
		ID_Text.setBounds(175, 131, 243, 49);
		panel2.add(ID_Text);
		
		// PW 입력 패스워드필드
		PW_Field = new JPasswordField();
		PW_Field.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		PW_Field.setColumns(15);
		PW_Field.setBounds(175, 212, 243, 49);
		PW_Field.setEchoChar('*');
		panel2.add(PW_Field);
		
		// 로그인 버튼
		JButton Login_Button = new JButton("로그인");
		Login_Button.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		Login_Button.setBackground(new Color(255, 228, 225));
		Login_Button.setForeground(Color.BLACK);
		Login_Button.setBounds(168, 355, 144, 56);
		panel2.add(Login_Button);
		
		connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		setVisible(true);
		
		// 로그인 버튼 클릭시 아이디 패스워드 확인 기능
		Login_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    
				// PW_Field의 값을 받아와서 String형태로 저장
			    id = ID_Text.getText();
			    char[] pwarray = PW_Field.getPassword(); 
			    pw = String.valueOf(pwarray);
			    
				// 로그인 메서드를 불러와서 리턴값을 확인
			    int result = login();
			    if(result == 1) {
			    	//new Admin_Select();
					dispose();
			    }else if (result == -1) {
			    	JOptionPane.showMessageDialog(null, "로그인 정보가 없습니다. ID,PW를 다시 확인 해 주세요.");
			    }else if (result == 0) {
			    	JOptionPane.showMessageDialog(null, "로그인 에러가 발생했습니다.");
			    }
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
		}
	}  // connect() 메서드 end
	
	int login() {
				// Query 작성
				try {
					pstmt = con.prepareStatement("select * from A_LOGIN where USER_ID = ? and USER_PWD = ?");
					// Query ? 완성 (index 1번 부터 시작)
					pstmt.setString(1, id);
					pstmt.setString(2, pw);
					
					// Query 실행
					rs = pstmt.executeQuery();
					
					// 일치하는 결과가 있는지 확인, 로그인 정보가 있으면 1을 반환, 없다면 -1을 반환.
					if(rs.next()) { //next()함수는 커서를 한칸 내리면서 해당 행에 데이터가 있으면 true, 없으면 false 반환
						//결과가 있다는 것은 해당 아이디와 비번에 매칭되는 값이 있다는 뜻.
						return 1; //로그인 성공
					}else {
						return -1; //로그인 실패
					}
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
}
