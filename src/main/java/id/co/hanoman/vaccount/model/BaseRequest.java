package id.co.hanoman.vaccount.model;

public class BaseRequest {
	String iat;

	String jti;

	String tid;
	
	String procode;
	
	String cnl;
	
	
	public String getIat() {
		return iat;
	}
	public void setIat(String iat) {
		this.iat = iat;
	}
	public String getJti() {
		return jti;
	}
	public void setJti(String jti) {
		this.jti = jti;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getProCode() {
		return procode;
	}
	
	public void setProCode(String procode) {
		 this.procode = procode; 
	}
	
	public String getCnl() {
		return cnl;
	}
	
	public void setCnl(String cnl) {
		 this.cnl = cnl; 
	}

}
