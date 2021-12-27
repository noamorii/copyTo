package cz.cvut.kbss.ear.copyto.dao;

import cz.cvut.kbss.ear.copyto.model.Version;
import cz.cvut.kbss.ear.copyto.model.Workplace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkplaceDao extends BaseDao<Workplace> {

    protected WorkplaceDao() {
        super(Workplace.class);
    }

    public List<Version> findAllVersion(){
        return em.createNamedQuery("Workplace.findAllVersions", Version.class)
                .getResultList();
    }

    public List<Workplace> findEditable(){
        return em.createNamedQuery("Workplace.findEditable", Workplace.class)
                .getResultList();
    }
}
