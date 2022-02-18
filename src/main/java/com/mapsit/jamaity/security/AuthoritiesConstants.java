package com.mapsit.jamaity.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String CompanyOwner = "ROLE_COMPANY_OWNER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String CompanyUser = "ROLE_COMPANY_USER";

    public static final String CompaktorAdmin  = "ROLE_COMPAKTOR_ADMIN";

    public static final String CompaktorTeam  = "ROLE_COMPAKTOR_TEAM";

    public static final String EshopClient = "ROLE_ESHOP_CLIENT";

//    public static final String DASHBOARD = "ROLE_DASHBOARD";

    public static final String SALE = "ROLE_SALE";

    public static final String PURCHASE = "ROLE_PURCHASE";

    public static final String STOCK = "ROLE_STOCK";

    public static final String TREASURY = "ROLE_TREASURY";

    public static final String ESHOP = "ROLE_ESHOP";




    private AuthoritiesConstants() {
    }
}
