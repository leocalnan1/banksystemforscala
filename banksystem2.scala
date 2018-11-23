//Leo Calnan, QA Trainee

import java.sql.{Connection,DriverManager}
import scala.io.StdIn.{readInt, readLine}

object bank_of_leo {
	Class.forName("com.mysql.cj.jdbc.Driver")
	val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "")
	val statement = connection.createStatement
}

class bank_of_leo {
	def personaldetails(name:String, address:String) = {
		var insertquery1 = s"Insert into personaldetails values(null, '%s','%s')".format(name,address)
		(bank_of_leo.statement).executeUpdate(insertquery1)	
	}

	def deposit(accno:String, amount:Int) = {
		var insertquery2 = s"Insert into deposit values('%s', %d,now())".format(accno, amount)
		(bank_of_leo.statement).executeUpdate(insertquery2)
	}

	def withdraw(accno:String, amount:String) = {
		var insertquery3 = s"Insert into withdrawals values('%s','%s',now())".format(accno, amount)
		(bank_of_leo.statement).executeUpdate(insertquery3)
	}

	def check_balance(accno:String):Int = {
		println("Checking balance.....")
		println('\n' + "---------------------------------------------------------------------------------")

		var deposits_amount = 0
		var withdrawals_amount = 0

		var insertquery4 = s"SELECT SUM(AMOUNT) AS money FROM deposit WHERE accno = '%s'".format(accno)
		var info4 = (bank_of_leo.statement).executeQuery(insertquery4)
		if(info4.next()) {
			deposits_amount = info4.getInt("money")
		}

		var insertquery5 = s"SELECT SUM(AMOUNT) AS money FROM withdrawals WHERE accno ='%s'".format(accno)
		var info5 = (bank_of_leo.statement).executeQuery(insertquery5)
		if(info5.next()) {
			withdrawals_amount = info5.getInt("money")
		}

		var balance = deposits_amount - withdrawals_amount
	return balance
	}

	def user_check(accno:String):Boolean = {
		var Q="SELECT * FROM personaldetails WHERE AccNo= " + accno
		var record = bank_of_leo.statement.executeQuery(Q)
		if(record.next()) {
			println('\n' + "<" + '\n' + "Account holder name: " + record.getString("name"))
			println("Account holder address: " + record.getString("address") + '\n' + ">" + '\n')
		return true
		}
		else {
			println('\n' + "Account number not recognised" + '\n')
		return false
		}
	}

	def create_account = {
		println('\n' + "Create Account")
		print('\n' + "Enter Your Name: ")

		var name = readLine

		print('\n' + "Enter Your Address: ")

		var address = readLine

		personaldetails(name, address)
		println('\n' + "Your name and address have been added to the system database")
		println('\n' + "---------------------------------------------------------------------------------")
	}

	def deposit_money = {
		println('\n' + "Deposit")
		println('\n' + "---------------------------------------------------------------------------------")
		print('\n' + "Enter account number: ")

		var ac = readLine
		if(user_check(ac) == true) {
			println('\n' + "Your Current Balance is: £" + check_balance(ac))

			print('\n' + "Enter amount to deposit: £")
			var dep = readInt
			deposit(ac, dep)

			println('\n' + "Depositing money.......")
			println('\n' + "Your deposit of £" + dep + " has been added to the account number of " + ac + '\n')
			println('\n' + "Your new Balance is: £" + check_balance(ac))
			println('\n' + "---------------------------------------------------------------------------------")
		}
	}

	def withdraw_money = {
		println('\n' + "Withdraw")
		println('\n' + "---------------------------------------------------------------------------------")
		print('\n' + "Enter account number: ")

		var ac = readLine
		if(user_check(ac) == true) {
			println('\n' + "Your Current Balance is: £" + check_balance(ac))

			print('\n' + "Enter amount to withdraw: £")
			var wit = readLine
			withdraw(ac, wit)

			println('\n' + "Withdrawing money.......")
			println('\n' + "Your withdrawal of £" + wit + " has been made from the account number of " + ac)
			println('\n' + "Your new Balance is: £" + check_balance(ac))
			println('\n' + "---------------------------------------------------------------------------------")
		}
	}
}

var bank_of_leo_ref_var = new bank_of_leo()

try {
	var x = 0	
	while(x == 0) {
		println('\n' + "Choose from options 1, 2, 3 and 4.")
		println("1) Create Account" + '\n' + "2) Deposit Money" + '\n' + "3) Withdraw Money" + '\n' + "4) Check Balance" + '\n')
		print("Please enter a number (1/2/3/4): ")
		var choice = readInt

		if(choice == 1) {
			bank_of_leo_ref_var.create_account
			print('\n' + "Would you like to complete the form again?" + '\n' + '\n' + "Type yes or no: ")
			var redo = readLine
			if(redo == "yes") {
				println('\n' + "---------------------------------------------------------------------------------")
			}
			else{
				x += 1
				println('\n' + "End.")
			}
		}

		if(choice == 2) {
			bank_of_leo_ref_var.deposit_money
			print('\n' + "Would you like to complete the form again?" + '\n' + '\n' + "Type yes or no: ")
			var redo = readLine
			if(redo == "yes") {
				println('\n' + "---------------------------------------------------------------------------------")
			}
			else{
				x += 1
				println('\n' + "End.")
			}
		}

		if(choice == 3) {
			bank_of_leo_ref_var.withdraw_money
			print('\n' + "Would you like to complete the form again?" + '\n' + '\n' + "Type yes or no: ")
			var redo = readLine
			if(redo == "yes") {
				println('\n' + "---------------------------------------------------------------------------------")
			}
			else{
				x += 1
				println('\n' + "End.")
			}
		}

		if(choice == 4) {
			print('\n' + "Enter your account number: ")
			var accno=readLine
			println('\n' + "Your Balance is: £"+bank_of_leo_ref_var.check_balance(accno))
			println('\n' + "---------------------------------------------------------------------------------")
			print('\n' + "Would you like to complete the form again?" + '\n' + '\n' + "Type yes or no: ")
			var redo = readLine
			if(redo == "yes") {
				println('\n' + "---------------------------------------------------------------------------------")
			}
			else{
				x += 1
				println('\n' + "End.")
			}
		}	

		if(choice >= 5 || choice <= 0) {
			println('\n' + "You entered an incorrect value.")
			print('\n' + "Would you like to choose from options 1, 2, 3 or 4 again?" + '\n' + '\n' + "Type yes or no: ")
			var redo = readLine
			if(redo == "yes") {
				println('\n' + "---------------------------------------------------------------------------------")
			}
			else{
				x += 1
				println('\n' + "End.")
			}
		}
	}
} catch {
	case e:Exception => println(e) 
}


//Leo Calnan, QA Trainee