package view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;

import model.Funcionario;
import model.FuncionarioDAO;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Login extends JFrame {

	private static UserData userPswd = new UserData();
	private Funcionario f;
	/**
	 * Create the frame.
	 */
	public Login(UserData data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 440);
		BufferedImage myImage;
		try {
			myImage = ImageIO.read(new File("src/img/backgroundLogin.png"));
			view.ImagePanel imagePanel = new ImagePanel(myImage);
			setContentPane(imagePanel);
			imagePanel.setLayout(null);

			JLabel lblLoginControlcine = new JLabel("");
			lblLoginControlcine.setIcon(new ImageIcon(Login.class.getResource("/img/logintxt2.png")));
			lblLoginControlcine.setFont(new Font("Open Sans", Font.BOLD, 35));
			lblLoginControlcine.setForeground(Color.WHITE);
			lblLoginControlcine.setHorizontalAlignment(SwingConstants.CENTER);
			lblLoginControlcine.setBounds(12, 65, 712, 48);
			imagePanel.add(lblLoginControlcine);

			JButton btnLogin = new JButton("");
			btnLogin.setOpaque(false);
			btnLogin.setContentAreaFilled(false);
			btnLogin.setBorderPainted(false);
			btnLogin.setToolTipText("Entra no sistema p/ venda de ingressos!");
			btnLogin.setIcon(new ImageIcon(Login.class.getResource("/img/login_btn2.png")));
			btnLogin.setBounds(139, 236, 199, 47);
			btnLogin.setPressedIcon(new ImageIcon(Login.class.getResource("/img/login_btn2_clicked.png")));
			imagePanel.add(btnLogin);
			JButton btnCadastro = new JButton("");
			btnCadastro.setOpaque(false);
			btnCadastro.setContentAreaFilled(false);
			btnCadastro.setBorderPainted(false);
			btnCadastro.setToolTipText("Administração do Sistema");
			btnCadastro.setIcon(new ImageIcon(Login.class.getResource("/img/admin_btn.png")));
			btnCadastro.setBackground(Color.LIGHT_GRAY);
			btnCadastro.setBounds(397, 236, 199, 45);
			imagePanel.add(btnCadastro);

			btnLogin.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean sucessful = Login();
					if(sucessful) {
						dispose();
						System.out.println(f.getNome());
						VendaIngresso vi = new VendaIngresso(f);
						vi.setVisible(true);
					}
				}
			});

			btnCadastro.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean sucessful = Login();
					if(sucessful) {
						if(f.isAdmin()) {
							if(f.getPswd().equals(userPswd.getPassword())) {
								dispose();
								AdminPanel ap = new AdminPanel();
								ap.setVisible(true);
							}else {
								JOptionPane.showMessageDialog(rootPane, "ERRO! Senha Incorreta!",
										"WRONG PASSWORD", JOptionPane.WARNING_MESSAGE);
								LoginDialog ld1 = new LoginDialog(userPswd);
								ld1.setVisible(true);
							}
						}else {
							JOptionPane.showMessageDialog(rootPane, "ERRO! Você não tem permissão de administrador!",
									"ERRO", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});

		} catch (IOException e) {
			System.err.println("ERROR! Background of Login not found!");
		}

		setLocationRelativeTo(null);
		setTitle("ControlCine System");
		setResizable(false);
	}
	private boolean Login() {
		boolean res = false;
		LoginDialog ld = new LoginDialog(userPswd);
		ld.setVisible(true);
		FuncionarioDAO func = new FuncionarioDAO();
		ResultSet r = func.getFuncionarioPerCPFResultSet(userPswd.getCPF());
		func.encerrar();
		try {
			if(!r.next()) {
				JOptionPane.showMessageDialog(rootPane, "Usuário não cadastrado!", "USER NOT FOUND", JOptionPane.ERROR_MESSAGE);
			}else {
				try {
					f = new Funcionario(r.getString("ID"), 
							r.getString("NOME"),
							r.getBoolean("ADMIN"),
							r.getString("SENHA"));
					if(!userPswd.getPassword().equals(f.getPswd())){
						JOptionPane.showMessageDialog(rootPane, "Senha Incorreta!", "WRONG PASSWORD", JOptionPane.WARNING_MESSAGE);
					}else {
						res=true;
					}
				} catch (SQLException e) {
					System.err.println("SQLException on getting information of Funcionario");
				}
			}
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login(userPswd);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

class ImagePanel extends JPanel {
	private Image image;
	public ImagePanel(Image image) {
		this.image = image;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}


