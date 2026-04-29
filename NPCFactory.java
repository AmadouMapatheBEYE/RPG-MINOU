import entity.NPC;

public class NPCFactory {

    public static NPC[] creerTousLesNPC(GamePanel gp) {
        NPC[] npcs = new NPC[8];
        int t = gp.tailleTuile;

        npcs[0] = new NPC(); npcs[0].nom = "Minou";
        npcs[0].mondeX = t*12; npcs[0].mondeY = t*12;
        npcs[0].chargerSprites("minou");
        npcs[0].dialogues = new String[]{
            "*miaou*", "*te regarde avec mepris royal*",
            "*se leche la patte*", "*ronronne*", "*te juge en silence*"
        };

        npcs[1] = new NPC(); npcs[1].nom = "Sara";
        npcs[1].mondeX = t*6; npcs[1].mondeY = t*5;
        npcs[1].chargerSprites("sara");
        npcs[1].dialogues = new String[]{"Bonjour ! Tu as besoin d'aide ?","Je revisais... on etudie ensemble ?"};

        npcs[2] = new NPC(); npcs[2].nom = "Lena";
        npcs[2].mondeX = t*24; npcs[2].mondeY = t*5;
        npcs[2].chargerSprites("lena");
        npcs[2].dialogues = new String[]{"Quoi, t'as rien de mieux a faire ?","..."};

        npcs[3] = new NPC(); npcs[3].nom = "Yuki";
        npcs[3].mondeX = t*15; npcs[3].mondeY = t*15;
        npcs[3].chargerSprites("yuki");
        npcs[3].dialogues = new String[]{"Qu'est-ce que veut dire avoir le cafard ?","*note dans son carnet*"};

        npcs[4] = new NPC(); npcs[4].nom = "Maya";
        npcs[4].mondeX = t*40; npcs[4].mondeY = t*12;
        npcs[4].chargerSprites("maya");
        npcs[4].dialogues = new String[]{"He toi ! Tu joues au basket ?","Allez, arrete de trainer !"};

        npcs[5] = new NPC(); npcs[5].nom = "Ellie";
        npcs[5].mondeX = t*25; npcs[5].mondeY = t*4;
        npcs[5].chargerSprites("ellie");
        npcs[5].dialogues = new String[]{"Oh ! Desolee je prenais toute la place.","*referme rapidement son carnet*"};

        npcs[6] = new NPC(); npcs[6].nom = "Noa";
        npcs[6].mondeX = t*24; npcs[6].mondeY = t*20;
        npcs[6].chargerSprites("noa");
        npcs[6].dialogues = new String[]{"T'as l'air de quelqu'un qui a arrete d'attendre.","*regarde la riviere*"};

        npcs[7] = new NPC(); npcs[7].nom = "";
        npcs[7].mondeX = t*20; npcs[7].mondeY = t*11;
        npcs[7].chargerSprites("sara");
        npcs[7].dialogues = new String[]{"Belle journee a Solevert !","Le cafe du coin est excellent."};

        return npcs;
    }
}