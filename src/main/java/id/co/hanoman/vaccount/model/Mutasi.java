package id.co.hanoman.vaccount.model;

import javax.validation.constraints.NotNull;

public class Mutasi  extends BaseRequest{
	

	@NotNull
	String Size ;

	@NotNull
	String Page ;

	@NotNull
	String AccNoFrom ;

	public String getSize() {
		return Size;
	}

	public void setSize(String size) {
		Size = size;
	}

	public String getPage() {
		return Page;
	}

	public void setPage(String page) {
		Page = page;
	}
	
	public String getAccNoFrom() {
		return AccNoFrom;
	}

	public void setAccNoFrom(String accNoFrom) {
		AccNoFrom = accNoFrom;
	}

}
