package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import org.springframework.security.access.AccessDeniedException;
import java.util.List;



@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    TokenService tokenService;

    @Value("${spring.secretkey}")
    private String secret;


    public LoginResponse validarUsuarioAutenticadoeRetornaToken(LoginRequest request) {


        if (usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }

        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return usuarioRepository.findAll().stream().map(UsuarioResponse::new).toList();
        }
        return usuarioRepository.getUsuarioByEmpresa_id(usuarioLogado.empresa_id()).stream().map(UsuarioResponse::new).toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) throws AccessDeniedException {

        if (criarAdminRequest.secretKey() == null ||!criarAdminRequest.secretKey().equals(secret)) {
           throw new AccessDeniedException( "Chave de adminstrador inválida");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuario Salvo com sucesso");
    }


    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) throws AccessDeniedException {


        //Novo Usuario
        if (id == 0L) {
            Usuario usuarioBanco = new Usuario(usuario);
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        } else {
            //Usuario existente; só o dono ou um ADMIN altera
            var logado = getUsuarioLogado();
            boolean admin = logado.authorities().stream().anyMatch(a ->a.getAuthority().equals("ROLE_ADMIN"));
            if(!admin && !logado.id().equals(id)){
                throw new AccessDeniedException("Voce só pode alterar o seu próprio cadastro");
            }
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if (usuarioBanco != null) {
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(usuario.senha());
                usuarioRepository.save(usuarioBanco);
                return new UsuarioResponse(usuarioBanco);
            }

        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);


    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if (usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;

    }

    public boolean entrarsessao(Long id, EntrarSessaoRequest entrarSessaoRequest) {
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        Sessao sessaoBanco = sessaoRepository.findById(entrarSessaoRequest.sessao_id()).orElse(null);
        if (usuarioBanco != null && sessaoBanco != null) {

            var sessao = usuarioBanco.getSessoes().stream().anyMatch(u -> u.getId().equals(sessaoBanco.getId()));

            if (sessao) {
                return true;

            } else {
                usuarioBanco.getSessoes().add(sessaoBanco);
                usuarioRepository.save(usuarioBanco);

                return true;
            }

        }
        return false;

    }

}
