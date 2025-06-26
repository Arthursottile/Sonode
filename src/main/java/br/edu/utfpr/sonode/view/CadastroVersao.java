// CadastroVersao.java
package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.controller.VersaoProjetoController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.VersaoProjeto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class CadastroVersao extends JFrame {
    private final JComboBox<Projeto> cmbProjetos = new JComboBox<>();
    private final JTextField txtNomeArquivo      = new JTextField();
    private final JTextField txtDescricao        = new JTextField();
    private final JTextField txtTamanhoKB        = new JTextField();
    private final VersaoProjetoController vCtrl  = new VersaoProjetoController();
    private final ProjetoController pCtrl        = new ProjetoController();

    public CadastroVersao() {
        super("Cadastro de Versão");
        setLayout(new GridLayout(0,2,5,5));

        add(new JLabel("Projeto:"));
        List<Projeto> projetos = pCtrl.listarProjetos();
        for (Projeto p : projetos) cmbProjetos.addItem(p);
        add(cmbProjetos);

        add(new JLabel("Nome do Arquivo:")); add(txtNomeArquivo);
        add(new JLabel("Descrição:"));       add(txtDescricao);
        add(new JLabel("Tamanho (KB):"));    add(txtTamanhoKB);

        JButton btnSalvar   = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            Projeto proj = (Projeto) cmbProjetos.getSelectedItem();
            String nome  = txtNomeArquivo.getText().trim();
            String desc  = txtDescricao.getText().trim();
            int tam      = Integer.parseInt(txtTamanhoKB.getText().trim());
            VersaoProjeto v = new VersaoProjeto(nome, desc, LocalDateTime.now(), tam, proj);
            vCtrl.cadastrarVersao(v);
            JOptionPane.showMessageDialog(this,"Versão cadastrada!");
            dispose();
        });
        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
