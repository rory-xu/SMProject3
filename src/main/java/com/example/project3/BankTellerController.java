package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.time.format.DateTimeFormatter;

/**
 * This class controls the application and determines what is executed when actions are made
 * @author Rory Xu, Hassan Alfareed
 */
public class BankTellerController {

	AccountDatabase database = new AccountDatabase();

	@FXML
	private ToggleGroup accountType;

	@FXML
	private ToggleGroup accountTypeBalance;

	@FXML
	private TextField amount;

	@FXML
	private TextField amountBalance;

	@FXML
	private RadioButton camden;

	@FXML
	private ToggleGroup campus;

	@FXML
	private RadioButton checkingButton;

	@FXML
	private RadioButton checkingBalanceButton;

	@FXML
	private Button closeButton;

	@FXML
	private RadioButton collegeCheckingButton;

	@FXML
	private RadioButton collegeCheckingBalanceButton;

	@FXML
	private Button depositButton;

	@FXML
	private DatePicker dateOfBirth;

	@FXML
	private DatePicker dateOfBirthBalance;

	@FXML
	private TextField firstName;

	@FXML
	private TextField firstNameBalance;

	@FXML
	private TextField lastName;

	@FXML
	private TextField lastNameBalance;

	@FXML
	private CheckBox loyalty;

	@FXML
	private RadioButton moneyMarketButton;

	@FXML
	private RadioButton moneyMarketBalanceButton;

	@FXML
	private RadioButton nb;

	@FXML
	private RadioButton newark;

	@FXML
	private Button openButton;

	@FXML
	private TextArea output;


	@FXML
	private RadioButton savingsButton;

	@FXML
	private RadioButton savingsBalanceButton;

	@FXML
	private Button showAllButton;

	@FXML
	private Button showByTypeButton;

	@FXML
	private Button showInterestButton;

	@FXML
	private Button updateAccountsButton;

	@FXML
	private Button withdrawButton;

	/**
	 * Opens an account with specific parameters
	 * @param event On open button click
	 */
	@FXML
	void openAccount(ActionEvent event) {
		try{
			String accType = ((RadioButton) accountType.getSelectedToggle()).getText();
			assert dateOfBirth.getValue() != null;
			Date dob = new Date(dateOfBirth.getValue().format(DateTimeFormatter.ofPattern(("MM/dd/yyyy"))));
			if (dateChecker(dob)) return;
			if (firstName.getText().isBlank() || lastName.getText().isBlank()) {
				output.appendText("Missing data for opening an account.\n");
				return;
			}
			Profile accHolder = new Profile(firstName.getText(), lastName.getText(), dob);
			double balance = Double.parseDouble(amount.getText());
			if (balance <= 0) {
				output.appendText("Initial deposit cannot be 0 or negative.\n");
				return;
			}
			Account acc = null;
			switch (accType) {
				case "Money Market" -> {
					if (balance < 2500) {
						output.appendText("Minimum of $2500 to open a MoneyMarket account.\n");
						return;
					}
					acc = new MoneyMarket(accHolder, balance, 1);
				}
				case "Checking" -> {
					acc = new Checking(accHolder, balance);
					if (database.findChecking(acc) != -1) {
						output.appendText(acc.holder + " same account(type) is in the database.\n");
						return;
					}
				}
				case "College Checking" -> {
					int campusCode = 0;
					if (!((RadioButton) campus.getSelectedToggle()).getText().equals("New Brunswick")) {
						campusCode = ((RadioButton) campus.getSelectedToggle()).getText().equals("Newark") ? 1 : 2;
					}
					acc = new CollegeChecking(accHolder, balance, campusCode);
					if (database.findChecking(acc) != -1) {
						output.appendText(acc.holder + " same account(type) is in the database.\n");
						return;
					}
				}
				case "Savings" -> acc = new Savings(accHolder, balance, loyalty.isSelected() ? 1 : 0);
			}
			assert acc != null;

			int index = database.findAccount(acc);
			if (index == -1 && database.open(acc)) {
				output.appendText("Account opened.\n");
			}
			else {
				if (database.isAccountClosed(acc)) {
					database.open(acc);
					output.appendText("Account reopened.\n");
				}
				else output.appendText(acc.holder + " same account(type) is in the database.\n");
			}
		}
		catch (NumberFormatException e) {
			output.appendText("Not a valid amount.\n");
		}
		catch (NullPointerException e) {
			output.appendText("Missing data for opening an account.\n");
		}
	}

	/**
	 * Closes the account with specific parameters
	 * @param event On close button click
	 */
	@FXML
	void closeAccount(ActionEvent event) {
		try {
			String accType = ((RadioButton) accountType.getSelectedToggle()).getText();
			assert dateOfBirth.getValue() != null;
			Date dob = new Date(dateOfBirth.getValue().format(DateTimeFormatter.ofPattern(("MM/dd/yyyy"))));
			if (dateChecker(dob)) return;
			if (firstName.getText().isBlank() || lastName.getText().isBlank()) {
				output.appendText("Missing data for closing an account.\n");
				return;
			}
			Profile accHolder = new Profile(firstName.getText(), lastName.getText(), dob);

			Account acc = switch (accType) {
				case "Money Market" -> new MoneyMarket(accHolder, 0, 0);
				case "Checking" -> new Checking(accHolder, 0);
				case "College Checking" -> new CollegeChecking(accHolder, 0, 0);
				case "Savings" -> new Savings(accHolder, 0, 0);
				default -> null;
			};
			if (database.findAccount(acc) == -1) {
				output.appendText("Cannot close an account that doesn't exist.\n");
			}
			else {
				if (!database.close(acc)) output.appendText("Account is closed already.\n");
				else output.appendText("Account closed.\n");
			}
		}
		catch (NullPointerException e) {
			output.appendText("Missing data for closing an account.\n");
		}
	}

	/**
	 * Deposits a sum of money into the specified account
	 * @param event On deposit button click
	 */
	@FXML
	void depositBalance(ActionEvent event) {
		try {
			String accType = ((RadioButton) accountTypeBalance.getSelectedToggle()).getText();
			assert dateOfBirthBalance.getValue() != null;
			Date dob = new Date(dateOfBirthBalance.getValue().format(DateTimeFormatter.ofPattern(("MM/dd/yyyy"))));
			if (dateChecker(dob)) return;
			if (firstNameBalance.getText().isBlank() || lastNameBalance.getText().isBlank()) {
				output.appendText("Missing data for deposit.\n");
				return;
			}
			Profile accHolder = new Profile(firstNameBalance.getText(), lastNameBalance.getText(), dob);
			double balance = Double.parseDouble(amountBalance.getText());
			if (balance <= 0) {
				output.appendText("Deposit - amount cannot be 0 or negative.\n");
				return;
			}

			Account acc = switch (accType) {
				case "Money Market" -> new MoneyMarket(accHolder, balance, 0);
				case "Checking" -> new Checking(accHolder, balance);
				case "College Checking" -> new CollegeChecking(accHolder, balance, 0);
				case "Savings" -> new Savings(accHolder, balance, 0);
				default -> null;
			};

			if (database.findAccount(acc) == -1) {
				assert acc != null;
				output.appendText(acc.holder + " " + acc.getType() + " is not in the database.\n");
				return;
			}
			assert acc != null;
			database.deposit(acc);
			output.appendText("Deposit - balance updated.\n");
		}
		catch(NumberFormatException e) {
			output.appendText("Not a valid amount.");
		}
	}

	/**
	 * Withdraws a sum of money from a specified account
	 * @param event On withdraw button click
	 */
	@FXML
	void withdrawBalance(ActionEvent event) {
		try {
			String accType = ((RadioButton) accountTypeBalance.getSelectedToggle()).getText();
			assert dateOfBirthBalance.getValue() != null;
			Date dob = new Date(dateOfBirthBalance.getValue().format(DateTimeFormatter.ofPattern(("MM/dd/yyyy"))));
			if (dateChecker(dob)) return;
			if (firstNameBalance.getText().isBlank() || lastNameBalance.getText().isBlank()) {
				output.appendText("Missing data for deposit.\n");
				return;
			}
			Profile accHolder = new Profile(firstNameBalance.getText(), lastNameBalance.getText(), dob);
			double balance = Double.parseDouble(amountBalance.getText());

			if (balance <= 0) {
				output.appendText("Withdraw - amount cannot be 0 or negative.\n");
				return;
			}
			Account acc = switch (accType) {
				case "Money Market" -> new MoneyMarket(accHolder, balance, 0);
				case "Checking" -> new Checking(accHolder, balance);
				case "College Checking" -> new CollegeChecking(accHolder, balance, 0);
				case "Savings" -> new Savings(accHolder, balance, 0);
				default -> null;
			};
			assert acc != null;
			if (database.findAccount(acc) == -1) {
				output.appendText(acc.holder + " " + acc.getType() + " is not in the database.\n");
			}
			else {
				output.appendText(database.withdraw(acc) ? "Withdraw - balance updated.\n" : "Withdraw - insufficient funds.\n");
			}

		}
		catch (NumberFormatException e) {
			output.appendText("Not a valid amount.\n");
		}
	}

	/**
	 * Disables campus and loyal customer options
	 * @param event On checking radio button selection
	 */
	@FXML
	void checking(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(true);
	}

	/**
	 * Enables campus and disables loyal customer options
	 * @param event On college checking radio button selection
	 */
	@FXML
	void collegeChecking(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(false);
		});
		loyalty.setDisable(true);
	}

	/**
	 * Enables loyal customer and disables campus options
	 * @param event On savings radio button selection
	 */
	@FXML
	void savings(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(false);
	}

	/**
	 * Disables campus and loyal customer options
	 * @param event
	 */
	@FXML
	void moneyMarket(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(true);
	}

	/**
	 * Outputs the list of all accounts in the database
	 * @param event On show all accounts button click
	 */
	@FXML
	void showAll(ActionEvent event) {
		output.appendText(database.print());
	}

	/**
	 * Outputs the sorted list of all accounts in the database by type
	 * @param event On sort all accounts by type button click
	 */
	@FXML
	void showByTypeButton(ActionEvent event) {
		output.appendText(database.printByAccountType());
	}

	/**
	 * Outputs the list of all accounts in the database with their interest and fees
	 * @param event On show all accounts interest and fees button click
	 */
	@FXML
	void showInterest(ActionEvent event) {
		output.appendText(database.printFeeAndInterest());
	}

	/**
	 * Updates and outputs the list of all accounts in the database after applying interest and fees
	 * @param event On update account balances after interest and fees button click
	 */
	@FXML
	void updateAccounts(ActionEvent event) {
		output.appendText(database.update());
	}

	/**
	 * Checks to see whether or not the date entered is a valid birth date
	 * @param dob A Date object
	 * @return True if the date is valid, false if not
	 */
	private boolean dateChecker(Date dob) {
		Date current = new Date();

		if (!dob.isValid() || dob.compareTo(current) >= 0) {
			output.appendText("Date of birth invalid.\n");
			return true;

		}
		return false;
	}
}

