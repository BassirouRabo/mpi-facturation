package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "telephone")
    private Long telephone;
    @Column(name = "reference", unique = true, nullable = false)
    private String reference;
    @Column(name = "email")
    private String email;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "information")
    private String information;

    /**
     * Constructeur avec paramètres
     *
     * @param nom
     * @param telephone
     * @param reference
     * @param email
     * @param adresse
     * @param information
     */
    public Client(String nom, Long telephone, String reference, String email, String adresse, String information) {
        this.nom = nom;
        this.telephone = telephone;
        this.reference = reference;
        this.email = email;
        this.adresse = adresse;
        this.information = information;
    }

    /**
     * Constructeur sans paramètres
     */
    public Client() {
    }


    /**
     * Tous les client
     *
     * @return
     */
    public List<Client> findList() {
        try {
            return JPA.em().createQuery("select client From Client client").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une client by Id
     *
     * @param id
     * @return
     */
    public Client findById(Long id) {
        try {
            return (Client) JPA.em().createQuery("select client From Client client WHERE client.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une client by reference
     *
     * @param reference
     * @return
     */
    public Client findByReference(String reference) {
        try {
            return (Client) JPA.em().createQuery("select client From Client client WHERE client.reference = :reference").setParameter("reference", reference).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une client
     *
     * @param client
     * @return
     */
    public String create(Client client) {
        Client clientExiste = findByReference(client.getReference());

        if (clientExiste != null) {
            return "Cette client existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(client);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * Modifier une client
     *
     * @param client
     * @return
     */
    public String update(Client client) {
        String result = null;

        Client clientExiste = findById(client.getId());

        if (clientExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            clientExiste.setNom(client.getNom());
            clientExiste.setEmail(client.getEmail());
            clientExiste.setTelephone(client.getTelephone());
            clientExiste.setReference(client.getReference());
            clientExiste.setInformation(client.getInformation());
            clientExiste.setAdresse(client.getAdresse());
            try {
                JPA.em().persist(clientExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une client
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Client clientExiste = findById(id);

        if (clientExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(clientExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
