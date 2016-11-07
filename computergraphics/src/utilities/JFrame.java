package utilities;

import task1.Line;
import task2.Circle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class JFrame extends JDialog {
    private final MouseCustomListener mouseCustomListener;
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
    private JPanel radiusPanel;
    private JPanel radiusYPanel;
    private JSpinner spinnerRadius;
    private JSpinner radiusY;

    private static JFrame instance;

    public static JFrame getInstance(){
        if (instance == null)
            instance = new JFrame();
        return instance;
    }

    private JFrame() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        x1.setValue(10);
        y1.setValue(10);
        x2.setValue(19);
        y2.setValue(15);

        this.mouseCustomListener = new MouseCustomListener();
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

        spinnerRadius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                spinnerChange();
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

    private void spinnerChange() {
        GraphicalObject gO = new Circle(
                new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                Integer.parseInt(this.spinnerRadius.getValue().toString())
        );
        gO.draw();
        this.mouseCustomListener.reset();
    }

    private void onCombo() {
        switch (this.comboBox1.getSelectedIndex()){
            case 2:
                resetFrame(true);
                break;
            case 1:
                resetFrame(false);
                break;
            default:
                x1.setEnabled(true);
                x2.setEnabled(true);
                y1.setEnabled(true);
                y2.setEnabled(true);
                this.radiusPanel.setVisible(false);
                this.radiusYPanel.setVisible(false);
                x1.setValue(0);
                y1.setValue(0);
                x2.setValue(100);
                y2.setValue(100);
                this.mouseCustomListener.reset();
                break;
        }
    }

    private void onClear() {
        this.jPanel1.repaint();
    }

    private void resetFrame(boolean b){
        this.radiusPanel.setVisible(true);
        this.radiusYPanel.setVisible(b);
        x1.setEnabled(false);
        x2.setEnabled(false);
        y1.setEnabled(false);
        y2.setEnabled(false);
        x1.setValue(this.getPanel1().getWidth()/2);
        y1.setValue(this.getPanel1().getHeight()/2);
        x2.setValue(300);
        y2.setValue(300);
        this.mouseCustomListener.reset();
    }

    private void onOK() {
        GraphicalObject gO =null;
        switch (this.comboBox1.getSelectedItem().toString()){
            case "line":
                gO = new Line(
                        new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                        new Point((Integer) x2.getValue(), (Integer) y2.getValue())
                );
                break;
            case "circle":
                gO = new Circle(
                        new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                        new Point((Integer) x2.getValue(), (Integer) y2.getValue())
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

    public void setRadius(int radius) {
        this.spinnerRadius.setValue(radius);
    }

    public JSpinner getSpinnerRadius() {
        return spinnerRadius;
    }

    public JSpinner getRadiusY() {
        return radiusY;
    }



    private class MouseCustomListener implements MouseListener{

        private Boolean sw = true;

        public  void reset(){
            sw = true;
        }
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

////TODO make JFRAME singleton parce que c'est plus opti