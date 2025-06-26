package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisualizadorProjetos extends JDialog {
    private JTable tabelaProjetos;
    private DefaultTableModel tableModel;
    private ProjetoController projetoController;
    private Usuario usuarioLogado;
    private Frame owner;
    private JTextField txtFiltro;

    public VisualizadorProjetos(Frame owner, Usuario usuario) {
        super(owner, "Visualizador de Projetos", true);
        this.owner = owner;
        this.usuarioLogado = usuario;
        this.projetoController = new ProjetoController();

        setSize(800, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

    
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.add(new JLabel("Filtrar por nome do projeto ou dono:"));
        txtFiltro = new JTextField(20);
        JButton btnFiltrar = new JButton("Filtrar");
        filtroPanel.add(txtFiltro);
        filtroPanel.add(btnFiltrar);
        add(filtroPanel, BorderLayout.NORTH);

        
        String[] colunas = {"ID", "Nome", "Descrição", "Dono", "Data de Criação"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabelaProjetos = new JTable(tableModel);
        add(new JScrollPane(tabelaProjetos), BorderLayout.CENTER);

        
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDetalhes = new JButton("Ver Detalhes/Editar");
        JButton btnExcluir = new JButton("Excluir Projeto");
        JButton btnNovo = new JButton("Novo Projeto");
        
        botoesPanel.add(btnNovo);
        botoesPanel.add(btnDetalhes);
        botoesPanel.add(btnExcluir);
        add(botoesPanel, BorderLayout.SOUTH);

        
        btnFiltrar.addActionListener(e -> carregarProjetos());
        
        btnNovo.addActionListener(e -> {
            new CadastroProjeto(owner, usuarioLogado, null).setVisible(true);
            carregarProjetos();
        });

        btnDetalhes.addActionListener(e -> abrirDetalhesProjeto());
        btnExcluir.addActionListener(e -> excluirProjeto());

        carregarProjetos();
    }

    private void carregarProjetos() {
        tableModel.setRowCount(0); 
        String termo = txtFiltro.getText();
        List<Projeto> projetos = projetoController.filtrarProjetos(termo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Projeto p : projetos) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getDono().getNome(),
                    p.getDataCriacao().format(formatter)
            });
        }
    }

    private void abrirDetalhesProjeto() {
        int selectedRow = tabelaProjetos.getSelectedRow();
        if (selectedRow >= 0) {
            Long projetoId = (Long) tableModel.getValueAt(selectedRow, 0);
            Projeto projeto = projetoController.buscarProjetoCompletoPorId(projetoId);
            new TelaProjeto(owner, usuarioLogado, projeto).setVisible(true);
            carregarProjetos(); 
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirProjeto() {
        int selectedRow = tabelaProjetos.getSelectedRow();
        if (selectedRow >= 0) {
            Long projetoId = (Long) tableModel.getValueAt(selectedRow, 0);
            Projeto projeto = projetoController.buscarProjetoCompletoPorId(projetoId);
            
            if(!projeto.getDono().getId().equals(usuarioLogado.getId())) {
                 JOptionPane.showMessageDialog(this, "Você só pode excluir projetos que você criou.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o projeto '" + projeto.getNome() + "'?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                projetoController.excluirProjeto(projeto);
                carregarProjetos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}