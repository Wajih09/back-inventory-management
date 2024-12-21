package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.ClientDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.models.Client;
import com.tech.gestiondestock.repository.ClientDao;
import com.tech.gestiondestock.services.ClientService;
import com.tech.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> errors = ClientValidator.validate(clientDto);
        if(!errors.isEmpty()){
            log.error("Client is not valid");
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(clientDao.save(ClientDto.toEntity(clientDto)));
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id==null){
            log.error("L'Id du client est null");
            return null; 
        }

        Optional<Client> client = clientDao.findById(id);
        return ClientDto.fromEntity(client.orElseThrow(() -> new EntityNotFoundException("Le client avec ID " + id +" n'est pas trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND)));
    }

    @Override
    public ClientDto findByNom(String nom) {
        if(!StringUtils.hasLength(nom)){
            log.error("Le nom du client est null");
            return null;
        }

        Optional<Client> client = clientDao.findByNom(nom);
        return ClientDto.fromEntity(client.orElseThrow(() -> new EntityNotFoundException("Le client avec nom " + nom +" n'est pas trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND)));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientDao.findAll().stream().map(ClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error(("Delete ID is null"));
            return;
        }
        clientDao.deleteById(id);
    }
}
