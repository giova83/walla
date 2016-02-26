package it.mapler.walla.api.response;

import it.mapler.walla.model.Candidature;


import java.util.List;

public class CandidatureResponse extends AbsResponse {

private static final long serialVersionUID = 1L;
	
	public List<Candidature> candidatures;
	
	public List<Candidature> getCandidatures() {
		return candidatures;
	}
	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}
}
