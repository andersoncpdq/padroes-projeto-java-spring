package com.acpdq.diopadroesprojeto.services.impl;

import com.acpdq.diopadroesprojeto.entities.Address;
import com.acpdq.diopadroesprojeto.entities.Client;
import com.acpdq.diopadroesprojeto.repositories.AddressRepository;
import com.acpdq.diopadroesprojeto.repositories.ClientRepository;
import com.acpdq.diopadroesprojeto.services.ClientService;
import com.acpdq.diopadroesprojeto.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClientService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 */

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public void insert(Client client) {
        saveClient(client);
    }

    @Override
    public void update(Long id, Client client) {
        Optional<Client> cli = clientRepository.findById(id);
        if (cli.isPresent())
            saveClient(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private void saveClient(Client client) {
        String cep = client.getAddress().getCep();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Address newAddress = viaCepService.consultarCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });
        client.setAddress(address);
        clientRepository.save(client);
    }
}
