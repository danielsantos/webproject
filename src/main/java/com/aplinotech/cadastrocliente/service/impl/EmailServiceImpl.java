package com.aplinotech.cadastrocliente.service.impl;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.service.EmailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;


public class EmailServiceImpl implements EmailService {

    @Override
    public void confirmarCadastro(Usuario usuario) {
        try {
            String texto = "Olá " + usuario.getNome() + ", " +
                    " \n\n" +
                    " Clique no link abaixo para confirmar seu cadastro no Vem Leiloar: " +
                    " \n\n " +
                    " http://localhost:8080/usuario/confirmaCadastro/" + usuario.getTokenCadastro();

            Email email = getEmail(usuario);
            email.setSubject("Vem Leiloar! - Confirmação de Cadastro");
            email.setMsg(texto);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Email getEmail(Usuario usuario) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.vemleiloar.com.br");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("contato@vemleiloar.com.br", "Alina200263"));
            email.setSSL(false);
            email.setFrom("contato@vemleiloar.com.br");
            email.addTo(usuario.getEmail());
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
