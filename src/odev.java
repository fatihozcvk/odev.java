import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class odev {
    public static class Islem {
        // Dosyaya yazdırma fonksiyonu
        public static void dosyayaYazdir(Kullanicilar kullanicilar1, Kullanicilar kullanicilar2) {
            String text = "";
            FileWriter fwriter = null;
            try {
                // Elit üyeleri dosyaya yazdırma
                text += "#" + kullanicilar1.getGrup() + " Üyeler\n";
                fwriter = new FileWriter("kullanicilar.txt", false);
                for (int i=0; i<=kullanicilar1.getIndex(); i++) {
                    if (kullanicilar1.getAdlar()[i] == null) continue;
                    text += kullanicilar1.getAdlar()[i] + "\t" + kullanicilar1.getSoyadlar()[i] + "\t" + kullanicilar1.getMailler()[i] + "\n";
                }
                // Genel Üyeleri dosyaya yazdırma
                text += "\n#" + kullanicilar2.getGrup() + " Üyeler\n";
                for (int i=0; i<=kullanicilar2.getIndex(); i++) {
                    if (kullanicilar2.getAdlar()[i] == null) continue;
                    text += kullanicilar2.getAdlar()[i] + "\t" + kullanicilar2.getSoyadlar()[i] + "\t" + kullanicilar2.getMailler()[i] + "\n";
                }
                fwriter.write(text);
                fwriter.close();
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }

        // Mail gönderme fonksiyonu
        public static void mailGonder(Kullanicilar kullanicilar) {
            String fromEmail="fatihozcevik03@gmail.com";  // gönderici mailini buraya giriniz
            String password="rfshjiqyqochfusx";  // burada mail göndermek için gmail uygulamasına girip oradan uygulama şifresi alıp kopyalamanız gerekir.
            String host = "smtp.gmail.com";

            // Mesaj içeriğini klavyeden girdi olarak alma
            Scanner sc = new Scanner(System.in);
            System.out.print("Mesaj: ");
            String text = sc.nextLine();
            sc.close();

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(fromEmail, password);

                }

            });

            // Used to debug SMTP issues
            session.setDebug(true);

            for (String toEmail: kullanicilar.getMailler()) {
                if (toEmail == null) continue;
                try {
                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session);

                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(fromEmail));

                    // Set To: header field of the header.
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

                    // Set Subject: header field
                    message.setSubject("This is the Subject Line!");

                    // Now set the actual message
                    message.setText(text);

                    System.out.println("sending...");
                    // Send message
                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }

            }

        }
    }
     // kullanıcılar classı
    public static class Kullanicilar extends Islem{
        // Tanımlamalar
        private String grup;
        private String[] adlar;
        private String[] soyadlar;
        private String[] mailler;
        private int index;

        // Kullanıcılar classının constuctur'ı
        public Kullanicilar(String grup) {
            this.grup = grup;
            this.adlar = new String[10];
            this.soyadlar = new String[10];
            this.mailler = new String[10];
            this.index = 0;
        }
        // erişim belirteçleri private olduğu için get metodlarıyla alınması
        public String getGrup() {return this.grup;}
        public String[] getAdlar() {return this.adlar;}
        public String[] getSoyadlar() {return this.soyadlar;}
        public String[] getMailler() {return this.mailler;}
        public int getIndex() {return this.index;}

         // Kullanıcı ekleme fonksiyonu
        public void kullaniciEkle(String ad, String soyad, String mail) {
            this.adlar[index] = ad;
            this.soyadlar[index] = soyad;
            this.mailler[index] = mail;
            this.index++;
        }
    }

    public static void main(String[] args) throws IOException{
        // Nesne oluşturma
        Kullanicilar elitler = new Kullanicilar("Elit");
        Kullanicilar geneller = new Kullanicilar("Genel");

        Scanner sc = new Scanner(System.in);

        // Tanımlamalar
        String ad;
        String soyad;
        String mail;

        int secim = 0;
        int secim2 = 0;

        // Program Çalışma Döngüsü

        while (secim != 4) {
            System.out.println("----- SEÇENEKLER ------");
            System.out.println("1- Elit Üye Ekleme ");
            System.out.println("2- Genel Üye Ekleme ");
            System.out.println("3- Mail Gonderme ");
            System.out.println("4- Menuden Cikis Yap");
            System.out.print("Görev: ");
            secim = sc.nextInt();
            if (secim == 1) {
                System.out.print("Ad: ");
                ad = sc.next();
                System.out.print("Soyad: ");
                soyad = sc.next();
                System.out.print("Mail: ");
                mail = sc.next();
                elitler.kullaniciEkle(ad, soyad, mail);
                Islem.dosyayaYazdir(elitler, geneller);
            }
            else if (secim == 2) {
                System.out.print("Ad: ");
                ad = sc.next();
                System.out.print("Soyad: ");
                soyad = sc.next();
                System.out.print("Mail: ");
                mail = sc.next();
                geneller.kullaniciEkle(ad, soyad, mail);
                Islem.dosyayaYazdir(elitler, geneller);
            }
            else if (secim == 3) {
                System.out.println("1-Elit Üyelere Mail Gonder");
                System.out.println("2-Genel Üyelere Mail Gonder");
                System.out.println("3-Tüm Üyelere Mail Gonder");
                System.out.print("Görev: ");
                secim2 = sc.nextInt();

                if (secim2 == 1) {
                    Kullanicilar.mailGonder(elitler);
                }
                else if (secim2 == 2) {
                    Kullanicilar.mailGonder(geneller);
                }
                else if (secim2 == 3) {
                    Kullanicilar.mailGonder(elitler);
                    Kullanicilar.mailGonder(geneller);
                }
            }
        }
// dosyayı kapatma
        sc.close();
    }
}