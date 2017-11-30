package br.com.contability.comum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

public class ShaPasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		return getSha512SecurePassword(rawPassword.toString());
	}
	
	/**
	 * @param senha
	 * @return ShaPasswordEncoder String
	 */
	public static String getSha512Securit(String senha){
		return new ShaPasswordEncoder().encode(senha);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		String passwordEncoded = getSha512SecurePassword(rawPassword.toString());
		
		return passwordEncoded.equals(encodedPassword) == true ? true : false;
	}
	
	private String getSha512SecurePassword(String rawPassword){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(rawPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
