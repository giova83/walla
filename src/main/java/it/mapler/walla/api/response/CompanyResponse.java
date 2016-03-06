package it.mapler.walla.api.response;

import it.mapler.walla.model.Company;

import java.util.List;

public class CompanyResponse extends AbsResponse{

private static final long serialVersionUID = 1L;
	
	public List<Company> companies;
	
	
	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
}