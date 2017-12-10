package com.sean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gui {
    public JFrame frame;
    JPanel panel;
    MyFile text = new MyFile();

    public Gui() {
        frame = new JFrame("fileEditor");
        panel = new JPanel();
    }

    public void init() {
        frame.setSize(1000, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placePanel(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Gui fileEditor = new Gui();
        fileEditor.init();
    }

    private void placePanel(JPanel panel) {

        panel.setLayout(null);
        JTextArea textArea = new JTextArea("try");
        textArea.setBounds(5, 70, 980, 540);

        JButton openButton = new JButton("open");
        openButton.setBounds(50, 20, 80, 30);

        // 四个按钮采用四种方式来实现（练手用的）,匿名内部类
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    text.open(file);
                    String content = null;
                    try {
                        InputStream inputStream = new FileInputStream(text.getFile());
                        byte[] bytes = new byte[(int)text.getFile().length()];
                        inputStream.read(bytes);
                        content = new String(bytes);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    textArea.setText(content);
                } else {
                    frame.remove(fileChooser);
                }
            }
        });

        JButton createButton = new JButton("create");
        createButton.setBounds(150, 20, 80, 30);
        class CreateListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Path createFile = Files.createFile(file.toPath());
                        text.open(createFile.toFile());
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                } else {
                    frame.remove(fileChooser);
                }
            }
        }
        createButton.addActionListener(new CreateListener());

        JButton saveButton = new JButton("save");
        saveButton.setBounds(250, 20, 80, 30);
        saveButton.addActionListener(event -> {
            if (text.getFile() == null) {
                createButton.doClick();
            }
            int comfirm = JOptionPane.showConfirmDialog(null,"Are you sure to save this file?","confirm",JOptionPane.YES_NO_CANCEL_OPTION);
            if(comfirm == 0) {
                String inputText = textArea.getText();
                OutputStream out = null;
                try {
                    out = new FileOutputStream(text.getFile());
                    out.write(inputText.getBytes());
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(text.getFile().length() == 0){
                    try {
                        Files.deleteIfExists(text.getFile().toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        JButton searchButton = new JButton("search");
        searchButton.setBounds(350, 20, 80, 30);
        searchButton.addActionListener(event ->{
            String content = textArea.getText();
        });

        JTextArea script = new JTextArea();
        script.setBounds(0, 610, 1000, 20);
        script.setBackground(Color.GRAY);

        panel.add(openButton);
        panel.add(createButton);
        panel.add(saveButton);
        panel.add(searchButton);
        panel.add(textArea);
        panel.add(script);

    }
}
