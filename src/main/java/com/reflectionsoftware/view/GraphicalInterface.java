package com.reflectionsoftware.view;

import javax.swing.*;
import com.reflectionsoftware.controller.AppController;
import com.reflectionsoftware.util.validator.InputValidator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class GraphicalInterface {
    
    @SuppressWarnings("unused")
    public void show() {
        // Criar a interface gráfica
        JFrame frame = new JFrame("Reflection Software");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela

        // Painel principal com layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona espaço ao redor

        // Cabeçalho
        JLabel headerLabel = new JLabel("Configurações do Software", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaçamento abaixo do título

        // Criar campos de entrada
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Grid com espaçamento entre os campos
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField templateDirectoryField = new JTextField(20);
        JTextField studentsDirectoryField = new JTextField(20);
        JTextField pdfDirectoryField = new JTextField(20);
        JTextField stepCorrectionField = new JTextField(20);

        fieldsPanel.add(new JLabel("Diretório de Template:"));
        fieldsPanel.add(templateDirectoryField);
        fieldsPanel.add(new JLabel("Diretório dos Alunos:"));
        fieldsPanel.add(studentsDirectoryField);
        fieldsPanel.add(new JLabel("Diretório PDFs:"));
        fieldsPanel.add(pdfDirectoryField);
        fieldsPanel.add(new JLabel("Passo de Correção:"));
        fieldsPanel.add(stepCorrectionField);

        panel.add(fieldsPanel);

        // Espaçamento adicional entre os campos e o botão
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Criar o botão de execução
        JButton startButton = new JButton("Iniciar");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(new Color(0, 123, 255)); // Cor de fundo azul
        startButton.setForeground(Color.WHITE); // Cor do texto branco
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startButton);

        // Adicionar o painel à janela
        frame.add(panel);
        frame.setVisible(true);

        // Ação para o botão de iniciar
        startButton.addActionListener((ActionEvent e) -> {
            try {
                // Obter os valores dos campos
                String templateDir = templateDirectoryField.getText();
                String studentsDir = studentsDirectoryField.getText();
                String pdfDir = pdfDirectoryField.getText();
                String stepCorrection = stepCorrectionField.getText();

                // Validar os diretórios
                InputValidator.validateArguments(new String[]{templateDir, studentsDir, pdfDir, stepCorrection});
                
                File templateDirectory = new File(templateDir);
                File studentsDirectory = new File(studentsDir);
                File pdfDirectory = new File(pdfDir);

                // Criar e iniciar o controlador principal
                AppController app = new AppController(templateDirectory, studentsDirectory, pdfDirectory, stepCorrection);
                app.start();

                // Exibir mensagem de sucesso e fechar o programa
                JOptionPane.showMessageDialog(frame, "Software executado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Fechar a janela

            } catch (Exception ex) {
                // Exibir o erro na interface gráfica
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
