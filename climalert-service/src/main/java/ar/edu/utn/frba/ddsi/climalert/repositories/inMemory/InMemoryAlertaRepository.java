package ar.edu.utn.frba.ddsi.climalert.repositories.inMemory;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.repositories.AlertaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAlertaRepository implements AlertaRepository {

    private final ObjectMapper objectMapper;
    private final Path storagePath;
    private final List<Alerta> alertas;

    public InMemoryAlertaRepository(
            ObjectMapper objectMapper,
            @Value("${climalert.storage.alerta-file:data/alertas.json}") String storagePath
    ) {
        this.objectMapper = objectMapper;
        this.storagePath = Path.of(storagePath);
        this.alertas = this.cargar();
    }

    @Override
    public synchronized Alerta guardar(Alerta alerta) {
        this.alertas.add(alerta);
        this.persistir();
        return alerta;
    }

    @Override
    public synchronized List<Alerta> buscarTodas() {
        return new ArrayList<>(this.alertas);
    }

    @Override
    public synchronized Optional<Alerta> buscarPorCodigo(String codigo) {
        return this.alertas.stream()
                .filter(alerta -> alerta.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public synchronized void eliminar(String codigo) {
        this.alertas.removeIf(alerta -> alerta.getCodigo().equals(codigo));
        this.persistir();
    }

    private List<Alerta> cargar() {
        if (Files.notExists(this.storagePath)) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = Files.newInputStream(this.storagePath)) {
            List<Alerta> registros = this.objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Alerta>>() {}
            );
            return new ArrayList<>(registros);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudieron cargar las alertas", exception);
        }
    }

    private void persistir() {
        try {
            Path parent = this.storagePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (OutputStream outputStream = Files.newOutputStream(this.storagePath)) {
                this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, this.alertas);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudieron persistir las alertas", exception);
        }
    }
}
