package Service;

import Model.Patrimoine;
import Repository.RepositoryInterface;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

@Service
public class PatrimoineService implements RepositoryInterface {

    private final Path filePath = Paths.get("patrimoines.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Patrimoine> readPatrimoinesFromFile() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        String content = Files.readString(filePath);
        return objectMapper.readValue(content, new TypeReference<List<Patrimoine>>() {});
    }

    private void writePatrimoinesToFile(List<Patrimoine> patrimoines) throws IOException {
        String content = objectMapper.writeValueAsString(patrimoines);
        Files.writeString(filePath, content);
    }

    @Override
    public Patrimoine updatePatrimoine(int id, Patrimoine patrimoineDetails) throws IOException {
        List<Patrimoine> patrimoinesListes = readPatrimoinesFromFile();
        if(id >= 0 && id < patrimoinesListes.size()) {
            Patrimoine patrimoine =  patrimoinesListes.get(id);
            patrimoine.setPossesseur(patrimoineDetails.getPossesseur());
            patrimoine.setDerniereModification(LocalDateTime.now());
            writePatrimoinesToFile(patrimoinesListes);
            return patrimoine;
        }
        patrimoineDetails.setDerniereModification(LocalDateTime.now());
        if (id == patrimoinesListes.size()) {
            patrimoinesListes.add(patrimoineDetails);
        } else if (id > patrimoinesListes.size()) {
            while (patrimoinesListes.size() < id) {
                patrimoinesListes.add(new Patrimoine(5, "Jane Doe", LocalDateTime.now()));
            }
            patrimoinesListes.add(patrimoineDetails);
        }

        writePatrimoinesToFile(patrimoinesListes);
        return patrimoineDetails;

    }


    public Patrimoine getPatrimoine(int id) throws IOException {
        List<Patrimoine> patrimoines = readPatrimoinesFromFile();
        if (id >= 0 && id < patrimoines.size()) {
            return patrimoines.get(id);
        } else {
            throw new IndexOutOfBoundsException("Doesn't exists");
        }
    }

}