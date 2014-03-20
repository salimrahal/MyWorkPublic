/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salim
 */
@Entity
@Table(name = "Fiche_Descriptive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FicheDescriptive.findAll", query = "SELECT f FROM FicheDescriptive f"),
    @NamedQuery(name = "FicheDescriptive.findByIdFicheDescriptive", query = "SELECT f FROM FicheDescriptive f WHERE f.idFicheDescriptive = :idFicheDescriptive"),
    @NamedQuery(name = "FicheDescriptive.findByNomCandidat", query = "SELECT f FROM FicheDescriptive f WHERE f.nomCandidat = :nomCandidat"),
    @NamedQuery(name = "FicheDescriptive.findByNoDossier", query = "SELECT f FROM FicheDescriptive f WHERE f.noDossier = :noDossier"),
    @NamedQuery(name = "FicheDescriptive.findByFiliere", query = "SELECT f FROM FicheDescriptive f WHERE f.filiere = :filiere"),
    @NamedQuery(name = "FicheDescriptive.findByDateValidationFiche", query = "SELECT f FROM FicheDescriptive f WHERE f.dateValidationFiche = :dateValidationFiche"),
    @NamedQuery(name = "FicheDescriptive.findByTypeProjetSiIngenieur", query = "SELECT f FROM FicheDescriptive f WHERE f.typeProjetSiIngenieur = :typeProjetSiIngenieur"),
    @NamedQuery(name = "FicheDescriptive.findByThematique", query = "SELECT f FROM FicheDescriptive f WHERE f.thematique = :thematique"),
    @NamedQuery(name = "FicheDescriptive.findByTitre", query = "SELECT f FROM FicheDescriptive f WHERE f.titre = :titre"),
    @NamedQuery(name = "FicheDescriptive.findByMotsCles", query = "SELECT f FROM FicheDescriptive f WHERE f.motsCles = :motsCles"),
    @NamedQuery(name = "FicheDescriptive.findByCahierDesCharges", query = "SELECT f FROM FicheDescriptive f WHERE f.cahierDesCharges = :cahierDesCharges"),
    @NamedQuery(name = "FicheDescriptive.findByTuteurResponsable", query = "SELECT f FROM FicheDescriptive f WHERE f.tuteurResponsable = :tuteurResponsable"),
    @NamedQuery(name = "FicheDescriptive.findByTuteurAssocie", query = "SELECT f FROM FicheDescriptive f WHERE f.tuteurAssocie = :tuteurAssocie"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireNomEntreprise", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireNomEntreprise = :partenaireNomEntreprise"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireRepresentant", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireRepresentant = :partenaireRepresentant"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireResponsabletechnique", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireResponsabletechnique = :partenaireResponsabletechnique"),
    @NamedQuery(name = "FicheDescriptive.findByPartenairePosteCandidat", query = "SELECT f FROM FicheDescriptive f WHERE f.partenairePosteCandidat = :partenairePosteCandidat"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireAddresse", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireAddresse = :partenaireAddresse"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireTelephone", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireTelephone = :partenaireTelephone"),
    @NamedQuery(name = "FicheDescriptive.findByPartenaireEmail", query = "SELECT f FROM FicheDescriptive f WHERE f.partenaireEmail = :partenaireEmail"),
    @NamedQuery(name = "FicheDescriptive.findByClientNom", query = "SELECT f FROM FicheDescriptive f WHERE f.clientNom = :clientNom"),
    @NamedQuery(name = "FicheDescriptive.findByClientRepresentant", query = "SELECT f FROM FicheDescriptive f WHERE f.clientRepresentant = :clientRepresentant"),
    @NamedQuery(name = "FicheDescriptive.findByLieuRealisation", query = "SELECT f FROM FicheDescriptive f WHERE f.lieuRealisation = :lieuRealisation"),
    @NamedQuery(name = "FicheDescriptive.findByVisaChefDepInsc", query = "SELECT f FROM FicheDescriptive f WHERE f.visaChefDepInsc = :visaChefDepInsc"),
    @NamedQuery(name = "FicheDescriptive.findByDateVisaCheDepInsc", query = "SELECT f FROM FicheDescriptive f WHERE f.dateVisaCheDepInsc = :dateVisaCheDepInsc"),
    @NamedQuery(name = "FicheDescriptive.findByTuteurResponsablePart", query = "SELECT f FROM FicheDescriptive f WHERE f.tuteurResponsablePart = :tuteurResponsablePart"),
    @NamedQuery(name = "FicheDescriptive.findByTuteurAssociePart", query = "SELECT f FROM FicheDescriptive f WHERE f.tuteurAssociePart = :tuteurAssociePart"),
    @NamedQuery(name = "FicheDescriptive.findByFicheDescriptivecol", query = "SELECT f FROM FicheDescriptive f WHERE f.ficheDescriptivecol = :ficheDescriptivecol"),
    @NamedQuery(name = "FicheDescriptive.findByTypeProjetSiStage", query = "SELECT f FROM FicheDescriptive f WHERE f.typeProjetSiStage = :typeProjetSiStage"),
    @NamedQuery(name = "FicheDescriptive.findByTypeProjetNiveau", query = "SELECT f FROM FicheDescriptive f WHERE f.typeProjetNiveau = :typeProjetNiveau")})
public class FicheDescriptive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFiche_Descriptive")
    private Integer idFicheDescriptive;
    @Column(name = "NomCandidat")
    private String nomCandidat;
    @Column(name = "NoDossier")
    private String noDossier;
    @Column(name = "Filiere")
    private String filiere;
    @Column(name = "DateValidationFiche")
    @Temporal(TemporalType.DATE)
    private Date dateValidationFiche;
    @Column(name = "TypeProjet_SiIngenieur")
    private Boolean typeProjetSiIngenieur;
    @Column(name = "Thematique")
    private String thematique;
    @Column(name = "Titre")
    private String titre;
    @Column(name = "MotsCles")
    private String motsCles;
    @Column(name = "CahierDesCharges")
    private String cahierDesCharges;
    @Column(name = "TuteurResponsable")
    private String tuteurResponsable;
    @Column(name = "TuteurAssocie")
    private String tuteurAssocie;
    @Column(name = "Partenaire_Nom_Entreprise")
    private String partenaireNomEntreprise;
    @Column(name = "Partenaire_Representant")
    private String partenaireRepresentant;
    @Column(name = "Partenaire_Responsable_technique")
    private String partenaireResponsabletechnique;
    @Column(name = "Partenaire_Poste_Candidat")
    private String partenairePosteCandidat;
    @Column(name = "Partenaire_Addresse")
    private String partenaireAddresse;
    @Column(name = "Partenaire_Telephone")
    private String partenaireTelephone;
    @Column(name = "Partenaire_Email")
    private String partenaireEmail;
    @Column(name = "Client_Nom")
    private String clientNom;
    @Column(name = "Client_Representant")
    private String clientRepresentant;
    @Column(name = "Lieu_Realisation")
    private String lieuRealisation;
    @Column(name = "visa_ChefDep_Insc")
    private Boolean visaChefDepInsc;
    @Column(name = "Date_Visa_CheDep_Insc")
    @Temporal(TemporalType.DATE)
    private Date dateVisaCheDepInsc;
    @Column(name = "TuteurResponsable_Part")
    private Short tuteurResponsablePart;
    @Column(name = "TuteurAssocie_Part")
    private Short tuteurAssociePart;
    @Column(name = "Fiche_Descriptivecol")
    private String ficheDescriptivecol;
    @Column(name = "TypeProjet_SiStage")
    private Boolean typeProjetSiStage;
    @Column(name = "TypeProjet_Niveau")
    private String typeProjetNiveau;

    public FicheDescriptive() {
    }

    public FicheDescriptive(Integer idFicheDescriptive) {
        this.idFicheDescriptive = idFicheDescriptive;
    }

    public Integer getIdFicheDescriptive() {
        return idFicheDescriptive;
    }

    public void setIdFicheDescriptive(Integer idFicheDescriptive) {
        this.idFicheDescriptive = idFicheDescriptive;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getNoDossier() {
        return noDossier;
    }

    public void setNoDossier(String noDossier) {
        this.noDossier = noDossier;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public Date getDateValidationFiche() {
        return dateValidationFiche;
    }

    public void setDateValidationFiche(Date dateValidationFiche) {
        this.dateValidationFiche = dateValidationFiche;
    }

    public Boolean getTypeProjetSiIngenieur() {
        return typeProjetSiIngenieur;
    }

    public void setTypeProjetSiIngenieur(Boolean typeProjetSiIngenieur) {
        this.typeProjetSiIngenieur = typeProjetSiIngenieur;
    }

    public String getThematique() {
        return thematique;
    }

    public void setThematique(String thematique) {
        this.thematique = thematique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getCahierDesCharges() {
        return cahierDesCharges;
    }

    public void setCahierDesCharges(String cahierDesCharges) {
        this.cahierDesCharges = cahierDesCharges;
    }

    public String getTuteurResponsable() {
        return tuteurResponsable;
    }

    public void setTuteurResponsable(String tuteurResponsable) {
        this.tuteurResponsable = tuteurResponsable;
    }

    public String getTuteurAssocie() {
        return tuteurAssocie;
    }

    public void setTuteurAssocie(String tuteurAssocie) {
        this.tuteurAssocie = tuteurAssocie;
    }

    public String getPartenaireNomEntreprise() {
        return partenaireNomEntreprise;
    }

    public void setPartenaireNomEntreprise(String partenaireNomEntreprise) {
        this.partenaireNomEntreprise = partenaireNomEntreprise;
    }

    public String getPartenaireRepresentant() {
        return partenaireRepresentant;
    }

    public void setPartenaireRepresentant(String partenaireRepresentant) {
        this.partenaireRepresentant = partenaireRepresentant;
    }

    public String getPartenaireResponsabletechnique() {
        return partenaireResponsabletechnique;
    }

    public void setPartenaireResponsabletechnique(String partenaireResponsabletechnique) {
        this.partenaireResponsabletechnique = partenaireResponsabletechnique;
    }

    public String getPartenairePosteCandidat() {
        return partenairePosteCandidat;
    }

    public void setPartenairePosteCandidat(String partenairePosteCandidat) {
        this.partenairePosteCandidat = partenairePosteCandidat;
    }

    public String getPartenaireAddresse() {
        return partenaireAddresse;
    }

    public void setPartenaireAddresse(String partenaireAddresse) {
        this.partenaireAddresse = partenaireAddresse;
    }

    public String getPartenaireTelephone() {
        return partenaireTelephone;
    }

    public void setPartenaireTelephone(String partenaireTelephone) {
        this.partenaireTelephone = partenaireTelephone;
    }

    public String getPartenaireEmail() {
        return partenaireEmail;
    }

    public void setPartenaireEmail(String partenaireEmail) {
        this.partenaireEmail = partenaireEmail;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientRepresentant() {
        return clientRepresentant;
    }

    public void setClientRepresentant(String clientRepresentant) {
        this.clientRepresentant = clientRepresentant;
    }

    public String getLieuRealisation() {
        return lieuRealisation;
    }

    public void setLieuRealisation(String lieuRealisation) {
        this.lieuRealisation = lieuRealisation;
    }

    public Boolean getVisaChefDepInsc() {
        return visaChefDepInsc;
    }

    public void setVisaChefDepInsc(Boolean visaChefDepInsc) {
        this.visaChefDepInsc = visaChefDepInsc;
    }

    public Date getDateVisaCheDepInsc() {
        return dateVisaCheDepInsc;
    }

    public void setDateVisaCheDepInsc(Date dateVisaCheDepInsc) {
        this.dateVisaCheDepInsc = dateVisaCheDepInsc;
    }

    public Short getTuteurResponsablePart() {
        return tuteurResponsablePart;
    }

    public void setTuteurResponsablePart(Short tuteurResponsablePart) {
        this.tuteurResponsablePart = tuteurResponsablePart;
    }

    public Short getTuteurAssociePart() {
        return tuteurAssociePart;
    }

    public void setTuteurAssociePart(Short tuteurAssociePart) {
        this.tuteurAssociePart = tuteurAssociePart;
    }

    public String getFicheDescriptivecol() {
        return ficheDescriptivecol;
    }

    public void setFicheDescriptivecol(String ficheDescriptivecol) {
        this.ficheDescriptivecol = ficheDescriptivecol;
    }

    public Boolean getTypeProjetSiStage() {
        return typeProjetSiStage;
    }

    public void setTypeProjetSiStage(Boolean typeProjetSiStage) {
        this.typeProjetSiStage = typeProjetSiStage;
    }

    public String getTypeProjetNiveau() {
        return typeProjetNiveau;
    }

    public void setTypeProjetNiveau(String typeProjetNiveau) {
        this.typeProjetNiveau = typeProjetNiveau;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFicheDescriptive != null ? idFicheDescriptive.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FicheDescriptive)) {
            return false;
        }
        FicheDescriptive other = (FicheDescriptive) object;
        if ((this.idFicheDescriptive == null && other.idFicheDescriptive != null) || (this.idFicheDescriptive != null && !this.idFicheDescriptive.equals(other.idFicheDescriptive))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FicheDescriptive[ idFicheDescriptive=" + idFicheDescriptive + " ]";
    }
    
}
