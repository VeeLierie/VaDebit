package id.co.hanoman.vaccount.model;

import javax.validation.constraints.NotNull;

public class Lingking  extends BaseRequest{
	
	@NotNull
	String AccNo ;
	
	@NotNull
	String CardNo ;
	
	@NotNull
	String AccName ;
	
	public String getAccNo() {
		return AccNo;
	}

	public void setAccNo(String accNo) {
		AccNo = accNo;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	
	public String getAccName() {
		return AccName;
	}

	public void setAccName(String accName) {
		AccName = accName;
	}
}
