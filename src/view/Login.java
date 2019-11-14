package view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Login extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
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
			btnLogin.setToolTipText("Entra no sistema p/ venda ou administração");
			btnLogin.setIcon(new ImageIcon(Login.class.getResource("/img/login_btn2.png")));
			btnLogin.setBounds(139, 236, 199, 47);
			imagePanel.add(btnLogin);
			
			JButton btnCadastro = new JButton("");
			btnCadastro.setOpaque(false);
			btnCadastro.setContentAreaFilled(false);
			btnCadastro.setBorderPainted(false);
			btnCadastro.setToolTipText("Adiciona novo funcionário. ADMIN apenas!");
			btnCadastro.setIcon(new ImageIcon(Login.class.getResource("/img/admin_btn.png")));
			btnCadastro.setBackground(Color.LIGHT_GRAY);
			btnCadastro.setBounds(397, 236, 199, 45);
			imagePanel.add(btnCadastro);
		} catch (IOException e) {
			System.err.println("ERROR! Background of Login not found!");
		}
		setLocationRelativeTo(null);
		setTitle("ControlCine System");
		setResizable(false);
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