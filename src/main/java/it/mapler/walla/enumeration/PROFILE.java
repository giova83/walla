package it.mapler.walla.enumeration;

public enum PROFILE {
	
	ADMIN("Admin"),
	CANDIDATO("Candidato"),
	RISTORATORE("Ristoratore"),
	FORNITORE("Fornitore");
	
    private String name;

	private PROFILE(String name) {
		this.name = name;
	}

    public String getName() {
		return name;
	}
    
    public static PROFILE getProfile(String profile){
    	if(PROFILE.ADMIN.getName().equalsIgnoreCase(profile)){
    		return PROFILE.ADMIN;
    	}else if(PROFILE.CANDIDATO.getName().equalsIgnoreCase(profile)){
    		return PROFILE.CANDIDATO;
    	}else if(PROFILE.FORNITORE.getName().equalsIgnoreCase(profile)){
    		return PROFILE.FORNITORE;
    	}else if(PROFILE.RISTORATORE.getName().equalsIgnoreCase(profile)){
    		return PROFILE.RISTORATORE;
        }else{
        	return null;
        }
    }
    
    

}
