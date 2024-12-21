package com.tech.gestiondestock.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  //v3 + v26 ecouter cette classe et assigner une modif sur @creationdtae et @lastmodifieddate lors de l'enregistrement dans DB + min46 https://www.youtube.com/watch?v=xqhdRrFzLFY&list=PL41m5U3u3wwl5FoM2Y5gIu1Q-Wr5ascD_&index=1
@FieldDefaults(level = AccessLevel.PRIVATE)
//v6 @Builder //v6 c un design pattern : une classe qui permet de construire un objet en exposant des methodes de meme nom des attributs et renvoyant les memes methodes et build() qui prend les attributs qui sont passés en parametres pour creer un objet
public class AbstractEntity implements Serializable { //v6 on va pas creer un dto pour cette classe car elle contient des champs techniques qu'on va pas les exposé mais ça contient le id qu'on va exposer => on va l'ajouter dans tous les dto et pas besoin de dto de cette classe

    @Id
    @GeneratedValue //strategy() default AUTO; delegate strategie à Hibernate v2
    Integer id;

    @Column(name = "creationDate"/*, nullable = false v16 min44*/, nullable = false, updatable = false)
    //v16 min40 @CreatedDate //il affecte la valeur à ce champs lors de la creation de l'objet  ***** v5 date creation dans bdd (comme dans lookup) c pas creation commande par exemple
    //v16 min38 @JsonIgnore // j'ai pas besoin de ce champ la lorsque j'invoque mon API car c'est un attribut technique v2
    @CreatedDate //reprise v26
    Instant creationDate;

    @Column(name = "lastModifiedDate" )
    //v16 min40 @LastModifiedDate // v6 données technique à ne pas exposer vers APIs externes
    //v16 min38 @JsonIgnore
    @LastModifiedDate //reprise v26
    Instant lastModifiedDate;

//v26    
//    //v16 min40
//    @PrePersist
//    void prePersist(){
//        creationDate = Instant.now();
//    }
//
//    //v16 min40
//    @PreUpdate
//    void preUpdate(){
//        lastModifiedDate = Instant.now();
//    }
}
