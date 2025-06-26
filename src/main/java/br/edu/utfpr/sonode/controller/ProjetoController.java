package br.edu.utfpr.sonode.controller;

import br.edu.utfpr.sonode.dao.ProjetoDAO;
import br.edu.utfpr.sonode.dao.UsuarioDAO;
import br.edu.utfpr.sonode.dao.VersaoProjetoDAO;
import br.edu.utfpr.sonode.model.Comentario;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;
import br.edu.utfpr.sonode.model.VersaoProjeto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProjetoController {

    private final ProjetoDAO projetoDAO;
    private final UsuarioDAO usuarioDAO;
    private final VersaoProjetoDAO versaoProjetoDAO;

    public ProjetoController() {
        this.projetoDAO = new ProjetoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.versaoProjetoDAO = new VersaoProjetoDAO(); 
    }
    
    public void criarProjeto(String nome, String descricao, Usuario dono) {
        Projeto projeto = new Projeto();
        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setDataCriacao(LocalDate.now());
        projeto.setDono(dono);
        projetoDAO.save(projeto);
    }
    
    public void atualizarProjeto(Projeto projeto) {
        projetoDAO.update(projeto);
    }

    public void excluirProjeto(Projeto projeto) {
        projetoDAO.delete(projeto);
    }
    
    public boolean adicionarColaborador(Projeto projeto, String emailColaborador) {
        Optional<Usuario> colaboradorOpt = usuarioDAO.findByEmail(emailColaborador);
        if(colaboradorOpt.isPresent()) {
            Usuario colaborador = colaboradorOpt.get();
            if (!projeto.getColaboradores().contains(colaborador) && !projeto.getDono().equals(colaborador)) {
                projeto.getColaboradores().add(colaborador);
                projetoDAO.update(projeto);
                return true;
            }
        }
        return false;
    }

    public void adicionarVersao(Projeto projeto, String nomeArquivo, String tipoArquivo) {
        VersaoProjeto versao = new VersaoProjeto();
        versao.setNomeArquivo(nomeArquivo);
        versao.setTipoArquivo(tipoArquivo);
        versao.setDataUpload(LocalDateTime.now());
        versao.setProjeto(projeto);
        
        int proximaVersao = projeto.getVersoes().stream()
                                  .mapToInt(VersaoProjeto::getNumeroVersao)
                                  .max()
                                  .orElse(0) + 1;
        versao.setNumeroVersao(proximaVersao);
        
        projeto.getVersoes().add(versao);
        projetoDAO.update(projeto);
    }
    
    public void adicionarComentario(Projeto projeto, Usuario autor, String conteudo) {
        Comentario comentario = new Comentario();
        comentario.setConteudo(conteudo);
        comentario.setAutor(autor);
        comentario.setProjeto(projeto);
        comentario.setDataComentario(LocalDateTime.now());
        
        projeto.getComentarios().add(comentario);
        projetoDAO.update(projeto);
    }
    
    public List<Projeto> buscarTodosProjetos() {
        return projetoDAO.findAll();
    }

    public List<Projeto> filtrarProjetos(String termo) {
        if(termo == null || termo.trim().isEmpty()) {
            return buscarTodosProjetos();
        }
        return projetoDAO.findByNomeOrUsuario(termo);
    }
    
    public Projeto buscarProjetoCompletoPorId(Long id) {
        return projetoDAO.findCompletoById(id);
    }

    public void editarVersao(VersaoProjeto versao, String novoNomeArquivo) {
        versao.setNomeArquivo(novoNomeArquivo);
        String novoTipo = novoNomeArquivo.contains(".") ? novoNomeArquivo.substring(novoNomeArquivo.lastIndexOf(".") + 1) : "desconhecido";
        versao.setTipoArquivo(novoTipo);
        versaoProjetoDAO.update(versao);
    }

    public void excluirVersao(Projeto projeto, VersaoProjeto versao) {
        projeto.getVersoes().remove(versao);
        projetoDAO.update(projeto);
    }
}