package br.com.tdc2014.jobs;

import br.com.tdc2014.Sessao;
import org.apache.log4j.Logger;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
public class GeradorChaveSessao implements Runnable {

    private static final Logger logger = Logger.getLogger(Tarefa.class);
    private final String key;
    private final boolean forceError;
    private final Sessao session;

    public GeradorChaveSessao(String key, Sessao session, boolean forceError) {
        this.key = key;
        this.forceError = forceError;
        this.session = session;
    }

    @Override
    public void run() {
        try {
            logger.info(String.format("Thread começou session-key: %s", key));
            if (forceError) {
                throw new InterruptedException("Esta thread foi interrompida!");
            }
            session.put(key);
            logger.info(String.format("Thread terminou session-key: %s", key));
        } catch (InterruptedException ex) {
            logger.error(null, ex);
        }
    }

}
