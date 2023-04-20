package users;

import database.Database;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserFunction {
    UserRepository userRepository;

    public UserFunction(Database database) {
        this.userRepository = new UserRepository(database);
    }
    public void addReaderToList() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        System.out.println("Enter user to library");
        do {
            try {
                System.out.println("Name:");
                String name = scanner.nextLine();
                System.out.println("Last Name");
                String lastName = scanner.nextLine();
                System.out.println("Email");
                String email = scanner.nextLine();
                System.out.println("Number:");
                int number = scanner.nextInt();
                scanner.nextLine();
                userRepository.createUser(new User(null, name, lastName, email, number));
            } catch (InputMismatchException e) {
                System.out.println("Bad type");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Back to menu -> yes/no");
            choice = scanner.nextLine();
        } while (!choice.equalsIgnoreCase("yes"));
    }
    public void deleteReaderFromLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Do you want delete reader -> yes/no");
            choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                readerListView();
                try {
                    System.out.println("Enter reader id to delete:");
                    int idReader = scanner.nextInt();
                    scanner.nextLine();
                    userRepository.removeUserFromDatabase(idReader);

                } catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                } catch(InputMismatchException e){
                    System.out.println("Bad type id");
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        } while (!choice.equalsIgnoreCase("no"));
    }
    public void readerListView() {
        Collection<User>  readerList = userRepository.loadUser();
        System.out.println("Readers in library:");
        for (User user : readerList ) {
            System.out.println(user.toString());
        }
    }
}
