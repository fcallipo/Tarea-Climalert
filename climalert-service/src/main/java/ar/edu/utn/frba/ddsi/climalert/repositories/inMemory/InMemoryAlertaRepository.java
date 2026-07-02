package ar.edu.utn.frba.ddsi.climalert.repositories.inMemory;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.repositories.AlertaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAlertaRepository implements AlertaRepository {

    private final List<Alerta> alertas;

    public InMemoryAlertaRepository() {
        this.alertas = new ArrayList<>();
    }

    @Override
    public Alerta guardar(Alerta alerta) {
        this.alertas.add(alerta);
        return alerta;
    }

    @Override
    public List<Alerta> buscarTodas() {
        return this.alertas;
    }

    @Override
    public Optional<Alerta> buscarPorCodigo(String codigo) {
        return this.alertas.stream()
                .filter(alerta -> alerta.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public void eliminar(String codigo) {
        this.alertas.removeIf(alerta -> alerta.getCodigo().equals(codigo));
    }
}