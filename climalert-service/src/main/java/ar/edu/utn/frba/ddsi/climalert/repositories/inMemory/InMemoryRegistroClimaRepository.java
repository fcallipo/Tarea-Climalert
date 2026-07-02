package ar.edu.utn.frba.ddsi.climalert.repositories.inMemory;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.RegistroClimaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRegistroClimaRepository implements RegistroClimaRepository {

    private final List<RegistroClima> registrosClima;

    public InMemoryRegistroClimaRepository() {
        this.registrosClima = new ArrayList<>();
    }

    @Override
    public RegistroClima guardar(RegistroClima registroClima) {
        this.registrosClima.add(registroClima);
        return registroClima;
    }

    @Override
    public List<RegistroClima> buscarTodos() {
        return this.registrosClima;
    }

    @Override
    public Optional<RegistroClima> buscarUltimo() {
        if (this.registrosClima.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(this.registrosClima.getLast());
    }

    @Override
    public Optional<RegistroClima> buscarPorCodigo(String codigo) {
        return this.registrosClima.stream()
                .filter(registro -> registro.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public void eliminar(String codigo) {
        this.registrosClima.removeIf(registro -> registro.getCodigo().equals(codigo));
    }
}