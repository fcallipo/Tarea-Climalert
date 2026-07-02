package ar.edu.utn.frba.ddsi.climalert.repositories;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;

import java.util.List;
import java.util.Optional;

public interface AlertaRepository {

    Alerta guardar(Alerta alerta);

    List<Alerta> buscarTodas();

    Optional<Alerta> buscarPorCodigo(String codigo);

    void eliminar(String codigo);
}