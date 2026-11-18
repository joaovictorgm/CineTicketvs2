package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Empresa;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cineticket_pro.application.DTOs.EnumStatusUsuario;



import org.springframework.security.access.AccessDeniedException;
import java.util.List;



@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    TokenService tokenService;

    @Value("${spring.secretkey}")
    private String secret;
    @Autowired
    private EmpresaRepository empresaRepository;


    public LoginResponse validarUsuarioAutenticadoeRetornaToken(LoginRequest request) {

          String email = new Email(request.email()).toString();
          var usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null && passwordEncoder.matches(request.senha(),usuario.getSenha())) {

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




    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) throws AccessDeniedException {

        var logado = getUsuarioLogado();
        boolean gerente = logado.authorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE"));
        boolean admin   = logado.authorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        //Novo Usuario
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
            usuarioBanco.setEmpresa(empresaRepository.getReferenceById(logado.empresa_id()));  // empresa do token
            usuarioBanco.setRole(gerente ? "ROLE_ADMIN" : "ROLE_USER");                        // gerente→ADMIN, admin→USER
            usuarioBanco.setStatus(usuario.status() != null ? usuario.status() : EnumStatusUsuario.ATIVO);
            usuarioBanco.setSenha(passwordEncoder.encode(usuario.senha()));
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);

        } else {
            //Usuario existente; só o dono, um ADMIN ou o GERENTE altera
            if (!gerente && !admin && !logado.id().equals(id)) {
                throw new AccessDeniedException("Voce só pode alterar o seu próprio cadastro");
            }

            Usuario usuarioBanco = usuarioRepository.findByIdAndEmpresaId(id, logado.empresa_id()).orElse(null);
            if (usuarioBanco != null) {
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(passwordEncoder.encode(usuario.senha()));
                usuarioRepository.save(usuarioBanco);
                return new UsuarioResponse(usuarioBanco);
            }
        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuario = usuarioRepository.findByIdAndEmpresaId(id, getUsuarioLogado().empresa_id()).orElse(null);
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

    public PerfilResponse buscarUsuarioLogado() {
        var logado = getUsuarioLogado();
        boolean gerente = logado.authorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE"));

        if (gerente) {
            var g = gerenteRepository.findById(logado.id())
                    .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
            return new PerfilResponse(g.getId(), g.getNome(), g.getEmail(), "ROLE_GERENTE");
        }

        var u = usuarioRepository.findById(logado.id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new PerfilResponse(u.getId(), u.getNome(), u.getEmail(), u.getRole());
    }

}
