public class DialogueChoix {

    public static class Choix {
        public String texte;
        public String personnage;
        public int    effetAffection;
        public String reponseNPC;

        public Choix(String texte, String personnage,
                     int effetAffection, String reponseNPC) {
            this.texte          = texte;
            this.personnage     = personnage;
            this.effetAffection = effetAffection;
            this.reponseNPC     = reponseNPC;
        }
    }

    public static class DialogueAvecChoix {
        public String  texteNPC;
        public Choix[] choix;
        public String  nomNPC;

        public DialogueAvecChoix(String nomNPC, String texteNPC, Choix[] choix) {
            this.nomNPC   = nomNPC;
            this.texteNPC = texteNPC;
            this.choix    = choix;
        }
    }

    public static DialogueAvecChoix[] getDialoguesSara() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Sara",
                "Oh ! Tu as rate le cours d'hier ? J'ai mes notes si tu veux...",
                new Choix[]{
                    new Choix("Oui merci, c'est vraiment gentil !",   "Sara", +15, "Pas de probleme ! Je les recopie proprement pour toi."),
                    new Choix("Non ca va, je vais me debrouiller.",    "Sara", -5,  "Ah... d'accord. Bonne chance alors."),
                    new Choix("T'as vraiment eu le temps de tout noter ?", "Sara", +8, "Oui... j'essaie d'etre organisee.")
                }
            ),
            new DialogueAvecChoix("Sara",
                "Je dois rendre ce devoir demain et j'ai pas dormi depuis deux jours...",
                new Choix[]{
                    new Choix("C'est pas idiot, tu travailles dur.",   "Sara", +20, "Tu... merci. Personne dit ca d'habitude."),
                    new Choix("Tu devrais dormir parfois, non ?",      "Sara", +10, "Je sais. Mais si je dors je pense a tout ce qu'il reste..."),
                    new Choix("Classique Sara la perfectionniste.",     "Sara", -10, "C'est si evident que ca ?")
                }
            ),
            new DialogueAvecChoix("Sara",
                "J'ai fait un gateau ce matin pour m'entrainer. C'est peut-etre pas tres reussi.",
                new Choix[]{
                    new Choix("Je gouterais bien !",         "Sara", +15, "Vraiment ? Il est... promets de pas juger."),
                    new Choix("T'es nulle en cuisine ?",     "Sara", +5,  "Catastrophique. Le four a failli bruler."),
                    new Choix("Sympa comme attention.",      "Sara", +8,  "C'etait pour personne en particulier !")
                }
            )
        };
    }

    public static DialogueAvecChoix[] getDialoguesLena() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Lena",
                "Quoi, t'as rien de mieux a faire que de trainer ici ?",
                new Choix[]{
                    new Choix("Apparemment non.",         "Lena", +15, "Au moins t'es honnete."),
                    new Choix("Je t'ecoutais jouer.",     "Lena", +20, "...Depuis combien de temps ?"),
                    new Choix("Desolé, je pars.",         "Lena", -5,  "...Ouais. C'est ca.")
                }
            ),
            new DialogueAvecChoix("Lena",
                "Ce morceau... je l'ai pas joue depuis deux ans.",
                new Choix[]{
                    new Choix("Peut-etre que t'etais prete.",  "Lena", +20, "T'es chiant a dire des trucs justes comme ca."),
                    new Choix("Il est beau ce morceau.",       "Lena", +15, "Merci. Je suppose."),
                    new Choix("Qu'est-ce qui s'est passe ?",   "Lena", -5,  "Rien. Laisse tomber.")
                }
            ),
            new DialogueAvecChoix("Lena",
                "Ton chat me fixe et j'arrive pas a partir.",
                new Choix[]{
                    new Choix("Minou fait ca a tout le monde.",    "Lena", +10, "Dis a ton chat d'arreter. Il me juge."),
                    new Choix("Il t'aime bien alors.",             "Lena", +15, "Super. Aimee par un chat qui m'assassine."),
                    new Choix("T'as qu'a pas le regarder.",        "Lena", +5,  "J'essaie ! Il est partout !")
                }
            )
        };
    }

    public static DialogueAvecChoix[] getDialoguesYuki() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Yuki",
                "Excusez-moi... qu'est-ce que veut dire avoir le cafard ?",
                new Choix[]{
                    new Choix("Ca veut dire etre triste.",         "Yuki", +10, "Fascinant. Pourquoi un insecte ?"),
                    new Choix("Tu imagines pas bien la tristesse ?","Yuki", +15, "Au Japon on dit le coeur devient bleu."),
                    new Choix("C'est une longue histoire...",       "Yuki", +8,  "J'ai le temps ! J'apprends tout !")
                }
            ),
            new DialogueAvecChoix("Yuki",
                "Ma famille a prevu des choses pour moi. Je suis venue ici pour reflechir.",
                new Choix[]{
                    new Choix("Parfois fuir c'est penser.",      "Yuki", +20, "Tu es tres sage. Je note ca."),
                    new Choix("Qu'est-ce qu'ils ont prevu ?",    "Yuki", +10, "Un... arrangement. C'est complique."),
                    new Choix("On fuit tous quelque chose.",     "Yuki", +15, "Toi aussi ? Qu'est-ce que tu fuis ?")
                }
            ),
            new DialogueAvecChoix("Yuki",
                "J'ai dit a quelqu'un qu'il m'avait vole le coeur. Il a appele la police.",
                new Choix[]{
                    new Choix("Oui. Tres grosse erreur.",        "Yuki", +10, "Je comprends maintenant. Mon carnet ment."),
                    new Choix("C'est la meilleure histoire !",   "Yuki", +20, "Je suis contente que tu ries. Pas lui."),
                    new Choix("A qui tu as dit ca ?",            "Yuki", +15, "Le professeur de chimie. Il etait... pas content.")
                }
            )
        };
    }

    public static DialogueAvecChoix[] getDialoguesMaya() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Maya",
                "He toi ! J'ai besoin d'un joueur pour le tournoi. Tu joues ?",
                new Choix[]{
                    new Choix("Je suis nul mais OK.",        "Maya", +15, "Parfait ! L'honnetete j'apprécie. Ce soir on s'entraine."),
                    new Choix("Ca depend du sport.",         "Maya", +8,  "Basket. T'as deux heures pour apprendre. Viens."),
                    new Choix("J'ai autre chose a faire.",   "Maya", -10, "T'as vraiment autre chose ou t'as peur ?")
                }
            ),
            new DialogueAvecChoix("Maya",
                "Mon genou... c'est rien. Arrete de regarder comme ca.",
                new Choix[]{
                    new Choix("Tu boites depuis ce matin.",      "Maya", +20, "...Personne l'avait remarque."),
                    new Choix("Je regarde rien.",                "Maya", +5,  "Bien. On en parle plus."),
                    new Choix("Tu devrais voir un medecin.",     "Maya", +10, "Les medecins disent d'arreter le sport. Donc non.")
                }
            ),
            new DialogueAvecChoix("Maya",
                "Si t'etais pas aussi nul au basket je dirais que t'es pas mal.",
                new Choix[]{
                    new Choix("C'est le plus beau compliment.", "Maya", +20, "T'es con. Mais dans le bon sens."),
                    new Choix("Je peux m'ameliorer.",           "Maya", +15, "J'ai vu des cactus plus souples. Mais... peut-etre."),
                    new Choix("T'es pas mal non plus.",         "Maya", +25, "J'ai dit SI. C'est hypothetique.")
                }
            )
        };
    }

    public static DialogueAvecChoix[] getDialoguesEllie() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Ellie",
                "Oh ! Pardon je... tu t'es assis sur mon carnet.",
                new Choix[]{
                    new Choix("C'est toi qui as dessine ca ?",   "Ellie", +20, "C'est... rien. Des gribouillis."),
                    new Choix("Desolé ! Je t'ai fait mal ?",     "Ellie", +10, "Non non ! C'est juste le carnet qui compte."),
                    new Choix("Je l'ai meme pas abime.",         "Ellie", +5,  "Ouf. Oui. C'est bon.")
                }
            ),
            new DialogueAvecChoix("Ellie",
                "Tu... tu reviens souvent dans ce coin du parc ?",
                new Choix[]{
                    new Choix("Depuis que t'es la, oui.",    "Ellie", +25, "*disparait derriere son carnet mais sourit*"),
                    new Choix("Parfois. Pourquoi ?",         "Ellie", +10, "Pour... rien. Juste pour savoir."),
                    new Choix("T'essaies de me chasser ?",   "Ellie", +15, "Non ! Tu peux rester.")
                }
            ),
            new DialogueAvecChoix("Ellie",
                "Quelqu'un a poste mes dessins en ligne sans me demander.",
                new Choix[]{
                    new Choix("C'est une violation. T'es en colere ?",  "Ellie", +20, "Oui. Tout le monde dit sois contente. Toi non."),
                    new Choix("Au moins les gens voient ton talent.",   "Ellie", -5,  "C'etait pas a eux de decider."),
                    new Choix("Qui a fait ca ?",                        "Ellie", +15, "Je... je sais pas si je veux le savoir.")
                }
            )
        };
    }

    public static DialogueAvecChoix[] getDialoguesNoa() {
        return new DialogueAvecChoix[]{
            new DialogueAvecChoix("Noa",
                "T'as l'air de quelqu'un qui a arrete d'attendre quelque chose.",
                new Choix[]{
                    new Choix("Bien vu.",                        "Noa", +20, "Je suis pareil. Enfin... j'etais pareille."),
                    new Choix("C'est quoi cette question ?",     "Noa", +10, "C'est pas une question. C'est une observation."),
                    new Choix("Tu connais meme pas mon nom.",    "Noa", +15, "Les noms viennent apres. Les visages parlent d'abord.")
                }
            ),
            new DialogueAvecChoix("Noa",
                "Ma soeur aimait ce banc. Elle disait qu'on voyait toute la ville de la.",
                new Choix[]{
                    new Choix("Elle te manque.",                     "Noa", +25, "Ouais. Tous les jours."),
                    new Choix("Elle avait raison sur quoi alors ?",  "Noa", +15, "Sur presque tout le reste."),
                    new Choix("Je suis desole.",                     "Noa", +10, "Merci. Mais pitie pas de regard de compassion.")
                }
            ),
            new DialogueAvecChoix("Noa",
                "Tu reviens. C'est interessant.",
                new Choix[]{
                    new Choix("T'es interessante.",              "Noa", +20, "Calibres differemment, je t'avais dit."),
                    new Choix("J'aime cette heure de la nuit.",  "Noa", +15, "Moi aussi. C'est pour ca que je te supporte."),
                    new Choix("J'allais juste passer.",          "Noa", +5,  "Bien sur. Assieds-toi quand meme.")
                }
            )
        };
    }
}