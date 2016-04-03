package com.chris.amazon;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AmazonCoverFinder {
	//http://ecs.amazonaws.com/onca/xml?AWSAccessKeyId=AKIAIL5VGOSTTL6ZVC6A&Artist=Nirvana&AssociateTag=2615-2750-3759&Operation=ItemSearch&ResponseGroup=Images&SearchIndex=Music&Service=AWSECommerceService&Timestamp=2016-04-03T14%3A55%3A25Z&Title=In%20bloom&Signature=As67kIVngR2vwoisFvXNXZbLj7l5W8Z6D63kMujSSKE%3D
	//http://ecs.amazonaws.com//onca/xml/AWSAccessKeyId=AKIAIL5VGOSTTL6ZVC6A&Artist=Nirvana&AssociateTag=2615-2750-3759&Operation=ItemSearch&ResponseGroup=Images&SearchIndex=Music&Service=AWSECommerceService&Timestamp=2016-04-03T15:17:26Z&Title=In bloom&Signature=VhMIB8AnPq0ITP6+Ydianltqy5BiYGO58DZ5Xz5LiXg=
	private final static String REQUEST_METHOD = "GET";
	private final static String AMAZONSTORE_SITE = "ecs.amazonaws.com";
	private final static String REST_PATH = "/onca/xml";
	
	private final static String REQUESTQUERY_TEMPLATE = "AWSAccessKeyId=%s"
			+ "&Artist='%s'"
			+ "&AssociateTag=%s"
			+ "&Operation=ItemSearch"
			+ "&ResponseGroup=Images,ItemAttributes"
			+ "&SearchIndex=Music"
			+ "&Service=AWSECommerceService"
			+ "&Timestamp=%s"
			+ "&Title='%s'";
	
	private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	
	private final String awsAccessKeyId;
	private final String awsSecretKey;
	private final String awsAssociateTag;
	
	public AmazonCoverFinder(String awsAccessKeyId, String awsSecretKey, String awsAssociateTag) {
		this.awsAccessKeyId = awsAccessKeyId;
		this.awsSecretKey = awsSecretKey;
		this.awsAssociateTag = awsAssociateTag;
	}
	
	public static void main(String[] args) throws Exception {
		String _awsAccessKeyId = "AKIAIL5VGOSTTL6ZVC6A";
		String _awsSecretKey = "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";
		String _awsAssociateTag = "2615-2750-3759";
		
		AmazonCoverFinder acf = new AmazonCoverFinder(_awsAccessKeyId, _awsSecretKey, _awsAssociateTag);
		String xml = acf.getCoverUrl("Unsteady", "X Ambassadors");
		
		System.out.println(xml);
				
	}
	
	public String getCoverUrl(String album, String artist) throws Exception {
		
		String requestQuery = String.format(REQUESTQUERY_TEMPLATE
											, awsAccessKeyId
											, artist
											, awsAssociateTag
											, getNewTimeStamp()
											, album);
		
		String reqSignature = generateSignature(requestQuery);
		
		String url = "http://" + AMAZONSTORE_SITE + REST_PATH + "?" + requestQuery + "&Signature=" + reqSignature;
		System.out.println(".:. " + url);
		
		return HttpUtils.doGet(url);
		
	}
	
	private String generateSignature(String reqQuery) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		String UTF_8 = StandardCharsets.UTF_8.name();
		
		byte[] secretKeyBytes = awsSecretKey.getBytes(UTF_8);
		SecretKeySpec sks = new SecretKeySpec(secretKeyBytes,HMAC_SHA256_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		mac.init(sks);
		
		String toSign = REQUEST_METHOD + "\n"
				+ AMAZONSTORE_SITE + "\n"
				+ REST_PATH + "\n"
				+ HttpUtils.encodeParameters(reqQuery);
		
		byte[] data = toSign.getBytes(UTF_8);
		byte[] rawHmac = mac.doFinal(data);
		Base64 encoder = new Base64();
		
		return new String(encoder.encode(rawHmac));
	}

	private String getNewTimeStamp () {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
		return dtf.format(LocalDateTime.now());
	}

}
