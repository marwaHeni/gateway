package com.mapsit.jamaity.association;

import com.mapsit.jamaity.location.Location;
import com.mapsit.jamaity.config.StorageProperties;
import com.mapsit.jamaity.imageAssociation.ImageAssociation;
import com.mapsit.jamaity.imageAssociation.ImageAssociationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service Implementation
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    private final StorageProperties storageProps;

    private final ImageAssociationRepository imageAssociationRepository;

    public AssociationService(AssociationRepository associationRepository, StorageProperties storageProps, ImageAssociationRepository imageAssociationRepository) {

        this.associationRepository = associationRepository;
        this.storageProps = storageProps;
        this.imageAssociationRepository = imageAssociationRepository;
    }

    /**
     * Save a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public Association save(Association association) throws IOException {
        log.debug("Request to save Association : {}", association);

        if (association.getAvatar() != null) {

            String path = storageProps.getPath();
            String realPath = path.substring(7, path.length());

            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);
            String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            //  String fileName = imageItem.getName();
            System.out.println("association.getLogo() "+ association.getLogo());
            int locationofExtension = association.getLogo().lastIndexOf('.');
            String extension = association.getLogo().substring(locationofExtension, association.getLogo().length());
            String nameWithoutExtension = association.getLogo().substring(0, locationofExtension);
            String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;
            //   System.out.println(newNameOfImage+"   name of Image");

            String pathAssociationLogo = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/associations/" + newNameOfImage;

            association.setLogo(pathAssociationLogo);
            associationRepository.save(association);
            String paymentEshopFolder = realPath + "/jamaityImagesMicro/associations/" + newNameOfImage;


            Path rootItems = Paths.get(paymentEshopFolder);
            byte[] scanBytes = Base64.getDecoder().decode(association.getAvatar());
            Files.write(rootItems, scanBytes);
        }

        System.out.println("association.getImageAssociations().size() "+association.getImageAssociations().size());

        Set<ImageAssociation> imageAssociationSet = association.getImageAssociations();


        Set<ImageAssociation> imageAssociationList = new HashSet<>();

        if(imageAssociationSet != null){

            String path = storageProps.getPath();
            String realPath = path.substring(7, path.length());
            Random rand2 = new Random();
            for (ImageAssociation imageAssociation : imageAssociationSet) {

                int rand_int1 = rand2.nextInt(1000);
                String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String fileName = imageAssociation.getName();

                int locationofExtension = fileName.lastIndexOf('.');
                String extension = fileName.substring(locationofExtension, fileName.length());
                String nameWithoutExtension = fileName.substring(0, locationofExtension);
                String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;

                String pathImageAssociations = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/imageAssociations/" + newNameOfImage;

                imageAssociation.setUrl(pathImageAssociations);
                imageAssociation.setAssociation(association);
                imageAssociationRepository.save(imageAssociation);
                imageAssociationList.add(imageAssociation);

                String itemsFolder = realPath + "/jamaityImagesMicro/imageAssociations/" + newNameOfImage;


                Path rootItems = Paths.get(itemsFolder);
                byte[] scanBytes = Base64.getDecoder().decode(imageAssociation.getAvatar());
                Files.write(rootItems, scanBytes);

            }

        }

        association.setImageAssociations(imageAssociationList);
        //    savedItem.setIsDeleted(false);
        associationRepository.save(association);


        return association;
    }




    /**
     * Update a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public Association update(Association association) throws IOException {
        log.debug("Request to save Association : {}", association);

        Optional<Association> associationDb = associationRepository.findById(association.getId());

        Set<ImageAssociation> imageAssociationsDbList = associationDb.get().getImageAssociations();

    if(imageAssociationsDbList != null && imageAssociationsDbList.size() != 0){
        for (ImageAssociation imageAssociation : imageAssociationsDbList) {

            String imageUrl = imageAssociationRepository.getOne(imageAssociation.getId()).getUrl();

            int locationofSlash = imageUrl.lastIndexOf('/');
            String imageName = imageUrl.substring(locationofSlash, imageUrl.length());

            String path = storageProps.getPath();
            String realPath = path.substring(7,path.length());
            String imageAssociationsFolder = realPath+"/jamaityImagesMicro/imageAssociations/"+ imageName;
            Path rootImageAssociations = Paths.get(imageAssociationsFolder);
            Files.delete(rootImageAssociations);
            imageAssociationRepository.deleteById(imageAssociation.getId());
        }
    }

        if (association.getAvatar() != null) {

            String imageUrl = associationRepository.getOne(associationDb.get().getId()).getLogo();

            int locationofSlash = imageUrl.lastIndexOf('/');
            String logoName = imageUrl.substring(locationofSlash, imageUrl.length());


            String path = storageProps.getPath();
            String realPath = path.substring(7,path.length());
            String associationsFolder = realPath+"/jamaityImagesMicro/associations/"+ logoName;
            Path rootAssociations = Paths.get(associationsFolder);
            Files.delete(rootAssociations);


            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);
            String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            //  String fileName = imageItem.getName();
            System.out.println("association.getLogo() "+ association.getLogo());
            int locationofExtension = association.getLogo().lastIndexOf('.');
            String extension = association.getLogo().substring(locationofExtension, association.getLogo().length());
            String nameWithoutExtension = association.getLogo().substring(0, locationofExtension);
            String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;
            //   System.out.println(newNameOfImage+"   name of Image");

            String pathAssociationLogo = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/associations/" + newNameOfImage;

            association.setLogo(pathAssociationLogo);
            associationRepository.save(association);
            String paymentEshopFolder = realPath + "/jamaityImagesMicro/associations/" + newNameOfImage;


            Path rootItems = Paths.get(paymentEshopFolder);
            byte[] scanBytes = Base64.getDecoder().decode(association.getAvatar());
            Files.write(rootItems, scanBytes);
        }

        System.out.println("association.getImageAssociations().size() "+association.getImageAssociations().size());

        Set<ImageAssociation> imageAssociationSet = association.getImageAssociations();


        Set<ImageAssociation> imageAssociationList = new HashSet<>();

        if(imageAssociationSet != null){

            String path = storageProps.getPath();
            String realPath = path.substring(7, path.length());
            Random rand2 = new Random();
            for (ImageAssociation imageAssociation : imageAssociationSet) {

                int rand_int1 = rand2.nextInt(1000);
                String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String fileName = imageAssociation.getName();

                int locationofExtension = fileName.lastIndexOf('.');
                String extension = fileName.substring(locationofExtension, fileName.length());
                String nameWithoutExtension = fileName.substring(0, locationofExtension);
                String newNameOfImage = nameWithoutExtension + currentDate + rand_int1 + extension;

                String pathImageAssociations = storageProps.getUrl() + "/jamaity-service/resources/jamaityImagesMicro/imageAssociations/" + newNameOfImage;

                imageAssociation.setUrl(pathImageAssociations);
                imageAssociation.setAssociation(association);
                imageAssociationRepository.save(imageAssociation);
                imageAssociationList.add(imageAssociation);

                String itemsFolder = realPath + "/jamaityImagesMicro/imageAssociations/" + newNameOfImage;


                Path rootItems = Paths.get(itemsFolder);
                byte[] scanBytes = Base64.getDecoder().decode(imageAssociation.getAvatar());
                Files.write(rootItems, scanBytes);

            }

        }

        association.setImageAssociations(imageAssociationList);
        //    savedItem.setIsDeleted(false);
        associationRepository.save(association);


        return association;
    }

    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Association> findAll(Pageable pageable) {
        log.debug("Request to get all Associations");
        return associationRepository.findAll(pageable);
    }

        /**
         * Get all the associations.
         *
         * @param pageable the pagination information.
         * @return the list of entities.
         */
    @Transactional(readOnly = true)
    public Page<Association> findAll(String lang, Pageable pageable) {
        log.debug("Request to get all Associations");

        List<Association> associationList = associationRepository.get_associations();
        List<Association> associationTranslatedList = new ArrayList<>();


        if (associationList != null && associationList.size() != 0) {
            for (Association association : associationList) {


                 if (lang.equals("fr")) {

                    Association associationTranslated = new Association();
                    associationTranslated.setId(association.getId());
                    associationTranslated.setLogo(association.getLogo());
                    associationTranslated.setTitle(association.getTitleFr());
                    associationTranslated.setDeuxVisaVisAssociation(association.getDeuxVisaVisAssociationFr());
                    associationTranslated.setAbreviation(association.getAbreviationFr());
                    associationTranslated.setAnneeFondation(association.getAnneeFondation());
                    associationTranslated.setDescription(association.getDescriptionFr());
                    associationTranslated.setEtat(association.getEtat());
                    associationTranslated.setVisibilite(association.getVisibilite());
                    associationTranslated.setDatePublication(association.getDatePublication());
                    associationTranslated.setPresident(association.getPresidentFr());
                    associationTranslated.setEmail(association.getEmail());
                    associationTranslated.setSiteWeb(association.getSiteWeb());
                    associationTranslated.setPhoneFixe(association.getPhoneFixe());
                    associationTranslated.setPhoneMobile(association.getPhoneMobile());
                    associationTranslated.setFax(association.getFax());
                    associationTranslated.setFacebook(association.getFacebook());
                    associationTranslated.setTwitter(association.getTwitter());
                    associationTranslated.setYoutube(association.getYoutube());
                    associationTranslated.setGooglePlus(association.getGooglePlus());
                    associationTranslated.setLinkedin(association.getLinkedin());
                    associationTranslated.setNote(association.getNote());
                    associationTranslated.setUserLogin(association.getUserLogin());
                    associationTranslated.setUserName(association.getUserName());
                    associationTranslated.setDateJort(association.getDateJort());
                    associationTranslated.setNumJort(association.getNumJort());
                    associationTranslated.setOrdre(association.getOrdre());
                    associationTranslated.setIsVerified(association.getIsVerified());
                    associationTranslated.setIsEnAvant(association.getIsEnAvant());
                    associationTranslated.setIsEnValeur(association.getIsEnValeur());
                    associationTranslated.setIsCoworkingSpace(association.getIsCoworkingSpace());
                    associationTranslated.setIsAllowComment(association.getIsAllowComment());
                    associationTranslated.setIsAllowRetrolien(association.getIsAllowRetrolien());

                    associationTranslatedList.add(associationTranslated);

                }




                if (lang.equals("ar")) {

                    Association associationTranslated = new Association();
                    associationTranslated.setId(association.getId());
                    associationTranslated.setLogo(association.getLogo());
                    associationTranslated.setTitle(association.getTitleAr());
                    associationTranslated.setDeuxVisaVisAssociation(association.getDeuxVisaVisAssociationAr());
                    associationTranslated.setAbreviation(association.getAbreviationAr());
                    associationTranslated.setAnneeFondation(association.getAnneeFondation());
                    associationTranslated.setDescription(association.getDescriptionAr());
                    associationTranslated.setEtat(association.getEtat());
                    associationTranslated.setVisibilite(association.getVisibilite());
                    associationTranslated.setDatePublication(association.getDatePublication());
                    associationTranslated.setPresident(association.getPresidentAr());
                    associationTranslated.setEmail(association.getEmail());
                    associationTranslated.setSiteWeb(association.getSiteWeb());
                    associationTranslated.setPhoneFixe(association.getPhoneFixe());
                    associationTranslated.setPhoneMobile(association.getPhoneMobile());
                    associationTranslated.setFax(association.getFax());
                    associationTranslated.setFacebook(association.getFacebook());
                    associationTranslated.setTwitter(association.getTwitter());
                    associationTranslated.setYoutube(association.getYoutube());
                    associationTranslated.setGooglePlus(association.getGooglePlus());
                    associationTranslated.setLinkedin(association.getLinkedin());
                    associationTranslated.setNote(association.getNote());
                    associationTranslated.setUserLogin(association.getUserLogin());
                    associationTranslated.setUserName(association.getUserName());
                    associationTranslated.setDateJort(association.getDateJort());
                    associationTranslated.setNumJort(association.getNumJort());
                    associationTranslated.setOrdre(association.getOrdre());
                    associationTranslated.setIsVerified(association.getIsVerified());
                    associationTranslated.setIsEnAvant(association.getIsEnAvant());
                    associationTranslated.setIsEnValeur(association.getIsEnValeur());
                    associationTranslated.setIsCoworkingSpace(association.getIsCoworkingSpace());
                    associationTranslated.setIsAllowComment(association.getIsAllowComment());
                    associationTranslated.setIsAllowRetrolien(association.getIsAllowRetrolien());

                    associationTranslatedList.add(associationTranslated);

                }

            }
        }


        int start = Math.min((int)pageable.getOffset(), associationTranslatedList.size());
        int end = Math.min((start + pageable.getPageSize()), associationTranslatedList.size());

        final Page<Association> page = new PageImpl<>(associationTranslatedList.subList(start,end), pageable, associationTranslatedList.size());
        return  page;


    }


    /**
     * Get all the associations.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Association> findAll() {
        log.debug("Request to get all Associations");
        return associationRepository.findAll();
    }


    /**
     * Get one association by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Association> findOne(UUID id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id);
    }


    /**
     * Get one association by id translated.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Association> findOne(UUID id, String lang) {
        log.debug("Request to get Association translated: {}", id);
        Optional<Association> association = associationRepository.findById(id);
        Location location = association.get().getLocation();
        Association associationTranslated = new Association();
        Location locationTranslated = new Location();

        if (lang.equals("fr")) {

            associationTranslated.setId(association.get().getId());
            associationTranslated.setLogo(association.get().getLogo());
            associationTranslated.setTitle(association.get().getTitleFr());
            associationTranslated.setDeuxVisaVisAssociation(association.get().getDeuxVisaVisAssociationFr());
            associationTranslated.setAbreviation(association.get().getAbreviationFr());
            associationTranslated.setAnneeFondation(association.get().getAnneeFondation());
            associationTranslated.setDescription(association.get().getDescriptionFr());
            associationTranslated.setEtat(association.get().getEtat());
            associationTranslated.setVisibilite(association.get().getVisibilite());
            associationTranslated.setDatePublication(association.get().getDatePublication());
            associationTranslated.setPresident(association.get().getPresidentFr());
            associationTranslated.setEmail(association.get().getEmail());
            associationTranslated.setSiteWeb(association.get().getSiteWeb());
            associationTranslated.setPhoneFixe(association.get().getPhoneFixe());
            associationTranslated.setPhoneMobile(association.get().getPhoneMobile());
            associationTranslated.setFax(association.get().getFax());
            associationTranslated.setFacebook(association.get().getFacebook());
            associationTranslated.setTwitter(association.get().getTwitter());
            associationTranslated.setYoutube(association.get().getYoutube());
            associationTranslated.setGooglePlus(association.get().getGooglePlus());
            associationTranslated.setLinkedin(association.get().getLinkedin());
            associationTranslated.setNote(association.get().getNote());
            associationTranslated.setUserLogin(association.get().getUserLogin());
            associationTranslated.setUserName(association.get().getUserName());
            associationTranslated.setDateJort(association.get().getDateJort());
            associationTranslated.setNumJort(association.get().getNumJort());
            associationTranslated.setOrdre(association.get().getOrdre());
            associationTranslated.setIsVerified(association.get().getIsVerified());
            associationTranslated.setIsEnAvant(association.get().getIsEnAvant());
            associationTranslated.setIsEnValeur(association.get().getIsEnValeur());
            associationTranslated.setIsCoworkingSpace(association.get().getIsCoworkingSpace());
            associationTranslated.setIsAllowComment(association.get().getIsAllowComment());
            associationTranslated.setIsAllowRetrolien(association.get().getIsAllowRetrolien());
            associationTranslated.setImageAssociations(association.get().getImageAssociations());

            //associationLocation

            locationTranslated.setId(location.getId());
            locationTranslated.setLocalNumber(location.getLocalNumber());
            locationTranslated.setStreetAddress(location.getStreetAddressFr());
            locationTranslated.setPostalCode(location.getPostalCode());
            locationTranslated.setCity(location.getCityFr());
            locationTranslated.setStateProvince(location.getStateProvinceFr());
            locationTranslated.setCountryName(location.getCountryNameFr());

            associationTranslated.setLocation(locationTranslated);

        }




        if (lang.equals("ar")) {

            associationTranslated.setId(association.get().getId());
            associationTranslated.setLogo(association.get().getLogo());
            associationTranslated.setTitle(association.get().getTitleAr());
            associationTranslated.setDeuxVisaVisAssociation(association.get().getDeuxVisaVisAssociationAr());
            associationTranslated.setAbreviation(association.get().getAbreviationAr());
            associationTranslated.setAnneeFondation(association.get().getAnneeFondation());
            associationTranslated.setDescription(association.get().getDescriptionAr());
            associationTranslated.setEtat(association.get().getEtat());
            associationTranslated.setVisibilite(association.get().getVisibilite());
            associationTranslated.setDatePublication(association.get().getDatePublication());
            associationTranslated.setPresident(association.get().getPresidentAr());
            associationTranslated.setEmail(association.get().getEmail());
            associationTranslated.setSiteWeb(association.get().getSiteWeb());
            associationTranslated.setPhoneFixe(association.get().getPhoneFixe());
            associationTranslated.setPhoneMobile(association.get().getPhoneMobile());
            associationTranslated.setFax(association.get().getFax());
            associationTranslated.setFacebook(association.get().getFacebook());
            associationTranslated.setTwitter(association.get().getTwitter());
            associationTranslated.setYoutube(association.get().getYoutube());
            associationTranslated.setGooglePlus(association.get().getGooglePlus());
            associationTranslated.setLinkedin(association.get().getLinkedin());
            associationTranslated.setNote(association.get().getNote());
            associationTranslated.setUserLogin(association.get().getUserLogin());
            associationTranslated.setUserName(association.get().getUserName());
            associationTranslated.setDateJort(association.get().getDateJort());
            associationTranslated.setNumJort(association.get().getNumJort());
            associationTranslated.setOrdre(association.get().getOrdre());
            associationTranslated.setIsVerified(association.get().getIsVerified());
            associationTranslated.setIsEnAvant(association.get().getIsEnAvant());
            associationTranslated.setIsEnValeur(association.get().getIsEnValeur());
            associationTranslated.setIsCoworkingSpace(association.get().getIsCoworkingSpace());
            associationTranslated.setIsAllowComment(association.get().getIsAllowComment());
            associationTranslated.setIsAllowRetrolien(association.get().getIsAllowRetrolien());
            associationTranslated.setImageAssociations(association.get().getImageAssociations());



            //associationLocation

            locationTranslated.setId(location.getId());
            locationTranslated.setLocalNumber(location.getLocalNumber());
            locationTranslated.setStreetAddress(location.getStreetAddressAr());
            locationTranslated.setPostalCode(location.getPostalCode());
            locationTranslated.setCity(location.getCityAr());
            locationTranslated.setStateProvince(location.getStateProvinceAr());
            locationTranslated.setCountryName(location.getCountryNameAr());

            associationTranslated.setLocation(locationTranslated);


        }


        return Optional.of(associationTranslated);
    }

    /**
     * Delete the association by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }





    /**
     * search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Association> searchAssociation(String attribute, String lang, Pageable pageable) {
        log.debug("Request to get all Associations");

        List<Association> associationList = associationRepository.findAllAssociationsWithPartOfAttribute(attribute);
        List<Association> associationTranslatedList = new ArrayList<>();


        if (associationList != null && associationList.size() != 0) {
            for (Association association : associationList) {


                if (lang.equals("fr")) {

                    Association associationTranslated = new Association();
                    associationTranslated.setId(association.getId());
                    associationTranslated.setLogo(association.getLogo());
                    associationTranslated.setTitle(association.getTitleFr());
                    associationTranslated.setDeuxVisaVisAssociation(association.getDeuxVisaVisAssociationFr());
                    associationTranslated.setAbreviation(association.getAbreviationFr());
                    associationTranslated.setAnneeFondation(association.getAnneeFondation());
                    associationTranslated.setDescription(association.getDescriptionFr());
                    associationTranslated.setEtat(association.getEtat());
                    associationTranslated.setVisibilite(association.getVisibilite());
                    associationTranslated.setDatePublication(association.getDatePublication());
                    associationTranslated.setPresident(association.getPresidentFr());
                    associationTranslated.setEmail(association.getEmail());
                    associationTranslated.setSiteWeb(association.getSiteWeb());
                    associationTranslated.setPhoneFixe(association.getPhoneFixe());
                    associationTranslated.setPhoneMobile(association.getPhoneMobile());
                    associationTranslated.setFax(association.getFax());
                    associationTranslated.setFacebook(association.getFacebook());
                    associationTranslated.setTwitter(association.getTwitter());
                    associationTranslated.setYoutube(association.getYoutube());
                    associationTranslated.setGooglePlus(association.getGooglePlus());
                    associationTranslated.setLinkedin(association.getLinkedin());
                    associationTranslated.setNote(association.getNote());
                    associationTranslated.setUserLogin(association.getUserLogin());
                    associationTranslated.setUserName(association.getUserName());
                    associationTranslated.setDateJort(association.getDateJort());
                    associationTranslated.setNumJort(association.getNumJort());
                    associationTranslated.setOrdre(association.getOrdre());
                    associationTranslated.setIsVerified(association.getIsVerified());
                    associationTranslated.setIsEnAvant(association.getIsEnAvant());
                    associationTranslated.setIsEnValeur(association.getIsEnValeur());
                    associationTranslated.setIsCoworkingSpace(association.getIsCoworkingSpace());
                    associationTranslated.setIsAllowComment(association.getIsAllowComment());
                    associationTranslated.setIsAllowRetrolien(association.getIsAllowRetrolien());

                    associationTranslatedList.add(associationTranslated);

                }




                if (lang.equals("ar")) {

                    Association associationTranslated = new Association();
                    associationTranslated.setId(association.getId());
                    associationTranslated.setLogo(association.getLogo());
                    associationTranslated.setTitle(association.getTitleAr());
                    associationTranslated.setDeuxVisaVisAssociation(association.getDeuxVisaVisAssociationAr());
                    associationTranslated.setAbreviation(association.getAbreviationAr());
                    associationTranslated.setAnneeFondation(association.getAnneeFondation());
                    associationTranslated.setDescription(association.getDescriptionAr());
                    associationTranslated.setEtat(association.getEtat());
                    associationTranslated.setVisibilite(association.getVisibilite());
                    associationTranslated.setDatePublication(association.getDatePublication());
                    associationTranslated.setPresident(association.getPresidentAr());
                    associationTranslated.setEmail(association.getEmail());
                    associationTranslated.setSiteWeb(association.getSiteWeb());
                    associationTranslated.setPhoneFixe(association.getPhoneFixe());
                    associationTranslated.setPhoneMobile(association.getPhoneMobile());
                    associationTranslated.setFax(association.getFax());
                    associationTranslated.setFacebook(association.getFacebook());
                    associationTranslated.setTwitter(association.getTwitter());
                    associationTranslated.setYoutube(association.getYoutube());
                    associationTranslated.setGooglePlus(association.getGooglePlus());
                    associationTranslated.setLinkedin(association.getLinkedin());
                    associationTranslated.setNote(association.getNote());
                    associationTranslated.setUserLogin(association.getUserLogin());
                    associationTranslated.setUserName(association.getUserName());
                    associationTranslated.setDateJort(association.getDateJort());
                    associationTranslated.setNumJort(association.getNumJort());
                    associationTranslated.setOrdre(association.getOrdre());
                    associationTranslated.setIsVerified(association.getIsVerified());
                    associationTranslated.setIsEnAvant(association.getIsEnAvant());
                    associationTranslated.setIsEnValeur(association.getIsEnValeur());
                    associationTranslated.setIsCoworkingSpace(association.getIsCoworkingSpace());
                    associationTranslated.setIsAllowComment(association.getIsAllowComment());
                    associationTranslated.setIsAllowRetrolien(association.getIsAllowRetrolien());

                    associationTranslatedList.add(associationTranslated);

                }

            }
        }


        int start = Math.min((int)pageable.getOffset(), associationTranslatedList.size());
        int end = Math.min((start + pageable.getPageSize()), associationTranslatedList.size());

        final Page<Association> page = new PageImpl<>(associationTranslatedList.subList(start,end), pageable, associationTranslatedList.size());
        return  page;


    }


}
