/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tdc2014;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import org.apache.log4j.Logger;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class SessaoImpl implements Sessao {

    private static final Logger logger = Logger.getLogger(Sessao.class);

    private final List<String> store;

    SessaoImpl() {
        this.store = new ArrayList<>();
    }

    @Lock(LockType.WRITE)
    @Override
    public void put(String key) {
        logger.info(String.format("new session started: %s", key));
        store.add(key);
    }

    @Lock(LockType.READ)
    @Override
    public boolean isValid(String key) {
        return store.contains(key);
    }
}
