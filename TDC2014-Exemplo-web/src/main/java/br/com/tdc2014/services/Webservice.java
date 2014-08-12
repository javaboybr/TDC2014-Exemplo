package br.com.tdc2014.services;

import br.com.tdc2014.Sessao;
import br.com.tdc2014.jobs.GeradorChaveSessao;
import br.com.tdc2014.jobs.Tarefa;
import com.google.gson.Gson;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
@Stateless
@Path("myTest")
public class Webservice {

    @Context
    private UriInfo context;
    @Resource(lookup = "MyExec")
    private ManagedExecutorService highPrioExecutor;
    @Resource(lookup = "MyThreadFacs")
    private ManagedThreadFactory threadFac;
    private static final Logger logger = Logger.getLogger(Webservice.class);
    @EJB
    private Sessao session;

    @GET
    @Produces("application/json")
    @Path("verifyKey/{key}")
    public String recuperaChave(@PathParam("key") String key) {
        try {
            Map<String, String> result = new TreeMap<>();
            //verifica uma chave de sessao e aguarda o retorno da thread
            Future<String> submit = (Future<String>) highPrioExecutor.submit(new Tarefa(key, session));
            result.put("status", submit.get());
            result.put("object", "none");
            result.put("context", context.getPath());
            return new Gson().toJson(result);
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(null, ex);
        }
        return null;
    }

    @GET
    @Produces("application/json")
    @Path("geraChave")
    public String geraChave() {
        Map<String, String> result = new TreeMap<>();
        UUID sessionKey = UUID.randomUUID();
        //gera uma chave de sessao e nao espera o fim da thread
        threadFac.newThread(new GeradorChaveSessao(sessionKey.toString(), session, false)).start();
        result.put("status", "success");
        result.put("session-key", sessionKey.toString());
        return new Gson().toJson(result);
    }

    @GET
    @Produces("application/json")
    @Path("forcaErro")
    public String errorThread() {
        Map<String, String> result = new TreeMap<>();
        UUID sessionKey = UUID.randomUUID();
        //Força um erro de interrupçao na criaçao de uma thread não gerenciada
        new Thread(new GeradorChaveSessao(sessionKey.toString(), session, true)).start();
        result.put("status", "");
        return new Gson().toJson(result);
    }

    @GET
    @Produces("application/json")
    @Path("forcaErroGerenciado")
    public String errorManagedThread() {
        Map<String, String> result = new TreeMap<>();
        UUID sessionKey = UUID.randomUUID();
        //Força um erro de interrupçao na criaçao de uma thread gerenciada
        threadFac.newThread(new GeradorChaveSessao(sessionKey.toString(), session, true)).start();
        result.put("status", "");
        return new Gson().toJson(result);
    }
}
