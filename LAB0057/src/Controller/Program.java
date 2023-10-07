package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Common.Validation;
import View.Menu;

public class Program extends Menu<String> {
    static String[] menuChoice = { "Create a new account", "Logic system", "Exit" };

    public Program() {
        super("========== USER MANAGEMENT SYSTEM ==========", menuChoice);
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1: {
                createNewAccount();
                break;
            }
            case 2: {
                loginSystem();
                break;
            }
            case 3: {
                System.exit(0);
            }
        }
    }

    // ---------------------------------------------------------------------------------
    public void createNewAccount() {
        System.out.println();
        System.out.println("========== CREATE NEW ACCOUNT ==========");
        if (!Validation.checkFileExist()) {
            return;
        }

        String username = Validation.getUserName();
        while (!Validation.checkUsernameExist(username)) {
            System.out.println("Username exits.");
            username = Validation.getUserName();
        }
        String password = Validation.getPassword();
        addAccountDate(username, password);
        System.out.println();
    }

    public void addAccountDate(String username, String password) {
        File file = new File("src\\Model\\user.dat");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(username + ";" + password + "\n");
            fileWriter.close();
            System.err.println("Create successly.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loginSystem() {
        // check file data exist or not
        if (!Validation.checkFileExist()) {
            return;
        }
        String username = Validation.getUserName();
        String password = Validation.getPassword();
        // check username exist or not
        if (!Validation.checkUsernameExist(username)) {
            if (!password.equalsIgnoreCase(passwordByUsername(username))) {
                System.err.println("Password incorrect.");
            }
            System.err.println("Login success.");
        }
    }

    public static String passwordByUsername(String username) {
        File file = new File("src\\Model\\user.dat");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] account = line.split(";");
                if (username.equalsIgnoreCase(account[0])) {
                    return account[1];
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
