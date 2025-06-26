// CadastroProjeto.java
package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.ProjetoController;
import br.edu.utfpr.sonode.controller.UsuarioController;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CadastroProjeto extends JFrame {
    private final JTextField txtNome = new JTextField();
    private final JTextField txtTom  = new JTextField();
    private final JTextField txtBpm  = new JTextField();
    private final JComboBox<Usuario> cmbUsuario = new JComboBox<>();
    private final ProjetoController pCtrl = new ProjetoController();
    private final UsuarioController uCtrl = new UsuarioController();

    public CadastroProjeto(JFrame parent) {
        super("Cadastro de Projeto");
        setLayout(new GridLayout(0,2,5,5));

        add(new JLabel("Nome:"));        add(txtNome);
        add(new JLabel("Tom:"));         add(txtTom);
        add(new JLabel("BPM:"));         add(txtBpm);
        add(new JLabel("Criador:"));
        List<Usuario> users = uCtrl.listarUsuarios();
        for (Usuario u : users) cmbUsuario.addItem(u);
        add(cmbUsuario);

        JButton btnSalvar   = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            Projeto p = new Projeto();
            p.setNome(txtNome.getText().trim());
            p.setTom(txtTom.getText().trim());
            p.setBpm(Integer.parseInt(txtBpm.getText().trim()));
            p.setCriador((Usuario) cmbUsuario.getSelectedItem());
            p.setDataCriacao(LocalDate.now());
            pCtrl.cadastrarProjeto(p);
            JOptionPane.showMessageDialog(this,"Projeto cadastrado!");
            dispose();
        });
        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
