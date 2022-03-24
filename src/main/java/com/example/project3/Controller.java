package com.example.project3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class Controller {

	AccountDatabase database = new AccountDatabase();
	private static final DecimalFormat df = new DecimalFormat("#,##0.00");

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
				case "Savings" -> {
					acc = new Savings(accHolder, balance, loyalty.isSelected() ? 1 : 0);
				}
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

		output.textProperty().addListener((observable, oldValue, newValue) -> {
			// this will run whenever text is changed
		});
	}

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
			if (!database.close(acc)) {
				output.appendText("Account is closed already.\n");
			}
			else output.appendText("Account closed.\n");
		}
		catch (NullPointerException e) {
			output.appendText("Missing data for closing an account.\n");
		}
	}

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

	@FXML
	void checking(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(true);
	}

	@FXML
	void collegeChecking(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(false);
		});
		loyalty.setDisable(true);
	}

	@FXML
	void savings(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(false);
	}

	@FXML
	void moneyMarket(ActionEvent event) {
		campus.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true);
		});
		loyalty.setDisable(true);
	}

	@FXML
	void showAll(ActionEvent event) {
		Account [] accs = database.getAccounts();
		if (!database.print()) {
			output.appendText("Account Database is empty!\n");
			return;
		}
		output.appendText("*list of accounts in the database*\n");
		for (Account account : accs) {
			if (account != null)
				output.appendText(account + "\n");
		}
		output.appendText("*end of list*\n");
	}

	@FXML
	void showByTypeButton(ActionEvent event) {
		Account [] accs = database.getAccounts();
		if (!database.printByAccountType()) {
			output.appendText("Account Database is empty!\n");
			return;
		}
		output.appendText("*list of accounts in the database*\n");
		for (Account account : accs) {
			if (account != null)
				output.appendText(account + "\n");
		}
		output.appendText("*end of list*\n");
	}

	@FXML
	void showInterest(ActionEvent event) {
		Account [] accs = database.getAccounts();
		if (!database.printFeeAndInterest()) {
			output.appendText("Account Database is empty!\n");
			return;
		}

		output.appendText("*list of accounts with fee and monthly interest*\n");
		for (Account account : accs) {
			System.out.println(account + "::fee $" + df.format(account.fee())
					+ "::monthly interest $"
					+ df.format(account.balance * account.monthlyInterest()));
		}
		System.out.println("*end of list.\n");
	}

	@FXML
	void updateAccounts(ActionEvent event) {
		Account [] accs = database.getAccounts();

		if (!database.update()) {
			output.appendText("Account Database is empty!\n");
			return;
		}
		output.appendText("*list of accounts with updated balance*\n");
		for (Account account : accs) {
			account.deposit(account.balance * account.monthlyInterest());
			account.withdraw(account.fee());
			output.appendText(account + "\n");
		}
		output.appendText("*end of list.\n");
	}

	private boolean dateChecker(Date dob) {
		Date current = new Date();

		if (!dob.isValid() || dob.compareTo(current) >= 0) {
			output.appendText("Date of birth invalid.\n");
			return true;

		}
		return false;
	}
}

