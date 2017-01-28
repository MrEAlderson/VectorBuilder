package de.marcely.vectorbuilder.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.marcely.vectorbuilder.VectorShape.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class Frame_PointSettings extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	public Frame_PointSettings(final Point point){
		super("Point Settings");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPointdata = new JLabel("Point x:" + point.getX() + " | y:" + point.getY());
		lblPointdata.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblPointdata, BorderLayout.NORTH);
		
		JSeparator separator = new JSeparator();
		panel.add(separator, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblIdentificationName = new JLabel("Identification Name:");
		GridBagConstraints gbc_lblIdentificationName = new GridBagConstraints();
		gbc_lblIdentificationName.anchor = GridBagConstraints.EAST;
		gbc_lblIdentificationName.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdentificationName.gridx = 0;
		gbc_lblIdentificationName.gridy = 0;
		panel_1.add(lblIdentificationName, gbc_lblIdentificationName);
		
		final JTextField textField = new JTextField(point.getIdentificationName());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		contentPane.add(btnSave, BorderLayout.SOUTH);
		final JFrame frame = this;
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(point.exists()){
					
					point.setIdentificationName(textField.getText());
					
				}else
					JOptionPane.showMessageDialog(frame, "Point doesn't exists!", "Warning", JOptionPane.WARNING_MESSAGE);
				
				frame.dispose();
			}
		});
	}
}
