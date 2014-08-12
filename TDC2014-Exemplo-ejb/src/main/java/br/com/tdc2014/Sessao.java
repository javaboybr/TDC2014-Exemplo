package br.com.tdc2014;

import javax.ejb.Remote;

/**
 *
 * @author martin
 */
@Remote
public interface Sessao {

    public void put(String key);

    public boolean isValid(String key);
}
