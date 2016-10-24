package utilities;

import task1.Line;
import task2.Circle;

import javax.swing.*;
import java.awt.event.*;

public class JFrame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JPanel jPanel1;
    private JSpinner x1;
    private JSpinner y1;
    private JSpinner x2;
    private JSpinner y2;
    private JButton clearButton;
    private JCheckBox jCheckBox;
    private JComboBox comboBox1;

    public JFrame() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        x1.setValue(10);
        y1.setValue(10);
        x2.setValue(19);
        y2.setValue(15);

        MouseCustomListener mouseCustomListener = new MouseCustomListener();
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

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClear();
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCombo();
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

    private void onCombo() {
        switch (this.comboBox1.getSelectedIndex()){
            case 1:
                x1.setValue(this.getPanel1().getWidth()/2);
                y1.setValue(this.getPanel1().getHeight()/2);
                x2.setValue(300);
                y2.setValue(300);
                break;
            default:
                x1.setValue(0);
                y1.setValue(0);
                x2.setValue(100);
                y2.setValue(100);
                break;
        }
    }

    private void onClear() {
        this.jPanel1.repaint();
    }

    private void onOK() {
        GraphicalObject gO =null;
        switch (this.comboBox1.getSelectedItem().toString()){
            case "line":
                gO = new Line(
                        new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                        new Point((Integer) x2.getValue(), (Integer) y2.getValue()),
                        this
                );
                break;
            case "circle":
                gO = new Circle(
                        new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                        new Point((Integer) x2.getValue(), (Integer) y2.getValue()),
                        this
                );
                break;
        }

        if (gO != null) {
            gO.draw();
        }
    }

    private void onCancel() {
        dispose();
    }

    public JPanel getPanel1() {
        return jPanel1;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    private class MouseCustomListener implements MouseListener{

        private Boolean sw = true;
        @Override
        public void mouseClicked(MouseEvent e) {
            if(this.sw){
                x1.setValue(e.getX());
                y1.setValue(e.getY());
                this.sw = !this.sw;
                jPanel1.getGraphics().drawRect(e.getX(),e.getY(),1,1);
            }else{
                x2.setValue(e.getX());
                y2.setValue(e.getY());
                this.sw = !this.sw;
                jPanel1.getGraphics().drawRect(e.getX(),e.getY(),1,1);
                if(jCheckBox.isSelected())
                    onOK();
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
    }
}
