package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class CadastroProjeto extends JDialog {
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnSalvar;
    private ProjetoController projetoController;
    private Usuario usuarioLogado;
    private Projeto projetoExistente;

    public CadastroProjeto(Frame owner, Usuario usuario, Projeto projeto) {
        super(owner, "Cadastro de Projeto", true);
        this.usuarioLogado = usuario;
        this.projetoExistente = projeto;
        this.projetoController = new ProjetoController();

        setSize(450, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome do Projeto:"));
        txtNome = new JTextField(20);
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(txtNome);

        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        formPanel.add(new JLabel("Descrição:"));
        txtDescricao = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(scrollPane);

        btnSalvar = new JButton("Salvar Projeto");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSalvar);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        if (projetoExistente != null) {
            setTitle("Editar Projeto");
            txtNome.setText(projetoExistente.getNome());
            txtDescricao.setText(projetoExistente.getDescricao());
        }

        btnSalvar.addActionListener(e -> salvarProjeto());
    }

    private void salvarProjeto() {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do projeto é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (projetoExistente == null) { 
            projetoController.criarProjeto(nome, descricao, usuarioLogado);
            JOptionPane.showMessageDialog(this, "Projeto criado com sucesso!");
        } else { 
            projetoExistente.setNome(nome);
            projetoExistente.setDescricao(descricao);
            projetoController.atualizarProjeto(projetoExistente);
            JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
        }

        this.dispose();
    }
}