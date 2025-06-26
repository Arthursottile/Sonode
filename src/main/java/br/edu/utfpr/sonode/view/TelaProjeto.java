package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.model.Comentario;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;
import br.edu.utfpr.sonode.model.VersaoProjeto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

public class TelaProjeto extends JDialog {
    private Projeto projeto;
    private Usuario usuarioLogado;
    private ProjetoController projetoController;

    private JTextArea txtComentarios, txtNovoComentario;
    private JTable tabelaVersoes;
    private DefaultTableModel versoesTableModel;
    private JLabel lblColaboradores;

    public TelaProjeto(Frame owner, Usuario usuario, Projeto projeto) {
        super(owner, "Detalhes do Projeto: " + projeto.getNome(), true);
        this.usuarioLogado = usuario;
        this.projeto = projeto;
        this.projetoController = new ProjetoController();

        setSize(900, 700);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Versões de Arquivos", criarPainelVersoes());
        tabbedPane.addTab("Comentários", criarPainelComentarios());
        tabbedPane.addTab("Colaboradores", criarPainelColaboradores());

        add(tabbedPane, BorderLayout.CENTER);
        
        carregarVersoes();
        carregarComentarios();
        carregarColaboradores();
    }
    
    private JPanel criarPainelVersoes() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        
        String[] colunas = {"Versão", "Nome do Arquivo", "Tipo", "Data de Upload"};
        versoesTableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaVersoes = new JTable(versoesTableModel);
        panel.add(new JScrollPane(tabelaVersoes), BorderLayout.CENTER);
        

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddVersao = new JButton("Adicionar Novo");
        JButton btnEditarVersao = new JButton("Editar Selecionado");
        JButton btnExcluirVersao = new JButton("Excluir Selecionado");

        botoesPanel.add(btnAddVersao);
        botoesPanel.add(btnEditarVersao);
        botoesPanel.add(btnExcluirVersao);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        btnAddVersao.addActionListener(e -> adicionarNovaVersao());
        btnEditarVersao.addActionListener(e -> editarVersaoSelecionada());
        btnExcluirVersao.addActionListener(e -> excluirVersaoSelecionada());

        return panel;
    }

    private JPanel criarPainelComentarios() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        
        txtComentarios = new JTextArea();
        txtComentarios.setEditable(false);
        txtComentarios.setMargin(new Insets(5,5,5,5));
        panel.add(new JScrollPane(txtComentarios), BorderLayout.CENTER);

        JPanel novoComentarioPanel = new JPanel(new BorderLayout());
        novoComentarioPanel.add(new JLabel("Novo Comentário:"), BorderLayout.NORTH);
        txtNovoComentario = new JTextArea(3, 20);
        novoComentarioPanel.add(new JScrollPane(txtNovoComentario), BorderLayout.CENTER);
        JButton btnAddComentario = new JButton("Adicionar");
        novoComentarioPanel.add(btnAddComentario, BorderLayout.EAST);
        
        panel.add(novoComentarioPanel, BorderLayout.SOUTH);

        btnAddComentario.addActionListener(e -> adicionarComentario());
        
        return panel;
    }

    private JPanel criarPainelColaboradores() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        
        lblColaboradores = new JLabel();
        lblColaboradores.setVerticalAlignment(JLabel.TOP);
        panel.add(new JScrollPane(lblColaboradores), BorderLayout.CENTER);
        
        JPanel addColabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addColabPanel.add(new JLabel("Adicionar colaborador por email:"));
        JTextField txtEmailColaborador = new JTextField(20);
        addColabPanel.add(txtEmailColaborador);
        JButton btnAddColab = new JButton("Adicionar");
        addColabPanel.add(btnAddColab);
        
        panel.add(addColabPanel, BorderLayout.SOUTH);
        
        btnAddColab.addActionListener(e -> {
            adicionarColaborador(txtEmailColaborador.getText());
            txtEmailColaborador.setText("");
        });

        return panel;
    }
    
    private void carregarVersoes() {
        versoesTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        projeto.getVersoes().stream()
                .sorted((v1, v2) -> v2.getNumeroVersao() - v1.getNumeroVersao())
                .forEach(v -> {
                    versoesTableModel.addRow(new Object[] {
                        v.getNumeroVersao(),
                        v.getNomeArquivo(),
                        v.getTipoArquivo(),
                        v.getDataUpload().format(formatter)
                    });
                });
    }
    
    private void carregarComentarios() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        projeto.getComentarios().stream()
                .sorted((c1, c2) -> c2.getDataComentario().compareTo(c1.getDataComentario()))
                .forEach(c -> {
                    sb.append(c.getAutor().getNome()).append(" em ")
                      .append(c.getDataComentario().format(formatter)).append(":\n");
                    sb.append(c.getConteudo()).append("\n-----------------\n");
                });
        txtComentarios.setText(sb.toString());
    }
    
    private void carregarColaboradores() {
        String texto = projeto.getColaboradores().stream()
                .map(u -> u.getNome() + " (" + u.getEmail() + ")")
                .collect(Collectors.joining("<br>", "<html>", "</html>"));
        lblColaboradores.setText(texto);
    }
    
    private void adicionarNovaVersao() {
        String nomeArquivo = JOptionPane.showInputDialog(this, "Nome do arquivo (ex: track1.mp3):");
        if (nomeArquivo != null && !nomeArquivo.trim().isEmpty()) {
            String tipo = nomeArquivo.contains(".") ? nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1) : "desconhecido";
            projetoController.adicionarVersao(projeto, nomeArquivo, tipo);
            this.projeto = projetoController.buscarProjetoCompletoPorId(projeto.getId());
            carregarVersoes();
        }
    }

    private void adicionarComentario() {
        String conteudo = txtNovoComentario.getText();
        if(conteudo != null && !conteudo.trim().isEmpty()) {
            projetoController.adicionarComentario(projeto, usuarioLogado, conteudo);
            this.projeto = projetoController.buscarProjetoCompletoPorId(projeto.getId());
            carregarComentarios();
            txtNovoComentario.setText("");
        }
    }
    
    private void adicionarColaborador(String email) {
        if(email != null && !email.trim().isEmpty()) {
           boolean sucesso = projetoController.adicionarColaborador(projeto, email);
           if(sucesso) {
               this.projeto = projetoController.buscarProjetoCompletoPorId(projeto.getId());
               carregarColaboradores();
           } else {
               JOptionPane.showMessageDialog(this, "Usuário não encontrado ou já é colaborador/dono.", "Erro", JOptionPane.ERROR_MESSAGE);
           }
        }
    }

    private void editarVersaoSelecionada() {
        int selectedRow = tabelaVersoes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma versão na tabela para editar.", "Nenhuma Versão Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numeroVersao = (int) versoesTableModel.getValueAt(selectedRow, 0);
        Optional<VersaoProjeto> versaoOpt = projeto.getVersoes().stream()
                .filter(v -> v.getNumeroVersao() == numeroVersao)
                .findFirst();

        if (versaoOpt.isPresent()) {
            VersaoProjeto versaoParaEditar = versaoOpt.get();
            String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome para o arquivo:", versaoParaEditar.getNomeArquivo());
            
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                projetoController.editarVersao(versaoParaEditar, novoNome);
                // Recarrega o projeto do banco para ter certeza que está atualizado
                this.projeto = projetoController.buscarProjetoCompletoPorId(projeto.getId());
                carregarVersoes();
            }
        }
    }

    private void excluirVersaoSelecionada() {
        int selectedRow = tabelaVersoes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma versão na tabela para excluir.", "Nenhuma Versão Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numeroVersao = (int) versoesTableModel.getValueAt(selectedRow, 0);
        String nomeArquivo = (String) versoesTableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir a versão " + numeroVersao + " (" + nomeArquivo + ")?",
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Optional<VersaoProjeto> versaoOpt = projeto.getVersoes().stream()
                    .filter(v -> v.getNumeroVersao() == numeroVersao)
                    .findFirst();

            if (versaoOpt.isPresent()) {
                projetoController.excluirVersao(projeto, versaoOpt.get());
                // Recarrega o projeto do banco para ter certeza que está atualizado
                this.projeto = projetoController.buscarProjetoCompletoPorId(projeto.getId());
                carregarVersoes();
            }
        }
    }
}