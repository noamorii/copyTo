package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.Version;
import cz.cvut.kbss.ear.copyto.model.Workplace;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/versions")
public class VersionController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(WorkplaceController.class);

    private final WorkplaceService workplaceService;

    @Autowired
    public VersionController(WorkplaceService workplaceService){
        this.workplaceService = workplaceService;
    }

    // --------------------CREATE--------------------------------------

    //TODO
    @PostMapping(value = "workplace-id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVersion(@PathVariable Integer id, @RequestBody Version version) {
        Workplace workplace = workplaceService.findWorkplace(id);

        workplaceService.addVersion(version, workplace);
        LOG.debug("created version {}.", version);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", version.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Version getById(@PathVariable Integer id) {
        final Version version = workplaceService.findVersion(id);
        if (version == null) {
            throw NotFoundException.create("Version", id);
        } return version;
    }

    //@PostFilter("hasRole('ADMIN')") // TODO
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getVersions() {
        return workplaceService.findVersions();
    }

    // TODO correct format pro date?
    /*@GetMapping(value = "/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getByDate(@PathVariable Date date) {
        final List<Version> versions = workplaceService.findVersion(date);
        if (versions == null) {
            throw NotFoundException.create("Version", date);
        } return versions;
    }*/

    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getByTitle(@PathVariable String title) {
        final List<Version> versions = workplaceService.findVersion(title);
        if (versions == null) {
            throw NotFoundException.create("Version", title);
        } return versions;
    }

    // --------------------UPDATE--------------------------------------

    // TODO filter + otazka proc to nefunguje s tim equals
    @PutMapping(value = "/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody Version version) {
        final Version original = workplaceService.findVersion(id);
/*        if(!original.getId().equals(version.getId())){
            throw new ValidationException("Version identifier in the data does not match the one in the request URL.");
        }*/
        original.setTitle(version.getTitle());
        original.setText(version.getText());
        workplaceService.update(version);
    }

    // --------------------DELETE--------------------------------------

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVersion(@PathVariable Integer id){
        final Version toRemove = workplaceService.findVersion(id);

        //TODO find workplace by version a nahradit
        final List<Workplace> workplaces = workplaceService.findWorkplaces();
        for(Workplace w : workplaces){
            if(w.getVersions().contains(toRemove)){
                workplaceService.remove(w, toRemove);
                break;
            }
        }
        LOG.debug("Removed version {}.", toRemove);
    }
}
