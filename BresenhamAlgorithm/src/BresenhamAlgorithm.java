import javax.swing.*;
import java.awt.event.*;

public class BresenhamAlgorithm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JPanel jPanel1;
    private JSpinner x1;
    private JSpinner y1;
    private JSpinner x2;
    private JSpinner y2;

    private BresenhamAlgorithm() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        x1.setValue(10);
        y1.setValue(10);
        x2.setValue(19);
        y2.setValue(15);

        MouseCustomListener mouseCustomListener = new MouseCustomListener();
        jPanel1.addMouseMotionListener(mouseCustomListener);
        jPanel1.addMouseListener(mouseCustomListener);

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
        Line line = new Line(
                new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                new Point((Integer) x2.getValue(), (Integer) y2.getValue()),
                this
        );
        line.drawLine();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        BresenhamAlgorithm dialog = new BresenhamAlgorithm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


    JPanel getjPanel1() {
        return jPanel1;
    }

    JTextArea getTextArea1() {
        return textArea1;
    }

    private class MouseCustomListener implements MouseMotionListener,MouseListener{

        private Boolean sw = true;
        @Override
        public void mouseClicked(MouseEvent e) {

            if(this.sw){
                x1.setValue(e.getX());
                y1.setValue(e.getY());
                this.sw = !this.sw;
            }else{
                x2.setValue(e.getX());
                y2.setValue(e.getY());
                this.sw = !this.sw;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
