package fr.uphf.utilisateurs.ressources;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CompteDetailDTO{
    private String numero;
    private CarteApiService.CartesItemListDTO[] cartes;
}