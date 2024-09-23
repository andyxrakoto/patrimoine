package Repository;

import Model.Patrimoine;
import org.springframework.stereotype.Repository;
import java.io.IOException;


@Repository
public interface RepositoryInterface {

    Patrimoine updatePatrimoine(int Id, Patrimoine patrimoine) throws IOException;
    Patrimoine getPatrimoine(int id) throws IOException;

}
