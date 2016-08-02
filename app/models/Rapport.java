package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rapport")
public class Rapport implements Serializable {


    private final String QUERRY_LIST = "select rapport From Rapport rapport";
    private final String QUERRY = "select code_rapport From bi.tb_requette_mdx where rapport_code=:code and grpe=:grpe";
    @Transient
    private final String QUERRY_FINDbyCODE = "select rapport From Rapport rapport WHERE rapport.codeRapport = :codeRapport";


    @Id
    private Long id;                                        // id = codeComposante (trigger)
    @Column(name = "code_rapport", unique = true, nullable = false, length = 2)
    private String codeRapport;
    @Column(name = "libelle_rapport", unique = true, nullable = false, length = 256)
    private String libelleRapport;
    @Column(name = "template", unique = true, nullable = false, length = 256)
    private String template;
    @Column(name = "requette_mdx", unique = true, length = 256)
    private String requetteMdx;
    @Column(name = "grp", unique = true, nullable = false)
    private Long grp;
    @Column(name = "tags")
    private String tags;
    @Column(name = "tmp_query")
    private String tmpQuery;
    @Column(name = "cube")
    private String cube;

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

    public ResultSet findByCodeAGroupe(String code_rapport, Long grpe) {

        return (ResultSet) JPA.em().createNativeQuery(QUERRY).setParameter("code", code_rapport).setParameter("grpe", grpe).getSingleResult();


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

    public Map<String, String> options() {
        @SuppressWarnings("unchecked")
        List<Rapport> rapports = JPA.em().createQuery(QUERRY_LIST).getResultList();
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (Rapport c : rapports) {
            options.put(c.codeRapport, c.getLibelleRapport());

        }
        return options;
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

    public String getRequetteMdx() {
        return requetteMdx;
    }

    public void setRequetteMdx(String requetteMdx) {
        this.requetteMdx = requetteMdx;
    }

    public Long getGrp() {
        return grp;
    }

    public void setGrp(Long grp) {
        this.grp = grp;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTmpQuery() {
        return tmpQuery;
    }

    public void setTmpQuery(String tmp_query) {
        this.tmpQuery = tmp_query;
    }

    public String getCube() {
        return cube;
    }

    public void setCube(String cube) {
        this.cube = cube;
    }


}
