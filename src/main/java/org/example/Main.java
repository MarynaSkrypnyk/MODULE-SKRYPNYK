package org.example;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;

public class Main {
    public static void main(String[] args) {
        createUser();
        createAccount();
        createOperation();
        informUsersAboutOperationsForAccounts();
        createReport();
        updateUserInfo();
        deleteUserInfo();
        informForUserAboutAccounts();
        infoForUserAboutOperationsForCertainTime();
        informForUserAboutBalances();
        theMostExpensiveOperationsForAmount();
        sortOperationForCategory();
        statisticIncomeForAccount();
        statisticExpenseForAccount();
        sumBalanceForAllUsersAccount();
    }
    private static void createUser() {
        UserDao userDao = new UserDao();
        User user = new User.UserBuilder()
                .firstName("Seven")
                .lastName("Seven")
                .build();
        userDao.save(user);
    }

    private static void createAccount() {
        UserDao userDao = new UserDao();
        User user = userDao.get(7L);

        AccountDao accountDao = new AccountDao();
        Account account = new Account.AccountBuilder()
                .CVC(421L)
                .balance(BigDecimal.valueOf(566))
                .user(user)
                .build();
        accountDao.save(account);
    }

    private static void createOperation() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(7L);

        OperationDao operationDao = new OperationDao();
        Operation operation = new Operation.OperationBuilder()
                .account(account)
                .amount(12300)
                .createdAt(LocalDateTime.of(2023,5, 3, 12, 12))
                .category(Category.EXPENSE)
                .name("clothe")
                .build();

        operationDao.save(operation);
    }
    private static String informUsersAboutOperationsForAccounts() {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getOperation(1L);
        System.out.println(accounts);
        return accounts.toString();
    }

    private static void createReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You account in our bank is: ").append(informForUserAboutAccounts()).append("\n")
                .append("List of you balances: ").append(informForUserAboutBalances()).append("\n")
                .append("Now sum money on you balances: ").append(sumBalanceForAllUsersAccount()).append("\n")
                .append("For this account made this operations: ").append(informUsersAboutOperationsForAccounts()).append("\n")
                .append("From 01/03/2023 to 01/05/2023 you made this transactions: ").append(infoForUserAboutOperationsForCertainTime()).append("\n")
                .append("For all time sum money that you income: ").append(statisticIncomeForAccount()).append("\n")
                .append("For all time sum money that you spend: ").append(statisticExpenseForAccount()).append("\n")
                .append("You category: ").append(sortOperationForCategory()).append("\n")
                .append("You the most expensive buy : ").append(theMostExpensiveOperationsForAmount()).append("\n");
        try(FileWriter fileWriter = new FileWriter("/Users/user/IdeaProjects/a-level/Module/src/main/java/org/example/ module.csv")){
            fileWriter.write(stringBuilder.toString());
            System.out.println("Write in csv");
        } catch (Exception e){
        }
    }

    private static void updateUserInfo() {
        UserDao userDao = new UserDao();
        User user = userDao.get(2L);
        user.setLastName("Kevin");
        userDao.update(user);

        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(3L);
        account.setBalance(BigDecimal.valueOf(666));
        accountDao.update(account);

        OperationDao operationDao = new OperationDao();
        Operation operation = operationDao.get(2L);
        operation.setName("Salary");
        operationDao.update(operation);
    }

    private static void deleteUserInfo() {
 // по логике удаляеться и юзер и аккаунт и операции
        UserDao userDao = new UserDao();
        userDao.delete(1L);

// по логике удаляеться аккаунт и его операции
        AccountDao accountDao = new AccountDao();
        accountDao.delete(3L);

// по логике удаляеться определенная операция
        OperationDao operationDao = new OperationDao();
        operationDao.delete(5L);

    }
    private static String informForUserAboutAccounts() {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAccount(2L);
        System.out.println(users);
        return users.toString();
    }
    private static String infoForUserAboutOperationsForCertainTime() {
        OperationDao operationDao = new OperationDao();
        List <Operation> operation= operationDao.findOrders(2L, LocalDateTime.of(2023,1,23,9,12),
                of(2023,5,23,9,12));
        System.out.println(operation);
        return operation.toString();
    }
    private static String theMostExpensiveOperationsForAmount() {
        OperationDao operationDao = new OperationDao();
        List <Operation> operation= operationDao.sortForAmount(2L);
        System.out.println(operation);
        return operation.toString();
    }

    private static String sortOperationForCategory() {
        OperationDao operationDao = new OperationDao();
        List <Operation> operation= operationDao.sortCategory(2l);
        System.out.println(operation);
        return operation.toString();
    }

    private static String sumBalanceForAllUsersAccount() {
        AccountDao accountDao = new AccountDao();
        List <Account> accounts = accountDao.getSumBalance(2L);
        System.out.println(accounts);
        return accounts.toString();
    }
    private static String statisticIncomeForAccount() {
        OperationDao operationDao = new OperationDao();
        List <Operation> operation= operationDao.getSumMoneyForCategory(2l, Category.INCOME);
        System.out.println(operation);
        return operation.toString();
    }
    private static String statisticExpenseForAccount() {
        OperationDao operationDao = new OperationDao();
        List <Operation> operation= operationDao.getSumMoneyForCategory(2l, Category.EXPENSE);
        System.out.println(operation);
        return operation.toString();
    }
    private static String informForUserAboutBalances() {
        AccountDao accountDao = new AccountDao();
        List <Account> accounts = accountDao.getBalance(2L);
        System.out.println(accounts);
        return accounts.toString();
    }
}


