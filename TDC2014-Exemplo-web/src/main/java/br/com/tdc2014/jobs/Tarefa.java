/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tdc2014.jobs;

import br.com.tdc2014.Sessao;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
public class Tarefa implements Callable<String> {

    private static final Logger logger = Logger.getLogger(Tarefa.class);
    private final String key;
    private final Sessao session;

    public Tarefa(String key, Sessao session) {
        this.key = key;
        this.session = session;
    }

    @Override
    public String call() throws Exception {
        logger.info(String.format("verificando chave %s", key));
        boolean isValid = session.isValid(key);
        String toString = Boolean.toString(isValid);
        if (isValid) {
            logger.info(String.format("chave %s é valida", key));
        } else {
            logger.info(String.format("chave %s é invalida", key));
        }
        return toString;
    }

}
