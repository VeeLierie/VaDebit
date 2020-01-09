package id.co.hanoman.controllers;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.hanoman.vaccount.model.Lingking;
import id.co.hanoman.vaccount.model.Mutasi;
import id.co.hanoman.vaccount.model.Service;
import id.co.hanoman.vaccount.util.NetClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="vaccount", description="Gateway for VAccount api")
@RestController
@RequestMapping("/vaccount/btn")
public class VaccountController {
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfDate2 = new SimpleDateFormat("dd MMM yyyy");
	@Autowired
	NetClient netClient;
	
	static Logger log = LoggerFactory.getLogger(NetClient.class);	 

	@ApiOperation(value = "Transaksi VA Debit",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> service(@RequestBody Service req){
		Object res = null;
		try {
			log.info("request service : "+getJson(req));
			res = netClient.service(req);
			log.info("response service : "+getJson(res));
		} catch (Exception e) {
			log.error("service",e);
		}
		return ResponseEntity.ok(res);
	}
	
	@ApiOperation(value = "Transaksi VA Debit",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/lingking", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> service(@RequestBody Lingking req){
		Object res = null;
		try {
			log.info("request lingking : "+getJson(req));
			res = netClient.lingking(req);
			log.info("response lingking : "+getJson(res));
		} catch (Exception e) {
			log.error("lingking",e);
		}
		return ResponseEntity.ok(res);
	}
	
	@ApiOperation(value = "Mutasi VA Debit",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/mutasi", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> service(@RequestBody Mutasi req){
		Object res = null;
		try {
			log.info("request mutasi : "+getJson(req));
			res = netClient.mutasi(req);
			log.info("response mutasi : "+getJson(res));
		} catch (Exception e) {
			log.error("mutasi",e);
		}
		return ResponseEntity.ok(res);
	}
	
	
	public JsonNode getJson(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode reqJson = mapper.valueToTree(obj);
		return reqJson;
	}
	
}
