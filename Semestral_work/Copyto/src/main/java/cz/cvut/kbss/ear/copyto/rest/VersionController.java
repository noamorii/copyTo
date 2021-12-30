package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    //TODO opravneny copywriter + vlastnik
    @PostMapping(value = "workplace-id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVersion(@PathVariable Integer id, @RequestBody Version version) {
        Workplace workplace = workplaceService.findWorkplace(id);

        workplaceService.addVersion(version, workplace);
        LOG.debug("created version {}.", version);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", version.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    //TODO opravneny copywriter + vlastnik
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Version getById(@PathVariable Integer id) {
        final Version version = workplaceService.findVersion(id);
        if (version == null) {
            throw NotFoundException.create("Version", id);
        } return version;
    }

    // TODO vlastnik, povereny copywriter + admin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getVersions() {
        return workplaceService.findVersions();
    }

    // TODO vlastnik, povereny copywriter + admin
    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getByTitle(@PathVariable String title) {
        final List<Version> versions = workplaceService.findVersion(title);
        if (versions == null) {
            throw NotFoundException.create("Version", title);
        } return versions;
    }

    // --------------------UPDATE--------------------------------------

    // copywriter + vlastnik
    @PutMapping(value = "/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody Version version) {
        final Version original = workplaceService.findVersion(id);
/*        if(!original.getId().equals(version.getId())){ // todo zitra checkni
            throw new ValidationException("Version identifier in the data does not match the one in the request URL.");
        }*/
        original.setTitle(version.getTitle());
        original.setText(version.getText());
        workplaceService.update(version);
    }

    // todo vlastnik + copywriter
    @PutMapping(value = "/id-clear/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearVersion(@PathVariable Integer id) {
        final Version original = workplaceService.findVersion(id);
        workplaceService.clear(original);
        workplaceService.update(original);
    }

    // todo vlastnik + copywriter
    @PutMapping(value = "/id/{id}/text/{text}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateText(@PathVariable Integer id, @PathVariable String text) {
        final Version original = workplaceService.findVersion(id);
        workplaceService.editText(original,text);
    }

    // todo vlastnik + copywriter
    @PutMapping(value = "/id/{id}/title/{title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTitle(@PathVariable Integer id, @PathVariable String title) {
        final Version original = workplaceService.findVersion(id);
        workplaceService.editTitle(original,title);
    }

    // --------------------DELETE--------------------------------------

    @PreAuthorize("hasRole('ADMIN')") // + todo vlastnik, copywriter
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
