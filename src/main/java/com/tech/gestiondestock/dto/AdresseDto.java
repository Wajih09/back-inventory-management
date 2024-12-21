package com.tech.gestiondestock.dto;
import java.util.List;

//dto est la couche intermidière entre l'externe et notre API v12 min6:40
import com.tech.gestiondestock.models.Adresse;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //v6 c un design pattern : une classe qui permet de construire un objet en exposant des methodes de meme nom des attributs et renvoyant les memes methodes et build() qui prend les attributs qui sont passés en parametres pour creer un objet
public class AdresseDto { //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)

    private String adresse1;

    private String adresse2;

    private String ville;

    private String codePostale;

    private String pays;

    public static AdresseDto fromEntity(Adresse adresse){ //v8 min3
        if(adresse==null){
            return null;
        }
        return AdresseDto.builder().adresse1(adresse.getAdresse1()).adresse2(adresse.getAdresse2())
                                   .ville(adresse.getVille()).codePostale(adresse.getCodePostale()).pays(adresse.getPays()).build();
    }

    public static Adresse toEntity(AdresseDto adresseDto){ //v8 min4 SET NOT GET HERE !!!!!!!!! ici on a essayer avec builder et on va excécuter et voir
        //return Adresse.builder().adresse1(adresseDto.getAdresse1()).adresse2(adresseDto.getAdresse2())
                                //.ville(adresseDto.ville).codePostale(adresseDto.codePostale).pays(adresseDto.pays).build();
        Adresse adresse = new Adresse();
        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setVille(adresseDto.getVille());
        adresse.setCodePostale(adresseDto.getCodePostale());
        adresse.setPays(adresseDto.getPays());
        return adresse;
    }
}
