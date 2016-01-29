package it.mapler.walla.api.response;
import java.util.List;

import it.mapler.walla.model.Candidate;

public class CandidateResponse extends AbsResponse{

	
private static final long serialVersionUID = 1L;
	
	public List<Candidate> candidates;
	
	public List<Candidate> getCandidates() {
		return candidates;
	}
	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}
}


