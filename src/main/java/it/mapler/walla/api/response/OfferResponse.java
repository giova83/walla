package it.mapler.walla.api.response;

import it.mapler.walla.model.Offer;

import java.util.List;

public class OfferResponse extends AbsResponse{

	private static final long serialVersionUID = 1L;
	
	public List<Offer> offerts;
	
	public List<Offer> getOfferts() {
		return offerts;
	}
	public void setOfferts(List<Offer> offerts) {
		this.offerts = offerts;
	}
	
	
}
