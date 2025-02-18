package memento;

import java.util.ArrayList;
import java.util.List;

class Memento {
    private final String mundo;
    private final int nivel;
    private final int energia;
    private final List<String> habilidades;

    public Memento(String mundo, int nivel, int energia, List<String> habilidades) {
        this.mundo = mundo;
        this.nivel = nivel;
        this.energia = energia;
        this.habilidades = new ArrayList<>(habilidades);
    }

    public String getMundo() { return mundo; }
    public int getNivel() { return nivel; }
    public int getEnergia() { return energia; }
    public List<String> getHabilidades() { return new ArrayList<>(habilidades); }
}

class Jogador {
    private String mundo;
    private int nivel;
    private int energia;
    private List<String> habilidades;

    public Jogador(String mundo, int nivel, int energia) {
        this.mundo = mundo;
        this.nivel = nivel;
        this.energia = energia;
        this.habilidades = new ArrayList<>();
    }

    public void desbloquearHabilidade(String habilidade) {
        habilidades.add(habilidade);
    }

    public void mostrarStatus() {
        System.out.println("Mundo: " + mundo + " | Nível: " + nivel + " | Energia: " + energia);
        System.out.println("Habilidades: " + habilidades);
    }

    public Memento salvarProgresso() {
        return new Memento(mundo, nivel, energia, habilidades);
    }

    public void restaurarProgresso(Memento memento) {
        this.mundo = memento.getMundo();
        this.nivel = memento.getNivel();
        this.energia = memento.getEnergia();
        this.habilidades = memento.getHabilidades();
    }

    public void setEstado(String mundo, int nivel, int energia) {
        this.mundo = mundo;
        this.nivel = nivel;
        this.energia = energia;
    }
}

class Caretaker {
    private List<Memento> checkpoints = new ArrayList<>();

    public void salvarCheckpoint(Memento memento) {
        checkpoints.add(memento);
    }

    public Memento carregarUltimoCheckpoint() {
        if (!checkpoints.isEmpty()) {
            return checkpoints.get(checkpoints.size() - 1);
        }
        return null;
    }
}

