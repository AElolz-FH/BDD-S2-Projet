package fr.uphf.utilisateurs.ressources;


import fr.uphf.utilisateurs.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/utilisateurs")
public class UtilisateursRessource {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/{idUtilisateur}")
    public Utilisateur getUtilisateur(@PathVariable String idUtilisateur){
        return utilisateurService.getUtilisateur(idUtilisateur);
    }
}
