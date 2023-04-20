
public class Main {
    public static void main(String[] args) throws Exception {
        try (MenuLibrary menuLibrary = new MenuLibrary()) {
            menuLibrary.useMenu();
        }
    }
}
