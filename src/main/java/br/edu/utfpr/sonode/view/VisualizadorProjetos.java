package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.controller.VersaoProjetoController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.VersaoProjeto;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisualizadorProjetos extends JFrame {
    private final ProjetoController projetoCtrl       = new ProjetoController();
    private final VersaoProjetoController versaoCtrl = new VersaoProjetoController();

    private final JComboBox<Projeto>          cbProjetos = new JComboBox<>();
    private final JLabel                      lblAutor    = new JLabel("Autor: ");
    private final DefaultListModel<VersaoProjeto> versoesModel = new DefaultListModel<>();
    private final JList<VersaoProjeto>        lstVersoes = new JList<>(versoesModel);

    public VisualizadorProjetos() {
        super("Visualizador de Projetos");
        setLayout(new BorderLayout(5,5));

        // Norte: combo de Projetos
        add(cbProjetos, BorderLayout.NORTH);

        // Centro: lista de versões
        add(new JScrollPane(lstVersoes), BorderLayout.CENTER);

        // Sul: label do autor
        add(lblAutor, BorderLayout.SOUTH);

        // Popula combo
        List<Projeto> projetos = projetoCtrl.listarProjetos();
        for (Projeto p : projetos) {
            cbProjetos.addItem(p);
        }

        // Render do combo para mostrar apenas o nome do Projeto
        cbProjetos.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Projeto) {
                    setText(((Projeto) value).getNome());
                }
                return this;
            }
        });

        // Ao trocar seleção, atualiza lista e autor
        cbProjetos.addActionListener(e -> atualizarListaEVersoes());

        // Seleciona o primeiro item (se existir)
        if (!projetos.isEmpty()) {
            cbProjetos.setSelectedIndex(0);
            atualizarListaEVersoes();
        }

        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void atualizarListaEVersoes() {
        Projeto sel = (Projeto) cbProjetos.getSelectedItem();
        if (sel == null) return;

        // Atualiza autor
        lblAutor.setText("Autor: " + sel.getCriador().getNome());

        // Busca versões e preenche modelo
        versoesModel.clear();
        List<VersaoProjeto> versoes = versaoCtrl.listarVersoesDoProjeto(sel);
        for (VersaoProjeto v : versoes) {
            versoesModel.addElement(v);
        }

        // Render do JList para exibir nº, nomeArquivo e dataHora formatada
        lstVersoes.setCellRenderer(new DefaultListCellRenderer(){
            private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof VersaoProjeto) {
                    VersaoProjeto vp = (VersaoProjeto) value;
                    setText(
                        "v" + vp.getNumero()
                        + " – " + vp.getNomeArquivo()
                        + " (" + vp.getDataHora().format(dtf) + ")"
                    );
                }
                return this;
            }
        });
    }

    // Para teste isolado:
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisualizadorProjetos::new);
    }
}
