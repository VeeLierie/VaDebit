package id.co.hanoman.vaccount.model;

import javax.validation.constraints.NotNull;

public class Service  extends BaseRequest{
	
	@NotNull
	String TrxAmount ;

	@NotNull
	String FeeAmount ;

	@NotNull
	String Narative ;

	@NotNull
	String AccNoTo ;

	@NotNull
	String NarativeExt ;
	
	@NotNull
	String AccNoFrom ;
	
	@NotNull
	String ReffNoRev ;

	public String getTrxAmount() {
		return TrxAmount;
	}

	public void setTrxAmount(String trxAmount) {
		TrxAmount = trxAmount;
	}

	public String getFeeAmount() {
		return FeeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		FeeAmount = feeAmount;
	}
	
	public String getNarative() {
		return Narative;
	}

	public void setNarative(String narative) {
		Narative = narative;
	}
	public String getAccNoTo() {
		return AccNoTo;
	}

	public void setAccNoTo(String accNoTo) {
		AccNoTo = accNoTo;
	}
	public String getNarativeExt() {
		return NarativeExt;
	}

	public void setNarativeExt(String narativeExt) {
		NarativeExt = narativeExt;
	}
	public String getAccNoFrom() {
		return AccNoFrom;
	}

	public void setAccNoFrom(String accNoFrom) {
		AccNoFrom = accNoFrom;
	}
	
	public String getReffNoRev() {
		return ReffNoRev;
	}

	public void setReffNoRev(String reffNoRev) {
		ReffNoRev = reffNoRev;
	}

}
