package com.example.cineticket_pro.application.service;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.SenhaPort;
import com.example.cineticket_pro.application.port.UsuarioAutenticadoPort;
import com.example.cineticket_pro.domain.entities.EnumStatusUsuario;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SenhaPort senhaPort;

    @Autowired
    private UsuarioAutenticadoPort usuarioAutenticado;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmpresaRepository empresaRepository;

    public LoginResponse validarUsuarioAutenticadoeRetornaToken(LoginRequest request) {
        String email = new Email(request.email()).toString();
        var usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario != null
                && usuario.getStatus() != EnumStatusUsuario.EXCLUIDO
                && senhaPort.conferir(request.senha(), usuario.getSenha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {
        Long empresaId = usuarioAutenticado.empresaId();

        if (empresaId == null) {
            throw new AccessDeniedException("Usuário sem empresa associada");
        }
        return usuarioRepository.getUsuarioByEmpresa_id(empresaId)
                .stream().map(UsuarioResponse::new).toList();
    }

    public UsuarioLogado getUsuarioLogado() {
        return usuarioAutenticado.usuarioLogado();
    }


    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) throws AccessDeniedException {

        var logado = usuarioAutenticado.usuarioLogado();
        boolean gerente = usuarioAutenticado.Gerente();
        boolean admin = usuarioAutenticado.Admin();

        // Novo usuário
        if (id == 0L) {
            if (!gerente && !admin) {
                throw new AccessDeniedException("Sem permissão para cadastrar usuário");
            }

            String email = new Email(usuario.email()).toString();
            if (usuarioRepository.findByEmail(email).isPresent()
                    || gerenteRepository.findByEmail(email).isPresent()) {
                throw new IllegalArgumentException("Email já cadastrado");
            }

            Usuario usuarioBanco = new Usuario(usuario);
            usuarioBanco.setEmpresa(empresaRepository.getReferenceById(logado.empresa_id())); // empresa do token
            usuarioBanco.setRole(gerente ? "ROLE_ADMIN" : "ROLE_USER");                       // gerente→ADMIN, admin→USER
            usuarioBanco.setSenha(senhaPort.codificar(usuario.senha()));
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        }

        // Usuário existente: só o dono, um ADMIN ou o GERENTE altera
        if (!gerente && !admin && !logado.id().equals(id)) {
            throw new AccessDeniedException("Você só pode alterar o seu próprio cadastro");
        }

        Usuario usuarioBanco = usuarioRepository.findByIdAndEmpresaId(id, logado.empresa_id()).orElse(null);
        if (usuarioBanco != null) {
            usuarioBanco.setNome(usuario.nome());

            String novoEmail = new Email(usuario.email()).toString();
            if (!novoEmail.equals(usuarioBanco.getEmail())) {
                if (usuarioRepository.findByEmail(novoEmail).isPresent()
                        || gerenteRepository.findByEmail(novoEmail).isPresent()) {
                    throw new IllegalArgumentException("Email já cadastrado");
                }
                usuarioBanco.setEmail(novoEmail);
            }

            if (usuario.senha() != null && !usuario.senha().isBlank()) {
                usuarioBanco.setSenha(senhaPort.codificar(usuario.senha()));
            }
            if (usuario.tipoCliente() != null && (gerente || admin)) {
                usuarioBanco.setTipoCliente(usuario.tipoCliente());
            }

            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {
        var usuario = usuarioRepository
                .findByIdAndEmpresaId(id, usuarioAutenticado.empresaId())
                .orElse(null);
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponse(usuario);
    }


    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {
        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresaId(id, usuarioAutenticado.empresaId())
                .orElse(null);
        if (usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;
    }


    public boolean entrarsessao(Long id, EntrarSessaoRequest entrarSessaoRequest) {
        var logado = usuarioAutenticado.usuarioLogado();
        boolean gerente = usuarioAutenticado.Gerente();
        boolean admin = usuarioAutenticado.Admin();

        if (!gerente && !admin && !logado.id().equals(id)) {
            throw new AccessDeniedException("Você só pode entrar em uma sessão com o seu próprio usuário");
        }

        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresaId(id, logado.empresa_id()).orElse(null);
        Sessao sessaoBanco = sessaoRepository
                .findByIdAndFilmeEmpresaId(entrarSessaoRequest.sessao_id(), logado.empresa_id()).orElse(null);

        if (usuarioBanco != null && sessaoBanco != null) {
            boolean estaNaSessao = usuarioBanco.getSessoes().stream()
                    .anyMatch(s -> s.getId().equals(sessaoBanco.getId()));

            if (estaNaSessao) {
                usuarioBanco.getSessoes().add(sessaoBanco);
                usuarioRepository.save(usuarioBanco);
            }
            return true;
        }
        return false;
    }

    public PerfilResponse buscarUsuarioLogado() {
        var logado = usuarioAutenticado.usuarioLogado();

        if (usuarioAutenticado.Gerente()) {
            var gerente = gerenteRepository.findById(logado.id())
                    .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
            return new PerfilResponse(gerente.getId(), gerente.getNome(), gerente.getEmail(), "ROLE_GERENTE");
        }

        var usuario = usuarioRepository.findById(logado.id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new PerfilResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }
}