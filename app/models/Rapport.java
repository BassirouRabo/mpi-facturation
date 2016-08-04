package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rapport")
public class Rapport implements Serializable {

    @Transient
    private final String QUERRY_LIST = "select rapport From Rapport rapport";
    @Transient
    private final String QUERRY_FINDbyCODE = "select rapport From Rapport rapport WHERE rapport.codeRapport = :codeRapport";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code_rapport", unique = true, nullable = false, length = 2)
    private String codeRapport;
    @Column(name = "libelle_rapport", unique = true, nullable = false, length = 256)
    private String libelleRapport;
    @Column(name = "template", unique = true, nullable = false, length = 256)
    private String template;
    @Column(name = "tmp_query")
    private String tmpQuery;

    /**
     * Liste des activite
     *
     * @return
     */
    public List<Rapport> findList() {
        try {
            return JPA.em().createQuery(QUERRY_LIST).getResultList();
        } catch (Exception e) {
           
            return null;
        }
    }

    public Rapport detail(Long id) {
        try {
            return (Rapport) JPA.em().createQuery("select rapport From Rapport rapport WHERE rapport.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Rapport findByCodeRapport(String codeRapport) {
        try {
            return (Rapport) JPA.em().createQuery(QUERRY_FINDbyCODE).setParameter("codeRapport", codeRapport).getSingleResult();
        } catch (Exception e) {
           
            return null;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRapport() {
        return codeRapport;
    }

    public void setCodeRapport(String codeRapport) {
        this.codeRapport = codeRapport;
    }

    public String getLibelleRapport() {
        return libelleRapport;
    }

    public void setLibelleRapport(String libelleRapport) {
        this.libelleRapport = libelleRapport;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTmpQuery() {
        return tmpQuery;
    }

    public void setTmpQuery(String tmp_query) {
        this.tmpQuery = tmp_query;
    }

}
