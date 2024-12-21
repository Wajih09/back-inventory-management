package com.tech.gestiondestock.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.tech.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

    @Autowired //si on commente le @Bean dans FlickrConfiguration alors erreur ici v15 min40
    private Flickr flickr;

    //on peut faire injection par constructeur  v15 min41
    @Autowired
    public FlickrServiceImpl(Flickr flickr) {
        this.flickr = flickr;
    }


    private void connect(){ //on aura pas besoin de la connexion car flick sera automatiquement injecté par le autowired qui est lié au Bean dans la flickrConfig la ou il ya cette connexion
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }

    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        try {
        	connect();
            UploadMetaData uploadMetaData = new UploadMetaData();
            uploadMetaData.setTitle(title);
            String photoId = flickr.getUploader().upload(photo, uploadMetaData);
            return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
        } catch (FlickrException e) {
            log.error("Failed to upload photo to Flickr: ", e);
            throw e;
        }
    }

}
