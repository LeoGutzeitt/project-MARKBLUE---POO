package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMensagemErro extends JFrame {

    public TelaMensagemErro(List<String> erros) {
        setTitle("Erros encontrados");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (String e : erros) {
            area.append("- " + e + "\n");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(ev -> dispose());
        add(btnOk, BorderLayout.SOUTH);

        setVisible(true);
    }
}
