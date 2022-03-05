import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class adminMenu extends JFrame {
	
	public adminMenu(Menu menu) {
		dispose();
		menu.mainFrame = new JFrame("Administrator Menu");
		menu.mainFrame.setSize(400, 400);
		menu.mainFrame.setLocation(200, 200);
		menu.mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		menu.mainFrame.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomerButton = new JButton("Delete Customer");
		deleteCustomerButton.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomerButton);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccountButton);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesButton = new JButton("Apply Bank Charges");
		bankChargesButton.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesButton);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestButton = new JButton("Apply Interest");
		interestPanel.add(interestButton);
		interestButton.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerButton = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerButton);
		editCustomerButton.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateButton = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateButton);
		navigateButton.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryButton = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryButton);
		summaryButton.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountButton = new JButton("Add an Account to a Customer");
		accountPanel.add(accountButton);
		accountButton.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton returnButton = new JButton("Exit Admin Menu");
		returnPanel.add(returnButton);

		JLabel label1 = new JLabel("Please select an option");

		Container content = menu.mainFrame.getContentPane();
		content.setLayout(new GridLayout(9, 1));
		content.add(label1);
		content.add(accountPanel);
		content.add(bankChargesPanel);
		content.add(interestPanel);
		content.add(editCustomerPanel);
		content.add(navigatePanel);
		content.add(summaryPanel);
		content.add(deleteCustomerPanel);
		// content.add(deleteAccountPanel);
		content.add(returnPanel);

		bankChargesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(menu.mainFrame, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					menu.mainFrame.dispose();
					menu.admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(menu.mainFrame,
								"Customer ID of Customer You Wish to Apply Charges to:");

						for (Customer aCustomer : menu.customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								menu.customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								menu.mainFrame.dispose();
								loop = false;

								menu.admin();
							}
						} else {
							menu.mainFrame.dispose();
							JFrame mainFrame = new JFrame("Administrator Menu");
							menu.mainFrame.setSize(400, 300);
							menu.mainFrame.setLocation(200, 200);
							menu.mainFrame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							menu.mainFrame.setVisible(true);

							JComboBox<String> comboBox = new JComboBox<String>();
							for (int i = 0; i < menu.customer.getAccounts().size(); i++) {

								comboBox.addItem(menu.customer.getAccounts().get(i).getNumber());
							}

							comboBox.getSelectedItem();

							JPanel boxPanel = new JPanel();
							boxPanel.add(comboBox);

							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Charge");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = menu.mainFrame.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (menu.customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(menu.mainFrame,
										"This menu.customer has no accounts! \n The admin must add acounts to this menu.customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								menu.mainFrame.dispose();
								menu.admin();
							} else {

								for (int i = 0; i < menu.customer.getAccounts().size(); i++) {
									if (menu.customer.getAccounts().get(i).getNumber() == comboBox.getSelectedItem()) {
										menu.acc = menu.customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";

										if (menu.acc instanceof CustomerDepositAccount) {

											JOptionPane.showMessageDialog(menu.mainFrame,
													"25" + euro + " deposit account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											menu.acc.setBalance(menu.acc.getBalance() - 25);
											JOptionPane.showMessageDialog(menu.mainFrame, "New balance = " + menu.acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										if (menu.acc instanceof CustomerCurrentAccount) {

											JOptionPane.showMessageDialog(menu.mainFrame,
													"15" + euro + " current account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											menu.acc.setBalance(menu.acc.getBalance() - 25);
											JOptionPane.showMessageDialog(menu.mainFrame, "New balance = " + menu.acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										menu.mainFrame.dispose();
										menu.admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										menu.mainFrame.dispose();
										menu.menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(menu.mainFrame, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					menu.mainFrame.dispose();
					menu.admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(menu.mainFrame,
								"Customer ID of Customer You Wish to Apply Interest to:");

						for (Customer aCustomer : menu.customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								menu.customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								menu.mainFrame.dispose();
								loop = false;

								menu.admin();
							}
						} else {
							menu.mainFrame.dispose();
							menu.mainFrame = new JFrame("Administrator Menu");
							menu.mainFrame.setSize(400, 300);
							menu.mainFrame.setLocation(200, 200);
							menu.mainFrame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							menu.mainFrame.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < menu.customer.getAccounts().size(); i++) {

								box.addItem(menu.customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();

							JLabel label = new JLabel("Select an account to apply interest to:");
							boxPanel.add(label);
							boxPanel.add(box);
							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Interest");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = menu.mainFrame.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (menu.customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(menu.mainFrame,
										"This menu.customer has no accounts! \n The admin must add acounts to this menu.customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								menu.mainFrame.dispose();
								menu.admin();
							} else {

								for (int i = 0; i < menu.customer.getAccounts().size(); i++) {
									if (menu.customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										menu.acc = menu.customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";
										double interest = 0;
										boolean loop = true;

										while (loop) {
											String interestString = JOptionPane.showInputDialog(menu.mainFrame,
													"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");// the
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
											if (Menu.isNumeric(interestString)) {

												interest = Double.parseDouble(interestString);
												loop = false;

												menu.acc.setBalance(
														menu.acc.getBalance() + (menu.acc.getBalance() * (interest / 100)));

												JOptionPane.showMessageDialog(menu.mainFrame,
														interest + "% interest applied. \n new balance = "
																+ menu.acc.getBalance() + euro,
														"Success!", JOptionPane.INFORMATION_MESSAGE);
											}

											else {
												JOptionPane.showMessageDialog(menu.mainFrame, "You must enter a numerical value!",
														"Oops!", JOptionPane.INFORMATION_MESSAGE);
											}

										}

										menu.mainFrame.dispose();
										menu.admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										menu.mainFrame.dispose();
										menu.menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(menu.mainFrame, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					menu.mainFrame.dispose();
					menu.admin();

				} else {

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(menu.mainFrame, "Enter Customer ID:");

						for (Customer aCustomer : menu.customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								menu.customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								menu.mainFrame.dispose();
								loop = false;

								menu.admin();
							}
						} else {
							loop = false;
						}

					}

					menu.mainFrame.dispose();

					menu.mainFrame.dispose();
					menu.mainFrame = new JFrame("Administrator Menu");
					menu.mainFrame.setSize(400, 300);
					menu.mainFrame.setLocation(200, 200);
					menu.mainFrame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});

					JLabel firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					JLabel surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					JLabel pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					JLabel dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					JLabel customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					JLabel passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					JTextField firstNameTextField = new JTextField(20);
					JTextField surnameTextField = new JTextField(20);
					JTextField pPSTextField = new JTextField(20);
					JTextField dOBTextField = new JTextField(20);
					JTextField customerIDTextField = new JTextField(20);
					JTextField passwordTextField = new JTextField(20);

					JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

					JPanel cancelPanel = new JPanel();

					textPanel.add(firstNameLabel);
					textPanel.add(firstNameTextField);
					textPanel.add(surnameLabel);
					textPanel.add(surnameTextField);
					textPanel.add(pPPSLabel);
					textPanel.add(pPSTextField);
					textPanel.add(dOBLabel);
					textPanel.add(dOBTextField);
					textPanel.add(customerIDLabel);
					textPanel.add(customerIDTextField);
					textPanel.add(passwordLabel);
					textPanel.add(passwordTextField);

					firstNameTextField.setText(menu.customer.getFirstName());
					surnameTextField.setText(menu.customer.getSurname());
					pPSTextField.setText(menu.customer.getPPS());
					dOBTextField.setText(menu.customer.getDOB());
					customerIDTextField.setText(menu.customer.getCustomerID());
					passwordTextField.setText(menu.customer.getPassword());

					// JLabel label1 = new JLabel("Edit menu.customer details below. The save");

					JButton saveButton = new JButton("Save");
					JButton cancelButton = new JButton("Exit");

					cancelPanel.add(cancelButton, BorderLayout.SOUTH);
					cancelPanel.add(saveButton, BorderLayout.SOUTH);
					// content.removeAll();
					Container content = menu.mainFrame.getContentPane();
					content.setLayout(new GridLayout(2, 1));
					content.add(textPanel, BorderLayout.NORTH);
					content.add(cancelPanel, BorderLayout.SOUTH);

					menu.mainFrame.setContentPane(content);
					menu.mainFrame.setSize(340, 350);
					menu.mainFrame.setLocation(200, 200);
					menu.mainFrame.setVisible(true);
					menu.mainFrame.setResizable(false);

					saveButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							menu.customer.setFirstName(firstNameTextField.getText());
							menu.customer.setSurname(surnameTextField.getText());
							menu.customer.setPPS(pPSTextField.getText());
							menu.customer.setDOB(dOBTextField.getText());
							menu.customer.setCustomerID(customerIDTextField.getText());
							menu.customer.setPassword(passwordTextField.getText());

							JOptionPane.showMessageDialog(null, "Changes Saved.");
						}
					});

					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							menu.mainFrame.dispose();

							menu.admin();
						}
					});
				}
			}
		});

		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				menu.mainFrame.dispose();

				menu.mainFrame = new JFrame("Summary of Transactions");
				menu.mainFrame.setSize(400, 700);
				menu.mainFrame.setLocation(200, 200);
				menu.mainFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});
				menu.mainFrame.setVisible(true);

				JLabel label1 = new JLabel("Summary of all transactions: ");

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

				for (int a = 0; a < menu.customerList.size(); a++)// For each menu.customer, for each account, it displays each
																// transaction.
				{
					for (int b = 0; b < menu.customerList.get(a).getAccounts().size(); b++) {
						menu.acc = menu.customerList.get(a).getAccounts().get(b);
						for (int c = 0; c < menu.customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

							textArea.append(menu.acc.getTransactionList().get(c).toString());
							// Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use
							// this to keep a running total but I couldnt get it working.

						}
					}
				}

				textPanel.add(textArea);
				content.removeAll();

				Container content = menu.mainFrame.getContentPane();
				content.setLayout(new GridLayout(1, 1));
				// content.add(label1);
				content.add(textPanel);
				// content.add(returnPanel);

				returnButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						menu.mainFrame.dispose();
						menu.admin();
					}
				});
			}
		});

		navigateButton.addActionListener(new ActionListener() {
			private int position = 0;
			public void actionPerformed(ActionEvent ae) {
				menu.mainFrame.dispose();

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					menu.admin();
				} else {

					JButton first, previous, next, last, cancel;
					JPanel gridPanel, buttonPanel, cancelPanel;

					Container content = getContentPane();

					content.setLayout(new BorderLayout());

					buttonPanel = new JPanel();
					gridPanel = new JPanel(new GridLayout(8, 2));
					cancelPanel = new JPanel();

					JLabel firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					JLabel surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					JLabel pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					JLabel dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					JLabel customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					JLabel passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					JTextField firstNameTextField = new JTextField(20);
					JTextField surnameTextField = new JTextField(20);
					JTextField pPSTextField = new JTextField(20);
					JTextField dOBTextField = new JTextField(20);
					JTextField customerIDTextField = new JTextField(20);
					JTextField passwordTextField = new JTextField(20);

					first = new JButton("First");
					previous = new JButton("Previous");
					next = new JButton("Next");
					last = new JButton("Last");
					cancel = new JButton("Cancel");

					firstNameTextField.setText(menu.customerList.get(0).getFirstName());
					surnameTextField.setText(menu.customerList.get(0).getSurname());
					pPSTextField.setText(menu.customerList.get(0).getPPS());
					dOBTextField.setText(menu.customerList.get(0).getDOB());
					customerIDTextField.setText(menu.customerList.get(0).getCustomerID());
					passwordTextField.setText(menu.customerList.get(0).getPassword());

					firstNameTextField.setEditable(false);
					surnameTextField.setEditable(false);
					pPSTextField.setEditable(false);
					dOBTextField.setEditable(false);
					customerIDTextField.setEditable(false);
					passwordTextField.setEditable(false);

					gridPanel.add(firstNameLabel);
					gridPanel.add(firstNameTextField);
					gridPanel.add(surnameLabel);
					gridPanel.add(surnameTextField);
					gridPanel.add(pPPSLabel);
					gridPanel.add(pPSTextField);
					gridPanel.add(dOBLabel);
					gridPanel.add(dOBTextField);
					gridPanel.add(customerIDLabel);
					gridPanel.add(customerIDTextField);
					gridPanel.add(passwordLabel);
					gridPanel.add(passwordTextField);

					buttonPanel.add(first);
					buttonPanel.add(previous);
					buttonPanel.add(next);
					buttonPanel.add(last);

					cancelPanel.add(cancel);

					content.add(gridPanel, BorderLayout.NORTH);
					content.add(buttonPanel, BorderLayout.CENTER);
					content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
					first.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							position = 0;
							firstNameTextField.setText(menu.customerList.get(0).getFirstName());
							surnameTextField.setText(menu.customerList.get(0).getSurname());
							pPSTextField.setText(menu.customerList.get(0).getPPS());
							dOBTextField.setText(menu.customerList.get(0).getDOB());
							customerIDTextField.setText(menu.customerList.get(0).getCustomerID());
							passwordTextField.setText(menu.customerList.get(0).getPassword());
						}
					});

					previous.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position < 1) {
								// don't do anything
							} else {
								position = position - 1;

								firstNameTextField.setText(menu.customerList.get(position).getFirstName());
								surnameTextField.setText(menu.customerList.get(position).getSurname());
								pPSTextField.setText(menu.customerList.get(position).getPPS());
								dOBTextField.setText(menu.customerList.get(position).getDOB());
								customerIDTextField.setText(menu.customerList.get(position).getCustomerID());
								passwordTextField.setText(menu.customerList.get(position).getPassword());
							}
						}
					});

					next.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position == menu.customerList.size() - 1) {
								// don't do anything
							} else {
								position = position + 1;

								firstNameTextField.setText(menu.customerList.get(position).getFirstName());
								surnameTextField.setText(menu.customerList.get(position).getSurname());
								pPSTextField.setText(menu.customerList.get(position).getPPS());
								dOBTextField.setText(menu.customerList.get(position).getDOB());
								customerIDTextField.setText(menu.customerList.get(position).getCustomerID());
								passwordTextField.setText(menu.customerList.get(position).getPassword());
							}

						}
					});

					last.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							position = menu.customerList.size() - 1;

							firstNameTextField.setText(menu.customerList.get(position).getFirstName());
							surnameTextField.setText(menu.customerList.get(position).getSurname());
							pPSTextField.setText(menu.customerList.get(position).getPPS());
							dOBTextField.setText(menu.customerList.get(position).getDOB());
							customerIDTextField.setText(menu.customerList.get(position).getCustomerID());
							passwordTextField.setText(menu.customerList.get(position).getPassword());
						}
					});

					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							dispose();
							menu.admin();
						}
					});
					setContentPane(content);
					setSize(400, 300);
					setVisible(true);
				}
			}
		});

		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				menu.mainFrame.dispose();

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(menu.mainFrame, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					menu.mainFrame.dispose();
					menu.admin();
				} else {
					boolean loop = true;

					boolean found = false;

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(menu.mainFrame,
								"Customer ID of Customer You Wish to Add an Account to:");

						for (Customer aCustomer : menu.customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								menu.customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								menu.mainFrame.dispose();
								loop = false;

								menu.admin();
							}
						} else {
							loop = false;
							// a combo box in an dialog box that asks the admin what type of account they
							// wish to create (deposit/current)
							String[] choices = { "Current Account", "Deposit Account" };
							String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
									"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

							if (account.equals("Current Account")) {
								// create current account
								boolean valid = true;
								double balance = 0;
								String number = String.valueOf("C" + (menu.customerList.indexOf(menu.customer) + 1) * 10
										+ (menu.customer.getAccounts().size() + 1));// this simple algorithm generates the
																				// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
								int randomPIN = (int) (Math.random() * 9000) + 1000;
								String pin = String.valueOf(randomPIN);

								ATMCard atm = new ATMCard(randomPIN, valid);

								CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
										transactionList);

								menu.customer.getAccounts().add(current);
								JOptionPane.showMessageDialog(menu.mainFrame, "Account number = " + number + "\n PIN = " + pin,
										"Account created.", JOptionPane.INFORMATION_MESSAGE);

								menu.mainFrame.dispose();
								menu.admin();
							}

							if (account.equals("Deposit Account")) {
								// create deposit account

								double balance = 0, interest = 0;
								String number = String.valueOf("D" + (menu.customerList.indexOf(menu.customer) + 1) * 10
										+ (menu.customer.getAccounts().size() + 1));// this simple algorithm generates the
																				// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

								CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
										transactionList);

								menu.customer.getAccounts().add(deposit);
								JOptionPane.showMessageDialog(menu.mainFrame, "Account number = " + number, "Account created.",
										JOptionPane.INFORMATION_MESSAGE);

								menu.mainFrame.dispose();
								menu.admin();
							}

						}
					}
				}
			}
		});

		deleteCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				if (menu.customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					dispose();
					menu.admin();
				} else {
					{
						Object customerID = JOptionPane.showInputDialog(menu.mainFrame,
								"Customer ID of Customer You Wish to Delete:");

						for (Customer aCustomer : menu.customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								menu.customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								menu.mainFrame.dispose();
								loop = false;

								menu.admin();
							}
						} else {
							if (menu.customer.getAccounts().size() > 0) {
								JOptionPane.showMessageDialog(menu.mainFrame,
										"This menu.customer has accounts. \n You must delete a menu.customer's accounts before deleting a menu.customer ",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								menu.customerList.remove(menu.customer);
								JOptionPane.showMessageDialog(menu.mainFrame, "Customer Deleted ", "Success.",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}
				}
			}
		});

		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				{
					Object customerID = JOptionPane.showInputDialog(menu.mainFrame,
							"Customer ID of Customer from which you wish to delete an account");

					for (Customer aCustomer : menu.customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							menu.customer = aCustomer;
							loop = false;
						}
					}

					if (found == false) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							menu.mainFrame.dispose();
							loop = false;

							menu.admin();
						}
					} else {
						// Here I would make the user select a an account to delete from a combo box. If
						// the account had a balance of 0 then it would be deleted. (I do not have time
						// to do this)
					}

				}
			}

		});
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
				menu.menuStart();
			}
		});
	}
}

