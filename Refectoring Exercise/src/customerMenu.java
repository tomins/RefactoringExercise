import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class customerMenu {
	public customerMenu(Customer e1, Menu menu) {
		menu.mainFrame = new JFrame("Customer Menu");
		Customer e = e1;
		menu.mainFrame.setSize(400, 300);
		menu.mainFrame.setLocation(200, 200);
		menu.mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		menu.mainFrame.setVisible(true);

		if (e.getAccounts().size() == 0) {
			JOptionPane.showMessageDialog(menu.mainFrame,
					"This menu.customer does not have any accounts yet. \n An admin must create an account for this menu.customer \n for them to be able to use menu.customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			menu.mainFrame.dispose();
			menu.menuStart();
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
					menu.acc = e.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			Container content = menu.mainFrame.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					menu.mainFrame.dispose();
					menu.menuStart();
				}
			});

			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					menu.mainFrame.dispose();

					menu.mainFrame = new JFrame("Customer Menu");
					menu.mainFrame.setSize(400, 300);
					menu.mainFrame.setLocation(200, 200);
					menu.mainFrame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					menu.mainFrame.setVisible(true);

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

					Container content = menu.mainFrame.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							menu.mainFrame.dispose();
							menu.mainFrame = new JFrame("Customer Menu");
							menu.mainFrame.setSize(400, 600);
							menu.mainFrame.setLocation(200, 200);
							menu.mainFrame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							menu.mainFrame.setVisible(true);

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

							for (int i = 0; i < menu.acc.getTransactionList().size(); i++) {
								textArea.append(menu.acc.getTransactionList().get(i).toString());

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
									menu.customer(e);
								}
							});
						}
					});

					lodgementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double balance = 0;

							if (menu.acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) menu.acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(menu.mainFrame,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) menu.acc).getAtm().setValid(false);
										menu.customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(menu.mainFrame, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(menu.mainFrame, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(menu.mainFrame,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);
										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(menu.mainFrame, "Enter amount you wish to lodge:");// the
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
								if (Menu.isNumeric(balanceTest)) {

									balance = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(menu.mainFrame, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}

								String euro = "\u20ac";
								menu.acc.setBalance(menu.acc.getBalance() + balance);
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();
								String type = "Lodgement";
								double amount = balance;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								menu.acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(menu.mainFrame, balance + euro + " added do you account!", "Lodgement",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(menu.mainFrame, "New balance = " + menu.acc.getBalance() + euro,
										"Lodgement", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					withdrawButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double withdraw = 0;

							if (menu.acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) menu.acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(menu.mainFrame,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) menu.acc).getAtm().setValid(false);
										menu.customer(e);
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(menu.mainFrame, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(menu.mainFrame, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(menu.mainFrame,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(menu.mainFrame,
										"Enter amount you wish to withdraw (max 500):");// the isNumeric method tests to
																						// see if the string entered was
																						// numeric.
								if (Menu.isNumeric(balanceTest)) {

									withdraw = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(menu.mainFrame, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(menu.mainFrame, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
								if (withdraw > menu.acc.getBalance()) {
									JOptionPane.showMessageDialog(menu.mainFrame, "Insufficient funds.", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}

								String euro = "\u20ac";
								menu.acc.setBalance(menu.acc.getBalance() - withdraw);
								// recording transaction:
								// String date = new
								// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
								Date date = new Date();
								String date2 = date.toString();

								String type = "Withdraw";
								double amount = withdraw;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								menu.acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(menu.mainFrame, withdraw + euro + " withdrawn.", "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(menu.mainFrame, "New balance = " + menu.acc.getBalance() + euro, "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					returnButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							menu.mainFrame.dispose();
							menu.menuStart();
						}
					});
				}
			});
		}
	}
}
