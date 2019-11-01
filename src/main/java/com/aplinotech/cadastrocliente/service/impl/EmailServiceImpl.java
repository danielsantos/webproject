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
                    " http://localhost:8080/confirmaCadastro/" + usuario.getTokenCadastro();

            Email email = getEmail(usuario);
            email.setSubject("Estoque Simples - Confirmação de Cadastro");
            email.setMsg(texto);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Email getEmail(Usuario usuario) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.estoquesimples.com.br");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("contato@estoquesimples.com.br", "*****"));
            email.setSSL(false);
            email.setFrom("naoresponda@estoquesimples.com.br");
            email.addTo(usuario.getEmail());
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
