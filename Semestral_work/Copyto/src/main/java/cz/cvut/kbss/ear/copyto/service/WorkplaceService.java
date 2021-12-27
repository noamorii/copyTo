package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.VersionDao;
import cz.cvut.kbss.ear.copyto.dao.WorkplaceDao;
import cz.cvut.kbss.ear.copyto.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkplaceService {

    private final WorkplaceDao workplaceDao;
    private final VersionDao versionDao;

    @Autowired
    public WorkplaceService(WorkplaceDao workplaceDao, VersionDao versionDao){
        this.workplaceDao = workplaceDao;
        this.versionDao = versionDao;
    }
}
