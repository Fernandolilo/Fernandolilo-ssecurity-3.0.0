package com.systempro.auth.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	//gera token
	 public String generateToken(String userName){
	        Map<String,Object> claims=new HashMap<>();
	        return createToken(claims,userName);
	    }

	private String createToken(Map<String, Object> claims, String userName) {
	
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 *60*24))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact())
				.compact();
	}
}
