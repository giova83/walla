package it.mapler.walla.enumeration;

public enum PROFILE {
	
	ADMIN("Admin","user"),
	CANDIDATO("Candidato","candidato"),
	RISTORATORE("Ristoratore","ristoratore"),
	FORNITORE("Fornitore","fornitore");
	
    private String name;
    private String table;

	private PROFILE(String name,String table) {
		this.name = name;
		this.table = table;
	}

    public String getName() {
		return name;
	}
    
    
    
    public String getTable() {
		return table;
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
