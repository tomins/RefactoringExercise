
import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.text.MaskFormatter;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Menu extends JFrame {

	protected ArrayList<Customer> customerList = new ArrayList<Customer>();
	protected Customer customer = null;
	protected CustomerAccount acc = new CustomerAccount();
	JFrame mainFrame, secondaryFrame;
	JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
	JTextField dOBTextField, firstNameTextField, surnameTextField , pPSTextField;
	JLabel customerIDLabel, passwordLabel;
	JTextField passwordTextField, customerIDTextField;
	
	public static void main(String[] args) {
		Menu driver = new Menu();
		driver.menuStart();
	}

	public void menuStart() {
		/*
		 * The menuStart method asks the user if they are a new customer, an existing
		 * customer or an admin. It will then start the create customer process if they
		 * are a new customer, or will ask them to log in if they are an existing
		 * customer or admin.
		 */

		mainFrame = new JFrame("User Type");
		mainFrame.setSize(400, 300);
		mainFrame.setLocation(200, 200);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioButton;
		userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
		radioButton.setActionCommand("Customer");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("Administrator"));
		radioButton.setActionCommand("Administrator");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("New Customer"));
		radioButton.setActionCommand("New Customer");
		userType.add(radioButton);

		JPanel continuePanel = new JPanel();
		JButton continueButton = new JButton("Continue");
		continuePanel.add(continueButton);

		Container mainFrameContent = mainFrame.getContentPane();
		mainFrameContent.setLayout(new GridLayout(2, 1));
		mainFrameContent.add(userTypePanel);
		mainFrameContent.add(continuePanel);

		continueButton.addActionListener(new ActionListener() {
			private String password;
			public void actionPerformed(ActionEvent ae) {
				String user = userType.getSelection().getActionCommand();

				// if user selects NEW
				// CUSTOMER--------------------------------------------------------------------------------------
				if (user.equals("New Customer")) {
					mainFrame.dispose();
					secondaryFrame = new JFrame("Create New Customer");
					secondaryFrame.setSize(400, 300);
					secondaryFrame.setLocation(200, 200);
					secondaryFrame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					Container content = secondaryFrame.getContentPane();
					content.setLayout(new BorderLayout());

					firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
					surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					JPanel panel = new JPanel(new GridLayout(6, 2));
					panel.add(firstNameLabel);
					panel.add(firstNameTextField);
					panel.add(surnameLabel);
					panel.add(surnameTextField);
					panel.add(pPPSLabel);
					panel.add(pPSTextField);
					panel.add(dOBLabel);
					panel.add(dOBTextField);

					JPanel panel2 = new JPanel();
					JButton add = new JButton("Add");

					add.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							String PPS = pPSTextField.getText();
							String firstName = firstNameTextField.getText();
							String surname = surnameTextField.getText();
							String DOB = dOBTextField.getText();
							password = "";

							String CustomerID = "ID" + PPS;

							add.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									secondaryFrame.dispose();

									boolean loop = true;
									while (loop) {
										password = JOptionPane.showInputDialog(mainFrame, "Enter 7 character Password;");

										if (password.length() != 7)// Making sure password is 7 characters
										{
											JOptionPane.showMessageDialog(null, null,
													"Password must be 7 charatcers long", JOptionPane.OK_OPTION);
										} else {
											loop = false;
										}
									}

									ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
									Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password,
											accounts);

									customerList.add(customer);

									JOptionPane.showMessageDialog(mainFrame,
											"CustomerID = " + CustomerID + "\n Password = " + password,
											"Customer created.", JOptionPane.INFORMATION_MESSAGE);
									menuStart();
									mainFrame.dispose();
								}
							});
						}
					});
					JButton cancel = new JButton("Cancel");
					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							secondaryFrame.dispose();
							menuStart();
						}
					});

					panel2.add(add);
					panel2.add(cancel);

					content.add(panel, BorderLayout.CENTER);
					content.add(panel2, BorderLayout.SOUTH);

					secondaryFrame.setVisible(true);

				}

				// ------------------------------------------------------------------------------------------------------------------

				// if user select
				// ADMIN----------------------------------------------------------------------------------------------
				if (user.equals("Administrator")) {
					boolean loop = true, loop2 = true;
					boolean cont = false;
					while (loop) {
						Object adminUsername = JOptionPane.showInputDialog(mainFrame, "Enter Administrator Username:");

						if (!adminUsername.equals("admin"))// search admin list for admin with matching admin username
						{
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								secondaryFrame.dispose();
								loop = false;
								loop2 = false;
								menuStart();
							}
						} else {
							loop = false;
						}
					}

					while (loop2) {
						Object adminPassword = JOptionPane.showInputDialog(mainFrame, "Enter Administrator Password;");

						if (!adminPassword.equals("admin11"))// search admin list for admin with matching admin password
						{
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {

							} else if (reply == JOptionPane.NO_OPTION) {
								secondaryFrame.dispose();
								loop2 = false;
								menuStart();
							}
						} else {
							loop2 = false;
							cont = true;
						}
					}

					if (cont) {
						mainFrame.dispose();
						loop = false;
						admin();
					}
				}
				// ----------------------------------------------------------------------------------------------------------------

				// if user selects CUSTOMER
				// ----------------------------------------------------------------------------------------
				if (user.equals("Customer")) {
					boolean loop = true, loop2 = true;
					boolean cont = false;
					boolean found = false;
					Customer customer = null;
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(mainFrame, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID))// search customer list for matching
																				// customer ID
							{
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								mainFrame.dispose();
								loop = false;
								loop2 = false;
								menuStart();
							}
						} else {
							loop = false;
						}

					}

					while (loop2) {
						Object customerPassword = JOptionPane.showInputDialog(mainFrame, "Enter Customer Password;");

						if (!customer.getPassword().equals(customerPassword))// check if custoemr password is correct
						{
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {

							} else if (reply == JOptionPane.NO_OPTION) {
								mainFrame.dispose();
								loop2 = false;
								menuStart();
							}
						} else {
							loop2 = false;
							cont = true;
						}
					}

					if (cont) {
						mainFrame.dispose();
						loop = false;
						customer(customer);
					}
				}
				// -----------------------------------------------------------------------------------------------------------------------
			}
		});
		mainFrame.setVisible(true);
	}

	public void admin() {
		adminMenu admin = new adminMenu(this);
	}
		

	public void customer(Customer e1) {
		customerMenu custMenu = new customerMenu(e1, this);
	}

	public static boolean isNumeric(String str) // a method that tests if a string is numeric
	{
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
