package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.CinemaDAO;
import model.Sala;
import model.SalaDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;

public class EditDialog extends JDialog {
	/**
	 * Create the dialog.
	 */
	public EditDialog(int opt, Object obj) {
		switch (opt) {
		case 1:
			updateSala((Sala)obj);
			break;
		}
	}
	public void updateSala(Sala sala) {
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 450, 261);
		setModal(true); //Não permite que mais de um seja aberto
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblSala = new JLabel("SALA");
		JLabel lblCapacidade = new JLabel("Capacidade:");
		JLabel lblCinema = new JLabel("Cinema:");
		JSpinner spinner = new JSpinner();
		JComboBox comboBox = new JComboBox();
		JPanel buttonPane = new JPanel();
		JButton okButton = new JButton("Confirmar");
		{
			lblSala.setFont(new Font("Dialog", Font.BOLD, 16));
			lblSala.setBounds(188, 27, 66, 15);
			contentPanel.add(lblSala);
		}
		{
			lblCapacidade.setBounds(22, 82, 84, 15);
			contentPanel.add(lblCapacidade);
		}
		{
			lblCinema.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCinema.setBounds(22, 122, 84, 15);
			contentPanel.add(lblCinema);
		}
		{
			spinner.setModel(new SpinnerNumberModel(100, null, null, 1));
			if(sala!=null) {
				spinner.setValue(sala.getCapacidade());
			}
			spinner.setBounds(124, 80, 233, 20);
			contentPanel.add(spinner);
		}
		List<ComboItem> listOfItem = new ArrayList<ComboItem>();
		{
			comboBox.setBounds(124, 117, 233, 24);
			contentPanel.add(comboBox);
			ResultSet rs = new CinemaDAO().consult("SELECT * FROM CONTROLCINE.CINEMA");
			try {
				while(rs.next()) {
					listOfItem.add(new ComboItem(rs.getString("NOME"), rs.getString("CNPJ")));
				}
				for (int i = 0; i < listOfItem.size(); i++) {
					comboBox.addItem(listOfItem.get(i));
				}
			} catch (SQLException e) {
				System.err.println("Erro ao obter dados dos cinemas - SALADATA");
				e.printStackTrace();
			}
			if(sala!=null) {
				comboBox.setSelectedItem(sala.getNome());
			}
		}
		{
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int capacidade = (int) spinner.getValue();
						int cinema = comboBox.getSelectedIndex();
						ComboItem cbi = listOfItem.get(cinema);
						Sala sal = sala;
						sal.setCapacidade(capacidade);
						sal.setCNPJ(cbi.getValue());
						SalaDAO sdao = new SalaDAO();
						sdao.update(sal);
						JOptionPane.showMessageDialog(rootPane, "Dados atualizados com sucesso!",
								"SUCESSO", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
	}
}

class ComboItem
{
    private String key;
    private String value;

    public ComboItem(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
