/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author system
 */
public class AppQueryFiguraMonografia {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Vote voto = new Vote();
        Counting apuracao = new Counting();
        Campaign campanha = new Campaign();
        Party partido = new Party();
        Elector eleitor = new Elector();
        Campaigner caboEleitoral = new Campaigner();
        Representative deputado = new Representative();
        Senator senador = new Senator();

//        Query q1 = new Query();
//        q1.from(EntitySenador.class);
//        q1.where(new Equal(EntitySenador.$tituloEleitor, senador.getIdentityCard()));
//        Collection<Senator> resSenador = q1.execute();
//        
//        Query q2 = new Query();
//        q2.from(EntityDeputado.class);
//        q2.where(new Equal(EntityDeputado.$tituloEleitor, deputado.getIdentityCard()));
//        Collection<Representative> resDeputado = q2.execute();
//
//        Query q3 = new Query();
//        q3.from(EntityCaboEleitoral.class);
//        q3.where(new Equal(EntityCaboEleitoral.$tituloEleitor, caboEleitoral.getIdentityCard()));
//        Collection<Campaigner> resCaboEleitoral = q3.execute();
//
//        Query q4 = new Query();
//        q4.from(EntityEleitor.class);
//        q4.where(new Equal(EntityEleitor.$tituloEleitor, eleitor.getIdentityCard()));
//        Collection<Elector> resEleitor = q4.execute();
//
//        Query q5 = new Query();
//        q5.from(EntityPartido.class);
//        q5.where(new Equal(EntityPartido.$sigla, partido.getSigla()));
//        Collection<Party> resPartido = q5.execute();
//
//        Query q6 = new Query();
//        q6.from(EntityCampanha.class);
//        q6.where(new Equal(EntityCampanha.$numeroRegistro, campanha.getNumeroRegistro()));
//        Collection<Campaign> resCampanha = q6.execute();
//
//        Query q7 = new Query();
//        q7.from(EntityVoto.class);
//        q7.where(new Equal(EntityVoto.$registroEletronico, voto.getRegistroEletronico()));
//        Collection<Vote> resVoto = q7.execute();
//
//        Query q8 = new Query();
//        q8.from(EntityApuracao.class);
//        q8.where(new Equal(EntityApuracao.$numeroApuracao, apuracao.getNumeroApuracao()));
//        Collection<Counting> resApuracao = q8.execute();
    }
}
