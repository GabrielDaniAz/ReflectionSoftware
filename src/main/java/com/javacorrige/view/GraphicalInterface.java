package com.javacorrige.view;

import javax.swing.*;

import com.javacorrige.controller.AppController;
import com.javacorrige.util.validator.InputValidator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class GraphicalInterface {

    private JFrame frame;
    private JPanel panel;

    // Campos de diretórios agora são instâncias da classe DirectoryField
    private DirectoryField templateDirectoryField;
    private DirectoryField studentsDirectoryField;
    private DirectoryField pdfDirectoryField;
    private DirectoryField stepCorrectionField;

    public void show() {
        frame = new JFrame("Reflection Software");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(550, 350);
        frame.setMinimumSize(new Dimension(500,300));
        frame.setMaximumSize(new Dimension(600,400));

        frame.setLocationRelativeTo(null); // Centraliza a janela

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addHeader();
        addFieldsPanel();
        addStartButton();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addHeader() {
        JLabel headerLabel = new JLabel("Configurações do Software", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addFieldsPanel() {
        // Criar instâncias de DirectoryField para cada campo de entrada
        templateDirectoryField = new DirectoryField("Diretório do Gabarito:");
        studentsDirectoryField = new DirectoryField("Diretório dos Alunos:");
        pdfDirectoryField = new DirectoryField("Diretório PDFs:");
        stepCorrectionField = new DirectoryField("Passo de Correção:");

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Ajustar para 1 coluna por campo
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adicionando os campos e labels ao painel
        fieldsPanel.add(templateDirectoryField.getPanel());
        fieldsPanel.add(studentsDirectoryField.getPanel());
        fieldsPanel.add(pdfDirectoryField.getPanel());
        fieldsPanel.add(stepCorrectionField.getPanel());

        panel.add(fieldsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addStartButton() {
        JButton startButton = new JButton("Iniciar");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(new Color(0, 123, 255)); // Cor de fundo azul
        startButton.setForeground(Color.WHITE); // Cor do texto branco
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener((ActionEvent e) -> executeApp());

        panel.add(startButton);
    }

    private void executeApp() {
        try {
            // Obter os valores dos campos diretamente de cada instância de DirectoryField
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
    }
}
