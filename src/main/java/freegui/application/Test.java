package freegui.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static void main(String[] args) {

        Form form = new Form(0,0,500,500);

        JTextField n1 = new JTextField(2);
        Label add = new Label("+");
        JTextField n2 = new JTextField(2);
        JButton button = new JButton("=");
        JTextField result = new JTextField(5);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer sum = Integer.valueOf(n1.getText()) + Integer.valueOf(n2.getText());
                result.setText(sum.toString());
            }
        });

        form.addComponent(n1);
        form.addComponent(add);
        form.addComponent(n2);
        form.addComponent(button);
        form.addComponent(result);

        form.show();
    }
}
