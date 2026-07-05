package ar.edu.utn.frba.ddsi.climalert.repositories.inMemory;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.RegistroClimaRepository;
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
public class InMemoryRegistroClimaRepository implements RegistroClimaRepository {

    private final ObjectMapper objectMapper;
    private final Path storagePath;
    private final List<RegistroClima> registrosClima;

    public InMemoryRegistroClimaRepository(
            ObjectMapper objectMapper,
            @Value("${climalert.storage.clima-file:data/clima-registros.json}") String storagePath
    ) {
        this.objectMapper = objectMapper;
        this.storagePath = Path.of(storagePath);
        this.registrosClima = this.cargar();
    }

    @Override
    public synchronized RegistroClima guardar(RegistroClima registroClima) {
        this.registrosClima.add(registroClima);
        this.persistir();
        return registroClima;
    }

    @Override
    public synchronized List<RegistroClima> buscarTodos() {
        return new ArrayList<>(this.registrosClima);
    }

    @Override
    public synchronized Optional<RegistroClima> buscarUltimo() {
        if (this.registrosClima.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(this.registrosClima.get(this.registrosClima.size() - 1));
    }

    @Override
    public synchronized Optional<RegistroClima> buscarPorCodigo(String codigo) {
        return this.registrosClima.stream()
                .filter(registro -> registro.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public synchronized void eliminar(String codigo) {
        this.registrosClima.removeIf(registro -> registro.getCodigo().equals(codigo));
        this.persistir();
    }

    private List<RegistroClima> cargar() {
        if (Files.notExists(this.storagePath)) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = Files.newInputStream(this.storagePath)) {
            List<RegistroClima> registros = this.objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<RegistroClima>>() {}
            );
            return new ArrayList<>(registros);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudieron cargar los registros climáticos", exception);
        }
    }

    private void persistir() {
        try {
            Path parent = this.storagePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (OutputStream outputStream = Files.newOutputStream(this.storagePath)) {
                this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, this.registrosClima);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudieron persistir los registros climáticos", exception);
        }
    }
}
