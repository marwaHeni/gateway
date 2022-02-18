package com.mapsit.jamaity.ptf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mapsit.jamaity.association.Association;
import com.mapsit.jamaity.config.StorageProperties;
import com.mapsit.jamaity.contactPtf.ContactPtf;
import com.mapsit.jamaity.contactPtf.ContactPtfRepository;
import com.mapsit.jamaity.location.Location;
import com.mapsit.jamaity.prioritePtf.PrioritePtf;
import com.mapsit.jamaity.prioritePtf.PrioritePtfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Service Implementation
 */
@Service
@Transactional
public class PtfService {

    private final Logger log = LoggerFactory.getLogger(PtfService.class);

    private final PtfRepository ptfRepository;

    private final StorageProperties storageProps;

    private final ContactPtfRepository contactPtfRepository;

    private final PrioritePtfRepository prioritePtfRepository;

    public PtfService(PtfRepository ptfRepository, StorageProperties storageProps, ContactPtfRepository contactPtfRepository, PrioritePtfRepository prioritePtfRepository) {

        this.ptfRepository = ptfRepository;
        this.storageProps = storageProps;
        this.contactPtfRepository = contactPtfRepository;
        this.prioritePtfRepository = prioritePtfRepository;
    }

    /**
     * Save a ptf.
     *
     * @param ptf the entity to save.
     * @return the persisted entity.
     */
    public Ptf save(Ptf ptf) throws IOException {
        log.debug("Request to save Ptf : {}", ptf);


        if (ptf.getAvatar() != null) {

            String path = storageProps.getPath();
            String realPath = path.substring(7, path.length());

            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);
            String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            //  String fileName = imageItem.getName();

            int locationofExtension = ptf.getImage().lastIndexOf('.');
            String extension = ptf.getImage().substring(locationofExtension, ptf.getImage().length());
            String nameWithoutExtension = ptf.getImage().substring(0, locationofExtension);
            String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;
            //   System.out.println(newNameOfImage+"   name of Image");

            String pathPtf = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/ptfs/" + newNameOfImage;

            ptf.setImage(pathPtf);
            ptfRepository.save(ptf);
            String ptfFolder = realPath + "/jamaityImagesMicro/ptfs/" + newNameOfImage;


            Path rootItems = Paths.get(ptfFolder);
            byte[] scanBytes = Base64.getDecoder().decode(ptf.getAvatar());
            Files.write(rootItems, scanBytes);
        }

        return ptfRepository.save(ptf);
    }


    /**
     * Update a ptf.
     *
     * @param ptf the entity to save.
     * @return the persisted entity.
     */
    public Ptf update(Ptf ptf) throws IOException {
        log.debug("Request to save Ptf : {}", ptf);

        Optional<Ptf> ptfDb = ptfRepository.findById(ptf.getId());

        if (ptf.getAvatar() != null) {

            String imageUrl = ptfRepository.getOne(ptfDb.get().getId()).getImage();

            int locationofSlash = imageUrl.lastIndexOf('/');
            String imageName = imageUrl.substring(locationofSlash, imageUrl.length());


            String path = storageProps.getPath();
            String realPath = path.substring(7, path.length());
            String ptfsFolder = realPath + "/jamaityImagesMicro/ptfs/" + imageName;
            Path rootPtfs = Paths.get(ptfsFolder);
            Files.delete(rootPtfs);


            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);
            String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

            int locationofExtension = ptf.getImage().lastIndexOf('.');
            String extension = ptf.getImage().substring(locationofExtension, ptf.getImage().length());
            String nameWithoutExtension = ptf.getImage().substring(0, locationofExtension);
            String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;
            //   System.out.println(newNameOfImage+"   name of Image");

            String pathPtfImage = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/ptfs/" + newNameOfImage;

            ptf.setImage(pathPtfImage);
            ptfRepository.save(ptf);
            String ptfFolder = realPath + "/jamaityImagesMicro/ptfs/" + newNameOfImage;


            Path rootPtfss = Paths.get(ptfFolder);
            byte[] scanBytes = Base64.getDecoder().decode(ptf.getImage());
            Files.write(rootPtfss, scanBytes);
        }


        Set<ContactPtf> contactPtfSet = ptfDb.get().getContactPtfs();
        if (contactPtfSet != null && contactPtfSet.size() != 0) {

            for (ContactPtf contactPtf : contactPtfSet) {

                contactPtfRepository.delete(contactPtf);

            }

        }


        Set<PrioritePtf> prioritePtfSet = ptfDb.get().getPrioritetPtfs();
        if (prioritePtfSet != null && prioritePtfSet.size() != 0) {

            for (PrioritePtf prioritePtf : prioritePtfSet) {

                prioritePtfRepository.delete(prioritePtf);

            }

        }

        return ptfRepository.save(ptf);
    }

    /**
     * Get all the ptfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ptf> findAll(Pageable pageable) {
        log.debug("Request to get all Ptfs");
        return ptfRepository.findAll(pageable);
    }


    /**
     * Get all the ptfs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Ptf> findAll() {
        log.debug("Request to get all Ptfs");
        return ptfRepository.findAll();
    }


    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ptf> findAll(String lang, Pageable pageable) {
        log.debug("Request to get all Associations");

        List<Ptf> ptfList = ptfRepository.findAll();
        List<Ptf> ptfTranslatedList = new ArrayList<>();


        if (ptfList != null && ptfList.size() != 0) {
            for (Ptf ptf : ptfList) {


                if (lang.equals("fr")) {

                    Ptf ptfTranslated = new Ptf();
                    ptfTranslated.setId(ptf.getId());
                    ptfTranslated.setTitle(ptf.getTitleFr());
                    ptfTranslated.setAbreviation(ptf.getAbreviationFr());
                    ptfTranslated.setAnneeFondation(ptf.getAnneeFondation());
                    ptfTranslated.setDescription(ptf.getDescriptionFr());
                    ptfTranslated.setEtat(ptf.getEtat());
                    ptfTranslated.setVisibilite(ptf.getVisibilite());
                    ptfTranslated.setDatePublication(ptf.getDatePublication());
                    ptfTranslated.setCouleur(ptf.getCouleur());
                    ptfTranslated.setMission(ptf.getMissionFr());
                    ptfTranslated.setEmail(ptf.getEmail());
                    ptfTranslated.setSiteWeb(ptf.getSiteWeb());
                    ptfTranslated.setPhoneFixe(ptf.getPhoneFixe());
                    ptfTranslated.setPhoneMobile(ptf.getPhoneMobile());
                    ptfTranslated.setFax(ptf.getFax());
                    ptfTranslated.setFacebook(ptf.getFacebook());
                    ptfTranslated.setTwitter(ptf.getTwitter());
                    ptfTranslated.setYoutube(ptf.getYoutube());
                    ptfTranslated.setGooglePlus(ptf.getGooglePlus());
                    ptfTranslated.setLinkedin(ptf.getLinkedin());
                    ptfTranslated.setNote(ptf.getNote());
                    ptfTranslated.setUserLogin(ptf.getUserLogin());
                    ptfTranslated.setUserName(ptf.getUserName());
                    ptfTranslated.setImage(ptf.getImage());

                    ptfTranslatedList.add(ptfTranslated);

                }


                if (lang.equals("ar")) {

                    Ptf ptfTranslated = new Ptf();
                    ptfTranslated.setId(ptf.getId());
                    ptfTranslated.setTitle(ptf.getTitleAr());
                    ptfTranslated.setAbreviation(ptf.getAbreviationAr());
                    ptfTranslated.setAnneeFondation(ptf.getAnneeFondation());
                    ptfTranslated.setDescription(ptf.getDescriptionAr());
                    ptfTranslated.setEtat(ptf.getEtat());
                    ptfTranslated.setVisibilite(ptf.getVisibilite());
                    ptfTranslated.setDatePublication(ptf.getDatePublication());
                    ptfTranslated.setCouleur(ptf.getCouleur());
                    ptfTranslated.setMission(ptf.getMissionAr());
                    ptfTranslated.setEmail(ptf.getEmail());
                    ptfTranslated.setSiteWeb(ptf.getSiteWeb());
                    ptfTranslated.setPhoneFixe(ptf.getPhoneFixe());
                    ptfTranslated.setPhoneMobile(ptf.getPhoneMobile());
                    ptfTranslated.setFax(ptf.getFax());
                    ptfTranslated.setFacebook(ptf.getFacebook());
                    ptfTranslated.setTwitter(ptf.getTwitter());
                    ptfTranslated.setYoutube(ptf.getYoutube());
                    ptfTranslated.setGooglePlus(ptf.getGooglePlus());
                    ptfTranslated.setLinkedin(ptf.getLinkedin());
                    ptfTranslated.setNote(ptf.getNote());
                    ptfTranslated.setUserLogin(ptf.getUserLogin());
                    ptfTranslated.setUserName(ptf.getUserName());
                    ptfTranslated.setImage(ptf.getImage());

                    ptfTranslatedList.add(ptfTranslated);

                }
            }
        }


        int start = Math.min((int) pageable.getOffset(), ptfTranslatedList.size());
        int end = Math.min((start + pageable.getPageSize()), ptfTranslatedList.size());

        final Page<Ptf> page = new PageImpl<>(ptfTranslatedList.subList(start, end), pageable, ptfTranslatedList.size());
        return page;

    }

    /**
     * Get one ptf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ptf> findOne(UUID id) {
        log.debug("Request to get Ptf : {}", id);
        return ptfRepository.findById(id);
    }


    /**
     * Get one ptf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ptf> findOne(UUID id, String lang) {
        log.debug("Request to get Ptf : {}", id);
        Optional<Ptf> ptf = ptfRepository.findById(id);
        Location location = ptf.get().getLocation();
        Ptf ptfTranslated = new Ptf();
        Location locationTranslated = new Location();


        if (lang.equals("fr")) {

            ptfTranslated.setId(ptf.get().getId());
            ptfTranslated.setTitle(ptf.get().getTitleFr());
            ptfTranslated.setAbreviation(ptf.get().getAbreviationFr());
            ptfTranslated.setAnneeFondation(ptf.get().getAnneeFondation());
            ptfTranslated.setDescription(ptf.get().getDescriptionFr());
            ptfTranslated.setEtat(ptf.get().getEtat());
            ptfTranslated.setVisibilite(ptf.get().getVisibilite());
            ptfTranslated.setDatePublication(ptf.get().getDatePublication());
            ptfTranslated.setCouleur(ptf.get().getCouleur());
            ptfTranslated.setMission(ptf.get().getMissionFr());
            ptfTranslated.setEmail(ptf.get().getEmail());
            ptfTranslated.setSiteWeb(ptf.get().getSiteWeb());
            ptfTranslated.setPhoneFixe(ptf.get().getPhoneFixe());
            ptfTranslated.setPhoneMobile(ptf.get().getPhoneMobile());
            ptfTranslated.setFax(ptf.get().getFax());
            ptfTranslated.setFacebook(ptf.get().getFacebook());
            ptfTranslated.setTwitter(ptf.get().getTwitter());
            ptfTranslated.setYoutube(ptf.get().getYoutube());
            ptfTranslated.setGooglePlus(ptf.get().getGooglePlus());
            ptfTranslated.setLinkedin(ptf.get().getLinkedin());
            ptfTranslated.setNote(ptf.get().getNote());
            ptfTranslated.setUserLogin(ptf.get().getUserLogin());
            ptfTranslated.setUserName(ptf.get().getUserName());
            ptfTranslated.setImage(ptf.get().getImage());

            //associationLocation

            locationTranslated.setId(location.getId());
            locationTranslated.setLocalNumber(location.getLocalNumber());
            locationTranslated.setStreetAddress(location.getStreetAddressFr());
            locationTranslated.setPostalCode(location.getPostalCode());
            locationTranslated.setCity(location.getCityFr());
            locationTranslated.setStateProvince(location.getStateProvinceFr());
            locationTranslated.setCountryName(location.getCountryNameFr());

            ptfTranslated.setLocation(locationTranslated);

        }


        if (lang.equals("ar")) {

            ptfTranslated.setId(ptf.get().getId());
            ptfTranslated.setTitle(ptf.get().getTitleAr());
            ptfTranslated.setAbreviation(ptf.get().getAbreviationAr());
            ptfTranslated.setAnneeFondation(ptf.get().getAnneeFondation());
            ptfTranslated.setDescription(ptf.get().getDescriptionAr());
            ptfTranslated.setEtat(ptf.get().getEtat());
            ptfTranslated.setVisibilite(ptf.get().getVisibilite());
            ptfTranslated.setDatePublication(ptf.get().getDatePublication());
            ptfTranslated.setCouleur(ptf.get().getCouleur());
            ptfTranslated.setMission(ptf.get().getMissionAr());
            ptfTranslated.setEmail(ptf.get().getEmail());
            ptfTranslated.setSiteWeb(ptf.get().getSiteWeb());
            ptfTranslated.setPhoneFixe(ptf.get().getPhoneFixe());
            ptfTranslated.setPhoneMobile(ptf.get().getPhoneMobile());
            ptfTranslated.setFax(ptf.get().getFax());
            ptfTranslated.setFacebook(ptf.get().getFacebook());
            ptfTranslated.setTwitter(ptf.get().getTwitter());
            ptfTranslated.setYoutube(ptf.get().getYoutube());
            ptfTranslated.setGooglePlus(ptf.get().getGooglePlus());
            ptfTranslated.setLinkedin(ptf.get().getLinkedin());
            ptfTranslated.setNote(ptf.get().getNote());
            ptfTranslated.setUserLogin(ptf.get().getUserLogin());
            ptfTranslated.setUserName(ptf.get().getUserName());
            ptfTranslated.setImage(ptf.get().getImage());

            //associationLocation

            locationTranslated.setId(location.getId());
            locationTranslated.setLocalNumber(location.getLocalNumber());
            locationTranslated.setStreetAddress(location.getStreetAddressAr());
            locationTranslated.setPostalCode(location.getPostalCode());
            locationTranslated.setCity(location.getCityAr());
            locationTranslated.setStateProvince(location.getStateProvinceAr());
            locationTranslated.setCountryName(location.getCountryNameAr());

            ptfTranslated.setLocation(locationTranslated);

        }

        return Optional.of(ptfTranslated);
    }


    /**
     * Delete the ptf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Ptf : {}", id);
        ptfRepository.deleteById(id);
    }


    /**
     * search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ptf> searchPtf(String attribute, String lang, Pageable pageable) {
        log.debug("Request to get all Associations");

        List<Ptf> ptfList = ptfRepository.findAllPtfsWithPartOfAttribute(attribute);
        List<Ptf> ptfTranslatedList = new ArrayList<>();


        if (ptfList != null && ptfList.size() != 0) {
            for (Ptf ptf : ptfList) {


                if (lang.equals("fr")) {

                    Ptf ptfTranslated = new Ptf();
                    ptfTranslated.setId(ptf.getId());
                    ptfTranslated.setTitle(ptf.getTitleFr());
                    ptfTranslated.setAbreviation(ptf.getAbreviationFr());
                    ptfTranslated.setAnneeFondation(ptf.getAnneeFondation());
                    ptfTranslated.setDescription(ptf.getDescriptionFr());
                    ptfTranslated.setEtat(ptf.getEtat());
                    ptfTranslated.setVisibilite(ptf.getVisibilite());
                    ptfTranslated.setDatePublication(ptf.getDatePublication());
                    ptfTranslated.setCouleur(ptf.getCouleur());
                    ptfTranslated.setMission(ptf.getMissionFr());
                    ptfTranslated.setEmail(ptf.getEmail());
                    ptfTranslated.setSiteWeb(ptf.getSiteWeb());
                    ptfTranslated.setPhoneFixe(ptf.getPhoneFixe());
                    ptfTranslated.setPhoneMobile(ptf.getPhoneMobile());
                    ptfTranslated.setFax(ptf.getFax());
                    ptfTranslated.setFacebook(ptf.getFacebook());
                    ptfTranslated.setTwitter(ptf.getTwitter());
                    ptfTranslated.setYoutube(ptf.getYoutube());
                    ptfTranslated.setGooglePlus(ptf.getGooglePlus());
                    ptfTranslated.setLinkedin(ptf.getLinkedin());
                    ptfTranslated.setNote(ptf.getNote());
                    ptfTranslated.setUserLogin(ptf.getUserLogin());
                    ptfTranslated.setUserName(ptf.getUserName());
                    ptfTranslated.setImage(ptf.getImage());

                    ptfTranslatedList.add(ptfTranslated);

                }


                if (lang.equals("ar")) {

                    Ptf ptfTranslated = new Ptf();
                    ptfTranslated.setId(ptf.getId());
                    ptfTranslated.setTitle(ptf.getTitleAr());
                    ptfTranslated.setAbreviation(ptf.getAbreviationAr());
                    ptfTranslated.setAnneeFondation(ptf.getAnneeFondation());
                    ptfTranslated.setDescription(ptf.getDescriptionAr());
                    ptfTranslated.setEtat(ptf.getEtat());
                    ptfTranslated.setVisibilite(ptf.getVisibilite());
                    ptfTranslated.setDatePublication(ptf.getDatePublication());
                    ptfTranslated.setCouleur(ptf.getCouleur());
                    ptfTranslated.setMission(ptf.getMissionAr());
                    ptfTranslated.setEmail(ptf.getEmail());
                    ptfTranslated.setSiteWeb(ptf.getSiteWeb());
                    ptfTranslated.setPhoneFixe(ptf.getPhoneFixe());
                    ptfTranslated.setPhoneMobile(ptf.getPhoneMobile());
                    ptfTranslated.setFax(ptf.getFax());
                    ptfTranslated.setFacebook(ptf.getFacebook());
                    ptfTranslated.setTwitter(ptf.getTwitter());
                    ptfTranslated.setYoutube(ptf.getYoutube());
                    ptfTranslated.setGooglePlus(ptf.getGooglePlus());
                    ptfTranslated.setLinkedin(ptf.getLinkedin());
                    ptfTranslated.setNote(ptf.getNote());
                    ptfTranslated.setUserLogin(ptf.getUserLogin());
                    ptfTranslated.setUserName(ptf.getUserName());
                    ptfTranslated.setImage(ptf.getImage());

                    ptfTranslatedList.add(ptfTranslated);

                }

            }
        }


        int start = Math.min((int) pageable.getOffset(), ptfTranslatedList.size());
        int end = Math.min((start + pageable.getPageSize()), ptfTranslatedList.size());

        final Page<Ptf> page = new PageImpl<>(ptfTranslatedList.subList(start, end), pageable, ptfTranslatedList.size());
        return page;

    }


}
