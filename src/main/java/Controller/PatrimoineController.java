package Controller;

import Model.Patrimoine;
import Service.PatrimoineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> updateOrCreatePatrimoine(@PathVariable int id, @RequestBody Patrimoine patrimoine) {
        try {
            Patrimoine updatedPatrimoine = patrimoineService.updatePatrimoine(id, patrimoine);
            HttpStatus status = (id < patrimoineService.readPatrimoinesFromFile().size()) ? HttpStatus.OK : HttpStatus.CREATED;
            return new ResponseEntity<>(updatedPatrimoine, status);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable int id) {
        try {
            Patrimoine patrimoine = patrimoineService.getPatrimoine(id);
            if (patrimoine != null) {
                return ResponseEntity.ok(patrimoine);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Patrimoine> getAllPatrimoine(int id) {
        return null;
    }
}
