import utilities.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame dialog = JFrame.getInstance();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
