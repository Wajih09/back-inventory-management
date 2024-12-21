package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable //v5 ca sera un champ composé ultérieurement mais c pas pour tous les classes comme id et dateCreation dans AbstractEntity
@Builder //v6 c une classe qui permet de construire un objet en exposant des methodes de meme nom des attributs et renvoyant les memes methodes et build() qui prend les attributs qui sont passés en parametres pour creer un objet
//v8 normalment on va enlever le builder dans les entities et on laisse seulement dans les dto, car on a pas accès au champ id hérité de AbstractEntity avec la builder().
public class Adresse { //v5

    @Column(name = "adresse1")
    private String adresse1;

    @Column(name = "adresse2")
    private String adresse2;

    @Column(name = "ville")
    private String ville;

    @Column(name = "codepostale")
    private String codePostale;

    @Column(name = "pays")
    private String pays;
}
