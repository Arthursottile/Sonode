// TelaProjeto.java
package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.controller.VersaoProjetoController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.VersaoProjeto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaProjeto extends JFrame {
    private final ProjetoController projetoCtrl     = new ProjetoController();
    private final VersaoProjetoController versaoCtrl = new VersaoProjetoController();

    public TelaProjeto() {
        super("Visualizar Projetos e Versões");
        setLayout(new BorderLayout(5,5));

        // Tabela de Projetos
        String[] colsP = {"ID","Nome","Tom","BPM","Criado em","Criador"};
        DefaultTableModel mp = new DefaultTableModel(colsP,0);
        JTable tp = new JTable(mp);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Projeto> projetos = projetoCtrl.listarProjetos();
        for (Projeto p : projetos) {
            mp.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getTom(),
                p.getBpm(),
                p.getDataCriacao().format(df),
                p.getCriador().getNome()
            });
        }

        tp.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tp.getSelectedRow()>=0) {
                int row = tp.getSelectedRow();
                Long id = Long.valueOf(tp.getValueAt(row,0).toString());
                Projeto p = projetoCtrl.buscarPorId(id);
                exibirVersoes(p);
            }
        });

        add(new JScrollPane(tp), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        add(btnFechar, BorderLayout.SOUTH);

        setSize(700,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void exibirVersoes(Projeto projeto) {
        List<VersaoProjeto> vs = versaoCtrl.listarVersoesDoProjeto(projeto);
        String[] colsV = {"#","Arquivo","Descrição","Data/Hora","TamanhoKB"};
        DefaultTableModel mv = new DefaultTableModel(colsV,0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (VersaoProjeto v : vs) {
            mv.addRow(new Object[]{
                v.getNumero(),
                v.getNomeArquivo(),
                v.getDescricao(),
                v.getDataHora().format(dtf),
                v.getTamanhoKB()
            });
        }

        JTable tv = new JTable(mv);
        JOptionPane.showMessageDialog(
          this,
          new JScrollPane(tv),
          "Versões de " + projeto.getNome(),
          JOptionPane.PLAIN_MESSAGE
        );
    }
}
