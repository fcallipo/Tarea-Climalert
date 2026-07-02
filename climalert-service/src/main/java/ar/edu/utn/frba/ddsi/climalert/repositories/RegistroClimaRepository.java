package ar.edu.utn.frba.ddsi.climalert.repositories;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;

import java.util.List;
import java.util.Optional;

public interface RegistroClimaRepository {

    RegistroClima guardar(RegistroClima registroClima);

    List<RegistroClima> buscarTodos();

    Optional<RegistroClima> buscarUltimo();

    Optional<RegistroClima> buscarPorCodigo(String codigo);

    void eliminar(String codigo);
}