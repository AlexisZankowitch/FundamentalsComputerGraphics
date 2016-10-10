import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BresenhamAlgorithm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private ArrayList<JTextField> coordsTxt;
    private ArrayList<Integer> coordsInt;
    private JTextField x1TextField;
    private JTextField x2TextField;
    private JTextField y1TextField;
    private JTextField y2TextField;
    private JTextArea textArea1;
    private JTextField textField1;

    public BresenhamAlgorithm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        coordsTxt = new ArrayList<>();
        coordsInt = new ArrayList<>();
        coordsTxt.add(x1TextField);
        coordsTxt.add(x2TextField);
        coordsTxt.add(y1TextField);
        coordsTxt.add(y2TextField);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // get coordinates
        for (JTextField item : coordsTxt) {
            try {
                coordsInt.add(Integer.parseInt(item.getText()));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        //calculate Dx and Dy

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        BresenhamAlgorithm dialog = new BresenhamAlgorithm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
