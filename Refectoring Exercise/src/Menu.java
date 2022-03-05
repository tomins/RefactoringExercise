
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
		mainFrame = new JFrame("Customer Menu");
		Customer e = e1;
		mainFrame.setSize(400, 300);
		mainFrame.setLocation(200, 200);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);

		if (e.getAccounts().size() == 0) {
			JOptionPane.showMessageDialog(mainFrame,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			mainFrame.dispose();
			menuStart();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			JButton returnButton = new JButton("Return");
			buttonPanel.add(returnButton);
			JButton continueButton = new JButton("Continue");
			buttonPanel.add(continueButton);

			JComboBox<String> box = new JComboBox<String>();
			for (int i = 0; i < e.getAccounts().size(); i++) {
				box.addItem(e.getAccounts().get(i).getNumber());
			}

			for (int i = 0; i < e.getAccounts().size(); i++) {
				if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
					acc = e.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			Container content = mainFrame.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					mainFrame.dispose();
					menuStart();
				}
			});

			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					mainFrame.dispose();

					mainFrame = new JFrame("Customer Menu");
					mainFrame.setSize(400, 300);
					mainFrame.setLocation(200, 200);
					mainFrame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					mainFrame.setVisible(true);

					JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton statementButton = new JButton("Display Bank Statement");
					statementButton.setPreferredSize(new Dimension(250, 20));

					statementPanel.add(statementButton);

					JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton lodgementButton = new JButton("Lodge money into account");
					lodgementPanel.add(lodgementButton);
					lodgementButton.setPreferredSize(new Dimension(250, 20));

					JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton withdrawButton = new JButton("Withdraw money from account");
					withdrawalPanel.add(withdrawButton);
					withdrawButton.setPreferredSize(new Dimension(250, 20));

					JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					JButton returnButton = new JButton("Exit Customer Menu");
					returnPanel.add(returnButton);

					JLabel label1 = new JLabel("Please select an option");

					Container content = mainFrame.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							mainFrame.dispose();
							mainFrame = new JFrame("Customer Menu");
							mainFrame.setSize(400, 600);
							mainFrame.setLocation(200, 200);
							mainFrame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							mainFrame.setVisible(true);

							JLabel label1 = new JLabel("Summary of account transactions: ");

							JPanel returnPanel = new JPanel();
							JButton returnButton = new JButton("Return");
							returnPanel.add(returnButton);

							JPanel textPanel = new JPanel();

							textPanel.setLayout(new BorderLayout());
							JTextArea textArea = new JTextArea(40, 20);
							textArea.setEditable(false);
							textPanel.add(label1, BorderLayout.NORTH);
							textPanel.add(textArea, BorderLayout.CENTER);
							textPanel.add(returnButton, BorderLayout.SOUTH);

							JScrollPane scrollPane = new JScrollPane(textArea);
							textPanel.add(scrollPane);

							for (int i = 0; i < acc.getTransactionList().size(); i++) {
								textArea.append(acc.getTransactionList().get(i).toString());

							}

							textPanel.add(textArea);
							content.removeAll();

							Container content = mainFrame.getContentPane();
							content.setLayout(new GridLayout(1, 1));
							// content.add(label1);
							content.add(textPanel);
							// content.add(returnPanel);

							returnButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									mainFrame.dispose();
									customer(e);
								}
							});
						}
					});

					lodgementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double balance = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(mainFrame,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(mainFrame, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(mainFrame, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(mainFrame,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);
										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(mainFrame, "Enter amount you wish to lodge:");// the
																														// isNumeric
																														// method
																														// tests
																														// to
																														// see
																														// if
																														// the
																														// string
																														// entered
																														// was
																														// numeric.
								if (isNumeric(balanceTest)) {

									balance = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(mainFrame, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}

								String euro = "\u20ac";
								acc.setBalance(acc.getBalance() + balance);
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();
								String type = "Lodgement";
								double amount = balance;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(mainFrame, balance + euro + " added do you account!", "Lodgement",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(mainFrame, "New balance = " + acc.getBalance() + euro,
										"Lodgement", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					withdrawButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double withdraw = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(mainFrame,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(mainFrame, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(mainFrame, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(mainFrame,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(mainFrame,
										"Enter amount you wish to withdraw (max 500):");// the isNumeric method tests to
																						// see if the string entered was
																						// numeric.
								if (isNumeric(balanceTest)) {

									withdraw = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(mainFrame, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(mainFrame, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
								if (withdraw > acc.getBalance()) {
									JOptionPane.showMessageDialog(mainFrame, "Insufficient funds.", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}

								String euro = "\u20ac";
								acc.setBalance(acc.getBalance() - withdraw);
								// recording transaction:
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();

								String type = "Withdraw";
								double amount = withdraw;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(mainFrame, withdraw + euro + " withdrawn.", "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(mainFrame, "New balance = " + acc.getBalance() + euro, "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					returnButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							mainFrame.dispose();
							menuStart();
						}
					});
				}
			});
		}
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
