package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Contract.findAllByClient", query = "SELECT c FROM Contract c WHERE :client = c.client"),
        @NamedQuery(name = "Contract.findAllByCopywriter", query = "SELECT c FROM Contract c WHERE :copywriter = c.copywriter"),
        @NamedQuery(name = "Contract.findByOrder", query = "SELECT c FROM Contract c WHERE :order = c.order"),
        @NamedQuery(name = "Contract.findAllByDateOfAgreement", query = "SELECT c FROM Contract c WHERE :dateOfAgreement = c.dateOfAgreement"),
        @NamedQuery(name = "Contract.findAllByDate", query = "SELECT c FROM Contract c WHERE :date = c.date")
})
public class Contract extends AbstractEntity{

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User client;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User copywriter;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Order order;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date dateOfAgreement = new Date();

    // TODO aby pri zmene deadlinu u objednavky zustalo zachovano puvodni domluvene datum
    @Basic(optional = false)
    @Column(nullable = true)
    private Date date;

    // TODO aby pri zmene castky u objednavky zustalo zachovano puvodni domluvena castka
    @Basic(optional = false)
    @Column(nullable = false)
    private double price;

    @Basic(optional = false)
    @Column(nullable = true)
    private double penalty;
}
