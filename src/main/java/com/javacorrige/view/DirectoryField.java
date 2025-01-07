package com.javacorrige.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class DirectoryField {
    private JLabel label;
    private JTextField textField;
    private JButton chooseButton;

    public DirectoryField(String labelText) {
        label = new JLabel(labelText);
        textField = new JTextField(20);
        chooseButton = new JButton("...");

        // Ajustar o tamanho do botão
        chooseButton.setPreferredSize(new Dimension(20, 20)); // Botão menor

        // Ação do botão que abre o FileChooser
        chooseButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Apenas diretórios
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                textField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        // Usar GridBagLayout para melhor controle do layout
        setLayout();
    }

    private void setLayout() {
        // Painel principal com layout horizontal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical para cada linha
    
        // Painel para o label, textField e o botão
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS)); // Layout horizontal
    
        // Adicionando o label com largura fixa
        label.setPreferredSize(new Dimension(150, 20)); // Defina o tamanho fixo do label
    
        fieldPanel.add(label); // Adiciona o label
    
        // Adicionando o textField
        fieldPanel.add(Box.createHorizontalStrut(10)); // Adiciona um pequeno espaçamento
        fieldPanel.add(textField);
    
        // Adicionando o botão FileChooser
        fieldPanel.add(Box.createHorizontalStrut(10)); // Adiciona um pequeno espaçamento
        fieldPanel.add(chooseButton);
    
        // Adiciona o painel de campo à tela
        panel.add(fieldPanel);
    
        // Define a largura do painel
        panel.setPreferredSize(new Dimension(400, 40));
    }
    
    

    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton getChooseButton() {
        return chooseButton;
    }

    public String getText() {
        return textField.getText();
    }

    public JPanel getPanel() {
        // Retorna o painel do campo completo, que inclui o layout configurado
        return (JPanel) chooseButton.getParent();
    }
}
