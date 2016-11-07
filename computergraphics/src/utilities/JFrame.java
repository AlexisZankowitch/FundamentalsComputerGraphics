package utilities;

import task1.Line;
import task2.Circle;
import task3.Ellipse;

import javax.swing.*;
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
    private JCheckBox drawAuto;
    private JComboBox comboBox1;
    private JPanel radiusPanel;
    private JPanel radiusYPanel;
    private JSpinner radiusX;
    private JSpinner radiusY;
    private JCheckBox withRadius;

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
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        clearButton.addActionListener(e -> onClear());
        comboBox1.addActionListener(e -> onCombo());
        withRadius.addActionListener(e -> onRadius());
        drawAuto.addActionListener(e -> onDrawAuto());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onDrawAuto() {
        this.mouseCustomListener.reset();
        this.withRadius.setSelected(false);
        this.radiusX.setEnabled(!this.drawAuto.isSelected());
    }

    private void onRadius() {
        if(this.withRadius.isSelected())
            this.drawAuto.setSelected(false);
        this.radiusX.setEnabled(this.withRadius.isSelected());
        this.radiusY.setEnabled(this.withRadius.isSelected());
    }

    private void onCombo() {
        switch (this.comboBox1.getSelectedIndex()){
            case 2:
                resetFrame(true);
                this.radiusY.setVisible(true);
                this.radiusX.setValue(100);
                this.radiusY.setValue(50);
                break;
            case 1:
                resetFrame(false);
                this.radiusY.setVisible(false);
                this.radiusX.setValue(100);
                break;
            case 0:
                x1.setEnabled(true);
                x2.setEnabled(true);
                y1.setEnabled(true);
                y2.setEnabled(true);
                this.radiusPanel.setVisible(false);
                this.withRadius.setSelected(false);
                this.mouseCustomListener.reset();
                break;
        }
    }

    private void onOK() {
        GraphicalObject gO =null;
        switch (this.comboBox1.getSelectedIndex()){
            case 2:
                if(this.withRadius.isSelected())
                    gO = new Ellipse(
                            new Point((Integer) x1.getValue(), (Integer) y1.getValue())
                    );
                else
                    gO = new Ellipse(
                            new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                            new Point((Integer) x2.getValue(), (Integer) y2.getValue())
                    );
                break;
            case 1:
                if(this.withRadius.isSelected())
                    gO = new Circle(
                            new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                            Integer.parseInt(this.radiusX.getValue().toString())
                    );
                else
                    gO = new Circle(
                            new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                            new Point((Integer) x2.getValue(), (Integer) y2.getValue())
                    );
                break;
            case 0:
                gO = new Line(
                        new Point((Integer) x1.getValue(), (Integer) y1.getValue()),
                        new Point((Integer) x2.getValue(), (Integer) y2.getValue())
                );
                break;
        }
        assert gO != null;
        gO.draw();
        this.mouseCustomListener.reset();
    }

    private void onClear() {
        this.jPanel1.repaint();
    }

    private void onCancel() {
        dispose();
    }

    private void resetFrame(boolean b){
        this.radiusPanel.setVisible(true);
        x2.setEnabled(false);
        y2.setEnabled(false);
        x1.setValue(this.getPanel1().getWidth()/2);
        y1.setValue(this.getPanel1().getHeight()/2);
        x2.setValue(300);
        y2.setValue(300);
        this.drawAuto.setSelected(false);
        this.withRadius.setSelected(true);
        this.mouseCustomListener.reset();
    }

    public JPanel getPanel1() {
        return jPanel1;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public void setRadius(int radius) {
        this.radiusX.setValue(radius);
    }

    public JSpinner getRadiusX() {
        return radiusX;
    }

    public JSpinner getRadiusY() {
        return radiusY;
    }

    private class MouseCustomListener implements MouseListener{

        private Boolean sw = true;

        void reset(){
            sw = true;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(this.sw){
                x1.setValue(e.getX());
                y1.setValue(e.getY());
                this.sw = !this.sw;
                jPanel1.getGraphics().drawRect(e.getX(),e.getY(),1,1);
            }else if (!withRadius.isSelected()){
                x2.setValue(e.getX());
                y2.setValue(e.getY());
                this.sw = !this.sw;
                jPanel1.getGraphics().drawRect(e.getX(),e.getY(),1,1);
                if(drawAuto.isSelected())
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