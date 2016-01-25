package it.mapler.walla.enumeration;

public enum STATUS {

	OK(10),
	WARNING_USER(101),
	WARNING_PWD(102),
	ERROR_DB(1002),
	ERROR_UNEXPECTED(1001),
	ERROR_TOKEN(1003);

	private int codError;

	private STATUS(int codError) {
		this.codError = codError;
	}

    public int getCodError() {
		return codError;
	}



}
