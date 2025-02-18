package mediator;

import java.util.ArrayList;
import java.util.List;

interface Chat {
    void registrarUsuario(Usuario usuario);
    void enviarMensagem(String mensagem, Usuario remetente);
}

class ChatMediatorImpl implements Chat {
    private List<Usuario> usuarios;

    public ChatMediatorImpl() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public void enviarMensagem(String mensagem, Usuario remetente) {
        for (Usuario usuario : usuarios) {
            if (usuario != remetente) {
                usuario.receberMensagem(mensagem, remetente);
            }
        }
    }
}

abstract class Usuario {
    protected Chat mediator;
    protected String nome;

    public Usuario(Chat mediator, String nome) {
        this.mediator = mediator;
        this.nome = nome;
    }

    public abstract void enviarMensagem(String mensagem);
    public abstract void receberMensagem(String mensagem, Usuario remetente);
}

class UsuarioConcreto extends Usuario {
    public UsuarioConcreto(Chat mediator, String nome) {
        super(mediator, nome);
    }

    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println(this.nome + " enviou: " + mensagem);
        mediator.enviarMensagem(mensagem, this);
    }

    @Override
    public void receberMensagem(String mensagem, Usuario remetente) {
        System.out.println(this.nome + " recebeu de " + remetente.nome + ": " + mensagem);
    }
}
