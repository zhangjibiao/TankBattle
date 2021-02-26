package net;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    private static final ClientFrame INSTANCE = new ClientFrame();

    public static ClientFrame getINSTANCE() {
        return INSTANCE;
    }

    Client client;
    TextArea ta = new TextArea();
    TextField tf = new TextField();

    public ClientFrame() {
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //把字符串发送到服务器
                client.send(tf.getText());
                tf.setText("");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.closeConnect();
                System.exit(0);
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        ClientFrame frame = ClientFrame.getINSTANCE();
        frame.connectToServer();
    }

    private void connectToServer() {
        client = new Client();
        client.connect();
    }

    public void showMsg(String s) {
        ta.setText(ta.getText() + s + "\n");
    }
}


