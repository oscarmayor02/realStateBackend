package com.realEstate.security;

import com.realEstate.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
// Marks this class as a Spring service component
@Service
public class JwtService {

    // Injects the value of 'jwt.secret' from application properties
    @Value("${jwt.secret}")
    private String secret;

    // Injects the value of 'jwt.expiration' from application properties
    @Value("${jwt.expiration}")
    private long expiration;

    // Generates a JWT token using the provided username
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("idUser", user.getId()); // Agrega el id como claim
        claims.put("name", user.getName()); // Agrega el nombre como claim
        return Jwts.builder()
                .setClaims(claims)

                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Extracts the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Uses a claim resolver to get the subject
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("idUser", Long.class);
    }

    // Extracts any claim from the token using a resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Gets all claims from the token
        return claimsResolver.apply(claims); // Applies the resolver to extract the desired claim
    }

    // Parses the token and retrieves all claims
    private Claims extractAllClaims(String token) {
        System.out.println("Nombre en el token: " + token);
        return Jwts.parser() // Creates a JWT parser
                .setSigningKey(secret) // Sets the signing key for validation
                .parseClaimsJws(token) // Parses the token
                .getBody(); // Retrieves the claims body
    }

    public String extractName(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("name", String.class);
    }

    // Validates the token by checking username and expiration
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token); // Extracts username from token
        return (tokenUsername.equals(username) && !isTokenExpired(token)); // Checks if username matches and token is not expired
    }

    // Checks if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compares expiration date with current date
    }

    // Extracts the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Uses a claim resolver to get expiration
    }
}

