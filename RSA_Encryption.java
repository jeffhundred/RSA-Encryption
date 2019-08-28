import java.math.BigInteger;
import java.util.*;
import java.io.*;
public class RSA_Encryption{

   public static String getFile() throws IOException{
      File file = new File("plaintext.txt");
      Scanner inputFile = new Scanner(file);
      
      return inputFile.nextLine();
   
   }
   public static void writeFile(String cryptText) throws IOException{
      try{
         PrintWriter write = new PrintWriter("cypherText.txt");
         write.print(cryptText);
         write.close();
      } catch (IOException e) {
         System.err.println(e);
      
      }
   
   }
   
   public static void main(String[] args) throws IOException{
      Random number = new Random();
      
      BigInteger p = BigInteger.probablePrime(512, number);
      BigInteger q = BigInteger.probablePrime(512, number);
      
      BigInteger n = p.multiply(q);
      BigInteger phiN = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
      
      BigInteger e = publicKey(phiN);
      BigInteger d = privateKey(e, phiN);
      
      //-------------------------------------------------------------------------//
      // First Message Encryption
      
      String message1 = getFile();
      System.out.println("plaintext: " + message1);
      BigInteger plainText1 = new BigInteger(message1.getBytes());
      
      BigInteger cipherText1 = encrypt(plainText1, e, n);
      System.out.println("Ciphertext: " + cipherText1);
      
      String encryptedMessage1 = new String(cipherText1.toByteArray());
      System.out.println("encrypted message: " + encryptedMessage1);
      writeFile(encryptedMessage1);
      
      plainText1 = decrypt(cipherText1, d, n);
      String decryptMessage1 = new String(plainText1.toByteArray());
      
      System.out.println("plaintext: " + decryptMessage1);
      
      BigInteger signature1 = digitalSignature(plainText1, d, n);
      System.out.println("Digital Signature: " + signature1);
      
      BigInteger verification1 = verify(signature1, e, n);
      System.out.println("Verification: " + verification1);
      
      System.out.println("-----------------------------------------------------------------------------------");
      
      //------------------------------------------------------------------------------//
      // Second message encryption
      
      String message2 = "This is the second secret message";
      System.out.println("plaintext: " + message2);
      BigInteger plainText2 = new BigInteger(message2.getBytes());
      
      
      BigInteger cipherText2 = encrypt(plainText2, e, n);
      System.out.println("Ciphertext: " + cipherText2);
      
      String encryptedMessage2 = new String(cipherText2.toByteArray());
      System.out.println("Encrypted message: " + encryptedMessage2);
      
      plainText2 = decrypt(cipherText2, d, n);
      String decryptMessage2 = new String(plainText2.toByteArray());

      
      System.out.println("plaintext: " + decryptMessage2);
      
      
      BigInteger signature2 = digitalSignature(plainText2, d, n);
      System.out.println("Digital Signature: " + signature2);
      
      BigInteger verification2 = verify(signature2, e, n);
      System.out.println("Verification: " + verification2);
      
      System.out.println("-----------------------------------------------------------------------------------");
      
      //-------------------------------------------------------------------------//
      // Proving homomorphic property of RSA
      
      BigInteger crossPlainText = plainText1.multiply(plainText2);
      BigInteger encryptedCross = encrypt(crossPlainText, e, n);
      
      BigInteger crossCipherText = cipherText1.multiply(cipherText2).mod(n);
      
      System.out.println(encryptedCross);
      System.out.println(crossCipherText);
      
      boolean homomorphic;
      
      if(encryptedCross.equals(crossCipherText)){
         homomorphic = true;
      }else{
         homomorphic = false;
      }
      
      System.out.println("is homomorphic: " + homomorphic);
      
      
      
   }
   
   public static BigInteger publicKey(BigInteger phiN){
      BigInteger e = new BigInteger("2");
      
      
      while(e.gcd(phiN).intValue() > 1){
         e = e.add(BigInteger.ONE);
      }
      
      return e;
   }
   
   
   public static BigInteger privateKey(BigInteger e, BigInteger phiN){
      return e.modInverse(phiN);
   }
   
   public static BigInteger digitalSignature(BigInteger m, BigInteger d, BigInteger n){
      BigInteger sig = m.modPow(d, n);
      return sig;
   }
   
   public static BigInteger verify(BigInteger sig, BigInteger e, BigInteger n){
      BigInteger message = sig.modPow(e, n);
      return message;
   }
   
   public static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n){
      BigInteger cipher = m.modPow(e,n);
      return cipher;
   }
   
   public static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n){
      BigInteger message = c.modPow(d,n);
      return message;
   }
      
   
   

}

