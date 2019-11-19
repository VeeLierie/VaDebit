package id.co.hanoman.vaccount.util;

import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.hanoman.config.YAMLConfig;
import id.co.hanoman.vaccount.model.BaseRequest;
import id.co.hanoman.vaccount.model.ErrorResponse;
import id.co.hanoman.vaccount.model.Lingking;
import id.co.hanoman.vaccount.model.Response;
import id.co.hanoman.vaccount.model.Service;

@Component
public class NetClient {
	static Logger log = LoggerFactory.getLogger(NetClient.class);	 

	@Autowired	
	YAMLConfig config ;

	public   Object service(Service req) throws Exception{
		Object resCall = null;
		try {
			String trxAmount = req.getTrxAmount();
			String feeAmount = req.getFeeAmount();
			String narative = req.getNarative() == null ? "":req.getNarative();
			String narativeExt = req.getNarativeExt() == null ?"":req.getNarativeExt();
			String accNoTo = req.getAccNoTo();
			String accNoFrom = req.getAccNoFrom();
			String reffNoRev = req.getReffNoRev();
			
			String data1 =  "\"data\":{\"trxAmount\":"+trxAmount+",\"feeAmount\":"+feeAmount+",\"narative\":\""+narative+"\",\"narativeExt\":\""+narativeExt+"\",\"accNoTo\":\""+accNoTo+"\",\"accNoFrom\":\""+accNoFrom+"\",\"reffNoRev\":\""+reffNoRev+"\"}";
			
			data1=data1.replace(" ", "");
			data1=data1.replace("\t", "");
			resCall = callUrl(data1,"service",req);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
		return resCall;
	}
	
	
	public   Object lingking(Lingking req) throws Exception{
		Object resCall = null;
		try {
			
			String accNo = req.getAccNo();
			String cardNo = req.getCardNo();
			String accName = req.getAccName();
			
			String data1 =  "\"data\":{\"accNo\":\""+accNo+"\",\"cardNo\":\""+cardNo+"\",\"accName\":\""+accName+"\"}";
			
			data1=data1.replace(" ", "");
			data1=data1.replace("\t", "");
			resCall = callUrl(data1,"service",req);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
		return resCall;
	}

	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs = 1 min
	
	private  static String getTimeStamp(){
		String pattern = "yyyy-MM-dd";
		String pattern2 = "HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

		
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		Date addTime=new Date(t + (	5 * ONE_MINUTE_IN_MILLIS));
		
		String day= simpleDateFormat.format(addTime);
		String time= simpleDateFormat2.format(addTime);
		return day+"T"+time+"+07:00";
	}	

	public static String calculateHMAC(String data, String key)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
		Mac mac = Mac.getInstance(HMAC_SHA512);
		mac.init(secretKeySpec);
		return Base64.getUrlEncoder().encodeToString(mac.doFinal(data.getBytes()));
	}
	
	private static final String HMAC_SHA512 = "HmacSHA512";
	private  Object callUrl(String data,String subPath,BaseRequest baseReq) throws Exception{
		URL url = new URL(config.getBaseUrl()+subPath);
		log.info("callUrl url  :"+url);
		try {
			long unixTime = System.currentTimeMillis() ;
			System.out.println("unixTime:"+unixTime);
			String jti =  (baseReq.getJti() == null ? String.valueOf(unixTime) : baseReq.getJti());
			String iat =  (baseReq.getIat() == null ? String.valueOf(unixTime) : baseReq.getIat());
			String tid =  (baseReq.getTid() == null ? "10001401" : baseReq.getTid());
			String procode = (baseReq.getProCode() == null ? "null" : baseReq.getProCode());
			String cnl = (baseReq.getCnl() == null ? "null" : baseReq.getCnl());
					
			String request = "{\"cnl\":\""+cnl+"\","+data+",\"iss\":\"--\",\"exp_iso8601\":\""+getTimeStamp()+"\",\"jti\":\""+jti+"\",\"iat\":"+iat+",\"tid\":\""+tid+"\",\"procode\":\""+procode+"\"}";
			log.info(request);
			
			String header = "{\"alg\":\"HS512\",\"typ\":\"JWT\"}";
//						String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes("UTF-8") );
			String encodedHeaderString = Base64.getEncoder().withoutPadding().encodeToString(header.getBytes());
//					System.out.println(encodedHeader);
//			lolg.info(encodedHeaderString);
			String encodedDataString = Base64.getEncoder().withoutPadding().encodeToString(request.getBytes());
//			log.info(encodedDataString);
			String apiKey = config.getApiKey();
			String hmac = calculateHMAC(encodedHeaderString+"."+encodedDataString, apiKey);
//			log.info("hmac:"+hmac);
			String data2 = encodedHeaderString+"."+encodedDataString+"."+hmac;
//			log.info("data1:"+data2);


			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(20000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("username", config.getUserId());
			conn.setRequestProperty("content-type", "text/plain");

			OutputStream os = conn.getOutputStream();
			os.write(data2.getBytes());
			os.flush();
			int rc = conn.getResponseCode();
			log.info("rc:"+rc);

			if (conn.getResponseCode() != 200) {
				ErrorResponse errRes = new ErrorResponse();
				errRes.setCode(String.valueOf(conn.getResponseCode()));
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getErrorStream())));
				String message = org.apache.commons.io.IOUtils.toString(br);
				log.info("message:"+message);
				String[] msgSplit= message.split("\\.");
				log.info("msgSplit:"+msgSplit[1]);
				byte[] dataByte = Base64.getDecoder().decode(msgSplit[1]);
				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode resJson = mapper.readTree(dataByte);
				JsonNode reqJson = mapper.readTree(request);
				Response resp = new Response();
				resp.setRequest(reqJson);
				resp.setResponse(resJson);
				return resp;

			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String message = org.apache.commons.io.IOUtils.toString(br);
								log.info("message:"+message);
				String[] msgSplit= message.split("\\.");
								log.info("msgSplit:"+msgSplit[1]);
				byte[] dataByte = Base64.getDecoder().decode(msgSplit[1]);
//								String dataString = new String(dataByte);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode resJson = mapper.readTree(dataByte);
				JsonNode reqJson = mapper.readTree(request);
//							log.info(dataString);
				Response resp = new Response();
				resp.setRequest(reqJson);
				resp.setResponse(resJson);
				return resp;
			}
		} catch (Exception e) {
			log.error("message:"+e.getMessage(),e);
//			e.printStackTrace();
			String strErrMsg = e.toString();
			ErrorResponse errRes = new ErrorResponse();
			
			errRes.setCode("400");
			if(strErrMsg.equals("java.net.ConnectException: Connection refused: connect")){
				errRes.setFaultcode("G02");
				errRes.setFaultMessage("Failed to Connect");
		
			} else if(strErrMsg.equals("java.net.SocketTimeoutException: Read timed out")){
				errRes.setFaultcode("G01");
				errRes.setFaultMessage("Timeout from VaDebit");
				
			} else if(strErrMsg.equals("java.net.ConnectException: Connection timed out: connect")){
				errRes.setFaultcode("G03");
				errRes.setFaultMessage("Connection timed out");
				
			}else {
				errRes.setFaultcode("G99");
				errRes.setFaultMessage("General Error");
			}
			return errRes;
		}
	}
}

